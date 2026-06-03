package kr.ac.hansung.dto;

import lombok.Getter;
import lombok.Setter;

public class ProductDto {

    private String name;
    private int price;
    private String description;
    private int stock;

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getPrice() { return price; }
    public void setPrice(int price) { this.price = price; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}
