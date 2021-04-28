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

    private ObservableList<Node> cards;

    public Player(ObservableList<Node> cards) {
        this.cards = cards;
    }

    public void takeCard(Card card) {
        cards.add(card);
    }

    public void toFront(int index) {
        cards.get(index).toFront();
    }

    public Node DropCard(int index) {
        Node card = cards.get(index);
        cards.remove(index);
        return card;
    }

    public void reset() {
        cards.clear();
    }

    public int Deadwood() {
        int deadwood = 0;
        return deadwood;
    }

    public int Score() {
        int score = 0;
        return score;
    }

    public int getSize() {
        return cards.size();
    }

    public char getSuit(int index) {
        return cards.get(index).toString().charAt(1);
    }

    public char getRank(int index) {
        return cards.get(index).toString().charAt(0);
    }

    public int getRankValue(int index) {
        int cardValue = 0;
        if (cards.get(index).toString().charAt(0) == '0'
                || cards.get(index).toString().charAt(0) == 'j'
                || cards.get(index).toString().charAt(0) == 'q'
                || cards.get(index).toString().charAt(0) == 'k') {
            cardValue = 10;
        } else {
            cardValue = Character.getNumericValue(cards.get(index).toString().charAt(0));
        }
        return cardValue;
    }

    public int getSuitValue(int index) {
        int cardValue = 0;
        if (cards.get(index).toString().charAt(0) == 'c') {
            cardValue = 1;
        } else if (cards.get(index).toString().charAt(0) == 'd') {
            cardValue = 2;
        } else if (cards.get(index).toString().charAt(0) == 'h') {
            cardValue = 3;
        } else if (cards.get(index).toString().charAt(0) == 's') {
            cardValue = 4;
        }
        return cardValue;
    }

    public int checkStraight(int index) {
        int cardValue = 0;
        if (cards.get(index).toString().charAt(0) == '0') {
            cardValue = 10;
        } else if (cards.get(index).toString().charAt(0) == 'j') {
            cardValue = 11;
        } else if (cards.get(index).toString().charAt(0) == 'q') {
            cardValue = 12;
        } else if (cards.get(index).toString().charAt(0) == 'k') {
            cardValue = 13;
        } else {
            cardValue = Character.getNumericValue(cards.get(index).toString().charAt(0));
        }
        return cardValue;
    }

    @Override
    public String toString() {
        return cards.toString();
    }

}
