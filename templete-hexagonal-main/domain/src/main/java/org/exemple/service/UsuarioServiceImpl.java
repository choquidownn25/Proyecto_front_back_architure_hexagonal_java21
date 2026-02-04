package org.exemple.service;

import org.exemple.data.ERoleDTO;

import org.exemple.data.UserDTO;
import org.exemple.data.response.Message;
import org.exemple.data.response.UserDTOResponse;
import org.exemple.ports.api.UserServicePort;
import org.exemple.ports.spi.UserPersistencePort;
import org.exemple.utils.StringResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class UsuarioServiceImpl implements UserServicePort {
    private final UserPersistencePort userPersistencePort;

    public UsuarioServiceImpl(UserPersistencePort userPersistencePort) {
        this.userPersistencePort = userPersistencePort;
    }

    @Override
    public String findByUsername(String username) {

        UserDTO userRole = userPersistencePort.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
         return userRole.getUsername();
    }

    @Override
    public Boolean existsByUsername(String username) {
        Message message = new Message();
        Boolean respuesta = userPersistencePort.existsByUsername(username);
        if(respuesta == null|| respuesta == true){
            message.setEcho (StringResponse.ErrorSAVE.getName());
            message.setCode( StringResponse.ErrorSAVE.getCode());
            throw new RuntimeException(message.getEcho().toString()+"\n" + message.getCode());

        }
        return false;
    }

    @Override
    public Boolean existsByEmail(String email) {
        Message message = new Message();
        Boolean respuesta = userPersistencePort.existsByEmail(email);
        if(respuesta == null || respuesta == true){
            message.setEcho (StringResponse.ErrorSAVE.getName());
            message.setCode( StringResponse.ErrorSAVE.getCode());

            throw new RuntimeException(message.getEcho().toString()+"\n" + message.getCode());

        }
        return false;
    }

    @Override
    public UserDTOResponse save(UserDTO userDTO) {
        UserDTOResponse userDTOResponse=new UserDTOResponse();
        Message message = new Message();
        UserDTO userRespon = userPersistencePort.save(userDTO);
        List<UserDTO> listuserDTO = new ArrayList<>();
        if(userRespon == null){
            message.setEcho (StringResponse.ErrorSAVE.getName());
            message.setCode( StringResponse.ErrorSAVE.getCode());
            userDTOResponse.setMessage( message);
        }else {
            listuserDTO.add(userRespon);
            message.setEcho (StringResponse.OK.getName());
            message.setCode( StringResponse.OK.getCode());
            userDTOResponse.setMessage( message);
            userDTOResponse.setListUserDTO(listuserDTO);
        }
        return userDTOResponse;
    }

    @Override
    public UserDTOResponse registerUserSet(Set<String> strRoles, ERoleDTO name, UserDTO userDTO) {

        UserDTOResponse userDTOResponse =new UserDTOResponse();
        Message message = new Message();
        List<UserDTO> listuserDTO = new ArrayList<>();
        UserDTO roleDTORes = userPersistencePort.registerUser(strRoles, name,userDTO);
        if(roleDTORes==null){
            message.setEcho (StringResponse.ErrorSAVE.getName());
            message.setCode( StringResponse.ErrorSAVE.getCode());
            //roleDTOResponse.setMessage( message);
            throw new RuntimeException(message.getEcho().toString()+"\n" + message.getCode());
        }else {
            listuserDTO.add(roleDTORes);
            message.setEcho (StringResponse.OKUser.getName());
            message.setCode( StringResponse.OK.getCode());
            userDTOResponse.setMessage( message);
            userDTOResponse.setListUserDTO(listuserDTO);
        }
        return userDTOResponse;
    }

}
