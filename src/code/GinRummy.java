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
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
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
import javafx.util.Duration;

public class GinRummy extends Application {

    private Deck deck = new Deck();
    private Player player;
    private Bot bot;
    private UpCard upcard;
    private DrawPile drawpile;
    private boolean isSelectedCard = false;
    private int page = 1;

    HBox playerCardsPane = new HBox(10);
    private Glow glow = new Glow();
    private DropShadow dropshadow = new DropShadow(BlurType.ONE_PASS_BOX, Color.BLACK, 10, 10, 3, 3);
    private boolean firstPass = false;
    private boolean botPass = false;
    private int botTurn = -1;
    private int playerTurn = -1;
    private boolean isTake = false;
    private Rectangle backcard = new Rectangle(101, 142);
    private Image imgbackcard = new Image("resources/backcard.png");

    private Parent createGame() {
        //set main pane
        Pane root = new Pane();
        root.setPrefSize(1000, 600);

        Rectangle InGameBG = new Rectangle(984, 562);
        Image imgendInGameBG = new Image("resources/background/InGameBackground.jpg");
        InGameBG.setFill(new ImagePattern(imgendInGameBG));

        //set end game background
        Rectangle BGopenCardEnd = new Rectangle(984, 562);
        Image imgopenCardEnd = new Image("resources/background/bgopencard.jpg");
        BGopenCardEnd.setFill(new ImagePattern(imgopenCardEnd));

        Rectangle endBGLose = new Rectangle(984, 562);
        Image imgendBGLose = new Image("resources/background/youLoseBackground.jpg");
        endBGLose.setFill(new ImagePattern(imgendBGLose));

        Rectangle endBGWin = new Rectangle(984, 562);
        Image imgendBGWin = new Image("resources/background/youWinBackground.jpg");
        endBGWin.setFill(new ImagePattern(imgendBGWin));

        //BUTTON ALL
        Rectangle buttonPass = new Rectangle(97, 40);
        Image imgbuttonPass = new Image("resources/background/PASS_Button.png");
        buttonPass.setFill(new ImagePattern(imgbuttonPass));

        Rectangle buttonNew = new Rectangle(97, 40);
        Image imgbuttonNew = new Image("resources/background/NEW_Button.png");
        buttonNew.setFill(new ImagePattern(imgbuttonNew));

        Rectangle buttonTake = new Rectangle(97, 40);
        Image imgbuttonTake = new Image("resources/background/TAKE_Button.png");
        buttonTake.setFill(new ImagePattern(imgbuttonTake));

        Rectangle buttonEndround = new Rectangle(97, 40);
        Image imgbuttonEndround = new Image("resources/background/END_TURN.png");
        buttonEndround.setFill(new ImagePattern(imgbuttonEndround));

        Rectangle buttonGin = new Rectangle(97, 40);
        Image imgbuttonGin = new Image("resources/background/GIN_button.png");
        buttonGin.setFill(new ImagePattern(imgbuttonGin));

        Rectangle buttonExit = new Rectangle(10, 10, 100, 65);
        Image imgbuttonBackToMenu = new Image("resources/background/exit.png");
        buttonExit.setFill(new ImagePattern(imgbuttonBackToMenu));

        //set next pane
        StackPane rootLayout = new StackPane();
        rootLayout.setPadding(new Insets(5, 5, 5, 5));

        //set final pane        
        HBox botCardsPane = new HBox(10);
        HBox botDeadwoodCardsPane = new HBox(10);
        HBox botStraightCardsPane = new HBox(10);
        HBox botKindCardsPane = new HBox(10);

        HBox playerDeadwoodCardsPane = new HBox(10);
        HBox playerStraightCardsPane = new HBox(10);
        HBox playerKindCardsPane = new HBox(10);

        HBox drawCardsPane = new HBox(50);
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

        Text countDrawpile = new Text("");
        countDrawpile.setFont(Font.font("Tahoma", 34));
        countDrawpile.setFill(Color.WHITE);
        countDrawpile.setStroke(Color.WHITE);
        countDrawpile.setStrokeWidth(1.5);

        countDrawpile.setLayoutX(387);
        countDrawpile.setLayoutY(230);
        backcard.setFill(new ImagePattern(imgbackcard));
        drawCardsPane.setAlignment(Pos.CENTER);
        drawCardsPane.getChildren().addAll(backcard, upCardPilePane);

        //declare arraylist
        player = new Player(playerDeadwoodCardsPane.getChildren(), playerStraightCardsPane.getChildren(), playerKindCardsPane.getChildren());
        bot = new Bot(botDeadwoodCardsPane.getChildren(), botStraightCardsPane.getChildren(), botKindCardsPane.getChildren());
        upcard = new UpCard(upCardPilePane.getChildren());
        drawpile = new DrawPile(drawPilePane.getChildren());
        playerCardsPane.getChildren().addAll(playerKindCardsPane, playerStraightCardsPane, playerDeadwoodCardsPane);
        botCardsPane.getChildren().addAll(botKindCardsPane, botStraightCardsPane, botDeadwoodCardsPane);

        //Bot Score
        Text botScore = new Text("");
        botScore.setFont(Font.font("Tahoma", 30));
        botScore.setFill(Color.WHITE);
        botScore.setStroke(Color.WHITE);
        botScore.setStrokeWidth(1.2);
        botScore.setLayoutX(268);
        botScore.setLayoutY(280);
        botCardsPane.setLayoutX(20);
        botCardsPane.setLayoutY(80);
        Text passText = new Text("PASS");
        passText.setFont(Font.font("Tahoma", 40));
        passText.setFill(Color.YELLOW);
        passText.setX(200);
        passText.setY(200);

        //Player Score
        HBox playerInfo = new HBox(30);
        Text playerScore = new Text("");
        Text playerDeadwood = new Text("");

        playerDeadwood.setFont(Font.font("Tahoma", 24));
        playerDeadwood.setFill(Color.WHITE);
        playerScore.setFont(Font.font("Tahoma", 21));
        playerScore.setFill(Color.WHITE);
        playerScore.setStroke(Color.WHITE);
        playerScore.setStrokeWidth(1.2);

        playerInfo.setAlignment(Pos.CENTER);
        playerInfo.getChildren().addAll(playerDeadwood);

        //All button
        HBox buttonBox = new HBox(5);
        buttonBox.setPadding(new Insets(5, 5, 5, 5));

        // ADD STACKS TO ROOT LAYOUT
        buttonBox.getChildren().addAll(buttonTake, buttonPass);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        drawCardsPane.setLayoutX(367);
        drawCardsPane.setLayoutY(210);

        countDrawpile.setLayoutX(400);
        countDrawpile.setLayoutY(290);

        playerCardsPane.setLayoutX(20);
        playerCardsPane.setLayoutY(430);

        buttonBox.setLayoutX(749);
        buttonBox.setLayoutY(317);

        playerInfo.setLayoutX(550);
        playerInfo.setLayoutY(375);

        root.getChildren().addAll(InGameBG, drawCardsPane, countDrawpile, buttonBox, playerInfo, playerCardsPane, buttonExit, botCardsPane);

        //START GAME
        startNewGame();

        // INIT BUTTONS
        //BUTTON EXIT
        buttonExit.setOnMousePressed(event -> {
            Platform.exit();
        }
        );
        buttonExit.setOnMouseEntered(event -> {
            glow.setLevel(1.3);
            buttonExit.setEffect(glow);
        });

        buttonExit.setOnMouseExited(event -> {
            buttonExit.setEffect(null);
        });

        //BUTTON DISCARD
        buttonEndround.setOnMousePressed(event -> {
            if (isSelectedCard == true) {
                botAction();

                //change button pass to new
                buttonBox.getChildren().remove(buttonEndround);
                buttonBox.getChildren().add(buttonTake);
                buttonBox.getChildren().add(buttonNew);

                //sort card
                player.sortDeadwoodCards();
                bot.sortDeadwoodCards();

                player.Deadwood();
                bot.Deadwood();

                playerDropCard();
                if (drawpile.getSize() <= 2) {
                    botScore.textProperty().bind(new SimpleStringProperty("DRAW!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                }
                //BOT WIN 
                if (bot.Deadwood() <= 10) {
                    player.Deadwood();
                    bot.Deadwood();
                    int layoffscore = layoffScore(convertObListToArList(bot.getKindCards()), convertObListToArList(bot.getStraightCards()), convertObListToArList(player.getDeadwoodCards()));
                    System.out.println("LAYOFF SCORE :: " + layoffscore);
                    if (bot.getDeadwoodCards().size() == 0) { //WIN GIN
                        botScore.textProperty().bind(new SimpleStringProperty("Dealer Win Gin!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                    } else {  //WIN KNOCK
                        int newscoreplayer = player.Deadwood() - layoffscore;
                        System.out.println("new score :: " + newscoreplayer);
                        playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Win Undercut!!  Dealer Deadwood : ").concat(Integer.toString(newscoreplayer)));
                        if (newscoreplayer <= bot.Deadwood()) {
                            botScore.textProperty().bind(new SimpleStringProperty("Player Win Undercut!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                        } else {
                            botScore.textProperty().bind(new SimpleStringProperty("Dealer Win Knock!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                        }
                    }
                    root.getChildren().clear();
                    root.getChildren().addAll(BGopenCardEnd, botCardsPane, playerCardsPane, playerInfo, botScore, buttonExit);
                }

                //PLAYER WIN
                if (player.Deadwood() <= 10) {
                    player.Deadwood();
                    bot.Deadwood();
                    int layoffscore = layoffScore(convertObListToArList(player.getKindCards()), convertObListToArList(player.getStraightCards()), convertObListToArList(bot.getDeadwoodCards()));
                    System.out.println("LAYOFF SCORE :: " + layoffscore);
                    if (player.getDeadwoodCards().size() == 0) { //WIN GIN
                        playerDeadwood.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                        botScore.textProperty().bind(new SimpleStringProperty("Player Win Gin!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                    } else {    //WIN KNOCK
                        int newscorebot = bot.Deadwood() - layoffscore;
                        System.out.println("new score :: " + newscorebot);
                        playerDeadwood.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                        if (newscorebot <= player.Deadwood()) {
                            botScore.textProperty().bind(new SimpleStringProperty("Dealer Win Undercut!!  Dealer Deadwood : ").concat(Integer.toString(newscorebot)));
                        } else {
                            botScore.textProperty().bind(new SimpleStringProperty("Player Win Knock!!  Dealer Deadwood : ").concat(Integer.toString(newscorebot)));
                        }
                    }
                    root.getChildren().clear();
                    root.getChildren().addAll(BGopenCardEnd, botCardsPane, playerCardsPane, playerInfo, botScore, buttonExit);
                }

                //Score 
                playerDeadwood.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                countDrawpile.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(drawpile.getSize())));
                isSelectedCard = false;
            }
        }
        );

        //BUTTON TAKE
        buttonTake.setOnMousePressed(event -> {
            firstPass = true;
            playerActionTake();
            isTake = true;
            root.getChildren().remove(passText);
            player.Deadwood();
            bot.Deadwood();

            //change button take to discard
            buttonBox.getChildren().clear();
            buttonBox.getChildren().addAll(buttonEndround);

            playerDropCard();

            //sort card
            player.sortDeadwoodCards();
            bot.sortDeadwoodCards();

            player.Deadwood();
            bot.Deadwood();

            //BOT WIN BIG GIN
            if (bot.Deadwood() == 0) {
                playerScore.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                botScore.textProperty().bind(new SimpleStringProperty("Dealer Win Gin!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                playerScore.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                botScore.textProperty().bind(new SimpleStringProperty("Dealer Win Knock!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));

                root.getChildren().clear();
                root.getChildren().addAll(BGopenCardEnd, botCardsPane, playerCardsPane, playerInfo, botScore, buttonExit);
            }

            //PLAYER WIN BIG GIN
            if (player.Deadwood() == 0) {
                playerScore.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                botScore.textProperty().bind(new SimpleStringProperty("Player Win Gin!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                playerScore.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                botScore.textProperty().bind(new SimpleStringProperty("Player Win Knock!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));

                root.getChildren().clear();
                root.getChildren().addAll(BGopenCardEnd, botCardsPane, playerCardsPane, playerInfo, botScore, buttonExit);
            }

            //Score 
            playerDeadwood.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
            countDrawpile.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(drawpile.getSize())));

        }
        );

        //BUTTON PASS
        buttonPass.setOnMousePressed(event -> {
            botAction();
            if (botPass) {
                root.getChildren().add(passText);
            }
            firstPass = true;

            //change button pass to new
            buttonBox.getChildren().remove(buttonPass);
            buttonBox.getChildren().add(buttonNew);

            //sort card
            player.sortDeadwoodCards();
            bot.sortDeadwoodCards();

            //Score 
            playerDeadwood.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
            countDrawpile.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(drawpile.getSize())));
        }
        );

        //BUTTON NEW
        buttonNew.setOnMousePressed(event -> {
            playerActionNew();
            root.getChildren().remove(passText);
            isTake = true;

            player.Deadwood();
            bot.Deadwood();

            //change button take to discard
            buttonBox.getChildren().clear();
            buttonBox.getChildren().addAll(buttonEndround);

            playerDropCard();
            //sort card
            player.sortDeadwoodCards();
            bot.sortDeadwoodCards();

            //Score 
            playerDeadwood.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
            countDrawpile.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(drawpile.getSize())));

        }
        );

        player.sortDeadwoodCards();
        bot.sortDeadwoodCards();

        //Score 
        playerDeadwood.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
        countDrawpile.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(drawpile.getSize())));

        return root;
    }

    public void botAction() {

        final Duration SEC = Duration.millis(400);
        TranslateTransition tt = new TranslateTransition(SEC);
        tt.setFromX(0);
        tt.setFromY(0);
        tt.setToX(249);
        tt.setToY(-160);

        ScaleTransition st = new ScaleTransition(SEC);
        st.setFromX(1);
        st.setFromY(1);
        st.setByX(-0.3);
        st.setByY(-0.3);

        ParallelTransition drawPileMove = new ParallelTransition(backcard, tt, st);

        botTurn = bot.botAction(upcard);

        //bot take card from upcard pile
        if (botTurn == 0) {
            bot.takeDeadwoodCard((Card) upcard.drawCard());
            bot.sortDeadwoodCards();
            upcard.keepCard((Card) bot.botDropCard());
        } else if (botTurn == 1) {
            bot.takeKindCard((Card) upcard.drawCard());
            bot.sortDeadwoodCards();
            upcard.keepCard((Card) bot.botDropCard());
        } else if (botTurn == 2) {
            bot.takeStraightCard((Card) upcard.drawCard());
            bot.sortDeadwoodCards();
            upcard.keepCard((Card) bot.botDropCard());
        } else {//bot take card from draw pile
            if (firstPass) {
                drawPileMove.play();
                bot.takeDeadwoodCard((Card) drawpile.drawCard());
                bot.sortDeadwoodCards();
                upcard.keepCard((Card) bot.botDropCard());
            } else {
                botPass = true;
            }
        }
        bot.Deadwood();
    }

    public void playerActionTake() {
        playerTurn = player.checkUpcardTake(upcard);

        //bot take card from upcard pile
        if (playerTurn == 1) {
            player.takeKindCard((Card) upcard.drawCard());
            player.sortDeadwoodCards();
        } else if (playerTurn == 2) {
            player.takeStraightCard((Card) upcard.drawCard());
            player.sortDeadwoodCards();
        } else {//bot take card from draw pile
            player.takeDeadwoodCard((Card) upcard.drawCard());
            player.sortDeadwoodCards();
        }

        player.Deadwood();

    }

    public void playerActionNew() {
        playerTurn = player.checkUpcardTake(drawpile);

        //bot take card from upcard pile
        if (playerTurn == 1) {
            player.takeKindCard((Card) drawpile.drawCard());
            player.sortDeadwoodCards();
        } else if (playerTurn == 2) {
            player.takeStraightCard((Card) drawpile.drawCard());
            player.sortDeadwoodCards();
        } else {//bot take card from draw pile
            player.takeDeadwoodCard((Card) drawpile.drawCard());
            player.sortDeadwoodCards();
        }

        player.Deadwood();

    }

    public void playerDropCard() throws IndexOutOfBoundsException {
        //choose deadwood card to drop
        for (int i = 0; i < player.getDeadwoodSize(); i++) {
            final int index = i;
            player.getDeadwoodCardNode(i).setOnMouseClicked(event -> {
                final int selectedIndex = index;
                event.consume();
                if (isTake == true) {
                    upcard.keepCard((Card) player.getDeadwoodCardNode(index));
                    isSelectedCard = true;
                    isTake = false;
                }
            });
            player.getDeadwoodCardNode(i).setOnMouseEntered(event -> {
                final int selectedIndex = index;
                event.consume();
                player.getDeadwoodCardNode(index).setEffect(dropshadow);
            });
            player.getDeadwoodCardNode(i).setOnMouseExited(event -> {
                final int selectedIndex = index;
                event.consume();
                player.getDeadwoodCardNode(index).setEffect(null);

            });
        }
    }

    private void startNewGame() {
        deck.refill();

        bot.reset();
        player.reset();

        //player and bot draw card
        for (int i = 0; i < 10; i++) {
            player.takeDeadwoodCard(deck.drawCard());
            bot.takeDeadwoodCard(deck.drawCard());
        }

        //deck keep card
        for (int i = 0; i < 31; i++) {
            drawpile.keepCard(deck.drawCard());
        }

        //open top card
        upcard.keepCard(deck.drawCard());
    }

    public static int layoffScore(ArrayList<String> listOfKind_string, ArrayList<String> listOfStraight_string,
            ArrayList<String> listOfDeadwood_string) {

        try {
            if (listOfKind_string.isEmpty() || listOfKind_string == null) {
                throw new IllegalArgumentException("LIST OF KIND IS EMPTY OR BEING NULL.");
            }
            if (listOfStraight_string.isEmpty() || listOfStraight_string == null) {
                throw new IllegalArgumentException("LIST OF STRAIGHT IS EMPTY OR BEING NULL.");
            }
            if (listOfDeadwood_string.isEmpty() || listOfDeadwood_string == null) {
                throw new IllegalArgumentException("LIST OF DEADWOOD IS EMPTY OR BEING NULL.");
            }
        } catch (IllegalArgumentException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
            return -1;
        }

        /* Convert ArrayList<String> type to ArrayList<MildCard> */
        ArrayList<LayoffCard> kindList = new ArrayList<LayoffCard>();
        ArrayList<LayoffCard> straightList = new ArrayList<LayoffCard>();
        ArrayList<LayoffCard> deadwoodList = new ArrayList<LayoffCard>();

        /* "1s" -> ACE, SLADE */

 /* Conver list of kind string to ArrayList<MildCard> */
        for (int i = 0; i < listOfKind_string.size(); i++) {

            int value;
            String suit = "";
            if (Character.toString(listOfKind_string.get(i).charAt(0)).equals("m")) {
                value = 10;
                suit = Character.toString(listOfKind_string.get(i).charAt(1));
            } else if (Character.toString(listOfKind_string.get(i).charAt(0)).equals("n")) {
                value = 11;
                suit = Character.toString(listOfKind_string.get(i).charAt(1));
            } else if (Character.toString(listOfKind_string.get(i).charAt(0)).equals("o")) {
                value = 12;
                suit = Character.toString(listOfKind_string.get(i).charAt(1));
            } else if (Character.toString(listOfKind_string.get(i).charAt(0)).equals("p")) {
                value = 13;
                suit = Character.toString(listOfKind_string.get(i).charAt(1));
            } else {
                value = Integer.parseInt(Character.toString(listOfKind_string.get(i).charAt(0)));
                suit = Character.toString(listOfKind_string.get(i).charAt(1));
            }
            kindList.add(new LayoffCard(value, suit));
        }
        Collections.sort(kindList);

        /* List of Straight */
        for (int i = 0; i < listOfStraight_string.size(); i++) {
            int value;
            String suit = "";
            if (Character.toString(listOfStraight_string.get(i).charAt(0)).equals("m")) {
                value = 10;
                suit = Character.toString(listOfStraight_string.get(i).charAt(1));
            } else if (Character.toString(listOfStraight_string.get(i).charAt(0)).equals("n")) {
                value = 11;
                suit = Character.toString(listOfStraight_string.get(i).charAt(1));
            } else if (Character.toString(listOfStraight_string.get(i).charAt(0)).equals("o")) {
                value = 12;
                suit = Character.toString(listOfStraight_string.get(i).charAt(1));
            } else if (Character.toString(listOfStraight_string.get(i).charAt(0)).equals("p")) {
                value = 13;
                suit = Character.toString(listOfStraight_string.get(i).charAt(1));
            } else {
                value = Integer.parseInt(Character.toString(listOfStraight_string.get(i).charAt(0)));
                suit = Character.toString(listOfStraight_string.get(i).charAt(1));
            }
            straightList.add(new LayoffCard(value, suit));
        }
        Collections.sort(straightList);

        /* List of Deadwood */
        for (int i = 0; i < listOfDeadwood_string.size(); i++) {
            int value;
            String suit = "";
            if (Character.toString(listOfDeadwood_string.get(i).charAt(0)).equals("m")) {
                value = 10;
                suit = Character.toString(listOfDeadwood_string.get(i).charAt(1));
            } else if (Character.toString(listOfDeadwood_string.get(i).charAt(0)).equals("n")) {
                value = 11;
                suit = Character.toString(listOfDeadwood_string.get(i).charAt(1));
            } else if (Character.toString(listOfDeadwood_string.get(i).charAt(0)).equals("o")) {
                value = 12;
                suit = Character.toString(listOfDeadwood_string.get(i).charAt(1));
            } else if (Character.toString(listOfDeadwood_string.get(i).charAt(0)).equals("p")) {
                value = 13;
                suit = Character.toString(listOfDeadwood_string.get(i).charAt(1));
            } else {
                value = Integer.parseInt(Character.toString(listOfDeadwood_string.get(i).charAt(0)));
                suit = Character.toString(listOfDeadwood_string.get(i).charAt(1));
            }
            deadwoodList.add(new LayoffCard(value, suit));
        }
        Collections.sort(deadwoodList);
        /* FINISHED CONVERT FROM ARRAYLIST OF STRING TO ARRAYLIST OF MILDCARD */

 /*
         * Convert from arrayList of MILDCARD to ArrayList of integer(rank value) to be
         * checked in getKindIndex(ArrayList<Integer>)
         */
        ArrayList<Integer> kindList_value = new ArrayList<Integer>();
        for (int i = 0; i < kindList.size(); i++) {
            kindList_value.add(kindList.get(i).getValue());
        }

        /* Check if in kindlist hadn't kind occur */
        try {
            if (getKindIndex(kindList_value).size() == 0) {
                /* NO KIND OCCUR */
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        }

        /* Check if in straightlist had straight occur */
 /* has to be matched in their own suit */
        /**
         * CHECK SUIT FIRST!
         */
        /**
         * Sparate in their own suit CLUBS HEART DIAMOND SPLADE
         */
        ArrayList<LayoffCard> straight_clubs = new ArrayList<LayoffCard>();
        ArrayList<LayoffCard> straight_heart = new ArrayList<LayoffCard>();
        ArrayList<LayoffCard> straight_diamond = new ArrayList<LayoffCard>();
        ArrayList<LayoffCard> straight_spade = new ArrayList<LayoffCard>();

        // System.out.println(straightList);
        // System.out.println("STRAIGHTLIST::SIZE()::" + straightList.size());
        for (int i = 0; i < straightList.size(); i++) {
            if (straightList.get(i).getSuit().equals("c")) {
                straight_clubs.add(straightList.get(i));
            } else if (straightList.get(i).getSuit().equals("h")) {
                straight_heart.add(straightList.get(i));
                // System.out.println("ADDED TO HEART");
                // System.out.println(straightList.get(i));
            } else if (straightList.get(i).getSuit().equals("d")) {
                straight_diamond.add(straightList.get(i));
            } else if (straightList.get(i).getSuit().equals("s")) {
                straight_spade.add(straightList.get(i));
            }
            // System.out.println("ITERATE");
        }
        /* FINISHED SEPARATE CARDS IN THEIR OWN SUIT */
        // System.out.println("xSTRAIGHT_CLUBS::SIZE()::" + straight_clubs.size());
        // System.out.println("xSTRAIGHT_HEART::SIZE()::" + straight_heart.size());
        // System.out.println("xSTRAIGHT_DIAMODN::SIZE()::" + straight_diamond.size());
        // System.out.println("xSTRAIGHT_SPADE::SIZE()::" + straight_spade.size());

        /**
         * Convert each suit list to be ArrayList<Integer> in order to be
         * checked in getStraightIndex(ArrayList<Integer>)
         */
        ArrayList<Integer> straight_clubs_value = new ArrayList<Integer>();
        ArrayList<Integer> straight_heart_value = new ArrayList<Integer>();
        ArrayList<Integer> straight_diamond_value = new ArrayList<Integer>();
        ArrayList<Integer> straight_spade_value = new ArrayList<Integer>();

        /*
         * Converting ArrayList<MildClub> straight_clubs to ArrayList<Integer>
         * straight_clubs_value
         */
        for (int i = 0; i < straight_clubs.size(); i++) {
            straight_clubs_value.add(Integer.valueOf(straight_clubs.get(i).getValue()));
        }
        /*
         * Converting ArrayList<MildClub> straight_heart to ArrayList<Integer>
         * straight_heart_value
         */
        for (int i = 0; i < straight_heart.size(); i++) {
            straight_heart_value.add(Integer.valueOf(straight_heart.get(i).getValue()));
        }
        /*
         * Converting ArrayList<MildClub> straight_diamond to ArrayList<Integer>
         * straight_diamond_value
         */
        for (int i = 0; i < straight_diamond.size(); i++) {
            straight_diamond_value.add(Integer.valueOf(straight_diamond.get(i).getValue()));
        }
        /*
         * Converting ArrayList<MildClub> straight_spade to ArrayList<Integer>
         * straight_spade_value
         */
        for (int i = 0; i < straight_spade.size(); i++) {
            straight_spade_value.add(Integer.valueOf(straight_spade.get(i).getValue()));
        }

        // System.out.println(straight_clubs_value);
        // System.out.println(straight_heart_value);
        // System.out.println(straight_diamond_value);
        // System.out.println(straight_spade_value);

        /* Check if Straight hadn't been occured */
        ArrayList<ArrayList<Integer>> club_straight_index = getStraightIndex(straight_clubs_value);
        ArrayList<ArrayList<Integer>> heart_straight_index = getStraightIndex(straight_heart_value);
        ArrayList<ArrayList<Integer>> diamond_straight_index = getStraightIndex(straight_diamond_value);
        ArrayList<ArrayList<Integer>> spade_straight_index = getStraightIndex(straight_spade_value);
        try {
            // System.out.println(club_straight_index);
            // System.out.println(heart_straight_index);
            // System.out.println(diamond_straight_index);
            // System.out.println(spade_straight_index);
            if ((club_straight_index == null) && (heart_straight_index == null) && (diamond_straight_index == null)
                    && (spade_straight_index == null)) {

                throw new IllegalArgumentException("NO STRAIGHT OCCUR IN ANY SUIT.");

            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return -1;
        }

        /* Layoff check */
        int result_score = 0;

        /* check if can be banked in kind. */
        // System.out.println("W(TSPEHRE)" + kindList.get(getKindIndex(kindList_value).get(0).get(0)).getValue());
        for (int i = 0; i < deadwoodList.size(); i++) {

            // System.out.println("GETSCORE()::" + deadwoodList.get(i).getScore());
            if (kindList.get(getKindIndex(kindList_value).get(0).get(0)).getValue() == deadwoodList.get(i).getValue()) {
                /* if matched, deadwoodlist.remove(i) */
 /* result_score = result_score + deadwoodLlist.getScore() */
                // System.out.println("GETSCORE()::" + deadwoodList.get(i).getScore());
                result_score += deadwoodList.remove(i).getScore();

            }

        }

        // System.out.println("LAYOFF_SCORE_IS::" + result_score);

        /* check if can be banked in straight */
        // Clubs straight check , check if can be banked in straight club.
        if (club_straight_index != null) {
            /* has straight occured in club */

            Collections.sort(straight_clubs);

            for (int j = 0; j < club_straight_index.size(); j++) {
                boolean isCombo = true;
                int q = 0;
                while (isCombo && q < deadwoodList.size() && q < deadwoodList.size()) /* check if it can be place in the bottom */ {
                    if ((straight_clubs.get(club_straight_index.get(j).get(0))
                            .getValue() == deadwoodList.get(q).getValue() + 1)
                            && deadwoodList.get(q).getSuit().equals("c")) {
                        /* can be placed in the bottom of the straighted card */
                        straight_clubs.add(deadwoodList.get(q)); // Add deadwood card to bank of straight
                        straight_clubs_value.add(deadwoodList.get(q).getValue()); // Add deadwood rank to bank value
                        Collections.sort(straight_clubs);
                        Collections.sort(straight_clubs_value);
                        club_straight_index = getStraightIndex(straight_clubs_value); // recall the straight index.
                        result_score += deadwoodList.remove(q).getScore(); // add score and remove.
                        isCombo = true;
                        q = 0;
                    } /* check if it can be placed in the upper */ else if (straight_clubs.get(club_straight_index.get(j).get(club_straight_index.get(j).size() - 1))
                            .getValue() == deadwoodList.get(q).getValue() - 1) {
                        /* can be placed at the upper of the straighted card */
                        straight_clubs.add(deadwoodList.get(q));
                        straight_clubs_value.add(deadwoodList.get(q).getValue());
                        Collections.sort(straight_clubs);
                        Collections.sort(straight_clubs_value);
                        club_straight_index = getStraightIndex(straight_clubs_value);
                        result_score += deadwoodList.remove(q).getScore();
                        isCombo = true;
                        q = 0;
                    } else {
                        /* No more straight combo can be placed */
                        q++;

                    }
                }

            }

        }

        // Heart straight check , check if can be banked in straight heart.
        if (heart_straight_index != null) {
            /* has straight occured in heart */
            // System.out.println("HEART_STRAIGHT_INDEX is not null");
            // System.out.println("HEART_STRAIGHT_INDEX::SIZE::" + heart_straight_index.size());
            Collections.sort(straight_heart);
            // System.out.println(straight_heart);
            for (int j = 0; j < heart_straight_index.size(); j++) {
                boolean isCombo = true;
                int q = 0;
                while (isCombo && q < straight_heart.size() && q < deadwoodList.size()) {

                    /* check if it can be place in the bottom */
                    if ((straight_heart.get(heart_straight_index.get(j).get(0))
                            .getValue() == deadwoodList.get(q).getValue() + 1)
                            && deadwoodList.get(q).getSuit().equals("h")) {
                        /* can be placed in the bottom of the straighted card */
                        // System.out.println("CAN BE PLACE IN THE BOTTOM OF THE STRAIGHT CARD" + deadwoodList.get(q));
                        straight_heart.add(deadwoodList.get(q)); // Add deadwood card to bank of straight
                        straight_heart_value.add(deadwoodList.get(q).getValue()); // Add deadwood rank to bank value
                        Collections.sort(straight_heart);
                        Collections.sort(straight_heart_value);
                        heart_straight_index = getStraightIndex(straight_heart_value); // recall the straight index.
                        result_score += deadwoodList.remove(q).getScore(); // add score and remove.
                        isCombo = true;
                        q = 0;
                    } /* check if it can be placed in the upper */ else if (straight_heart.get(heart_straight_index.get(j).get(heart_straight_index.get(j).size() - 1))
                            .getValue() == deadwoodList.get(q).getValue() - 1) {
                        /* can be placed at the upper of the straighted card */
                        // System.out.println("CAN BE PLACE AT THE UPPER OF THE STRAIGHT CARD" + deadwoodList.get(q));
                        // System.out.println("LEFT IN DEADWOOD::"+deadwoodList);
                        straight_heart.add(deadwoodList.get(q));
                        straight_heart_value.add(deadwoodList.get(q).getValue());
                        Collections.sort(straight_heart);
                        Collections.sort(straight_heart_value);
                        heart_straight_index = getStraightIndex(straight_heart_value);
                        result_score += deadwoodList.remove(q).getScore();
                        isCombo = true;
                        q = 0;
                    } else {
                        /* No more straight combo can be placed */
                        q++;

                    }
                }

            }

        }

        // Diamond straight check , check if can be banked in straight diamond.
        if (diamond_straight_index != null) {
            /* has straight occured in diamond */

            Collections.sort(straight_diamond);

            for (int j = 0; j < diamond_straight_index.size(); j++) {
                boolean isCombo = true;
                int q = 0;
                while (isCombo && q < straight_diamond.size() && q < deadwoodList.size()) /* check if it can be place in the bottom */ {
                    if ((straight_diamond.get(diamond_straight_index.get(j).get(0))
                            .getValue() == deadwoodList.get(q).getValue() + 1)
                            && deadwoodList.get(q).getSuit().equals("d")) {
                        /* can be placed in the bottom of the straighted card */
                        straight_diamond.add(deadwoodList.get(q)); // Add deadwood card to bank of straight
                        straight_diamond_value.add(deadwoodList.get(q).getValue()); // Add deadwood rank to bank value
                        Collections.sort(straight_diamond);
                        Collections.sort(straight_diamond_value);
                        diamond_straight_index = getStraightIndex(straight_diamond_value); // recall the straight index.
                        result_score += deadwoodList.remove(q).getScore(); // add score and remove.
                        isCombo = true;
                        q = 0;
                    } /* check if it can be placed in the upper */ else if (straight_diamond
                            .get(diamond_straight_index.get(j).get(diamond_straight_index.get(j).size() - 1))
                            .getValue() == deadwoodList.get(q).getValue() - 1) {
                        /* can be placed at the upper of the straighted card */
                        straight_diamond.add(deadwoodList.get(q));
                        straight_diamond_value.add(deadwoodList.get(q).getValue());
                        Collections.sort(straight_diamond);
                        Collections.sort(straight_diamond_value);
                        diamond_straight_index = getStraightIndex(straight_diamond_value);
                        result_score += deadwoodList.remove(q).getScore();
                        isCombo = true;
                        q = 0;
                    } else {
                        /* No more straight combo can be placed */
                        q++;

                    }
                }

            }

        }

        // spade straight check , check if can be banked in straight spade.
        if (spade_straight_index != null) {
            /* has straight occured in spade */

            Collections.sort(straight_spade);

            for (int j = 0; j < spade_straight_index.size(); j++) {
                boolean isCombo = true;
                int q = 0;
                while (isCombo && q < straight_spade.size() && q < deadwoodList.size()) /* check if it can be place in the bottom */ {
                    if ((straight_spade.get(spade_straight_index.get(j).get(0))
                            .getValue() == deadwoodList.get(q).getValue() + 1)
                            && deadwoodList.get(q).getSuit().equals("s")) {
                        /* can be placed in the bottom of the straighted card */
                        straight_spade.add(deadwoodList.get(q)); // Add deadwood card to bank of straight
                        straight_spade_value.add(deadwoodList.get(q).getValue()); // Add deadwood rank to bank value
                        Collections.sort(straight_spade);
                        Collections.sort(straight_spade_value);
                        spade_straight_index = getStraightIndex(straight_spade_value); // recall the straight index.
                        result_score += deadwoodList.remove(q).getScore(); // add score and remove.
                        isCombo = true;
                        q = 0;
                    } /* check if it can be placed in the upper */ else if (straight_spade.get(spade_straight_index.get(j).get(spade_straight_index.get(j).size() - 1))
                            .getValue() == deadwoodList.get(q).getValue() - 1) {
                        /* can be placed at the upper of the straighted card */
                        straight_spade.add(deadwoodList.get(q));
                        straight_spade_value.add(deadwoodList.get(q).getValue());
                        Collections.sort(straight_spade);
                        Collections.sort(straight_spade_value);
                        spade_straight_index = getStraightIndex(straight_spade_value);
                        result_score += deadwoodList.remove(q).getScore();
                        isCombo = true;
                        q = 0;
                    } else {
                        /* No more straight combo can be placed */
                        q++;

                    }
                }

            }

        }
        System.out.println("LAYOFF_SCORE::" + result_score);
        return result_score;
    }

    public static ArrayList<ArrayList<Integer>> getKindIndex(ArrayList<Integer> arr) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        int indexOfSubArray = 0;
        int kindCount = 0;

        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i) == arr.get(i + 1)) {
                kindCount++;
                if (kindCount == 2) {
                    result.add(new ArrayList<Integer>());
                    result.get(indexOfSubArray).add(i - 1);
                    result.get(indexOfSubArray).add(i);
                    result.get(indexOfSubArray).add(i + 1);
                } else if (kindCount > 2) {
                    result.get(indexOfSubArray).add(i + 1);
                }
            } else {
                if (kindCount >= 2) {
                    indexOfSubArray++;
                }
                kindCount = 0;
            }
        }
        return (result.isEmpty()) ? null : result;

    }

    public static ArrayList<ArrayList<Integer>> getStraightIndex(ArrayList<Integer> arr) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        int indexOfSubArray = 0;
        boolean continuity = false;
        int straightCount = 0;

        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i + 1) - arr.get(i) == 1 && i != arr.size() - 2) {
                /* If the next number is more than the current number for 1 (Sequencely) */
                continuity = true;
                straightCount++;
            } else {
                /* No more continue */
                if (continuity == true && arr.size() - 2 == i) {
                    /* case of the last 2 index of the array */
                    if (arr.get(arr.size() - 1) - arr.get(arr.size() - 2) == 1) {
                        /* They are straight */
                        straightCount++;
                        i++;
                    }
                }
                if (continuity == true) {
                    if (straightCount >= 2) {
                        /* They are straight */
                        result.add(new ArrayList<Integer>());
                        for (int k = 0; k < straightCount + 1; k++) {
                            /* Add index number from here until previous ex. 4 5 6 -> adding : 6 5 4 */
                            result.get(indexOfSubArray).add(i - k);
                        }
                        Collections.sort(result.get(indexOfSubArray));
                        /* Sort back to 4 5 6 */
                        indexOfSubArray++;
                        continuity = false;
                        straightCount = 0;
                    } else {
                        straightCount = 0;
                        /* Not continue and straight count is not more than 2 */
                    }
                    continuity = false;
                }

            }
        }

        return (result.isEmpty()) ? null : result;
    }

    public static ArrayList<String> convertObListToArList(ObservableList<Node> cards) {
        ArrayList<String> cardsAll = new ArrayList<String>();
        for (int i = 0; i < cards.size(); i++) {
            cardsAll.add(cards.get(i).toString());
        }
        return cardsAll;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //ADD ICON
        primaryStage.getIcons().add(new Image("resources/background/icon.png"));

        //MENU
        //set pane for menu
        Pane root = new Pane();
        root.setPrefSize(1000, 600);
        Pane rootLayout = new Pane();
        rootLayout.setPrefSize(1000, 600);

        root.setPrefSize(1000, 600);
        Rectangle BGabout1 = new Rectangle(984, 562);
        Image imgBgabout1 = new Image("resources/background/About-1.jpg");
        BGabout1.setFill(new ImagePattern(imgBgabout1));
        Rectangle BGabout2 = new Rectangle(984, 562);
        Image imgBgabout2 = new Image("resources/background/About-2.jpg");
        BGabout2.setFill(new ImagePattern(imgBgabout2));
        Rectangle BGabout3 = new Rectangle(984, 562);
        Image imgBgabout3 = new Image("resources/background/About-3.jpg");
        BGabout3.setFill(new ImagePattern(imgBgabout3));
        Rectangle BGabout4 = new Rectangle(984, 562);
        Image imgBgabout4 = new Image("resources/background/About-4.jpg");
        BGabout4.setFill(new ImagePattern(imgBgabout4));

        Rectangle backMenu = new Rectangle(30, 480, 237, 70);
        Image imgBgbackMenu = new Image("resources/background/backtomenu.png");
        backMenu.setFill(new ImagePattern(imgBgbackMenu));
        Rectangle back = new Rectangle(50, 480, 135, 70);
        Image imgBgback = new Image("resources/background/back.png");
        back.setFill(new ImagePattern(imgBgback));
        Rectangle next = new Rectangle(840, 480, 135, 70);
        Image imgBgnext = new Image("resources/background/next.png");
        next.setFill(new ImagePattern(imgBgnext));

        //set black background
        Region background = new Region();
        background.setPrefSize(1000, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        //set menu background
        Rectangle menuBg = new Rectangle(1000, 600);
        Image imgBg = new Image("resources/background/bg2.jpg");
        menuBg.setFill(new ImagePattern(imgBg));

        //set text menu background
        Rectangle textGin = new Rectangle(950, 560);
        Image imgtextGin = new Image("resources/background/ginrummytext.png");
        textGin.setFill(new ImagePattern(imgtextGin));

        Rectangle decor = new Rectangle(55, 50);
        Image imgdecor = new Image("resources/background/decor.gif");
        decor.setFill(new ImagePattern(imgdecor));

        //set effect animation to menu
        ScaleTransition stFortextGin = new ScaleTransition(Duration.millis(800), textGin);
        stFortextGin.setByX(0.05f);
        stFortextGin.setByY(0.05f);
        stFortextGin.setCycleCount(Timeline.INDEFINITE);
        stFortextGin.setAutoReverse(true);
        stFortextGin.play();

        final Duration SEC_2 = Duration.millis(7000);

        TranslateTransition tt = new TranslateTransition(SEC_2);
        tt.setFromX(-100);
        tt.setToX(1200);
        tt.setFromY(10);
        tt.setToY(300);

        ScaleTransition st = new ScaleTransition(SEC_2);
        st.setByX(3.5);
        st.setByY(3.5);

        ParallelTransition pt = new ParallelTransition(decor, tt, st);
        pt.play();

        //set button on menu
        Rectangle btnStart = new Rectangle(395, 440, 190, 120);
        Image imgBtnStart = new Image("resources/background/play.png");
        btnStart.setFill(new ImagePattern(imgBtnStart));

        Rectangle btnExit = new Rectangle(810, 460, 160, 95);
        Image imgBtnExit = new Image("resources/background/exit.png");
        btnExit.setFill(new ImagePattern(imgBtnExit));

        Rectangle btnAbout = new Rectangle(40, 460, 173, 95);
        Image imgbtnAbout = new Image("resources/background/about.png");
        btnAbout.setFill(new ImagePattern(imgbtnAbout));

        //add all to layout
        rootLayout.getChildren().addAll(menuBg, decor, textGin, btnStart, btnExit, btnAbout);
        root.getChildren().addAll(background, rootLayout);

        //button action
        //start game
        btnStart.setOnMousePressed(event -> {
            primaryStage.setScene(new Scene(createGame()));
        });

        btnExit.setOnMousePressed(event -> {
            Platform.exit();
        });

        btnAbout.setOnMousePressed(event -> {
            root.getChildren().clear();
            root.getChildren().addAll(BGabout1, backMenu, next);
        });

        back.setOnMousePressed(event -> {
            page--;
            if (page <= 1) {
                page = 1;
                root.getChildren().clear();
                root.getChildren().addAll(BGabout1, backMenu, next);
            } else if (page == 2) {
                root.getChildren().clear();
                root.getChildren().addAll(BGabout2, back, next);
            } else if (page == 3) {
                root.getChildren().clear();
                root.getChildren().addAll(BGabout3, back, next);
            } else if (page >= 4) {
                page = 4;
                root.getChildren().clear();
                root.getChildren().addAll(BGabout4, back);
            }
        });

        backMenu.setOnMousePressed(event -> {
            root.getChildren().clear();
            root.getChildren().addAll(background, rootLayout);
        });

        next.setOnMousePressed(event -> {
            page++;
            if (page <= 1) {
                page = 1;
                root.getChildren().clear();
                root.getChildren().addAll(BGabout1, backMenu, next);
            } else if (page == 2) {
                root.getChildren().clear();
                root.getChildren().addAll(BGabout2, back, next);
            } else if (page == 3) {
                root.getChildren().clear();
                root.getChildren().addAll(BGabout3, back, next);
            } else if (page >= 4) {
                page = 4;
                root.getChildren().clear();
                root.getChildren().addAll(BGabout4, back);
            }
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

        btnAbout.setOnMouseEntered(event -> {
            glow.setLevel(1.3);
            btnAbout.setEffect(glow);
        });

        btnAbout.setOnMouseExited(event -> {
            btnAbout.setEffect(null);
        });

        back.setOnMouseEntered(event -> {
            glow.setLevel(1.3);
            back.setEffect(glow);
        });

        back.setOnMouseExited(event -> {
            back.setEffect(null);
        });

        backMenu.setOnMouseEntered(event -> {
            glow.setLevel(1.3);
            backMenu.setEffect(glow);
        });

        backMenu.setOnMouseExited(event -> {
            backMenu.setEffect(null);
        });

        next.setOnMouseEntered(event -> {
            glow.setLevel(1.3);
            next.setEffect(glow);
        });

        next.setOnMouseExited(event -> {
            next.setEffect(null);
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
