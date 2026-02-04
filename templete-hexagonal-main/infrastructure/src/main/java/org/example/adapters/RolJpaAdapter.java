package org.example.adapters;

import org.example.entity.ERole;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.mappers.RolMapper;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.exemple.data.ERoleDTO;
import org.exemple.data.RoleDTO;
import org.exemple.data.UserDTO;
import org.exemple.ports.spi.RolPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class RolJpaAdapter implements RolPersistencePort {

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Override
    public RoleDTO findByName(Set<String> strRoles, ERoleDTO name, UserDTO userDTO) {
        ERole.valueOf(String.valueOf(name));
        System.out.println("Dato -> " + ERole.valueOf(String.valueOf(name)));
        System.out.println("Dato -> " + Collections.singleton(name.name()));
        System.out.println("Dato Roles  -> " + strRoles);

        User user = new User(userDTO.getUsername(),
                userDTO.getEmail(),
                encoder.encode(userDTO.getPassword()));

        Set<Role> roles = new HashSet<>();
        if (strRoles == null) {
            Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        Role userRole = roleRepository.findByName(ERole.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        //user.setRoles(roles);
        userRepository.save(user);
        return null;
    }


}
