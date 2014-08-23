package org.voimala.gameoflife.programbody;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.voimala.gameoflife.gamecomponents.GameSession;
import org.voimala.gameoflife.gamecomponents.Map;
import org.voimala.gameoflife.gamecomponents.ThreadMonitor;

public class GameOfLifeApp {
    
    private static GameOfLifeApp instanceOfThis = null;
    private GameSession gameSession = null;
    
    public static final GameOfLifeApp getInstance() {
        if (instanceOfThis == null) {
            instanceOfThis = new GameOfLifeApp();
        }
        
        return instanceOfThis;
    }

    public void run(final String inputFile, final int maxGenerations) throws IOException {
        System.out.println("Running simulation...");
        gameSession = new GameSession();
        gameSession.startSimulation(inputFile, maxGenerations);
        System.out.println("Stopping simulation.");
    }
    
}
