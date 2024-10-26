package com.example.AuctionProject.auction.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Random;

@Service
public class ImageService {
    @Value("${upload.path}") // application.properties에서 파일 업로드 경로 설정
    private String uploadPath;

    public String saveImage(MultipartFile imageFile) throws IOException {
        // 이미지 파일을 업로드 경로에 저장하고, 파일 경로를 반환

        Random random = new Random();
        System.out.println(random);


        String fileName = random.toString() + ".png";

        Path filePath = Paths.get(uploadPath, fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // 상대 경로로 반환
        return fileName;
    }

    public void deleteImage(String fileName) throws IOException {
        // 파일 시스템에서 이미지 파일 삭제
        Path filePath = Paths.get(uploadPath, fileName);
        Files.deleteIfExists(filePath);
    }
}
