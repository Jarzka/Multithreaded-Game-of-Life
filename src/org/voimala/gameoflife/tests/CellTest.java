package org.voimala.gameoflife.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.voimala.gameoflife.exceptions.GameLogicException;
import org.voimala.gameoflife.gamecomponents.Map;

public class CellTest {
    
    @Test (expected = GameLogicException.class)
    public void testCellInvalidValue() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 5);
    }

    @Test
    public void testGenerationOneBecomesZero() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        assertEquals(map.getCellAtPosition(1, 1).getNextGenerationValue(), 0);
    }
    
    @Test
    public void testGenerationOneBecomesZero2() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        map.setCellValueAtPosition(2, 2, 1);
        assertEquals(map.getCellAtPosition(1, 1).getNextGenerationValue(), 0);
    }
    
    @Test
    public void testGenerationOneBecomesZero3() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        map.setCellValueAtPosition(1, 2, 1);
        map.setCellValueAtPosition(1, 3, 1);
        map.setCellValueAtPosition(2, 1, 1);
        map.setCellValueAtPosition(2, 2, 1);
        assertEquals(map.getCellAtPosition(2, 2).getNextGenerationValue(), 0);
    }
    
    @Test
    public void testGenerationOneBecomesZero4() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 5, 1);
        map.setCellValueAtPosition(2, 5, 1);
        map.setCellValueAtPosition(3, 7, 1);
        map.setCellValueAtPosition(4, 7, 1);
        map.setCellValueAtPosition(6, 7, 1);
        assertEquals(map.getCellAtPosition(3, 7).getNextGenerationValue(), 0);
    }
    
    @Test
    public void testGenerationOneStaysOne1() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        map.setCellValueAtPosition(1, 2, 1);
        map.setCellValueAtPosition(2, 2, 1);
        assertEquals(map.getCellAtPosition(2, 2).getNextGenerationValue(), 1);
    }
    
    @Test
    public void testGenerationOneStaysOne2() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(3, 3, 1);
        map.setCellValueAtPosition(3, 4, 1);
        map.setCellValueAtPosition(3, 5, 1);
        map.setCellValueAtPosition(4, 4, 1);
        assertEquals(map.getCellAtPosition(4, 4).getNextGenerationValue(), 1);
    }
    
    @Test
    public void testGenerationOneStaysOne3() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        map.setCellValueAtPosition(1, 2, 1);
        map.setCellValueAtPosition(1, 3, 1);
        assertEquals(map.getCellAtPosition(1, 2).getNextGenerationValue(), 1);
    }
    
    @Test
    public void testGenerationZeroBecomessOne1() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        map.setCellValueAtPosition(2, 1, 1);
        map.setCellValueAtPosition(3, 1, 1);
        assertEquals(map.getCellAtPosition(2, 2).getNextGenerationValue(), 1);
    }
    
    @Test
    public void testGenerationZeroBecomessOne2() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        map.setCellValueAtPosition(1, 2, 1);
        map.setCellValueAtPosition(2, 1, 1);
        assertEquals(map.getCellAtPosition(2, 2).getNextGenerationValue(), 1);
    }
    
    @Test
    public void testGenerationZeroBecomessOne3() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 2, 1);
        map.setCellValueAtPosition(2, 1, 1);
        map.setCellValueAtPosition(2, 2, 1);
        assertEquals(map.getCellAtPosition(1, 1).getNextGenerationValue(), 1);
    }
    
    @Test
    public void testGenerationZeroStaysZero1() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        assertEquals(map.getCellAtPosition(2, 2).getNextGenerationValue(), 0);
    }
    
    @Test
    public void testGenerationZeroStaysZero2() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        map.setCellValueAtPosition(1, 2, 1);
        assertEquals(map.getCellAtPosition(2, 1).getNextGenerationValue(), 0);
    }
    
    @Test
    public void testGenerationZeroStaysZero3() {
        Map map = new Map(10, 10);
        map.setCellValueAtPosition(1, 1, 1);
        map.setCellValueAtPosition(2, 1, 1);
        map.setCellValueAtPosition(3, 1, 1);
        assertEquals(map.getCellAtPosition(1, 2).getNextGenerationValue(), 0);
    }

}
