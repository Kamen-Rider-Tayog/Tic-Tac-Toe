package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePanel extends JPanel {
    private JFrame parentFrame;
    
    public HomePanel(JFrame parentFrame) {
        this.parentFrame = parentFrame;
        setupUI();
        SoundManager.playBackgroundMusic();
    }
    
    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));
        
        Font segoeUI = new Font("SegoeUI", Font.PLAIN, 14);
        UIManager.put("Button.font", segoeUI);
        UIManager.put("Label.font", segoeUI);
        
        JLabel title = new JLabel("Tic Tac Toe", SwingConstants.CENTER);
        title.setFont(new Font("SegoeUI", Font.BOLD, 36));
        title.setForeground(new Color(70, 70, 70));
        
        JButton playBtn = createModernButton("PLAY", new Color(0, 122, 255), Color.WHITE);
        playBtn.setFont(new Font("SegoeUI", Font.BOLD, 24));
        playBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new ModeSelectionPanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
        
        JButton exitBtn = createModernButton("EXIT", Color.WHITE, new Color(70, 70, 70));
        exitBtn.setFont(new Font("SegoeUI", Font.BOLD, 18));
        exitBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            System.exit(0);
        });
        
        JButton infoBtn = new JButton("i");
        styleIconButton(infoBtn);
        infoBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            showInstructions();
        });
        
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 240, 240));
        topBar.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        leftPanel.setOpaque(false);
        
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rightPanel.setOpaque(false);
        rightPanel.add(infoBtn);
        
        topBar.add(leftPanel, BorderLayout.WEST);
        topBar.add(rightPanel, BorderLayout.EAST);
        
        JPanel centerContent = new JPanel();
        centerContent.setLayout(new BoxLayout(centerContent, BoxLayout.Y_AXIS));
        centerContent.setBackground(new Color(240, 240, 240));
        centerContent.add(Box.createVerticalGlue());
        centerContent.add(title);
        centerContent.add(Box.createRigidArea(new Dimension(0, 30)));
        centerContent.add(playBtn);
        centerContent.add(Box.createRigidArea(new Dimension(0, 15)));
        centerContent.add(exitBtn);
        centerContent.add(Box.createVerticalGlue());
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        playBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        exitBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        add(topBar, BorderLayout.NORTH);
        add(centerContent, BorderLayout.CENTER);
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
    
    private void styleIconButton(JButton button) {
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        button.setBackground(new Color(240, 240, 240));
        button.setForeground(new Color(70, 70, 70));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setFont(new Font("SegoeUI", Font.PLAIN, 16));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(220, 220, 220));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(240, 240, 240));
            }
        });
    }
    
    private void showInstructions() {
        JDialog instructionsDialog = new JDialog(parentFrame, "", true);
        instructionsDialog.setUndecorated(true);
        instructionsDialog.setSize(350, 350);
        instructionsDialog.setLocationRelativeTo(parentFrame);
        instructionsDialog.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        instructionsDialog.getContentPane().setBackground(new Color(250, 250, 250));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(250, 250, 250));

        JButton closeBtn = new JButton("✕");
        closeBtn.setFocusPainted(false);
        closeBtn.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        closeBtn.setBackground(new Color(250, 250, 250));
        closeBtn.setForeground(new Color(70, 70, 70));
        closeBtn.setFont(new Font("SegoeUI", Font.PLAIN, 16));
        closeBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            instructionsDialog.dispose();
        });

        JPanel closePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        closePanel.setOpaque(false);
        closePanel.add(closeBtn);

        JLabel instructionsLabel = new JLabel(
            "<html><div style='text-align:center;padding:10px;'>" +
            "<h2>How to Play</h2>" +
            "<p style='text-align:left;'>1. The game is played on a 3x3 grid.</p>" +
            "<p style='text-align:left;'>2. Players take turns marking a space (X or O).</p>" +
            "<p style='text-align:left;'>3. The first player to get 3 marks in a row wins.</p>" +
            "<p style='text-align:left;'>4. If all 9 squares are filled without a winner, it's a draw.</p>" +
            "<br>" +
            "<p>Made with <font color='red'>❤</font> by Tayog</p>" +
            "</div></html>"
        );
        instructionsLabel.setFont(new Font("SegoeUI", Font.PLAIN, 14));
        instructionsLabel.setForeground(new Color(70, 70, 70));

        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setOpaque(false);
        centerPanel.add(instructionsLabel, BorderLayout.CENTER);

        panel.add(closePanel, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);

        instructionsDialog.add(panel);
        instructionsDialog.setVisible(true);
    }
}