package com.ludogorieSoft.villagelifefrontend.service;

import com.ludogorieSoft.villagelifefrontend.dtos.VillageDTO;
import io.minio.*;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VillageImageService {
    @Value("${digital.ocean.bucket.name}")
    private String digitalOceanBucketName;
    private final MinioClient minioClient;

    public String uploadImage(final MultipartFile file, String randomUuid) {
        try {
            // Upload the file to DigitalOcean Spaces
            minioClient.putObject(PutObjectArgs.builder()
                    .bucket(digitalOceanBucketName)
                    .object(randomUuid )
                    .contentType(file.getContentType())
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .build());

            return randomUuid;
        } catch (MinioException e) {
            // Handle Minio-specific exceptions
            log.warn("Minio error: " + e.getMessage());
        } catch (IOException e) {
            // Handle general IO exceptions
            log.warn("Error uploading file: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            log.warn("An unexpected error occurred: " + e.getMessage());
        }
        return null;
    }

    public String getImageFromSpace(String objectKey) {
        try {
            // Fetch the image bytes from DigitalOcean Spaces
            GetObjectResponse objectResponse = minioClient.getObject(GetObjectArgs.builder()
                    .bucket(digitalOceanBucketName)
                    .object(objectKey)
                    .build());

            // Get the input stream from the object response
            try (InputStream inputStream = objectResponse) {
                // Read the image bytes
                byte[] imageBytes = IOUtils.toByteArray(inputStream);

                // Encode the image bytes as a Base64 string
                String base64Image = Base64.encodeBase64String(imageBytes);

                return base64Image;
            }
        } catch (MinioException e) {
            // Handle Minio-specific exceptions
            log.warn("Minio error: " + e.getMessage());
        } catch (Exception e) {
            // Handle other exceptions
            log.warn("An unexpected error occurred: " + e.getMessage());
        }
        return null;
    }

    public List<VillageDTO> getVillagesWithImages(List<VillageDTO> villages){
     return villages.stream()
                .map(villageDTO -> {
                    System.out.println("service images stream1 " + villageDTO.toString());
                    System.out.println("service images stream2 " + villageDTO.getImages());
                    villageDTO.setImages(villageDTO.getImages().stream()
                            .map(this::getImageFromSpace)
                            .toList());

                    System.out.println("controller images stream3 " + villageDTO.getImages());

                    return villageDTO;
                }).toList();
    }

    public boolean deleteImage(String objectName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(digitalOceanBucketName)
                            .object(objectName)
                            .build());
            return true;
        } catch (MinioException e) {
            log.warn("Minio error while deleting image: " + e.getMessage());
        } catch (Exception e) {
            log.warn("An unexpected error occurred: " + e.getMessage());
        }
        return false;
    }
}
