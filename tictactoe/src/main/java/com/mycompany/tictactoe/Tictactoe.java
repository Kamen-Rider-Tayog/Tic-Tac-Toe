package com.mycompany.tictactoe;

/**
 *
 * @author tayog
 */

import java.awt.Color;
import javax.swing.*;

public class Tictactoe {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Tic Tac Toe");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 500);
            frame.setResizable(false);
            frame.setLocationRelativeTo(null);
            frame.setUndecorated(true); 
            frame.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
            
            HomePanel homePanel = new HomePanel(frame);
            frame.add(homePanel);
            frame.setVisible(true);
        });
    }
}