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

    HBox playerCardsPane = new HBox(10);
    private Glow glow = new Glow();
    private DropShadow dropshadow = new DropShadow(BlurType.ONE_PASS_BOX, Color.BLACK, 10, 10, 3, 3);
    private boolean firstPass = false;
    private boolean botPass = false;
    private int botTurn = -1;
    private boolean isTake = false;

    private Parent createGame() {
        //set main pane
        Pane root = new Pane();
        root.setPrefSize(1000, 600);

        Region background = new Region();
        background.setPrefSize(1000, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        //set end game background
        Rectangle endBG = new Rectangle(1000, 600);
        Image imgendBG = new Image("resources/background/endgame.png");
        endBG.setFill(new ImagePattern(imgendBG));

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
        Text playerWin = new Text("You Win!");
        Text botWin = new Text("You Lose!");
        playerWin.setFont(Font.font("Tahoma", 25));
        playerWin.setFill(Color.WHITE);
        botWin.setFont(Font.font("Tahoma", 25));
        botWin.setFill(Color.WHITE);
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

        // INIT BUTTONS
        //BUTTON DISCARD
        btnDiscard.setOnAction(event -> {
            botAction();

            //change button pass to new
            buttonBox.getChildren().remove(btnDiscard);
            buttonBox.getChildren().add(btnTake);
            buttonBox.getChildren().add(btnNew);

            //sort card
            player.sortDeadwoodCards();
            bot.sortDeadwoodCards();

            //Score 
            playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
            playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(player.Deadwood())));
            botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(bot.Deadwood())));
        });

        //BUTTON TAKE
        btnTake.setOnAction(event -> {
            player.takeDeadwoodCard((Card) upcard.drawCard());
            root.getChildren().remove(passText);
//            isTake = true;

            //change button take to discard
            buttonBox.getChildren().remove(btnPass);
            buttonBox.getChildren().remove(btnTake);
            buttonBox.getChildren().remove(btnNew);
            buttonBox.getChildren().add(btnDiscard);
            if (player.Deadwood() <= 10) {
                buttonBox.getChildren().add(btnKnock);
            }

            //sort card
            player.sortDeadwoodCards();
            bot.sortDeadwoodCards();

            //Score 
            playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
            playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(player.Deadwood())));
            botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(bot.Deadwood())));

        });

        //BUTTON PASS
        btnPass.setOnAction(event -> {
            botAction();
            if (botPass) {
                root.getChildren().add(passText);
            }
            firstPass = true;

            //change button pass to new
            buttonBox.getChildren().remove(btnPass);
            buttonBox.getChildren().add(btnNew);

            //sort card
            player.sortDeadwoodCards();
            bot.sortDeadwoodCards();

            //Score 
            playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
            playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(player.Deadwood())));
            botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(bot.Deadwood())));
        });

        //BUTTON NEW
        btnNew.setOnAction(event -> {
            player.takeDeadwoodCard((Card) drawpile.drawCard());
            root.getChildren().remove(passText);

            //change button take to discard
            buttonBox.getChildren().remove(btnTake);
            buttonBox.getChildren().remove(btnNew);
            buttonBox.getChildren().add(btnDiscard);
            if (player.Deadwood() <= 10) {
                buttonBox.getChildren().add(btnKnock);
            }

            //sort card
            player.sortDeadwoodCards();
            bot.sortDeadwoodCards();

            playerDropCard();
            //Score 
            playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
            playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(player.Deadwood())));
            botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(bot.Deadwood())));

        });

        //BUTTON KNOCK
        btnKnock.setOnAction(event -> {
            root.getChildren().add(endBG);
            root.getChildren().add(playerWin);
        });

        startNewGame();
        player.sortDeadwoodCards();
        bot.sortDeadwoodCards();

        playerDropCard();
        
        //Score 
        playerScore.textProperty().bind(new SimpleStringProperty("Player Score : ").concat(Integer.toString(player.Score())));
        playerDeadwood.textProperty().bind(new SimpleStringProperty("Player Deadwood : ").concat(Integer.toString(player.Deadwood())));
        botScore.textProperty().bind(new SimpleStringProperty("Dealer Score : ").concat(Integer.toString(bot.Deadwood())));

        if (bot.Deadwood() <= 10) {
            root.getChildren().add(endBG);
            root.getChildren().add(botWin);
        }

        return root;
    }

    public void botAction() {
        botTurn = bot.botAction(upcard);
        System.out.println("BOT ACTION :: BOT TURN" + botTurn);
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
                bot.takeDeadwoodCard((Card) drawpile.drawCard());
                bot.sortDeadwoodCards();
                upcard.keepCard((Card) bot.botDropCard());
            } else {
                botPass = true;
            }
        }
    }

    public void playerDropCard() {
        //choose deadwood card to drop
        player.sortDeadwoodCards();
        for (int i = 0; i < player.getDeadwoodSize(); i++) {
            final int index = i;
            player.getDeadwoodCardNode(i).setOnMouseClicked(event -> {
                final int selectedIndex = index;
                event.consume();
                upcard.keepCard((Card) player.getDeadwoodCardNode(index));
            });
            player.getDeadwoodCardNode(i).setOnMouseEntered(event -> {
                final int selectedIndex = index;
                player.getDeadwoodCardNode(index).setEffect(dropshadow);
            });
            player.getDeadwoodCardNode(i).setOnMouseExited(event -> {
                final int selectedIndex = index;
                player.getDeadwoodCardNode(index).setEffect(null);
            });
        }
        //BUGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGGG 
//        //choose straight card to drop
//        for (int i = 0; i < player.getStraightCards().size(); i++) {
//            final int index = i;
//            player.getStraightCardNode(i).setOnMouseClicked(event -> {
//                final int selectedIndex = index;
//                event.consume();
//                upcard.keepCard((Card) player.getStraightCardNode(index));
//                botAction();
//            });
//            player.getStraightCardNode(i).setOnMouseEntered(event -> {
//                final int selectedIndex = index;
//                player.getStraightCardNode(index).setEffect(dropshadow);
//            });
//            player.getStraightCardNode(i).setOnMouseExited(event -> {
//                final int selectedIndex = index;
//                player.getStraightCardNode(index).setEffect(null);
//            });
//        }
//
//        //choose kind card to drop
//        for (int i = 0; i < player.getKindCards().size(); i++) {
//            final int index = i;
//            player.getKindCardNode(i).setOnMouseClicked(event -> {
//                final int selectedIndex = index;
//                event.consume();
//                upcard.keepCard((Card) player.getKindCardNode(index));
//                botAction();
//            });
//            player.getKindCardNode(i).setOnMouseEntered(event -> {
//                final int selectedIndex = index;
//                player.getKindCardNode(index).setEffect(dropshadow);
//            });
//            player.getKindCardNode(i).setOnMouseExited(event -> {
//                final int selectedIndex = index;
//                player.getKindCardNode(index).setEffect(null);
//            });
//        }
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
//        upcard.keepCard(new Card("h", "6"));
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

        primaryStage.setScene(new Scene(createGame()));
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
