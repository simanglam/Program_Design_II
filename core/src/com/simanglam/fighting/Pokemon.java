public class Pokemon {
    private String name;
    private int level;
    private int currentHP;
    private int maxHP;
    private int attack;
    private int defense;
    private String[] types; // 寶可夢類型
    private Move[] moves; // 寶可夢的招式

    public Pokemon(String name, int level) {
        // 從API或其他來源獲取寶可夢的屬性和招式，這裡我們暫時初始化為一些示例值
        this.name = name;
        this.level = level;
        this.currentHP = 100; // 假設每隻寶可夢的最大HP都是100
        this.maxHP = 100;
        this.attack = 50;
        this.defense = 30;
        this.types = new String[]{"Fire"}; // 假設這隻寶可夢的類型是火屬性
        this.moves = new Move[]{new Move("Scratch"), new Move("Ember")}; // 假設這隻寶可夢會兩個招式：Scratch和Ember
    }

    // 獲取寶可夢的名字
    public String getName() {
        return name;
    }

    // 獲取寶可夢的當前HP
    public int getCurrentHP() {
        return currentHP;
    }

    // 攻擊對手
    public void attack(Pokemon opponent, Move move) {
        // 計算傷害
        int damage = (2 * level + 10) / 250 * attack / opponent.defense * move.getPower();
        // 如果招式屬性與對手的其中一個類型匹配，則增加傷害
        for (String type : opponent.types) {
            if (type.equals(move.getType())) {
                damage *= 1.5;
                break;
            }
        }
        // 對手扣除傷害
        opponent.takeDamage(damage);
    }

    // 承受傷害
    public void takeDamage(int damage) {
        currentHP -= damage;
        if (currentHP < 0) {
            currentHP = 0;
        }
    }

    // 使用招式
    public Move[] getMoves() {
        return moves;
    }
}
