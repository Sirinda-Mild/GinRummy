/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GinRummy extends Application {

    private Deck deck = new Deck();
    private Player player;
    private Bot bot;
    private UpCard upcard;
    private DrawPile drawpile;
//    private Text message = new Text();

    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);

    private HBox botCardsPane = new HBox(10);
    private HBox playerCardsPane = new HBox(10);
    private HBox drawCardsPane = new HBox(10);
    private StackPane upCardPilePane = new StackPane();
    private StackPane drawPilePane = new StackPane();
    private ArrayList<Card> playerCards = new ArrayList<>();
    private ArrayList<Card> botCards = new ArrayList<>();
    private ArrayList<Card> upCardPile = new ArrayList<>();
    private ArrayList<Card> drawPile = new ArrayList<>();

    private Parent createContent() {
        playerCardsPane.setPadding(new Insets(5, 5, 5, 5));
        botCardsPane.setPadding(new Insets(5, 5, 5, 5));
        
        playerCardsPane.setAlignment(Pos.CENTER);
        botCardsPane.setAlignment(Pos.CENTER);
        upCardPilePane.setAlignment(Pos.CENTER);
        drawPilePane.setAlignment(Pos.CENTER);
        
        player = new Player(playerCards);
        bot = new Bot(botCards);
        upcard = new UpCard(upCardPile);
        drawpile = new DrawPile(drawPile);

        playerCardsPane.getChildren().addAll(playerCards);
        botCardsPane.getChildren().addAll(botCards);
        upCardPilePane.getChildren().addAll(upCardPile);
        drawPilePane.getChildren().addAll(drawPile);
        
        drawCardsPane.setAlignment(Pos.CENTER);
        drawCardsPane.getChildren().addAll(drawPilePane, upCardPilePane);

        Pane root = new Pane();
        root.setPrefSize(1000, 600);

        Region background = new Region();
        background.setPrefSize(1000, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        StackPane rootLayout = new StackPane();
        rootLayout.setPadding(new Insets(5, 5, 5, 5));
        Rectangle BG = new Rectangle(973, 550);
        BG.setArcWidth(30);
        BG.setArcHeight(30);
        BG.setFill(Color.GREEN);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        Text botScore = new Text("Dealer Score : ");
        Text playerScore = new Text("Player Score : ");

        HBox buttonBox = new HBox(5);
        buttonBox.setPadding(new Insets(5, 5, 5, 5));
        Button btnPass = new Button("PASS");
        Button btnTake = new Button("TAKE");
//        Button btnNew = new Button("NEW");
//        Button btnDiscard = new Button("DISCARD");
//        Button btnKnock = new Button("KNOCK");

        buttonBox.getChildren().addAll(btnTake, btnPass);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        vbox.getChildren().addAll(botCardsPane, botScore, drawCardsPane, buttonBox, playerScore, playerCardsPane);

        // ADD BOTH STACKS TO ROOT LAYOUT
        rootLayout.getChildren().addAll(new StackPane(BG), new StackPane(vbox));
        root.getChildren().addAll(background, rootLayout);

        // BIND PROPERTIES
        btnTake.disableProperty().bind(playable.not());
//        btnHit.disableProperty().bind(playable.not());
//        btnStand.disableProperty().bind(playable.not());
//
//        playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(player.valueProperty().asString()));
//        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(dealer.valueProperty().asString()));
//
//        player.valueProperty().addListener((obs, old, newValue) -> {
//            if (newValue.intValue() >= 21) {
//                endGame();
//            }
//        });
//
//        dealer.valueProperty().addListener((obs, old, newValue) -> {
//            if (newValue.intValue() >= 21) {
//                endGame();
//            }
//        });
//
        // INIT BUTTONS
        btnTake.setOnAction(event -> {
            
        });

        btnPass.setOnAction(event -> {

        });
//
//        btnStand.setOnAction(event -> {
//            while (dealer.valueProperty().get() < 17) {
//                dealer.takeCard(deck.drawCard());
//            }
//
//            endGame();
//        });
        startNewGame();
        System.out.println("start new game");
        return root;
    }

    private void startNewGame() {
        playable.set(true);
//        message.setText("");

        deck.refill();

        bot.reset();
        player.reset();

        player.takeCard(deck.drawCard());
//        bot.takeCard(deck.drawCard(), 10);
//        drawpile.keepCard(deck.drawCard(), 31);
//        upcard.keepCard(deck.drawCard(), 1);
    }

//    private void endGame() {
//        playable.set(false);
//
//        int dealerValue = bot.valueProperty().get();
//        int playerValue = player.valueProperty().get();
//        String winner = "Exceptional case: d: " + dealerValue + " p: " + playerValue;
//
//        // the order of checking is important
//        if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue
//                || (dealerValue < 21 && dealerValue > playerValue)) {
//            winner = "DEALER";
//        } else if (playerValue == 21 || dealerValue > 21 || playerValue > dealerValue) {
//            winner = "PLAYER";
//        }
//
//        message.setText(winner + " WON");
//    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setScene(new Scene(createContent()));
        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("GinRummy");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
