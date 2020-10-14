/* *****************************************************************************
 *  Topic:    8 puzzle
 *            The 8-puzzle is a sliding puzzle that is played on a 3-by-3 grid
 *            with 8 square tiles labeled 1 through 8, plus a blank square. The goal
 *            is to rearrange the tiles so that they are in row-major order, using
 *            as few moves as possible. We are permitted to slide tiles either
 *            horizontally or vertically into the blank square.
 *
 *  @author:  Ying Chu
 **************************************************************************** */

import edu.princeton.cs.algs4.Queue;

public class Board {
    private final int[][] tiles;
    private final int n; // size of grid

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {

        if (tiles == null || tiles.length != tiles[0].length) {
            throw new java.lang.IllegalArgumentException();
        }

        this.n = tiles.length;
        this.tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    // The toString() method returns a string composed of n + 1 lines. The first line contains the board size n; the remaining n lines contains the n-by-n grid of tiles in row-major order, using 0 to designate the blank square.
    public String toString() {
        StringBuilder result = new StringBuilder();
        // board size
        result.append(n);
        result.append("\n");
        // the remaining board
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                result.append(tiles[i][j] + "  ");
            }
            result.append("\n");
        }
        return String.valueOf(result);
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    // The Hamming priority function is the Hamming distance of a board plus the
    // number of moves made so far to get to the search node. Intuitively, a search
    // node with a small number of tiles in the wrong position is close to the goal,
    // and we prefer a search node if has been reached using a small number of moves.
    public int hamming() {
        int hammingDist = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) continue;
                if (tiles[i][j] != i * n + j + 1) {
                    hammingDist += 1;
                }
            }
        }
        return hammingDist;
    }

    // sum of Manhattan distances between tiles and goal
    // The Manhattan priority function is the Manhattan distance of a board plus
    // the number of moves made so far to get to the search node.
    public int manhattan() {
        int manhattanDist = 0;
        int correctRow;
        int correctCol;
        //Goal
        for (int i = 0; i < n; i++) {
            for (int j =  0; j < n; j++)
                if (tiles[i][j] != 0 && tiles[i][j] != i * n + j + 1) {
                    correctRow = (tiles[i][j] - 1) / n;
                    correctCol = (tiles[i][j] - 1) % n;
                    manhattanDist += Math.abs(correctRow - i) + Math.abs(correctCol - j);
                }
        }
        return manhattanDist;
    }

    // is this board the goal board?
    public boolean isGoal() {
        return this.hamming() == 0;
    }

    // does this board equal y? Two boards are equal if they are have the same size and their corresponding tiles are in the same positions.
    public boolean equals(Object y) {
        // if y is itself
        if (this == y) {
            return true;
        }
        // if y is null
        if (y == null) {
            return false;
        }
        // check if the classes are the same
        if (y.getClass() != this.getClass()) {
            return false;
        }
        // compare dimension
        if (this.n != ((Board) y).n) {
           return false;
        }
        // see if all tiles are the same
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != ((Board) y).tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
     }

    // all neighboring boards
    // Returns an iterable containing the neighbors of the board. Depending on the location of the blank square, a board can have 2, 3, or 4 neighbors.
    public Iterable<Board> neighbors() {
        // find where the space is
        Queue<Board> neighborQueue = new Queue<Board>();
        int spaceRow = 0;
        int spaceCol = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == 0) {
                    spaceRow = i;
                    spaceCol = j;
                    break;
                }
            }
        }
        // if not in the first row, space can switch with the block above
        if (spaceRow > 0) {
            neighborQueue.enqueue(new Board(exchange(spaceRow, spaceCol, spaceRow - 1, spaceCol)));
        }
        // if not in the last row, space can switch with the block below
        if (spaceRow < n - 1) {
            neighborQueue.enqueue(new Board(exchange(spaceRow, spaceCol, spaceRow + 1, spaceCol)));
        }
        // switch block on the left
        if (spaceCol > 0) {
            neighborQueue.enqueue(new Board(exchange(spaceRow, spaceCol, spaceRow, spaceCol - 1)));
        }
        // switch block on the right
        if (spaceCol < n - 1) {
            neighborQueue.enqueue(new Board(exchange(spaceRow, spaceCol, spaceRow, spaceCol + 1)));
        }
        return neighborQueue;
    }

    private int[][] exchange(int row1, int col1, int row2, int col2) {
        int[][] neighborTiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                neighborTiles[i][j] = tiles[i][j];
            }
        }

        int tmp = neighborTiles[row1][col1];
        neighborTiles[row1][col1] = neighborTiles[row2][col2];
        neighborTiles[row2][col2] = tmp;
        return neighborTiles;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n - 1; j++) {
                if ((tiles[i][j] != 0) && (tiles[i][j + 1] != 0)) {
                    return new Board(exchange(i, j, i, j + 1));
                }
            }
        }
        throw new RuntimeException();
    }
}
