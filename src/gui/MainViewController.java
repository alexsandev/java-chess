package gui;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import chess.ChessException;
import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.pieces.Pawn;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainViewController implements Initializable {
    
    ChessMatch chessMatch = new ChessMatch();

    List<ChessPiece> capturedPieces = new ArrayList<>();

    ChessPosition source, target;

    @FXML
    ImageView imageView;

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
                    createDialogForm(currentStage((ActionEvent)event));
                    //String type = null;
                    //chessMatch.replacePromotedPiece(type);
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("\\img\\board.png");
        imageView.setImage(img);
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

    //Para quando clicar em uma peça e selecionar as casas que ela pode andar
    public void printBoard(ChessPiece[][] pieces, boolean[][] possibleMoves){
        for(int i = 0; i < pieces.length; i++){
            for(int j = 0; j < pieces.length; j++){
                printPiece(pieces[i][j], possibleMoves[i][j], getButtons()[i][j]);
            }
            System.out.println();
        }
    }

    public void printPiece(ChessPiece piece, boolean background, Button button){
        if(background){
            button.setStyle("-fx-background-color: DeepSkyBlue; -fx-opacity: 0.5");
        }else{
            button.setStyle("-fx-background-color: transparent; -fx-opacity: 0.5");
        }
        if(piece != null){
            if(piece.getColor() == chess.Color.BLACK){
                if(piece.toString() == "P"){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/black_pawn.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
                if(piece.toString() == "R"){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/black_rock.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
                if(piece.toString() == "B"){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/black_bishop.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
                if(piece.toString() == "K"){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/black_king.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
                if(piece.toString() == "N"){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/black_knight.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
                if(piece.toString() == "Q"){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/black_queen.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
            }else{
                if(piece instanceof Pawn){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/pawn.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
                if(piece.toString() == "R"){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/rock.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
                if(piece.toString() == "B"){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/bishop.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
                if(piece.toString() == "K"){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/king.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
                if(piece.toString() == "N"){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/knight.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
                if(piece.toString() == "Q"){
                    button.setStyle("-fx-background-position: center; -fx-background-color: transparent; -fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/queen.png\"); -fx-background-size: 50px 50px; -fx-background-repeat: no-repeat;");
                }
            }   
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
                alert.setText("CHECK!");
            }
        }else{
            alert.setTextFill(Color.GREEN);
            alert.setText("CHECKMATE! Winner: " + chessMatch.getCurrentPlayer());
        }    
    }
    
    public void printCapturedPieces(List<ChessPiece> capturedPieces){
        System.out.println("peças capturadas.");
    }

    private void createDialogForm(Stage parentStage) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/gui/PromotedFormView.fxml"));
            Pane pane = loader.load();

            PromotedFormController controller = loader.getController();
            controller.setColorToPromoted(chessMatch.getCurrentPlayer());

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

    public Stage currentStage(ActionEvent event) {
        return (Stage)((Node) event.getSource()).getScene().getWindow();
    }
}
