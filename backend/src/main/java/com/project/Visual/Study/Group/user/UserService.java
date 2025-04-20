package com.project.Visual.Study.Group.user;

import com.project.Visual.Study.Group.config.security.JWTService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTService jwtService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public ResponseEntity<LoginResponse> login(LoginCredentials credentials) {
        User user = userRepository.findByUsername(credentials.getUsername())
                .orElseThrow(() -> new BadCredentialsException("No such username exist"));

        if (!bCryptPasswordEncoder.matches(credentials.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("Wrong Password");
        }
        String token = jwtService.generateToken(credentials.getUsername());
        LoginResponse response = new LoginResponse(
                user.getId(),
                token,
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPhone()
        );
        userRepository.save(user);

        return ResponseEntity.ok(response);
    }

    public ResponseEntity<LoginResponse> register(RegisterCredentials credentials) {
        if (userRepository.findByUsername(credentials.getUsername()).isPresent()) {
            throw new RuntimeException("Username already taken");
        }
        String token = jwtService.generateToken(credentials.getUsername());

        User user = new User();
        user.setName(credentials.getName());
        user.setUsername(credentials.getUsername());
        user.setEmail(credentials.getEmail());
        user.setPhone(credentials.getPhone());
        user.setToken(token);
        user.setPassword(bCryptPasswordEncoder.encode(credentials.getPassword()));

        userRepository.save(user);

        LoginResponse response = new LoginResponse(
                user.getId(),
                token,
                user.getName(),
                user.getEmail(),
                user.getUsername(),
                user.getPhone()
        );

        return ResponseEntity.ok(response);
    }
}
