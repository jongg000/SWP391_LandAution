package com.se1858.G5.LandAuction.Service.ServiceImpl;

import com.se1858.G5.LandAuction.Entity.Land;
import com.se1858.G5.LandAuction.Entity.LandImage;
import com.se1858.G5.LandAuction.Entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class UploadFile {

    public void UploadImagesForLand(List<MultipartFile> images, Land land){
        String imageUploadDir = "src/main/resources/static/Land_images/";
        //kiem tra xem thu muc da ton tai chua
        File directory = new File(imageUploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); //tao thu muc neu chua ton tai
        }
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                try {
                    // Lấy tên file gốc
                    String originalFileName = image.getOriginalFilename();
                    if (originalFileName == null) continue;
                    // Tách tên file và phần mở rộng
                    String fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
                    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                    // Tạo đường dẫn file
                    String imgName = "Asset_" + fileName + fileExtension;
                    Path path = Paths.get(imageUploadDir + imgName);
                    // Kiểm tra file đã tồn tại hay chưa, nếu có thì thêm số phiên bản vào
                    int version = 1;
                    while (Files.exists(path)) {
                        imgName = "Asset_" + fileName + "(" + version + ")" + fileExtension;
                        path = Paths.get(imageUploadDir + imgName);
                        version++;
                    }
                    // Lưu tệp vào thư mục
                    byte[] bytes = image.getBytes();
                    Files.write(path, bytes);
                    // Tạo đối tượng Image và liên kết với Asset
                    LandImage img = new LandImage();
                    img.setImageUrl(imgName);
                    land.addImg(img);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void upLoadDocumentAsset(MultipartFile document, Land land) {
        String imgUploadDir = "src/main/resources/static/doc/";
        File directory = new File(imgUploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (!document.isEmpty()) {
            try{
                String originalFilename = document.getOriginalFilename();
                if(originalFilename == null || originalFilename.isEmpty()) {
                    return;
                }
                String nameFile = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String docName = "Document" + nameFile + "." + fileExtension;
                Path path = Paths.get(imgUploadDir + docName);
                // Kiểm tra file đã tồn tại hay chưa, nếu có thì thêm số phiên bản vào
                int version = 1;
                while (Files.exists(path)) {
                    docName = "Document_" + nameFile + "(" + version + ")" + fileExtension;
                    path = Paths.get(imgUploadDir + docName);
                    version++;
                }
                // Lưu tệp vào thư mục
                byte[] bytes = document.getBytes();
                Files.write(path, bytes);
                // Tạo đối tượng Image và liên kết với Land
               land.setPath(docName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void upLoadDocumentAvata(MultipartFile document, User user) {
        String imageUploadDir = "src/main/resources/static/User_images/";
        File directory = new File(imageUploadDir);
        if (!directory.exists()) {
            directory.mkdir();
        }
        if (!document.isEmpty()) {
            try{
                String originalFilename = document.getOriginalFilename();
                if(originalFilename == null || originalFilename.isEmpty()) {
                    return;
                }
                String nameFile = originalFilename.substring(0, originalFilename.lastIndexOf("."));
                String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
                String docName = "User" + nameFile + "." + fileExtension;
                Path path = Paths.get(imageUploadDir + docName);
                // Kiểm tra file đã tồn tại hay chưa, nếu có thì thêm số phiên bản vào
                int version = 1;
                while (Files.exists(path)) {
                    docName = "User_" + nameFile + "(" + version + ")" + fileExtension;
                    path = Paths.get(imageUploadDir + docName);
                    version++;
                }
                // Lưu tệp vào thư mục
                byte[] bytes = document.getBytes();
                Files.write(path, bytes);
                // Tạo đối tượng Image và liên kết với Land
                user.setAvatar(docName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void UploadImagesNationalF(List<MultipartFile> images, User user){
        String imageUploadDir = "src/main/resources/static/User_images/";
        //kiem tra xem thu muc da ton tai chua
        File directory = new File(imageUploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); //tao thu muc neu chua ton tai
        }
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                try {
                    // Lấy tên file gốc
                    String originalFileName = image.getOriginalFilename();
                    if (originalFileName == null) continue;
                    // Tách tên file và phần mở rộng
                    String fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
                    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                    // Tạo đường dẫn file
                    String imgName = "User_" + fileName + fileExtension;
                    Path path = Paths.get(imageUploadDir + imgName);
                    // Kiểm tra file đã tồn tại hay chưa, nếu có thì thêm số phiên bản vào
                    int version = 1;
                    while (Files.exists(path)) {
                        imgName = "User_" + fileName + "(" + version + ")" + fileExtension;
                        path = Paths.get(imageUploadDir + imgName);
                        version++;
                    }
                    // Lưu tệp vào thư mục
                    byte[] bytes = image.getBytes();
                    Files.write(path, bytes);
                    // Tạo đối tượng Image và liên kết với Asset
                    user.setNationalFrontImage(imgName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void UploadImagesNationalB(List<MultipartFile> images, User user){
        String imageUploadDir = "src/main/resources/static/User_images/";
        //kiem tra xem thu muc da ton tai chua
        File directory = new File(imageUploadDir);
        if (!directory.exists()) {
            directory.mkdirs(); //tao thu muc neu chua ton tai
        }
        for (MultipartFile image : images) {
            if (!image.isEmpty()) {
                try {
                    // Lấy tên file gốc
                    String originalFileName = image.getOriginalFilename();
                    if (originalFileName == null) continue;
                    // Tách tên file và phần mở rộng
                    String fileName = originalFileName.substring(0, originalFileName.lastIndexOf('.'));
                    String fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
                    // Tạo đường dẫn file
                    String imgName = "User_" + fileName + fileExtension;
                    Path path = Paths.get(imageUploadDir + imgName);
                    // Kiểm tra file đã tồn tại hay chưa, nếu có thì thêm số phiên bản vào
                    int version = 1;
                    while (Files.exists(path)) {
                        imgName = "User_" + fileName + "(" + version + ")" + fileExtension;
                        path = Paths.get(imageUploadDir + imgName);
                        version++;
                    }
                    // Lưu tệp vào thư mục
                    byte[] bytes = image.getBytes();
                    Files.write(path, bytes);
                    // Tạo đối tượng Image và liên kết với Asset
                    user.setNationalBackImage(imgName);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
