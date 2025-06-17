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

import java.io.*;
import java.util.*;

public class ExcelLoader {
    private static List<RoomData> roomDataList = new ArrayList<>();

    public static boolean loadExcelFile() {
        roomDataList.clear();

        try (InputStream is = ExcelLoader.class.getClassLoader().getResourceAsStream("Вар2_приложение1.xlsx")) {
            if (is == null) {
                System.err.println(" Файл Вар2_приложение2.xlsx не найден в ресурсах");
                return false;
            }

            try (Workbook workbook = new XSSFWorkbook(is)) {
                Sheet sheet = workbook.getSheetAt(0); // первый лист

                if (sheet == null) {
                    System.err.println("❌ Лист не найден в файле");
                    return false;
                }

                boolean firstRowSkipped = false;

                for (Row row : sheet) {
                    if (!firstRowSkipped) {
                        firstRowSkipped = true;
                        continue;
                    }

                    if (isRowEmpty(row)) {
                        System.out.println("⚠️ Пропущена пустая строка");
                        continue;
                    }

                    try {
                        String roomCode = getCellAsString(row.getCell(0));
                        String roomName = getCellAsString(row.getCell(1));
                        String location = getCellAsString(row.getCell(2));
                        double length = getNumericValue(row.getCell(3));
                        double width = getNumericValue(row.getCell(4));
                        double height = getNumericValue(row.getCell(5));
                        double area = getNumericValue(row.getCell(6));
                        double volume = getNumericValue(row.getCell(7));
                        String wallMaterial = getCellAsString(row.getCell(10));
                        String floorMaterial = getCellAsString(row.getCell(11));
                        String wallCovering = getCellAsString(row.getCell(12));
                        double wallCoveringArea = getNumericValue(row.getCell(13));
                        String ceilingCovering = getCellAsString(row.getCell(14));
                        double ceilingCoveringArea = getNumericValue(row.getCell(15));
                        String floorCovering = getCellAsString(row.getCell(16));
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
                        System.err.println("⚠️ Ошибка чтения строки: " + row.getRowNum());
                        continue;
                    }
                }

                return !roomDataList.isEmpty();

            }
        } catch (IOException e) {
            System.err.println("❌ Ошибка при открытии файла: " + e.getMessage());
            return false;
        }
    }

    // Получаем значение ячейки как строку
    public static String getCellAsString(Cell cell) {
        if (cell == null) return "";
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();
            case NUMERIC:
                return String.valueOf((int) cell.getNumericCellValue());
            default:
                return "";
        }
    }

    // Получаем числовое значение
    public static double getNumericValue(Cell cell) {
        if (cell == null) return 0.0;
        if (cell.getCellType() != CellType.NUMERIC) {
            try {
                String value = getCellAsString(cell).replaceAll(",", ".");
                return Double.parseDouble(value);
            } catch (NumberFormatException e) {
                return 0.0;
            }
        }
        return cell.getNumericCellValue();
    }

    public static List<RoomData> getRoomDataList() {
        return roomDataList;
    }

    public static boolean isRowEmpty(Row row) {
        if (row == null) return true;

        for (int i = 0; i < 25; i++) {
            Cell cell = row.getCell(i);
            if (cell != null && cell.getCellType() != CellType.BLANK &&
                !(cell.getCellType() == CellType.STRING && cell.getStringCellValue().trim().isEmpty())) {
                return false;
            }
        }
        return true;
    }
}
