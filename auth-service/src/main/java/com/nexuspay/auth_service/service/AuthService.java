package com.nexuspay.auth_service.service;

import com.nexuspay.auth_service.dto.UserCreatedEvent;
import com.nexuspay.auth_service.model.User;
import com.nexuspay.auth_service.repository.UserRepository;
import com.nexuspay.auth_service.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // String yerine Object kullanarak DTO göndermeyi sağlıyoruz
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public String register(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole("ROLE_USER");

        // Önce kullanıcıyı kaydediyoruz
        User savedUser = userRepository.save(user);

        // KAFKA'YA MESAJ GÖNDER: DTO paketini hazırlıyoruz
        UserCreatedEvent event = new UserCreatedEvent(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail()
        );

        log.info("Kafka'ya user-created-topic üzerinden mesaj gönderiliyor: {}", event);

        // Mesajı gönderiyoruz
        kafkaTemplate.send("user-created-topic", event);

        return "Kullanıcı başarıyla kaydedildi!";
    }

    public String login(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Kullanıcı bulunamadı!"));

        if (passwordEncoder.matches(password, user.getPassword())) {
            return jwtUtil.generateToken(username);
        } else {
            throw new RuntimeException("Hatalı şifre!");
        }
    }
}