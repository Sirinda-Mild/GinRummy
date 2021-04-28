package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class Player {

    private ArrayList<Card> cards = new ArrayList<>();
//    private SimpleIntegerProperty value = new SimpleIntegerProperty(0);

    public Player(ArrayList<Card> playerCards) {
        this.cards = playerCards;
        System.out.println("construct");
    }

    public void takeCard(Card card) {
        System.out.println("takecard");
        cards.add(card);
    }

//    public void takeCard(Card card, int count) {
//        System.out.println("takecard");
//        for (int i = 0; i < count; i++) {
//            System.out.println("adding" + i);
//            cards.add(card);
//        }
//
//    }
    public void DropCard(Card card) {
//        cards.remove(card);
    }

    public void reset() {
        System.out.println("reset");
        cards.clear();
//        value.set(0);
    }

    public int Deadwood() {
        int deadwood = 0;
        return deadwood;
    }

    public int Score() {
        int score = 0;
        return score;
    }
}
