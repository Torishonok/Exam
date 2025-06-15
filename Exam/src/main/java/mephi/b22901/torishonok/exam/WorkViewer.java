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
import java.util.List;

public class WorkViewer extends JFrame {

    public WorkViewer() {
        setTitle("Проводимые работы");
        setSize(1200, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        List<WorkData> workList = WorkLoader.getWorkDataList();

        if (workList == null || workList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Нет данных для отображения.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Object[][] tableData = workList.stream()
                .map(data -> new Object[]{
                        data.getNumber(),
                        data.getRoomCode(),
                        data.getRoomName(),
                        data.getPart(),
                        data.getElementCode(),
                        data.getWorkName(),
                        data.getDescription(),
                        data.getWorkType(),
                        data.getPrice(),
                        data.getPriority(),
                        data.getTimeNorm(),
                        data.getWorkersCount()
                })
                .toArray(Object[][]::new);

        String[] columns = {
                "№", "Код помещения", "Помещение", "Часть", "Код элемента",
                "Наименование работы", "Описание", "Тип работы", "Цена",
                "Очерёдность", "Норма времени", "Число рабочих"
        };

        JTable table = new JTable(tableData, columns);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);
        setVisible(true);
    }
}
