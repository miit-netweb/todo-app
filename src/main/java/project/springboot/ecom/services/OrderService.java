package project.springboot.ecom.services;

import com.itextpdf.text.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.springboot.ecom.entity.Orders;
import project.springboot.ecom.repository.OrderRepository;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private InvoiceService invoiceService;

    public Orders saveOrderWithPdf(Orders order) throws DocumentException, IOException {
        Orders save = orderRepository.save(order);
        CompletableFuture.runAsync(()->{
            try {
                invoiceService.generateInvoicePDF(order);
            } catch (DocumentException | IOException e) {
                throw new RuntimeException(e);
            }
        });
        return save;
    }
}
