package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame implements ActionListener {

    private JFrame jFrame;

    public MainFrame() {
        this.jFrame = new JFrame("Desktop Shop");
    }

    public void createAndShowGUI() {
        Container contentPane = this.jFrame.getContentPane();
        contentPane.removeAll();

        contentPane.add(getShopName(), BorderLayout.PAGE_START);

        this.jFrame.pack();
        this.jFrame.setVisible(true);

                            //preferred width and height
        this.jFrame.setSize(680, 390);
        this.jFrame.setResizable(false);
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


    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
