package main;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;

public class JumpScare extends JFrame {

    JLabel label;
    Color[] flashColors = {Color.BLACK, Color.RED, Color.WHITE};
    int flashIndex = 0;

    public JumpScare() {
        setUndecorated(true);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new BorderLayout());

        // Jumpscare image
        label = new JLabel(new ImageIcon(getClass().getResource("/jumpscare/jump.png")));
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);
        add(label, BorderLayout.CENTER);

        setVisible(true);

        // Flash the screen every 100ms
        Timer flashTimer = new Timer();
        flashTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                getContentPane().setBackground(flashColors[flashIndex]);
                flashIndex = (flashIndex + 1) % flashColors.length;
            }
        }, 0, 100); // every 100 milliseconds

        // Auto close and stop flashing after 2 seconds
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                flashTimer.cancel();
                dispose();
            }
        }, 2000);
    }
}
