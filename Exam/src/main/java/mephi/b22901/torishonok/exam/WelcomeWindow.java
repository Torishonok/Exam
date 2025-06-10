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
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

/**
 *
 * @author vikus
 */

public class WelcomeWindow extends JFrame {

    // Цветовая палитра (зелёные оттенки)
    private static final Color PRIMARY_COLOR = new Color(16, 185, 129);   // #10B981 — основной цвет
    private static final Color PRIMARY_HOVER = new Color(5, 150, 105);    // #059669 — ховер
    private static final Color SECONDARY_COLOR = new Color(240, 255, 244); // #F0FFF4 — фон второстепенной кнопки
    private static final Color SECONDARY_HOVER = new Color(209, 250, 229); // #D1FAE5 — ховер
    private static final Color TEXT_SECONDARY = new Color(72, 187, 120);  // #48BB78 — текст

    // Градиентный фон
    private static final Color BACKGROUND_TOP = new Color(230, 252, 236); // #E6FCEC
    private static final Color BACKGROUND_BOTTOM = new Color(200, 240, 215); // #C8F0D7

    public WelcomeWindow() {
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

        JPanel backgroundPanel = new GradientPanel(new BorderLayout());
        setContentPane(backgroundPanel);
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
        JLabel label = new JLabel("<html>Добро пожаловать в программу по выполнению экономического<br>" +
                                  "и радиационного расчёта для выводимого из эксплуатации<br>" +
                                  "объекта использования атомной энергии</html>", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 24));
        label.setForeground(PRIMARY_COLOR);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 40, 0));
        return label;
    }

    private JLabel createQuestionLabel() {
        JLabel label = new JLabel("Готовы начать работу?");
        label.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        label.setForeground(TEXT_SECONDARY);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 30, 0));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }

    private Component createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        panel.setOpaque(false);

        JButton startButton = createStyledButton("Начать работу", PRIMARY_COLOR, PRIMARY_HOVER);
        JButton exitButton = createStyledButton("Завершить работу", SECONDARY_COLOR, SECONDARY_HOVER);
        exitButton.setForeground(PRIMARY_COLOR);

        exitButton.addActionListener(e -> System.exit(0));

        startButton.addActionListener(e -> showCustomInfoDialog());

        panel.add(startButton);
        panel.add(exitButton);

        return panel;
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
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(hoverColor);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(bgColor);
            }
        });

        return button;
    }

    // Кастомное информационное окно
    private void showCustomInfoDialog() {
        JDialog dialog = new JDialog(this, "Информация", true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setResizable(false);
        dialog.setLayout(new BorderLayout());

        // Панель с градиентным фоном
        JPanel dialogPanel = new GradientPanel(new BorderLayout());
        dialogPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Иконка
        Icon systemIcon = UIManager.getIcon("OptionPane.informationIcon");
        JLabel iconLabel = new JLabel(systemIcon);
        iconLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        // Текст сообщения
        JTextPane messagePane = new JTextPane();
        messagePane.setContentType("text/html");
        messagePane.setText("<html><div style='font-family: Segoe UI; font-size: 16px; color: #111827;'>Вы успешно начали работу с программой. <br><br>" +
                            "В следующем окне вы сможете выбрать тип расчёта:</div>" +
                            "<ul style='margin-top: 10px; padding-left: 20px;'>" +
                            "<li>Экономический расчёт</li>" +
                            "<li>Радиационный расчёт</li>" +
                            "</ul></html>");
        messagePane.setEditable(false);
        messagePane.setOpaque(false);
        messagePane.setBorder(BorderFactory.createEmptyBorder(10, 0, 20, 0));

        // Панель с контентом
        JPanel textPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        textPanel.setOpaque(false);
        textPanel.add(iconLabel);
        textPanel.add(messagePane);

        // Кнопки
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton continueButton = createStyledButton("Продолжить", PRIMARY_COLOR, PRIMARY_HOVER);
        JButton cancelButton = createStyledButton("Отмена", SECONDARY_COLOR, SECONDARY_HOVER);
        cancelButton.setForeground(PRIMARY_COLOR);

        continueButton.addActionListener(e -> {
            dialog.dispose();
            // Здесь можно открыть следующее окно
            JOptionPane.showMessageDialog(WelcomeWindow.this, "Переход к выбору типа расчёта...");
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        buttonPanel.add(cancelButton);
        buttonPanel.add(continueButton);

        dialogPanel.add(textPanel, BorderLayout.CENTER);
        dialogPanel.add(buttonPanel, BorderLayout.SOUTH);

        dialog.add(dialogPanel);
        dialog.setVisible(true);
    }

    // Панель с градиентным фоном
    static class GradientPanel extends JPanel {
        public GradientPanel(LayoutManager layout) {
            super(layout);
            setOpaque(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            int width = getWidth();
            int height = getHeight();

            GradientPaint gp = new GradientPaint(
                    0, 0, BACKGROUND_TOP,
                    0, height, BACKGROUND_BOTTOM
            );

            g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }
}