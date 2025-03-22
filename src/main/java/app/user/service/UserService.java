package app.user.service;


import app.GCV;
import app.security.AuthenticationUserData;
import app.user.model.User;
import app.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository theUserRepository;


    public UserService(
            UserRepository _newUserRepo
    )
    {
        theUserRepository = _newUserRepo;
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


}
