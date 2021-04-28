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

    public Bot(ObservableList<Node> cards) {
        super(cards);
    }

    @Override
    public void reset() {
        super.reset(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node DropCard(int index) {
        return super.DropCard(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getSuitValue(int index) {
        return super.getSuitValue(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void toFront(int index) {
        super.toFront(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void takeCard(Card card) {
        super.takeCard(card); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int Deadwood() {
        return super.Deadwood(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int Score() {
        return super.Score(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getRankValue(int index) {
        return super.getRankValue(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public char getRank(int index) {
        return super.getRank(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public char getSuit(int index) {
        return super.getSuit(index); //To change body of generated methods, choose Tools | Templates.
    }


    @Override
    public int getSize() {
        return super.getSize(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int checkStraight(int index) {
        return super.checkStraight(index); //To change body of generated methods, choose Tools | Templates.
    }

}
