public class Move {
    private String name;
    private int power;
    private String type;

    public Move(String name) {
        // 從API或其他來源獲取招式的屬性，這裡我們暫時初始化為一些示例值
        this.name = name;
        this.power = 40; // 假設招式威力為40
        this.type = "Normal"; // 假設招式類型為普通屬性
    }

    public String getName() {
        return name;
    }

    public int getPower() {
        return power;
    }

    public String getType() {
        return type;
    }
}
