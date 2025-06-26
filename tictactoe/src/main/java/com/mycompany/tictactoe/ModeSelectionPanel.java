package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ModeSelectionPanel extends JPanel {
    private JFrame parentFrame;
    
    public ModeSelectionPanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setupUI();
    }
    
    private void setupUI() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(new Color(240, 240, 240));
        
        JLabel title = new JLabel("Select Game Mode", SwingConstants.CENTER);
        title.setFont(new Font("SegoeUI", Font.BOLD, 24));
        title.setForeground(new Color(70, 70, 70));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        JButton pvpBtn = createModernButton("Player vs Player", new Color(0, 122, 255), Color.WHITE);
        pvpBtn.setFont(new Font("SegoeUI", Font.BOLD, 18));
        pvpBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        pvpBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new GamePanel(parentFrame, false));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        
        JButton pvcBtn = createModernButton("Player vs Computer", new Color(0, 122, 255), Color.WHITE);
        pvcBtn.setFont(new Font("SegoeUI", Font.BOLD, 18));
        pvcBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        pvcBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new DifficultySelectionPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        
        JButton backBtn = createModernButton("Back", Color.WHITE, new Color(70, 70, 70));
        backBtn.setFont(new Font("SegoeUI", Font.BOLD, 16));
        backBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        backBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new HomePanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        
        add(Box.createVerticalGlue());
        add(title);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(pvpBtn);
        add(Box.createRigidArea(new Dimension(0, 15)));
        add(pvcBtn);
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
}