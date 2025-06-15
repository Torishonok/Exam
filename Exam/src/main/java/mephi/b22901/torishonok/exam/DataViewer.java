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
import java.util.List;

public class DataViewer extends JFrame {

    public DataViewer() {
        setTitle("Данные по помещениям");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        List<RoomData> dataList = ExcelLoader.getRoomDataList();

        if (dataList == null || dataList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Нет данных для отображения.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[][] tableData = dataList.stream()
                .map(data -> new Object[]{
                        data.getRoomCode(),
                        data.getRoomName(),
                        data.getLocation(),
                        data.getLength(),
                        data.getWidth(),
                        data.getHeight(),
                        data.getArea(),
                        data.getWallMaterial(),
                        data.getFloorMaterial(),
                        data.getRadiationDoseRate(),
                        data.getVolumetricActivity()
                })
                .toArray(Object[][]::new);

        String[] columns = {
                "Код помещения",
                "Помещение",
                "Расположение",
                "Длина",
                "Ширина",
                "Высота",
                "Площадь",
                "Материал стен",
                "Материал пола",
                "Мощность дозы",
                "Объемная активность"
        };

        JTable table = new JTable(tableData, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton nextButton = new JButton("Продолжить");
        nextButton.setPreferredSize(new Dimension(200, 40));
        nextButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        nextButton.setBackground(new Color(16, 185, 129));
        nextButton.setForeground(Color.WHITE);
        nextButton.setFocusPainted(false);
        nextButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        nextButton.addActionListener(e -> new ProjectActionWindow(new AppTheme(
                new Color(16, 185, 129),
                new Color(5, 150, 105),
                new Color(230, 252, 236),
                new Color(200, 240, 215)
        )));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(nextButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }
}