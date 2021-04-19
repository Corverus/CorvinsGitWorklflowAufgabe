package world;

import java.util.*;

import fight.enemy.meeple.Enemy;
import fight.Fight;
import level.*;

public class Level {
    //IDs
    private static final int SPIDERMAN_LEVEL1_ID = 1;
    private static final int SPIDERMAN_LEVEL2_ID = 2;

    private static final String USER_MOVE_DIRECTION_REQUEST = "Wohin gehst du?";
    private static final int MOVE_SELECT = 1;
    private static final int EXAMINE_SELECT = 2;
    private static final String MOVE_NORTH = "N";
    private static final String MOVE_EAST = "O";
    private static final String MOVE_SOUTH = "S";
    private static final String MOVE_WEST = "W";
    public static final String WRONG_USER_INPUT = "Ungültige Eingabe";
    private static final String NO_EXIT = "Sorry, aber da ist eine Wand";
    private static final String USER_OPTIONS_LEVEL = "Was möchtest du tun?\n1:Bewegen    2:Untersuchen";

    private Account myAccount;
    private int levelDifficulty;
    private String name;
    private Room currentRoom;
    private List<Enemy> possibleMobs = new ArrayList();
    private List<Enemy> boss = new ArrayList();
    private boolean levelRunning;

    public Level(String name) {
        this.name = name;
    }

    public Level generateLevel(int levelID) {
        return switch (levelID) {
            case SPIDERMAN_LEVEL1_ID -> new Level1(getMyWorldPlayer());
            case SPIDERMAN_LEVEL2_ID -> new Level2(getMyWorldPlayer());
            default -> null;
        };
    }

    public void playerOptionsWorld() throws InputMismatchException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println(USER_OPTIONS_LEVEL);
            int userInputAction = scanner.nextInt();
            switch (userInputAction) {
                case MOVE_SELECT -> {
                    System.out.println(USER_MOVE_DIRECTION_REQUEST);
                    System.out.println(getCurrentRoom().exitString());
                    String userInputMove = scanner.next();
                    move(userInputMove);
                }
                case EXAMINE_SELECT -> examine();
                default -> System.out.println(WRONG_USER_INPUT);
            }
        }catch(InputMismatchException wrongUserInput){
            System.out.println(WRONG_USER_INPUT);
        }
    }

    public void move(String direction) throws InputMismatchException {
        try {
            Room targetRoom;
            switch (direction.toUpperCase()) {
                case MOVE_NORTH -> targetRoom = getCurrentRoom().getNeighborNorth();
                case MOVE_EAST -> targetRoom = getCurrentRoom().getNeighborEast();
                case MOVE_SOUTH -> targetRoom = getCurrentRoom().getNeighborSouth();
                case MOVE_WEST -> targetRoom = getCurrentRoom().getNeighborWest();
                default -> {
                    System.out.println(WRONG_USER_INPUT + " Versuche: n,o,s,w");
                    return;
                }
            }
            if (targetRoom != null) {
                setCurrentRoom(targetRoom);
                triggerMobFight(4);
            } else {
                System.out.println(NO_EXIT);
            }
        } catch (InputMismatchException wrongUserInput) {
            System.out.println(WRONG_USER_INPUT + " Versuche: n,o,s,w");
        }
    }

    public void examine() {
        if (getCurrentRoom().isEvent()) {
            triggerEvent(getCurrentRoom().getRoomID());
        } else {
            System.out.println("Du befindest dich hier: " + getCurrentRoom().getDescription());
        }
    }

    public void triggerEvent(int RoomID) {

    }

    public void addMob(Enemy mob) {
        possibleMobs.add(mob);
    }

    public void setBoss(Enemy bossEnemy) {
        bossEnemy.setLevel(getLevelDifficulty());
        boss.add(bossEnemy);
    }

    public void triggerMobFight(int numberOfEnemys) {
        if (currentRoom.isEnemySpawn() && currentRoom.isFight()) {
            List<Enemy> currentMobs = new ArrayList();
            for (int i = 0; i < numberOfEnemys; i++) {
                Enemy myEnemy = new Enemy();
                myEnemy.setValues(getRandomMob());
                myEnemy.setLevel(getLevelDifficulty());
                currentMobs.add(myEnemy);
            }
            Fight myFight = new Fight(getMyWorldPlayer().getParty(), currentMobs);
            getMyWorldPlayer().setParty(myFight.getPlayers());
        }
    }

    public String getName() {
        return name;
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
    }

    public Enemy getRandomMob() {
        int min = 0;
        int max = possibleMobs.size() - 1;
        Random number = new Random();
        int randomNumber = number.nextInt((max - min) + 1) + min;
        return possibleMobs.get(randomNumber);
    }

    public void setName(String name) {
        this.name = name;
    }

    public Enemy getBossEnemy() {
        return boss.get(0);
    }

    public List<Enemy> getBoss() {
        return boss;
    }

    public int getLevelDifficulty() {
        return levelDifficulty;
    }

    public void setLevelDifficulty(int levelDifficulty) {
        this.levelDifficulty = levelDifficulty;
    }

    public Account getMyWorldPlayer() {
        return myAccount;
    }

    public void setMyWorldPlayer(Account myPlayer) {
        this.myAccount = myPlayer;
    }

    public List<Enemy> getMobs() {
        return possibleMobs;
    }

    public boolean isLevelRunning() {
        return levelRunning;
    }

    public void setLevelRunning(boolean levelRunning) {
        this.levelRunning = levelRunning;
    }
}
