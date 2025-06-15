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

public class ElementWorkViewer extends JFrame {

    public ElementWorkViewer() {
        setTitle("Элементы объекта с привязкой работ");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        List<RoomData> roomList = ExcelLoader.getRoomDataList();
        List<WorkData> workList = WorkLoader.getWorkDataList();

        if (roomList == null || workList == null) {
            JOptionPane.showMessageDialog(this, "Данные не загружены.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Связываем комнаты с работами
        StringBuilder sb = new StringBuilder();
        for (RoomData room : roomList) {
            sb.append("Комната: ").append(room.getRoomName()).append(" (").append(room.getRoomCode()).append(")\n");
            boolean found = false;
            for (WorkData work : workList) {
                if (work.getRoomCode().equals(room.getRoomCode())) {
                    sb.append("  - ").append(work.getWorkName())
                      .append(", тип: ").append(work.getWorkType())
                      .append(", цена: ").append(work.getPrice())
                      .append(" руб.\n");
                    found = true;
                }
            }
            if (!found) {
                sb.append("  - Нет работ\n");
            }
            sb.append("\n");
        }

        JTextArea area = new JTextArea(sb.toString());
        area.setEditable(false);
        area.setFont(new Font("Consolas", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(area);

        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
        setVisible(true);
    }
}