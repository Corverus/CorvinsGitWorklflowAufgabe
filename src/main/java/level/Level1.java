package level;

import fight.enemy.meeple.Enemy;
import fight.Fight;
import fight.enemy.skillEnemy.Crowbar;
import fight.enemy.skillEnemy.Pistol;
import world.Level;
import world.Room;
import world.Account;

public class Level1 extends Level {
    private Room esu;
    private Room secondAvenue;
    private Room christopherStreet;
    private Room joesArena;
    private Room thirdAvenue;
    private Room beverlySquare;
    private Room ernieStreet;
    private Room home;
    private Room dunkleGasse;
    private Room lagerhaus;


    public Level1(Account myAccount) {
        super("Der Tag an dem alles begann!");
        setLevelRunning(true);
        setMyWorldPlayer(myAccount);
        setLevelDifficulty(1);
        createMap();
        dunkleGasse.setEnemySpawn(100);
        addMob(new Enemy("Verbrecher",3,1,1,2,3,null));
        addMob(new Enemy("Schläger",4,1,2,1,4, new Crowbar()));
        setBoss(new Enemy("Einbrecher", 10, 2, 2, 3, 8, new Pistol()));
        startLevel();
    }

    public void startLevel(){
        introMessage();
        setCurrentRoom(esu);
    }

    public void createMap() {
        esu = new Room("ESU", 1);
        secondAvenue = new Room("Second Avenue", 2);
        christopherStreet = new Room("Christopher Street", 3);
        joesArena = new Room("Joe's Arena", 4);
        thirdAvenue = new Room("Third Avenue", 5);
        beverlySquare = new Room("Beverly Square", 6);
        ernieStreet = new Room("Ernie Street", 7);
        home = new Room("Zuhause", 8);
        dunkleGasse = new Room("dunkle Gasse", 9);
        lagerhaus = new Room("Lagerhaus", 10);
        esu.setNeighbors(null,null,secondAvenue,null);
        secondAvenue.setNeighbors(esu,christopherStreet,null,null);
        christopherStreet.setNeighbors(null,null,joesArena,secondAvenue);
        joesArena.setNeighbors(christopherStreet,null,null,null);
        joesArena.setEvent(true);
    }

    public void introMessage() {
        System.out.println("'Ey Parker, Fang!!'");
        System.out.println("Du siehst ein Football direkt auf dich zufliegen!");
        System.out.println("Bevor du realisierst was gerade geschehen ist hörst du einen aus dem Team schon glucksen:");
        System.out.println("'Verdammt Flash, mit dem Wurf hättest du den Bücherwurm geradewegs köpfen können!'");
        System.out.println("'Zum Glück ja nicht' entgegnet Flash und fährt nach einer kurzen Pause fort:\n'Ich mein die Bohnenstange ist ein deutlich besseres Ziel um meine Genauigkeit zu trainieren als die fette Berta!'");
        System.out.println("*schallendes Gelächter*");
        System.out.println("'Was für Arschlöcher!' denkst du dir während du dich aufrappelst");
        System.out.println("'Wenn die wüssten mit welcher Leichtigkeit ich Ihnen ihr blödes Grinsen aus dem Gesicht wischen könnte");
        System.out.println("'Na gut es wäre nicht immer so leicht gewesen, aber seit diesem Vorfall letzte Woche'...");
        System.out.println("'Oh verdammt, fasst hätte ich es vergessen, heute ist ja der Kampf'");
    }

    @Override
    public void triggerEvent(int roomID) {
        switch (roomID) {
            case 4 -> {
                joesArena.setEvent(false);
                eventMessage1();
                secondAvenue.setNeighbors(esu, christopherStreet, null, thirdAvenue);
                thirdAvenue.setNeighbors(null, secondAvenue, beverlySquare, null);
                beverlySquare.setNeighbors(thirdAvenue, ernieStreet, null, null);
                ernieStreet.setNeighbors(null, null, home, beverlySquare);
                home.setNeighbors(ernieStreet, null, null, null);
                home.setEvent(true);
            }
            case 8 -> {
                home.setEvent(false);
                eventMessage2();
                beverlySquare.setNeighbors(thirdAvenue, ernieStreet, null, dunkleGasse);
                dunkleGasse.setNeighbors(null, beverlySquare, null, lagerhaus);
                lagerhaus.setNeighbors(null, dunkleGasse, null, null);
                lagerhaus.setEvent(true);
            }
            case 10 -> {
                lagerhaus.setEvent(false);
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
