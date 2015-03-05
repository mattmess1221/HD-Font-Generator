package mnm.hdfontgen;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;

/*
 * Mostly generated using Window Builder Pro
 */
public class GeneratorWindow implements ActionListener, ItemListener, Runnable {

    public static GeneratorWindow instance;
    private Thread thread;

    JFrame frmHdFontGenerator;
    private JComboBox<HDFont> choice;
    private JComboBox<TextureSize> comboBox;
    private JLabel lblDisplay;
    private JLabel lblStatus;
    private JCheckBox chckbxUnicode;
    private JButton btnCreate;

    public GeneratorWindow() {
        initialize();
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
                | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frmHdFontGenerator = new JFrame();
        frmHdFontGenerator.setTitle("HD Font Generator");
        frmHdFontGenerator.setBounds(100, 100, 230, 322);
        frmHdFontGenerator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmHdFontGenerator.getContentPane().setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Font settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        frmHdFontGenerator.getContentPane().add(panel, BorderLayout.CENTER);
        GridBagLayout gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[] { 25, 117, 81, 25 };
        gbl_panel.rowHeights = new int[] { 85, 0, 0, 0, 0, 0, 32, 0 };
        gbl_panel.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0 };
        gbl_panel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE };
        panel.setLayout(gbl_panel);

        lblDisplay = new JLabel("AaBbCcDdEe");
        GridBagConstraints gbc_lblDisplay = new GridBagConstraints();
        gbc_lblDisplay.fill = GridBagConstraints.VERTICAL;
        gbc_lblDisplay.gridwidth = 4;
        gbc_lblDisplay.insets = new Insets(0, 0, 5, 0);
        gbc_lblDisplay.gridx = 0;
        gbc_lblDisplay.gridy = 0;
        panel.add(lblDisplay, gbc_lblDisplay);

        JLabel lblFont = new JLabel("Font:");
        GridBagConstraints gbc_lblFont = new GridBagConstraints();
        gbc_lblFont.anchor = GridBagConstraints.WEST;
        gbc_lblFont.insets = new Insets(0, 0, 5, 5);
        gbc_lblFont.gridx = 1;
        gbc_lblFont.gridy = 1;
        panel.add(lblFont, gbc_lblFont);

        choice = new JComboBox<HDFont>(new Vector<>(getInstalledFonts()));
        choice.addItemListener(this);
        GridBagConstraints gbc_choice = new GridBagConstraints();
        gbc_choice.fill = GridBagConstraints.BOTH;
        gbc_choice.gridwidth = 2;
        gbc_choice.insets = new Insets(0, 0, 5, 5);
        gbc_choice.gridx = 1;
        gbc_choice.gridy = 2;
        panel.add(choice, gbc_choice);

        JLabel lblTextureSize = new JLabel("Texture Size:");
        GridBagConstraints gbc_lblTextureSize = new GridBagConstraints();
        gbc_lblTextureSize.insets = new Insets(0, 0, 5, 5);
        gbc_lblTextureSize.gridx = 1;
        gbc_lblTextureSize.gridy = 3;
        panel.add(lblTextureSize, gbc_lblTextureSize);

        comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(TextureSize.values()));
        GridBagConstraints gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 2;
        gbc_comboBox.gridy = 3;
        panel.add(comboBox, gbc_comboBox);

        btnCreate = new JButton("Create");
        btnCreate.addActionListener(this);

        chckbxUnicode = new JCheckBox("Unicode (Experimental)");
        GridBagConstraints gbc_chckbxUnicode = new GridBagConstraints();
        gbc_chckbxUnicode.gridwidth = 2;
        gbc_chckbxUnicode.insets = new Insets(0, 0, 5, 5);
        gbc_chckbxUnicode.gridx = 1;
        gbc_chckbxUnicode.gridy = 4;
        panel.add(chckbxUnicode, gbc_chckbxUnicode);
        GridBagConstraints gbc_btnCreate = new GridBagConstraints();
        gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
        gbc_btnCreate.gridwidth = 2;
        gbc_btnCreate.gridx = 1;
        gbc_btnCreate.gridy = 6;
        panel.add(btnCreate, gbc_btnCreate);

        lblStatus = new JLabel("Started");
        lblStatus.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frmHdFontGenerator.getContentPane().add(lblStatus, BorderLayout.SOUTH);
    }

    private Collection<HDFont> getInstalledFonts() {
        List<HDFont> fonts = new ArrayList<>();
        for (Font f : GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts()) {
            fonts.add(new HDFont(f));
        }
        return fonts;
    }

    private HDFont getChoice() {
        return (HDFont) choice.getSelectedItem();
    }

    private TextureSize getTextureSize() {
        return (TextureSize) comboBox.getSelectedItem();
    }

    public void itemStateChanged(ItemEvent e) {
        Font f = getChoice().getFont();
        lblDisplay.setFont(new Font(f.getName(), 0, 24));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this);
            thread.start();
        }
    }

    @Override
    public void run() {
        lblStatus.setText("Working...");
        btnCreate.setEnabled(false);

        HDFont font = new HDFont(getChoice().getFont(), getTextureSize(), chckbxUnicode.isSelected());
        try {
            FontGenerator.generate(font);
            lblStatus.setText("Created " + font.getFriendlyName());
        } catch (IOException e) {
            lblStatus.setText("An error has occured.");
            JOptionPane.showMessageDialog(this.frmHdFontGenerator, e.getMessage());
        } finally {
            btnCreate.setEnabled(true);
        }
    }
}
