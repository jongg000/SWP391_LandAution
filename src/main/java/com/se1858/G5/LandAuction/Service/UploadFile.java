package com.se1858.G5.LandAuction.Service;

import com.se1858.G5.LandAuction.Entity.AssetRegistration;
import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.LandImage;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

public class UploadFile {
    public void uploadImageForLand(List<MultipartFile> images, Land land) {
        String imgUploadDir = "src/main/resources/static/img/";
        File directory = new File(imgUploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                try{
                    String originalFilename = image.getOriginalFilename();
                    if(originalFilename != null) {
                        continue;
                    }
                    String nameFile = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                    String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                    String imgName = "Asset" + nameFile + "." + fileExtension;
                    Path path = Paths.get(imgUploadDir + imgName);
                    // Kiểm tra file đã tồn tại hay chưa, nếu có thì thêm số phiên bản vào
                    int version = 1;
                    while (Files.exists(path)) {
                        imgName = "Asset_" + nameFile + "(" + version + ")" + fileExtension;
                        path = Paths.get(imgUploadDir + imgName);
                        version++;
                    }
                    // Lưu tệp vào thư mục
                    byte[] bytes = image.getBytes();
                    Files.write(path, bytes);
                    // Tạo đối tượng Image và liên kết với Land
                    LandImage landImage = new LandImage();
                    landImage.setName(nameFile);
                    landImage.setLand(land);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
//    public void upLoadDocumentAsset(MultipartFile document, AssetRegistration assetRegistration) {
//        String imgUploadDir = "src/main/resources/static/doc/";
//        File directory = new File(imgUploadDir);
//        if (!directory.exists()) {
//            directory.mkdir();
//        }
//        if (!document.isEmpty()) {
//            try{
//                String originalFilename = document.getOriginalFilename();
//                if(originalFilename == null || originalFilename.isEmpty()) {
//                    return;
//                }
//                String nameFile = originalFilename.substring(0, originalFilename.lastIndexOf("."));
//                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
//                String imgName = "Document" + nameFile + "." + fileExtension;
//                Path path = Paths.get(imgUploadDir + imgName);
//                // Kiểm tra file đã tồn tại hay chưa, nếu có thì thêm số phiên bản vào
//                int version = 1;
//                while (Files.exists(path)) {
//                    imgName = "Document_" + nameFile + "(" + version + ")" + fileExtension;
//                    path = Paths.get(imgUploadDir + imgName);
//                    version++;
//                }
//                // Lưu tệp vào thư mục
//                byte[] bytes = document.getBytes();
//                Files.write(path, bytes);
//                // Tạo đối tượng Image và liên kết với Land
//               AssetRegistration assetRegistration1 = new AssetRegistration();
//               assetRegistration.setPath(nameFile);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
