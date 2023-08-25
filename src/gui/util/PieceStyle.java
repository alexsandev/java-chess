package gui.util;

import model.chess.Color;

public class PieceStyle {
    
    public static String getStyle(Color color, String type){
        String style = "";
        if(type == "P") style = getPawn(color);
        if(type == "R") style = getRock(color);
        if(type == "B") style = getBishop(color);
        if(type == "K") style = getKing(color);
        if(type == "N") style = getKnight(color);
        if(type == "Q") style = getQueen(color);
        return style;
    }

    public static String getKing(Color color){
        if(color == Color.WHITE){
            return "-fx-background-position: center;"
            + "-fx-background-color: transparent;" 
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/king.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;";
        }else{
            return "-fx-background-position: center;"
            + "-fx-background-color: transparent;" 
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/black_king.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;";
        }
    }

    public static String getQueen(Color color){
        if(color == Color.WHITE){
            return "-fx-background-position: center;"
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/queen.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;"; 
        }else{
            return "-fx-background-position: center;" 
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/black_queen.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;";
        }
    }

    public static String getBishop(Color color){
        if(color == Color.WHITE){
            return "-fx-background-position: center;"
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/bishop.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;";
        }else{
            return "-fx-background-position: center;"
            + "-fx-background-color: transparent;" 
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/black_bishop.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;";
        }
    }

    public static String getKnight(Color color){
        if(color == Color.WHITE){
            return "-fx-background-position: center;"
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/knight.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;";
        }else{
            return "-fx-background-position: center;" 
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/black_knight.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;";
        }
    }

    public static String getRock(Color color){
        if(color == Color.WHITE){ 
            return "-fx-background-position: center;"
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/rock.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;";
        }else{
            return "-fx-background-position: center;"
            + "-fx-background-color: transparent;"
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/black_rock.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;";
        }  
    }
    
    public static String getPawn(Color color){
        if(color == Color.WHITE){
            return "-fx-background-position: center;"
            + "-fx-background-color: transparent;" 
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/pawn.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;";
        }else{
            return "-fx-background-position: center;"
            + "-fx-background-color: transparent;" 
            + "-fx-background-image: url(\"file:///D:/OneDrive/Documentos/Java/java-chess/src/gui/images/black_pawn.png\");"
            + "-fx-background-size: 50px 50px;"
            + "-fx-background-repeat: no-repeat;";
        }
    }
}
