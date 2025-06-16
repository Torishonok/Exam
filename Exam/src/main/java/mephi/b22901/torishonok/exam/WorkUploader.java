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
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;

public class WorkUploader extends JFrame {

    private final String calculationType;
    private final AppTheme theme;

    public WorkUploader(String calculationType, AppTheme theme) {
        this.calculationType = calculationType;
        this.theme = theme;
        loadAndShowResult();
        setVisible(true);
    }

    

    private void loadAndShowResult() {
        JFileChooser fc = new JFileChooser();
        fc.setDialogTitle("Выберите файл с проводимыми работами");
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Excel файлы (.xlsx)", "xlsx"));
        fc.setAcceptAllFileFilterUsed(false);
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));

        int result = fc.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();
            boolean success = WorkLoader.loadExcelFile(selectedFile);

            if (success) {
                JOptionPane.showMessageDialog(this, "Файл успешно загружен.");
                openActionWindow();
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка при чтении файла или неверная структура данных.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Файл не выбран.", "Отмена", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void openActionWindow() {
        dispose(); 
        new WorkDataMenuWindow(calculationType, theme); 
    }

    
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

            GradientPaint gp = new GradientPaint(0, 0, backgroundTop, 0, height, backgroundBottom);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, width, height);
        }
    }
}