/**
 *
 *
 * https://www.hackerrank.com/contests/crescent-practice-3rd-years/challenges/8-queens-problem
 * Only shows the first possible combination
 *
 */


public class EightQueens {

  private static boolean solved = false;

  public static void main(String[] args) {
    int[][] board = new int[8][8];
    int locatedQueenCount = 0;

    printCoordinates(board, locatedQueenCount);

  }

  private static void printCoordinates(int[][] board, int locatedQueenCount) {
    for (int i = 0; i < 8; i++) {
      for (int j = 0; j < 8; j++) {
        if (board[i][j] == 0) {
          if (locatedQueenCount < 7) {
            printCoordinates(updateBoard(board, locatedQueenCount + 1, i, j), locatedQueenCount + 1);
            if(solved) return;
          } else {
            solved = true;
            int pos = 1;
            for (int k = 0; k < 8; k++) {
              for (int l = 0; l < 8; l++) {
                if (board[k][l] > 0) {
                  System.out.println("Coordinates of Q" + (pos++) + ": " + k + "," + l);
                }
              }
            }
            System.out.println("Coordinates of Q" + (pos++) + ": " + i + "," + j);
            System.out.println("---");
            System.out.println();
          }
        }
      }
    }
  }

  private static int[][] updateBoard(int[][] board, int queenNumber, int i, int j) {
    int[][] newBoard = new int[8][8];

    for (int k = 0; k < 8; k++) {
      for (int l = 0; l < 8; l++) {
        newBoard[k][l] = board[k][l];
      }
    }

    newBoard[i][j] = queenNumber;
    for (int k = 0; k < 8; k++) {
      if (k != i) {
        newBoard[k][j] = -1;
      }
    }

    for (int k = 0; k < 8; k++) {
      if (k != j) {
        newBoard[i][k] = -1;
      }
    }
    int k = i;
    int l = j;
    while (k < 7 && l < 7) {
      newBoard[++k][++l] = -1;
    }
    k = i;
    l = j;
    while (k < 7 && l > 0) {
      newBoard[++k][--l] = -1;
    }
    k = i;
    l = j;
    while (k > 0 && l < 7) {
      newBoard[--k][++l] = -1;
    }
    k = i;
    l = j;
    while (k > 0 && l > 0) {
      newBoard[--k][--l] = -1;
    }

    return newBoard;
  }

}
