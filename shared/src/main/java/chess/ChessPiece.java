package chess;
import java.util.*;


public class ChessPiece {

    protected final ChessGame.TeamColor pieceColor;
    protected final PieceType type;
    protected boolean moved;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    public static ChessPiece newPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        return switch (type) {
            case PAWN -> new PawnPiece(pieceColor);
            case ROOK -> new RookPiece(pieceColor);
            case KNIGHT -> new KnightPiece(pieceColor);
            case BISHOP -> new BishopPiece(pieceColor);
            case QUEEN -> new QueenPiece(pieceColor);
            case KING -> new KingPiece(pieceColor);
        };
    }

    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        List<ChessMove> moves = new ArrayList<>();
        switch (type) {
            case PAWN -> moves.addAll(getPawnMoves(board, myPosition, null));
            case ROOK -> moves.addAll(getRookMoves(board, myPosition));
            case KNIGHT -> moves.addAll(getKnightMoves(board, myPosition));
            case BISHOP -> moves.addAll(getBishopMoves(board, myPosition));
            case QUEEN -> {
                moves.addAll(getRookMoves(board, myPosition));
                moves.addAll(getBishopMoves(board, myPosition));
            }
            case KING -> moves.addAll(getKingMoves(board, myPosition));
        }
        return moves;
    }

    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame game) {
        Collection<ChessMove> moves = new ArrayList<>();

        switch (type) {
            case PAWN -> moves.addAll(getPawnMoves(board, myPosition, game));
            case ROOK -> moves.addAll(getRookMoves(board, myPosition));
            case KNIGHT -> moves.addAll(getKnightMoves(board, myPosition));
            case BISHOP -> moves.addAll(getBishopMoves(board, myPosition));
            case QUEEN -> {
                moves.addAll(getRookMoves(board, myPosition));
                moves.addAll(getBishopMoves(board, myPosition));
            }
            case KING -> moves.addAll(getKingMoves(board, myPosition));
        }
        return moves;
    }

    protected Collection<ChessMove> getPawnMoves(ChessBoard board, ChessPosition myPosition, ChessGame game) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        int dir;
        int startRow;
        int promoteRow;
        if (pieceColor == ChessGame.TeamColor.WHITE) {
            dir = 1;
            startRow = 2;
            promoteRow = 8;
        }
        else {
            dir = -1;
            startRow = 7;
            promoteRow = 1;
        }

        ChessPosition forwardPos = new ChessPosition(row + dir, col);
        if (isValidPosition(forwardPos) && board.getPiece(forwardPos) == null) {
            if (row + dir == promoteRow) {
                moves.add(new ChessMove(myPosition, forwardPos, PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, forwardPos, PieceType.ROOK));
                moves.add(new ChessMove(myPosition, forwardPos, PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, forwardPos, PieceType.KNIGHT));
            }
            else {
                moves.add(new ChessMove(myPosition, forwardPos, null));
            }
        }

        if (row == startRow) {
            ChessPosition twoForwardPos = new ChessPosition(row + 2 * dir, col);
            if (isValidPosition(twoForwardPos) && board.getPiece(twoForwardPos) == null && board.getPiece(forwardPos) == null) {
                moves.add(new ChessMove(myPosition, twoForwardPos, null));
            }
        }

        for (int colTemp : new int[]{-1, 1}) {
            ChessPosition capturePos = new ChessPosition(row + dir, col + colTemp);
            if (isValidPosition(capturePos)) {
                ChessPiece targetPiece = board.getPiece(capturePos);
                if (targetPiece != null && targetPiece.getTeamColor() != pieceColor) {
                    if (row + dir == promoteRow) {
                        moves.add(new ChessMove(myPosition, capturePos, PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, capturePos, PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, capturePos, PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, capturePos, PieceType.KNIGHT));
                    }
                    else {
                        moves.add(new ChessMove(myPosition, capturePos, null));
                    }
                }
            }
        }
        return moves;
    }

    protected Collection<ChessMove> getRookMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int[][] directions = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};

        for (int[] direction : directions) {
            moves.addAll(movingPieces(board, myPosition, direction[0], direction[1]));
        }
        return moves;
    }

    protected Collection<ChessMove> getBishopMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] direction : directions) {
            moves.addAll(movingPieces(board, myPosition, direction[0], direction[1]));
        }
        return moves;
    }

    protected Collection<ChessMove> getKnightMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int[][] knightMoves = {{2, 1}, {2, -1}, {-2, 1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};

        for (int[] move : knightMoves) {
            ChessPosition newPos = new ChessPosition(myPosition.getRow() + move[0], myPosition.getColumn() + move[1]);
            if (isValidPosition(newPos)) {
                ChessPiece piece = board.getPiece(newPos);
                if (piece == null || piece.getTeamColor() != pieceColor) {
                    moves.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }
        return moves;
    }

    protected Collection<ChessMove> getQueenMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        moves.addAll(getRookMoves(board, myPosition));
        moves.addAll(getBishopMoves(board, myPosition));
        return moves;
    }

    protected Collection<ChessMove> getKingMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        int[][] kingMoves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] move : kingMoves) {
            ChessPosition newPos = new ChessPosition(myPosition.getRow() + move[0], myPosition.getColumn() + move[1]);
            if (isValidPosition(newPos)) {
                ChessPiece piece = board.getPiece(newPos);
                if (piece == null || piece.getTeamColor() != pieceColor) {
                    moves.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }
        return moves;
    }

    protected boolean isValidPosition(ChessPosition position) {
        return position.getRow() >= 1 && position.getRow() <= 8 && position.getColumn() >= 1 && position.getColumn() <= 8;
    }

    protected Collection<ChessMove> movingPieces(ChessBoard board, ChessPosition myPosition, int rowDir, int colDir) {
        Collection<ChessMove> moves = new ArrayList<>();
        int row = myPosition.getRow() + rowDir;
        int col = myPosition.getColumn() + colDir;

        while (isValidPosition(new ChessPosition(row, col))) {
            ChessPiece piece = board.getPiece(new ChessPosition(row, col));
            if (piece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
            } else {
                if (piece.getTeamColor() != pieceColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(row, col), null));
                }
                break;
            }
            row += rowDir;
            col += colDir;
        }
        return moves;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ChessPiece that = (ChessPiece) o;
        return moved == that.moved && pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(pieceColor, type);
    }
}