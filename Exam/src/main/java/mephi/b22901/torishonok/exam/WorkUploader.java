/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.exam;

/**
 *
 * @author vikus
 */

import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class WorkUploader extends JFrame {

    private static final String FILE_PATH = "Вар2_приложение2.xlsx";

    private final List<WorkData> workDataList = new ArrayList<>();

    public WorkUploader() {
    setTitle("Загрузка файла с работами");
    setSize(400, 200);
    setLocationRelativeTo(null);
    setLayout(new BorderLayout());
    getContentPane().setBackground(Color.WHITE);

    JLabel label = new JLabel("Выберите файл с работами (.xlsx)", SwingConstants.CENTER);
    label.setFont(new Font("Segoe UI", Font.BOLD, 18));
    JButton chooseButton = new JButton("Выбрать файл");

    chooseButton.addActionListener(e -> {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fc.addChoosableFileFilter(new FileNameExtensionFilter("Excel файлы (.xlsx)", "xlsx"));
        fc.setAcceptAllFileFilterUsed(false);

        if (fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            loadExcelFile(fc.getSelectedFile());

            if (!workDataList.isEmpty()) {
                dispose(); // Закрываем окно выбора
                showWorkTable(); // Открываем таблицу
            } else {
                JOptionPane.showMessageDialog(this, "Файл пустой или структура неверна.");
            }
        }
    });

    JPanel panel = new JPanel(new BorderLayout(10, 10));
    panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
    panel.setBackground(Color.WHITE);
    panel.add(label, BorderLayout.NORTH);
    panel.add(chooseButton, BorderLayout.SOUTH);

    add(panel);
    setVisible(true);
}

private void showWorkTable() {
    JFrame frame = new JFrame("Работы по помещениям");
    frame.setSize(1200, 600);
    frame.setLocationRelativeTo(null);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

    Object[][] tableData = workDataList.stream()
            .map(data -> new Object[]{
                    data.getNumber(),
                    data.getRoomCode(),
                    data.getRoomName(),
                    data.getPart(),
                    data.getElementCode(),
                    data.getWorkName(),
                    data.getDescription(),
                    data.getWorkType(),
                    data.getPrice(),
                    data.getPriority(),
                    data.getTimeNorm(),
                    data.getWorkersCount()
            })
            .toArray(Object[][]::new);

    String[] columns = {
            "Номер",
            "Код помещения",
            "Помещение",
            "Часть",
            "Код элемента",
            "Наименование работы",
            "Описание",
            "Тип работы",
            "Цена",
            "Очерёдность",
            "Норма времени",
            "Кол-во рабочих"
    };

    JTable table = new JTable(tableData, columns);
    JScrollPane scrollPane = new JScrollPane(table);
    frame.add(scrollPane);
    frame.setVisible(true);
}

    private void loadExcelFile(File file) {
        if (!file.exists()) {
            JOptionPane.showMessageDialog(this, "Файл не найден: " + file.getName(), "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Лист3");

            if (sheet == null) {
                JOptionPane.showMessageDialog(this, "Лист 'Лист3' не найден.", "Ошибка", JOptionPane.ERROR_MESSAGE);
                return;
            }

            boolean firstRowSkipped = false;

            for (Row row : sheet) {
                if (!firstRowSkipped) {
                    firstRowSkipped = true;
                    continue;
                }

                try {
                    int number = (int) row.getCell(0).getNumericCellValue();
                    String roomCode = getStringValue(row.getCell(1));
                    String roomName = getStringValue(row.getCell(2));
                    String part = getStringValue(row.getCell(3));
                    String elementCode = getStringValue(row.getCell(4));
                    String workName = getStringValue(row.getCell(5));
                    String description = getStringValue(row.getCell(6));
                    String workType = getStringValue(row.getCell(7));
                    double price = row.getCell(8).getNumericCellValue();
                    int priority = (int) row.getCell(9).getNumericCellValue();
                    int timeNorm = (int) row.getCell(10).getNumericCellValue();
                    int workersCount = (int) row.getCell(11).getNumericCellValue();

                    workDataList.add(new WorkData(number, roomCode, roomName, part, elementCode,
                            workName, description, workType, price, priority, timeNorm, workersCount));

                } catch (Exception e) {
                    System.err.println("Ошибка при чтении строки: " + row.getRowNum());
                    continue;
                }
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Ошибка при чтении файла: " + e.getMessage(), "Ошибка", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String getStringValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        }
        return cell.getStringCellValue().trim();
    }

    private void showTable() {
        if (workDataList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Нет данных для отображения.", "Информация", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        Object[][] tableData = workDataList.stream()
                .map(data -> new Object[]{
                        data.getNumber(),
                        data.getRoomCode(),
                        data.getRoomName(),
                        data.getPart(),
                        data.getElementCode(),
                        data.getWorkName(),
                        data.getDescription(),
                        data.getWorkType(),
                        data.getPrice(),
                        data.getPriority(),
                        data.getTimeNorm(),
                        data.getWorkersCount()
                })
                .toArray(Object[][]::new);

        String[] columns = {
                "Номер",
                "Код помещения",
                "Помещение",
                "Часть",
                "Код элемента",
                "Наименование работы",
                "Описание",
                "Тип работы",
                "Цена",
                "Очерёдность",
                "Норма времени",
                "Кол-во рабочих"
        };

        JTable table = new JTable(tableData, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);
    }
}
