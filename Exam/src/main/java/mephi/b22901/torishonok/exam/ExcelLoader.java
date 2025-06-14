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

    private static final String FILE_PATH = "C:\\Users\\vikus\\Downloads\\Вар2_приложение1.xlsx"; // Файл должен лежать в корне проекта
    private static List<RoomData> roomDataList = new ArrayList<>();

    public static boolean loadFileAutomatically() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            System.err.println("Файл не найден: " + FILE_PATH);
            return false;
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Первый лист
            boolean firstRowSkipped = false;

            for (Row row : sheet) {
                if (!firstRowSkipped) {
                    firstRowSkipped = true;
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

                    double contaminationAreaWall = getNumericValue(row.getCell(14));
                    double contaminationDepthWall = getNumericValue(row.getCell(15));
                    double contaminationAreaCeiling = getNumericValue(row.getCell(16));
                    double contaminationDepthCeiling = getNumericValue(row.getCell(17));
                    double contaminationAreaFloor = getNumericValue(row.getCell(18));
                    double contaminationDepthFloor = getNumericValue(row.getCell(19));

                    double radiationDoseRate = getNumericValue(row.getCell(20));
                    double volumetricActivity = getNumericValue(row.getCell(21));

                    roomDataList.add(new RoomData(roomCode, roomName, location,
                            length, width, height, area, volume,
                            wallMaterial, floorMaterial,
                            contaminationAreaWall, contaminationDepthWall,
                            contaminationAreaCeiling, contaminationDepthCeiling,
                            contaminationAreaFloor, contaminationDepthFloor,
                            radiationDoseRate, volumetricActivity));

                } catch (Exception e) {
                    System.err.println("Ошибка при чтении строки: " + row.getRowNum());
                    continue;
                }
            }

            return !roomDataList.isEmpty();

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return false;
        }
    }
    
    public boolean loadExcelFile(File file) {
        if (file == null || !file.exists()) {
            System.err.println("Файл не найден или недоступен.");
            return false;
        }

        try (FileInputStream fis = new FileInputStream(file);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Получаем первый лист

            if (sheet == null) {
                System.err.println("Лист в файле не найден.");
                return false;
            }

            // Проверяем, есть ли данные (хотя бы одна строка после заголовка)
            for (Row row : sheet) {
                if (row.getRowNum() > 0) { // Пропускаем заголовок
                    return true; // Есть данные
                }
            }

            System.err.println("Файл пустой.");
            return false;

        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
            return false;
        }
    }


    private static String getStringValue(Cell cell) {
        if (cell == null) return "";
        return cell.toString().trim();
    }

    private static double getNumericValue(Cell cell) {
        if (cell == null || cell.getCellType() != CellType.NUMERIC) return 0.0;
        return cell.getNumericCellValue();
    }

    public static List<RoomData> getRoomDataList() {
        return roomDataList;
    }
}
