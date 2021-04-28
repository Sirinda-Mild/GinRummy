/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.ArrayList;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class DrawPile extends UpCard {

    public DrawPile(ObservableList<Node> cards) {
        super(cards);
    }

    @Override
    public Node drawCard() {
        return super.drawCard(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keepCard(Card card) {
        super.keepCard(card); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getSize() {
        return super.getSize(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getRankValue(int index) {
        return super.getRankValue(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int checkStraight(int index) {
        return super.checkStraight(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public char getRank(int index) {
        return super.getRank(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public char getSuit(int index) {
        return super.getSuit(index); //To change body of generated methods, choose Tools | Templates.
    }


}
