package dev.abraham.dreamshops.service.image;

import dev.abraham.dreamshops.dto.ImageDTO;
import dev.abraham.dreamshops.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface IImageService {
    Image getImageById(Long id);
    void deleteImageById(Long id);
    List<ImageDTO> saveImage(List<MultipartFile> file, Long product_id);
    void updateImage(MultipartFile file, Long image_id);
}
