package gui;

import chess.Color;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class PromotedFormController {
    
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
        String type = ((Button)event.getSource()).getId();
    }

    public void setColorToPromoted(Color currentPlayer){
        if(currentPlayer == Color.WHITE){
            btnQueen.setStyle("-fx-background-position: center;" 
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/black_queen.png\");"
            + "-fx-background-size: 100px 100px;"
            + "-fx-background-repeat: no-repeat;");

            btnBishop.setStyle("-fx-background-position: center;"
            + "-fx-background-color: transparent;" 
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/black_bishop.png\");"
            + "-fx-background-size: 100px 100px;"
            + "-fx-background-repeat: no-repeat;");

            btnKnight.setStyle("-fx-background-position: center;" 
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/black_knight.png\");"
            + "-fx-background-size: 100px 100px;"
            + "-fx-background-repeat: no-repeat;");

            btnRock.setStyle("-fx-background-position: center;"
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/black_rock.png\");"
            + "-fx-background-size: 100px 100px;"
            + "-fx-background-repeat: no-repeat;");

        }else{
            btnQueen.setStyle("-fx-background-position: center;"
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/queen.png\");"
            + "-fx-background-size: 100px 100px;"
            + "-fx-background-repeat: no-repeat;");

            btnBishop.setStyle("-fx-background-position: center;"
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///src/img/bishop.png\");"
            + "-fx-background-size: 100px 100px;"
            + "-fx-background-repeat: no-repeat;");

            btnKnight.setStyle("-fx-background-position: center;"
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/knight.png\");"
            + "-fx-background-size: 100px 100px;"
            + "-fx-background-repeat: no-repeat;");

            btnRock.setStyle("-fx-background-position: center;"
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/img/rock.png\");"
            + "-fx-background-size: 100px 100px;"
            + "-fx-background-repeat: no-repeat;");
        }
    }
}
