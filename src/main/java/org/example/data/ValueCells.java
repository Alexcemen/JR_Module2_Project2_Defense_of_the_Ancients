package org.example.data;

import com.javarush.engine.cell.Color;
import org.example.config.Config;
import org.example.models.Coordinates;
import org.example.models.ValueCell;

public class ValueCells {
    private ValueCell[][] valueCellArray;
    private Config config;

    public ValueCells(Config config) {
        this.config = config;
        valueCellArray = new ValueCell[config.sideField][config.sideField];
    }

    public void updateValueCell(int x, int y, Color color, String value) {
        valueCellArray[x][y].setColor(color);
        valueCellArray[x][y].setValue(value);
    }

    public ValueCell getValueCell(Coordinates coordinates) {
        int x = coordinates.x();
        int y = coordinates.y();
        return valueCellArray[x][y];
    }
}
