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
    private TeamColor teamTurn;
    private ChessMove lastMove;

    public ChessGame() {
        board = new ChessBoard();
        board.resetBoard();
        teamTurn = TeamColor.WHITE;
        lastMove = null;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        teamTurn = team;
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
        ChessPiece piece = board.getPiece(startPosition);
        if (piece == null) {
            return null;
        }
        //a move is valid if it is a piece move for the piece at the input position and leaving that
        //get all the moves based on the piece type
        Collection<ChessMove> allMoves = piece.pieceMoves(board, startPosition);

        //creating a new list for all possible valid moves to live in
        Collection<ChessMove> validMoves = new ArrayList<>();

        //position would not put the king in danger
        for (ChessMove moves : allMoves) {
            if (isValidMove(moves)) {
                validMoves.add(moves);
            }
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
        ChessPosition kingPos = getKingPos(teamColor);
        if (kingPos == null) {
            return false;
        }
        return isUnderAttack(kingPos, teamColor);
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

        //need to check if there are any pieces that can kill or block opposing team's piece that puts king in check
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition currPos = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(currPos);
                if (piece != null && piece.getTeamColor() == teamColor) {
                    Collection<ChessMove> moves = piece.pieceMoves(board, currPos);
                    for (ChessMove move : moves) {
                        if (isValidMove(move)) {
                            return false;
                        }
                    }
                }
            }
        }
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
        if (isInCheck(teamColor)) {
            return false;
        }

        //if pieces keep moving back and forth with no way of winning, return true
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getTeamColor() == teamColor) {
                    Collection<ChessMove> moves = piece.pieceMoves(board, position);
                    for (ChessMove move : moves) {
                        if (isValidMove(move)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
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

    public ChessMove getLastMove() {
        return lastMove;
    }

    public ChessPosition getKingPos(TeamColor teamColor) {
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING &&
                        piece.getTeamColor() == teamColor) {
                    return position;
                }
            }
        }
        return null;
    }

    private boolean isValidMove(ChessMove move) {
        ChessPiece piece = board.getPiece(move.getStartPosition());
        if (piece == null) {
            return false;
        }

        Collection<ChessMove> possibleMoves = piece.pieceMoves(board, move.getStartPosition(), this);
        if (!possibleMoves.contains(move)) {
            return false;
        }

        ChessPiece capturedPiece = board.getPiece(move.getEndPosition());
        ChessPiece originalPiece = board.getPiece(move.getStartPosition());
        board.addPiece(move.getEndPosition(), originalPiece);
        board.addPiece(move.getStartPosition(), null);
        boolean inCheck = isInCheck(piece.getTeamColor());
        board.addPiece(move.getStartPosition(), originalPiece);
        board.addPiece(move.getEndPosition(), capturedPiece);
        return !inCheck;
    }

    public boolean isUnderAttack(ChessPosition position, TeamColor defendingTeam) {
        TeamColor attackingTeam = (defendingTeam == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;

        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition attackerPosition = new ChessPosition(row, col);
                ChessPiece attacker = board.getPiece(attackerPosition);
                if (attacker != null && attacker.getTeamColor() == attackingTeam) {
                    Collection<ChessMove> moves = attacker.pieceMoves(board, attackerPosition, this);
                    for (ChessMove move : moves) {
                        if (move.getEndPosition().equals(position)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}