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
                "Название",
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

        add(panel);
        setVisible(true);
    }
}
