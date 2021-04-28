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

    private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
    private Glow glow = new Glow();
    private boolean isPass = false;
    private boolean isTake = false;
    private boolean firstPass = false;

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

        drawCardsPane.setAlignment(Pos.CENTER);
        drawCardsPane.getChildren().addAll(drawPilePane, upCardPilePane);

        //declare arraylist
        player = new Player(playerCardsPane.getChildren());
        bot = new Bot(botCardsPane.getChildren());
        upcard = new UpCard(upCardPilePane.getChildren());
        drawpile = new DrawPile(drawPilePane.getChildren());

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

        //Score 
        playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
        playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(player.Deadwood())));
        botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(bot.Score())));

        // INIT BUTTONS
        btnTake.setOnAction(event -> {
            player.takeCard((Card) upcard.drawCard());
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
            player.takeCard((Card) drawpile.drawCard());
            if (firstPass == true) {
                root.getChildren().remove(passText);
            }
        });

        startNewGame();
        return root;
    }

    private void startNewGame() {
        playable.set(true);

        deck.refill();

        bot.reset();
        player.reset();

        //player and bot draw card
        for (int i = 0; i < 10; i++) {
            player.takeCard(deck.drawCard());
            bot.takeCard(deck.drawCard());
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
    
    private void botTakeorPass() {
        playable.set(false);
        if (checkStraightorKind(upcard, bot) == true) {
            bot.takeCard((Card) upcard.drawCard());
            isTake = true;
        } else {
            isPass = true;
        }
    }

    private boolean checkStraightorKind(UpCard upcard, Bot bot) {
        int sameRank = 0;
        int sameSuit = 0;
        boolean isStraight = false;

        //check three of kind
        for (int i = 0; i < bot.getSize(); i++) {
            if (upcard.getRank(upcard.getSize() - 1) == bot.getRank(i)) {
                sameRank++;
            }
        }

        //check straight
        for (int i = 0; i < bot.getSize(); i++) {
            if ((upcard.getSuit(upcard.getSize() - 1) == bot.getSuit(i))
                    && (bot.checkStraight(i) == (upcard.checkStraight(upcard.getSize() - 1) + 1)
                    || (bot.checkStraight(i) == upcard.checkStraight(upcard.getSize() - 1) - 1)
                    || (bot.checkStraight(i) == upcard.checkStraight(upcard.getSize() - 1) + 2)
                    || (bot.checkStraight(i) == upcard.checkStraight(upcard.getSize() - 1) - 2))) {
                sameSuit++;
            }
        }
        if (sameSuit >= 2) {
            isStraight = true;
        }
        if (sameRank >= 2 || isStraight == true) {
            return true;
        } else {
            return false;
        }
    }

//    private void botSortThreeofKind() {
//        int sameRank = 0;
//        int[] suit = new int[13];
//        //sort three of kind
//        for (int i = 0; i < bot.getSize(); i++) {
//            for (int j = i + 1; j < bot.getSize(); j++) {
//                if (bot.getRank(i) == bot.getRank(j)) {
//                    suit[sameRank] = j;
//                    sameRank++;
//                }
//            }
//            if (sameRank >= 2) {
//                int[] suitValue = new int[13];
//                int[] indexCard = new int[sameRank];
//                suitValue[0] = bot.getSuitValue(i);
//                indexCard[0] = i;
//                for (int count = 1; count < sameRank; count++) {
//                    suitValue[count] = bot.getRankValue(suit[count - 1]);
//                    indexCard[count] = suit[count - 1];
//                }
//                Arrays.sort(suitValue);
//                System.out.println(suitValue);
//                for (int count = 0, count2 = suitValue.length - 1; count < sameRank && count2 >= 0; count++) {
//                    if (bot.getRankValue(indexCard[count]) == suitValue[count2]) {
//                        bot.toFront(indexCard[count]);
//                        count2--;
//                    }
//                }
//            }
//        }
//    }
//
//    private void botSortStraight() {
//        int sameRank = 0;
//        int sameSuit = 0;
//        //sort straight
//        int[] suit = new int[13];
//        for (int i = 0; i < bot.getSize(); i++) {
//            for (int j = i + 1; j < bot.getSize(); j++) {
//                if ((bot.getSuit(i) == bot.getSuit(j))
//                        && ((bot.checkStraight(i) == bot.getSuit(j) + 1)
//                        || (bot.checkStraight(i) == bot.getSuit(j) - 1))) {
//                    suit[sameSuit] = j;
//                    sameSuit++;
//                }
//            }
//            if (sameSuit >= 2) {
//                int[] valueCard = new int[sameSuit];
//                int[] indexCard = new int[sameSuit];
//                valueCard[0] = bot.getRankValue(i);
//                indexCard[0] = i;
//                for (int count = 1; count < sameSuit; count++) {
//                    valueCard[count] = bot.getRankValue(suit[count - 1]);
//                    indexCard[count] = suit[count - 1];
//                }
//                Arrays.sort(valueCard);
//                for (int count = 0, count2 = valueCard.length - 1; count < sameSuit && count2 >= 0; count++) {
//                    if (bot.getRankValue(indexCard[count]) == valueCard[count2]) {
//                        bot.toFront(indexCard[count]);
//                        count2--;
//                    }
//                }
//            }
//        }
//    }
//
//    private void playerSortThreeofKind() {
//        int sameRank = 0;
//        int[] suit = new int[13];
//        //sort three of kind
//        for (int i = 0; i < player.getSize(); i++) {
//            for (int j = i + 1; j < player.getSize(); j++) {
//                if (player.getRank(i) == player.getRank(j)) {
//                    suit[sameRank] = j;
//                    sameRank++;
//                }
//            }
//            if (sameRank >= 2) {
//                int[] suitValue = new int[13];
//                int[] indexCard = new int[sameRank];
//                suitValue[0] = player.getSuitValue(i);
//                indexCard[0] = i;
//                for (int count = 1; count < sameRank; count++) {
//                    suitValue[count] = player.getRankValue(suit[count - 1]);
//                    indexCard[count] = suit[count - 1];
//                }
//                Arrays.sort(suitValue);
//                for (int count = 0, count2 = suitValue.length - 1; count < sameRank && count2 >= 0; count++) {
//                    if (player.getRankValue(indexCard[count]) == suitValue[count2]) {
//                        player.toFront(indexCard[count]);
//                        count2--;
//                    }
//                }
//            }
//        }
//    }
//
//    private void playerSortStraight() {
//        int sameRank = 0;
//        int sameSuit = 0;
//        //sort straight
//        int[] suit = new int[13];
//        for (int i = 0; i < player.getSize(); i++) {
//            for (int j = i + 1; j < player.getSize(); j++) {
//                if ((player.getSuit(i) == player.getSuit(j))
//                        && ((player.checkStraight(i) == player.getSuit(j) + 1)
//                        || (player.checkStraight(i) == player.getSuit(j) - 1))) {
//                    suit[sameSuit] = j;
//                    sameSuit++;
//                }
//            }
//            if (sameSuit >= 2) {
//                int[] valueCard = new int[sameSuit];
//                int[] indexCard = new int[sameSuit];
//                valueCard[0] = player.getRankValue(i);
//                indexCard[0] = i;
//                for (int count = 1; count < sameSuit; count++) {
//                    valueCard[count] = player.getRankValue(suit[count - 1]);
//                    indexCard[count] = suit[count - 1];
//                }
//                Arrays.sort(valueCard);
//                for (int count = 0, count2 = valueCard.length - 1; count < sameSuit && count2 >= 0; count++) {
//                    if (player.getRankValue(indexCard[count]) == valueCard[count2]) {
//                        player.toFront(indexCard[count]);
//                        count2--;
//                    }
//                }
//            }
//        }
//    }

    private void botDropCard() {
        playable.set(false);
        int sameRank = 0;
        int sameSuit = 0;
        boolean isStraight = false;

        for (int i = 0, j = bot.getSize(); i < bot.getSize(); i++, j--) {
            if (bot.getRank(i) == bot.getRank(j)) {
                sameRank++;
            }
        }
//        if (sameRank) {
//
//        }
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
