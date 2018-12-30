package ru.lruzanova.nic.gui;

import ru.lruzanova.nic.Controller;
import ru.lruzanova.nic.gui.elements.LightJButton;
import ru.lruzanova.nic.gui.elements.color.GrayShadesColorResolver;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class LoadPanel extends JPanel {
    private JLabel loadLabel;
    private JRadioButton url;
    private JTextField urlValue;
    private JRadioButton fromDisk;
    private JTextField fileValue;
    private JButton selectDir;
    private JButton fileFineOrNot;
    private JButton urlFineOrNot;
    private Controller controller;
    private Icon valid;
    private Icon invalid;
    private Icon question;

    public LoadPanel(Controller controller) {
        this.controller = controller;
        initComponents();
        place();

    }

    private void initComponents() {
        try {
            BufferedImage bufferedImage = ImageIO.read(ClassLoader.getSystemResource("valid.png"));
            valid = new ImageIcon(bufferedImage);
        } catch (Exception e) {
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(ClassLoader.getSystemResource("invalid.png"));
            invalid = new ImageIcon(bufferedImage);
        } catch (Exception e) {
        }
        try {
            BufferedImage bufferedImage = ImageIO.read(ClassLoader.getSystemResource("question.png"));
            question = new ImageIcon(bufferedImage);
        } catch (Exception e) {
        }
        loadLabel = new JLabel("Загрузить документ");
        url = new JRadioButton("Url");
        urlValue = new JTextField();
        fromDisk = new JRadioButton("С диска");
        fileValue = new JTextField();
        fileFineOrNot = getCheckResButton();
        urlFineOrNot = getCheckResButton();
        try {
            BufferedImage bufferedImage = ImageIO.read(ClassLoader.getSystemResource("open.png"));
            selectDir = new LightJButton(bufferedImage, new GrayShadesColorResolver());
        } catch (Exception e) {
            selectDir = new LightJButton("...", new GrayShadesColorResolver());
        }
        selectDir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new java.io.File("."));
                fileChooser.setDialogTitle("Выберите файл");
                if (fileChooser.showOpenDialog(LoadPanel.this) == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    fileValue.setText(selectedFile.toString());
                    setIconCheckResButton(fileFineOrNot, controller.checkFileFromDisk(selectedFile));
                }
            }
        });

        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(url);
        buttonGroup.add(fromDisk);
        fromDisk.setSelected(true);
        url.setEnabled(false);
        urlValue.setEnabled(false);
    }

    private void place() {
        GroupLayout layout = new GroupLayout(this);
        setLayout(layout);

        layout.setAutoCreateGaps(true);

        layout.setHorizontalGroup(layout.createParallelGroup()
                .addComponent(loadLabel)
                .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup()
                                .addComponent(url)
                                .addComponent(fromDisk)
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(urlValue)
                                .addGroup(layout.createSequentialGroup()
                                        .addComponent(fileValue)
                                        .addComponent(selectDir))
                        )
                        .addGroup(layout.createParallelGroup()
                                .addComponent(urlFineOrNot)
                                .addComponent(fileFineOrNot))
                )

        );
        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(loadLabel)
                .addGroup(layout.createBaselineGroup(true, true)
                        .addComponent(url)
                        .addComponent(urlValue)
                        .addComponent(urlFineOrNot))
                .addGroup(layout.createBaselineGroup(true, true)
                        .addComponent(fromDisk)
                        .addComponent(fileValue)
                        .addComponent(selectDir)
                        .addComponent(fileFineOrNot))

        );
    }

    private JButton getCheckResButton() {
        JButton button = new JButton();
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setBorderPainted(false);

        button.setOpaque(true);
        button.setContentAreaFilled(false);
        button.setFocusPainted(false);
        button.setIcon(question);
        return button;
    }

    private void setIconCheckResButton(JButton button, boolean flag) {
        button.setIcon(flag ? valid : invalid);
    }
}