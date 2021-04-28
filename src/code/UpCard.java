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

    private ArrayList<Card> cards = new ArrayList<>();
//    private SimpleIntegerProperty value = new SimpleIntegerProperty(0);

    public UpCard(ArrayList<Card> cards) {
        this.cards = cards;
    }

    public void keepCard(Card card, int count) {
        for (int i = 0; i < count; i++) {
            cards.add(card);
        }
    }

    public Node drawCard() {
        Node card = cards.get(cards.size() - 1);
        cards.remove(cards.size() - 1);
        return card;
    }

//    public void reset() {
//        cards.clear();
//        value.set(0);
//    }
}
