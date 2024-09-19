public class Main {

    public static void main(String[] args) {
        //creating the game field
        char[][] gameField = createEmptyBoard();
        printBoard(gameField);

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
}
