package world;

import fight.player.meeple.Player;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Hub {
    private final Account myAccount;
    private static final String WRONG_USER_INPUT = "Ungültige Eingabe";
    private static final String USER_OPTIONS_HUB = "1:Wähle ein Level     2:Team bearbeiten\n3:Schlafen gehen.      4:Spiel beenden";
    private static final String UNLOCKED_CHARACTERS = "Wen möchtest du deinem Team hinzufügen?\nDies sind deine freigeschalteten Charaktere:\n";
    private static final int LEVEL_SELECT = 1;
    private static final int CHANGE_TEAM = 2;
    private static final int SAFE_GAME = 3;
    private static final int END_GAME = 4;
    private Level myLevel;
    private boolean runningHub;

    public Hub(Account myPlayer) {
        this.myAccount = myPlayer;
        setRunningHub(true);
    }

    public void getPlayerOptionsHub() throws InputMismatchException {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Was möchtest du tun?\n" + USER_OPTIONS_HUB);
            int menuOptions = scanner.nextInt();
            switch (menuOptions) {
                case LEVEL_SELECT -> selectLevel();
                case CHANGE_TEAM -> changeTeam();
                case SAFE_GAME -> {
                    getMyAccount().saveData();
                    getMyAccount().loadData();
                }
                case END_GAME -> {
                    setRunningHub(false);
                }
                default -> System.out.println(WRONG_USER_INPUT);
            }
        } catch (InputMismatchException wrongUserInput) {
            System.out.println(WRONG_USER_INPUT);
        }
    }

    public void selectLevel() throws InputMismatchException {
        try {
            for (int i = 0; i < myAccount.getUnlockedLevel().size(); i++) {
                System.out.println((i + 1) + ": " + myAccount.getUnlockedLevel().get(i).getName());
            }
            Scanner levelSelectScanner = new Scanner(System.in);
            int levelOptions = levelSelectScanner.nextInt() - 1;
            this.myLevel = myAccount.getUnlockedLevel().get(levelOptions).generateLevel(levelOptions + 1);
            setRunningHub(false);
        } catch (InputMismatchException wrongUserInput) {
            System.out.println(WRONG_USER_INPUT);
        }
    }

    public void changeTeam() throws IndexOutOfBoundsException, InputMismatchException {
        try {
            Scanner changeTeamScanner = new Scanner(System.in);
            List<Player> myList;
            myList = myAccount.getParty();
            myAccount.getParty().clear();
            int i;
            int k = 0;
            System.out.println(UNLOCKED_CHARACTERS);
            for (i = 0; i < myAccount.getUnlockedCharacters().size(); i++) {
                System.out.println((i + 1) + ":" + myAccount.getUnlockedCharacters().get(i).getName() + "\n");
            }
            while (i > 0 && k < 4) {
                int teamOptions = changeTeamScanner.nextInt() - 1;
                myAccount.getParty().add(myAccount.getUnlockedCharacters().get(teamOptions));
                i--;
                k++;
            }
            if (i == 0 || k == 4) {
                System.out.println("Ist dies dein Team?\n");
                for (Player player : myAccount.getParty()) {
                    System.out.println(player.getName());
                }
                System.out.println("\n1:Ja  2:Nein");
                int confirm = changeTeamScanner.nextInt();
                if (confirm == 1) {
                    System.out.println("Das Team wurde gespeichert.\n");
                }
                if (confirm == 2) {
                    myAccount.getParty().clear();
                    myAccount.setParty(myList);
                } else {
                    System.out.println(WRONG_USER_INPUT);
                }
            }
        } catch (IndexOutOfBoundsException | InputMismatchException wrongUserInput) {
            System.out.println(WRONG_USER_INPUT);
            changeTeam();
        }
    }

    public boolean isRunningHub() {
        return runningHub;
    }

    public void setRunningHub(boolean runningHub) {
        this.runningHub = runningHub;
    }

    public Account getMyAccount() {
        return myAccount;
    }

    public Level getMyLevel() {
        return myLevel;
    }
}
