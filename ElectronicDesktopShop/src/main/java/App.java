import data.ImageLoaderImpl;
import data.TextParserImpl;
import gui.MainFrameImpl;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrameImpl mf = new MainFrameImpl(new TextParserImpl(), new ImageLoaderImpl());
                mf.createAndShowGUI();
            }
        });
    }
}
