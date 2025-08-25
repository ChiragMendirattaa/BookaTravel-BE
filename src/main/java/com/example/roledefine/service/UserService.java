package com.example.roledefine.service;

import com.example.roledefine.dto.RegistrationRequest;
import com.example.roledefine.dto.hoteldto.request.HotelBookingRequestDTO;
import com.example.roledefine.entity.AnonymousUser;
import com.example.roledefine.entity.HotelTransaction;
import com.example.roledefine.entity.User;
import com.example.roledefine.repository.AnonymousUserRepository;
import com.example.roledefine.repository.HotelTransactionRepository;
import com.example.roledefine.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final HotelTransactionRepository hotelTransactionRepository;
    private final AnonymousUserRepository anonymousUserRepository;
    private final ObjectMapper objectMapper;


    public User registerNewUser(RegistrationRequest registrationRequest) throws Exception {
        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            throw new Exception("Username already exists");
        }
        User newUser = new User();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword()));
        newUser.setRole(registrationRequest.getRole());
        return userRepository.save(newUser);
    }

    @Transactional
    public void mergeAnonymousData(UUID anonymousId, String username) {
        User user = userRepository.findByUsername(username).orElse(null);
        AnonymousUser anonymousUser = anonymousUserRepository.findById(anonymousId).orElse(null);

        if (user == null || anonymousUser == null) {
            log.warn("User or AnonymousUser not found, cannot merge data.");
            return;
        }

        List<HotelTransaction> guestTransactions = hotelTransactionRepository.findByAnonymousUser(anonymousUser);
        int mergedCount = 0;

        for (HotelTransaction transaction : guestTransactions) {
            try {
                HotelBookingRequestDTO bookingRequest = objectMapper.readValue(transaction.getBookingRequestData(), HotelBookingRequestDTO.class);
                String guestEmail = bookingRequest.getCustomerEmail();

                if (user.getUsername().equalsIgnoreCase(guestEmail)) {
                    transaction.setAnonymousUser(null);
                    transaction.setUser(user);
                    hotelTransactionRepository.saveAndFlush(transaction);
                    mergedCount++;
                }
            } catch (Exception e) {
                log.error("Could not parse booking data for transaction ID: {}", transaction.getId(), e);
            }
        }

        if (mergedCount > 0 && mergedCount == guestTransactions.size()) {
            anonymousUserRepository.delete(anonymousUser);
            log.info("Successfully merged all {} booking(s) and deleted anonymous user {}", mergedCount, anonymousId);
        } else if (mergedCount > 0) {
            log.info("Successfully merged {} booking(s) for user {}. Other bookings remain for anonymous user {}", mergedCount, username, anonymousId);
        }
    }
}