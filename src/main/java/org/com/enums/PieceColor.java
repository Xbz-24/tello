package org.com.enums;

public enum PieceColor {
    BLACK,
    WHITE,
    NONE;
    public CellState toCellState(){
        switch(this){
            case NONE: return CellState.EMPTY;
            case BLACK: return CellState.BLACK;
            case WHITE: return CellState.WHITE;
            default: throw new IllegalStateException("Invalid PieceColor: " + this);
        }
    }
    public PieceColor getOpponentColor(){
        switch(this){
            case BLACK: return WHITE;
            case WHITE: return BLACK;
            default: throw new IllegalStateException("No opponent color for: " + this);
        }
    }
}

