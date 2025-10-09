package chess;

import java.util.*;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private ChessBoard board;
    public ChessGame() {
        board = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        //if there is no piece at the location, return null
        ChessPiece piece = getBoard().getPiece(startPosition);
        if (piece == null) {
            return null;
        }
        //a move is valid if it is a piece move for the piece at the input position and leaving that
        //get all the moves based on the piece type
        Collection<ChessMove> allMoves = piece.pieceMoves(getBoard(), startPosition);

        //creating a new list for all possible valid moves to live in
        Collection<ChessMove> validMoves = new ArrayList<>();

        //position would not put the king in danger
        for (ChessMove moves : allMoves) {
            ChessBoard boardCopy = copy();
        }
        //return all moves that a piece can make
        return validMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to perform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {

        if (isInCheckPieceType(teamColor) == null) {
            return false;
        }
        else {
            return true;
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        if (!isInCheck(teamColor)) {
            return false;
        }
        isInCheckPieceType(teamColor) ==


        return true;
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves while not in check.
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        //default return
        return false;
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return board;
    }

    public ChessBoard copy() {
        ChessBoard newBoard = new ChessBoard();

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPosition newPos = new ChessPosition(row, col);
                ChessPiece piece = getBoard().getPiece(newPos);

                if (piece != null) {
                    ChessPiece pieceCopy = new ChessPiece(piece.getTeamColor(), piece.getPieceType());
                    newBoard.addPiece(newPos, pieceCopy);
                }
            }
        }
        return newBoard;
    }

    public ChessPosition getKingPos(TeamColor teamColor) {
        ChessBoard newBoard = new ChessBoard();
        ChessPosition kingPos = null;
        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                ChessPosition newPos = new ChessPosition(row, col);
                ChessPiece piece = getBoard().getPiece(newPos);

                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING && piece.getTeamColor() == teamColor) {
                    kingPos = newPos;
                    break;
                }
            }
            if (kingPos != null) {
                break;
            }
        }
        return kingPos;
    }

    private ChessPosition isInCheckPieceType(TeamColor teamColor) {
        ChessPosition kingPos = getKingPos(teamColor);
        ChessBoard board = getBoard();
        ChessPiece tempKnight = new ChessPiece(teamColor, ChessPiece.PieceType.KNIGHT);
        Collection<ChessMove> knightMoves = new ArrayList<ChessMove>();
        knightMoves = tempKnight.pieceMoves(board, kingPos);
        ChessPiece tempRook = new ChessPiece(teamColor, ChessPiece.PieceType.ROOK);
        Collection<ChessMove> rookMoves = new ArrayList<ChessMove>();
        rookMoves = tempRook.pieceMoves(board, kingPos);
        ChessPiece tempBishop = new ChessPiece(teamColor, ChessPiece.PieceType.BISHOP);
        Collection<ChessMove> bishopMoves = new ArrayList<ChessMove>();
        bishopMoves = tempKnight.pieceMoves(board, kingPos);
        ChessPiece tempQueen = new ChessPiece(teamColor, ChessPiece.PieceType.QUEEN);
        Collection<ChessMove> queenMoves = new ArrayList<ChessMove>();
        queenMoves = tempKnight.pieceMoves(board, kingPos);

        //check if in check by knight
        for (ChessMove move : knightMoves) {
            if (board.getPiece(move.getEndPosition()) != null) {
                if (board.getPiece(move.getEndPosition()).getTeamColor() != tempKnight.getTeamColor()) {
                    if (board.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.KNIGHT) {
                        return move.getEndPosition();
                    }
                }
            }
        }

        for (ChessMove move : rookMoves) {
            if (board.getPiece(move.getEndPosition()) != null) {
                if (board.getPiece(move.getEndPosition()).getTeamColor() != tempRook.getTeamColor()) {
                    if (board.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.ROOK) {
                        return move.getEndPosition();
                    }
                }
            }
        }

        for (ChessMove move : bishopMoves) {
            if (board.getPiece(move.getEndPosition()) != null) {
                if (board.getPiece(move.getEndPosition()).getTeamColor() != tempBishop.getTeamColor()) {
                    if (board.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.BISHOP) {
                        return move.getEndPosition();
                    }
                }
            }
        }

        for (ChessMove move : queenMoves) {
            if (board.getPiece(move.getEndPosition()) != null) {
                if (board.getPiece(move.getEndPosition()).getTeamColor() != tempQueen.getTeamColor()) {
                    if (board.getPiece(move.getEndPosition()).getPieceType() == ChessPiece.PieceType.QUEEN) {
                        return move.getEndPosition();
                    }
                }
            }
        }

        if (teamColor == TeamColor.WHITE) {
            if (kingPos.getRow() < 7 && kingPos.getColumn() > 1) {
                ChessPosition tempPawn = new ChessPosition(kingPos.getRow() + 1, kingPos.getColumn() + 1);
                if (board.getPiece(tempPawn) != null) {
                    if (board.getPiece(tempPawn).getTeamColor() != teamColor) {
                        if (board.getPiece(tempPawn).getPieceType() == ChessPiece.PieceType.PAWN) {
                            return tempPawn;
                        }
                    }
                }
            }
            if (kingPos.getRow() < 7 && kingPos.getColumn() < 8 ) {
                ChessPosition tempPawn = new ChessPosition(kingPos.getRow() + 1, kingPos.getColumn() - 1);
                if (board.getPiece(tempPawn) != null) {
                    if (board.getPiece(tempPawn).getTeamColor() != teamColor) {
                        if (board.getPiece(tempPawn).getPieceType() == ChessPiece.PieceType.PAWN) {
                            return tempPawn;
                        }
                    }
                }
            }
        }
        else {
            if (kingPos.getRow() > 2 && kingPos.getColumn() > 1) {
                ChessPosition tempPawn = new ChessPosition(kingPos.getRow() - 1, kingPos.getColumn() + 1);
                if (board.getPiece(tempPawn) != null) {
                    if (board.getPiece(tempPawn).getTeamColor() != teamColor) {
                        if (board.getPiece(tempPawn).getPieceType() == ChessPiece.PieceType.PAWN) {
                            return tempPawn;
                        }
                    }
                }
            }
            if (kingPos.getRow() > 2 && kingPos.getColumn() < 8 ) {
                ChessPosition tempPawn = new ChessPosition(kingPos.getRow() - 1, kingPos.getColumn() - 1);
                if (board.getPiece(tempPawn) != null) {
                    if (board.getPiece(tempPawn).getTeamColor() != teamColor) {
                        if (board.getPiece(tempPawn).getPieceType() == ChessPiece.PieceType.PAWN) {
                            return tempPawn;
                        }
                    }
                }
            }
        }
        //check left, right, down, up, & diagonals
        return null;
    }
}


