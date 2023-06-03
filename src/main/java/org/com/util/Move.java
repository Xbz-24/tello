package org.com.util;

import java.util.ArrayList;
import java.util.List;

public class Move {
    private final int row;
    private final int col;
    private final List<Position> flippedPositions;
    public Move(int row, int col) {
        this.row = row;
        this.col = col;
        this.flippedPositions = new ArrayList<>();
    }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public List<Position> getFlippedPositions() { return flippedPositions; }
    public void addFlippedPosition(int row, int col) { flippedPositions.add(new Position(row,col)); }
    public void addFlippedPositions(List<Position> positions){
        flippedPositions.addAll(positions);
    }
}
