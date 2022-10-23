package four_in_a_row.core.structure;

import four_in_a_row.core.logic.TableCoordinates;
import javafx.beans.property.SimpleObjectProperty;

public class Cell {

    private TableCoordinates coordinates;
    private SimpleObjectProperty<Token> item ;

    public Cell(TableCoordinates coordinates, Token item) {
        this.coordinates = coordinates;
        this.item = new SimpleObjectProperty<>(item);
    }

    public Cell(TableCoordinates coordinates) {
        this.coordinates = coordinates;
        this.item = new SimpleObjectProperty<>(null);
    }

    public TableCoordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(TableCoordinates coordinates) {
        this.coordinates = coordinates;
    }

    public SimpleObjectProperty<Token> getItem() {
        return item;
    }

    public void setItem(Token item) {
        this.item.set(item);
    }
}