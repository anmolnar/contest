package org.dolphy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainGui {
    private final static Random random = new Random();
    private static final ExecutorService backgroundExec = Executors.newCachedThreadPool();

    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {
        //Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setSize(300, 300);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //Add the ubiquitous "Hello World" label.
        JButton quitButton = new JButton("Quit");

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quitButton.setEnabled(false);
                quitButton.setText("Busy...");
                Future bgtask = backgroundExec.submit(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);

                        } catch (InterruptedException ex) {
                            Thread.currentThread().interrupt();
                        } finally {
                            GuiExecutor.instance().execute(new Runnable() {
                                @Override
                                public void run() {
                                    quitButton.setEnabled(true);
                                    quitButton.setText("OK");
                                }
                            });
                        }
                    }
                });
            }
        });

        JPanel contentPane = new JPanel(new BorderLayout());
        contentPane.add(quitButton, BorderLayout.CENTER);
        frame.setContentPane(contentPane);

        //Display the window.
        //frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
