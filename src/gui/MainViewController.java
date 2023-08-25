package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.util.PieceStyle;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.chess.ChessException;
import model.chess.ChessMatch;
import model.chess.ChessPiece;
import model.chess.ChessPosition;

public class MainViewController implements Initializable {
    
    ChessMatch chessMatch = new ChessMatch();

    List<ChessPiece> capturedPieces = new ArrayList<>();

    ChessPosition source, target;

    @FXML
    GridPane gridPane;

    @FXML
    GridPane gridPaneBlack;

    @FXML
    GridPane gridPaneWhite;

    @FXML
    Label alert;

    @FXML
    Label turn;

    @FXML
    Label currentPlayer;
    
    @FXML
    public void onAction(Event event){
        if(!chessMatch.getCheckMate()){
            try{
                char column = ((Button)event.getSource()).getId().charAt(0);
                int row = Integer.parseInt(((Button)event.getSource()).getId().substring(1));
                
                if(source == null){
                    clearScreen();
                    source = new ChessPosition(column, row);
                    boolean[][] possibleMoves = chessMatch.possibleMoves(source);
                    printBoard(chessMatch.getPieces(), possibleMoves);
                }else{
                    target = new ChessPosition(column, row);
                    ChessPiece capturedPiece = chessMatch.performChessMove(source, target);

                    if(capturedPiece != null){
                        capturedPieces.add(capturedPiece);
                    }

                    if(chessMatch.getPromoted() != null){
                        createDialogForm(Utils.currentStage((ActionEvent)event));
                    }
                    
                    printMatch(chessMatch, capturedPieces);
                    source = null;
                }
            }catch(ChessException e){
                    clearScreen();
                    alert.setTextFill(Color.RED);
                    alert.setText(e.getMessage());
                    source = null;
            }   
        } 
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        printMatch(chessMatch, capturedPieces);
    }

    private Button[][] getButtons(){
        Button[][] buttons = new Button[8][8];
        int cont = 0;
        for(int i = 0; i < buttons.length; i++){
            for(int j = 0; j < buttons.length; j++){
                buttons[i][j] = (Button)gridPane.getChildren().get(cont);
                cont++;
            }
        }
        return buttons;
    }

    public void printBoard(ChessPiece[][] pieces){
        for(int i = 0; i < pieces.length; i++){
            for(int j = 0; j < pieces.length; j++){
                printPiece(pieces[i][j], false, getButtons()[i][j]);
            }
        }
    }

    public void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves){
        for(int i = 0; i < pieces.length; i++){
            for(int j = 0; j < pieces.length; j++){
                printPiece(pieces[i][j], possibleMoves[i][j], getButtons()[i][j]);
            }
            System.out.println();
        }
    }

    public void printPiece(ChessPiece piece, boolean background, Button button){
        if(piece != null){
            button.setStyle(PieceStyle.getStyle(piece.getColor(), piece.toString()));  
        }else{
            button.setStyle("-fx-background-color: transparent;");
        }
        if(background){
            button.setStyle(button.getStyle() + "-fx-background-color: DeepSkyBlue;");
        }
    }

    public void clearScreen(){
        alert.setText(null);
        printBoard(chessMatch.getPieces());
    }

    public void printMatch(ChessMatch chessMatch, List<ChessPiece> capturedPieces){
        printBoard(chessMatch.getPieces());
        printCapturedPieces(capturedPieces);
        turn.setText(chessMatch.getTurn()+"");
         
        if(!chessMatch.getCheckMate()){
            currentPlayer.setText(chessMatch.getCurrentPlayer()+"");
            if(chessMatch.getCheck()){
                alert.setTextFill(Color.BLACK);
                alert.setText("CHECK!");
            }
        }else{
            alert.setTextFill(Color.GREEN);
            alert.setText("CHECKMATE! Winner: " + chessMatch.getCurrentPlayer());
        }    
    }
    
    public void printCapturedPieces(List<ChessPiece> capturedPieces){
        //implementação pendente.
    }

    private void createDialogForm(Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PromotedFormView.fxml"));
            Pane pane = loader.load();

            PromotedFormController controller = loader.getController();
            controller.setColor(chessMatch.getCurrentPlayer());
            controller.setChessMatch(chessMatch);

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Which piece do you want to promote?");
            dialogStage.setScene(new Scene(pane));
            dialogStage.setResizable(false);
            dialogStage.initOwner(parentStage);
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.showAndWait();
        } catch (IOException e) {
           alert.setText(e.getMessage());
        }
    }
}
