package project.springboot.ecom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.springboot.ecom.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders,String> {
}
