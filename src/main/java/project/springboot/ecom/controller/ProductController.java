package project.springboot.ecom.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import project.springboot.ecom.entity.Product;
import project.springboot.ecom.services.ProductService;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("/getall")
    public ResponseEntity<List<Product>> getAllJournalForUser(){
        return new ResponseEntity<>(productService.getAll(),HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<Product> saveEntry(
                                            @RequestParam("id") String id,
                                            @RequestParam("title") String title,
                                            @RequestParam("content") String content,
                                            @RequestParam("price") float price,
                                            @RequestParam("stock") int stock,
                                            @RequestParam("image")MultipartFile image){
        Product product = new Product();

        try {
            String originalFilename = image.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;

            String uploadDir = new File("src/main/resources/uploads/").getAbsolutePath();
            File directory = new File(uploadDir);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            File file = new File(uniqueFileName);
            image.transferTo(file);

            product.setId(id);
            product.setTitle(title);
            product.setContent(content);
            product.setPrice(price);
            product.setStock(stock);
            product.setImage(uniqueFileName);

            productService.saveProduct(product);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
        return new ResponseEntity<>(product,HttpStatus.OK);
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
