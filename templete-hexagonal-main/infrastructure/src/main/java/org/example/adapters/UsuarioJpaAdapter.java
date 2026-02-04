package org.example.adapters;

import org.example.entity.ERole;
import org.example.entity.Role;
import org.example.entity.User;
import org.example.mappers.UserMapper;
import org.example.repository.RoleRepository;
import org.example.repository.UserRepository;
import org.exemple.data.ERoleDTO;
import org.exemple.data.UserDTO;
import org.exemple.data.response.Message;
import org.exemple.data.response.MessageResponse;
import org.exemple.ports.spi.RolPersistencePort;
import org.exemple.ports.spi.UserPersistencePort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

    public class UsuarioJpaAdapter implements UserPersistencePort {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RolPersistencePort rolPersistencePort;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    private RoleRepository roleRepository;


    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(UserMapper.INSTANCE::userDTOToUser);
    }

    @Override
    public Boolean existsByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Error: Username is already taken!");
        }else
            return false;
    }

    @Override
    public Boolean existsByEmail(String email) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Error: Email is already in use!");
        }else
        return false;
    }

    @Override
    public UserDTO save(UserDTO userDTO) {
        User userResult = UserMapper.INSTANCE.userToUserDTO(userDTO);
        User userSave = userRepository.save(userResult);
        UserDTO userDTORepose = UserMapper.INSTANCE.userDTOToUser(userSave);
        return userDTORepose;
    }

    @Override
    public UserDTO registerUser(Set<String> strRoles, ERoleDTO name, UserDTO userDTO) {

        ERole.valueOf(String.valueOf(name));
        System.out.println("Dato -> " + ERole.valueOf(String.valueOf(name)));
        System.out.println("Dato -> " + Collections.singleton(name.name()));
        System.out.println("Dato Roles  -> " + strRoles);

//        User user = new User(userDTO.getUsername(),
//                userDTO.getEmail(),
//                encoder.encode(userDTO.getPassword()));
        User user = new User(
                userDTO.getUsername(),
                userDTO.getEmail(),
                userDTO.getPassword() // YA viene encriptado
        );


        Set<String> strRolest = strRoles;
        System.out.println("Dato Roles  -> " + strRolest);
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
        user.setRoles(roles);
        User userdLeave = userRepository.save(user);
        UserDTO userDTORepose = UserMapper.INSTANCE.userDTOToUser(userdLeave);
        return userDTORepose;
    }
}
