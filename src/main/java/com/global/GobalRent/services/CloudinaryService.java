package com.global.GobalRent.services;

import com.cloudinary.Cloudinary;
import com.global.GobalRent.entity.ImgEntity;
import com.global.GobalRent.exceptions.ImgUploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CloudinaryService {

    private final Cloudinary cloudinary;

    public ImgEntity uploadImg(MultipartFile image){

        try {
            Map<String,Object> uploadResult = cloudinary.uploader().upload(
                    image.getBytes(),
                    Map.of("folder","cars")
            );

            String securedUrl = (String) uploadResult.get("secure_url");

            String publicId = (String) uploadResult.get("public_id");

            return ImgEntity.builder()
                    .securedUrl(securedUrl)
                    .publicId(publicId)
                    .build();

        } catch (Exception e) {
            throw new ImgUploadException("Error al cargar la imagen");
        }

    }
}
