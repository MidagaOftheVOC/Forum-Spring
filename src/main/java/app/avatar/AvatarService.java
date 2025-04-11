package app.avatar;

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

    public String getAvatarStoragePath(){
        return theAvatarClient.getStoragePath().getBody();
    }

    //  sole purpose is rerouting
    public void uploadAvatar(UUID userId, MultipartFile file){
        theAvatarClient.uploadAvatar(userId, file);
    }

    public String getAvatarUrl(UUID userId){
        ResponseEntity<String> response = theAvatarClient.getAvatar(userId);
        return response.getBody();
    }

    public void deleteAvatar(UUID userId){
        theAvatarClient.deleteAvatar(userId);
    }

    public void test(){
        theAvatarClient.test();
    }

}
