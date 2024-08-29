package dev.abraham.dreamshops.controller;

import dev.abraham.dreamshops.dto.ImageDTO;
import dev.abraham.dreamshops.exceptions.ImageNotFoundException;
import dev.abraham.dreamshops.model.Image;
import dev.abraham.dreamshops.response.APIResponse;
import dev.abraham.dreamshops.service.image.IImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.sql.SQLException;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/images")
public class ImageController {
    private final IImageService imageService;

    @PostMapping("/upload")
    public ResponseEntity<APIResponse> saveImages(@RequestParam List<MultipartFile> files, @RequestParam Long product_id){
        try{
            List<ImageDTO> dtos=imageService.saveImage(files, product_id);
            return ResponseEntity.ok(new APIResponse("Upload Success", dtos));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Upload failed", e.getMessage()));
        }
    }

    @GetMapping("/image/download/{image_id}")
    public ResponseEntity<Resource> downloadImage(@PathVariable Long image_id) throws SQLException {
        Image image=imageService.getImageById(image_id);
        ByteArrayResource resource=new ByteArrayResource(image.getImage().getBytes(1, (int)image.getImage().length()));
        return ResponseEntity.ok().contentType(MediaType.parseMediaType(image.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+image.getFileName()+"\"")
                .body(resource);
    }

    @PutMapping("/image/{image_id}/update")
    public ResponseEntity<APIResponse> updateImages(@PathVariable Long image_id, @RequestBody MultipartFile file){
        try {
            Image image=imageService.getImageById(image_id);
            if(image!=null){
                imageService.updateImage(file, image_id);
                return ResponseEntity.ok(new APIResponse("Update Success", null));
            }
        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Update failed", e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Update failed", HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @DeleteMapping("/image/{image_id}/delete")
    public ResponseEntity<APIResponse> deleteImages(@PathVariable Long image_id){
        try {
            Image image=imageService.getImageById(image_id);
            if(image!=null){
                imageService.deleteImageById(image_id);
                return ResponseEntity.ok(new APIResponse("Delete Success", null));
            }
        } catch (ImageNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new APIResponse("Update failed", e.getMessage()));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new APIResponse("Update failed", HttpStatus.INTERNAL_SERVER_ERROR));
    }
}
