package project.springboot.ecom.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.springboot.ecom.entity.Product;
import project.springboot.ecom.entity.ProductDto;
import project.springboot.ecom.services.ProductService;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    private String saveImage(MultipartFile file) throws IOException {
        Path uploadPath = Paths.get("D:\\React\\todo-app\\target\\classes\\uploads");
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String fileName = UUID.randomUUID() + "_" + originalFilename;
        Path filePath = uploadPath.resolve(fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        System.out.println(fileName);
        return fileName;
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Product>> getAllJournalForUser(){
        return new ResponseEntity<>(productService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/{imageName}")
    public ResponseEntity<Resource> getImage(@PathVariable String imageName) {
        try {
            // Specify the target folder where images are stored (e.g., after building the app)
            String imagePath = "target/classes/uploads/" + imageName; // Adjust path if necessary
            File imageFile = new File(imagePath);

            if (imageFile.exists()) {
                Resource resource = new FileSystemResource(imageFile);

                // Set the content type (you can use a library like Apache Tika to detect it automatically)
                String contentType = "image/jpeg"; // Update based on file type (e.g., image/png, image/jpg, etc.)

                HttpHeaders headers = new HttpHeaders();
                headers.add(HttpHeaders.CONTENT_TYPE, contentType);

                return new ResponseEntity<>(resource, headers, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Image not found
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR); // Server error
        }
    }

    @PostMapping("/save")
    public ResponseEntity<Product> saveEntry(
            ProductDto product){
        Product product1 = new Product();
        try {
            String uniqueFileName = saveImage(product.getImage());

            product1.setId(product.getId());
            product1.setTitle(product.getTitle());
            product1.setContent(product.getContent());
            product1.setPrice(product.getPrice());
            product1.setStock(product.getStock());
            product1.setImage(uniqueFileName);
            product1.setQuantity(0);

            productService.saveProduct(product1);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
        return new ResponseEntity<>(product1,HttpStatus.OK);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){

            Optional<Product> product = productService.getByID(id);
            System.out.println(product);
            if(product.isPresent()){
                return new ResponseEntity<>(product.get(),HttpStatus.OK);
            }
        return new ResponseEntity<>("No product was found",HttpStatus.NOT_FOUND);
    }


    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateJournal(
            @PathVariable("id") String id,
            @RequestBody Product newJournal){

        Product old = productService.getByID(id).orElse(null);
        System.out.println("old : "+old);
        if(old != null){
            old.setTitle(newJournal.getTitle()!=null && !newJournal.getTitle().equals("") ? newJournal.getTitle() : old.getTitle() );
            old.setContent(newJournal.getContent() !=null && !newJournal.getContent().equals("") ? newJournal.getContent() : old.getContent());
            productService.saveProduct(old);
            return new ResponseEntity<>(old,HttpStatus.OK);
        }
        return new ResponseEntity<>("product not found",HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteJournal(@PathVariable("id") String id) {

        Optional<Product> old = productService.getByID(id);
        if(old.isEmpty()){
            return new ResponseEntity<>("todo with this id doesn't exist",HttpStatus.NOT_FOUND);
        }
        boolean removed =  productService.deleteEntry(id);
        if(removed){
            return new ResponseEntity<>("Deleted todo",HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
