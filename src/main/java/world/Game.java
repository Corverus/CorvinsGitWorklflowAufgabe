package world;

import fight.player.meeple.SpiderMan;

public class Game {
    private Account myAccount;
    private Level myLevel;
    private Hub myHub;

    public static void main(String[] args) {
        new Game();
    }

    public Game() {
        initialize();
        while (myHub.isRunningHub()) {
            run();
        }
    }

    private void run() {
        while (myHub.isRunningHub()) {
            myHub.getPlayerOptionsHub();
        }
        myAccount = myHub.getMyAccount();
        if (myHub.getMyLevel() != null) {
            myLevel = myHub.getMyLevel();
            myLevel.setMyWorldPlayer(myHub.getMyAccount());
            while (myLevel.isLevelRunning()) {
                myLevel.playerOptionsWorld();
            }
            myAccount = myLevel.getMyWorldPlayer();
            myHub.setRunningHub(true);
        }
    }

    private void initialize() {
        myAccount = new Account(new SpiderMan(), "Bruno");
        myHub = new Hub(this.myAccount);
        myLevel = new Level("Der Tag an dem alles begann!");
        myAccount.getUnlockedLevel().add(myLevel);
    }
}
