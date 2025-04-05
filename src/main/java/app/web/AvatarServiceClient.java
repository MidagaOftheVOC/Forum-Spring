package app.web;


import app.avatar.config.FeignMultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@FeignClient(
        name = "avatar-svc",                                    //  useless
        url = "${avatar.microservice.base-url}",                //  URL
        configuration = FeignMultipartSupportConfig.class       //  config for file transfer
)
public interface AvatarServiceClient {

    @GetMapping("/avatar/{userId}")
    public ResponseEntity<String> getAvatar(@PathVariable("userId") UUID userId);

    @PostMapping(value = "/upload_avatar/{userId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> uploadAvatar(@PathVariable("userId") UUID userId,
                             @RequestPart("file") MultipartFile file); // image date from browser to be sent to MS

    @DeleteMapping("/delete_avatar/{userId}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable("userId") UUID userId);

    @GetMapping("/avatar_test")
    public ResponseEntity<Void> test();

}


