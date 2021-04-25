/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class UpCard {

    private ObservableList<Node> cards;
//    private SimpleIntegerProperty value = new SimpleIntegerProperty(0);

    public UpCard(ObservableList<Node> cards) {
        this.cards = cards;
    }

    public void takeCard(Card card) {
        cards.add(card);

//        if (card.rank == Rank.ACE) {
//            aces++;
//        }
//
//        if (value.get() + card.value > 21 && aces > 0) {
//            value.set(value.get() + card.value - 10);    //then count ace as '1' not '11'
//            aces--;
//        }
//        else {
//            value.set(value.get() + card.value);
//        }
    }

    public Card drawCard() {
        Card card = cards.get(cards.size()-1);
        cards.remove(cards.size() - 1);
        return card;
    }

//    public void reset() {
//        cards.clear();
//        value.set(0);
//    }
}
