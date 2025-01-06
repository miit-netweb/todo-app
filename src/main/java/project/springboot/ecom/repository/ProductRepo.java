package project.springboot.ecom.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.springboot.ecom.entity.Product;


@Repository
public interface ProductRepo extends JpaRepository<Product, String> {
}
