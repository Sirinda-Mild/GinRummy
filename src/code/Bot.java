/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 *
 * @author acer
 */
public class Bot extends Player {

    public Bot(ArrayList<Card> cards) {
        super(cards);
    }

    @Override
    public void reset() {
        super.reset(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void DropCard(Card card) {
        super.DropCard(card); //To change body of generated methods, choose Tools | Templates.
    }

//    @Override
//    public void takeCard(Card card, int count) {
//        super.takeCard(card, count); //To change body of generated methods, choose Tools | Templates.
//    }


}
