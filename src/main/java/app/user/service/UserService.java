package app.user.service;


import app.GCV;
import app.security.AuthenticationUserData;
import app.user.RegisteringExistingUserException;
import app.user.model.User;
import app.user.model.UserStatus;
import app.user.model.UserType;
import app.user.repository.UserRepository;
import app.web.dto.RegistrationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;


@Slf4j
@Service
public class UserService implements UserDetailsService {

    private final UserRepository theUserRepository;
    private final PasswordEncoder thePasswordEncoder;

    public UserService(
            UserRepository _newUserRepo,
            PasswordEncoder _passEncoder
    )
    {
        theUserRepository = _newUserRepo;
        thePasswordEncoder = _passEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String _username) throws UsernameNotFoundException {

        User user= theUserRepository.findByUsername(
                            _username
                    )
                    .orElseThrow(() -> new UsernameNotFoundException("Username not found."));

        GCV.info("User just logged in!");

        if(GCV.isDebugging()){
            GCV.debug(user.debugString());
        }



        return new AuthenticationUserData(
            user.getId(), _username, user.getUserhash(), user.getUserStatus(), user.getUserType()
        );
    }

    /**
     *
     * @param registrationRequest
     * @return User object that was parsed and added to the database OR null if GCV.DODGE_EXCEPTION is true
     */
    @Transactional  // spring ver
    public User register(RegistrationRequest registrationRequest) {

        Optional<User> optionUser = theUserRepository.findByUsernameOrEmail(
                registrationRequest.getUsername(),
                registrationRequest.getEmail()
        );


        if(optionUser.isPresent()){

            if(GCV.isDodgingExceptions())
            {
                log.error("Username and email from reg. req.:\n[%s]\t[%s]".formatted(
                        registrationRequest.getUsername(),
                        registrationRequest.getEmail()
                ));

                return null;
            }
            else
                throw new RegisteringExistingUserException("Username and email from reg. req.:\n[%s]\t[%s]".formatted(
                        registrationRequest.getUsername(),
                        registrationRequest.getEmail()
                ));
        }

        if(registrationRequest.hasAltUsername()){
            optionUser = theUserRepository.findByShownUsername(registrationRequest.getShownUsername());

            if(optionUser.isPresent()){
                throw new RegisteringExistingUserException("Shown username from reg. req.: [%s]".formatted(
                        registrationRequest.getShownUsername()
                ));
            }
        }


        String hash = thePasswordEncoder.encode(registrationRequest.getRawPassword());

        User newUser = new User();

        newUser.setUsername(registrationRequest.getUsername());
        newUser.setUserhash(hash);
        newUser.setShownUsername(registrationRequest.getShownUsername());
        newUser.setEmail(registrationRequest.getEmail());

        //newUser.setId(UUID.randomUUID());
        newUser.setUserStatus(UserStatus.ACTIVE);
        newUser.setUserType(UserType.STANDARD);

        newUser.setCreationDate(LocalDateTime.now());
        newUser.setLastActiveDate(LocalDateTime.now());

        newUser.setLastRedactDate(null);
        newUser.setAvatarUrl(null);
        newUser.setQuote(null);

        // dates are created at entry creation in DB

        theUserRepository.save(newUser);

        //  TODO:   here go the notification extra project
        //  TODO:   remove debugger check
        if(GCV.isDebugging()){
            log.debug(
                    "DEBUG <> User registered with creds:\nU: [%s]\tRawP: [%s]\tE: [%s]"
                            .formatted(
                                    registrationRequest.getUsername(),
                                    registrationRequest.getRawPassword(),
                                    registrationRequest.getShownUsername()
                            )
            );
        }
        else
            log.info("User [%s] registered!".formatted(newUser.getUsername()));

        return newUser;
    }
}
