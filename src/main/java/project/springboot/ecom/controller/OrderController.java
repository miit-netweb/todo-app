package project.springboot.ecom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import project.springboot.ecom.entity.Orders;
import project.springboot.ecom.entity.OrderDto;
import project.springboot.ecom.entity.Product;
import project.springboot.ecom.services.OrderService;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/api")
public class OrderController {

    @Autowired
    private OrderService orderService;



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
                list.add(new Product(order1.getProducts().get(i).getId(),order1.getProducts().get(i).getPrice(),order1.getProducts().get(i).getQuantity()));
            }

            order.setProducts(list);
            System.out.println(order.getProducts().get(0).getPrice());
            orderService.saveOrderWithPdf(order);
        } catch (Exception e){

        }
        return ResponseEntity.ok(order);
    }
}
