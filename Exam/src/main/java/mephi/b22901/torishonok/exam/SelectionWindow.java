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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 *
 * @author vikus
 */


public class SelectionWindow extends JFrame {

    // Цветовая палитра (берём из WelcomeWindow)
    private static final Color PRIMARY_COLOR = new Color(16, 185, 129);   // #10B981 — основной цвет
    private static final Color PRIMARY_HOVER = new Color(5, 150, 105);    // #059669 — ховер
    private static final Color BACKGROUND_TOP = new Color(230, 252, 236); // #E6FCEC
    private static final Color BACKGROUND_BOTTOM = new Color(200, 240, 215); // #C8F0D7

    public SelectionWindow() {
        setupWindow();
        addComponents();
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Выбор типа расчёта");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Панель с градиентным фоном
        JPanel backgroundPanel = new GradientPanel(new BorderLayout());
        setContentPane(backgroundPanel);
    }

    private void addComponents() {
        // Центральная панель с контентом
        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        JLabel titleLabel = createTitleLabel();
        gbc.gridy = 0;
        contentPanel.add(titleLabel, gbc);

        Component buttonsPanel = createButtonsPanel();
        gbc.gridy = 1;
        contentPanel.add(buttonsPanel, gbc);

        add(contentPanel);
    }

    private JLabel createTitleLabel() {
        JLabel label = new JLabel("<html><div style='text-align: center;'>Выберите тип расчёта</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 36));
        label.setForeground(PRIMARY_COLOR);
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));
        return label;
    }

    private Component createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
        panel.setOpaque(false);

        JButton economicButton = createStyledButton("Экономический расчёт");
        JButton radiationButton = createStyledButton("Радиационный расчёт");

        economicButton.addActionListener(e -> openActionWindow("Экономический", AppThemes.YELLOW));
        radiationButton.addActionListener(e -> openActionWindow("Радиационный", AppThemes.ORANGE));

        panel.add(economicButton);
        panel.add(radiationButton);

        return panel;
    }
    
    private void openActionWindow(String calculationType, AppTheme theme) {
    dispose(); // закрываем текущее окно
    new ActionWindow(calculationType, theme); // открываем окно действий
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 60));
        button.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(PRIMARY_COLOR);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(PRIMARY_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(PRIMARY_COLOR);
            }
        });

        return button;
    }

    

    // Внутренний класс для градиентного фона
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
