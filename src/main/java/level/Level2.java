package level;

import fight.enemy.meeple.Enemy;
import fight.Fight;
import fight.enemy.skillEnemy.Crowbar;
import fight.enemy.skillEnemy.Pistol;
import world.Level;
import world.Room;
import world.Account;

public class Level2 extends Level {
    private Room bowery;
    private Room lowerEastSide;
    private Room goblinHQ;
    private Room brooklynBridge;
    private Room futureBrooklynBridge;
    private Room futureBrooklyn;
    private Room futureQueens;
    private Room houseRuin;


    public Level2(Account myAccount) {
        super("Der Tag an dem wirklich alles begann!");
        setLevelRunning(true);
        setMyWorldPlayer(myAccount);
        setLevelDifficulty(3);
        createMap();
        goblinHQ.setEnemySpawn(100);
        addMob(new Enemy("Goblin Handlanger", 3, 1, 1, 2, 3, null));
        addMob(new Enemy("Schläger", 4, 1, 2, 1, 4, new Crowbar()));
        setBoss(new Enemy("Green Goblin", 10, 2, 2, 3, 8, new Pistol()));
        startLevel();
    }

    public void startLevel() {
        introMessage();
        setCurrentRoom(bowery);
    }

    public void createMap() {
        bowery = new Room("Bowery", 1);
        lowerEastSide = new Room("Lower East Side", 2);
        goblinHQ = new Room("Goblin HQ", 3);
        brooklynBridge = new Room("Brooklyn Bridge", 4);
        futureBrooklynBridge = new Room("Brooklyn Bridge", 5);
        futureBrooklyn = new Room("Brooklyn", 6);
        futureQueens = new Room("Queens", 7);
        houseRuin = new Room("Haus Ruine", 8);
        bowery.setNeighbors(null, lowerEastSide, null, null);
        lowerEastSide.setNeighbors(null, null, goblinHQ, lowerEastSide);
        goblinHQ.setNeighbors(lowerEastSide, null, brooklynBridge, null);
        brooklynBridge.setNeighbors(goblinHQ, null, null, null);
        brooklynBridge.setEvent(true);
        futureBrooklynBridge.setNeighbors(null, null, futureBrooklyn, null);
        futureBrooklyn.setNeighbors(futureBrooklynBridge, futureQueens, null, null);
        futureQueens.setNeighbors(houseRuin, null, null, futureBrooklyn);
        houseRuin.setNeighbors(null, null, futureQueens, null);
    }

    public void introMessage() {
        System.out.println("Redakteur: 'Aber Sir...?!");
        System.out.println("???: Das ist ungeheuerlich!! Ich bin die Güte in Person und womit wird es mir gedankt?");
        System.out.println("Redakteur: Mr. Jameson, ich bitte Sie!\n ich arbeite seit nun mehr 5 Jahren für Sie und habe noch nie auch nur einen Tag freigenommen und meiner Tochter heiratet doch kommendes Wochenende\n");
        //Usw.
    }

    @Override
    public void triggerEvent(int roomID) {
        switch (roomID) {
            case 4 -> {
                brooklynBridge.setEvent(false);
                houseRuin.setEvent(true);
                eventMessage1();
                new Fight(getMyWorldPlayer().getParty(), getBoss());
                eventMessage2();
                setCurrentRoom(futureBrooklynBridge);
            }
            case 8 -> {
                houseRuin.setEvent(false);
                eventMessage2();

            }
            case 10 -> {

                new Fight(getMyWorldPlayer().getParty(), getBoss());
                outroMessage();
            }
        }
    }

    public void eventMessage1() {
        System.out.println("'Ladys und Gentlemen endlich ist es soweit!\nDer Kampf auf den Sie alle gewartet haben!");
        System.out.println("'In der einen Ecke, der amtierende Champion: Ronan der Brecher!!'");
        System.out.println("*Die Menge jubelt*");
        System.out.println("'Und herausgefordert wird er von...'");
        System.out.println("*Der Moderator schaut dich etwas skeptisch an.*");
        System.out.println("'Hey Kleiner, wie ist dein Name?'");
        System.out.println("'Ähmmm...wie wärs mit >Die menschliche Spinne<!!!'");
        System.out.println("'Ernsthaft?! Die menschliche Spinne?!'");
        System.out.println("'...Spider-Man!!!'");
        System.out.println("*Stille*");
    }

    public void eventMessage2() {
        System.out.println("Eine Tür hat sich geöffnet");
    }

    public void outroMessage() {
        System.out.println("Du hast das Level geschafft und ein neues freigeschaltet, eyyyy");
        Level myLevel = new Level("Ist es ein Flugzeug?! Nein es ist...");
        getMyWorldPlayer().getUnlockedLevel().add(myLevel);
        setLevelRunning(false);
    }

}
