package me.arco.pos.ui;

import me.arco.pos.exception.BadJsonException;
import me.arco.pos.exception.UserNotFoundException;
import me.arco.pos.util.PosClient;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by arnaudcoel on 11/11/15.
 */
public class MainWindow extends JFrame {
    private PosClient posClient;

    public MainWindow() {
        this.posClient = new PosClient();

        this.initComponents();
    }

    private void initComponents() {
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank");
        this.getContentPane().setCursor(blankCursor);

        final JPanel panel = new JPanel(new GridBagLayout());

        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setTitle("EhackB ShowMe");

        final JLabel result = new JLabel("Leg kaart op lezer");
        result.setFont(new Font("Helvetica Neue", Font.PLAIN, 64));

        final JTextField rfidField = new JTextField(12);
        rfidField.getDocument().addDocumentListener(new DocumentListener() {
            public void handleUpdate(DocumentEvent e) {

                if(e.getDocument().getLength() == 10) {
                    System.out.println("Received RFID");
                    SwingUtilities.invokeLater(new Runnable() {

                        public void run() {
                            String balance = null;
                            boolean success = true;

                            try {
                                balance = posClient.getBalance(rfidField.getText());
                            } catch (BadJsonException e1) {
                                panel.setBackground(new Color(242, 222, 222));
                                result.setForeground(new Color(169, 68, 66));
                                result.setText("ERROR: Webrequest went bad!");
                                success = false;
                            } catch (UserNotFoundException e1) {
                                panel.setBackground(new Color(242, 222, 222));
                                result.setForeground(new Color(169, 68, 66));
                                result.setText("ERROR: User not found!");
                                success = false;
                            }

                            if(success) {
                                panel.setBackground(new Color(223, 240, 216));
                                result.setForeground(new Color(60, 118, 61));
                                result.setText("Balance: " + balance + " bonnen");
                            }

                            new java.util.Timer().schedule(
                                    new java.util.TimerTask() {

                                        @Override
                                        public void run() {
                                            result.setText("Leg kaart op lezer");
                                            result.setForeground(Color.BLACK);
                                            panel.setBackground(new Color(238, 238, 238));
                                            rfidField.setText(null);
                                        }
                                    },
                                    5000
                            );
                        }

                    });
                }

            }

            public void insertUpdate(DocumentEvent e) {
                handleUpdate(e);
            }

            public void removeUpdate(DocumentEvent e) {
                handleUpdate(e);
            }

            public void changedUpdate(DocumentEvent e) {
                handleUpdate(e);
            }
        });

        panel.add(result);
        this.add(rfidField);

        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        this.add(panel);
        this.setExtendedState(Frame.MAXIMIZED_BOTH);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}
