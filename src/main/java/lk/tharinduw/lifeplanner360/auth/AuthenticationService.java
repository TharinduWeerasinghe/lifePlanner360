package lk.tharinduw.lifeplanner360.auth;

import lk.tharinduw.lifeplanner360.config.JwtService;
import lk.tharinduw.lifeplanner360.user.Role;
import lk.tharinduw.lifeplanner360.user.User;
import lk.tharinduw.lifeplanner360.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UserRepository repository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) throws ParseException {
//        DateFormat formatter = new SimpleDateFormat("MM/dd/yy");
//        Date date = formatter.parse("01/29/02");
        var user = User.builder()
                .userEmail(request.getUserEmail())
                .userPassword(passwordEncoder.encode(request.getUserPassword()))
                .userFirstName(request.getUserFirstName())
                .userLastName(request.getUserLastName())
                .userDOB(request.getUserDOB())
                .userGender(request.getUserGender())
                .userRole(Role.USER)
                .build();
        repository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = repository.findByUserEmail(request.getEmail()).orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
