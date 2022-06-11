package mnm.hdfontgen;

import java.awt.*;
import java.io.PrintWriter;
import java.io.StringWriter;

import javax.swing.*;

public class SwingMain implements Runnable {

    @Override
    public void run() {
        try {
            var window = GeneratorWindow.instance = new GeneratorWindow();
            window.frmHdFontGenerator.setVisible(true);
        } catch (HeadlessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();

            var sw = new StringWriter();
            var pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            JOptionPane.showMessageDialog(null, sw.toString(), "Error!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        main();
    }

    public static void main() {
        EventQueue.invokeLater(new SwingMain());
    }
}
