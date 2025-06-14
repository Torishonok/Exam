/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.exam;

/**
 *
 * @author vikus
 */
public class RoomData {
    private String roomCode;
    private String roomName;
    private String location;
    private double length;
    private double width;
    private double height;
    private double area;
    private double volume;
    private String wallMaterial;
    private String floorMaterial;
    private double contaminationAreaWall;
    private double contaminationDepthWall;
    private double contaminationAreaCeiling;
    private double contaminationDepthCeiling;
    private double contaminationAreaFloor;
    private double contaminationDepthFloor;
    private double radiationDoseRate;
    private double volumetricActivity;

    // Конструктор, геттеры и сеттеры
    public RoomData(String roomCode, String roomName, String location,
                    double length, double width, double height,
                    double area, double volume, String wallMaterial,
                    String floorMaterial, double contaminationAreaWall,
                    double contaminationDepthWall, double contaminationAreaCeiling,
                    double contaminationDepthCeiling, double contaminationAreaFloor,
                    double contaminationDepthFloor, double radiationDoseRate,
                    double volumetricActivity) {
        this.roomCode = roomCode;
        this.roomName = roomName;
        this.location = location;
        this.length = length;
        this.width = width;
        this.height = height;
        this.area = area;
        this.volume = volume;
        this.wallMaterial = wallMaterial;
        this.floorMaterial = floorMaterial;
        this.contaminationAreaWall = contaminationAreaWall;
        this.contaminationDepthWall = contaminationDepthWall;
        this.contaminationAreaCeiling = contaminationAreaCeiling;
        this.contaminationDepthCeiling = contaminationDepthCeiling;
        this.contaminationAreaFloor = contaminationAreaFloor;
        this.contaminationDepthFloor = contaminationDepthFloor;
        this.radiationDoseRate = radiationDoseRate;
        this.volumetricActivity = volumetricActivity;
    }

    // Геттеры
    public String getRoomCode() { return roomCode; }
    public String getRoomName() { return roomName; }
    public String getLocation() { return location; }
    public double getLength() { return length; }
    public double getWidth() { return width; }
    public double getHeight() { return height; }
    public double getArea() { return area; }
    public double getVolume() { return volume; }
    public String getWallMaterial() { return wallMaterial; }
    public String getFloorMaterial() { return floorMaterial; }
    public double getContaminationAreaWall() { return contaminationAreaWall; }
    public double getContaminationDepthWall() { return contaminationDepthWall; }
    public double getContaminationAreaCeiling() { return contaminationAreaCeiling; }
    public double getContaminationDepthCeiling() { return contaminationDepthCeiling; }
    public double getContaminationAreaFloor() { return contaminationAreaFloor; }
    public double getContaminationDepthFloor() { return contaminationDepthFloor; }
    public double getRadiationDoseRate() { return radiationDoseRate; }
    public double getVolumetricActivity() { return volumetricActivity; }
}
