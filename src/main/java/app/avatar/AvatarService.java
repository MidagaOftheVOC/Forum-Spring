package app.avatar;

import app.web.AvatarServiceClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
public class AvatarService {

    final private AvatarServiceClient theAvatarClient;

    public AvatarService(
            AvatarServiceClient avatarServiceClient
    )
    {
        theAvatarClient = avatarServiceClient;
    }

    //  sole purpose is rerouting
    public void uploadAvatar(UUID userId, MultipartFile file){
        theAvatarClient.uploadAvatar(userId, file);
    }

    public String getAvatarUrl(UUID userId){
        System.out.printf("Getting avatar URL for user [%s]", userId.toString());

        ResponseEntity<String> response = theAvatarClient.getAvatar(userId);

        // This one always returns 200 OK

        System.out.println("GET request returns: " + response.getBody());
        return response.getBody();
    }

    public void deleteAvatar(UUID userId){
        theAvatarClient.deleteAvatar(userId);
    }

    public void test(){
        theAvatarClient.test();
    }

}
