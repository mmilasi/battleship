import java.util.Scanner;

public class Main {

    //ship information (name and length)
    static final String[][] SHIPS = {
            {"Aircraft Carrier", "5"},
            {"Battleship", "4"},
            {"Submarine", "3"},
            {"Cruiser", "3"},
            {"Destroyer", "2"}
    };

    public static void main(String[] args) {
        //creating the game field
        char[][] gameField = createEmptyBoard();
        printBoard(gameField);
        System.out.println();

        //input ship coordinates - start and end points of the ship
        Scanner scanner = new Scanner(System.in);

        for (String[] ship : SHIPS) {
            boolean validPlacement = false; //the placement is initially not validated

            while (!validPlacement) { //while placement is invalid (false)

                System.out.println("Enter the coordinates of the " + ship[0] + "(" + ship[1] + " cells):");
                String input = scanner.nextLine();
                String[] coordinates = input.split(" ");

                //validating the input - two coordinates are needed; basic error checking
                if (coordinates.length != 2) {
                    System.out.println("Error!");
                    continue;
                }

                String start = coordinates[0];
                String end = coordinates[1];

                //coordinates parsing (converting input strings into numeric coordinates)
                int[] startCoords = parseCoordinates(start);
                int[] endCoords = parseCoordinates(end);

                //validating the input - two coordinates not null are needed; basic error checking
                if (startCoords == null || endCoords == null) {
                    System.out.println("Error! Invalid coordinates. Try again.");
                    continue;
                }

                //validate if the ship is in same row or column
                if (!isValidShipPlacement(startCoords, endCoords)) {
                    System.out.println("Error! Wrong ship location! Try again.");
                    continue;
                }

                //calculate ship length
                int length = calculateShipLength(startCoords, endCoords);
                //validate ship length for current ship
                if (length != Integer.parseInt(ship[1])) {
                    System.out.println("Error! Wrong length of the " + ship[0] + "! Try again.");
                    continue;
                }

                //check if ship can be placed without collision
                if (!canPlaceShip(gameField, startCoords, endCoords)) {
                    System.out.println("Error! You placed it too close to another one. Try again.");
                    continue;
                }

                //place ship on game field
                placeShip(gameField, startCoords, endCoords);
                printBoard(gameField);
                System.out.println();
                validPlacement = true;
            }
        }

    }

    // methods area here --------------------------------------------------------------

    private static char[][] createEmptyBoard() {
        char[][] board = new char[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                board[i][j] = '~'; //filling the cells of the board with the water sign
            }
        }
        return board;
    }

    private static void printBoard(char[][] board) {
        System.out.print(" "); //spacing to adjust coordinates
        //printing the column numbers
        for (int i = 1; i <= 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println(); //visual separation from the column numbers
        char rowLabel = 'A'; //starting the row labeling from A moving through the alphabet
        for (int i = 0; i < 10; i++) {
            System.out.print(rowLabel + " "); //printing characters at beginning of each row
            for (int j = 0; j < 10; j++) {
                System.out.print(board[i][j] + " "); //accessing the values of the game board (in createEmptyBoard())
            }
            System.out.println(); //moving onto the next row in outer for loop
            rowLabel++; //after printing each row, increments alphabet char value
        }
    }

    private static int[] parseCoordinates(String coordinate) {
        if (coordinate.length() < 2 || coordinate.length() > 3) {
            return null;
        } //length validation

        //column number parsing:
        char row = coordinate.charAt(0); //extract first character of the coordinate string
        int col; //extract numeric part of the coordinate and convert it to integer
        try {
            col = Integer.parseInt(coordinate.substring(1)) - 1; //subtract 1 for java 0 indexing
        } catch (NumberFormatException e) {
            return null;
        }

        //converting row character into index:
        int rowIndex = row - 'A';
        //subtracting 'A' from numeric values based on ASCII codes of the characters
        //results in numeric index result (int)
        if (rowIndex < 0 || rowIndex >= 10 || col < 0 || col >= 10) { //checking for valid indices
            return null;
        }

        return new int[] {rowIndex, col}; //returning parsed coordinates
    }

    private static boolean isValidShipPlacement(int[] startCoords, int[] endCoords) {
        return (startCoords[0] == endCoords[0] || startCoords[1] == endCoords[1]);
    }

    private static int calculateShipLength(int[] startCoords, int[] endCoords) {
        if (startCoords[0] == endCoords[0]) {
            //using Math.abs for always positive difference between values
            return Math.abs(startCoords[1] - endCoords[1]) + 1; //horizontal ship
        } else {
            return Math.abs(startCoords[0] - endCoords[0]) + 1; //vertical ship
        }
    }

    private static void placeShip(char[][] board, int[] startCoords, int[] endCoords) {
        if (startCoords[0] == endCoords[0]) { //it's a horizontal ship
            int row = startCoords[0];
            int startCol = Math.min(startCoords[1], endCoords[1]);
            int endCol = Math.max(startCoords[1], endCoords[1]);
            for (int col = startCol; col <= endCol; col++) {
                board[row][col] = 'O';
            }
        } else { //vertical ship
            int col = startCoords[1];
            int startRow = Math.min(startCoords[0], endCoords[0]);
            int endRow = Math.max(startCoords[0], endCoords[0]);
            for (int row = startRow; row <= endRow; row++) {
                board[row][col] = 'O';
            }
        }
    }

    private static boolean canPlaceShip(char[][] board, int[] startCoords, int[] endCoords) {
        //looking for the start coordinates and end coordinates in the input with min and max
        int rowStart = Math.min(startCoords[0], endCoords[0]);
        int rowEnd = Math.max(startCoords[0], endCoords[0]);
        int colStart = Math.min(startCoords[1], endCoords[1]);
        int colEnd = Math.max(startCoords[1], endCoords[1]);

        for (int i = rowStart - 1; i <= rowEnd + 1; i++) {
            for (int j = colStart - 1; j <= colEnd + 1; j++) {
                if (i >= 0 && i < 10 && j >= 0 && j < 10) {
                    if (board[i][j] == 'O') { //checks for the ship symbol char 'O' in the cells
                        return false; //indicates there is a ship too close to another one
                    }
                }
            }
        }
        return true;
    }
}
