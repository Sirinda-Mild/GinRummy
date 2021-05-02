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

        //CHECK OF A KIND FROM DEADWOOD
        for (int i = 0; i < getDeadwoodSize(); i++) {
            if ((upcard.getRank(upcardIndex) == getDeadwoodRank(i))) {
                sameRank++;
            }
        }

        //CHECK STRAIGHT FROM DEADWOOD
        addDeadwoodCardToOwnSuit();
        if (upcard.getSuit(upcardIndex) == 'c') {
            ArrayList<Integer> clubsNew = addRankToIntArraylistByCompare(getDeadwoodCards(), clubs);
            clubsNew.add(upcard.getRankValue(upcardIndex));
            Collections.sort(clubsNew);
            hasStraight = getStraightIndex(clubsNew);

        } else if (upcard.getSuit(upcardIndex) == 'd') {
            ArrayList<Integer> diamondsNew = addRankToIntArraylistByCompare(getDeadwoodCards(), diamonds);
            diamondsNew.add(upcard.getRankValue(upcardIndex));
            Collections.sort(diamondsNew);
            hasStraight = getStraightIndex(diamondsNew);

        } else if (upcard.getSuit(upcardIndex) == 'h') {

            ArrayList<Integer> heartsNew = addRankToIntArraylistByCompare(getDeadwoodCards(), hearts);
            heartsNew.add(upcard.getRankValue(upcardIndex));
            Collections.sort(heartsNew);
            hasStraight = getStraightIndex(heartsNew);

        } else if (upcard.getSuit(upcardIndex) == 's') {

            ArrayList<Integer> spadesNew = addRankToIntArraylistByCompare(getDeadwoodCards(), spades);
            spadesNew.add(upcard.getRankValue(upcardIndex));
            Collections.sort(spadesNew);
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
        if (upcard.getSuit(upcardIndex) == 'c') {
            if (clubs != null) {
                for (int i = 0; i < clubs.size(); i++) {
                    if ((upcard.getRankValueForCheckKind(upcardIndex)) == (getRankValueForCheckKind(getStraightCards(), clubs.get(0)) - 1)
                            || (upcard.getRankValueForCheckKind(upcardIndex)) == (getRankValueForCheckKind(getStraightCards(), clubs.get(clubs.size() - 1)) + 1)) {
                        isStraight = true;
                    }
                }
            }
        } else if (upcard.getSuit(upcardIndex) == 'd') {
            if (diamonds != null) {
                for (int i = 0; i < diamonds.size(); i++) {
                    if ((upcard.getRankValueForCheckKind(upcardIndex)) == (getRankValueForCheckKind(getStraightCards(), diamonds.get(0)) - 1)
                            || (upcard.getRankValueForCheckKind(upcardIndex)) == (getRankValueForCheckKind(getStraightCards(), diamonds.get(diamonds.size() - 1)) + 1)) {
                        isStraight = true;
                    }
                }
            }
        } else if (upcard.getSuit(upcardIndex) == 'h') {
            if (hearts != null) {
                for (int i = 0; i < hearts.size(); i++) {
                    if ((upcard.getRankValueForCheckKind(upcardIndex)) == (getRankValueForCheckKind(getStraightCards(), hearts.get(0)) - 1)
                            || (upcard.getRankValueForCheckKind(upcardIndex)) == (getRankValueForCheckKind(getStraightCards(), hearts.get(hearts.size() - 1)) + 1)) {
                        isStraight = true;
                    }
                }
            }
        } else if (upcard.getSuit(upcardIndex) == 's') {
            if (spades != null) {
                for (int i = 0; i < spades.size(); i++) {
                    if ((upcard.getRankValueForCheckKind(upcardIndex)) == (getRankValueForCheckKind(getStraightCards(), spades.get(0)) - 1)
                            || (upcard.getRankValueForCheckKind(upcardIndex)) == (getRankValueForCheckKind(getStraightCards(), spades.get(spades.size() - 1)) + 1)) {
                        isStraight = true;
                    }
                }
            }
        }
        
        if (isFourth) {
            return 1; //keep in kind card
        } else if (isStraight) {
            return 2; //keep in straight
        } else if (sameRank
                >= 2) {
            return 0;// keep in deadwood
        } else if (hasStraight
                != null) {
            if (!hasStraight.isEmpty()) {
                return 0; // keep in deadwood
            }
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
