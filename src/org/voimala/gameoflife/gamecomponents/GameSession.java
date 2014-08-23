package org.voimala.gameoflife.gamecomponents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameSession {
    
    private Map map = null;
    private ThreadMonitor threadMonitor = null;
    
    public void startSimulation(final String inputFile, final int maxGenerations) throws IOException {
        createMap(inputFile, maxGenerations);
        createThreadMonitor(maxGenerations);
        runSimulation();
    }

    private void createMap(final String inputFile, final int maxGenerations) throws IOException {
        List<String> rows = loadMapFile(inputFile);
        map = new Map(rows.size(), rows.get(0).length());
        map.loadCellValuesFromStringRows(rows);
        
        System.out.println("Input map" + ":");
        map.printMap();
    }

    /** Loads the given txt file and stores its every row in a String list. */
    private List<String> loadMapFile(final String inputFile) throws IOException {
        ArrayList<String> rows = new ArrayList<>();
        
        BufferedReader bufferedReader = new BufferedReader(new FileReader(inputFile));
        try {
            String line = bufferedReader.readLine();

            while (line != null) {
                rows.add(line);
                line = bufferedReader.readLine();
            }
        } finally {
            bufferedReader.close();
        }
        
        return rows;
    }

    private void createThreadMonitor(int maxGenerations) {
        threadMonitor = new ThreadMonitor(map);
        threadMonitor.setMaxGenerations(maxGenerations);
    }
    
    private void runSimulation() {
        System.out.println("Running simulation...");
        threadMonitor.runSimulation();
    }
    
}
