package org.com.model;

import org.com.enums.CellState;
import org.com.enums.PieceColor;
import org.com.util.Move;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final int size;
    private final Cell[][] cells;
    private int blackCount;
    private int whiteCount;
    public Board(int size){
        this.size = size;
        cells = new Cell[size][size];
        initializeBoard();
    }

    private void initializeBoard() {
        createEmptyCells();
        placeInitialPieces();
    }


    private void createEmptyCells(){
        for(int i = 0; i<size; i++){
            for(int j = 0; j<size; j++){
                cells[i][j] = new Cell(i, j);
            }
        }
    }

    private void placeInitialPieces() {
        int mid = size / 2;
        cells[mid - 1][mid - 1].setState(CellState.WHITE);
        cells[mid][mid].setState(CellState.WHITE);
        cells[mid - 1][mid].setState(CellState.BLACK);
        cells[mid][mid - 1].setState(CellState.BLACK);
        blackCount = 2;
        whiteCount = 2;
    }
    public int getSize() {
        return size;
    }
    public CellState getCellState(int x, int y){
        return cells[x][y].getState();
    }
    public void setCellState(int x, int y, CellState state){
        CellState currentState = cells[x][y].getState();
        if(currentState == CellState.BLACK){
            blackCount--;
        }else if(currentState == CellState.WHITE){
            whiteCount--;
        }
        cells[x][y].setState(state);
        if(state == CellState.BLACK){
            blackCount++;
        }else if(state == CellState.WHITE){
            whiteCount++;
        }
    }
    public List<Move> getPossibleMoves(CellState player){
        List<Move> possibleMoves = new ArrayList<>();
        for(int row = 0; row < size; row++){
            for(int col = 0; col < size; col++){
                if(isValidMove(row, col, player)){
                    possibleMoves.add(new Move(row, col));
                }
            }
        }
        return possibleMoves;
    }
    public boolean isValidMove(int row, int col, CellState player){
        if(getCellState(row, col) != CellState.EMPTY){
            return false;
        }

        for(int dr = -1; dr <= 1; dr++){
            for(int dc = -1; dc <= 1; dc++){
                if(dr == 0 && dc == 0){
                    continue;
                }
                if(isValidDirection(row, col, dr, dc, player)){
                    return true;
                }
            }
        }
        return false;
    }
    private boolean isValidDirection(int row, int col, int dr, int dc, CellState player){
        int currentRow = row + dr;
        int currentCol = col + dc;
        // logical error
        CellState opponentState = player.toPieceColor().toCellState(); //TODO: fix this
        // CellState opponentState = player.opposite().toCellState();
        boolean foundOpponentDisc = false;


        while(isValidCoordinate(currentRow,currentCol)){
            CellState currentCellState = cells[currentRow][currentCol].getState();

            if(currentCellState == CellState.EMPTY){
                break;
            }
            else if(currentCellState == player) {
                if(foundOpponentDisc){
                    return true;
                }
                break;
            }
            else if(currentCellState == opponentState) {
                foundOpponentDisc = true;
            }
            currentRow += dr;
            currentCol += dc;
        }

        return false;
    }
    public boolean isValidCoordinate(int currentRow, int currentCol) {
        return currentRow >= 0 && currentRow < size && currentCol >= 0 && currentCol < size;
    }
    public int getPieceCount(CellState player) {
        if(player == CellState.BLACK){
            return blackCount;
        }
        else if(player == CellState.WHITE){
            return whiteCount;
        }
        else{
            return 0;
        }
    }

    public boolean hasValidMove(PieceColor color) {
        return !getPossibleMoves(color.toCellState()).isEmpty();
    }
}
