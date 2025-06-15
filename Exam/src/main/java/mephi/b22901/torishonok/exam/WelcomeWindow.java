/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.exam;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 *
 * @author vikus
 */



public class WelcomeWindow extends JFrame {
    private final boolean isFileLoaded;
    private final AppTheme theme;

    public WelcomeWindow(boolean isFileLoaded) {
        this.isFileLoaded = isFileLoaded;
        this.theme = AppThemes.GREEN; // Твоя основная тема — зелёная
        setupWindow();
        addComponents();
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Программа расчёта");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel backgroundPanel = new GradientPanel(new BorderLayout(), theme); // Передаём тему
        setContentPane(backgroundPanel);
    }
    
  // Внутри любого JFrame-класса (например, SelectionWindow)
class GradientPanel extends JPanel {
    private final Color backgroundTop;
    private final Color backgroundBottom;

    public GradientPanel(LayoutManager layout, AppTheme theme) {
        super(layout);
        this.backgroundTop = theme.getBackgroundTop();
        this.backgroundBottom = theme.getBackgroundBottom();
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        int width = getWidth();
        int height = getHeight();

        GradientPaint gp = new GradientPaint(
                0, 0, backgroundTop,
                0, height, backgroundBottom
        );

        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setPaint(gp);
        g2d.fillRect(0, 0, width, height);
    }
}
    
    private void addComponents() {
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        JLabel titleLabel = createTitleLabel();
        gbc.gridy = 0;
        contentPanel.add(titleLabel, gbc);

        JLabel questionLabel = createQuestionLabel();
        gbc.gridy = 1;
        contentPanel.add(questionLabel, gbc);

        Component buttonsPanel = createButtonsPanel();
        gbc.gridy = 2;
        contentPanel.add(buttonsPanel, gbc);

        add(contentPanel);
    }

    private JLabel createTitleLabel() {
        JLabel label = new JLabel("<html><div style='text-align: center;'>Добро пожаловать в программу<br>по выполнению экономического<br>и радиационного расчёта</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 24));
        label.setForeground(theme.getPrimaryColor()); // Цвет из темы
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        return label;
    }

    private JLabel createQuestionLabel() {
        JLabel label = new JLabel("Готовы начать работу?");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        label.setForeground(theme.getPrimaryColor().darker().darker()); // Более тёмный оттенок
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private Component createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        panel.setOpaque(false);

        JButton startButton = createStyledButton("Начать работу", theme.getPrimaryColor(), theme.getPrimaryHover());
        JButton exitButton = createStyledButton("Завершить работу", new Color(240, 255, 244), new Color(209, 250, 229));

        exitButton.setForeground(theme.getPrimaryColor());

        exitButton.addActionListener(e -> System.exit(0));
        startButton.addActionListener(e -> showCustomInfoDialog());

        panel.add(startButton);
        panel.add(exitButton);

        return panel;
    }

    private void showCustomInfoDialog() {
        JDialog dialog = new JDialog(this, "Информация", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel dialogPanel = new GradientPanel(new BorderLayout(), theme);
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Icon systemIcon = UIManager.getIcon("OptionPane.informationIcon");
        JLabel iconLabel = new JLabel(systemIcon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JTextPane messagePane = new JTextPane();
        messagePane.setContentType("text/html");
        messagePane.setEditable(false);
        messagePane.setOpaque(false);
        messagePane.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        if (isFileLoaded) {
            messagePane.setText(String.format(
                    "<html><div style='font-family: \"Segoe UI\"; font-size: 16px; color: %s;'>Файл с данными об объекте успешно загружен.</div></html>",
                    toHexString(theme.getPrimaryColor())
            ));
        } else {
            messagePane.setText(String.format(
                    "<html><div style='font-family: \"Segoe UI\"; font-size: 16px; color: #B91C1C;'>Ошибка загрузки файла или файл отсутствует.</div></html>"
            ));
        }

        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textPanel.setOpaque(false);
        textPanel.add(iconLabel);
        textPanel.add(messagePane);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton continueButton = createStyledButton("Продолжить", theme.getPrimaryColor(), theme.getPrimaryHover());
        JButton cancelButton = createStyledButton("Отмена", new Color(240, 255, 244), new Color(209, 250, 229));
        cancelButton.setForeground(theme.getPrimaryColor());

        continueButton.addActionListener(e -> {
            dialog.dispose();
            new SelectionWindow(theme); // ← Теперь передаём theme
        });
        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(continueButton);

        dialogPanel.add(textPanel, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(dialogPanel);
        dialog.setVisible(true);
    }

    private JButton createStyledButton(String text, Color bgColor, Color hoverColor) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(180, 50));
        button.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(hoverColor);
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // Утилитарный метод для преобразования цвета в HEX
    private String toHexString(Color c) {
        return String.format("#%02x%02x%02x", c.getRed(), c.getGreen(), c.getBlue());
    }
}