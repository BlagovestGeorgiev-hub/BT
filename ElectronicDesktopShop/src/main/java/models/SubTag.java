package models;

import java.util.ArrayList;

public class SubTag {
    private String tagName;

    private ArrayList<Product> products;

    public SubTag(String tagName, ArrayList<Product> products) {
        this.tagName = tagName;
        this.products = products;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public void addProduct(Product product){
        this.products.add(product);
    }

    @Override
    public String toString() {
        return "SubTag{" +
                "tagName='" + tagName + '\'' +
                System.lineSeparator() +
                ", products=" + products +
                System.lineSeparator() +
                '}';
    }
}
