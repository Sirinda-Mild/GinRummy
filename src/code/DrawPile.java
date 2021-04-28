/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 *
 * @author acer
 */
public class DrawPile extends UpCard {

    public DrawPile(ArrayList<Card> cards) {
        super(cards);
    }

    @Override
    public Node drawCard() {
        return super.drawCard(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keepCard(Card card, int count) {
        super.keepCard(card, count); //To change body of generated methods, choose Tools | Templates.
    }

}
