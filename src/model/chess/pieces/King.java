package model.chess.pieces;

import model.boardgame.Board;
import model.boardgame.Position;
import model.chess.ChessMatch;
import model.chess.ChessPiece;
import model.chess.Color;

public class King extends ChessPiece{

    private ChessMatch chessMatch;

    public King(Board board, Color color, ChessMatch chessMatch) {
        super(board, color);
        this.chessMatch = chessMatch;
    }

    private boolean canMove(Position position){
        return !getBoard().thereIsAPiece(position) || isThereOpponentPiece(position);
    }

    private boolean testRookCastling(Position position){
        ChessPiece p = (ChessPiece)getBoard().piece(position);
        return p != null && p instanceof Rook && p.getColor() == getColor() && p.getMoveCount() == 0;
    }

    @Override
    public String toString() {
        return "K";
    }

    @Override
    public boolean[][] possibleMoves(){
        boolean[][] mat = new boolean[getBoard().getRows()][getBoard().getColumns()];

        Position p = new Position(0, 0);

        //Above
        p.setValues(this.position.getRow() - 1, this.position.getColumn());
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        //Below
        p.setValues(this.position.getRow() + 1, this.position.getColumn());
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        //Left
        p.setValues(this.position.getRow(), this.position.getColumn() - 1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        //Right
        p.setValues(this.position.getRow(), this.position.getColumn() + 1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(this.position.getRow() - 1, this.position.getColumn() - 1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(this.position.getRow() - 1, this.position.getColumn() + 1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(this.position.getRow() + 1, this.position.getColumn() - 1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        p.setValues(this.position.getRow() + 1, this.position.getColumn() + 1);
        if(getBoard().positionExists(p) && canMove(p)){
            mat[p.getRow()][p.getColumn()] = true;
        }

        // #specialmove castling:
        if(getMoveCount() == 0 && !chessMatch.getCheck()){
            Position p1 = new Position(0, 0);
            Position p2 = new Position(0, 0);
            Position p3 = new Position(0, 0);
            Position rookPosition = new Position(0, 0);
            // #specialmove castling kingside rook:
            p1.setValues(position.getRow(), position.getColumn() + 1);
            p2.setValues(position.getRow(), position.getColumn() + 2);
            rookPosition.setValues(position.getRow(), position.getColumn() + 3);
            if(!getBoard().thereIsAPiece(p1) && !getBoard().thereIsAPiece(p2) && testRookCastling(rookPosition)){
                mat[p2.getRow()][p2.getColumn()] = true;
            }
            // #specialmove castling queenside rook:
            p1.setValues(position.getRow(), position.getColumn() - 1);
            p2.setValues(position.getRow(), position.getColumn() - 2);
            p3.setValues(position.getRow(), position.getColumn() - 3);
            rookPosition.setValues(position.getRow(), position.getColumn() - 4);
            if(!getBoard().thereIsAPiece(p1) && !getBoard().thereIsAPiece(p2) && !getBoard().thereIsAPiece(p3) && testRookCastling(rookPosition)){
                mat[p2.getRow()][p2.getColumn()] = true;
            }
        }
        return mat;
    }
}
