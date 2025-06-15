/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.exam;

/**
 *
 * @author vikus
 */
import java.awt.*;

import java.awt.Color;

public class AppTheme {
    private final Color primaryColor;
    private final Color primaryHover;
    private final Color backgroundTop;
    private final Color backgroundBottom;

    public AppTheme(Color primaryColor, Color primaryHover, Color backgroundTop, Color backgroundBottom) {
        this.primaryColor = primaryColor;
        this.primaryHover = primaryHover;
        this.backgroundTop = backgroundTop;
        this.backgroundBottom = backgroundBottom;
    }

    public Color getPrimaryColor() { return primaryColor; }
    public Color getPrimaryHover() { return primaryHover; }
    public Color getBackgroundTop() { return backgroundTop; }
    public Color getBackgroundBottom() { return backgroundBottom; }
}