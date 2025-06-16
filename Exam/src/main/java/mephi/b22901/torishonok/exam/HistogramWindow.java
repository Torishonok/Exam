/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.exam;

/**
 *
 * @author vikus
 */


import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import javax.swing.*;
import java.awt.*;

public class HistogramWindow extends JFrame {

    public static void showHistogram(double[] data, String title, String xAxisLabel, Color color) {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.FREQUENCY);
        dataset.addSeries(title, data, 50); 

        JFreeChart chart = ChartFactory.createHistogram(
                title,
                xAxisLabel,
                "Частота",
                dataset
        );

        chart.getXYPlot().getDomainAxis().setTickLabelPaint(color);
        chart.getXYPlot().getRangeAxis().setTickLabelPaint(color);


        chart.getXYPlot().getDomainAxis().setAxisLinePaint(Color.GRAY);
        chart.getXYPlot().getRangeAxis().setAxisLinePaint(Color.GRAY);

        ChartPanel panel = new ChartPanel(chart);
        panel.setPreferredSize(new Dimension(800, 600));

        JFrame frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
