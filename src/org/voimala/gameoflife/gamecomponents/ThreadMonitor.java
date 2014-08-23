package org.voimala.gameoflife.gamecomponents;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import org.voimala.gameoflife.encoders.AnimatedGifEncoder;
import org.voimala.gameoflife.exceptions.GameLogicException;

public class ThreadMonitor extends Thread {
    
    private Map gameSessionMap = null;
    private ArrayList<Cell> cells = null;
    /* All cell values are stored in this array before they are simultaneously changed.
     * This array is mainly used for checking that not a single cell changes its value
     * before ThreadMonitor gives a permission to do so. */
    private String cellValues = null;
    private int maxGenerations = 10;
    private int threadsFinishedNextValueCalculation = 0;
    private int threadsFinishedNextValueSet = 0;
    
    private AnimatedGifEncoder animatedGifEncoder = new AnimatedGifEncoder();
    private int cellSizeInPixels = 32; // For Gif encoding.
    private int gifDefaultDurationMs = 200;
    
    public ThreadMonitor(final Map map) {
        this.gameSessionMap = map;
    }
    
    public final void setMaxGenerations(final int maxGenerations) {
        this.maxGenerations = maxGenerations;
    }
    
    public final void runSimulation() {
        initializeGifEncoder();
        setCellsThreadMonitor();
        startCellThreads();
        computeGenerations();
    }
    
    private void initializeGifEncoder() {
        animatedGifEncoder.start("output.gif");
        createGifFrame();
        animatedGifEncoder.setDelay(gifDefaultDurationMs);
        animatedGifEncoder.setRepeat(0); // Does not work for some reason.
    }

    private void setCellsThreadMonitor() {
        cells = (ArrayList<Cell>) gameSessionMap.getCells();
        for (Cell cell : cells) {
            cell.setThreadMonitor(this);
        }
    }
    
    private void startCellThreads() {
        for (Cell cell : cells) {
            cell.start();
        }
    }
    
    private synchronized void computeGenerations() {
        /* When this method is called for the first time, cells are calculating their next
         * generation values. */
        for (int i = 1; i <= maxGenerations; i++) {
            // saveCellValues(); // Can be used for testing purposes.
            waitNextGenerationValueCalculations();
            // checkCellValues(); // Can be used for testing purposes.
            
            wakeUpCellThreads();
            waitNextGenerationValueSets();
            
            printGeneration(i);
            createGifFrame();
            
            // Will the loop be executed after this round?
            if (i < maxGenerations) {
                wakeUpCellThreads(); // Cells will start calculating their next generation values.
            }
        }
        
        stopThreads();
        wakeUpCellThreads(); // Cells will check their interrupted status before continuing to the next generation.
        saveGif();
    }

    private void saveCellValues() {
        cellValues = "";
        for (Cell cell : cells) {
            cellValues += String.valueOf(cell.getValue());
        }
    }

    private void checkCellValues() {
        String newCellValues = "";
        
        for (Cell cell : cells) {
            newCellValues += String.valueOf(cell.getValue());
        }
        
        if (!newCellValues.contains(cellValues)) {
            throw new GameLogicException("At least one cell has changed it's value before ThreadMonitor "
                    + "gave a permission to do so. The simulation can not continue.");
        }
    }
    
    private void waitNextGenerationValueCalculations() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void waitNextGenerationValueSets() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void wakeUpCellThreads() {
        for (Cell cell : cells) {
            cell.continueGame();
        }
    }

    private void printGeneration(int generationNumber) {
        System.out.println("---------- " + "Generation" + " " + generationNumber + " ----------");
        gameSessionMap.printMap();
    }
    
    private void createGifFrame() {
        BufferedImage image = new BufferedImage(gameSessionMap.getColumns() * cellSizeInPixels, gameSessionMap.getRows() * cellSizeInPixels, BufferedImage.TYPE_INT_RGB);
        
        for (int i = 1; i <= gameSessionMap.getRows(); i++) {
            for (int j = 1; j <= gameSessionMap.getColumns(); j++) {
                int rgb = 0;
                int brightness = gameSessionMap.getCellAtPosition(i, j).getValue() * 255;
                rgb = (brightness << 16) | (brightness << 8) | (brightness);
                for (int k = 0; k < cellSizeInPixels; k++) {
                    for (int l = 0; l < cellSizeInPixels; l++) {
                        image.setRGB((j - 1) * cellSizeInPixels + k, (i - 1) * cellSizeInPixels + l, rgb);
                    }
                }
            }
        }
        
        animatedGifEncoder.addFrame(image);
    }
    
    private void saveGif() {
        animatedGifEncoder.finish();
    }
    
    private void stopThreads() {
        for (Cell cell : cells) {
            cell.stopGame();
        }
    }

    /**
     * Cell Thread calls this method when it has finished calculating it's next generation value.
     * If all threads have calculated their next generation value, notify this thread.
     * */
    public final synchronized void threadFinishedNextValueCalculation() {
        threadsFinishedNextValueCalculation++;
        
        if (threadsFinishedNextValueCalculation == cells.size()) {
            threadsFinishedNextValueCalculation = 0;
            notify();
        }
    }
    
    /**
     * Cell Thread calls this method when it has finished setting it's next generation value.
     * If all threads have set their next generation value, notify this thread.
     * */
    public final synchronized void threadFinishedNextValueSet() {
        threadsFinishedNextValueSet++;
        
        if (threadsFinishedNextValueSet == cells.size()) {
            threadsFinishedNextValueSet = 0;
            notify();
        }
    }

}
