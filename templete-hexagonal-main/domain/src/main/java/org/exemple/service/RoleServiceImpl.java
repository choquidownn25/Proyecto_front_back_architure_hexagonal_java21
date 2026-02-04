package org.exemple.service;


import org.exemple.data.ERoleDTO;
import org.exemple.data.RoleDTO;
import org.exemple.data.UserDTO;
import org.exemple.data.response.Message;
import org.exemple.ports.api.RoleServicePort;
import org.exemple.ports.spi.RolPersistencePort;
import org.exemple.utils.StringResponse;
import java.util.Set;

public class RoleServiceImpl implements RoleServicePort {

    private final RolPersistencePort rolPersistencePort;

    public RoleServiceImpl(RolPersistencePort rolPersistencePort) {
        this.rolPersistencePort = rolPersistencePort;
    }

    @Override
    public RoleDTO findByName(Set<String> strRoles, ERoleDTO name,  UserDTO userDTO) {
        //RoleDTOResponse roleDTOResponse = null;
        Message message = new Message();
        RoleDTO roleDTORes=rolPersistencePort.findByName(strRoles, name, userDTO);
        if(roleDTORes!=null){
            message.setEcho (StringResponse.ErrorSAVE.getName());
            message.setCode( StringResponse.ErrorSAVE.getCode());
            //roleDTOResponse.setMessage( message);
            throw new RuntimeException(message.getEcho().toString()+"\n" + message.getCode());
        }
        return roleDTORes;
    }

}
