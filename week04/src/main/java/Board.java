import edu.princeton.cs.algs4.StdRandom;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private int[][] blocks;

    public Board(int[][] blocks) {
        if (blocks == null) throw new IllegalArgumentException();

        this.blocks = copy(blocks);
    }

    private static int[][] copy(int[][] blocks) {
        int[][] result = new int[blocks.length][blocks.length];
        for (int row = 0; row < blocks.length; row++) {
            for (int column = 0; column < blocks.length; column++) {
                result[row][column] = blocks[row][column];
            }
        }

        return result;
    }

    public int dimension() {
        return blocks.length;
    }

    public int hamming() {
        int blocksOutOfPlace = 0;

        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                int cellIndex = row * dimension() + column + 1;
                int expectedNumber = cellIndex < dimension() * dimension() ? cellIndex : 0;

                int currentBlock = blocks[row][column];
                if (currentBlock != 0 && currentBlock != expectedNumber) {
                    blocksOutOfPlace++;
                }
            }
        }

        return blocksOutOfPlace;
    }

    public int manhattan() {
        int distance = 0;

        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                int currentBlock = blocks[row][column];
                int currentBlockExpectedRow = (currentBlock - 1) / dimension();
                int currentBlockExpectedColumn = (currentBlock - 1) % dimension();

                if (currentBlock != 0) {
                    distance += Math.abs(currentBlockExpectedRow - row) + Math.abs(currentBlockExpectedColumn - column);
                }
            }
        }

        return distance;
    }

    public boolean isGoal() {
        return hamming() == 0;
    }

    public Board twin() {
        while (true) {
            int rowOfFirst = StdRandom.uniform(0, dimension());
            int columnOfFirst = StdRandom.uniform(0, dimension());
            if (blocks[rowOfFirst][columnOfFirst] == 0) continue;

            int rowOfSecond = StdRandom.uniform(0, dimension());
            int columnOfSecond = StdRandom.uniform(0, dimension());
            if (blocks[rowOfSecond][columnOfSecond] == 0) continue;

            return swap(new Position(rowOfFirst, columnOfFirst), new Position(rowOfSecond, columnOfSecond));
        }
    }

    private Board swap(Position p1, Position p2) {
        int[][] blocksCopy = copy(blocks);
        int tmp = blocksCopy[p1.row()][p1.column()];
        blocksCopy[p1.row()][p1.column()] = blocksCopy[p2.row()][p2.column()];
        blocksCopy[p2.row()][p2.column()] = tmp;
        return new Board(blocksCopy);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Board board = (Board) o;

        return Arrays.deepEquals(blocks, board.blocks);

    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(blocks);
    }

    public Iterable<Board> neighbors() {
        List<Board> result = new ArrayList<Board>();

        Position emptyBlockPosition = emptyBlockPosition();

        Position[] moveDirections = {
                new Position(0, -1),
                new Position(0, 1),
                new Position(-1, 0),
                new Position(1, 0)
        };

        for (Position moveDirection : moveDirections) {
            Position emptyBlockNewPosition = emptyBlockPosition.move(moveDirection);
            if (emptyBlockNewPosition.row >= 0 && emptyBlockNewPosition.row < dimension() &&
                    emptyBlockNewPosition.column() >= 0 && emptyBlockNewPosition.column() < dimension()) {
                result.add(swap(emptyBlockPosition, emptyBlockNewPosition));
            }
        }

        return result;
    }

    private Position emptyBlockPosition() {
        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                if (blocks[row][column] == 0) {
                    return new Position(row, column);
                }
            }
        }

        throw new IllegalStateException();
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(dimension() + "\n");
        for (int row = 0; row < dimension(); row++) {
            for (int column = 0; column < dimension(); column++) {
                s.append(String.format("%2d ", blocks[row][column]));
            }

            s.append("\n");
        }

        return s.toString();
    }

    public static void main(String[] args) {
    }

    private class Position {
        private int row;
        private int column;

        public Position(int row, int column) {
            this.row = row;
            this.column = column;
        }

        public int row() {
            return row;
        }

        public int column() {
            return column;
        }

        public Position move(Position moveDirection) {
            return new Position(row + moveDirection.row(), column + moveDirection.column());
        }
    }
}
