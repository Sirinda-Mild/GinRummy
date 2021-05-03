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

        HBox drawCardsPane = new HBox(100);
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

        drawCardsPane.setAlignment(Pos.CENTER);
        drawCardsPane.getChildren().addAll(countDrawpile, upCardPilePane);

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

        drawCardsPane.setLayoutX(402);
        drawCardsPane.setLayoutY(230);

        playerCardsPane.setLayoutX(20);
        playerCardsPane.setLayoutY(430);

        buttonBox.setLayoutX(749);
        buttonBox.setLayoutY(317);

        playerInfo.setLayoutX(550);
        playerInfo.setLayoutY(375);

        root.getChildren().addAll(InGameBG, drawCardsPane, buttonBox, playerInfo, playerCardsPane, buttonExit);

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
                //BOT WIN 
                if (bot.Deadwood() <= 10) {
                    if (bot.getDeadwoodCards().size() == 1) { //WIN GIN
                        playerScore.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                        botScore.textProperty().bind(new SimpleStringProperty("Dealer Win Gin!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                    } else if (bot.getDeadwoodCards().size() == 0) { //WIN KNOCK
                        playerScore.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                        botScore.textProperty().bind(new SimpleStringProperty("Dealer Win Big Gin!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                    } else {
                        playerScore.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                        botScore.textProperty().bind(new SimpleStringProperty("Dealer Win Knock!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                    }
                    root.getChildren().clear();
                    root.getChildren().addAll(BGopenCardEnd, botCardsPane, playerCardsPane, playerInfo, botScore, buttonExit);
                }

                //PLAYER WIN
                if (player.Deadwood() <= 10) {
                    if (player.getDeadwoodCards().size() < 2) { //WIN GIN
                        playerScore.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                        botScore.textProperty().bind(new SimpleStringProperty("Player Win Gin!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                    } else if (player.getDeadwoodCards().size() == 0) { //WIN KNOCK
                        playerScore.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                        botScore.textProperty().bind(new SimpleStringProperty("Player Win Big Gin!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
                    } else {
                        playerScore.textProperty().bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
                        botScore.textProperty().bind(new SimpleStringProperty("Player Win Knock!!  Dealer Deadwood : ").concat(Integer.toString(bot.Deadwood())));
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
        playerDeadwood.textProperty()
                .bind(new SimpleStringProperty("").concat(Integer.toString(player.Deadwood())));
        countDrawpile.textProperty()
                .bind(new SimpleStringProperty("").concat(Integer.toString(drawpile.getSize())));

        return root;
    }

    public void botAction() {
        botTurn = bot.botAction(upcard);
        System.out.println("BOT ACTION :: BOT TURN :: " + botTurn);
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

    @Override
    public void start(Stage primaryStage) throws Exception {
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
//        Rectangle BGabout4 = new Rectangle(984, 562);
//        Image imgBgabout4 = new Image("resources/background/About-4.jpg");
//        BGabout4.setFill(new ImagePattern(imgBgabout4));

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
            } else if (page >= 3) {
                page = 3;
                root.getChildren().clear();
                root.getChildren().addAll(BGabout3, back);
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
            } else if (page >= 3) {
                page = 3;
                root.getChildren().clear();
                root.getChildren().addAll(BGabout3, back);
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
