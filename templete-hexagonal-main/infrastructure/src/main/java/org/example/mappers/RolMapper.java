package org.example.mappers;

import org.example.entity.Producto;
import org.example.entity.Role;
import org.exemple.data.ProductoDto;
import org.exemple.data.RoleDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Optional;

@Mapper
public interface RolMapper {
    RolMapper INSTANCE = Mappers.getMapper(RolMapper.class);
    RoleDTO roleDTOToRole(Role role);
    Role roleToRoleDTO(RoleDTO roleDTO);
    //listado
    List<RoleDTO> roleDtoListToRoleList(List<Role> roleList);
    List<Role> roleListToroleDtoList(List<RoleDTO> roleDTOList);
    //
    //Optional<RoleDTO> optroleDtoListToRoleList(Optional<Role> roleList);

}
