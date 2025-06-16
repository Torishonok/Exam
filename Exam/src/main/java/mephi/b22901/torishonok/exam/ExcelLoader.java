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

public class ExcelLoader {

    private static List<RoomData> roomDataList = new ArrayList<>();

    public static boolean loadExcelFile() {
        roomDataList.clear();

        try (Workbook workbook = new XSSFWorkbook(new FileInputStream("C:\\Users\\vikus\\OneDrive\\Документы\\GitHub\\Exam\\Exam\\resorces\\Вар2_приложение1.xlsx"))) {
            Sheet sheet = workbook.getSheetAt(0); // Лист1

            if (sheet == null) {
                System.err.println("Лист 'Лист1' не найден.");
                return false;
            }

            boolean firstRowSkipped = false;

            for (Row row : sheet) {
                if (!firstRowSkipped) {
                    firstRowSkipped = true;
                    continue;
                }

                // Пропуск пустых строк
                if (isRowEmpty(row)) {
                    System.out.println("Пропущена пустая строка: " + row.getRowNum());
                    continue;
                }

                // Пример: если первая ячейка не является числом и не содержит код помещения — это не данные о помещении
                Cell potentialCodeCell = row.getCell(0);
                if (potentialCodeCell == null || 
                    (potentialCodeCell.getCellType() != CellType.NUMERIC && 
                     !potentialCodeCell.getStringCellValue().matches("\\d+"))) {
                    System.out.println("Пропущена незначимая строка: " + row.getRowNum());
                    continue;
                }

                try {
                    String roomCode = getStringValue(row.getCell(0));
                    String roomName = getStringValue(row.getCell(1));
                    String location = getStringValue(row.getCell(2));

                    double length = getNumericValue(row.getCell(3));
                    double width = getNumericValue(row.getCell(4));
                    double height = getNumericValue(row.getCell(5));
                    double area = getNumericValue(row.getCell(6));
                    double volume = getNumericValue(row.getCell(7));

                    String wallMaterial = getStringValue(row.getCell(10));
                    String floorMaterial = getStringValue(row.getCell(11));

                    String wallCovering = getStringValue(row.getCell(12));
                    double wallCoveringArea = getNumericValue(row.getCell(13));

                    String ceilingCovering = getStringValue(row.getCell(14));
                    double ceilingCoveringArea = getNumericValue(row.getCell(15));

                    String floorCovering = getStringValue(row.getCell(16));
                    double floorCoveringArea = getNumericValue(row.getCell(17));

                    double contaminationWallArea = getNumericValue(row.getCell(18));
                    double contaminationWallDepth = getNumericValue(row.getCell(19));

                    double contaminationCeilingArea = getNumericValue(row.getCell(20));
                    double contaminationCeilingDepth = getNumericValue(row.getCell(21));

                    double contaminationFloorArea = getNumericValue(row.getCell(22));
                    double contaminationFloorDepth = getNumericValue(row.getCell(23));

                    double radiationDoseRate = getNumericValue(row.getCell(24));
                    double volumetricActivity = getNumericValue(row.getCell(25));

                    roomDataList.add(new RoomData(
                            roomCode, roomName, location,
                            length, width, height, area, volume,
                            wallMaterial, floorMaterial,
                            wallCovering, wallCoveringArea,
                            ceilingCovering, ceilingCoveringArea,
                            floorCovering, floorCoveringArea,
                            contaminationWallArea, contaminationWallDepth,
                            contaminationCeilingArea, contaminationCeilingDepth,
                            contaminationFloorArea, contaminationFloorDepth,
                            radiationDoseRate, volumetricActivity
                    ));

                } catch (Exception e) {
                    System.err.println("Ошибка при чтении строки: " + row.getRowNum());
                    continue;
                }
            }

            return !roomDataList.isEmpty();

        } catch (IOException e) {
            System.err.println("Ошибка при открытии файла: " + e.getMessage());
            return false;
        }
    }

    public static List<RoomData> getRoomDataList() {
        return roomDataList;
    }

    private static String getStringValue(Cell cell) {
        if (cell == null) return "";
        if (cell.getCellType() == CellType.NUMERIC) {
            return String.valueOf((int) cell.getNumericCellValue());
        }
        return cell.getStringCellValue().trim();
    }

    private static double getNumericValue(Cell cell) {
        if (cell == null) return 0.0;
        if (cell.getCellType() != CellType.NUMERIC) {
            try {
                return Double.parseDouble(getStringValue(cell).replaceAll(",", "."));
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return cell.getNumericCellValue();
    }

    // Метод для определения пустой строки
    private static boolean isRowEmpty(Row row) {
        if (row == null) return true;

        for (int i = 0; i < 20; i++) { // Проверяем первые 20 колонок
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK &&
                !(cell.getCellType() == CellType.STRING && cell.getStringCellValue().trim().isEmpty())) {
                return false;
            }
        }
        return true;
    }
}
