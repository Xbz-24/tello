package org.com.enums;

public enum CellState {
    EMPTY,
    BLACK,
    WHITE;

    public PieceColor toPieceColor() {
        switch(this){
            case EMPTY: return PieceColor.NONE;
            case BLACK: return PieceColor.BLACK;
            case WHITE: return PieceColor.WHITE;
            default: throw new IllegalStateException("Invalid CellState: " + this);
        }
    }
}
