package org.com.controller;

import org.com.enums.CellState;
import org.com.enums.Direction;
import org.com.enums.PieceColor;
import org.com.model.Board;
import org.com.model.Player;
import org.com.view.GameView;

public class GameController {
    private final Board board;
    private final Player[] players;
    private final GameView gameView;
    private int currentPlayerIndex;
    public GameController(){
        this.board = new Board(8);
        this.players = new Player[2];
        this.currentPlayerIndex = 0;
        this.gameView = new GameView();
    }
    public void startGame(){
        gameView.displayMessage("Welcome to Othello!");

        initializePlayers();
        playGame();
    }
    private void initializePlayers(){
        players[0] = new Player("Player 1", PieceColor.WHITE);
        players[1] = new Player("Player 2", PieceColor.BLACK);
    }
    private void playGame(){
        gameView.displayBoard(board);

        while(!isGameOver()){
            Player currentPlayer = getCurrentPlayer();
            String move = getPlayerMove(currentPlayer);

            if(isValidMove(move)){
                int[] coordinates = parseMove(move);
                int row = coordinates[0];
                int col = coordinates[1];

                if(board.isValidMove(row, col, currentPlayer.getColor().toCellState())){
                    board.setCellState(row,col,currentPlayer.getColor().toCellState());
                    flipOpponentsPieces(row, col, currentPlayer.getColor());
                    currentPlayerIndex = (currentPlayerIndex + 1) % 2;
                    gameView.updateBoard(board);
                }else{
                    gameView.displayMessage("Invalid move. Please try again.");
                }
            } else {
                gameView.displayMessage("Invalid input. Please try again.");
            }
        }
        PieceColor winnerColor = determineWinner();
        displayGameResult(winnerColor);
    }

    private boolean isGameOver(){
        return !board.hasValidMove(players[0].getColor()) && !board.hasValidMove(players[1].getColor());
    }
    private Player getCurrentPlayer(){
        return players[currentPlayerIndex];
    }
    private String getPlayerMove(Player currentPlayer){
        gameView.displayMessage(currentPlayer.getName() + "'s turn (" + currentPlayer.getColor() + ")");
        gameView.displayMessage("Enter your move (e.g., A1): ");
        return gameView.promptPlayerInput();
    }
    private boolean isValidMove(String move){
        return move.matches("^[A-Ha-h][1-8]$");
    }
    private int[] parseMove(String move){
        int row = Character.toUpperCase(move.charAt(0)) - 'A';
        int col = Character.getNumericValue(move.charAt(1)) - 1;
        return new int[]{row,col};
    }
    private void flipOpponentsPieces(int row, int col, PieceColor currentPlayerColor){
        for(Direction direction : Direction.values()){
            int currentRow = row + direction.getRowDelta();
            int currentCol = col + direction.getColDelta();
            if(canFlipPiecesInDirection(currentRow, currentCol, direction, currentPlayerColor )){
                flipPiecesInDirection(row, col, direction, currentPlayerColor);
            }
        }
    }

    private void flipPiecesInDirection(int row , int col, Direction direction, PieceColor currentPlayerColor) {
        int currentRow = row + direction.getRowDelta();
        int currentCol = col + direction.getColDelta();
        CellState opponentColor = currentPlayerColor.getOpponentColor().toCellState();

        while(board.isValidCoordinate(currentRow, currentCol)){
            CellState currentCellColor = board.getCellState(currentRow, currentCol);

            if(currentCellColor == opponentColor){
                board.setCellState(currentRow, currentCol, currentPlayerColor.toCellState());
            }else{
                break;
            }
            currentRow += direction.getRowDelta();
            currentCol += direction.getColDelta();
        }
    }

    private boolean canFlipPiecesInDirection(int row, int col, Direction direction, PieceColor currentPlayerColor) {
        int currentRow = row + direction.getRowDelta();
        int currentCol = col + direction.getColDelta();
        CellState opponentColor = currentPlayerColor.getOpponentColor().toCellState();
        boolean foundOpponentDisc = false;

        while (board.isValidCoordinate(currentRow, currentCol)) {
            CellState currentCellColor = board.getCellState(currentRow, currentCol);

            if (isEmptyCell(currentCellColor)) {
                return false;
            }
            if (isCurrentPlayerColor(currentCellColor,currentCellColor) && foundOpponentDisc) {
                return true;
            }
            if (isOpponentColor(currentCellColor, opponentColor)) {
                foundOpponentDisc = true;
            }
            currentRow += direction.getRowDelta();
            currentCol += direction.getColDelta();
        }
        return false;
    }

    private boolean isOpponentColor(CellState cellState, CellState opponentColor) {
        return cellState == opponentColor;
    }

    private boolean isEmptyCell(CellState cellState) {
        return cellState == CellState.EMPTY;
    }
    private boolean isCurrentPlayerColor(CellState cellState, CellState currentPlayerColor) {
        return cellState == currentPlayerColor;
    }

    private PieceColor determineWinner() {
        int blackCount = board.getPieceCount(CellState.BLACK);
        int whiteCount = board.getPieceCount(CellState.WHITE);

        if (whiteCount > blackCount) {
            return PieceColor.WHITE;
        } else if (blackCount > whiteCount) {
            return PieceColor.BLACK;
        } else{
            return PieceColor.NONE;
        }
    }
    private void displayGameResult(PieceColor winnerColor){
        gameView.displayMessage("Game over.");
        if(winnerColor == PieceColor.NONE){
            gameView.displayMessage("The game ended in a tie.");
        } else {
            Player winner = getPlayerByColor(winnerColor);
            gameView.displayMessage("The winner is: " + winner.getName());
        }
    }
    private Player getPlayerByColor(PieceColor color){
        for(Player player : players){
            if(player.getColor() == color){
                return player;
            }
        }
        return null;
    }
}
