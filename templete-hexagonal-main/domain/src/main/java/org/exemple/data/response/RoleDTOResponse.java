package org.exemple.data.response;

import lombok.*;
import org.exemple.data.RoleDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RoleDTOResponse {
    private List<RoleDTO> listRoleDTO;
    private Message message;
}
