package mnm.hdfontgen;

import javax.swing.*;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.io.IOException;

/*
 * Mostly generated using Window Builder Pro
 */
public class GeneratorWindow {

    public static GeneratorWindow instance;
    private Thread thread;

    JFrame frmHdFontGenerator;
    private JComboBox<String> choiceFont;
    private JComboBox<TextureSize> comboBox;
    private JLabel lblDisplay;
    private JLabel lblStatus;
    private JCheckBox checkboxUnicode;
    private JButton btnCreate;

    public GeneratorWindow() {
        initialize();
    }

    private void initialize() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ReflectiveOperationException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        frmHdFontGenerator = new JFrame();
        frmHdFontGenerator.setTitle("HD Font Generator");
        frmHdFontGenerator.setBounds(100, 100, 230, 322);
        frmHdFontGenerator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmHdFontGenerator.getContentPane().setLayout(new BorderLayout(0, 0));

        var panel = new JPanel();
        panel.setBorder(new TitledBorder(null, "Font settings", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        frmHdFontGenerator.getContentPane().add(panel, BorderLayout.CENTER);
        var gbl_panel = new GridBagLayout();
        gbl_panel.columnWidths = new int[]{25, 117, 81, 25};
        gbl_panel.rowHeights = new int[]{85, 0, 0, 0, 0, 0, 32, 0};
        gbl_panel.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0};
        gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, 1.0, Double.MIN_VALUE};
        panel.setLayout(gbl_panel);

        lblDisplay = new JLabel("AaBbCcDdEe");
        var gbc_lblDisplay = new GridBagConstraints();
        gbc_lblDisplay.fill = GridBagConstraints.VERTICAL;
        gbc_lblDisplay.gridwidth = 4;
        gbc_lblDisplay.insets = new Insets(0, 0, 5, 0);
        gbc_lblDisplay.gridx = 0;
        gbc_lblDisplay.gridy = 0;
        panel.add(lblDisplay, gbc_lblDisplay);

        var lblFont = new JLabel("Font:");
        var gbc_lblFont = new GridBagConstraints();
        gbc_lblFont.anchor = GridBagConstraints.WEST;
        gbc_lblFont.insets = new Insets(0, 0, 5, 5);
        gbc_lblFont.gridx = 1;
        gbc_lblFont.gridy = 1;
        panel.add(lblFont, gbc_lblFont);

        choiceFont = new JComboBox<>(getInstalledFonts());
        choiceFont.addItemListener(e -> {
            var f = getChoiceFont();
            lblDisplay.setFont(f.deriveFont(Font.PLAIN, 24));
        });
        var gbc_choice = new GridBagConstraints();
        gbc_choice.fill = GridBagConstraints.BOTH;
        gbc_choice.gridwidth = 2;
        gbc_choice.insets = new Insets(0, 0, 5, 5);
        gbc_choice.gridx = 1;
        gbc_choice.gridy = 2;
        panel.add(choiceFont, gbc_choice);

        var lblTextureSize = new JLabel("Texture Size:");
        var gbc_lblTextureSize = new GridBagConstraints();
        gbc_lblTextureSize.insets = new Insets(0, 0, 5, 5);
        gbc_lblTextureSize.gridx = 1;
        gbc_lblTextureSize.gridy = 3;
        panel.add(lblTextureSize, gbc_lblTextureSize);

        comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(TextureSize.values()));
        var gbc_comboBox = new GridBagConstraints();
        gbc_comboBox.insets = new Insets(0, 0, 5, 5);
        gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
        gbc_comboBox.gridx = 2;
        gbc_comboBox.gridy = 3;
        panel.add(comboBox, gbc_comboBox);

        btnCreate = new JButton("Create");
        btnCreate.addActionListener(e -> this.startThread());

        checkboxUnicode = new JCheckBox("Unicode (Experimental)");
        var gbc_checkboxUnicode = new GridBagConstraints();
        gbc_checkboxUnicode.gridwidth = 2;
        gbc_checkboxUnicode.insets = new Insets(0, 0, 5, 5);
        gbc_checkboxUnicode.gridx = 1;
        gbc_checkboxUnicode.gridy = 4;
        panel.add(checkboxUnicode, gbc_checkboxUnicode);
        var gbc_btnCreate = new GridBagConstraints();
        gbc_btnCreate.insets = new Insets(0, 0, 0, 5);
        gbc_btnCreate.gridwidth = 2;
        gbc_btnCreate.gridx = 1;
        gbc_btnCreate.gridy = 6;
        panel.add(btnCreate, gbc_btnCreate);

        lblStatus = new JLabel("Started");
        lblStatus.setBorder(new BevelBorder(BevelBorder.LOWERED));
        frmHdFontGenerator.getContentPane().add(lblStatus, BorderLayout.SOUTH);

        Log.addLogger(lblStatus::setText);
    }

    private String[] getInstalledFonts() {
        return GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    }

    private Font getChoiceFont() {
        return Font.decode((String) choiceFont.getSelectedItem());
    }

    private TextureSize getTextureSize() {
        return (TextureSize) comboBox.getSelectedItem();
    }

    private void startThread() {
        if (thread == null || !thread.isAlive()) {
            thread = new Thread(this::run);
            thread.start();
        }
    }

    private void run() {
        lblStatus.setText("Working...");
        btnCreate.setEnabled(false);

        var font = new HDFont(getChoiceFont(), getTextureSize());
        var unicode = checkboxUnicode.isSelected();
        try {
            FontGenerator.generate(font, unicode);
        } catch (IOException e) {
            e.printStackTrace();
            lblStatus.setText("An error has occurred.!");
            // TODO create log file so users can check the log
            JOptionPane.showMessageDialog(this.frmHdFontGenerator, "An error has occurred.\nCheck the log for details.", "Error!", JOptionPane.ERROR_MESSAGE);
        } finally {
            btnCreate.setEnabled(true);
        }
    }
}
