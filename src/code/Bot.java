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

    public Bot(ObservableList<Node> deadwoodCards, ObservableList<Node> straightCards, ObservableList<Node> kindCards) {
        super(deadwoodCards, straightCards, kindCards);
    }

    public boolean botAction(UpCard upcard) {
        int sameRank = 0;
        int sameSuit = 0;
        boolean isStraight = false;

        //check three of kind
        for (int i = 0; i < getDeadwoodSize(); i++) {
            if ((upcard.getRank(upcard.getSize() - 1)) == getDeadwoodRank(i)) {
                sameRank++;
            }
        }

        //check straight
        for (int i = 0; i < getDeadwoodSize(); i++) {
            if ((upcard.getSuit(upcard.getSize() - 1) == getDeadwoodSuit(i))
                    && ((getRankValueForCheckKind(getDeadwoodCards(), i) == upcard.checkStraight(upcard.getSize() - 1) + 1)
                    || (getRankValueForCheckKind(getDeadwoodCards(), i) == upcard.checkStraight(upcard.getSize() - 1) - 1)
                    || (getRankValueForCheckKind(getDeadwoodCards(), i) == upcard.checkStraight(upcard.getSize() - 1) + 2)
                    || (getRankValueForCheckKind(getDeadwoodCards(), i) == upcard.checkStraight(upcard.getSize() - 1) - 2))) {
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

    public Node botDropCard() {
        int index = 0;
        return getDeadwoodCards().remove(index);
    }

}
