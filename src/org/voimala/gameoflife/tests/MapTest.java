package org.voimala.gameoflife.tests;

import static org.junit.Assert.*;

import org.junit.Test;
import org.voimala.gameoflife.gamecomponents.Map;

public class MapTest {

    @Test
    public void testGetCell1() {
        Map map = new Map(10, 10);
        assertEquals(map.getCellAtPosition(1, 1).getRow(), 1);
        assertEquals(map.getCellAtPosition(1, 1).getColumn(), 1);
    }
    
    @Test
    public void testGetCell2() {
        Map map = new Map(10, 10);
        assertEquals(map.getCellAtPosition(2, 2).getRow(), 2);
        assertEquals(map.getCellAtPosition(2, 2).getColumn(), 2);
    }
    
    @Test
    public void testGetCell3() {
        Map map = new Map(1000, 1000);
        assertEquals(map.getCellAtPosition(543, 864).getRow(), 543);
        assertEquals(map.getCellAtPosition(543, 864).getColumn(), 864);
    }
    
    @Test
    public void testGetCell4() {
        Map map = new Map(100, 100);
        assertEquals(map.getCellAtPosition(7, 5).getRow(), 7);
        assertEquals(map.getCellAtPosition(7, 5).getColumn(), 5);
    }

}
