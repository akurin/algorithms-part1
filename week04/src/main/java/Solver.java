import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Solver {
    private final List<Board> solution;

    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();

        this.solution = findSolution(initial);
    }

    private List<Board> findSolution(Board initial) {
        SolutionPath solutionPath = new SolutionPath(initial);
        SolutionPath twinSolutionPath = new SolutionPath(initial.twin());

        while (true) {
            boolean solutionHasReachedGoal = !solutionPath.hasNext();
            boolean twinSolutionHasReachedGoal = !twinSolutionPath.hasNext();

            if (twinSolutionHasReachedGoal) {
                return null;
            }

            if (solutionHasReachedGoal) {
                SearchNode searchNode = solutionPath.next();
                return searchNode.reconstruct();
            }
        }
    }

    public boolean isSolvable() {
        return solution != null;
    }

    public int moves() {
        return solution == null ? -1 : solution.size() - 1;
    }

    public Iterable<Board> solution() {
        return solution;
    }

    public static void main(String[] args) {
    }

    private class SearchNode implements Comparable<SearchNode> {
        private SearchNode prev;
        private Board board;
        private int distance;

        public SearchNode(Board board) {
            this.board = board;
            distance = 0;
        }

        public SearchNode(SearchNode prev, Board board, int distance) {
            this.prev = prev;
            this.board = board;
            this.distance = distance;
        }

        public int compareTo(SearchNode another) {
            return (board.manhattan() + distance) - (another.board.manhattan() + another.distance);
        }

        public Iterable<SearchNode> neighbors() {
            ArrayList<SearchNode> result = new ArrayList<>();

            for (Board board : board.neighbors()) {
                result.add(new SearchNode(this, board, distance + 1));
            }

            return result;
        }

        public boolean isGoal() {
            return board.isGoal();
        }

        public List<Board> reconstruct() {
            List<Board> result = new ArrayList<>();

            SearchNode currentSearchNode = this;
            while (currentSearchNode != null) {
                result.add(currentSearchNode.board);
                currentSearchNode = currentSearchNode.prev;
            }

            Collections.reverse(result);
            return result;
        }

        @Override
        public String toString() {
            return board.toString();
        }

        public boolean goesBack() {
            return prev != null && prev.prev != null && board.equals(prev.prev.board);
        }
    }

    private class SolutionPath implements Iterator<SearchNode> {
        MinPQ<SearchNode> minPQ;
        SearchNode next;

        public SolutionPath(Board initialBoard) {
            minPQ = new MinPQ<>();
            minPQ.insert(new SearchNode(initialBoard));
        }

        public boolean hasNext() {
            next = minPQ.delMin();

            for (SearchNode neighbor : next.neighbors()) {
                if (!next.goesBack()) {
                    minPQ.insert(neighbor);
                }
            }

            return !next.isGoal();
        }

        public SearchNode next() {
            return next;
        }
    }
}
