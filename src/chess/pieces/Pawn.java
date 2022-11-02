package chess.pieces;

import boardgame.Board;
import boardgame.Position;
import chess.ChessPiece;
import chess.Color;

public class Pawn extends ChessPiece{

    public Pawn(Board board, Color color){
        super(board, color);
    }

    @Override
    public boolean[][] possibleMoves(){
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p1 = new Position(0, 0);
        Position p2 = new Position(0, 0);

        if(getColor() == Color.WHITE){
            p1.setValues(position.getRow() - 1, position.getColumn());
            if(getBoard().positionExists(p1) && !getBoard().thereIsAPiece(p1)){
                mat[p1.getRow()][p1.getColumn()] = true;
            }
            p2.setValues(position.getRow() - 2, position.getColumn());
            if(getBoard().positionExists(p1) && !getBoard().thereIsAPiece(p1) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
                mat[p2.getRow()][p2.getColumn()] = true;
            }
            p1.setValues(position.getRow() - 1, position.getColumn() - 1);
            if(getBoard().positionExists(p1) && isThereOpponentPiece(p1)){
                mat[p1.getRow()][p1.getColumn()] = true;
            }
            p1.setValues(position.getRow() - 1, position.getColumn() + 1);
            if(getBoard().positionExists(p1) && isThereOpponentPiece(p1)){
                mat[p1.getRow()][p1.getColumn()] = true;
            }
        }else{
            p1.setValues(position.getRow() + 1, position.getColumn());
            if(getBoard().positionExists(p1) && !getBoard().thereIsAPiece(p1)){
                mat[p1.getRow()][p1.getColumn()] = true;
            }
            p2.setValues(position.getRow() + 2, position.getColumn());
            if(getBoard().positionExists(p1) && !getBoard().thereIsAPiece(p1) && getBoard().positionExists(p2) && !getBoard().thereIsAPiece(p2) && getMoveCount() == 0){
                mat[p2.getRow()][p2.getColumn()] = true;
            }
            p1.setValues(position.getRow() + 1, position.getColumn() - 1);
            if(getBoard().positionExists(p1) && isThereOpponentPiece(p1)){
                mat[p1.getRow()][p1.getColumn()] = true;
            }
            p1.setValues(position.getRow() + 1, position.getColumn() + 1);
            if(getBoard().positionExists(p1) && isThereOpponentPiece(p1)){
                mat[p1.getRow()][p1.getColumn()] = true;
            }
        }
        return mat;
    }

    @Override
    public String toString(){
        return "P";
    }
}
