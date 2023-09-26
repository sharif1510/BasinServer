package com.basinda.api.user;

import com.basinda.services.FlatService;
import com.basinda.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class UploadImageController {
    private final UserService userService;
    private final FlatService flatService;

    public UploadImageController(UserService userService, FlatService flatService) {
        this.userService = userService;
        this.flatService = flatService;
    }

    @PostMapping(value = "/upload/profile/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadProfileImage(@PathVariable("userId") Long userId, @RequestParam("files") MultipartFile image){
        if (isEmpty(image)){
            throw new IllegalArgumentException("Files missing.");
        }
        userService.uploadProfileImage(userId,image);
    }

    @PostMapping(value = "/upload/nid-font/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadNidCardFontImage(@PathVariable("userId") Long userId, @RequestParam("files") MultipartFile image){
        if (isEmpty(image)){
            throw new IllegalArgumentException("Files missing.");
        }
        userService.uploadNidCardFontImage(userId,image);
    }

    @GetMapping(value = "/download/nid-font/{userId}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] downloadNidFontImage(@PathVariable("userId") Long userId){
        return userService.downloadNidFontImage(userId);
    }

    @PostMapping(value = "/upload/nid-back/{userId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadNidCardBackImage(@PathVariable("userId") Long userId, @RequestParam("files") MultipartFile image){
        if (isEmpty(image)){
            throw new IllegalArgumentException("Files missing.");
        }
        userService.uploadNidCardBackImage(userId,image);
    }

    @GetMapping(value = "/download/nid-back/{userId}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] downloadNidCardBackImage(@PathVariable("userId") Long userId){
        return userService.downloadNidBackImage(userId);
    }

    @GetMapping(value = "/download/profile/{userId}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] downloadProfileImage(@PathVariable("userId") Long userId){
        return userService.downloadProfileImage(userId);
    }

    @PostMapping(value = "/upload/flat/{flatId}",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadFlatImage(@PathVariable("flatId") Long flatId, @RequestParam("files") MultipartFile image){
        if (isEmpty(image)){
            throw new IllegalArgumentException("Files missing.");
        }
        flatService.uploadFlatImage(flatId,image);
    }

    @GetMapping(value = "/download/flat/{flatId}",
            produces = MediaType.IMAGE_JPEG_VALUE)
    public byte[] downloadFlatImage(@PathVariable("flatId") Long flatId){
        return flatService.downloadFlatImage(flatId);
    }

    private boolean isEmpty(MultipartFile image){
        return image == null;
    }
}