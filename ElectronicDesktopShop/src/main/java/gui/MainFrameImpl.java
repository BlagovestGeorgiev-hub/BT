package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import global.Constants;
import interfaces.*;
import interfaces.postEventHandlers.*;
import listeners.*;
import models.DataHolder;
import models.Product;

public class MainFrameImpl implements AddToCartPostEventHandler, OrderByNamePostEventHandler,
        OrderByPricePostEventHandler, SubTagPostEventHandler, TagPostEventHandler, GlobalShoppingCarPostEventHandler {
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

    private WelcomeScreenImpl welcomeScreenImpl;
    private ShopNameScreenImpl shopNameScreen;
    private ShopTagsAndSubTagsScreenImpl shopTagsAndSubTagsScreen;
    private MainShopScreen mainShopScreen;

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

        this.productsInCarHolder = new LinkedHashMap<>();
        this.defaultOrderByName = false;
        this.defaultOrderByPrice = true;

        this.welcomeScreenImpl = new WelcomeScreenImpl();
        this.shopNameScreen = new ShopNameScreenImpl();
        this.shopTagsAndSubTagsScreen = new ShopTagsAndSubTagsScreenImpl(this);
        this.mainShopScreen = new MainShopScreen(this);
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

    public void setProductsInCarHolder(Map<Product, Integer> productsInCarHolder) { this.productsInCarHolder = productsInCarHolder; }

    public void setDefaultOrderByName(boolean defaultOrderByName) {
        this.defaultOrderByName = defaultOrderByName;
    }

    public void setDefaultOrderByPrice(boolean defaultOrderByPrice) {
        this.defaultOrderByPrice = defaultOrderByPrice;
    }

    public void setData(DataHolder data) {
        this.data = data;
    }

    @Override
    public DataHolder getData() {
        return data;
    }

    public TextParser getTextParser() {
        return textParser;
    }

    public ImageLoader getImageLoader() {
        return imageLoader;
    }

    @Override
    public ArrayList<String> getTagsPressed() {
        return tagsPressed;
    }

    @Override
    public String getTagPressed() {
        return tagPressed;
    }

    @Override
    public String getSubTagPressed() {
        return subTagPressed;
    }

    @Override
    public Map<Product, Integer> getProductsInCarHolder() {
        return productsInCarHolder;
    }

    public MouseListener getTagListener() {
        return tagListener;
    }

    public MouseListener getSubTagListener() {
        return subTagListener;
    }

    public MouseListener getAddToCartListener() {
        return addToCartListener;
    }

    public MouseListener getGlobalShoppingCarListener() {
        return globalShoppingCarListener;
    }

    public MouseListener getOrderByNameListener() {
        return orderByNameListener;
    }

    public MouseListener getOrderByPriceListener() {
        return orderByPriceListener;
    }

    public boolean isDefaultOrderByName() {
        return defaultOrderByName;
    }

    public boolean isDefaultOrderByPrice() {
        return defaultOrderByPrice;
    }

    public JFrame getFrame() {
        return jFrame;
    }

    public void createAndShowGUI() {
        Container contentPane = this.jFrame.getContentPane();
        contentPane.removeAll();
        contentPane.repaint();

        contentPane.setLayout(new BorderLayout(1, 1));
        contentPane.add(this.shopNameScreen.constructShopName(), BorderLayout.PAGE_START);

        contentPane.add(shopTagsAndSubTagsScreen.constructShopTagsAndSubTags(tagsPressed), BorderLayout.LINE_START);
        if (isSubTagPressed) {
            contentPane.add(mainShopScreen.constructMainShopWindow(), BorderLayout.CENTER);
        } else {
            contentPane.add(this.welcomeScreenImpl.constructWelcomeScreen(), BorderLayout.CENTER);
        }

        this.jFrame.pack();
        this.jFrame.setVisible(true);
        this.jFrame.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.jFrame.setResizable(false);
    }
}
