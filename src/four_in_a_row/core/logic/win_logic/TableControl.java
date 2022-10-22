package four_in_a_row.core.logic.win_logic;

import four_in_a_row.core.logic.Coordinates;
import four_in_a_row.core.logic.TokenColor;
import four_in_a_row.core.structure.Cell;
import four_in_a_row.core.structure.Table;
import four_in_a_row.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.stream.IntStream;

public class TableControl {

    private Cell[][] cells;
    private TokenColor controlColor;

    public TableControl(Cell[][] cells, TokenColor controlColor) {
        this.cells = cells;
        this.controlColor = controlColor;
    }

    public List<Coordinates> controlFourInARow(Coordinates initCoordsToCheck, ControlFactorType... controlFactors) {
        List<Boolean> flags = new ArrayList<>() {{
            IntStream.range(0, controlFactors.length * 2).forEach(i -> this.add(true));
        }};

        for (int incrNumber = 0;
             flags.stream().reduce((firstBool, secondBool) -> firstBool || secondBool).get(); incrNumber++) {

            for (int j = 0; j < controlFactors.length; j++) {
                // For each control factor we need to have also an internal list that specifies what was found
                Pair<Boolean> controls = control(controlFactors[j], initCoordsToCheck, incrNumber, this.controlColor);

            }
        }

        return List.of();
    }

    public Pair<Boolean> control(ControlFactorType controlFactor,
                                 Coordinates currentToCheck,
                                 int incrNumber,
                                 TokenColor color) {

        int row = currentToCheck.getY(),
                col = currentToCheck.getX();

        BiPredicate<Integer, Integer> controlOfTwoFactors = (xFactor, yFactor) -> {
            int xIncrement = xFactor * incrNumber, yIncrement = yFactor * incrNumber;

            if (col + xIncrement >= Table.WIDTH || col + xIncrement < 0) return false;
            else if (row + yIncrement >= Table.HEIGHT || row + yIncrement < 0) return false;
            else return cells[row + yIncrement][col + xIncrement].getItem().get().getColor() == color;
        };

        Pair<Boolean> controlFlags = new Pair<>();
        controlFlags.first = controlOfTwoFactors.test(controlFactor.getXFactor(), controlFactor.getYFactor());
        controlFlags.second = controlOfTwoFactors.test(controlFactor.getSecondXFactor(), controlFactor.getSecondYFactor());

        return controlFlags;
    }

}