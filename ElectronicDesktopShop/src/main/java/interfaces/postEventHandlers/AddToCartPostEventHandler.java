package interfaces.postEventHandlers;

import interfaces.CreateAndShowGUI;
import models.DataHolder;
import models.Product;

import java.util.Map;

public interface AddToCartPostEventHandler extends CreateAndShowGUI {

    Map<Product, Integer> getProductsInCarHolder();

    DataHolder getData();

    String getTagPressed();

    String getSubTagPressed();

    void setProductsInCarHolder(Map<Product, Integer> productsInCarHolder);
}
