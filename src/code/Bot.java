/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.util.ArrayList;
import java.util.Collections;
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

    public int botAction(UpCard upcard) {
        int sameRank = 0;
        boolean isFourth = false;
        boolean isStraight = false;
        ArrayList<ArrayList<Integer>> hasStraight = new ArrayList<ArrayList<Integer>>();
        int upcardIndex = upcard.getSize() - 1;

        //CHECK OF A KIND
        for (int i = 0; i < getDeadwoodSize(); i++) {
            if ((upcard.getRank(upcardIndex) == getDeadwoodRank(i))) {
                sameRank++;
            }
        }

        //CHECK STRAIGHT
        addDeadwoodCardToOwnSuit();
        if (upcard.getSuit(upcardIndex) == 'c') {

            ArrayList<Integer> clubsNew = addRankToIntArraylistByCompare(getDeadwoodCards(), clubs);
            clubsNew.add(upcard.getRankValue(upcardIndex));
            hasStraight = getStraightIndex(clubsNew);

        } else if (getDeadwoodSuit(upcardIndex) == 'd') {

            ArrayList<Integer> diamondsNew = addRankToIntArraylistByCompare(getDeadwoodCards(), diamonds);
            diamondsNew.add(upcard.getRankValue(upcardIndex));
            hasStraight = getStraightIndex(diamondsNew);

        } else if (getDeadwoodSuit(upcardIndex) == 'h') {

            ArrayList<Integer> heartsNew = addRankToIntArraylistByCompare(getDeadwoodCards(), hearts);
            heartsNew.add(upcard.getRankValue(upcardIndex));
            hasStraight = getStraightIndex(heartsNew);

        } else if (getDeadwoodSuit(upcardIndex) == 's') {

            ArrayList<Integer> spadesNew = addRankToIntArraylistByCompare(getDeadwoodCards(), spades);
            spadesNew.add(upcard.getRankValue(upcardIndex));
            hasStraight = getStraightIndex(spadesNew);

        }

        //CHECK IF CARD CAN MELDS WITH ALREADY STRAIGHT OR KIND
        //KIND
        for (int i = 0; i < getKindCards().size(); i++) {
            if ((upcard.getRank(upcardIndex) == getRank(getKindCards(), i))) {
                isFourth = true;
            }
        }

        //STRAIGHT 
        addStraightCardToOwnSuit();
        System.out.println("hearts : " + getHeartsStraightInHand());
        if (upcard.getSuit(upcardIndex) == 'c') {
            for (int i = 0; i < getClubsStraightInHand().size(); i++) {
                if (upcard.getRankValueForCheckKind(upcardIndex) == (getRankValueForCheckKind(getStraightCards(), getClubsStraightInHand().get(i).get(0)) - 1)
                        || upcard.getRankValue(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getClubsStraightInHand().get(i).get(getClubsStraightInHand().get(i).size() - 1)) + 1) {
                    isStraight = true;
                }
            }
        } else if (upcard.getSuit(upcardIndex) == 'd') {
            for (int i = 0; i < getDiamondsStraightInHand().size(); i++) {
                if (upcard.getRankValueForCheckKind(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getDiamondsStraightInHand().get(i).get(0)) - 1
                        || upcard.getRankValue(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getDiamondsStraightInHand().get(i).get(getDiamondsStraightInHand().get(i).size() - 1)) + 1) {
                    isStraight = true;
                }
            }
        } else if (upcard.getSuit(upcardIndex) == 'h') {
            for (int i = 0; i < getHeartsStraightInHand().size(); i++) {
                if (upcard.getRankValueForCheckKind(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getHeartsStraightInHand().get(i).get(0)) - 1
                        || upcard.getRankValue(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getHeartsStraightInHand().get(i).get(getHeartsStraightInHand().get(i).size() - 1)) + 1) {
                    isStraight = true;
                }
            }
        } else if (upcard.getSuit(upcardIndex) == 's') {
            for (int i = 0; i < getSpadesStraightInHand().size(); i++) {
                if (upcard.getRankValueForCheckKind(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getSpadesStraightInHand().get(i).get(0)) - 1
                        || upcard.getRankValue(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getSpadesStraightInHand().get(i).get(getSpadesStraightInHand().get(i).size() - 1)) + 1) {
                    isStraight = true;
                }
            }
        }

        if (sameRank >= 2 || hasStraight != null) {
            return 0;
        } else if (isFourth) {
            return 1;
        } else if (isStraight) {
            return 2;
        }

        return -1;
    }

    public Node botDropCard() {
        ArrayList<String> cardsAll = new ArrayList<String>();
        cardsAll = convertObListToArList(getDeadwoodCards());
        String cardDrop = botIntelliDrop(cardsAll);
        int index = findIndexByString(getDeadwoodCards(), cardDrop);
        return getDeadwoodCards().remove(index);
    }

    public static String botIntelliDrop(ArrayList<String> arr) {

        Collections.sort(arr);
        ArrayList<String> duplicate = (ArrayList<String>) arr.clone();
        ArrayList<String> duplicateForNull = (ArrayList<String>) arr.clone();

        for (int i = 0; i < arr.size(); i++) {
            duplicate.add(arr.get(i));
            duplicateForNull.add(arr.get(i));
        }

        int i = duplicate.size() - 1;
        while (i > 0) {
            if (duplicate.get(i).charAt(0) == duplicate.get(i - 1).charAt(0)) {
                duplicate.remove(i);
                duplicate.remove(i - 1);
                i--;
            } else if (duplicate.get(i).charAt(0) != duplicate.get(i - 1).charAt(0)
                    && Character.toString(duplicate.get(i).charAt(1)).equals(Character.toString((duplicate.get(i - 1).charAt(1))))) {
                duplicate.remove(i);
                duplicate.remove(i - 1);
                i--;
            }
            i--;
        }
        if (duplicate.isEmpty()) {
            return duplicateForNull.get(duplicateForNull.size() - 1);
        }
        return duplicate.get(duplicate.size() - 1);
    }
}
