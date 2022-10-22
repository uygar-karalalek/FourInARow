package four_in_a_row.core.structure;

import four_in_a_row.core.logic.Coordinates;
import javafx.beans.property.SimpleObjectProperty;

public class Cell {

    private Coordinates coordinates;
    private SimpleObjectProperty<Token> item;

    public Cell(Coordinates coordinates, Token item) {
        this.coordinates = coordinates;
        this.item = new SimpleObjectProperty<>(item);
    }

    public Cell(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.item = new SimpleObjectProperty<>(null);
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    public SimpleObjectProperty<Token> getItem() {
        return item;
    }

    public void setItem(Token item) {
        this.item.set(item);
    }
}