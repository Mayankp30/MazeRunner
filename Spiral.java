/**
 * Created by Mayank on 7/20/16.
 */

public class Spiral {
    //char board.

    private char[][] board;

    public static void main(String[] args) {

        Spiral test = new Spiral();

        test.start(1);
    }

    public void start(int size) {
        board = new char[size][size];
        this.drawTop(0, 0, size);
    }

    private void drawTop(int x, int y, int size) {
        for (int counter = 0; counter < size; counter++) {
            board[x++][y] = '*';
        }
        if (--size > 0) {
            this.drawRight(--x, ++y, size);
        } else {
            printBoard();
        }
    }

    private void drawRight(int x, int y, int size) {
        for (int counter = 0; counter < size; counter++) {
            board[x][y++] = '*';
        }
        if (--size > 0) {
            this.drawBottom(--x, --y, size);
        } else {
            printBoard();
        }
    }

    private void drawBottom(int x, int y, int size) {
        for (int counter = 0; counter < size; counter++) {
            board[x--][y] = '*';
        }
        if (--size > 0) {
            this.drawLeft(++x, --y, size);
        } else {
            printBoard();
        }
    }

    private void drawLeft(int x, int y, int size) {
        for (int counter = 0; counter < size; counter++) {
            board[x][y--] = '*';
        }
        if (--size > 0) {
            this.drawTop(++x, ++y, size);
        } else {
            printBoard();
        }
    }

    private void printBoard() {
        for (int y = 0; y < board.length; y++) {
            for (char[] board1 : board) {
                System.out.print(board1[y]);
            }
            System.out.println();
        }
    }
}
