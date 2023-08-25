package gui;

import java.net.URL;
import java.util.ResourceBundle;

import chess.ChessMatch;
import chess.ChessPiece;
import chess.ChessPosition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

public class MainViewController implements Initializable {
    
    @FXML
    ImageView imageView;

    @FXML
    GridPane gridPane;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Image img = new Image("\\img\\board.png");
        imageView.setImage(img);
        ChessMatch chessMatch = new ChessMatch();
        printBoard(chessMatch.getPieces(), chessMatch.possibleMoves(new ChessPosition('a', 2)));
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
            button.setStyle("-fx-background-color: DeepSkyBlue; -fx-opacity: 0.5");;
        }
        if(piece == null){
            button.setText("vazio");
        }else{
            if(piece.getColor() == chess.Color.BLACK){
                button.styleProperty().setValue("-fx-backgroud-image: url(\"\"); -fx-opacity: 0.5");
                button.setText(piece.toString());
            }else{
                
                button.setStyle("-fx-backgroud-image: url(\"\"); -fx-opacity: 0.5");
                button.setText(piece.toString());
            }   
        }
    }

    /*
    public static void clearScreen(){
        System.out.println("\033[H\033[2J");
        System.out.flush();
    }

    public static ChessPosition readChessPosition(Scanner sc){
        try{
            String s = sc.nextLine();
            char column = s.charAt(0);
            int row = Integer.parseInt(s.substring(1));
            return new ChessPosition(column, row);
        }
        catch (RuntimeException e){
            throw new InputMismatchException("Error reading ChessPosition. Valid values are from a1 to h8.");
        }
    }

    public static void printMatch(ChessMatch chessMatch, List<ChessPiece> capturedPieces){
        printBoard(chessMatch.getPieces());
        System.out.println();
        printCapturedPieces(capturedPieces);
        System.out.println();
        System.out.println("Turn: " + chessMatch.getTurn());
        if(!chessMatch.getCheckMate()){
            System.out.println("Waiting player: " + chessMatch.getCurrentPlayer());
            if(chessMatch.getCheck()){
                System.out.println("CHECK!");
            }
        }else{
            System.out.println("CHECKMATE!");
            System.out.println("Winner: " + chessMatch.getCurrentPlayer());
        }    
    }

    public static void printCapturedPieces(List<ChessPiece> printCapturedPieces){
        System.out.println();
        List<ChessPiece> white = printCapturedPieces.stream().filter(x -> x.getColor() == Color.WHITE).collect(Collectors.toList());
        List<ChessPiece> black = printCapturedPieces.stream().filter(x -> x.getColor() == Color.BLACK).collect(Collectors.toList());
        System.out.println("Captured pieces:");
        System.out.print("White: ");
        System.out.print(ANSI_WHITE);
        System.out.println(Arrays.toString(white.toArray()));
        System.out.print(ANSI_RESET);
        System.out.print("Black: ");
        System.out.print(ANSI_YELLOW);
        System.out.println(Arrays.toString(black.toArray()));
        System.out.print(ANSI_RESET);
    }
    */
}
