package four_in_a_row.core.logic.win_logic;

import four_in_a_row.core.logic.Coordinates;

import java.util.ArrayList;
import java.util.List;

public class ControlFactor {

    private final List<Coordinates> factorCoords = new ArrayList<>(4);
    private final ControlFactorType controlFactorType;

    public ControlFactor(ControlFactorType controlFactorType) {
        this.controlFactorType = controlFactorType;
    }

    public ControlFactorType getControlFactorType() {
        return controlFactorType;
    }

}