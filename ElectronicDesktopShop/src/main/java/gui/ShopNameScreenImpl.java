package gui;

import javax.swing.*;
import java.awt.*;

public class ShopNameScreenImpl {

    public JPanel constructShopName() {
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

}
