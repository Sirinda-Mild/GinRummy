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
    public String getValue(int index) {
        return super.getValue(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node getNode(int index) {
        return super.getNode(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setCards(ObservableList<Node> cards) {
        super.setCards(cards); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ObservableList<Node> getCards() {
        return super.getCards(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Node DropCard(int index) {
        return super.DropCard(index); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void toBack(int index) {
        super.toBack(index); //To change body of generated methods, choose Tools | Templates.
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

    public boolean takeOrPass(UpCard upcard, Bot bot) {
        int sameRank = 0;
        int sameSuit = 0;
        boolean isStraight = false;

        //check three of kind
        for (int i = 0; i < bot.getSize(); i++) {
            if ((upcard.getRank(upcard.getSize() - 1)) == bot.getRank(i)) {
                sameRank++;
            }
        }

        //check straight
        for (int i = 0; i < bot.getSize(); i++) {
            if ((upcard.getSuit(upcard.getSize() - 1) == bot.getSuit(i))
                    && ((bot.getRankValueForCheckKind(i) == upcard.checkStraight(upcard.getSize() - 1) + 1)
                    || (bot.getRankValueForCheckKind(i) == upcard.checkStraight(upcard.getSize() - 1) - 1)
                    || (bot.getRankValueForCheckKind(i) == upcard.checkStraight(upcard.getSize() - 1) + 2)
                    || (bot.getRankValueForCheckKind(i) == upcard.checkStraight(upcard.getSize() - 1) - 2))) {
                sameSuit++;
            }
        }
        if (sameSuit >= 2) {
            isStraight = true;
        }
        if (sameRank >= 2 || isStraight == true) {
            return true;
        } else {
            return false;
        }
    }
}
