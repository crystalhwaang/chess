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
            int[][] directions = {{2, 1}, {-2, 1}, {2, -1}, {-2, -1}, {1, 2}, {1, -2}, {-1, 2}, {-1, -2}};

            // as long as they don't hit the edge of the board, keep in the same direction
            for (int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                //while (true) {
                row += direction[0];
                col += direction[1];

                if (row >= 1 && row <= 8 && col >= 1 && col <= 8) {

                    // create a ChessPosition for the new position that the piece is at on the board
                    ChessPosition newPos = new ChessPosition(row, col);
                    ChessPiece spotTaken = board.getPiece(newPos);

                    // if there isn't a piece in the spot, add it to the moves array
                    if (spotTaken == null) {
                        moves.add(new ChessMove(myPosition, newPos, null));
                    }
                    // if there is a piece there & it is the opposing team's, add it to the move's array, otherwise break
                    else {
                        if (spotTaken.getTeamColor() != piece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, newPos, null));
                        }
                    }
                }
            }
        }

        // code for the queen piece
        if (piece.getPieceType() == PieceType.QUEEN) {
            // all possible directions a queen can go
            int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};

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

        // code for the king piece
        if (piece.getPieceType() == PieceType.KING) {
            // all possible directions a king can go
            int[][] directions = {{1, 1}, {1, -1}, {-1, 1}, {-1, -1}, {0, 1}, {0, -1}, {1, 0}, {-1, 0}};

            // as long as they don't hit the edge of the board, keep in the same direction
            for (int[] direction : directions) {
                int row = myPosition.getRow();
                int col = myPosition.getColumn();
                //while (true) {
                row += direction[0];
                col += direction[1];

                if (row >= 1 && row <= 8 && col >= 1 && col <= 8) {

                    // create a ChessPosition for the new position that the piece is at on the board
                    ChessPosition newPos = new ChessPosition(row, col);
                    ChessPiece spotTaken = board.getPiece(newPos);

                    // if there isn't a piece in the spot, add it to the moves array
                    if (spotTaken == null) {
                        moves.add(new ChessMove(myPosition, newPos, null));
                    }
                    // if there is a piece there & it is the opposing team's, add it to the moves array, otherwise break
                    else {
                        if (spotTaken.getTeamColor() != piece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, newPos, null));
                        }
                        //break;
                    }
                }
            }
        }

        // code for the pawn piece
        if (piece.getPieceType() == PieceType.PAWN) {
            // all possible directions a pawn can go on the first turn
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            if (piece.getTeamColor() == ChessGame.TeamColor.BLACK) {
                if (row == 7) {
                    if (board.getPiece(new ChessPosition(row - 1, col)) == null && board.getPiece(new ChessPosition(row - 2, col)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row - 2, col), null));
                    }
                }
                if (row > 2) {
                    if (board.getPiece(new ChessPosition(row - 1, col)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), null));
                    }
                }
                else {
                    if (board.getPiece(new ChessPosition(row - 1, col)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col), PieceType.KNIGHT));
                    }
                }
                if (col > 1) {
                    ChessPiece spotTaken = board.getPiece(new ChessPosition(row - 1, col - 1));
                    if (spotTaken != null) {
                        if (row > 2) {
                            if (board.getPiece(new ChessPosition(row - 1, col - 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), null));
                            }
                        }
                        else {
                            if (board.getPiece(new ChessPosition(row - 1, col - 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), PieceType.ROOK));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), PieceType.BISHOP));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), PieceType.QUEEN));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col - 1), PieceType.KNIGHT));
                            }
                        }
                    }
                }
                if (col < 8) {
                    ChessPiece spotTaken = board.getPiece(new ChessPosition(row - 1, col + 1));
                    if (spotTaken != null) {
                        if (row > 2) {
                            if (board.getPiece(new ChessPosition(row - 1, col + 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), null));
                            }
                        }
                        else {
                            if (board.getPiece(new ChessPosition(row - 1, col + 1)).getTeamColor() == ChessGame.TeamColor.WHITE) {
                                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), PieceType.ROOK));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), PieceType.BISHOP));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), PieceType.QUEEN));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row - 1, col + 1), PieceType.KNIGHT));
                            }
                        }
                    }
                }
            }
            else {
                if (row == 2) {
                    if (board.getPiece(new ChessPosition(row + 1, col)) == null && board.getPiece(new ChessPosition(row + 2, col)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row + 2, col), null));
                    }
                }
                if (row < 7) {
                    if (board.getPiece(new ChessPosition(row + 1, col)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), null));
                    }
                }
                else {
                    if (board.getPiece(new ChessPosition(row + 1, col)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col), PieceType.KNIGHT));
                    }
                }
                if (col > 1) {
                    ChessPiece spotTaken = board.getPiece(new ChessPosition(row + 1, col - 1));
                    if (spotTaken != null) {
                        if (row < 7) {
                            if (board.getPiece(new ChessPosition(row + 1, col - 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), null));
                            }
                        }
                        else {
                            if (board.getPiece(new ChessPosition(row + 1, col - 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), PieceType.ROOK));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), PieceType.BISHOP));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), PieceType.QUEEN));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col - 1), PieceType.KNIGHT));
                            }
                        }
                    }
                }
                if (col < 8) {
                    ChessPiece spotTaken = board.getPiece(new ChessPosition(row + 1, col + 1));
                    if (spotTaken != null) {
                        if (row < 7) {
                            if (board.getPiece(new ChessPosition(row + 1, col + 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), null));
                            }
                        }
                        else {
                            if (board.getPiece(new ChessPosition(row + 1, col + 1)).getTeamColor() == ChessGame.TeamColor.BLACK) {
                                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), PieceType.ROOK));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), PieceType.BISHOP));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), PieceType.QUEEN));
                                moves.add(new ChessMove(myPosition, new ChessPosition(row + 1, col + 1), PieceType.KNIGHT));
                            }
                        }
                    }
                }
            }
        }
        return moves;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ChessPiece that = (ChessPiece) object;
        return pieceColor == that.pieceColor && type == that.type;
    }

//    public int hashCode() {
//        int result = super.hashCode();
//        result = 31 * result + java.util.Objects.hashCode(pieceColor);
//        result = 31 * result + java.util.Objects.hashCode(type);
//        return result;
//    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(pieceColor, type);
    }
}