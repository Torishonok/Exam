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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class IntegralCalculator extends JFrame {

    private static final double PRICE_L = 500; 
    private final AppTheme theme;
    

    public IntegralCalculator(AppTheme theme) {
        this.theme = theme;
        setTitle("Интегральные показатели проекта");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        panel.setBackground(Color.WHITE);

        JLabel titleLabel = new JLabel("<html><h2 style='text-align:center;'>Рассчитанные интегральные показатели</h2></html>", SwingConstants.CENTER);
        titleLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(theme.getPrimaryColor());
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

            double mdi = room.getRadiationDoseRate();
            double hv = work.getTimeNorm();         
            int kr = work.getWorkersCount();        
            String workType = work.getWorkType().trim().toLowerCase();
            double price_c = work.getPrice();       
            double pr = getWorkArea(work, room);     
            double gr = getAverageContaminationDepth(room, work); 
            double time;
            double cost;

            if ("поверхностная".equalsIgnoreCase(workType)) {
                time = hv / kr * pr;
                cost = price_c * pr + time * kr * PRICE_L;
            } else if ("скол".equalsIgnoreCase(workType)) {
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

        if ("Радиационный".equals(WorkDataMenuWindow.getCurrentCalculationTypeStatic())) {
            JButton histogramButton = createStyledButton("Показать гистограммы", theme);
            histogramButton.addActionListener(e -> {
                new MonteCarloDoseCalculator(workList, roomList).runSimulation();
            });
            histogramButton.setAlignmentX(Component.LEFT_ALIGNMENT);
            panel.add(Box.createRigidArea(new Dimension(0, 30)));
            panel.add(histogramButton);
        }
        
        

        add(panel);
        setVisible(true);
    }

    public static RoomData findRoomByCode(List<RoomData> rooms, String code) {
        for (RoomData r : rooms) {
            if (r.getRoomCode().equals(code)) {
                return r;
            }
        }
        return null;
    }

    public static double getWorkArea(WorkData work, RoomData room) {
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

    public static double getAverageContaminationDepth(RoomData room, WorkData work) {
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

    
    private JButton createStyledButton(String text, AppTheme theme) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(250, 40));
        button.setFont(new Font("Segoe UI Semibold", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(theme.getPrimaryColor());
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(theme.getPrimaryHover());
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(theme.getPrimaryColor());
            }
        });

        return button;
    }

    
    public static String getCurrentCalculationType() {
        return WorkDataMenuWindow.getCurrentCalculationTypeStatic();
    }
}