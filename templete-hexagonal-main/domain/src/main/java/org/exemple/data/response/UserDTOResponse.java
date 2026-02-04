package org.exemple.data.response;

import lombok.*;
import org.exemple.data.UserDTO;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class UserDTOResponse {
    private List<UserDTO> listUserDTO;
    private Message message;
}
