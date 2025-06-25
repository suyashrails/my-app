package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class RoverAppTest {

    @BeforeEach
    void setup() {
        RoverApp.rovers.clear();
        RoverApp.occupied.clear();
        RoverApp.gridSize = 5;
    }

    @Test
    void testCreateRoverSuccess() {
        RoverApp.handleCommand("create R1 1 1 N");
        assertTrue(RoverApp.rovers.containsKey("R1"));
        assertEquals("(1, 1)", RoverApp.rovers.get("R1").pos.toString());
    }

    @Test
    void testCreateRoverOutOfBounds() {
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> RoverApp.handleCommand("create R2 10 10 E"));
        assertEquals("Out of bounds.", e.getMessage());
    }

    @Test
    void testCreateRoverCollision() {
        RoverApp.handleCommand("create R1 1 1 N");
        Exception e = assertThrows(IllegalArgumentException.class,
                () -> RoverApp.handleCommand("create R2 1 1 S"));
        assertEquals("Position occupied.", e.getMessage());
    }

    @Test
    void testRoverMoveValid() {
        RoverApp.handleCommand("create R1 1 1 N");
        RoverApp.handleCommand("R1 move");
        assertEquals("(0, 1)", RoverApp.rovers.get("R1").pos.toString());
    }

    @Test
    void testRoverMoveBlockedByWall() {
        RoverApp.handleCommand("create R1 0 0 N");
        RoverApp.handleCommand("R1 move");
        assertEquals("(0, 0)", RoverApp.rovers.get("R1").pos.toString()); // unchanged
    }

    @Test
    void testRoverMoveBlockedByAnotherRover() {
        RoverApp.handleCommand("create R1 2 2 N");
        RoverApp.handleCommand("create R2 1 2 S"); // blocks R1’s forward move
        RoverApp.handleCommand("R1 move");
        assertEquals("(2, 2)", RoverApp.rovers.get("R1").pos.toString()); // move denied
    }

    @Test
    void testTurnLeftAndRight() {
        RoverApp.handleCommand("create R1 1 1 N");
        RoverApp.handleCommand("R1 turn left");
        assertEquals(RoverApp.Direction.W, RoverApp.rovers.get("R1").dir);
        RoverApp.handleCommand("R1 turn right");
        assertEquals(RoverApp.Direction.N, RoverApp.rovers.get("R1").dir);
    }
}
