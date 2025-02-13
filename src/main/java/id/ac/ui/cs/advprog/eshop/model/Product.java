package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;

    public Product() {
        this.productId = UUID.randomUUID().toString();
    }

    public void setProductQuantity(int quantity) {
        this.productQuantity = Math.max(0, quantity);
    }

    public void setProductName(String name) {
        this.productName = (name == null || name.trim().isEmpty()) ? "Untitled Product" : name;
    }
}