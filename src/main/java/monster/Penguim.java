package monster;

public class Penguim extends Monster {
    private int maxHp;

    public Penguim(int no_of_frame) {
        reward = 2 + no_of_frame / 10;
        icon = "penguin.png";
        hp = 8 + no_of_frame / 4;
        maxHp = hp;
        speed = speed + no_of_frame / 10; // every 10 frame speed++
    }

    void replenish() {
        if (hp < maxHp)
            this.hp++;
    }
}