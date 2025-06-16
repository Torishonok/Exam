/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.exam;

/**
 *
 * @author vikus
 */
public class RoomWorkData {
    private final String roomCode;
    private final double mdi; 
    private final boolean isFirstFloor;

    public RoomWorkData(String code, double mdi, boolean isFirstFloor) {
        this.roomCode = code;
        this.mdi = mdi;
        this.isFirstFloor = isFirstFloor;
    }

    public String getRoomCode() { return roomCode; }
    public double getMdi() { return mdi; }
    public boolean isFirstFloor() { return isFirstFloor; }
}
