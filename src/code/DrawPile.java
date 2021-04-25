/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import javafx.collections.ObservableList;
import javafx.scene.Node;

/**
 *
 * @author acer
 */
public class DrawPile extends UpCard{

    public DrawPile(ObservableList<Node> cards) {
        super(cards);
    }

    @Override
    public void reset() {
        super.reset(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void removeCard() {
        super.removeCard(); //To change boDropdy of generated methods, choose Tools | Templates.
    }

    @Override
    public void takeCard(Card card) {
        super.takeCard(card); //To change body of generated methods, choose Tools | Templates.
    }
    
}
