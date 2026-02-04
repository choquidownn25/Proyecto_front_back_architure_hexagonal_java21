package org.exemple.ports.spi;

import org.exemple.data.ERoleDTO;
import org.exemple.data.UserDTO;

import java.util.Optional;
import java.util.Set;

public interface UserPersistencePort {
    Optional<UserDTO> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    UserDTO save(UserDTO userDTO);
    UserDTO registerUser(Set<String> strRoles, ERoleDTO name, UserDTO userDTO);
}
