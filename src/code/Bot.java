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
            System.out.println("CLUB array :: " + clubsNew);
            clubsNew.add(upcard.getRankValue(upcardIndex));
            Collections.sort(clubsNew);
            hasStraight = getStraightIndex(clubsNew);

        } else if (getDeadwoodSuit(upcardIndex) == 'd') {

            ArrayList<Integer> diamondsNew = addRankToIntArraylistByCompare(getDeadwoodCards(), diamonds);
            System.out.println("DIAMOND array :: " + diamondsNew);
            diamondsNew.add(upcard.getRankValue(upcardIndex));
            Collections.sort(diamondsNew);
            hasStraight = getStraightIndex(diamondsNew);

        } else if (getDeadwoodSuit(upcardIndex) == 'h') {

            ArrayList<Integer> heartsNew = addRankToIntArraylistByCompare(getDeadwoodCards(), hearts);
            System.out.println("HEART array :: " + heartsNew);
            heartsNew.add(upcard.getRankValue(upcardIndex));
            Collections.sort(heartsNew);
            hasStraight = getStraightIndex(heartsNew);

        } else if (getDeadwoodSuit(upcardIndex) == 's') {

            ArrayList<Integer> spadesNew = addRankToIntArraylistByCompare(getDeadwoodCards(), spades);
            System.out.println("SPADE array :: " + spadesNew);
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
            if (getClubsStraightInHand() != null) {
                for (int i = 0; i < getClubsStraightInHand().size(); i++) {
                    if (upcard.getRankValueForCheckKind(upcardIndex) == (getRankValueForCheckKind(getStraightCards(), getClubsStraightInHand().get(i).get(0)) - 1)
                            || upcard.getRankValue(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getClubsStraightInHand().get(i).get(getClubsStraightInHand().get(i).size() - 1)) + 1) {
                        isStraight = true;
                    }
                }
            }
        } else if (upcard.getSuit(upcardIndex) == 'd') {
            if (getDiamondsStraightInHand() != null) {
                for (int i = 0; i < getDiamondsStraightInHand().size(); i++) {
                    if (upcard.getRankValueForCheckKind(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getDiamondsStraightInHand().get(i).get(0)) - 1
                            || upcard.getRankValue(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getDiamondsStraightInHand().get(i).get(getDiamondsStraightInHand().get(i).size() - 1)) + 1) {
                        isStraight = true;
                    }
                }
            }
        } else if (upcard.getSuit(upcardIndex) == 'h') {
            if (getHeartsStraightInHand() != null) {
                for (int i = 0; i < getHeartsStraightInHand().size(); i++) {
                    if (upcard.getRankValueForCheckKind(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getHeartsStraightInHand().get(i).get(0)) - 1
                            || upcard.getRankValue(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getHeartsStraightInHand().get(i).get(getHeartsStraightInHand().get(i).size() - 1)) + 1) {
                        isStraight = true;
                    }
                }
            }
        } else if (upcard.getSuit(upcardIndex) == 's') {
            if (getSpadesStraightInHand() != null) {
                for (int i = 0; i < getSpadesStraightInHand().size(); i++) {
                    if (upcard.getRankValueForCheckKind(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getSpadesStraightInHand().get(i).get(0)) - 1
                            || upcard.getRankValue(upcardIndex) == getRankValueForCheckKind(getStraightCards(), getSpadesStraightInHand().get(i).get(getSpadesStraightInHand().get(i).size() - 1)) + 1) {
                        isStraight = true;
                    }
                }
            }
        }

        System.out.println("sameRank : " + sameRank);
        System.out.println("hasStraight : " + hasStraight);
        System.out.println("isFourth : " + isFourth);
        System.out.println("isStraight : " + isStraight);
        if (isFourth) {
            return 1; //keep in kind card
        } else if (isStraight) {
            return 2; //keep in straight
        } else if (sameRank >= 2) {
            return 0;// keep in deadwood
        } else if (hasStraight != null) {
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
    
     public void sortBotDeadwoodCards() {
        
        //sort card in deadwood card
        sortCardsByRank(getDeadwoodCards());

        //CHECK KIND
        kindInHand = getKindIndex(addRankToIntArraylist(getDeadwoodCards()));

        if (kindInHand != null) {
            //add card from deadwood to kind observable list
            for (int i = kindInHand.size() - 1; i >= 0; i--) {
                for (int index = kindInHand.get(i).size() - 1; index >= 0; index--) {
                    getKindCards().add(getDeadwoodCards().get(kindInHand.get(i).get(index)));
                }
            }
            sortCardsByRank(getKindCards());
        }

        //CHECK STRAIGHT
        addDeadwoodCardToOwnSuit();

        //check straight in club
        if (clubs.size() >= 3) {
            clubsStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(getDeadwoodCards(), clubs));
            if (clubsStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = clubsStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = clubsStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        getStraightCards().add(getDeadwoodCards().get(clubs.get(clubsStraightInHand.get(i).get(index))));
                    }
                }
            }
            addDeadwoodCardToOwnSuit();
        }

        //check straight in diamonds
        if (diamonds.size() >= 3) {
            diamondsStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(getDeadwoodCards(), diamonds));
            if (diamondsStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = diamondsStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = diamondsStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        getStraightCards().add(getDeadwoodCards().get(diamonds.get(diamondsStraightInHand.get(i).get(index))));
                    }
                }
            }
            addDeadwoodCardToOwnSuit();
        }

        //check straight in hearts
        if (hearts.size() >= 3) {
            heartsStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(getDeadwoodCards(), hearts));
            if (heartsStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = heartsStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = heartsStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        getStraightCards().add(getDeadwoodCards().get(hearts.get(heartsStraightInHand.get(i).get(index))));
                    }
                }
            }
            addDeadwoodCardToOwnSuit();
        }

        //check straight in spades
        if (spades.size() >= 3) {
            spadesStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(getDeadwoodCards(), spades));
            if (spadesStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = spadesStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = spadesStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        getStraightCards().add(getDeadwoodCards().get(spades.get(spadesStraightInHand.get(i).get(index))));
                    }
                }
            }
            addDeadwoodCardToOwnSuit();
        }
        sortCardsForStraight(getStraightCards());
        sortCardsByRank(getKindCards());
    }
}
