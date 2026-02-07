package chess;

import java.util.ArrayList;
import java.util.Collection;

public class KingPiece extends ChessPiece {

    public KingPiece(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KING);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return getKingMoves(board, myPosition);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition, ChessGame game) {
        Collection<ChessMove> moves = new ArrayList<>();
        int[][] kingMoves = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}};

        for (int[] move : kingMoves) {
            ChessPosition newPos = new ChessPosition(myPosition.getRow() + move[0], myPosition.getColumn() + move[1]);
            if (isValidPosition(newPos)) {
                ChessPiece targetPiece = board.getPiece(newPos);
                if (targetPiece == null || targetPiece.getTeamColor() != pieceColor) {
                    moves.add(new ChessMove(myPosition, newPos, null));
                }
            }
        }

        return moves;
    }
}