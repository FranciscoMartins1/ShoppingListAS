package com.example.compras;

public class Product {
    private int id;
    private String name;
    private int quantity;
    private boolean inCart;

    public Product() {

    }

    public Product(int id, String name, int quantity, boolean inCart) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.inCart = inCart;
    }

    public Product(String name, int quantity, boolean inCart) {
        this(-1, name, quantity, inCart);
    }

    public int getID() {
        return id;
    }

    public void setID(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }
}
