/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package mephi.b22901.torishonok.exam;

import javax.swing.SwingUtilities;

/**
 *
 * @author vikus
 */
public class Exam {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            boolean loaded = ExcelLoader.loadFileAutomatically();
            new WelcomeWindow(loaded);
        });
    }
}
