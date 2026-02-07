package chess;

import java.util.Arrays;

/**
 * A chessboard that can hold and rearrange chess pieces.
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessBoard {
    private final ChessPiece[][] board;
    public ChessBoard() {
        board = new ChessPiece[8][8];

    }

    /**
     * Adds a chess piece to the chessboard
     *
     * @param position where to add the piece to
     * @param piece    the piece to add
     */
    public void addPiece(ChessPosition position, ChessPiece piece) {
        //throw new RuntimeException("Not implemented");
        board[position.getRow() - 1][position.getColumn() - 1] = piece;
    }

    /**
     * Gets a chess piece on the chessboard
     *
     * @param position The position to get the piece from
     * @return Either the piece at the position, or null if no piece is at that
     * position
     */
    public ChessPiece getPiece(ChessPosition position) {
        //throw new RuntimeException("Not implemented");
        return board[position.getRow() - 1][position.getColumn() - 1]; //replace this with the above line later
    }

    /**
     * Sets the board to the default starting board
     * (How the game of chess normally starts)
     */
    public void resetBoard() {
        for (int row = 1; row < 8; row++) {
            for (int col = 1; col < 8; col++) {
                addPiece(new ChessPosition(row, col), null);
            }
        }

        ChessPiece bBishop = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.BISHOP);
        ChessPiece bRook = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.ROOK);
        ChessPiece bKing = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KING);
        ChessPiece bKnight = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.KNIGHT);
        ChessPiece bQueen = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.QUEEN);
        ChessPiece bPawn = new ChessPiece(ChessGame.TeamColor.BLACK, ChessPiece.PieceType.PAWN);
        ChessPiece wBishop = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.BISHOP);
        ChessPiece wRook = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.ROOK);
        ChessPiece wKing = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KING);
        ChessPiece wKnight = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.KNIGHT);
        ChessPiece wQueen = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.QUEEN);
        ChessPiece wPawn = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);

        addPiece(new ChessPosition(8,1), bRook);
        addPiece(new ChessPosition(8,8), bRook);
        addPiece(new ChessPosition(8,2), bKnight);
        addPiece(new ChessPosition(8,7), bKnight);
        addPiece(new ChessPosition(8,3), bBishop);
        addPiece(new ChessPosition(8,6), bBishop);
        addPiece(new ChessPosition(8,4), bQueen);
        addPiece(new ChessPosition(8,5), bKing);
        addPiece(new ChessPosition(1,1), wRook);
        addPiece(new ChessPosition(1,8), wRook);
        addPiece(new ChessPosition(1,2), wKnight);
        addPiece(new ChessPosition(1,7), wKnight);
        addPiece(new ChessPosition(1,3), wBishop);
        addPiece(new ChessPosition(1,6), wBishop);
        addPiece(new ChessPosition(1,4), wQueen);
        addPiece(new ChessPosition(1,5), wKing);

        addPiece(new ChessPosition(7, 1), bPawn);
        addPiece(new ChessPosition(7, 2), bPawn);
        addPiece(new ChessPosition(7, 3), bPawn);
        addPiece(new ChessPosition(7, 4), bPawn);
        addPiece(new ChessPosition(7, 5), bPawn);
        addPiece(new ChessPosition(7, 6), bPawn);
        addPiece(new ChessPosition(7, 7), bPawn);
        addPiece(new ChessPosition(7, 8), bPawn);

        addPiece(new ChessPosition(2, 1), wPawn);
        addPiece(new ChessPosition(2, 2), wPawn);
        addPiece(new ChessPosition(2, 3), wPawn);
        addPiece(new ChessPosition(2, 4), wPawn);
        addPiece(new ChessPosition(2, 5), wPawn);
        addPiece(new ChessPosition(2, 6), wPawn);
        addPiece(new ChessPosition(2, 7), wPawn);
        addPiece(new ChessPosition(2, 8), wPawn);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ChessBoard that = (ChessBoard) o;
        return Arrays.deepEquals(board, that.board);
    }

    @Override
    public int hashCode() {
        return Arrays.deepHashCode(board);
    }
}