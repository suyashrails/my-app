# 🚀 Multi-Rover CLI Simulator (Java 21)

A simple Java-based command-line application that simulates the motion of multiple autonomous rovers on a square 2D grid.

Each rover can move, turn left, or turn right, while avoiding collisions with the grid boundaries or other rovers.

---

## 🛠 Tech Stack

- Java 21
- Maven
- JUnit 5 (for testing)

---

## ✅ Features

- Supports multiple rovers with unique IDs
- Each rover maintains position and direction (`N`, `S`, `E`, `W`)
- Interactive CLI: users issue commands one at a time
- Rovers cannot move into occupied cells or outside grid
- Prints status of all rovers after every command

---

## 🚦 How It Works

1. On startup, user provides the **grid size** (e.g., 5 → a 5×5 grid)
2. Then the program waits for user commands:
   - Create a rover
   - Move a rover
   - Turn a rover left or right
   - Exit the program

The grid is 0-indexed and uses array-style coordinates: `(row, col)`  
Top-left is `(0, 0)`; rows increase downward, columns increase rightward.

---

## 📥 Commands

### ➕ Create a new rover
```bash
create R1 2 3 N
```
Creates a rover `R1` at row `2`, column `3`, facing North.

### 🚚 Move a rover forward by 1 unit
```bash
R1 move
```
Moves rover `R1` in its current direction if the destination is unoccupied and in bounds.

### 🔄 Turn a rover
```bash
R1 turn left
R1 turn right
```
Rotates the rover 90° in the specified direction.

### ❌ Exit
```bash
exit
```
Ends the program.

---

## 🖥️ Example Session

```
Grid size (N): 5
Command (create/move/turn left/turn right/exit): create R1 1 1 E
R1 => Position: (1, 1), Direction: E

Command: R1 move
R1 => Position: (1, 2), Direction: E

Command: R1 turn left
R1 => Position: (1, 2), Direction: N

Command: create R2 0 2 S
R1 => Position: (1, 2), Direction: N
R2 => Position: (0, 2), Direction: S

Command: R1 move
Blocked: Position occupied.
R1 => Position: (1, 2), Direction: N
R2 => Position: (0, 2), Direction: S
```

---

## 🧪 Run Tests

```bash
mvn test
```

---

## ▶️ Run App

```bash
mvn compile
mvn exec:java -Dexec.mainClass="com.example.RoverApp"
```

> (Make sure you have `exec-maven-plugin` in your `pom.xml`, or run via your IDE)

---

## 📂 Project Structure

```
src/
  main/
    java/
      com/example/RoverApp.java
  test/
    java/
      RoverAppTest.java
pom.xml
README.md
```

---

## 📌 Notes

- Grid is square (`N × N`)
- Rover IDs must be unique
- All inputs are space-separated and case-insensitive for commands

---

## 📃 License

MIT (or your chosen license)
