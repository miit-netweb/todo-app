package project.springboot.ecom.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;
import project.springboot.ecom.entity.Product;

import project.springboot.ecom.repository.ProductRepo;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService
{

    @Autowired
    ProductRepo productRepo;


    public List<Product> getAll (){
        return productRepo.findAll();
    }

    public void saveProduct(Product todo){
        Product savedToDo = productRepo.save(todo);
    }
    public String uploadImage(MultipartFile image) {
        try {

            String originalFilename = image.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;

            String uploadDir = "uploads/";
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }

            File file = new File(uploadDir + uniqueFileName);
            image.transferTo(file);

            return uniqueFileName;

        } catch (IOException e) {
            e.printStackTrace();
            return "Error saving image";
        }
    }



    public boolean deleteEntry(String id){
        try{
                productRepo.deleteById(id);
                return true;
        } catch (Exception e){
            System.out.println("Error occured while deleting the journal : "+ e);
        }
        return false;
    }

    public Optional<Product> getByID(String id){
        return productRepo.findById(id);
    }


}
