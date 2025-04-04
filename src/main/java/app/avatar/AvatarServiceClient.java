package app.avatar;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@FeignClient(name = "avatar-svc", url = "${avatar.microservice.base-url}")  // MS URL
public interface AvatarServiceClient {

    @GetMapping("/{userId}")
    public ResponseEntity<String> getAvatar(@PathVariable("userId") UUID userId);

    @PostMapping("/upload_avatar/{userId}")
    public ResponseEntity<Void> uploadAvatar(@PathVariable("userId") UUID userId,
                             @RequestParam("file") MultipartFile file); // image date from browser to be sent to MS

    @DeleteMapping("/delete_avatar/{userId}")
    public ResponseEntity<Void> deleteAvatar(@PathVariable("userId") UUID userId);

    @GetMapping("/avatar_test")
    public ResponseEntity<Void> test();

}


