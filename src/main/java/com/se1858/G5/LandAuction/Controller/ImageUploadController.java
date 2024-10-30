package com.se1858.G5.LandAuction.Controller;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ImageUploadController {
    @RequestMapping(value = "getimage/{image}", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable("image") String photo) {
        // Check if the photo name is null or empty
        if (photo != null && !photo.isEmpty()) {
            try {
                // Construct the path to the file
                Path filename = Paths.get("uploads", photo);
                byte[] buffer = Files.readAllBytes(filename); // Read the image file into a byte array

                // Create a ByteArrayResource to return the file content as a response
                ByteArrayResource byteArrayResource = new ByteArrayResource(buffer);

                // Return the response with the image data, content type, and length
                return ResponseEntity.ok()
                        .contentLength(buffer.length)
                        .contentType(MediaType.parseMediaType("image/png")) // You can adjust the media type based on your file
                        .body(byteArrayResource);
            } catch (Exception e) {
                // Handle exceptions (e.g., file not found, IO errors)
                e.printStackTrace();
                return ResponseEntity.status(500).build(); // Internal Server Error if something goes wrong
            }
        }

        // Return bad request if the photo name is invalid
        return ResponseEntity.badRequest().build();
    }

}
