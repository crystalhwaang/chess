package chess;

import java.util.Collection;

public class RookPiece extends ChessPiece {

    public RookPiece(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.ROOK);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return getRookMoves(board, myPosition);
    }
}