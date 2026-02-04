package org.example.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import jakarta.validation.Valid;
import org.example.security.jwt.JwtUtils;
import org.example.security.services.UserDetailsImpl;
import org.exemple.data.ERoleDTO;
import org.exemple.data.RoleDTO;
import org.exemple.data.UserDTO;
import org.exemple.data.request.LoginRequest;
import org.exemple.data.request.SignupRequest;
import org.exemple.data.response.JwtResponse;
import org.exemple.data.response.MessageResponse;
import org.exemple.data.response.ProductoDtoResponse;
import org.exemple.data.response.UserDTOResponse;
import org.exemple.ports.api.UserServicePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserServicePort userRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;
    private static final String API_URL = "https://api.json2video.com/v1/render";
    private static final String API_KEY = "XoTtPDM2VxGcbBC5G89eLfaGPqNeJb2dwB1cEIch";

    private final RestTemplate restTemplate = new RestTemplate();
    @PostMapping("/generar")
    public ResponseEntity<String> generarVideo(@RequestBody String payload) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(API_KEY);

        HttpEntity<String> request = new HttpEntity<>(payload, headers);

        ResponseEntity<String> response =
                restTemplate.postForEntity(API_URL, request, String.class);

        return response;
    }
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String token = jwtUtils.generateToken(userDetails);
            UserDetailsImpl userDetail = (UserDetailsImpl) authentication.getPrincipal();
            List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());

                return ResponseEntity.ok(new JwtResponse(token,
                        userDetail.getId(),
                        userDetails.getUsername(),
                        userDetail.getEmail(),
                        roles));

    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        UserDTOResponse userRole=new UserDTOResponse();
        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        // Create new user's account
        UserDTO user = new UserDTO(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        if (strRoles == null) {
             userRole= userRepository.registerUserSet(strRoles, ERoleDTO.ROLE_USER, user);
        } else {
             userRole= userRepository.registerUserSet(strRoles, ERoleDTO.ROLE_USER, user);
        }

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}