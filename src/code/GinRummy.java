/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.ArrayList;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class GinRummy extends Application {

    private Deck deck = new Deck();
    private Player player;
    private Bot bot;
    private UpCard upcard;
    private DrawPile drawpile;

    private SimpleBooleanProperty playable = new SimpleBooleanProperty(true);
    private Glow glow = new Glow();

    private Parent createGame() {
        System.out.println("creatgame laewja");
        //set main pane
        Pane root = new Pane();
        root.setPrefSize(950, 600);

        Region background = new Region();
        background.setPrefSize(950, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        //set next pane
        StackPane rootLayout = new StackPane();
        rootLayout.setPadding(new Insets(5, 5, 5, 5));
        Rectangle BG = new Rectangle(924, 550);
        BG.setArcWidth(30);
        BG.setArcHeight(30);
        BG.setFill(Color.GREEN);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        //set final pane
        HBox botCardsPane = new HBox(10);
        HBox playerCardsPane = new HBox(10);
        HBox drawCardsPane = new HBox(10);
        StackPane upCardPilePane = new StackPane();
        StackPane drawPilePane = new StackPane();
        playerCardsPane.setPadding(new Insets(5, 5, 5, 5));
        botCardsPane.setPadding(new Insets(5, 5, 5, 5));

        playerCardsPane.setAlignment(Pos.CENTER);
        botCardsPane.setAlignment(Pos.CENTER);
        upCardPilePane.setAlignment(Pos.CENTER);
        drawPilePane.setAlignment(Pos.CENTER);

        //declare arraylist
        ArrayList<Card> playerCards = new ArrayList<>();
        ArrayList<Card> botCards = new ArrayList<>();
        ArrayList<Card> upCardPile = new ArrayList<>();
        ArrayList<Card> drawPile = new ArrayList<>();
        player = new Player(playerCards);
        bot = new Bot(botCards);
        upcard = new UpCard(upCardPile);
        drawpile = new DrawPile(drawPile);

        //add card to pane
        playerCardsPane.getChildren().addAll(playerCards);
        botCardsPane.getChildren().addAll(botCards);
        upCardPilePane.getChildren().addAll(upCardPile);
        drawPilePane.getChildren().addAll(drawPile);

        drawCardsPane.setAlignment(Pos.CENTER);
        drawCardsPane.getChildren().addAll(drawPilePane, upCardPilePane);

        //Bot Score
        Text botScore = new Text("Dealer Score : ");
        botScore.setFont(Font.font("Tahoma", 18));
        botScore.setFill(Color.WHITE);

        //Player Score
        HBox playerInfo = new HBox(30);
        Text playerScore = new Text("Player Score : ");
        Text playerDeadwood = new Text("Player Deadwood : ");
        playerScore.setFont(Font.font("Tahoma", 18));
        playerScore.setFill(Color.WHITE);

        playerDeadwood.setFont(Font.font("Tahoma", 18));
        playerDeadwood.setFill(Color.WHITE);

        playerInfo.setAlignment(Pos.CENTER);
        playerInfo.getChildren().addAll(playerScore, playerDeadwood);

        //All button
        HBox buttonBox = new HBox(5);
        buttonBox.setPadding(new Insets(5, 5, 5, 5));
        Button btnPass = new Button("PASS");
        Button btnTake = new Button("TAKE");
        Button btnNew = new Button("NEW");
        Button btnDiscard = new Button("DISCARD");
        Button btnKnock = new Button("KNOCK");

        // ADD STACKS TO ROOT LAYOUT
        buttonBox.getChildren().addAll(btnTake, btnPass);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        vbox.getChildren().addAll(botCardsPane, botScore, drawCardsPane, buttonBox, playerInfo, playerCardsPane);
        rootLayout.getChildren().addAll(new StackPane(BG), new StackPane(vbox));
        root.getChildren().addAll(background, rootLayout);

        // BIND PROPERTIES
        btnTake.disableProperty().bind(playable);
        btnPass.disableProperty().bind(playable);
        btnNew.disableProperty().bind(playable);
        btnDiscard.disableProperty().bind(playable);
        btnKnock.disableProperty().bind(playable.not());

        //Score 
        playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
        playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(player.Deadwood())));
        botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(bot.Score())));

        // INIT BUTTONS
        btnTake.setOnAction(event -> {

        });

        btnPass.setOnAction(event -> {

        });

        startNewGame();
        return root;
    }

    private void startNewGame() {
        playable.set(true);
//        message.setText("");

        deck.refill();

        bot.reset();
        player.reset();

//        player.takeCard(deck.drawCard());
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
        //MENU
        //set pane for menu
        Pane root = new Pane();
        root.setPrefSize(950, 600);
        Pane rootLayout = new Pane();
        rootLayout.setPrefSize(950, 600);

        //set black background
        Region background = new Region();
        background.setPrefSize(950, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        //set menu background
        Rectangle menuBg = new Rectangle(950, 600);
        Image imgBg = new Image("resources/background/bg3.jpg");
        menuBg.setFill(new ImagePattern(imgBg));

        //set text menu background
        Rectangle textGin = new Rectangle(900, 560);
        Image imgtextGin = new Image("resources/background/textginrummy.png");
        textGin.setFill(new ImagePattern(imgtextGin));

        //set button on menu
        Rectangle btnStart = new Rectangle(204, 420, 211, 70);
        Image imgBtnStart = new Image("resources/background/play.png");
        btnStart.setFill(new ImagePattern(imgBtnStart));

        Rectangle btnExit = new Rectangle(528, 420, 211, 70);
        Image imgBtnExit = new Image("resources/background/exit.png");
        btnExit.setFill(new ImagePattern(imgBtnExit));

        //add all to layout
        rootLayout.getChildren().addAll(menuBg, textGin, btnStart, btnExit);
        root.getChildren().addAll(background, rootLayout);

        //button action
        //start game
        btnStart.setOnMousePressed(event -> {
            primaryStage.setScene(new Scene(createGame()));
        });

        btnExit.setOnMousePressed(event -> {
            Platform.exit();
        });

        //set effect button
        btnStart.setOnMouseEntered(event -> {
            glow.setLevel(1.5);
            btnStart.setEffect(glow);
        });

        btnStart.setOnMouseExited(event -> {
            btnStart.setEffect(null);
        });

        btnExit.setOnMouseEntered(event -> {
            glow.setLevel(1.3);
            btnExit.setEffect(glow);
        });

        btnExit.setOnMouseExited(event -> {
            btnExit.setEffect(null);
        });

        primaryStage.setScene(new Scene(root));
        primaryStage.setWidth(950);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);
        primaryStage.setTitle("GinRummy");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

}
