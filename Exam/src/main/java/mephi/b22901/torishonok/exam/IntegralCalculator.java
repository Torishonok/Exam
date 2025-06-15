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
import java.awt.*;
import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class IntegralCalculator extends JFrame {

    private static final double PRICE_L = 500; // Трудовая стоимость на человека·час

    public IntegralCalculator() {
        setTitle("Интегральные показатели проекта");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("<html><h2 style='text-align:center;'>Интегральные показатели</h2></html>", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 40, 10));
        panel.add(titleLabel);

        List<RoomData> roomList = ExcelLoader.getRoomDataList();
        List<WorkData> workList = WorkLoader.getWorkDataList();

        if (roomList == null || workList == null || workList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ошибка: данные не загружены.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double totalCost = 0;
        double totalTime = 0;
        double collectiveDose = 0;
        int totalWorkers = 0;

        for (WorkData work : workList) {
            RoomData room = findRoomByCode(roomList, work.getRoomCode());
            if (room == null) continue;

            double mdi = room.getRadiationDoseRate(); // Мощность дозы
            double hv = work.getTimeNorm();           // Норма времени
            int kr = work.getWorkersCount();          // Число рабочих
            String workType = work.getWorkType().trim().toLowerCase();
            double price_c = work.getPrice();         // Цена из столбца 8
            double pr = getWorkArea(work, room);      // Площадь обработки
            double gr = getAverageContaminationDepth(room, work); // Глубина загрязнения

            // Логирование
            System.out.printf("🧾 Работа: %s%n", work.getWorkName());
            System.out.printf(" - Комната: %s (%s)%n", room.getRoomName(), room.getRoomCode());
            System.out.printf(" - Тип работы: %s%n", workType);
            System.out.printf(" - МДИ: %.2f%n", mdi);
            System.out.printf(" - Цена: %.2f%n", price_c);
            System.out.printf(" - Норма времени: %d%n", work.getTimeNorm());
            System.out.printf(" - Работников: %d%n", kr);
            System.out.printf(" - Площадь: %.2f%n", pr);
            System.out.printf(" - Глубина загрязнения: %.2f%n", gr);
            System.out.println("------------------------------");

            double time;
            double cost;

            if ("поверхностная".equalsIgnoreCase(workType)) {
                time = hv / kr * pr;
                cost = price_c * pr + time * kr * PRICE_L;

            } else if ("с кол".equalsIgnoreCase(workType)) {
                int ceilDepth = (int) Math.ceil(gr / 10.0);
                time = hv / kr * pr * ceilDepth;
                cost = price_c * pr * ceilDepth + time * kr * PRICE_L;

            } else {
                System.err.println("⚠️ Неизвестный тип работы: " + workType);
                continue;
            }

            double kdo = mdi * time * kr;
            double ido = kdo / kr;

            totalCost += cost;
            totalTime += time;
            collectiveDose += kdo;
            totalWorkers += kr;
        }

        JLabel costLabel = new JLabel(String.format("a. Стоимость проекта: %.2f руб.", totalCost));
        JLabel timeLabel = new JLabel(String.format("b. Общее время выполнения: %.2f ч", totalTime));
        JLabel doseCollectiveLabel = new JLabel(String.format("c. Коллективная эквивалентная доза: %.2f мкЗв", collectiveDose));
        JLabel doseIndividualLabel = new JLabel(String.format("d. Средняя индивидуальная доза: %.2f мкЗв", totalWorkers > 0 ? collectiveDose / totalWorkers : 0));

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 18);
        costLabel.setFont(labelFont);
        timeLabel.setFont(labelFont);
        doseCollectiveLabel.setFont(labelFont);
        doseIndividualLabel.setFont(labelFont);

        costLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        timeLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        doseCollectiveLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        doseIndividualLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        panel.add(costLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(timeLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(doseCollectiveLabel);
        panel.add(Box.createRigidArea(new Dimension(0, 10)));
        panel.add(doseIndividualLabel);

        add(panel);
        setVisible(true);
    }

    private RoomData findRoomByCode(List<RoomData> rooms, String code) {
        for (RoomData r : rooms) {
            if (r.getRoomCode().equals(code)) {
                return r;
            }
        }
        return null;
    }

    private double getWorkArea(WorkData work, RoomData room) {
        String part = work.getPart().toLowerCase();
        if (part.contains("стен")) {
            return room.getWallCoveringArea();
        } else if (part.contains("потолок")) {
            return room.getCeilingCoveringArea();
        } else if (part.contains("пол")) {
            return room.getFloorCoveringArea();
        }
        return 0;
    }

    private double getAverageContaminationDepth(RoomData room, WorkData work) {
        String part = work.getPart().toLowerCase();
        if (part.contains("стен")) {
            return room.getContaminationWallDepth();
        } else if (part.contains("потолок")) {
            return room.getContaminationCeilingDepth();
        } else if (part.contains("пол")) {
            return room.getContaminationFloorDepth();
        }
        return 0;
    }
}