package org.com.model;

import org.com.enums.CellState;
import org.com.enums.Direction;
import org.com.enums.PieceColor;

public class Cell {
    private int row;
    private int col;
    private PieceColor owner;

    public Cell(int row, int col){
        this.row = row;
        this.col = col;
        this.owner = PieceColor.NONE;
    }
    public Cell(int row, int col, PieceColor owner){
        this.row = row;
        this.col = col;
        this.owner = owner;
    }
    public int getRow() { return row; }
    public int getCol() { return col; }
    public PieceColor getOwner() { return owner; }
    public void setOwner(PieceColor owner) { this.owner = owner; }
    public boolean isOwned() { return owner != PieceColor.NONE; }
    public boolean isOwnedBy(PieceColor playerColor) { return owner == playerColor; }
    public CellState getState(){
        return owner.toCellState();
    }
    public void setState(CellState state){
        this.owner = state.toPieceColor();
    }
    @Override
    public String toString() {
        return "Cell{" +
                "row=" + row +
                ", col=" + col +
                ", owner=" + owner +
                '}';
    }
}
