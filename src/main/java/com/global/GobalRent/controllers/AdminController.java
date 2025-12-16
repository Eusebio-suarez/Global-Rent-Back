package com.global.GobalRent.controllers;

import com.cloudinary.Cloudinary;
import com.global.GobalRent.dto.request.CarRequestDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final Cloudinary cloudinary;

    @PostMapping(path = "/cars",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Map<String, Object> uploadFIle(@RequestPart("image")MultipartFile image, @RequestPart("data")CarRequestDTO car) throws IOException {

        Map<String,Object> uploadResult = cloudinary.uploader().upload(
                image.getBytes(),
                Map.of("folder","cars")
        );

        String imageUrl = (String) uploadResult.get("secure_url");

        String public_id = (String) uploadResult.get("public_id");

        return  uploadResult;

    }

}
