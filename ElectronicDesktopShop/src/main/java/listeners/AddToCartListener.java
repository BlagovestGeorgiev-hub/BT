package listeners;

import interfaces.postEventHandlers.AddToCartPostEventHandler;
import models.DataHolder;
import models.Product;
import models.SubTag;
import models.Tag;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Map;

public class AddToCartListener implements MouseListener {

    private AddToCartPostEventHandler addToCartPostEventHandler;

    public AddToCartListener(AddToCartPostEventHandler frame) {
        this.addToCartPostEventHandler = frame;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        JLabel source = (JLabel) e.getSource();
        Container spinnerAndCartHolder = source.getParent();
        Component[] components = spinnerAndCartHolder.getComponents();
        Container productUIContainer = spinnerAndCartHolder.getParent();
        Component[] productUIContainerComponents = productUIContainer.getComponents();
        int currentValue = 0;
        for (Component comp : components) {
            if (comp instanceof JSpinner) {
                currentValue = (Integer) ((JSpinner) comp).getValue();
            }
        }

        Map<Product, Integer> productsInCarHolder = addToCartPostEventHandler.getProductsInCarHolder();
        DataHolder data = addToCartPostEventHandler.getData();

        JLabel productAsJLabel = (JLabel) (productUIContainerComponents[0]);
        boolean breakSequence = false;
        for (Tag tag : data.getTags()) {
            if (tag.getTagName().equals(addToCartPostEventHandler.getTagPressed())) {
                for (SubTag subTag : tag.getSubTags()) {
                    if (subTag.getTagName().equals(addToCartPostEventHandler.getSubTagPressed())) {
                        for (Product product : subTag.getProducts()) {
                            if (product.getName().equals(productAsJLabel.getText())) {
                                if (productsInCarHolder.containsKey(product)) {
                                    productsInCarHolder.replace(product, productsInCarHolder.get(product) + currentValue);
                                } else {
                                    productsInCarHolder.put(product, currentValue);
                                }
                                breakSequence = true;
                            }
                            if (breakSequence) {
                                break;
                            }
                        }
                    }
                    if (breakSequence) {
                        break;
                    }
                }
            }
            if (breakSequence) {
                break;
            }
        }
        addToCartPostEventHandler.setProductsInCarHolder(productsInCarHolder);
        addToCartPostEventHandler.createAndShowGUI();
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
