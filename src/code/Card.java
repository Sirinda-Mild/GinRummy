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

    private static final int CARD_WIDTH = 80;
    private static final int CARD_HEIGHT = 120;

    enum Suit {
        c, d, h, s;
    }
//
//    enum Rank {
//        TWO(2), THREE(3), FOUR(4), FIVE(5), SIX(6), SEVEN(7), EIGHT(8), NINE(9), TEN(10),
//        j(10), q(10), k(10), a(1);
//
//        final int value;
//
//        Rank(int value) {
//            this.value = value;
//        }
//
//        String displayName() {
//            return ordinal() < 9 ? String.valueOf(value) : name().substring(0, 1);
//        }
//    }

    public final Suit suit;
    private int rank;

    public Card(Suit suit, int rank) {
        this.suit = suit;
        this.rank = rank;
        String imageLocation = "resources/" + Integer.toString(rank) + suit + ".png";

        Rectangle card = new Rectangle(CARD_WIDTH, CARD_HEIGHT);
        card.setArcWidth(15);
        card.setArcHeight(15);
        Image imgCard = new Image(imageLocation);
        card.setFill(new ImagePattern(imgCard));

//        Text text1 = new Text(rank.displayName());
//        text1.setFont(Font.font(18));
//        text1.setX(CARD_WIDTH - text1.getLayoutBounds().getWidth() - 10);
//        text1.setY(text1.getLayoutBounds().getHeight());
//
//        Text text2 = new Text(text1.getText());
//        text2.setFont(Font.font(18));
//        text2.setX(10);
//        text2.setY(CARD_HEIGHT - 10);
//        ImageView view = new ImageView(suit.image);
//        view.setRotate(180);
//        view.setX(CARD_WIDTH - 32);
//        view.setY(CARD_HEIGHT - 32);
        getChildren().addAll(card);
    }

    @Override
    public String toString() {
        return Integer.toString(rank) + " of " + suit.toString();
    }
}
