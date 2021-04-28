/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class UpCard {

    private ObservableList<Node> cards;

    public UpCard(ObservableList<Node> cards) {
        this.cards = cards;
    }

    public void keepCard(Card card) {
        cards.add(card);
    }

    public Node drawCard() {
        Node card = cards.get(cards.size() - 1);
        cards.remove(cards.size() - 1);
        return card;
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

    public int getSize() {
        return cards.size();
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

//    public void reset() {
//        cards.clear();
//        value.set(0);
//    }
    @Override
    public String toString() {
        return cards.toString();
    }
}
