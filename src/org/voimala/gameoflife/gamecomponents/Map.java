package org.voimala.gameoflife.gamecomponents;

import java.util.ArrayList;
import java.util.List;

import org.voimala.gameoflife.exceptions.GameLogicException;

public class Map {
    
    private Cell[][] map; // rows, columns
    private int columns = 0;
    private int rows = 0;
    
    public Map(final int rows, final int columns) {
        this.columns = columns;
        this.rows = rows;
        initializeCells();
    }

    /**
     * @param stringRows And array of strings containing values 0 or 1.
     */
    public void loadCellValuesFromStringRows(List<String> stringRows) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                char character = stringRows.get(i).charAt(j);
                int value = Integer.valueOf(String.valueOf(character));
                if (value != 0 && value != 1) {
                    throw new GameLogicException(
                            "Input array must contain only values 1 or 0. Found value" + " " + value + ".");
                }
                
                map[i][j] = new Cell(this, i + 1, j + 1, value);
            }
        }
    }
    
    /** Creates cells with the value of 0. */
    public void initializeCells() {
        map = new Cell[rows][columns];
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                map[i][j] = new Cell(this, i + 1, j + 1, 0);
            }
        }
    }

    public final void printMap() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print(map[i][j].getValue());
            }
            
            System.out.print("\n");
        }
    }
    
    /**
     * @param row (1-index)
     * @param column (1-index)
     */
    public void setCellValueAtPosition(final int row, final int column, final int newValue) {
        getCellAtPosition(row, column).setValue(newValue);
    }
    
    /**
     * @param row (1-index)
     * @param column (1-index)
     * @return null if out of bounds.
     */
    public final Cell getCellAtPosition(final int row, final int column) {
        if (row > rows || row < 1) {
            return null;
        }
        
        if (column > columns || column < 1) {
            return null;
        }
        
        Cell cell = map[row - 1][column - 1];
        
        if (cell.getRow() != row || cell.getColumn() != column) {
            throw new RuntimeException("Cell was not in the position where it supposed to be." +
                    " " + "Asked row" + " " + row + " " + "and column" + " " + column + " " + "but found row" +
                    " " + cell.getRow() + " " + "and column" + " " + cell.getColumn());
        }
        
        return cell;
    }
    
    public final List<Cell> getCells() {
        ArrayList<Cell> cells = new ArrayList<>();
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cells.add(map[i][j]);
            }
        }
        
        return cells;
    }
    
    public final int getRows() {
        return rows;
    }
    
    public final int getColumns() {
        return columns;
    }
    
}
