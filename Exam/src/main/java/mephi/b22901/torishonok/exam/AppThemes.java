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

public class AppThemes {

    // Зелёная тема (по умолчанию)
    public static final AppTheme GREEN = new AppTheme(
            new Color(16, 185, 129),   // PRIMARY_COLOR
            new Color(5, 150, 105),    // PRIMARY_HOVER
            new Color(230, 252, 236),  // BACKGROUND_TOP
            new Color(200, 240, 215)   // BACKGROUND_BOTTOM
    );

    // Оранжевая тема (радиационный расчёт)
    public static final AppTheme ORANGE = new AppTheme(
            new Color(249, 113, 24),
            new Color(234, 88, 12),
            new Color(255, 247, 237),
            new Color(255, 228, 194)
    );

    // Жёлтая тема (экономический расчёт)
    public static final AppTheme YELLOW = new AppTheme(
            new Color(250, 204, 21),
            new Color(234, 179, 8),
            new Color(255, 251, 235),
            new Color(254, 243, 199)
    );
}
