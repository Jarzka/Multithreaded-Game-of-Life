package org.voimala.gameoflife.gamecomponents;

import org.voimala.gameoflife.exceptions.GameLogicException;

public class Cell extends Thread {
    private int value = 0;
    private int row = 0;
    private int column = 0;
    private int nextGenerationValue = -1;
    private Map map = null;
    private ThreadMonitor threadmonitor = null;
    
    public Cell(final Map map, final int row, final int column, final int value) {
        super("Cell Thread");
        this.map = map;
        this.row = row;
        this.column = column;
        this.value = value;
    }
    
    public final void setThreadMonitor(ThreadMonitor threadMonitor) {
        this.threadmonitor = threadMonitor;
    }
    
    public final void run() {
        if (threadmonitor == null) {
            throw new GameLogicException("Cell Thread must have ThreadMonitor.");
        }
        
        while (!isInterrupted()) {
            playGame();
        }
        
        System.out.println(getName() + " [" + row + "," + column + "] "+ "terminated.");
        
    }

    private final synchronized void playGame() {
        calculateNextGenerationValue();
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        
        setNextGenerationValue();
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /** Only ThreadMonitor should call this method. */
    public final synchronized void continueGame() {
        notify();
    }
    
    /** Only ThreadMonitor should call this method. */
    public final synchronized void stopGame() {
        notify();
        interrupt();
    }

    private void calculateNextGenerationValue() {
        nextGenerationValue = getNextGenerationValue();
        threadmonitor.threadFinishedNextValueCalculation();
    }

    public final int getNextGenerationValue() {
        if (value == 1) {
            return getNextGenerationValueForOne();
        } else {
            
            return getNextGenerationValueForZero();
        }
    }

    private int getNextGenerationValueForOne() {
        // 1 becomes 0 if less than 2 or greater than 3 neighbours are 1 valued
        int neighborsAmount = getNumberOfNeighborsWithValue(1);
        if (neighborsAmount < 2 || neighborsAmount > 3) {
            return 0;
        }
        
        return 1;
    }

    private int getNextGenerationValueForZero() {
        // 0 becomes 1 if exactly 3 neighbours are valued 1.
        int neighborsAmount = getNumberOfNeighborsWithValue(1);
        if (neighborsAmount == 3) {
            return 1;
        }
        
        return 0;
    }
    

    /** Returns the number of neighbors that have the given neighbourValue. */
    private int getNumberOfNeighborsWithValue(int neighborValue) {
        int amount = 0;
        for (int i = row - 1; i <= row + 1; i++) {
            for (int j = column - 1; j <= column + 1; j++) {
                Cell neighbor = map.getCellAtPosition(i, j);
                
                if (neighbor == this) {
                    continue; // Do not count this.
                }
                
                if (neighbor != null) {
                    if (neighbor.getValue() == neighborValue) {
                        amount++;
                    }
                }
            }
        }
        
        return amount;
    }
    
    /** Cell will immediately change it's current value to pre-calculated next generation value. */
    private final void setNextGenerationValue() {
        if (nextGenerationValue != 0 && nextGenerationValue != 1) {
            throw new GameLogicException("Pre-calculated next generation value must be 0 or 1. It was"
                    + " " + nextGenerationValue + ".");
        }
        
        value = nextGenerationValue;
        nextGenerationValue = -1;
        threadmonitor.threadFinishedNextValueSet();
    }

    public final int getValue() {
        return value;
    }

    public final void setValue(final int newValue) {
        if (newValue != 0 && newValue != 1) {
            throw new GameLogicException("Cell value must be 0 or 1, not" + " " + newValue + ".");
        }
        value = newValue;
    }

    public final int getRow() {
        return row;
    }
    
    public final int getColumn() {
        return column;
    }
}
