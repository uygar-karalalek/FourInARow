package four_in_a_row.core.logic;

public class Player {

    private TokenColor color;
    private String name;

    public Player(TokenColor color, String name) {
        this.color = color;
        this.name = name;
    }

    public Player(TokenColor color) {
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TokenColor getColor() {
        return color;
    }

    public void setColor(TokenColor color) {
        this.color = color;
    }
}