package project.springboot.ecom.services;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import project.springboot.ecom.entity.Orders;
import project.springboot.ecom.repository.OrderRepository;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InvoiceService invoiceService;

    public List<Orders> getAllOrders(){
        return orderRepository.findAll();
    }


    public Orders saveOrderWithPdf(Orders order) throws DocumentException, IOException {
        try{
            Orders save = orderRepository.save(order);
        }
        catch (Exception e){
            System.out.println(e.getMessage());
        }

        System.out.println("save");
//        CompletableFuture.runAsync(()->{
            try {
                invoiceService.generateInvoicePDF(order);
            } catch (DocumentException | IOException e) {
                throw new RuntimeException(e);
            }
//        });
        return null;
    }
}
