package chess;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;


public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

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
        List<ChessMove> moves = new ArrayList<ChessMove>();
        //var capture = new ArrayList<ChessMove>();
        ChessPiece piece = board.getPiece(myPosition);

        // code for the bishop piece
        if (piece.getPieceType() == PieceType.BISHOP) {
            // all possible directions a bishop can go
            int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

            // as long as they don't hit the edge of the board, keep in the same direction
            for (int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                while (true) {
                    row += direction[0];
                    col += direction[1];

                    if (row < 1 || row > 8 || col < 1 || col > 8) {
                        break;
                    }

                    // create a ChessPosition for the new position that the piece is at on the board
                    ChessPosition newPos = new ChessPosition(row, col);
                    ChessPiece spotTaken = board.getPiece(newPos);

                    // if there isn't a piece in the spot, add it to the moves array
                    if (spotTaken == null) {
                        moves.add(new ChessMove(myPosition, newPos, null));
                    }
                    // if there is a piece there & it is the opposing team's, add it to the move's array, otherwise break
                    else {
                        if (spotTaken.getTeamColor() != piece.getTeamColor()) { //issue is here -- the team colors are not different, why?
                            moves.add(new ChessMove(myPosition, newPos, null));
                        }
                        break;
                    }
                }
            }
        }

        // code for the rook piece
        if (piece.getPieceType() == PieceType.ROOK) {
            // all possible directions a rook can go
            int[][] directions = {{0, 1}, {0, -1}, {-1, 0}, {1, 0}};

            // as long as they don't hit the edge of the board, keep in the same direction
            for (int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                while (true) {
                    row += direction[0];
                    col += direction[1];

                    if (row < 1 || row > 8 || col < 1 || col > 8) {
                        break;
                    }

                    // create a ChessPosition for the new position that the piece is at on the board
                    ChessPosition newPos = new ChessPosition(row, col);
                    ChessPiece spotTaken = board.getPiece(newPos);

                    // if there isn't a piece in the spot, add it to the moves array
                    if (spotTaken == null) {
                        moves.add(new ChessMove(myPosition, newPos, null));
                    }
                    // if there is a piece there & it is the opposing team's, add it to the move's array, otherwise break
                    else {
                        if (spotTaken.getTeamColor() != piece.getTeamColor()) { //issue is here -- the team colors are not different, why?
                            moves.add(new ChessMove(myPosition, newPos, null));
                        }
                        break;
                    }
                }
            }
        }

        // code for the knight piece
        if (piece.getPieceType() == PieceType.KNIGHT) {
            // all possible directions a knight can go
            int[][] directions = {{2, 1}, {-2, 1}, {2, -1}, {-2, -1}};

            // as long as they don't hit the edge of the board, keep in the same direction
            for (int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                while (true) {
                    row += direction[0];
                    col += direction[1];

                    if (row < 1 || row > 8 || col < 1 || col > 8) {
                        break;
                    }

                    // create a ChessPosition for the new position that the piece is at on the board
                    ChessPosition newPos = new ChessPosition(row, col);
                    ChessPiece spotTaken = board.getPiece(newPos);

                    // if there isn't a piece in the spot, add it to the moves array
                    if (spotTaken == null) {
                        moves.add(new ChessMove(myPosition, newPos, null));
                    }
                    // if there is a piece there & it is the opposing team's, add it to the move's array, otherwise break
                    else {
                        if (spotTaken.getTeamColor() != piece.getTeamColor()) { //issue is here -- the team colors are not different, why?
                            moves.add(new ChessMove(myPosition, newPos, null));
                        }
                        break;
                    }
                }
            }
        }
        return moves;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) object;
        return pieceColor == that.pieceColor && type == that.type;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + java.util.Objects.hashCode(pieceColor);
        result = 31 * result + java.util.Objects.hashCode(type);
        return result;
    }
}