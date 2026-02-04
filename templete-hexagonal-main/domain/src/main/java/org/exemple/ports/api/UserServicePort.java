package org.exemple.ports.api;

import org.exemple.data.ERoleDTO;
import org.exemple.data.UserDTO;
import org.exemple.data.response.UserDTOResponse;

import java.util.Set;

public interface UserServicePort {
    String findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);
    UserDTOResponse save(UserDTO userDTO);
    UserDTOResponse registerUserSet(Set<String> strRoles, ERoleDTO name, UserDTO userDTO);
}
