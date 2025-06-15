/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.exam;

/**
 *
 * @author vikus
 */
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorkLoader {

    private static final String FILE_PATH = "Вар2_приложение2.xlsx";
    private static List<WorkData> workDataList = new ArrayList<>();

    public static boolean loadExcelFile(File file) {
        workDataList.clear();

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheet("Лист3");

            if (sheet == null) {
                System.err.println("Лист 'Лист3' не найден.");
                return false;
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

            return !workDataList.isEmpty();

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return false;
        }
    }

    private static String getStringValue(Cell cell) {
    if (cell == null) return "";
    switch (cell.getCellType()) {
        case STRING:
            return cell.getStringCellValue().trim();
        case NUMERIC:
            if (cell.getCellStyle().getDataFormatString() != null && 
                cell.getCellStyle().getDataFormatString().toLowerCase().contains("text")) {
                return String.valueOf((int) cell.getNumericCellValue());
            }
            return String.valueOf(cell.getNumericCellValue()).replaceAll("\\.0$", "");
        default:
            return "";
    }
}

private static double getNumericValue(Cell cell) {
    if (cell == null) return 0;
    if (cell.getCellType() != CellType.NUMERIC) {
        try {
            return Double.parseDouble(getStringValue(cell));
        } catch (NumberFormatException e) {
            return 0;
        }
    }
    return cell.getNumericCellValue();
}

    public static List<WorkData> getWorkDataList() {
        return workDataList;
    }
}
