/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 *
 * @author acer
 */
public class Bot extends Player{

    public Bot(ObservableList<Node> cards) {
        super(cards);
    }

    @Override
    public void reset() {
        super.reset(); 
    }

    @Override
    public void takeCard(Card card) {
        super.takeCard(card); 
    }
    
}
