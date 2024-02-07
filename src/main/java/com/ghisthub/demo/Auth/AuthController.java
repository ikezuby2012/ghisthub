package com.ghisthub.demo.Auth;

import com.ghisthub.demo.Model.Response.CustomResponse;
import com.ghisthub.demo.Model.Response.LoginResponse;
import com.ghisthub.demo.Model.Role;
import com.ghisthub.demo.Model.request.LoginRequest;
import com.ghisthub.demo.Model.request.SignupRequest;
import com.ghisthub.demo.User.User;
import com.ghisthub.demo.User.UserRepository;
import com.ghisthub.demo.UserDetails.UserDetailsServiceImpl;
import com.ghisthub.demo.config.JwtService;
import io.lettuce.core.ScriptOutputType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

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

    @PostMapping("/login")
    public ResponseEntity<?> signInUser(@RequestBody LoginRequest body) throws Exception {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(body.getEmail(), body.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetails userDetails = userDetailsService.loadUserByUsername(body.getEmail());
        Optional<User> getUser = userRepository.findByEmail(body.getEmail());

        if (getUser.isEmpty()) {
            throw new Exception("user not found");
        }
        User user = getUser.get();
        String jwt = jwtService.generateToken(userDetails, user);

        return ResponseEntity.ok().body(new LoginResponse(HttpStatus.OK, jwt, "Login successful", user));
    }
}
