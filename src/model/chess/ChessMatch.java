package model.chess;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import model.boardgame.Board;
import model.boardgame.Piece;
import model.boardgame.Position;
import model.chess.pieces.Bishop;
import model.chess.pieces.King;
import model.chess.pieces.Knight;
import model.chess.pieces.Pawn;
import model.chess.pieces.Queen;
import model.chess.pieces.Rook;

public class ChessMatch {

    private int turn;
    private Color currentPlayer;
    private Board board;
    private List<ChessPiece> piecesOnTheBoard;
    private List<ChessPiece> capturedPieces;
    private boolean check;
    private boolean checkMate;
    private ChessPiece enPassantVunerable;
    private ChessPiece promoted;

    public ChessMatch(){
        board = new Board(8, 8);
        turn = 1;
        currentPlayer = Color.WHITE;
        piecesOnTheBoard = new ArrayList<>();
        capturedPieces = new ArrayList<>();
        initialSetup();
    }

    public int getTurn(){
        return turn;
    }

    public Color getCurrentPlayer(){
        return currentPlayer;
    }

    public boolean getCheck(){
        return check;
    }

    public boolean getCheckMate(){
        return checkMate;
    }

    public ChessPiece getEnPassantVunerable(){
        return enPassantVunerable;
    }

    public ChessPiece getPromoted(){
        return promoted;
    }

    public ChessPiece[][] getPieces(){
        ChessPiece[][] mat = new ChessPiece[board.getRows()][board.getColumns()];

        for(int i=0; i < board.getRows(); i++){
            for(int j=0; j < board.getColumns(); j++){
                mat[i][j] = (ChessPiece) board.piece(i, j);
            }
        }
        return mat;
    }

    public boolean[][] possibleMoves(ChessPosition sourcePosition){
        Position position = sourcePosition.toPosition();
        validateSourcePosition(position);
        return board.piece(position).possibleMoves();
    }
    
    public ChessPiece performChessMove(ChessPosition sourcePosition, ChessPosition targetPosition){
        Position source = sourcePosition.toPosition();
        Position target = targetPosition.toPosition();
        validateSourcePosition(source);
        validateTargetPosition(source, target);
        Piece capturedPiece = makeMove(source, target);

        if(testCheck(currentPlayer)){
            undoMove(source, target, capturedPiece);
            throw new ChessException("Você não pode se colocar em xeque");
        }

        ChessPiece movedPiece = (ChessPiece)board.piece(target);

        //#specialmove promoted
        promoted = null;
        if(movedPiece instanceof Pawn){
            if(movedPiece.getColor() == Color.WHITE && target.getRow() == 0 || movedPiece.getColor() == Color.BLACK && target.getRow() == 7){
                promoted = movedPiece;
                promoted = replacePromotedPiece("Q");
            }
        }

        check = (testCheck(opponent(currentPlayer))) ? true : false;

        if(testCheckMate(opponent(currentPlayer))){
            checkMate = true;
        }else{
            nextTurn();
        }

        if(movedPiece instanceof Pawn && target.getRow() == source.getRow() - 2 || target.getRow()  == source.getRow() + 2){
            enPassantVunerable = movedPiece;
        }else{
            enPassantVunerable = null;
        }
        
        return (ChessPiece) capturedPiece;
    }

    public ChessPiece replacePromotedPiece(String type){
        if(promoted == null){
            throw new IllegalStateException("Não há nenhuma peça a ser promovida");
        }
        if(!type.equals("Q") && !type.equals("B") && !type.equals("N") && !type.equals("R")){
            return promoted;
        }

        Position position = promoted.getChessPosition().toPosition();
        Piece p = board.removePiece(position);
        piecesOnTheBoard.remove(p);

        ChessPiece newPiece = newPiece(type, promoted.getColor());
        board.placePiece(newPiece, position);
        piecesOnTheBoard.add(newPiece);

        return newPiece;
    }

    private ChessPiece newPiece(String type, Color color){
        if(type.equals("Q")) return new Queen(board, color);
        if(type.equals("R")) return new Rook(board, color);
        if(type.equals("N")) return new Knight(board, color);
         return new Bishop(board, color);

    }

    private Piece makeMove(Position source, Position target){
        ChessPiece p = (ChessPiece)board.removePiece(source);
        p.increaseMoveCount();
        Piece capturedPiece = board.removePiece(target);
        board.placePiece(p, target);

        if(capturedPiece != null){
            piecesOnTheBoard.remove(capturedPiece);
            capturedPieces.add((ChessPiece)capturedPiece);
        }

        // #specialmove castling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){
            Position rookSource = new Position(source.getRow(), source.getColumn() + 3);
            Position rookTarget = new Position(source.getRow(), source.getColumn() + 1); 
            ChessPiece rook = (ChessPiece) board.removePiece(rookSource);
            board.placePiece(rook, rookTarget);
            rook.increaseMoveCount();
        }

        // #specialmove castling queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){
            Position rookSource = new Position(source.getRow(), source.getColumn() - 4);
            Position rookTarget = new Position(source.getRow(), source.getColumn() - 1); 
            ChessPiece rook = (ChessPiece) board.removePiece(rookSource);
            board.placePiece(rook, rookTarget);
            rook.increaseMoveCount();
        }

        // #specialmove en passant
        if(p instanceof Pawn){
            if(source.getColumn() != target.getColumn() && capturedPiece == null){
                Position pawnPosition;
                if(p.getColor() == Color.WHITE){
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                }else{
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }
                capturedPiece = board.removePiece(pawnPosition);
                piecesOnTheBoard.remove(capturedPiece);
                capturedPieces.add((ChessPiece)capturedPiece);            
            }
        }

        return capturedPiece;
    }

    private void undoMove(Position source, Position target, Piece capturedPiece){
        ChessPiece p = (ChessPiece)board.removePiece(target);
        board.placePiece(p, source);

        if(capturedPiece != null){
            board.placePiece(capturedPiece, target);
            capturedPieces.remove(capturedPiece);
            piecesOnTheBoard.add((ChessPiece)capturedPiece);
        }

        // #specialmove castling kingside rook
        if (p instanceof King && target.getColumn() == source.getColumn() + 2){
            Position rookSource = new Position(source.getRow(), source.getColumn() + 3);
            Position rookTarget = new Position(source.getRow(), source.getColumn() + 1); 
            ChessPiece rook = (ChessPiece) board.removePiece(rookTarget);
            board.placePiece(rook, rookSource);
            rook.decreaseMoveCount();
        }

        // #specialmove castling queenside rook
        if (p instanceof King && target.getColumn() == source.getColumn() - 2){
            Position rookSource = new Position(source.getRow(), source.getColumn() - 4);
            Position rookTarget = new Position(source.getRow(), source.getColumn() - 1); 
            ChessPiece rook = (ChessPiece) board.removePiece(rookTarget);
            board.placePiece(rook, rookSource);
            rook.decreaseMoveCount();
        }

        // #specialmove en passant
        if(p instanceof Pawn){
            if(source.getColumn() != target.getColumn() && capturedPiece == enPassantVunerable){
                ChessPiece pawn = (ChessPiece)board.piece(target);
                Position pawnPosition;
                if(p.getColor() == Color.WHITE){
                    pawnPosition = new Position(target.getRow() - 1, target.getColumn());
                }else{
                    pawnPosition = new Position(target.getRow() + 1, target.getColumn());
                }
                board.placePiece(pawn, pawnPosition);       
            }
        }
    }

    private void validateSourcePosition(Position position){
        if(!board.thereIsAPiece(position)){
            throw new ChessException("Não há peça na posição de origem");
        }
        if(currentPlayer != ((ChessPiece)board.piece(position)).getColor()){
            throw new ChessException("A peça escolhida não é sua");
        }
        if(!board.piece(position).isThereAnyPossibleMove()){
            throw new ChessException("Não há movimentos possiveis para pela escolhida");
        }
    }

    private void validateTargetPosition(Position source, Position target){
        if(!board.piece(source).possibleMove(target)){
            throw new ChessException("A peça escolhida não pode se mover para posição alvo");
        }
    }

    public void nextTurn(){
        turn++;
        currentPlayer = (currentPlayer == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private Color opponent(Color color){
        return (color == Color.WHITE) ? Color.BLACK : Color.WHITE;
    }

    private ChessPiece king(Color color){
        List<ChessPiece> list = piecesOnTheBoard.stream().filter(x -> x.getColor() == color).collect(Collectors.toList());
        for(ChessPiece chessPiece : list){
            if(chessPiece instanceof King){
                return chessPiece;
            }
        }
        throw new IllegalStateException("There is no " + color + " King on the board.");
    }

    private boolean testCheck(Color color){
        Position kingPosition = king(color).getChessPosition().toPosition();
        List<ChessPiece> opponentPieces = piecesOnTheBoard.stream().filter(x -> x.getColor() == opponent(color)).collect(Collectors.toList());
        for(ChessPiece p : opponentPieces){
            if(p.possibleMoves()[kingPosition.getRow()][kingPosition.getColumn()] == true){
                return true;
            }
        }
        return false;
    }

    private boolean testCheckMate(Color color){
        if(!testCheck(color)){
            return false;
        }
        List<ChessPiece> list =  piecesOnTheBoard.stream().filter(x -> x.getColor() == color).collect(Collectors.toList());
        for(ChessPiece p : list){
            boolean mat[][] = p.possibleMoves();
            for(int i = 0; i < board.getRows(); i++){
                for(int j = 0; j < board.getColumns(); j++){
                    if(mat[i][j]){
                        Position source = p.getChessPosition().toPosition();
                        Position target = new Position(i, j);
                        ChessPiece capturedPiece = (ChessPiece)makeMove(source, target);
                        boolean testCheck = testCheck(color);
                        undoMove(source, target, capturedPiece);
                        if(!testCheck){
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    public void placeNewPiece(char column, int row, ChessPiece piece){
        board.placePiece(piece, new ChessPosition(column, row).toPosition());
        piecesOnTheBoard.add(piece);
    }

    public void initialSetup(){
        placeNewPiece('a', 1, new Rook(board, Color.WHITE));
        placeNewPiece('b', 1, new Knight(board, Color.WHITE));
        placeNewPiece('c', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('d', 1, new Queen(board, Color.WHITE));
        placeNewPiece('e', 1, new King(board, Color.WHITE, this));
        placeNewPiece('f', 1, new Bishop(board, Color.WHITE));
        placeNewPiece('g', 1, new Knight(board, Color.WHITE));
        placeNewPiece('h', 1, new Rook(board, Color.WHITE));
        placeNewPiece('a', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('b', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('c', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('d', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('e', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('f', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('g', 2, new Pawn(board, Color.WHITE, this));
        placeNewPiece('h', 2, new Pawn(board, Color.WHITE, this));

        placeNewPiece('a', 8, new Rook(board, Color.BLACK));
        placeNewPiece('b', 8, new Knight(board, Color.BLACK));
        placeNewPiece('c', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('d', 8, new Queen(board, Color.BLACK));
        placeNewPiece('e', 8, new King(board, Color.BLACK, this));
        placeNewPiece('f', 8, new Bishop(board, Color.BLACK));
        placeNewPiece('g', 8, new Knight(board, Color.BLACK));
        placeNewPiece('h', 8, new Rook(board, Color.BLACK));
        placeNewPiece('a', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('b', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('c', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('d', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('e', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('f', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('g', 7, new Pawn(board, Color.BLACK, this));
        placeNewPiece('h', 7, new Pawn(board, Color.BLACK, this));
    }
}
