package org.exemple.ports.spi;


import org.exemple.data.ERoleDTO;
import org.exemple.data.RoleDTO;
import org.exemple.data.UserDTO;

import java.util.Optional;
import java.util.Set;

public interface RolPersistencePort {
    RoleDTO findByName(Set<String> strRoles, ERoleDTO name,  UserDTO userDTO);
}
