package com.example;

import java.util.*;

public class RoverApp {
    // 🚀 Step 1: Enum for direction
    enum Direction { N, E, S, W }

    // 🚀 Step 2: Position class for coordinates
    static class Position {
        int row, col;

        Position(int row, int col) {
            this.row = row;
            this.col = col;
        }

        Position copy() {
            return new Position(row, col);
        }

        @Override public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Position p)) return false;
            return row == p.row && col == p.col;
        }

        @Override public int hashCode() {
            return Objects.hash(row, col);
        }

        @Override public String toString() {
            return "(" + row + ", " + col + ")";
        }
    }

    // 🚀 Step 3: Rover class now includes ID and exposes peekNextPosition
    static class Rover {
        String id;
        Position pos;
        Direction dir;

        Rover(String id, int row, int col, Direction dir) {
            this.id = id;
            this.pos = new Position(row, col);
            this.dir = dir;
        }

        Position peekNextPosition() {
            Position next = pos.copy();
            switch (dir) {
                case N -> next.row--;
                case S -> next.row++;
                case E -> next.col++;
                case W -> next.col--;
            }
            return next;
        }

        void move() {
            pos = peekNextPosition(); // assumes validation done externally
        }

        void turnLeft() {
            dir = switch (dir) {
                case N -> Direction.W;
                case W -> Direction.S;
                case S -> Direction.E;
                case E -> Direction.N;
            };
        }

        void turnRight() {
            dir = switch (dir) {
                case N -> Direction.E;
                case E -> Direction.S;
                case S -> Direction.W;
                case W -> Direction.N;
            };
        }

        String status() {
            return id + " => Position: " + pos + ", Direction: " + dir;
        }
    }

    // 🚀 Step 4: Main controller state
    static final Map<String, Rover> rovers = new HashMap<>();
    static final Set<Position> occupied = new HashSet<>();
    static int gridSize;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // 🟢 Init grid size
        System.out.print("Grid size (N): ");
        gridSize = sc.nextInt();
        sc.nextLine(); // consume newline

        // 🟢 Command loop
        while (true) {
            System.out.print("Command (create/move/turn left/turn right/exit): ");
            String input = sc.nextLine().trim();
            if (input.equalsIgnoreCase("exit")) break;

            try {
                handleCommand(input);
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

            printAllStatuses();
        }
    }

    // 🚀 Step 5: Command handler
    static void handleCommand(String input) {
        String[] parts = input.split("\\s+");
        if (parts[0].equals("create")) {
            // create R1 1 1 N
            if (parts.length != 5) throw new IllegalArgumentException("Usage: create <id> <row> <col> <dir>");
            String id = parts[1];
            int row = Integer.parseInt(parts[2]);
            int col = Integer.parseInt(parts[3]);
            Direction dir = Direction.valueOf(parts[4].toUpperCase());

            Position p = new Position(row, col);
            if (!inBounds(p)) throw new IllegalArgumentException("Out of bounds.");
            if (isOccupied(p)) throw new IllegalArgumentException("Position occupied.");

            Rover rover = new Rover(id, row, col, dir);
            rovers.put(id, rover);
            occupied.add(p);
        } else {
            // R1 move / R2 turn left
            if (parts.length < 2) throw new IllegalArgumentException("Usage: <id> <command>");
            String id = parts[0];
            String cmd = String.join(" ", Arrays.copyOfRange(parts, 1, parts.length));

            Rover rover = rovers.get(id);
            if (rover == null) throw new IllegalArgumentException("Unknown rover ID.");

            switch (cmd) {
                case "move" -> {
                    Position next = rover.peekNextPosition();
                    if (!inBounds(next)) {
                        System.out.println("Blocked: Out of bounds.");
                    } else if (isOccupied(next)) {
                        System.out.println("Blocked: Position occupied.");
                    } else {
                        occupied.remove(rover.pos);
                        rover.move();
                        occupied.add(rover.pos);
                    }
                }
                case "turn left" -> rover.turnLeft();
                case "turn right" -> rover.turnRight();
                default -> throw new IllegalArgumentException("Unknown command.");
            }
        }
    }

    // 🚀 Step 6: Utility methods
    static boolean inBounds(Position p) {
        return p.row >= 0 && p.row < gridSize && p.col >= 0 && p.col < gridSize;
    }

    static boolean isOccupied(Position p) {
        return occupied.contains(p);
    }

    static void printAllStatuses() {
        for (Rover r : rovers.values()) {
            System.out.println(r.status());
        }
    }
}
