package models;

import java.util.Objects;

public class Product {

    private String name;

    private double price;

    private String pathToPicture;

    public Product(String name, double price, String pathToPicture) {
        this.name = name;
        this.price = price;
        this.pathToPicture = pathToPicture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getPathToPicture() {
        return pathToPicture;
    }

    public void setPathToPicture(String pathToPicture) {
        this.pathToPicture = pathToPicture;
    }

    @Override
    public boolean equals(Object o) {

        Product product = (Product) o;
        return Double.compare(product.price, price) == 0 &&
                name.equals(product.name) &&
                pathToPicture.equals(product.pathToPicture);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, price, pathToPicture);
    }

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", price=" + price +
                ", pathToPicture='" + pathToPicture + '\'' +
                '}';
    }
}
