/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
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

    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
    private Glow glow = new Glow();
    private boolean isPass = false;
    private boolean isTake = false;
    private boolean firstPass = false;
    private boolean firstPoint = true;
    private int playerDW = 0;
    private int botDW = 0;

    private Parent createGame() {
        System.out.println("creatgame laewja");
        //set main pane
        Pane root = new Pane();
        root.setPrefSize(1000, 600);

        Region background = new Region();
        background.setPrefSize(1000, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        //set next pane
        StackPane rootLayout = new StackPane();
        rootLayout.setPadding(new Insets(5, 5, 5, 5));
        Rectangle BG = new Rectangle(973, 550);
        BG.setArcWidth(30);
        BG.setArcHeight(30);
        BG.setFill(Color.GREEN);

        VBox vbox = new VBox(20);
        vbox.setAlignment(Pos.CENTER);

        //set final pane        
        HBox botCardsPane = new HBox(10);
        HBox botDeadwoodCardsPane = new HBox(10);
        HBox botStraightCardsPane = new HBox(10);
        HBox botKindCardsPane = new HBox(10);

        HBox playerCardsPane = new HBox(10);
        HBox playerDeadwoodCardsPane = new HBox(10);
        HBox playerStraightCardsPane = new HBox(10);
        HBox playerKindCardsPane = new HBox(10);

        HBox drawCardsPane = new HBox(10);
        StackPane upCardPilePane = new StackPane();
        StackPane drawPilePane = new StackPane();
        playerDeadwoodCardsPane.setPadding(new Insets(5, 5, 5, 5));
        botDeadwoodCardsPane.setPadding(new Insets(5, 5, 5, 5));
        playerKindCardsPane.setPadding(new Insets(5, 5, 5, 5));
        botKindCardsPane.setPadding(new Insets(5, 5, 5, 5));
        playerStraightCardsPane.setPadding(new Insets(5, 5, 5, 5));
        botStraightCardsPane.setPadding(new Insets(5, 5, 5, 5));

        playerDeadwoodCardsPane.setAlignment(Pos.CENTER);
        botDeadwoodCardsPane.setAlignment(Pos.CENTER);
        playerKindCardsPane.setAlignment(Pos.CENTER);
        botKindCardsPane.setAlignment(Pos.CENTER);
        playerStraightCardsPane.setAlignment(Pos.CENTER);
        botStraightCardsPane.setAlignment(Pos.CENTER);
        upCardPilePane.setAlignment(Pos.CENTER);
        drawPilePane.setAlignment(Pos.CENTER);
        playerCardsPane.setAlignment(Pos.CENTER);
        botCardsPane.setAlignment(Pos.CENTER);

        drawCardsPane.setAlignment(Pos.CENTER);
        drawCardsPane.getChildren().addAll(drawPilePane, upCardPilePane);

        //declare arraylist
        player = new Player(playerDeadwoodCardsPane.getChildren(), playerStraightCardsPane.getChildren(), playerKindCardsPane.getChildren());
        bot = new Bot(botDeadwoodCardsPane.getChildren(), botStraightCardsPane.getChildren(), botKindCardsPane.getChildren());
        upcard = new UpCard(upCardPilePane.getChildren());
        drawpile = new DrawPile(drawPilePane.getChildren());
        playerCardsPane.getChildren().addAll(playerKindCardsPane, playerStraightCardsPane, playerDeadwoodCardsPane);
        botCardsPane.getChildren().addAll(botKindCardsPane, botStraightCardsPane, botDeadwoodCardsPane);

        //Bot Score
        Text botScore = new Text("Dealer Score : ");
        botScore.setFont(Font.font("Tahoma", 18));
        botScore.setFill(Color.WHITE);

        Text passText = new Text("PASS");
        passText.setFont(Font.font("Tahoma", 40));
        passText.setFill(Color.YELLOW);
        passText.setX(200);
        passText.setY(200);

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
        btnTake.disableProperty().bind(playable.not());
        btnPass.disableProperty().bind(playable.not());
        btnNew.disableProperty().bind(playable.not());
        btnDiscard.disableProperty().bind(playable.not());
        btnKnock.disableProperty().bind(playable);

        // INIT BUTTONS
        btnTake.setOnAction(event -> {
            player.takeDeadwoodCard((Card) upcard.drawCard());
            if (firstPass == true) {
                root.getChildren().remove(passText);
            }
//            playerSortStraight();
//            playerSortThreeofKind();
//            botSortStraight();
//            botSortThreeofKind();
        });

        btnPass.setOnAction(event -> {
            botTakeorPass();
            if (isPass == true) {
                root.getChildren().add(passText);
                buttonBox.getChildren().remove(btnPass);
                buttonBox.getChildren().add(btnNew);
                playable.set(true);
                firstPass = true;
                isPass = false;
            }
            if (isTake == true) {
                botDropCard();
            }
        });

        btnNew.setOnAction(event -> {
            player.takeDeadwoodCard((Card) drawpile.drawCard());
            if (firstPass == true) {
                root.getChildren().remove(passText);
            }
        });

        startNewGame();
        playerDW = player.Deadwood();
        botDW = bot.Deadwood();
        player.sortDeadwoodCards();
//        bot.sortDeadwoodCards();
        //Score 
        playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
        playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(playerDW)));
        botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(botDW)));

//        checkPlayerStraight();
//        checkBotStraight();
        return root;
    }

    private void startNewGame() {
        playable.set(true);

        deck.refill();

        bot.reset();
        player.reset();

        player.takeDeadwoodCard(new Card("c", "7"));
        player.takeDeadwoodCard(new Card("h", "7"));
        player.takeDeadwoodCard(new Card("s", "3"));
        player.takeDeadwoodCard(new Card("s", "7"));
        player.takeDeadwoodCard(new Card("d", "5"));
        player.takeDeadwoodCard(new Card("d", "7"));
        player.takeDeadwoodCard(new Card("h", "3"));
        player.takeDeadwoodCard(new Card("s", "5"));
        player.takeDeadwoodCard(new Card("c", "3"));
        player.takeDeadwoodCard(new Card("s", "2"));
        System.out.println("deadwood : " + player.getDeadwoodCards());
        System.out.println("kind : " + player.getKindCards());
        System.out.println("straight : " + player.getStraightCards());
        //player and bot draw card
        for (int i = 0; i < 10; i++) {
//            player.takeDeadwoodCard(deck.drawCard());
            bot.takeDeadwoodCard(deck.drawCard());
        }

        //deck keep card
        for (int i = 0; i < 31; i++) {
            drawpile.keepCard(deck.drawCard());
        }
        //open top card
        upcard.keepCard(deck.drawCard());

        //print test
        System.out.println(player.toString());
        System.out.println(bot.toString());
        System.out.println(drawpile.toString());
        System.out.println(upcard.toString());
    }

//    private void endRound() {
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
    private void botTakeorPass() {
        playable.set(false);
        if (bot.takeOrPass(upcard, bot) == true) {
            bot.takeDeadwoodCard((Card) upcard.drawCard());
            isTake = true;
        } else {
            isPass = true;
        }
    }

//    private void checkPlayerStraight() {
//        ArrayList<String> clubs = new ArrayList<>();
//        ArrayList<String> diamonds = new ArrayList<>();
//        ArrayList<String> hearts = new ArrayList<>();
//        ArrayList<String> spades = new ArrayList<>();
//        int straightCount = 0;
//        int deadwoodRe = 0;
//        for (int i = 0; i < player.getSize(); i++) {
//            if (player.getSuit(i) == 'c') {
//                clubs.add(Integer.toString(i));
//            } else if (player.getSuit(i) == 'd') {
//                diamonds.add(Integer.toString(i));
//            } else if (player.getSuit(i) == 'h') {
//                hearts.add(Integer.toString(i));
//            } else if (player.getSuit(i) == 's') {
//                spades.add(Integer.toString(i));
//            }
//        }
//        System.out.println("clubs");
//        System.out.println(clubs);
//        System.out.println("clubs size " + clubs.size());
//        straightCount = 0;
//        deadwoodRe = 0;
//        if (clubs.size() >= 3) {
//            for (int i = 0, j = i + 1; j < clubs.size(); i++, j++) {
//                if ((player.getRankValue(Integer.parseInt(clubs.get(i))) + 1) == player.getRankValue(Integer.parseInt(clubs.get(j)))) {
//                    straightCount++;
//                }
//            }
//            System.out.println("count " + straightCount);
//            if (straightCount >= 2) {
//                System.out.println("straight jaa");
//                for (int i = 0, j = clubs.size() - 1; j >= 0 && i < player.getSize(); i++) {
//                    if (player.getValue(i).equals(clubs.get(j))) {
//                        player.toBack(i);
//                        j--;
//                        i = 0;
//                    }
//                }
//                for (int index = 0; index < clubs.size(); index++) {
//                    deadwoodRe += player.getRankValue(Integer.parseInt(clubs.get(index)));
//                }
//                playerDW -= deadwoodRe;
//            }
//        }
//
//        System.out.println("diamond");
//        System.out.println(diamonds);
//        System.out.println("diamonds size " + diamonds.size());
//        straightCount = 0;
//        deadwoodRe = 0;
//        if (diamonds.size() >= 3) {
//            for (int i = 0, j = i + 1; j < diamonds.size(); i++, j++) {
//                if ((player.getRankValue(Integer.parseInt(diamonds.get(i))) + 1) == player.getRankValue(Integer.parseInt(diamonds.get(j)))) {
//                    straightCount++;
//                }
//            }
//
//            System.out.println("count " + straightCount);
//            if (straightCount >= 2) {
//                System.out.println("straight jaa");
//                for (int i = 0, j = diamonds.size() - 1; j >= 0 && i < player.getSize(); i++) {
//                    if (player.getValue(i).equals(diamonds.get(j))) {
//                        player.toBack(i);
//                        j--;
//                        i = 0;
//                    }
//                }
//                for (int index = 0; index < diamonds.size(); index++) {
//                    deadwoodRe += player.getRankValue(Integer.parseInt(diamonds.get(index)));
//                }
//                playerDW -= deadwoodRe;
//            }
//        }
//        System.out.println("heart");
//        System.out.println(hearts);
//        System.out.println("hearts size " + hearts.size());
//        straightCount = 0;
//        deadwoodRe = 0;
//        if (hearts.size() >= 3) {
//            for (int i = 0, j = i + 1; j < hearts.size(); i++, j++) {
//                if ((player.getRankValue(Integer.parseInt(hearts.get(i))) + 1) == player.getRankValue(Integer.parseInt(hearts.get(j)))) {
//                    straightCount++;
//                }
//            }
//
//            System.out.println("count " + straightCount);
//            if (straightCount >= 2) {
//                System.out.println("straight jaa");
//                for (int i = 0, j = hearts.size() - 1; j >= 0 && i < player.getSize(); i++) {
//                    if (player.getValue(i).equals(hearts.get(j))) {
//                        player.toBack(i);
//                        j--;
//                        i = 0;
//                    }
//                }
//                for (int index = 0; index < hearts.size(); index++) {
//                    deadwoodRe += player.getRankValue(Integer.parseInt(hearts.get(index)));
//                }
//                playerDW -= deadwoodRe;
//            }
//        }
//        System.out.println("spades");
//        System.out.println(spades);
//        System.out.println("spades size " + spades.size());
//        straightCount = 0;
//        deadwoodRe = 0;
//        if (spades.size() >= 3) {
//            for (int i = 0, j = i + 1; j < spades.size(); i++, j++) {
//                if ((player.getRankValue(Integer.parseInt(spades.get(i))) + 1) == player.getRankValue(Integer.parseInt(spades.get(j)))) {
//                    straightCount++;
//                }
//            }
//
//            System.out.println("count " + straightCount);
//            if (straightCount >= 2) {
//                System.out.println("straight jaa");
//                for (int i = 0, j = spades.size() - 1; j >= 0 && i < player.getSize(); i++) {
//                    if (player.getValue(i).equals(spades.get(j))) {
//                        player.toBack(i);
//                        j--;
//                        i = 0;
//                    }
//                }
//                for (int index = 0; index < spades.size(); index++) {
//                    deadwoodRe += player.getRankValue(Integer.parseInt(spades.get(index)));
//                }
//                playerDW -= deadwoodRe;
//            }
//        }
//    }
//
//    private void checkBotStraight() {
//        ArrayList<String> clubs = new ArrayList<>();
//        ArrayList<String> diamonds = new ArrayList<>();
//        ArrayList<String> hearts = new ArrayList<>();
//        ArrayList<String> spades = new ArrayList<>();
//        int straightCount = 0;
//        int deadwoodRe = 0;
//        for (int i = 0; i < bot.getSize(); i++) {
//            if (bot.getSuit(i) == 'c') {
//                clubs.add(Integer.toString(i));
//            } else if (bot.getSuit(i) == 'd') {
//                diamonds.add(Integer.toString(i));
//            } else if (bot.getSuit(i) == 'h') {
//                hearts.add(Integer.toString(i));
//            } else if (bot.getSuit(i) == 's') {
//                spades.add(Integer.toString(i));
//            }
//        }
//
//        straightCount = 0;
//        deadwoodRe = 0;
//        if (clubs.size() >= 3) {
//            for (int i = 0, j = i + 1; j < clubs.size(); i++, j++) {
//                if ((bot.getRankValue(Integer.parseInt(clubs.get(i))) + 1) == bot.getRankValue(Integer.parseInt(clubs.get(j)))) {
//                    straightCount++;
//                }
//            }
//            if (straightCount >= 2) {
//                for (int i = 0, j = clubs.size() - 1; j >= 0 && i < bot.getSize(); i++) {
//                    if (bot.getValue(i).equals(clubs.get(j))) {
//                        bot.toBack(i);
//                        j--;
//                        i = 0;
//                    }
//                }
//                for (int index = 0; index < clubs.size(); index++) {
//                    deadwoodRe += bot.getRankValue(Integer.parseInt(clubs.get(index)));
//                }
//                botDW -= deadwoodRe;
//            }
//        }
//
//        straightCount = 0;
//        deadwoodRe = 0;
//        if (diamonds.size() >= 3) {
//            for (int i = 0, j = i + 1; j < diamonds.size(); i++, j++) {
//                if ((bot.getRankValue(Integer.parseInt(diamonds.get(i))) + 1) == bot.getRankValue(Integer.parseInt(diamonds.get(j)))) {
//                    straightCount++;
//                }
//            }
//            if (straightCount >= 2) {
//                for (int i = 0, j = diamonds.size() - 1; j >= 0 && i < bot.getSize(); i++) {
//                    if (bot.getValue(i).equals(diamonds.get(j))) {
//                        bot.toBack(i);
//                        j--;
//                        i = 0;
//                    }
//                }
//                for (int index = 0; index < diamonds.size(); index++) {
//                    deadwoodRe += bot.getRankValue(Integer.parseInt(diamonds.get(index)));
//                }
//                botDW -= deadwoodRe;
//            }
//        }
//
//        straightCount = 0;
//        deadwoodRe = 0;
//        if (hearts.size() >= 3) {
//            for (int i = 0, j = i + 1; j < hearts.size(); i++, j++) {
//                if ((bot.getRankValue(Integer.parseInt(hearts.get(i))) + 1) == bot.getRankValue(Integer.parseInt(hearts.get(j)))) {
//                    straightCount++;
//                }
//            }
//            if (straightCount >= 2) {
//                for (int i = 0, j = hearts.size() - 1; j >= 0 && i < bot.getSize(); i++) {
//                    if (bot.getValue(i).equals(hearts.get(j))) {
//                        bot.toBack(i);
//                        j--;
//                        i = 0;
//                    }
//                }
//                for (int index = 0; index < hearts.size(); index++) {
//                    deadwoodRe += bot.getRankValue(Integer.parseInt(hearts.get(index)));
//                }
//                botDW -= deadwoodRe;
//            }
//        }
//
//        straightCount = 0;
//        deadwoodRe = 0;
//        if (spades.size() >= 3) {
//            for (int i = 0, j = i + 1; j < spades.size(); i++, j++) {
//                if ((bot.getRankValue(Integer.parseInt(spades.get(i))) + 1) == bot.getRankValue(Integer.parseInt(spades.get(j)))) {
//                    straightCount++;
//                }
//            }
//            if (straightCount >= 2) {
//                for (int i = 0, j = spades.size() - 1; j >= 0 && i < bot.getSize(); i++) {
//                    if (bot.getValue(i).equals(spades.get(j))) {
//                        bot.toBack(i);
//                        j--;
//                        i = 0;
//                    }
//                }
//                for (int index = 0; index < spades.size(); index++) {
//                    deadwoodRe += bot.getRankValue(Integer.parseInt(spades.get(index)));
//                }
//                botDW -= deadwoodRe;
//            }
//        }
//    }
    private void botDropCard() {
        playable.set(false);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //MENU
        //set pane for menu
        Pane root = new Pane();
        root.setPrefSize(1000, 600);
        Pane rootLayout = new Pane();
        rootLayout.setPrefSize(1000, 600);

        //set black background
        Region background = new Region();
        background.setPrefSize(1000, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        //set menu background
        Rectangle menuBg = new Rectangle(1000, 600);
        Image imgBg = new Image("resources/background/bg3.jpg");
        menuBg.setFill(new ImagePattern(imgBg));

        //set text menu background
        Rectangle textGin = new Rectangle(950, 560);
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
