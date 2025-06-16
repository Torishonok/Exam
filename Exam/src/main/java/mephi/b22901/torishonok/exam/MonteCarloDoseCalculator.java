/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.exam;

/**
 *
 * @author vikus
 */
import java.awt.Color;
import org.apache.commons.math3.distribution.NormalDistribution;

import java.util.List;

import org.apache.commons.math3.distribution.NormalDistribution;
import javax.swing.*;
import java.util.List;

public class MonteCarloDoseCalculator {

    private static final int EXPERIMENTS = 10_000;
    private final List<WorkData> workList;
    private final List<RoomData> roomList;

    public MonteCarloDoseCalculator(List<WorkData> workList, List<RoomData> roomList) {
        this.workList = workList;
        this.roomList = roomList;
    }

    public void runSimulation() {
        double[] collectiveDoses = new double[EXPERIMENTS];
        double[] individualDoses = new double[EXPERIMENTS];

        for (int i = 0; i < EXPERIMENTS; i++) {
            double totalCollectiveDose = 0;
            int totalWorkers = 0;

            for (WorkData work : workList) {
                RoomData room = findRoomByCode(roomList, work.getRoomCode());
                if (room == null) continue;

                double baseMdi = room.getRadiationDoseRate();
                double sigma = room.getLocation().contains("Этаж 1") ? baseMdi * 0.05 : baseMdi * 0.10;
                double randomMdi = generateNormalRandom(baseMdi, sigma);

                double hv = work.getTimeNorm();
                int kr = work.getWorkersCount();
                double pr = IntegralCalculator.getWorkArea(work, room);
                double gr = IntegralCalculator.getAverageContaminationDepth(room, work);
                String workType = work.getWorkType().trim().toLowerCase();

                double time;
                if ("поверхностная".equalsIgnoreCase(workType)) {
                    time = hv / kr * pr;
                } else if ("скол".equalsIgnoreCase(workType)) {
                    int ceilDepth = (int) Math.ceil(gr / 10.0);
                    time = hv / kr * pr * ceilDepth;
                } else {
                    continue;
                }

                double kdo = randomMdi * time * kr;
                double ido = kdo / kr;

                totalCollectiveDose += kdo;
                totalWorkers += kr;
            }

            collectiveDoses[i] = totalCollectiveDose;
            individualDoses[i] = totalWorkers > 0 ? totalCollectiveDose / totalWorkers : 0;
        }

        // Показываем гистограммы
        HistogramWindow.showHistogram(collectiveDoses, "Коллективная доза", "Доза (мкЗв)", new Color(249, 113, 24));
        HistogramWindow.showHistogram(individualDoses, "Индивидуальная доза", "Доза (мкЗв)", new Color(16, 185, 129));
    }

    private double generateNormalRandom(double mean, double stdDev) {
        NormalDistribution nd = new NormalDistribution(mean, stdDev);
        return nd.sample();
    }

    private RoomData findRoomByCode(List<RoomData> rooms, String code) {
        for (RoomData r : rooms) {
            if (r.getRoomCode().equals(code)) {
                return r;
            }
        }
        return null;
    }
}