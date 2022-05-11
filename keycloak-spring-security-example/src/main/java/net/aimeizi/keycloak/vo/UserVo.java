package net.aimeizi.keycloak.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserVo implements Serializable {

    private String email;
    private String password;
    private String firstname;
    private String lastname;
    private String status;
    private int statusCode;

}
