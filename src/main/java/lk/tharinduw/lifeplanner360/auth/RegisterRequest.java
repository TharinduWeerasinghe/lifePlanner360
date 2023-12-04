package lk.tharinduw.lifeplanner360.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    private String userEmail;
    private String userPassword;
    private String userFirstName;
    private String userLastName;
    private Date userDOB;
    private String userGender;
}
