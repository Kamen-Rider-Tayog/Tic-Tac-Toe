package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DifficultySelectionPanel extends JPanel {
    private JFrame parentFrame;
    
    public DifficultySelectionPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 240, 240));
        
        JLabel title = new JLabel("Select Difficulty", SwingConstants.CENTER);
        title.setFont(new Font("SegoeUI", Font.BOLD, 24));
        title.setForeground(new Color(70, 70, 70));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton easyBtn = createModernButton("Easy", new Color(76, 175, 80), Color.WHITE);
        easyBtn.setFont(new Font("SegoeUI", Font.BOLD, 18));
        easyBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        easyBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            startGame(1);
        });
        
        JButton moderateBtn = createModernButton("Moderate", new Color(255, 152, 0), Color.WHITE);
        moderateBtn.setFont(new Font("SegoeUI", Font.BOLD, 18));
        moderateBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        moderateBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            startGame(2);
        });
        
        JButton extremeBtn = createModernButton("Extreme", new Color(244, 67, 54), Color.WHITE);
        extremeBtn.setFont(new Font("SegoeUI", Font.BOLD, 18));
        extremeBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        extremeBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            startGame(3);
        });
        
        JButton backBtn = createModernButton("Back", Color.WHITE, new Color(70, 70, 70));
        backBtn.setFont(new Font("SegoeUI", Font.BOLD, 16));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new ModeSelectionPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        
        add(Box.createVerticalGlue());
        add(title);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(easyBtn);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(moderateBtn);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(extremeBtn);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(backBtn);
        add(Box.createVerticalGlue());
    }
    
    private JButton createModernButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(10, 25, 10, 25)
        ));
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor == Color.WHITE ? new Color(245, 245, 245) : 
                    new Color(Math.max(0, bgColor.getRed() - 20), 
                    Math.max(0, bgColor.getGreen() - 20), 
                    Math.max(0, bgColor.getBlue() - 20)));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private void startGame(int difficulty) {
        parentFrame.getContentPane().removeAll();
        parentFrame.add(new GamePanel(parentFrame, true, difficulty));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}