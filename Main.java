import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        //creating the game field
        char[][] gameField = createEmptyBoard();
        printBoard(gameField);
        System.out.println();

        //input ship coordinates - start and end points of the ship
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the coordinates of the ship:");
        String input = scanner.nextLine();
        String[] coordinates = input.split(" ");

        //validating the input - two coordinates are needed; basic error checking
        if (coordinates.length != 2) {
            System.out.println("Error!");
        }

        String start = coordinates[0];
        String end = coordinates[1];

        //coordinates parsing (converting input strings into numeric coordinates)
        int[] startCoords = parseCoordinates(start);
        int[] endCoords = parseCoordinates(end);

        //validating the input - two coordinates are needed; basic error checking
        if (startCoords == null || endCoords == null) {
            System.out.println("Error!");
            return;
        }

        //validate if the ship is in same row or column
        if (!isValidShipPlacement(startCoords, endCoords)) {
            System.out.println("Error!");
            return;
        }

        //calculate ship length
        int length = calculateShipLength(startCoords, endCoords);

        //generate positions of ship parts based on coordinates and length
        String[] shipParts = getShipParts(startCoords, endCoords, length);

        //output ship length and parts
        System.out.println("Length: " + length);
        System.out.print("Parts: ");
        for (String part : shipParts) {
            System.out.print(part + " ");
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
        for(int i = 0; i < 10; i++) {
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

    private static String[] getShipParts(int[] startCoords, int[] endCoords, int length) {
        String[] parts = new String[length]; //length of array is number of cells occupied

        if (startCoords[0] == endCoords[0]) { //ship is horizontal
            int row = startCoords[0]; //storing row index
            int startCol = Math.min(startCoords[1], endCoords[1]); //user coordinates input either way
            //generating horizontal ship:
            for (int i = 0; i < length; i++) {
                parts[i] = (char) ('A' + row) + String.valueOf(startCol + i + 1);
            }
        } else { //ship is vertical
            int col = startCoords[1]; //storing column index
            int startRow = Math.min(startCoords[0], endCoords[0]);
            //generating vertical ship:
            for (int i = 0; i < length; i++) {
                parts[i] = (char) ('A' + startRow + i) + String.valueOf(col + 1); //column number for each part
            }

        }
        return parts;
    }
}
