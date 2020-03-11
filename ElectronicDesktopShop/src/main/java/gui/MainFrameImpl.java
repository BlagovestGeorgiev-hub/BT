package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import global.Constants;
import interfaces.*;
import interfaces.postEventHandlers.*;
import listeners.*;
import models.DataHolder;
import models.Product;
import models.SubTag;
import models.Tag;

public class MainFrameImpl implements AddToCartPostEventHandler, OrderByNamePostEventHandler, OrderByPricePostEventHandler, SubTagPostEventHandler, TagPostEventHandler {

    public static final String SUB_TAG_PREFIX = "       ";

    private JFrame jFrame;

    private DataHolder data;

    private TextParser textParser;
    private ImageLoader imageLoader;

    private ArrayList<String> tagsPressed;

    private boolean isSubTagPressed;
    private String tagPressed;
    private String subTagPressed;
    private Map<Product, Integer> productsInCarHolder;

    private MouseListener tagListener;
    private MouseListener subTagListener;
    private MouseListener addToCartListener;
    private MouseListener globalShoppingCarListener;
    private MouseListener orderByNameListener;
    private MouseListener orderByPriceListener;

    private boolean defaultOrderByName;
    private boolean defaultOrderByPrice;

    public MainFrameImpl(TextParser textParser, ImageLoader imageLoader) {
        this.jFrame = new JFrame("Desktop Shop");
        this.textParser = textParser;
        this.tagsPressed = new ArrayList<>();
        this.imageLoader = imageLoader;
        this.tagListener = new TagListener(this);
        this.subTagListener = new SubTagListener(this);
        this.addToCartListener = new AddToCartListener(this);
        this.globalShoppingCarListener = new GlobalShoppingCarListener(this);
        this.orderByNameListener = new OrderByNameListener(this);
        this.orderByPriceListener = new OrderByPriceListener(this);

        this.productsInCarHolder = new HashMap<>();
        this.defaultOrderByName = false;
        this.defaultOrderByPrice = true;
    }

    public DataHolder getData() {
        return data;
    }

    public ArrayList<String> getTagsPressed() {
        return tagsPressed;
    }

    public void setSubTagPressed(boolean subTagPressed) {
        isSubTagPressed = subTagPressed;
    }

    public void setTagPressed(String tagPressed) {
        this.tagPressed = tagPressed;
    }

    public void setSubTagPressed(String subTagPressed) {
        this.subTagPressed = subTagPressed;
    }

    public Map<Product, Integer> getProductsInCarHolder() {
        return productsInCarHolder;
    }

    public void setProductsInCarHolder(Map<Product, Integer> productsInCarHolder) { this.productsInCarHolder = productsInCarHolder; }

    public String getTagPressed() {
        return tagPressed;
    }

    public String getSubTagPressed() {
        return subTagPressed;
    }

    public void setDefaultOrderByName(boolean defaultOrderByName) {
        this.defaultOrderByName = defaultOrderByName;
    }

    public void setDefaultOrderByPrice(boolean defaultOrderByPrice) {
        this.defaultOrderByPrice = defaultOrderByPrice;
    }

    public void createAndShowGUI() {
        Container contentPane = this.jFrame.getContentPane();
        contentPane.removeAll();
        contentPane.repaint();

        contentPane.setLayout(new BorderLayout(1, 1));
        contentPane.add(constructShopName(), BorderLayout.PAGE_START);

        contentPane.add(constructShopTagsAndSubTags(tagsPressed), BorderLayout.LINE_START);
        if (isSubTagPressed) {
            contentPane.add(constructMainShopWindow(), BorderLayout.CENTER);
        } else {
            contentPane.add(constructWelcomeScreen(), BorderLayout.CENTER);
        }

        this.jFrame.pack();
        this.jFrame.setVisible(true);
        this.jFrame.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.jFrame.setResizable(false);
    }

    private JPanel constructWelcomeScreen(){
        JPanel welcomeScreenHolder = new JPanel(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel welcome = new JLabel("WELCOME !!!");
        welcome.setFont(new Font(welcome.getFont().getName(), Font.BOLD, 25));
        c.gridx = 0;
        c.gridy = 0;
        welcomeScreenHolder.add(welcome, c);

        JLabel informationMessage = new JLabel("Please, select a category.");
        informationMessage.setFont(new Font(informationMessage.getFont().getName(), Font.BOLD, 15));
        c.gridx = 0;
        c.gridy = 1;
        welcomeScreenHolder.add(informationMessage, c);

        welcomeScreenHolder.setBorder(BorderFactory.createLineBorder(Color.black));
        return welcomeScreenHolder;
    }

    private JPanel constructMainShopWindow() {
        JPanel productsHolder = new JPanel();
        productsHolder.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JPanel shopProductsPanel = constructShopProducts(this.defaultOrderByName, this.defaultOrderByPrice);
        shopProductsPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 3;
        productsHolder.add(shopProductsPanel, c);


        ArrayList<JLabel> orderByLabels = new ArrayList<>();
        getJLabelFromString(orderByLabels, Constants.ORDER_BY_NAME);
        orderByLabels.get(orderByLabels.size() - 1).addMouseListener(this.orderByNameListener);

        getJLabelFromString(orderByLabels, Constants.ORDER_BY_PRICE);
        orderByLabels.get(orderByLabels.size() - 1).addMouseListener(this.orderByPriceListener);

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
        JLabel shopItemsInfoMessageFirstPart = new JLabel("You have " + this.productsInCarHolder.size());
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

    private JPanel constructShopProducts(boolean orderByName, boolean orderByPrice) {
        JPanel productsPanel = new JPanel();
        ArrayList<Product> products = new ArrayList<>();

        for (Tag tag : data.getTags()) {
            if (tag.getTagName().equals(this.tagPressed)) {
                for (SubTag subTag : tag.getSubTags()) {
                    if (subTag.getTagName().equals(this.subTagPressed)) {
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
            BufferedImage bufferedImage = imageLoader.loadImageInPreferredSize(
                    pr.getPathToPicture(), Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_WIDTH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_HEIGHT);
            JLabel picLabel = new JLabel(new ImageIcon(bufferedImage));
            productPicAsJLabels.add(picLabel);

            //GetNamesAsJLabels
            getJLabelFromString(productNamesAsJLabels, pr.getName());

            //GetProductPricesAsJLabels
            getJLabelFromString(productPricesAsJLabels, String.valueOf(pr.getPrice()) + Constants.PRODUCT_PRICE_SUFFIX);
        }

        BufferedImage shoppingCar20x20 = imageLoader.loadImageInPreferredSize(
                Constants.PREFERRED_CAR_PICTURE_PATH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_WIDTH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_HEIGHT);

        constructUIRelatedProductHolder(productsPanel, productPicAsJLabels, productNamesAsJLabels, productPricesAsJLabels, shoppingCar20x20);

        return productsPanel;
    }

    private void constructUIRelatedProductHolder(JPanel productsPanel, ArrayList<JLabel> productPicAsJLabels, ArrayList<JLabel> productNamesAsJLabels, ArrayList<JLabel> productPricesAsJLabels, BufferedImage shoppingCar20x20) {
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
            label.addMouseListener(this.addToCartListener);
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

    private JPanel constructShoppingCartHolder() {
        BufferedImage shoppingCar20x20 = imageLoader.loadImageInPreferredSize(
                Constants.PREFERRED_CAR_PICTURE_PATH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_WIDTH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_HEIGHT);

        //Product shopping car and sorting buttons
        JPanel shoppingHolder = new JPanel();
        JLabel shoppingCarLabel = new JLabel(new ImageIcon(shoppingCar20x20));
        shoppingCarLabel.addMouseListener(this.globalShoppingCarListener);
        shoppingHolder.add(shoppingCarLabel);
        return shoppingHolder;
    }

    private void getJLabelFromString(ArrayList<JLabel> productNamesAsJLabels, String name) {
        JLabel label = new JLabel(name);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 15));
        productNamesAsJLabels.add(label);
    }

    private JPanel constructShopName() {
        JPanel shopName = new JPanel();
        JLabel text = new JLabel("Blago's super expensive high quality  shop.");
        text.setFont(new Font(text.getFont().getName(), Font.BOLD, 15));
        text.setBorder(BorderFactory.createLineBorder(Color.black));

        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1f;
        c.gridx = 0;
        c.gridy = 0;

        shopName.setLayout(new GridBagLayout());
        shopName.add(text, c);

        return shopName;
    }


    private JPanel constructShopTagsAndSubTags(ArrayList<String> tagsPressed) {
        this.textParser.readData();
        this.data = this.textParser.getData();
        ArrayList<JLabel> tagsAsLabels = new ArrayList<>();
        createJLabelsFromTags(data, tagsAsLabels);

        JPanel container = new JPanel();
        BoxLayout boxLayout = new BoxLayout(container, BoxLayout.Y_AXIS);
        container.setLayout(boxLayout);

        for (JLabel label : tagsAsLabels) {
            label.addMouseListener(tagListener);
            container.add(label);
            container.setAlignmentX(Component.LEFT_ALIGNMENT);
            createJLabelsFromSubTags(tagsPressed, data, container, label);
        }
        container.setBorder(BorderFactory.createLineBorder(Color.black));
        return container;
    }

    private void createJLabelsFromTags(DataHolder data, ArrayList<JLabel> tagsAsLabels) {
        for (Tag tag : data.getTags()) {
            getJLabelFromString(tagsAsLabels, tag.getTagName());
        }
    }

    private void createJLabelsFromSubTags(ArrayList<String> tagsPressed, DataHolder data, JPanel container, JLabel label) {
        if (tagsPressed.contains(label.getText())) {
            data.getTags().forEach(t -> {
                if (t.getTagName().equals(label.getText())) {
                    ArrayList<SubTag> subTags = t.getSubTags();
                    for (SubTag subTag : subTags) {
                        JLabel subTagLabel = new JLabel(SUB_TAG_PREFIX + label.getText() + Constants.TAG_SUBTAG_SEPARATOR + subTag.getTagName());
                        subTagLabel.setFont(new Font(subTagLabel.getFont().getName(), Font.BOLD, 15));
                        subTagLabel.addMouseListener(this.subTagListener);
                        container.add(subTagLabel);
                        container.setAlignmentX(Component.LEFT_ALIGNMENT);
                    }
                }
            });
        }
    }
}
