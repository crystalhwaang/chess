package chess;

import java.util.Collection;

public class KnightPiece extends ChessPiece {

    public KnightPiece(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.KNIGHT);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return getKnightMoves(board, myPosition);
    }
}