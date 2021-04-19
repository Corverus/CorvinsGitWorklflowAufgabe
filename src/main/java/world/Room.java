package world;

import java.util.Random;

public class Room {
    private String description;
    private Room neighborNorth;
    private Room neighborEast;
    private Room neighborSouth;
    private Room neighborWest;
    private boolean enemySpawn;
    private int roomID;
    private boolean event;
    private int enemySpawnChance;

    public Room(String description, int roomID){
        this.roomID = roomID;
        this.description = description;
    }

    public String exitString() {
        String exitString = "Ausgänge:\n";
        if (neighborNorth != null) {
            exitString += "Im Norden liegt " + neighborNorth.getDescription() + "\n";
        }
        if (neighborEast != null) {
            exitString += "Im Osten liegt " + neighborEast.getDescription() + "\n";
        }
        if (neighborSouth != null) {
            exitString += "Im Süden liegt " + neighborSouth.getDescription() + "\n";
        }
        if (neighborWest != null) {
            exitString += "Im Westen liegt " + neighborWest.getDescription() + "\n";
        }
        return exitString;
    }

    public boolean isFight(){
        int min = 1;
        int max = 100;
        Random number = new Random();
        int randomNumber = number.nextInt((max - min) + 1) + min;
        return getEnemySpawnChance() >= randomNumber;
    }

    public void setNeighbors(Room neighborNorth, Room neighborEast, Room neighborSouth, Room neighborWest) {
        this.neighborNorth = neighborNorth;
        this.neighborEast = neighborEast;
        this.neighborSouth = neighborSouth;
        this.neighborWest = neighborWest;
    }

    public String getDescription() {
        return description;
    }

    public Room getNeighborNorth() {
        return neighborNorth;
    }

    public Room getNeighborEast() {
        return neighborEast;
    }

    public Room getNeighborSouth() {
        return neighborSouth;
    }

    public Room getNeighborWest() {
        return neighborWest;
    }

    public boolean isEnemySpawn() {
        return enemySpawn;
    }

    public void setEnemySpawn(int enemySpawnChance) {
        this.enemySpawn = true;
        this.enemySpawnChance = enemySpawnChance;
    }

    public boolean isEvent() {
        return event;
    }

    public void setEvent(boolean event) {
        this.event = event;
    }

    public int getEnemySpawnChance() {
        return enemySpawnChance;
    }

    public int getRoomID() {
        return roomID;
    }
}
