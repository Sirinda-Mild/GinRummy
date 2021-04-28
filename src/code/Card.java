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
