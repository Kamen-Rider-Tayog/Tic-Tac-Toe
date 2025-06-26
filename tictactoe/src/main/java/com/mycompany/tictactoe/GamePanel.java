package com.mycompany.tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GamePanel extends JPanel {
    private JFrame parentFrame;
    private boolean vsComputer;
    private int difficulty;
    private GameLogic gameLogic;
    private JButton[][] buttons = new JButton[3][3];
    private JLabel turnLabel;
    private boolean isPlayerTurn = true;

    public GamePanel(JFrame parentFrame, boolean vsComputer) {
        this(parentFrame, vsComputer, 0);
    }

    public GamePanel(JFrame parentFrame, boolean vsComputer, int difficulty) {
        this.parentFrame = parentFrame;
        this.vsComputer = vsComputer;
        this.difficulty = difficulty;
        boolean playerFirst = Math.random() < 0.5;
        this.isPlayerTurn = playerFirst;
        this.gameLogic = new GameLogic(vsComputer, difficulty, playerFirst);
        SoundManager.stopBackgroundMusic();
        setupUI();
        if (vsComputer && !playerFirst) {
            setButtonsEnabled(false);
            gameLogic.scheduleComputerMove();
        }
    }

    private void setupUI() {
        setLayout(new BorderLayout());
        setBackground(new Color(240, 240, 240));

        Font segoeUI = new Font("SegoeUI", Font.PLAIN, 14);
        UIManager.put("Button.font", segoeUI);
        UIManager.put("Label.font", segoeUI);

        turnLabel = new JLabel("Current Turn: " + gameLogic.getCurrentPlayer(), SwingConstants.CENTER);
        turnLabel.setFont(new Font("SegoeUI", Font.BOLD, 18));
        turnLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        gameLogic.setTurnLabel(turnLabel);

        JButton pauseBtn = createModernButton("⏸");
        pauseBtn.setFont(new Font("SegoeUI", Font.PLAIN, 18));
        pauseBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            showPauseDialog();
        });

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(new Color(240, 240, 240));

        JPanel leftPadding = new JPanel();
        leftPadding.setOpaque(false);
        leftPadding.setPreferredSize(new Dimension(60, 40));

        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING));
        rightPanel.setOpaque(false);
        rightPanel.add(pauseBtn);

        topBar.add(leftPadding, BorderLayout.WEST);
        topBar.add(turnLabel, BorderLayout.CENTER);
        topBar.add(rightPanel, BorderLayout.EAST);

        JPanel gameBoard = new JPanel(new GridLayout(3, 3, 10, 10));
        gameBoard.setBorder(BorderFactory.createEmptyBorder(20, 30, 30, 30));
        gameBoard.setBackground(new Color(240, 240, 240));

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                JButton btn = createModernButton("");
                btn.setFont(new Font("SegoeUI", Font.BOLD, 48));
                btn.setPreferredSize(new Dimension(100, 100));
                final int r = row;
                final int c = col;
                btn.addActionListener(e -> handleButtonClick(btn, r, c));
                buttons[row][col] = btn;
                gameBoard.add(btn);
            }
        }

        gameLogic.setButtons(buttons);
        gameLogic.setGamePanel(this);

        add(topBar, BorderLayout.NORTH);
        add(gameBoard, BorderLayout.CENTER);
    }

    private JButton createModernButton(String text) {
        JButton button = new JButton(text);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(200, 200, 200), 1),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        button.setBackground(Color.WHITE);
        button.setForeground(new Color(70, 70, 70));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(245, 245, 245));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(Color.WHITE);
            }
        });

        return button;
    }

    private void setButtonsEnabled(boolean enabled) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col].setEnabled(enabled);
            }
        }
    }

    private void showPauseDialog() {
        JDialog pauseDialog = new JDialog(parentFrame, "", true);
        pauseDialog.setUndecorated(true);
        pauseDialog.setSize(300, 200);
        pauseDialog.setLocationRelativeTo(parentFrame);
        pauseDialog.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        pauseDialog.getContentPane().setBackground(new Color(250, 250, 250));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(250, 250, 250));

        JLabel pauseLabel = new JLabel("Game Paused", SwingConstants.CENTER);
        pauseLabel.setFont(new Font("SegoeUI", Font.BOLD, 24));
        pauseLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.setOpaque(false);

        JButton playBtn = createModernButton("▶ Continue");
        playBtn.setFont(new Font("SegoeUI", Font.PLAIN, 16));
        playBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            pauseDialog.dispose();
        });

        JButton homeBtn = createModernButton("⌂ Home");
        homeBtn.setFont(new Font("SegoeUI", Font.PLAIN, 16));
        homeBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            SoundManager.stopAllSounds();
            pauseDialog.dispose();
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new HomePanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        buttonPanel.add(playBtn);
        buttonPanel.add(homeBtn);

        panel.add(pauseLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        pauseDialog.add(panel);
        pauseDialog.setVisible(true);
    }

    private void handleButtonClick(JButton btn, int row, int col) {
        if (!btn.getText().isEmpty() || gameLogic.checkGameOver() || !isPlayerTurn) {
            return;
        }

        SoundManager.playClickSound();
        String currentPlayer = gameLogic.getCurrentPlayer();
        btn.setText(currentPlayer);
        gameLogic.makeMove(row, col, currentPlayer);

        if (gameLogic.checkGameOver()) {
            return;
        }

        if (vsComputer && !gameLogic.isBoardFull()) {
            isPlayerTurn = false;
            setButtonsEnabled(false);
            gameLogic.scheduleComputerMove();
        }
    }

    public void onComputerMoveComplete() {
        if (vsComputer) {
            isPlayerTurn = true;
            setButtonsEnabled(true);
        }
    }

    public void showGameResult(String result) {
        if (result.contains("wins")) {
            if (vsComputer && result.contains("O wins")) {
                SoundManager.playLoseSound();
            } else {
                SoundManager.playWinSound();
            }
        } else if (result.contains("draw")) {
            SoundManager.playDrawSound();
        }

        JDialog resultDialog = new JDialog(parentFrame, "", true);
        resultDialog.setUndecorated(true);
        resultDialog.setSize(350, 200);
        resultDialog.setLocationRelativeTo(parentFrame);
        resultDialog.getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        resultDialog.getContentPane().setBackground(new Color(250, 250, 250));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(250, 250, 250));

        JLabel resultLabel = new JLabel(result, SwingConstants.CENTER);
        resultLabel.setFont(new Font("SegoeUI", Font.BOLD, 24));
        resultLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        buttonPanel.setOpaque(false);

        JButton homeBtn = createModernButton("⌂ Home");
        homeBtn.setFont(new Font("SegoeUI", Font.PLAIN, 16));
        homeBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            SoundManager.stopAllSounds();
            resultDialog.dispose();
            parentFrame.getContentPane().removeAll();
            parentFrame.add(new HomePanel(parentFrame));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        JButton playAgainBtn = createModernButton("↻ Play Again");
        playAgainBtn.setFont(new Font("SegoeUI", Font.PLAIN, 16));
        playAgainBtn.addActionListener(e -> {
            SoundManager.playButtonSound();
            SoundManager.stopAllSounds();
            resultDialog.dispose();
            parentFrame.getContentPane().removeAll();
            GamePanel gamePanel;
            if (vsComputer) {
                gamePanel = new GamePanel(parentFrame, true, difficulty);
            } else {
                gamePanel = new GamePanel(parentFrame, false);
            }
            parentFrame.add(gamePanel);
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        buttonPanel.add(homeBtn);
        buttonPanel.add(playAgainBtn);

        panel.add(resultLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        resultDialog.add(panel);
        resultDialog.setVisible(true);
    }
}