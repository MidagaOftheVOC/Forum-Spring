package app.avatar;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
public class AvatarController {

    final private AvatarServiceClient theAvatarServiceClient;

    public AvatarController(
            AvatarServiceClient avatarServiceClient
    )
    {
        theAvatarServiceClient = avatarServiceClient;
    }



    @GetMapping("/avatar/{userId}")
    public ResponseEntity<String> getAvatar(@PathVariable UUID userId){
        return theAvatarServiceClient.getAvatar(userId);
    }

    @PostMapping("/upload_avatar/{userId}")
    public ResponseEntity<Void> uploadAvatar(@PathVariable("userId") UUID userId,
                                             @RequestParam("file") MultipartFile file)
    {
        return theAvatarServiceClient.uploadAvatar(userId, file);
    }

    @DeleteMapping("/delete_avatar/{userId}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable UUID userId) {
        return theAvatarServiceClient.deleteAvatar(userId);
    }


    @GetMapping("/avatar_test")
    public ResponseEntity<Void> test(){
        return theAvatarServiceClient.test();
    }

}
