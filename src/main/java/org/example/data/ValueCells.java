package org.example.data;

import com.javarush.engine.cell.Color;
import org.example.config.Config;
import org.example.models.Coordinates;
import org.example.models.ValueCell;

public class ValueCells {
    private final ValueCell[][] valueCells;

    public ValueCells(Config config) {
        valueCells = new ValueCell[config.sideField][config.sideField];
        for (int x = 0; x < config.sideField; x++) {
            for (int y = 0; y < config.sideField; y++) {
                valueCells[x][y] = new ValueCell(x, y, Color.WHITE, "");
            }
        }
    }

    public void updateValueCell(int x, int y, Color color, String value) {
        valueCells[x][y].setColor(color);
        valueCells[x][y].setValue(value);
    }
}
