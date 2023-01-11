import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefence;
    public static int[] heroesHealth = {280, 270, 250, 400, 5000, 250, 500, 600 };
    public static int[] heroesDamage = {10, 15, 20, 0, 5, 10, 25, 20};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Lucky", "Berserk", "Thor"};
    public static int roundNumber;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++; // roundNumber = roundNumber + 1
        chooseBossDefence();
        golemWork();
        bossHits();
        heroesHit();
        thorWork();
        medicWork();
        luckyWork();
        berserkWork();
        printStatistics();
        bossStart();
    }

    private static void bossStart() {
        bossDamage = 50;
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0, 1, 2
        bossDefence = heroesAttackType[randomIndex];
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int damage = heroesDamage[i];
                if (heroesAttackType[i] == bossDefence) {
                    Random random = new Random();
                    int coefficient = random.nextInt(9) + 2; // 2,3,4,5,6,7,8,9,10
                    damage = coefficient * heroesDamage[i];
                    System.out.println("Critical damage: " + damage);
                }
                if (bossHealth - damage < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - damage;
                }
            }
        }
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void printStatistics() {
        /*String defence = "No defence";
        if (bossDefence != null) {
            defence = bossDefence;
        }*/
        System.out.println("ROUND " + roundNumber + " --------------");
        System.out.println("Boss health: " + bossHealth + " damage: " + bossDamage
                + " defence: " + (bossDefence != null ? bossDefence : "No defence"));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i]
                    + " damage: " + heroesDamage[i]);
        }
    }

    public static void medicWork() {
        if (heroesHealth[3] > 0) {
            for (int i = 0; i < heroesHealth.length; i++) {
                if (heroesHealth[i] != 3 && heroesHealth[i] <= 100 && heroesHealth[i] > 0) {
                    heroesHealth[i] = heroesHealth[i] + 20;
                    System.out.println(heroesAttackType[i] + "____________+20");
                    break;
                }
            }
        }
    }
    public static void golemWork() {
        bossDamage = bossDamage - (bossDamage / 5);
        heroesHealth[4] = heroesHealth[4] - bossDamage - ((heroesHealth.length - 1) * 10);
    }
    public static void luckyWork(){
        Random random = new Random();
        boolean chance = random.nextBoolean();
        if (chance){
            heroesHealth[5] = heroesHealth[5] + bossDamage;
            System.out.println("Lucky is evaded");
        }
    }
    public static void berserkWork(){
        int block = 20;
        heroesHealth[6] = heroesHealth[6] + (bossDamage - block);
        bossHealth = bossHealth - block;
    }
    public static void thorWork(){
        Random random = new Random();
        boolean chance = random.nextBoolean();
        if (chance){
            bossDamage = 0;
            System.out.println("Boss is evaded");
        }
    }
}
