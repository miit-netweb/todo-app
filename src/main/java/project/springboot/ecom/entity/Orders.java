package project.springboot.ecom.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Orders {

    @Id
    private String id;
    @OneToMany(mappedBy = "id")
    private List<Product> products;
    private String name;
    private String number;
    private String paymentMethod;
    private String lane;
    private String city;
    private String state;
    private int pincode;

    public Orders() {
    }

    public Orders(String id, List<Product> products, String name, String number, String paymentMethod, String lane, String city, String state, int pincode) {
        this.id = id;
        this.products = products;
        this.name = name;
        this.number = number;
        this.paymentMethod = paymentMethod;
        this.lane = lane;
        this.city = city;
        this.state = state;
        this.pincode = pincode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) {
        this.lane = lane;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getPincode() {
        return pincode;
    }

    public void setPincode(int pincode) {
        this.pincode = pincode;
    }
}
