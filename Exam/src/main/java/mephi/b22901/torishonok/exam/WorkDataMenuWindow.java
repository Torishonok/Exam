package mephi.b22901.torishonok.exam;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author vikus
 */
import javax.swing.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class WorkDataMenuWindow extends JFrame {

    private final String calculationType;
    private final AppTheme theme;

    public WorkDataMenuWindow(String calculationType, AppTheme theme) {
        this.calculationType = calculationType;
        this.theme = theme;
        setupWindow();
        addComponents();
        setVisible(true);
    }

    private void setupWindow() {
        setTitle("Действия — " + calculationType + " расчёт");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);
        setResizable(false);

        // Передаем theme в GradientPanel
        JPanel backgroundPanel = new GradientPanel(new BorderLayout(), theme);
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

        Component buttonsPanel = createButtonsPanel(theme); // ← передаем theme
        gbc.gridy = 1;
        contentPanel.add(buttonsPanel, gbc);

        add(contentPanel);
    }

    private JLabel createTitleLabel() {
        JLabel label = new JLabel("<html><div style='text-align: center;'>Выберите действие для<br>" +
                calculationType + " расчёта</div></html>", SwingConstants.CENTER);
        label.setFont(new Font("Segoe UI Semibold", Font.BOLD, 36));
        label.setForeground(theme.getPrimaryColor());
        label.setBorder(BorderFactory.createEmptyBorder(0, 0, 60, 0));
        return label;
    }

    private Component createButtonsPanel(AppTheme theme) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 30));
        panel.setOpaque(false);

        JButton viewObjectButton = createStyledButton("Просмотр данных по объекту", theme);
        JButton viewWorksButton = createStyledButton("Просмотр данных по проводимым работам", theme);
        JButton viewElementsButton = createStyledButton("Просмотр элементов с привязкой работ", theme);
        JButton calculateButton = createStyledButton("Расчёт интегральных показателей", theme);

        // Обработчики событий
        viewObjectButton.addActionListener(e -> new DataViewer());
        viewWorksButton.addActionListener(e -> new WorkViewer());
        viewElementsButton.addActionListener(e -> new ElementWorkViewer());
        calculateButton.addActionListener(e -> new IntegralCalculator());

        panel.add(viewObjectButton);
        panel.add(viewWorksButton);
        panel.add(viewElementsButton);
        panel.add(calculateButton);

        return panel;
    }

    private JButton createStyledButton(String text, AppTheme theme) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(350, 60));
        button.setFont(new Font("Segoe UI Semibold", Font.BOLD, 18));
        button.setForeground(Color.WHITE);
        button.setBackground(theme.getPrimaryColor());
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(theme.getPrimaryHover());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(theme.getPrimaryColor());
            }
        });

        return button;
    }

    // Градиентная панель — статическая, принимает theme через конструктор
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
