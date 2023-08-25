package gui;

import gui.util.PieceStyle;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import model.chess.ChessMatch;
import model.chess.Color;

public class PromotedFormController {
    
    ChessMatch chessMatch;
    
    @FXML
    Button btnBishop;

    @FXML
    Button btnKnight;

    @FXML
    Button btnRock;

    @FXML
    Button btnQueen;

    @FXML
    public void onAction(Event event){
        chessMatch.replacePromotedPiece(changeType(event));
        Utils.currentStage((ActionEvent)event).close();
    }

    public void setChessMatch(ChessMatch chessMatch){
        this.chessMatch = chessMatch;
    }

    public void setColor(Color currentPlayer){
        if(currentPlayer == Color.WHITE){
            btnQueen.setStyle(PieceStyle.getQueen(Color.BLACK));
            btnBishop.setStyle(PieceStyle.getBishop(Color.BLACK));
            btnKnight.setStyle(PieceStyle.getKnight(Color.BLACK));
            btnRock.setStyle(PieceStyle.getRock(Color.BLACK));
        }else{
            btnQueen.setStyle(PieceStyle.getQueen(Color.WHITE));
            btnBishop.setStyle(PieceStyle.getBishop(Color.WHITE));
            btnKnight.setStyle(PieceStyle.getKnight(Color.WHITE));
            btnRock.setStyle(PieceStyle.getRock(Color.WHITE));
        }
    }

    public String changeType(Event event){
            if(((Button)event.getSource()).getId().equals("btnBishop")) return "B";
            if(((Button)event.getSource()).getId().equals("btnKnight")) return "N";
            if(((Button)event.getSource()).getId().equals("btnRock")) return "R";
            return "Q";
    }
}
