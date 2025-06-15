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

public class IntegralCalculator extends JFrame {

    // Трудовая стоимость на человека·час (руб.)
    private static final double PRICE_L = 500;

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

            double mdi = room.getRadiationDoseRate(); // Мощность дозы излучения
            double hv = work.getTimeNorm();           // Норма времени
            int kr = work.getWorkersCount();          // Число работников
            String workType = work.getWorkType().trim().toLowerCase();
            double price_c = work.getPrice();         // Цена из столбца "Цена"
            double pr = getWorkArea(work, room);      // Площадь обработки
            double gr = getAverageContaminationDepth(room, work); // Глубина загрязнения

            // Логирование для отладки
            System.out.printf("🧾 Работа: %s%n", work.getWorkName());
            System.out.printf(" - Комната: %s (%s)%n", room.getRoomName(), room.getRoomCode());
            System.out.printf(" - Тип: %s%n", workType);
            System.out.printf(" - МДИ: %.2f%n", mdi);
            System.out.printf(" - Цена: %.2f%n", price_c);
            System.out.printf(" - Норма времени: %d%n", work.getTimeNorm());
            System.out.printf(" - Работники: %d%n", work.getWorkersCount());
            System.out.printf(" - Площадь: %.2f%n", pr);
            System.out.printf(" - Глубина: %.2f%n", gr);
            System.out.println("------------------------------");

            if (mdi <= 0) System.err.println("⚠️ МДИ = 0 для помещения: " + room.getRoomCode());
            if (price_c <= 0) System.err.println("⚠️ Цена = 0 для работы: " + work.getWorkName());
            if (hv <= 0) System.err.println("⚠️ Норма времени = 0 для работы: " + work.getWorkName());
            if (kr <= 0) System.err.println("⚠️ Число работников = 0 для работы: " + work.getWorkName());

            double time;
            double cost;

            if ("поверхностная".equalsIgnoreCase(workType)) {
                time = hv / kr * pr;
                cost = price_c * pr + time * kr * PRICE_L;

            } else if ("с кол".equalsIgnoreCase(workType)) {
                int ceilDepth = (int) Math.ceil(gr / 10.0); // округление вверх
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

        // Отображение результатов в GUI
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
        showDetailedResults(workList, roomList);

        add(panel);
        setVisible(true);
    }
    
    private void showDetailedResults(List<WorkData> workList, List<RoomData> roomList) {
    StringBuilder sb = new StringBuilder();
    sb.append("🧮 Детальный расчёт:\n\n");

    for (WorkData work : workList) {
        RoomData room = findRoomByCode(roomList, work.getRoomCode());
        if (room == null) continue;

        double mdi = room.getRadiationDoseRate();
        double hv = work.getTimeNorm();
        int kr = work.getWorkersCount();
        double pr = getWorkArea(work, room);
        double gr = getAverageContaminationDepth(room, work);
        String workType = work.getWorkType().trim().toLowerCase();
        double price_c = work.getPrice();

        double time;
        double cost;
        double kdo;
        double ido;

        if ("поверхностная".equalsIgnoreCase(workType)) {
            time = hv / kr * pr;
            cost = price_c * pr + time * kr * PRICE_L;
        } else if ("с кол".equalsIgnoreCase(workType)) {
            int ceilDepth = (int) Math.ceil(gr / 10.0);
            time = hv / kr * pr * ceilDepth;
            cost = price_c * pr * ceilDepth + time * kr * PRICE_L;
        } else {
            continue;
        }

        kdo = mdi * time * kr;
        ido = kdo / kr;

        sb.append("Работа: ").append(work.getWorkName()).append("\n");
        sb.append(" - Комната: ").append(room.getRoomName()).append(" (").append(room.getRoomCode()).append(")\n");
        sb.append(" - Тип: ").append(workType).append("\n");
        sb.append(" - Время: ").append(time).append(" ч\n");
        sb.append(" - Стоимость: ").append(cost).append(" руб.\n");
        sb.append(" - Коллективная доза: ").append(kdo).append(" мкЗв\n");
        sb.append(" - Индивидуальная доза: ").append(ido).append(" мкЗв\n");
        sb.append("------------------------------\n");
    }

            JTextArea area = new JTextArea(sb.toString());
            area.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(area);
            //panel.add(scrollPane);
        }

    // Поиск комнаты по коду
    private RoomData findRoomByCode(List<RoomData> rooms, String code) {
        for (RoomData r : rooms) {
            if (r.getRoomCode().equals(code)) {
                System.out.println("✅ Найдено помещение: " + code);
                return r;
            }
        }
        System.err.println("❌ Не найдено помещение с кодом: " + code);
        return null;
    }

    // Получаем площадь обработки
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

    // Получаем глубину загрязнения
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