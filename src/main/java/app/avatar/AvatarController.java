package app.avatar;

import app.GCV;
import feign.FeignException;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

        ResponseEntity<String> response = theAvatarServiceClient.getAvatar(userId);

        System.out.println(response.getBody());

        if(!response.getStatusCode().is2xxSuccessful()){
            System.out.println("Entering @get in /avatar/{userId} ERROR CLAUSE");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        return response;
    }

    @PostMapping("/upload_avatar/{userId}")
    public void uploadAvatar(@PathVariable("userId") UUID userId,
                             @RequestParam("file") MultipartFile file,
                             HttpServletResponse response) throws IOException {
        if (GCV.isDebugging()) System.out.println("Entered avatar-svc POST mapping in monolith");

        // Unfortunately, java can't handle a non 2xx response without crashing
        // so we will do this ugliness instead
        ResponseEntity<Void> uploadResponse;
        try {
            uploadResponse = theAvatarServiceClient.uploadAvatar(userId, file);

        } catch(HttpClientErrorException | HttpServerErrorException ex){

            if(ex.getStatusCode().equals(HttpStatus.CONFLICT)){ // this is for changing the avatar
                ResponseEntity<Void> changeResponse = theAvatarServiceClient.deleteAvatar(userId);

                if( changeResponse.equals(HttpStatus.NO_CONTENT)){   // sucessful DELETE
                    changeResponse = theAvatarServiceClient.uploadAvatar(userId, file);

                    if(changeResponse.getStatusCode().is2xxSuccessful()){
                        response.sendRedirect("/user/edit/" + userId);
                    }
                }
            }   /*   end change avatar block   */
            throw new RuntimeException("AvatarController-@PostMapping :: %s".formatted(ex.getStatusCode().toString()));
        }
        response.sendRedirect("/user/edit/" + userId);
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
