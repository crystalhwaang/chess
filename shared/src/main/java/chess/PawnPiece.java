package chess;

import java.util.Collection;

public class PawnPiece extends ChessPiece {

    public PawnPiece(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.PAWN);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return getPawnMoves(board, myPosition, null);
    }
}