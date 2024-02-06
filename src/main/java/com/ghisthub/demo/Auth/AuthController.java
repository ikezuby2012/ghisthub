package com.ghisthub.demo.Auth;

import com.ghisthub.demo.Model.Response.CustomResponse;
import com.ghisthub.demo.Model.Role;
import com.ghisthub.demo.Model.request.SignupRequest;
import com.ghisthub.demo.User.User;
import com.ghisthub.demo.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    @PostMapping("/signup")
    public ResponseEntity<CustomResponse> registerUser(@RequestBody SignupRequest body) throws Exception {
        if (userRepository.existsByEmail(body.getEmail())) {
            return ResponseEntity.badRequest().body(new CustomResponse(HttpStatus.BAD_REQUEST, false, "email does not exist!", 0, null));
        }

        User user = User.builder()
                .name(body.getFullName())
                .email(body.getEmail())
                .DOB(body.getDOB())
                .password(encoder.encode(body.getPassword()))
                .role(Role.USER)
                .build();

        userRepository.save(user);

        return ResponseEntity.ok().body(new CustomResponse(HttpStatus.CREATED, true, "User registered successfully!", 1, user));
    }
}
