package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import global.Constants;
import interfaces.ImageLoader;
import interfaces.TextParser;
import listeners.AddToCartListener;
import listeners.SubTagListener;
import listeners.TagListener;
import models.DataHolder;
import models.Product;
import models.SubTag;
import models.Tag;

public class MainFrame {

    public static final String SUB_TAG_PREFIX = "       ";

    private JFrame jFrame;

    private DataHolder data;

    private TextParser textParser;
    private ImageLoader imageLoader;

    private ArrayList<String> tagsPressed;

    private boolean isSubTagPressed;
    private String tagPressed;
    private String subTagPressed;

    private MouseListener tagListener;
    private MouseListener subTagListener;
    private MouseListener addToCartListener;


    public MainFrame(TextParser textParser, ImageLoader imageLoader) {
        this.jFrame = new JFrame("Desktop Shop");
        this.textParser = textParser;
        this.tagsPressed = new ArrayList<>();
        this.imageLoader = imageLoader;
        this.tagListener = new TagListener(this);
        this.subTagListener = new SubTagListener(this);
        this.addToCartListener =  new AddToCartListener(this);
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

    public void createAndShowGUI() {
        Container contentPane = this.jFrame.getContentPane();
        contentPane.removeAll();
        contentPane.repaint();

        //Shop name in a JPanel
        contentPane.add(getShopName(), BorderLayout.PAGE_START);

        contentPane.add(constructShopTagsAndSubTags(tagsPressed), BorderLayout.LINE_START);
        if (isSubTagPressed) {
            contentPane.add(getShopProducts(), BorderLayout.CENTER);
        } else {
            //show welcome screen
        }

        this.jFrame.pack();
        this.jFrame.setVisible(true);
        this.jFrame.setSize(Constants.WIDTH, Constants.HEIGHT);
        this.jFrame.setResizable(false);
    }

    private JPanel getShopProducts() {
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

        // TODO Sorting of products must be here

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

        BufferedImage bufferedImage = imageLoader.loadImageInPreferredSize(
                Constants.PREFERRED_CAR_PICTURE_PATH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_WIDTH, Constants.PREFERRED_CAR_PICTURE_FOR_PRODUCT_HEIGHT);

        constructUIRelatedProductHolder(productsPanel, productPicAsJLabels, productNamesAsJLabels, productPricesAsJLabels, bufferedImage);

        return productsPanel;
    }

    private void constructUIRelatedProductHolder(JPanel productsPanel, ArrayList<JLabel> productPicAsJLabels, ArrayList<JLabel> productNamesAsJLabels, ArrayList<JLabel> productPricesAsJLabels, BufferedImage bufferedImage) {
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
            JLabel label = new JLabel(new ImageIcon(bufferedImage));
            label.addMouseListener(this.addToCartListener);
            innerHolder.add(label);
            innerHolder.add(new JSpinner());
            c.fill = GridBagConstraints.CENTER;
            c.gridwidth = 2;
            c.gridx = 1;
            c.gridy = 3;
            currentProductPanel.add(innerHolder, c);

            productsPanel.add(currentProductPanel);
        }
    }

    private void getJLabelFromString(ArrayList<JLabel> productNamesAsJLabels, String name) {
        JLabel label = new JLabel(name);
        label.setFont(new Font(label.getFont().getName(), Font.BOLD, 15));
        productNamesAsJLabels.add(label);
    }

    private JPanel getShopName() {
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
