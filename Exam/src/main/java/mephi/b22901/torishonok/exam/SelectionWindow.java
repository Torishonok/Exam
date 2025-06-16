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
    private final AppTheme theme;

    public SelectionWindow(AppTheme theme) {
        this.theme = theme;
        setupWindow();
        addComponents();
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Выбор типа расчёта");
        setSize(900, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setContentPane(new GradientPanel(new BorderLayout(), theme));
    }

    private void addComponents() {
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
        label.setForeground(theme.getPrimaryColor());
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));
        return label;
    }

    private Component createButtonsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
        panel.setOpaque(false);

        JButton economicButton = createStyledButton("Экономический расчёт", AppThemes.GREEN);
        JButton radiationButton = createStyledButton("Радиационный расчёт", AppThemes.GREEN);

        economicButton.addActionListener(e -> openWorkUploader("Экономический", AppThemes.GREEN));
        radiationButton.addActionListener(e -> openWorkUploader("Радиационный", AppThemes.GREEN));

        panel.add(economicButton);
        panel.add(radiationButton);

        return panel;
    }

    private JButton createStyledButton(String text, AppTheme buttonTheme) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(300, 60));
        button.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(buttonTheme.getPrimaryColor());
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(buttonTheme.getPrimaryHover());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(buttonTheme.getPrimaryColor());
            }
        });

        return button;
    }

    private void openWorkUploader(String calculationType, AppTheme theme) {
        dispose();
        new WorkUploader(calculationType, theme);
    }

    // Градиентная панель
    static class GradientPanel extends JPanel {
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
}
