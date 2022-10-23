package four_in_a_row.core.logic.win_logic;

import four_in_a_row.core.logic.Coordinates;
import four_in_a_row.core.logic.TokenColor;
import four_in_a_row.core.structure.Cell;
import four_in_a_row.core.structure.Table;
import four_in_a_row.util.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
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
        List<Coordinates> coordinateList = new ArrayList<>();

        List<Boolean> flags = new ArrayList<>() {{
            IntStream.range(0, controlFactors.length * 2).forEach(i -> this.add(true));
        }};

        for (int incrNumber = 0;
             flags.stream().reduce((firstBool, secondBool) -> firstBool || secondBool).get(); incrNumber++) {

            for (int j = 0; j < controlFactors.length; j++) {
                // For each control factor we need to have also an internal list that specifies what was found
                CoordinateSearchResult controls = control(controlFactors[j], initCoordsToCheck, incrNumber, this.controlColor);

            }
        }

        return List.of();
    }

    public CoordinateSearchResult control(ControlFactorType controlFactor,
                                          Coordinates currentToCheck,
                                          int incrNumber,
                                          TokenColor color) {

        int row = currentToCheck.getY(),
                col = currentToCheck.getX();

        BiFunction<Integer, Integer, Coordinates> controlOfTwoFactors = (xFactor, yFactor) -> {
            int xIncrement = xFactor * incrNumber, yIncrement = yFactor * incrNumber;
            int newXCoords = col + xIncrement, newYCoords = row + yIncrement;

            if (newXCoords >= Table.WIDTH || newXCoords < 0) return null;
            else if (newYCoords >= Table.HEIGHT || newYCoords < 0) return null;
            else if (cells[newYCoords][newXCoords].getItem().get().getColor() == color)
                return new Coordinates(newXCoords, newYCoords);
            return null;
        };

        CoordinateSearchResult coordinateSearchResult = new CoordinateSearchResult();
        coordinateSearchResult.setFirstSearched(controlOfTwoFactors.apply(controlFactor.getXFactor(), controlFactor.getYFactor()));
        coordinateSearchResult.setSecondSearched(controlOfTwoFactors.apply(controlFactor.getSecondXFactor(), controlFactor.getSecondYFactor()));

        return coordinateSearchResult;
    }

}