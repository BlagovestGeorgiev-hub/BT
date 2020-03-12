package listeners;

import global.Constants;
import interfaces.postEventHandlers.ChangeCountPostEventHandler;
import interfaces.postEventHandlers.ChangeProductCountPostEventHandler;
import interfaces.postEventHandlers.GlobalShoppingCarPostEventHandler;
import interfaces.postEventHandlers.PayButtonPostEventHandler;
import models.Product;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.NumberFormat;
import java.util.*;
import java.util.List;


public class GlobalShoppingCarListener implements MouseListener, ChangeProductCountPostEventHandler,
        ChangeCountPostEventHandler, PayButtonPostEventHandler {

    private GlobalShoppingCarPostEventHandler frame;
    private JDialog dialog;
    private ArrayList<JLabel> counterComponents;
    private Product selectionForChange;
    private JFormattedTextField field;


    public GlobalShoppingCarListener(GlobalShoppingCarPostEventHandler frame) {
        this.frame = frame;
    }

    @Override
    public void constructInvoice(){
        Map<Product, Integer> productsInCarHolder = frame.getProductsInCarHolder();

        File invoice = new File("Invoice.csv");
        try {
            invoice.createNewFile();
            PrintWriter printWriter = new PrintWriter(invoice);
            StringBuilder sb = new StringBuilder();

            sb.append("Name")
                    .append(Constants.CSV_SEPARATOR)
                    .append("Count")
                    .append(Constants.CSV_SEPARATOR)
                    .append("Price")
                    .append(Constants.CSV_SEPARATOR)
                    .append("Overall")
                    .append(System.lineSeparator());

            double overall = 0;
            for (Map.Entry<Product, Integer> entry : productsInCarHolder.entrySet()){
                overall += entry.getValue() * entry.getKey().getPrice();
                sb.append(entry.getKey().getName())
                        .append(Constants.CSV_SEPARATOR)
                        .append(entry.getValue())
                        .append(Constants.CSV_SEPARATOR)
                        .append(entry.getKey().getPrice())
                        .append(Constants.PRODUCT_PRICE_SUFFIX)
                        .append(Constants.CSV_SEPARATOR)
                        .append(entry.getValue() * entry.getKey().getPrice())
                        .append(System.lineSeparator());
            }

            sb.append("Overall : ").append(overall).append(Constants.PRODUCT_PRICE_SUFFIX);

            printWriter.write(sb.toString());
            printWriter.close();

            dialog.dispose();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changeCount() {
        String text = field.getText();
        text = text.replaceAll("[^\\d.]", "");
        frame.getProductsInCarHolder().replace(selectionForChange, Integer.parseInt(text));
        constructAndShowDialog();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        constructAndShowDialog();
    }

    private void constructAndShowDialog() {
        if (this.dialog != null) {
            this.dialog.dispose();
        }
        this.dialog = new JDialog(frame.getFrame(), Constants.INVOICES, true);

        JPanel panel = constructDialog();

        dialog.add(panel);
        dialog.pack();
        dialog.setVisible(true);
    }

    @Override
    public void reconstructDialog(JLabel label) {
        if (counterComponents != null && counterComponents.size() > 0) {
            this.dialog.dispose();
            this.dialog = new JDialog(frame.getFrame(), Constants.INVOICES, true);

            JPanel dialogPanel = new JPanel();
            dialogPanel.setLayout(new GridBagLayout());
            GridBagConstraints c = new GridBagConstraints();
            c.insets = new Insets(1, 2, 1, 2);
            Map<Product, Integer> productsInCarHolder = frame.getProductsInCarHolder();

            constrictLabels(dialogPanel, c);
            JLabel count;

            Map<Product, Integer> result = reorderProducts(productsInCarHolder);


            int counter = 1;
            double overallSum = 0.0;
            for (Product product : result.keySet()) {

                constructJLabelFromString(dialogPanel, c, counter, product.getName(), 0);
                JLabel com = counterComponents.get(counter - 1);
                if (label == com) {
                    selectionForChange = product;
                    counterComponents.set(counter - 1, null);
                    Integer value = Integer.parseInt(label.getText());

                    NumberFormat format = NumberFormat.getInstance();
                    NumberFormatter formatter = new NumberFormatter(format);

                    formatter.setValueClass(Integer.class);
                    formatter.setMinimum(0);
                    formatter.setMaximum(Integer.MAX_VALUE);
                    formatter.setAllowsInvalid(false);
                    formatter.setCommitsOnValidEdit(true);

                    field = new JFormattedTextField(formatter);
                    field.setValue(value);

                    field.addKeyListener(new ChangeCountListener(this));
                    field.setBorder(BorderFactory.createLineBorder(Color.black));
                    field.setFont(new Font(field.getFont().getName(), Font.BOLD, 15));
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 1;
                    c.gridy = counter;
                    dialogPanel.add(field, c);

                } else {
                    count = new JLabel(result.get(product).toString());
                    count.addMouseListener(new ChangeProductCountListener(this));
                    count.setBorder(BorderFactory.createLineBorder(Color.black));
                    count.setFont(new Font(count.getFont().getName(), Font.BOLD, 15));
                    c.fill = GridBagConstraints.HORIZONTAL;
                    c.gridx = 1;
                    c.gridy = counter;
                    dialogPanel.add(count, c);
                    counterComponents.set(counter - 1, count);
                }

                constructJLabelFromString(dialogPanel, c, counter, String.valueOf(product.getPrice()), 2);
                overallSum = constructOverall(dialogPanel, c, result, counter, overallSum, product);
                counter++;
            }

            JLabel overallSumLabel = new JLabel(Constants.OVERALL + String.valueOf(overallSum) + Constants.PRODUCT_PRICE_SUFFIX);
            overallSumLabel.setBorder(BorderFactory.createLineBorder(Color.black));
            overallSumLabel.setFont(new Font(overallSumLabel.getFont().getName(), Font.BOLD, 15));
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 3;
            c.gridy = counter;
            dialogPanel.add(overallSumLabel, c);

            counter ++;
            constructPayButtons(dialogPanel, c, counter);

            dialog.add(dialogPanel);
            dialog.pack();
            dialog.setVisible(true);
        }
    }

    private void constructPayButtons(JPanel dialogPanel, GridBagConstraints c, int counter) {
        JButton payWithCard = new JButton("Pay via Card");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridwidth = 2;
        c.gridy = counter;
        payWithCard.addActionListener(new PayButtonActionListener(this));
        dialogPanel.add(payWithCard, c);

        JButton payWithPayPal = new JButton("Pay via PayPal");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridwidth = 2;
        c.gridy = counter;
        payWithPayPal.addActionListener(new PayButtonActionListener(this));
        dialogPanel.add(payWithPayPal, c);
    }

    private double constructOverall(JPanel dialogPanel, GridBagConstraints c, Map<Product, Integer> result, int counter, double overallSum, Product product) {
        JLabel overall;
        overallSum += product.getPrice() * result.get(product);
        overall = new JLabel(String.valueOf(product.getPrice() * result.get(product)));
        overall.setBorder(BorderFactory.createLineBorder(Color.black));
        overall.setFont(new Font(overall.getFont().getName(), Font.BOLD, 15));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = counter;
        dialogPanel.add(overall, c);
        return overallSum;
    }

    private void constructJLabelFromString(JPanel dialogPanel, GridBagConstraints c, int counter, String s, int i) {
        JLabel price;
        price = new JLabel(s);
        price.setBorder(BorderFactory.createLineBorder(Color.black));
        price.setFont(new Font(price.getFont().getName(), Font.BOLD, 15));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = i;
        c.gridy = counter;
        dialogPanel.add(price, c);
    }

    private JPanel constructDialog() {
        counterComponents = new ArrayList<>();

        JPanel dialogPanel = new JPanel();
        dialogPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(1, 2, 1, 2);
        Map<Product, Integer> productsInCarHolder = frame.getProductsInCarHolder();

        constrictLabels(dialogPanel, c);

        Map<Product, Integer> result = reorderProducts(productsInCarHolder);

        int counter = 1;
        double overallSum = 0.0;
        for (Product product : result.keySet()) {

            constructJLabelFromString(dialogPanel, c, counter, product.getName(), 0);
            constructCount(dialogPanel, c, result, counter, product);
            constructJLabelFromString(dialogPanel, c, counter, String.valueOf(product.getPrice()), 2);
            overallSum = constructOverall(dialogPanel, c, result, counter, overallSum, product);

            counter++;
        }

        JLabel overallSumLabel = new JLabel(Constants.OVERALL + String.valueOf(overallSum) + Constants.PRODUCT_PRICE_SUFFIX);
        overallSumLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        overallSumLabel.setFont(new Font(overallSumLabel.getFont().getName(), Font.BOLD, 15));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = counter;
        dialogPanel.add(overallSumLabel, c);

        counter ++;
        constructPayButtons(dialogPanel, c, counter);

        return dialogPanel;


    }

    private void constructCount(JPanel dialogPanel, GridBagConstraints c, Map<Product, Integer> result, int counter, Product product) {
        JLabel count;
        count = new JLabel(result.get(product).toString());
        count.addMouseListener(new ChangeProductCountListener(this));
        count.setBorder(BorderFactory.createLineBorder(Color.black));
        count.setFont(new Font(count.getFont().getName(), Font.BOLD, 15));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = counter;
        dialogPanel.add(count, c);
        counterComponents.add(count);
    }

    private Map<Product, Integer> reorderProducts(Map<Product, Integer> productsInCarHolder) {
        List<Map.Entry<Product, Integer>> list = new ArrayList<>(productsInCarHolder.entrySet());
        list.sort(new Comparator<Map.Entry<Product, Integer>>() {
            @Override
            public int compare(Map.Entry<Product, Integer> o1, Map.Entry<Product, Integer> o2) {
                return o1.getKey().getName().compareToIgnoreCase(o2.getKey().getName());
            }
        });

        Map<Product, Integer> result = new LinkedHashMap<>();
        for (Map.Entry<Product, Integer> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }

    private void constrictLabels(JPanel dialogPanel, GridBagConstraints c) {
        JLabel name = new JLabel(Constants.PRODUCT_ORDER_ASC);
        name.setBorder(BorderFactory.createLineBorder(Color.black));
        name.setFont(new Font(name.getFont().getName(), Font.BOLD, 15));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        dialogPanel.add(name, c);

        JLabel count = new JLabel(Constants.COUNT);
        count.setBorder(BorderFactory.createLineBorder(Color.black));
        count.setFont(new Font(count.getFont().getName(), Font.BOLD, 15));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 1;
        c.gridy = 0;
        dialogPanel.add(count, c);

        JLabel price = new JLabel(Constants.PRICE);
        price.setBorder(BorderFactory.createLineBorder(Color.black));
        price.setFont(new Font(price.getFont().getName(), Font.BOLD, 15));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 2;
        c.gridy = 0;
        dialogPanel.add(price, c);

        JLabel overall = new JLabel(Constants.OVERALL);
        overall.setBorder(BorderFactory.createLineBorder(Color.black));
        overall.setFont(new Font(overall.getFont().getName(), Font.BOLD, 15));
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 3;
        c.gridy = 0;
        dialogPanel.add(overall, c);
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
