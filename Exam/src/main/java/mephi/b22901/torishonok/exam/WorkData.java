/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.exam;

/**
 *
 * @author vikus
 */
public class WorkData {
    private final int number;
    private final String roomCode;
    private final String roomName;
    private final String part;
    private final String elementCode;
    private final String workName;
    private final String description;
    private final String workType;
    private final double price;
    private final int priority;
    private final int timeNorm;
    private final int workersCount;

    public WorkData(int number, String roomCode, String roomName, String part, String elementCode,
                    String workName, String description, String workType, double price,
                    int priority, int timeNorm, int workersCount) {
        this.number = number;
        this.roomCode = roomCode;
        this.roomName = roomName;
        this.part = part;
        this.elementCode = elementCode;
        this.workName = workName;
        this.description = description;
        this.workType = workType;
        this.price = price;
        this.priority = priority;
        this.timeNorm = timeNorm;
        this.workersCount = workersCount;
    }

    // Геттеры
    public int getNumber() { return number; }
    public String getRoomCode() { return roomCode; }
    public String getRoomName() { return roomName; }
    public String getPart() { return part; }
    public String getElementCode() { return elementCode; }
    public String getWorkName() { return workName; }
    public String getDescription() { return description; }
    public String getWorkType() { return workType; }
    public double getPrice() { return price; }
    public int getPriority() { return priority; }
    public int getTimeNorm() { return timeNorm; }
    public int getWorkersCount() { return workersCount; }
}
