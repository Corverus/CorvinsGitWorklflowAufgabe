package fight;

import fight.enemy.meeple.Enemy;
import fight.player.meeple.Player;

import java.util.*;

public class Fight {
    private static final String PLAYER_TURN_OPTIONS = "1:Angreifen    2:Fähigkeiten\n";
    private static final String ENEMY_TARGET_BLOCK = " hat deinen Angriff abgewehrt, doch du konntest seine Verteidigung schwächen.\n";
    private static final String ENEMY_TARGET_DODGE = " ist deinem Angriff gekonnt ausgewichen.\n";
    private static final String WRONG_USER_INPUT = "Ungültige Eingabe\n";
    private static final int PLAYER_TURN_ATTACK = 1;
    private static final int PLAYER_TURN_SKILL = 2;
    private Enemy enemysBoss;
    private List<Player> players;
    private List<Enemy> enemys;
    private int round;
    private int xpReward;
    private List<Meeple> queue;

    public Fight(List<Player> players, List<Enemy> enemys) {
        initializeFight(players, enemys);
        introMessage();
        while (!isFightOver()) {
            fighting();
        }
    }


    public void initializeFight(List<Player> players, List<Enemy> enemys) {
        setRound(0);
        this.players = players;
        this.enemys = enemys;
        calculateXpReward();
        enemysBoss = enemys.get(0);
    }

    public void introMessage() {
        if (enemys.size() == 1) {
            System.out.println("Es beginnt ein Kampf mit " + enemys.get(0).getName() + "\n");
        } else {
            System.out.println("Es beginnt ein Kampf mit " + enemys.get(0).getName() + " und seinen Männern.\n");
        }
    }

    public boolean isFightOver() {
        if (queue != null) {
            int playerDeathCount = 0;
            int enemyDeathCount = 0;
            for (Meeple anyMeeple : queue) {
                if (!anyMeeple.checkAlive(false) && anyMeeple.isPlayer()) {
                    playerDeathCount++;
                }
                if (!anyMeeple.checkAlive(false) && !anyMeeple.isPlayer()) {
                    enemyDeathCount++;
                }
            }
            if (playerDeathCount == players.size()) {
                System.out.println("Game Over!");
                return true;
            }
            if (enemyDeathCount == enemys.size()) {
                System.out.println("Du hast den Kampf gegen " + enemysBoss.getName() + " gewonnen!\n");
                for (Player myPlayer : players) {
                    decreaseAllCooldown(5);
                    myPlayer.setXp(myPlayer.getXp() + xpReward);
                    myPlayer.levelUp();
                }
                return true;
            }
        }
        return false;
    }


    public void fighting() {
        generateQueue();
        round++;
        decreaseAllCooldown(1);
        for (Meeple myMeeple : queue) {
            checkCondition(myMeeple);
            if (myMeeple.checkAlive(false)) {
                applyTurn(myMeeple);
                if (isFightOver()) {
                    break;
                }
            }
        }
    }

    public void generateQueue() {
        queue = new ArrayList<>();
        queue.addAll(players);
        queue.addAll(enemys);
        queue.sort((o1, o2) -> Integer.compare(o2.getSpeed(), o1.getSpeed()));
    }

    public void applyTurn(Meeple myMeeple) {
        if (myMeeple.getMyConditions().contains(Meeple.Condition.STUNNED)) {
            System.out.println(myMeeple.getName() + " ist betäubt und kann sich nicht bewegen.\n");
        } else if (myMeeple.getMyConditions().contains(Meeple.Condition.COCOONED)) {
            System.out.println(myMeeple.getName() + " ist eingesponnen und kann sich nicht bewegen.\n");
        } else {
            if (myMeeple.isPlayer()) {
                playerTurn(myMeeple);
            } else {
                enemyTurn(myMeeple);
            }
        }
    }


    //Player Turn
    public void playerTurn(Meeple myMeeple) throws IndexOutOfBoundsException, InputMismatchException {
        try {
            Scanner scanner = new Scanner(System.in);
            turnIntroMessage(myMeeple);
            System.out.println(PLAYER_TURN_OPTIONS);
            int playerTurnInput = scanner.nextInt();
            switch (playerTurnInput) {
                case PLAYER_TURN_ATTACK -> {
                    showEnemyTargets();
                    int playerInputAttack = scanner.nextInt() - 1;
                    if (!enemys.get(playerInputAttack).isAlive()) {
                        System.out.println("Dieser Gegner wurde schon besiegt.\n");
                        playerTurn(myMeeple);
                    } else {
                        playerAttack(myMeeple, enemys.get(playerInputAttack));
                        enemys.get(playerInputAttack).checkAlive(true);
                    }
                }
                case PLAYER_TURN_SKILL -> {
                    showPlayerSkills(myMeeple);
                    int playerInputChooseSkill = scanner.nextInt() - 1;
                    Skill activeSkill = myMeeple.getMySkills().get(playerInputChooseSkill);
                    if (!activeSkill.isReady()) {
                        System.out.println("Diese Fähigkeit ist noch nicht bereit.\nAbklingzeit: " + (activeSkill.getCooldown() - activeSkill.getCurrentCooldown()) + " Runden\n");
                        playerTurn(myMeeple);
                    } else {
                        if (!activeSkill.isFriendlyFire()) {
                            showEnemyTargets();
                            int playerInputSkill = scanner.nextInt() - 1;
                            Enemy target = enemys.get(playerInputSkill);
                            if (!target.isAlive()) {
                                System.out.println("Dieser Gegner wurde schon besiegt.\n");
                                playerTurn(myMeeple);
                                break;
                            }
                            activeSkill.activate(myMeeple, target);
                            target.checkAlive(true);
                        } else {
                            showPlayerTargets();
                            int playerInputSkillFriendlyFire = scanner.nextInt() - 1;
                            Player target = players.get(playerInputSkillFriendlyFire);
                            if (!target.isAlive()) {
                                System.out.println("Dieser Spieler wurde schon besiegt.\n");
                                playerTurn(myMeeple);
                                break;
                            }
                            activeSkill.activate(myMeeple, target);
                            target.checkAlive(true);
                        }
                    }
                }
                default -> {
                    System.out.println(WRONG_USER_INPUT);
                    playerTurn(myMeeple);
                }
            }
        } catch (IndexOutOfBoundsException | InputMismatchException wrongUserInput) {
            System.out.println(WRONG_USER_INPUT);
            playerTurn(myMeeple);
        }
    }

    public void playerAttack(Meeple user, Enemy target) {
        int damage = user.getAtk() - target.getDef();
        if (damage > 0 && !target.checkDodge()) {
            target.setCurrentLP(target.getCurrentLP() - damage);
            System.out.println("Du hast " + target.getName() + " " + damage + " Schaden zugefügt.\n");
        } else if (damage == 0 && !target.checkDodge()) {
            target.setDef(target.getDef() - 1);
            System.out.println(target.getName() + ENEMY_TARGET_BLOCK);
        } else {
            System.out.println(target.getName() + ENEMY_TARGET_DODGE);
        }
    }

    public void showEnemyTargets() {
        for (Enemy targetEnemy : enemys) {
            if (targetEnemy.checkAlive(false)) {
                System.out.println((enemys.indexOf(targetEnemy) + 1) + ": " + targetEnemy.getName());
            }
        }
    }

    public void showPlayerTargets() {
        for (Player targetPlayer : players) {
            if (targetPlayer.checkAlive(false)) {
                System.out.println((players.indexOf(targetPlayer) + 1) + ": " + targetPlayer.getName());
            }
        }
    }

    public void showPlayerSkills(Meeple myMeeple) {
        for (Skill mySkill : myMeeple.getMySkills()) {
            System.out.println((myMeeple.getMySkills().indexOf(mySkill) + 1) + ": " + mySkill.getName());
        }
    }

    public void enemyTurn(Meeple myMeeple) {
        turnIntroMessage(myMeeple);
        if (myMeeple.getMySkills().size() > 0) {
            int i = 0;
            while (i < myMeeple.getMySkills().size()) {
                Skill activeSkill = myMeeple.getMySkills().get(i);
                if (activeSkill.isReady() && !activeSkill.isFriendlyFire()) {
                    activeSkill.activate(myMeeple, chooseRandomPlayerTarget());
                    break;
                }
                if (activeSkill.isReady() && activeSkill.isFriendlyFire()) {
                    activeSkill.activate(myMeeple, chooseRandomEnemyTarget());
                    break;
                } else {
                    if(i < myMeeple.getMySkills().size()) {
                        i++;
                    }else{
                        enemyAttack(myMeeple, chooseRandomPlayerTarget());
                        break;
                    }
                }
            }
        } else {
            enemyAttack(myMeeple, chooseRandomPlayerTarget());
        }
    }

    public void enemyAttack(Meeple user, Player target) {
        int damage = user.getAtk() - target.getDef();
        if (damage > 0 && !target.checkDodge()) {
            target.setCurrentLP(target.getCurrentLP() - damage);
            System.out.println(user.getName() + " hat " + target.getName() + " " + damage + " Schaden zugefügt.\n");
        } else if (damage == 0 && !target.checkDodge()) {
            target.setDef(target.getDef() - 1);
            System.out.println(target.getName() + " hat den Angriff abgewehrt. Doch " + user.getName() + " hat deine Verteidigung geschwächt.\n");
        } else {
            System.out.println(target.getName() + " ist dem Angriff gekonnt ausgewichen.\n");
        }
    }

    public Player chooseRandomPlayerTarget() {
        int min = 0;
        int max = players.size() - 1;
        Random number = new Random();
        int target = number.nextInt((max - min) + 1) + min;
        return players.get(target);
    }

    public Enemy chooseRandomEnemyTarget() {
        int min = 0;
        int max = enemys.size() - 1;
        Random number = new Random();
        int target = number.nextInt((max - min) + 1) + min;
        return enemys.get(target);
    }

    public void turnIntroMessage(Meeple myMeeple) {
        System.out.println(myMeeple.getName() + " ist am Zug.\n");
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void calculateXpReward() {
        for (int i = 0; i <= enemys.size() - 1; i++)
            this.xpReward += enemys.get(i).getXpReward();
    }

    public void decreaseAllCooldown(int decrease) {
        for (Meeple myMeeple : queue) {
            for (int i = 0; i < myMeeple.getMySkills().size(); i++)
                myMeeple.getMySkills().get(i).setCurrentCooldown(myMeeple.getMySkills().get(i).getCurrentCooldown() + decrease);
        }
    }

    private void checkCondition(Meeple myMeeple) {
        if (!myMeeple.getMyConditions().isEmpty()) {
            List<Meeple.Condition> toRemove = new ArrayList();
            for (Meeple.Condition myCondition : myMeeple.getMyConditions()) {
                activateCondition(myMeeple, myCondition);
                myCondition.setDuration(myCondition.getDuration() - 1);
                if (myCondition.getDuration() < 0) {
                    toRemove.add(myCondition);
                }
            }
            myMeeple.getMyConditions().removeAll(toRemove);
        }
    }

    private void activateCondition(Meeple myMeeple, Meeple.Condition myCondition) {
        switch (myCondition) {
            case BLEEDING:
                int damage = myMeeple.getMaxLP() / 10;
                myMeeple.setCurrentLP(myMeeple.getCurrentLP() - damage);
                System.out.println("Die Blutung fügt dir " + damage + " Schaden zu.\n");
                break;
            default:
        }
    }
}
