package chess;

import java.util.Collection;

public class BishopPiece extends ChessPiece {

    public BishopPiece(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.BISHOP);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return getBishopMoves(board, myPosition);
    }
}