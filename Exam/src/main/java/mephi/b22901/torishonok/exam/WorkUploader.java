/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.exam;

/**
 *
 * @author vikus
 */

import javax.swing.*;
import java.awt.*;
import java.io.File;
import javax.swing.filechooser.FileNameExtensionFilter;

public class WorkUploader extends JFrame {

    private final String calculationType;
    private final AppTheme theme;

    public WorkUploader(String calculationType, AppTheme theme) {
        this.calculationType = calculationType;
        this.theme = theme;
        setupWindow();
        showInstructionDialog(); // Показываем всплывающее окно с инструкцией
    }

    private void setupWindow() {
        setTitle("Загрузка файла с проводимыми работами");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel backgroundPanel = new GradientPanel(new BorderLayout(), theme);
        setContentPane(backgroundPanel);
    }

    // Показываем всплывающее окно с инструкцией
    private void showInstructionDialog() {
        JOptionPane.showMessageDialog(
                this,
                "Пожалуйста, выберите файл с проводимыми работами",
                "Информация",
                JOptionPane.INFORMATION_MESSAGE
        );
        openFileChooser(); // После окна — открываем диалог выбора файла
    }

    // Открываем диалог выбора файла
    private void openFileChooser() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Выберите файл с проводимыми работами");

        // Ограничиваем выбор только .xlsx-файлами
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Excel файлы (.xlsx)", "xlsx"));
        fc.setAcceptAllFileFilterUsed(false);

        // Устанавливаем начальную директорию как текущую папку проекта
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = fc.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();

            // Проверяем, является ли выбранный файл Excel-файлом
            boolean success = WorkLoader.loadExcelFile(selectedFile);

            if (success) {
                JOptionPane.showMessageDialog(this, "Файл успешно загружен.", "Успех", JOptionPane.INFORMATION_MESSAGE);
                dispose(); // Закрываем текущее окно
                new WorkDataMenuWindow(calculationType, theme); // Переход к меню действий
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка при чтении файла или неверная структура данных.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Файл не выбран. Работа прервана.", "Отмена", JOptionPane.WARNING_MESSAGE);
            dispose(); // Закрываем окно
        }
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