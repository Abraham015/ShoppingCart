package dev.abraham.dreamshops.service.image;

import dev.abraham.dreamshops.dto.ImageDTO;
import dev.abraham.dreamshops.exceptions.ImageNotFoundException;
import dev.abraham.dreamshops.model.Image;
import dev.abraham.dreamshops.model.Product;
import dev.abraham.dreamshops.repository.ImageRepository;
import dev.abraham.dreamshops.service.product.IProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.sql.rowset.serial.SerialBlob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ImageService implements IImageService {
    private final ImageRepository imageRepository;
    private final IProductService productService;

    public Image getImageById(Long id) {
        return imageRepository.findById(id)
                .orElseThrow(()->new ImageNotFoundException("Image not found with id: "+ id));
    }

    public void deleteImageById(Long id) {
        imageRepository.findById(id)
                .ifPresentOrElse(imageRepository::delete,
                        ()->{throw new ImageNotFoundException("Image not found with id: "+ id);});
    }

    public List<ImageDTO> saveImage(List<MultipartFile> file, Long product_id) {
        Product product = productService.getProductById(product_id);
        String route="/api/v1/images/image/download/";
        List<ImageDTO> savedImageDTO=new ArrayList<>();
        for (MultipartFile multipartFile : file) {
            try{
                Image image = new Image();
                image.setFileName(multipartFile.getOriginalFilename());
                image.setFileType(multipartFile.getContentType());
                image.setImage(new SerialBlob(multipartFile.getBytes()));
                image.setProduct(product);
                String downloadURL=route+image.getId();
                image.setDownloadUrl(downloadURL);
                Image saved=imageRepository.save(image);
                saved.setDownloadUrl(route+saved.getId());
                imageRepository.save(saved);
                ImageDTO dto=new ImageDTO();
                dto.setImageId(saved.getId());
                dto.setImageName(saved.getFileName());
                dto.setDownloadUrl(saved.getDownloadUrl());
                savedImageDTO.add(dto);
            } catch (IOException| SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return savedImageDTO;
    }

    public void updateImage(MultipartFile file, Long image_id) {
        Image image=getImageById(image_id);
        try{
            image.setFileName(file.getOriginalFilename());
            image.setFileType(file.getContentType());
            image.setImage(new SerialBlob(file.getBytes()));
            imageRepository.save(image);
        }catch (IOException | SQLException e){
            throw new RuntimeException(e);
        }
    }
}
