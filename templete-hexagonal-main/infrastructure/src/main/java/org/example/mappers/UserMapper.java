package org.example.mappers;

import org.example.entity.User;
import org.example.entity.User;
import org.exemple.data.UserDTO;
import org.exemple.data.UserDTO;
import org.mapstruct.Mapper;
import org.mapstruct.control.MappingControl;
import org.mapstruct.factory.Mappers;

import java.util.List;
@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
    UserDTO userDTOToUser(User user);
    User userToUserDTO(UserDTO userDTO);
    //listado
    List<UserDTO> userDtoListToUserList(List<User> userList);
    List<User> userListToUserDtoList(List<UserDTO> userDTOList);

}
