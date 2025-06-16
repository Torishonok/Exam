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
    private final String roomCode;
    private final String roomName;
    private final String location;

    private final double length;
    private final double width;
    private final double height;
    private final double area;
    private final double volume;

    private final String wallMaterial;
    private final String floorMaterial;

    private final String wallCovering;
    private final double wallCoveringArea;

    private final String ceilingCovering;
    private final double ceilingCoveringArea;

    private final String floorCovering;
    private final double floorCoveringArea;

    private final double contaminationWallArea;
    private final double contaminationWallDepth;

    private final double contaminationCeilingArea;
    private final double contaminationCeilingDepth;

    private final double contaminationFloorArea;
    private final double contaminationFloorDepth;

    private final double radiationDoseRate;
    private final double volumetricActivity;

    public RoomData(String roomCode, String roomName, String location,
                    double length, double width, double height,
                    double area, double volume,
                    String wallMaterial, String floorMaterial,
                    String wallCovering, double wallCoveringArea,
                    String ceilingCovering, double ceilingCoveringArea,
                    String floorCovering, double floorCoveringArea,
                    double contaminationWallArea, double contaminationWallDepth,
                    double contaminationCeilingArea, double contaminationCeilingDepth,
                    double contaminationFloorArea, double contaminationFloorDepth,
                    double radiationDoseRate, double volumetricActivity) {
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
        this.wallCovering = wallCovering;
        this.wallCoveringArea = wallCoveringArea;
        this.ceilingCovering = ceilingCovering;
        this.ceilingCoveringArea = ceilingCoveringArea;
        this.floorCovering = floorCovering;
        this.floorCoveringArea = floorCoveringArea;
        this.contaminationWallArea = contaminationWallArea;
        this.contaminationWallDepth = contaminationWallDepth;
        this.contaminationCeilingArea = contaminationCeilingArea;
        this.contaminationCeilingDepth = contaminationCeilingDepth;
        this.contaminationFloorArea = contaminationFloorArea;
        this.contaminationFloorDepth = contaminationFloorDepth;
        this.radiationDoseRate = radiationDoseRate;
        this.volumetricActivity = volumetricActivity;
    }


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

    public String getWallCovering() { return wallCovering; }
    public double getWallCoveringArea() { return wallCoveringArea; }

    public String getCeilingCovering() { return ceilingCovering; }
    public double getCeilingCoveringArea() { return ceilingCoveringArea; }

    public String getFloorCovering() { return floorCovering; }
    public double getFloorCoveringArea() { return floorCoveringArea; }

    public double getContaminationWallArea() { return contaminationWallArea; }
    public double getContaminationWallDepth() { return contaminationWallDepth; }

    public double getContaminationCeilingArea() { return contaminationCeilingArea; }
    public double getContaminationCeilingDepth() { return contaminationCeilingDepth; }

    public double getContaminationFloorArea() { return contaminationFloorArea; }
    public double getContaminationFloorDepth() { return contaminationFloorDepth; }

    public double getRadiationDoseRate() { return radiationDoseRate; }
    public double getVolumetricActivity() { return volumetricActivity; }
}