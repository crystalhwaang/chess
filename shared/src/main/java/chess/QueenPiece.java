package chess;

import java.util.Collection;

public class QueenPiece extends ChessPiece {

    public QueenPiece(ChessGame.TeamColor pieceColor) {
        super(pieceColor, PieceType.QUEEN);
    }

    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return getQueenMoves(board, myPosition);
    }
}