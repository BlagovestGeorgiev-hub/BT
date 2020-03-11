package gui;

import javax.swing.*;
import java.awt.*;

public class WelcomeScreenImpl {

    public JPanel constructWelcomeScreen(){
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
}
