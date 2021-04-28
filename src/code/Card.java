//package code;
//
//import java.io.File;
//import javafx.scene.image.Image;
//import javafx.scene.paint.Color;
//import javafx.scene.paint.ImagePattern;
//import javafx.scene.shape.Rectangle;
//import javafx.scene.shape.Shape;
//
//public class Card {
//
//    private String suit;
//    private String rank;
//    private String color;
//    private Image cardImage;
//    private Shape cardObj;
//    private String id;
//    private String imageLocation;
//    private boolean draggable;
//
//    public Card(String suit, String rank) {
//        super(146, 194);
//        this.suit = suit;
//        this.rank = rank;
//        draggable = false;
//        imageLocation = "/resources/" + rank + suit + ".png";
//        super.setArcHeight(15);
//        super.setArcWidth(15);
//        super.setStyle("-fx-background-image: url(" + imageLocation + "); -fx-background-repeat: no-repeat;-fx-background-size: contain;");
//
//        Image test = new Image(imageLocation);
//        super.setFill(new ImagePattern(test));
//        super.setStroke(Color.BLACK);
//        super.setManaged(false);
//        id = rank + suit;
//        super.setId(id);
//        updateColor(suit);
//    }
//
//    Card() {
//
//    }
//
//    public boolean getDraggable() {
//        return this.draggable;
//    }
//
//    public void setDraggable(boolean value) {
//        this.draggable = value;
//    }
//
//    public String getSuit() {
//        return this.suit;
//    }
//
//    public int getRank() {
//        if (this.rank.equals("a")) {
//            return 1;
//        }
//        if (this.rank.equals("j")) {
//            return 11;
//        }
//        if (this.rank.equals("q")) {
//            return 12;
//        }
//        if (this.rank.equals("k")) {
//            return 13;
//        }
//        int rankToInt = Integer.parseInt(this.rank);
//        return rankToInt;
//    }
//
//    public String getStringRank() {
//        return this.rank;
//    }
//
//    public String getColor() {
//        return this.color;
//    }
//
//    public Image getImage() {
//        return this.cardImage;
//    }
//
//    public Shape getCardObj() {
//        return this.cardObj;
//    }
//
//    public String getID() {
//        return this.id;
//    }
//
//    public Image getCardImage() {
//        return this.cardImage;
//    }
//
//    public void updateColor(String suit) {
//        if ((suit.equals("d")) || (suit.equals("h"))) {
//            this.color = "red";
//        } else if ((suit.equals("c")) || (suit.equals("s"))) {
//            this.color = "black";
//        }
//    }
//}
package code;

import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Card extends Parent {

    private static final int CARD_WIDTH = 75;
    private static final int CARD_HEIGHT = 110;
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
        String imageLocation = "resources/" + rank + suit + ".png";

        Rectangle card = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        card.setArcWidth(15);
        card.setArcHeight(15);
        Image imgCard = new Image(imageLocation);
        card.setFill(new ImagePattern(imgCard));

        getChildren().addAll(card);
    }

    private void backCard() {

    }

    @Override
    public String toString() {
        return rank.toString() + suit.toString();
    }
}
