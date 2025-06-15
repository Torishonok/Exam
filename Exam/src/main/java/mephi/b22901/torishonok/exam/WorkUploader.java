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
import mephi.b22901.torishonok.exam.WelcomeWindow.GradientPanel;

public class WorkUploader extends JFrame {

    private final String calculationType;
    private final AppTheme theme;

    public WorkUploader(String calculationType, AppTheme theme) {
        this.calculationType = calculationType;
        this.theme = theme;
        setupWindow();
        loadAndShowResult();
    }

    private void setupWindow() {
        setTitle("Загрузка файла с проводимыми работами");
        setSize(500, 300);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel backgroundPanel = new GradientPanel(new BorderLayout());
        setContentPane(backgroundPanel);
    }

    private void loadAndShowResult() {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Excel файлы (.xlsx)", "xlsx"));
        fc.setAcceptAllFileFilterUsed(false);

        int result = fc.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fc.getSelectedFile();

            // Загрузка данных из Excel
            boolean success = WorkLoader.loadExcelFile(selectedFile);

            if (success) {
                JOptionPane.showMessageDialog(this, "Файл успешно загружен.", "Успех", JOptionPane.INFORMATION_MESSAGE);
                openActionWindow();
            } else {
                JOptionPane.showMessageDialog(this, "Ошибка при чтении файла или неверная структура данных.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Файл не выбран.", "Отмена", JOptionPane.WARNING_MESSAGE);
            dispose(); // Закрываем текущее окно
        }
    }

    private void openActionWindow() {
        dispose(); // Закрываем текущее окно
        new WorkDataMenuWindow(calculationType, theme);
    }
}