package interfaces.postEventHandlers;

import models.Product;

import javax.swing.*;
import java.util.Map;

public interface GlobalShoppingCarPostEventHandler {

    JFrame getFrame();

    Map<Product, Integer> getProductsInCarHolder();
}
