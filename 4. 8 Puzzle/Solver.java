/* *****************************************************************************
 *  Topic:    Solution--Game Tree
 *  Methods:
 *  1. Each search node is a node in the game tree.
 *  2. The children of a node correspond to its neighboring search nodes.
 *  3. The root of the game tree is the initial search node; the internal nodes
 *     have already been processed; the leaf nodes are maintained in a priority queue.
 *  4. At each step, the A* algorithm removes the node with the smallest priority
 *     from the priority queue and processes it.
 *  @author:  Ying Chu
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

public class Solver {
    private Stack<Board> solutionList = new Stack<Board>(); // to record pathway
    private boolean solvable;
    private SearchNode min;
    private int moves;

    private class SearchNode implements Comparable<SearchNode> {
        private final Board board;
        private final SearchNode parent;
        private boolean isTwin;
        private final int move;
        private final int priority;


        public SearchNode(Board board, SearchNode parent, boolean isTwin, int move) {
            this.board = board;
            this.parent = parent;
            this.isTwin = isTwin;
            this.move = move;
            priority = this.board.manhattan() + move;
        }

        @Override
        public int compareTo(SearchNode that) {
            return Integer.compare(this.priority, that.priority);
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // corner cases
        if (initial == null) {
            throw new IllegalArgumentException("The board is null");
        }

        MinPQ<SearchNode> pq = new MinPQ<SearchNode>();
        SearchNode initialNode = new SearchNode(initial, null, false, 0);
        SearchNode twinNode = new SearchNode(initial.twin(), null, true, 0);
        pq.insert(initialNode);
        pq.insert(twinNode);

        // A* algorithm
        while (true) {
            // original
            min = pq.delMin();

            // In the beginning, if the initial min == initial's twin, then the puzzle is unsolvable
            if (min.board.isGoal()) {
                // if min turns our to be a twin, then the puzzle is unsolvable.
                if (!min.isTwin) {
                    solvable = true;
                    moves = min.move;
                    break;
                } else {
                    solvable = false;
                    moves = -1;
                    break;
                }
            }
            for (Board neighbor : min.board.neighbors()) {
                SearchNode neighNode = new SearchNode(neighbor, min, min.isTwin, min.move + 1);
                if (min.parent == null) {
                    pq.insert(neighNode);
                } else if (!neighbor.equals(min.parent.board)) {
                    pq.insert(neighNode);
                }
            }
        }
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
            return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return moves; // the last min's moves
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (isSolvable()) {
            while (min != null) {
                solutionList.push(min.board);
                min = min.parent;
            }
        } else {
            solutionList = null;
        }
        return solutionList;
    }

    // test client (see below)
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
