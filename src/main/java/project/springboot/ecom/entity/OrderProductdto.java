package project.springboot.ecom.entity;

public class OrderProductdto {
    private String id;
    private int quantity;
    private float price;

    public OrderProductdto() {
    }

    public OrderProductdto(String id, int quantity) {
        this.id = id;
        this.quantity = quantity;
    }

    public OrderProductdto(String id, int quantity, float price) {
        this.id = id;
        this.quantity = quantity;
        this.price = price;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
