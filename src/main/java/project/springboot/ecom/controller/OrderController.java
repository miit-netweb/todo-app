package project.springboot.ecom.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import project.springboot.ecom.entity.Orders;
import project.springboot.ecom.entity.OrderDto;
import project.springboot.ecom.entity.Product;
import project.springboot.ecom.services.OrderService;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping("/get/orders")
    public ResponseEntity<List<Orders>> getAllOrders(){
        return ResponseEntity.ok(orderService.getAllOrders());
    }

    @PostMapping("/create/order")
    public ResponseEntity<Orders> createOrder(@RequestBody OrderDto order1){
        System.out.println(order1.getId());
        Orders order = new Orders();
        try{
            order.setId(order1.getId());
            order.setCity(order1.getCity());
            order.setLane(order1.getLane());
            order.setNumber(order1.getNumber());
            order.setPaymentMethod(order1.getPaymentMethod());
            order.setState(order1.getState());
            order.setPincode(order1.getPincode());
            order.setName(order1.getName());

            List<Product> list = new ArrayList<>();
            for(int i=0;i<order1.getProducts().size();i++){
                list.add(new Product(
                        order1.getProducts().get(i).getId(),
                        order1.getProducts().get(i).getTitle(),
                        order1.getProducts().get(i).getPrice(),
                        order1.getProducts().get(i).getQuantity())
                );
            }
            order.setProducts(list);
            System.out.println(order.getProducts().get(0).getPrice());
            orderService.saveOrderWithPdf(order);
        } catch (Exception e){

        }
        return ResponseEntity.ok(order);
    }


    @GetMapping("/invoice/{invoiceId}")
    public ResponseEntity<Resource> downloadInvoice(@PathVariable String invoiceId) {
        final Path invoiceDirectory = Paths.get("D:\\REACT-SpringBoot POC\\todo-app\\src\\main\\resources\\invoices\\");
        try {
            // Construct the file path based on the invoice ID
            Path filePath = invoiceDirectory.resolve(invoiceId + ".pdf");

            // Create a resource from the file path
            Resource resource = new UrlResource(filePath.toUri());

            // Check if the file exists
            if (resource.exists() && resource.isReadable()) {
                // Set the Content-Disposition header to indicate a file download
                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_PDF) // Or other types based on file type
                        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                        .body(resource);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}
