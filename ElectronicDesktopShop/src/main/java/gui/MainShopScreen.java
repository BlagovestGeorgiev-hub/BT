package gui;

import global.Constants;
import models.DataHolder;
import models.Product;
import models.SubTag;
import models.Tag;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;

public class MainShopScreen {

    private MainFrameImpl mainFrame;

    public MainShopScreen(MainFrameImpl mainFrame) {
        this.mainFrame = mainFrame;
    }

    public JPanel constructMainShopWindow() {
        JPanel productsHolder = new JPanel();
        productsHolder.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel shopProductsPanel = constructShopProducts(this.mainFrame.isDefaultOrderByName(), this.mainFrame.isDefaultOrderByPrice());
        shopProductsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        productsHolder.add(shopProductsPanel, c);


        ArrayList<JLabel> orderByLabels = new ArrayList<>();
        Helper.getJLabelFromString(orderByLabels, Constants.ORDER_BY_NAME);
        orderByLabels.get(orderByLabels.size() - 1).addMouseListener(this.mainFrame.getOrderByNameListener());

        Helper.getJLabelFromString(orderByLabels, Constants.ORDER_BY_PRICE);
        orderByLabels.get(orderByLabels.size() - 1).addMouseListener(this.mainFrame.getOrderByPriceListener());

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        orderByLabels.get(0).setBorder(BorderFactory.createLineBorder(Color.black));
        productsHolder.add(orderByLabels.get(0), c);

        c.gridx = 1;
        c.gridy = 1;
        orderByLabels.get(1).setBorder(BorderFactory.createLineBorder(Color.black));
        productsHolder.add(orderByLabels.get(1), c);

        JPanel shoppingCartHolderPanel = constructShoppingCartHolder();
        c.gridx = 2;
        c.gridy = 1;
        shoppingCartHolderPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        productsHolder.add(shoppingCartHolderPanel, c);

        JPanel infoMessageHolder = new JPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.fill = GridBagConstraints.VERTICAL;
        JLabel shopItemsInfoMessageFirstPart = new JLabel("You have " + this.mainFrame.getProductsInCarHolder().size());
        shopItemsInfoMessageFirstPart.setFont(new Font(shopItemsInfoMessageFirstPart.getFont().getName(), Font.BOLD, 15));
        infoMessageHolder.add(shopItemsInfoMessageFirstPart, gc);
        JLabel shopItemsInfoMessageSecondPart = new JLabel(" products in the cart.");
        shopItemsInfoMessageSecondPart.setFont(new Font(shopItemsInfoMessageSecondPart.getFont().getName(), Font.BOLD, 15));
        infoMessageHolder.setBorder(BorderFactory.createLineBorder(Color.black));
        infoMessageHolder.add(shopItemsInfoMessageSecondPart, gc);

        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 3;
        productsHolder.add(infoMessageHolder, c);


        return productsHolder;
    }

    public JPanel constructShopProducts(boolean orderByName, boolean orderByPrice) {
        JPanel productsPanel = new JPanel();
        ArrayList<Product> products = new ArrayList<>();

        for (Tag tag : this.mainFrame.getData().getTags()) {
            if (tag.getTagName().equals(this.mainFrame.getTagPressed())) {
                for (SubTag subTag : tag.getSubTags()) {
                    if (subTag.getTagName().equals(this.mainFrame.getSubTagPressed())) {
                        products.addAll(subTag.getProducts());
                    }
                }
            }
        }

        if (orderByName) {
            products.sort(new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return o1.getName().compareToIgnoreCase(o2.getName());
                }
            });
        }
        if (orderByPrice) {
            products.sort(new Comparator<Product>() {
                @Override
                public int compare(Product o1, Product o2) {
                    return Double.compare(o1.getPrice(), o2.getPrice());
                }
            });
        }

        ArrayList<JLabel> productPicAsJLabels = new ArrayList<>();
        ArrayList<JLabel> productNamesAsJLabels = new ArrayList<>();
        ArrayList<JLabel> productPricesAsJLabels = new ArrayList<>();
        for (Product pr : products) {

            //GetPictureAsJLabel
            BufferedImage bufferedImage = this.mainFrame.getImageLoader().loadImageInPreferredSize(
                    pr.getPathToPicture(), Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_WIDTH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_HEIGHT);
            JLabel picLabel = new JLabel(new ImageIcon(bufferedImage));
            productPicAsJLabels.add(picLabel);

            //GetNamesAsJLabels
            Helper.getJLabelFromString(productNamesAsJLabels, pr.getName());

            //GetProductPricesAsJLabels
            Helper.getJLabelFromString(productPricesAsJLabels, String.valueOf(pr.getPrice()) + Constants.PRODUCT_PRICE_SUFFIX);
        }

        BufferedImage shoppingCar20x20 = this.mainFrame.getImageLoader().loadImageInPreferredSize(
                Constants.PREFERRED_CAR_PICTURE_PATH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_WIDTH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_HEIGHT);

        constructUIRelatedProductHolder(productsPanel, productPicAsJLabels, productNamesAsJLabels, productPricesAsJLabels, shoppingCar20x20);

        return productsPanel;
    }

    public void constructUIRelatedProductHolder(JPanel productsPanel, ArrayList<JLabel> productPicAsJLabels, ArrayList<JLabel> productNamesAsJLabels, ArrayList<JLabel> productPricesAsJLabels, BufferedImage shoppingCar20x20) {
        for (int i = 0; i < productPicAsJLabels.size(); i++) {
            JPanel currentProductPanel = new JPanel();
            currentProductPanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();

            // Product Labels
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 1;
            c.gridy = 0;
            currentProductPanel.add(productNamesAsJLabels.get(i), c);

            // Product Pics
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.0;
            c.gridwidth = 4;
            c.gridx = 1;
            c.gridy = 1;
            currentProductPanel.add(productPicAsJLabels.get(i), c);

            //Product prices
            c.fill = GridBagConstraints.CENTER;
            c.weightx = 0.5;
            c.gridwidth = 1;
            c.gridx = 1;
            c.gridy = 2;
            currentProductPanel.add(productPricesAsJLabels.get(i), c);

            //Product car and spinner
            JPanel innerHolder = new JPanel();
            JLabel label = new JLabel(new ImageIcon(shoppingCar20x20));
            label.addMouseListener(this.mainFrame.getAddToCartListener());
            innerHolder.add(label);
            SpinnerModel model = new SpinnerNumberModel(0, 0, 1000, 1);
            innerHolder.add(new JSpinner(model));
            c.fill = GridBagConstraints.CENTER;
            c.gridwidth = 2;
            c.gridx = 1;
            c.gridy = 3;
            currentProductPanel.add(innerHolder, c);

            productsPanel.add(currentProductPanel);
        }
    }

    public JPanel constructShoppingCartHolder() {
        BufferedImage shoppingCar20x20 = this.mainFrame.getImageLoader().loadImageInPreferredSize(
                Constants.PREFERRED_CAR_PICTURE_PATH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_WIDTH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_HEIGHT);

        //Product shopping car and sorting buttons
        JPanel shoppingHolder = new JPanel();
        JLabel shoppingCarLabel = new JLabel(new ImageIcon(shoppingCar20x20));
        shoppingCarLabel.addMouseListener(this.mainFrame.getGlobalShoppingCarListener());
        shoppingHolder.add(shoppingCarLabel);
        return shoppingHolder;
    }








}
