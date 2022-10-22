package four_in_a_row.core.structure;

import four_in_a_row.core.logic.TokenColor;

public class Token {

    private TokenColor color;

    public Token(TokenColor color) {
        this.color = color;
    }

    public TokenColor getColor() {
        return color;
    }

    public void setColor(TokenColor color) {
        this.color = color;
    }

}