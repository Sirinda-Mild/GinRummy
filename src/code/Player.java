package code;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;

public class Player {

    private ObservableList<Node> deadwoodCards;
    private ObservableList<Node> straightCards;
    private ObservableList<Node> kindCards;
//    ArrayList<Integer> deadwoodInHand = new ArrayList<>();
    ArrayList<ArrayList<Integer>> straightInHand = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> kindInHand = new ArrayList<ArrayList<Integer>>();

    public Player(ObservableList<Node> deadwoodCards, ObservableList<Node> straightCards, ObservableList<Node> kindCards) {
        this.deadwoodCards = deadwoodCards;
        this.straightCards = straightCards;
        this.kindCards = kindCards;
    }

    public ObservableList<Node> getDeadwoodCards() {
        return deadwoodCards;
    }

    public ObservableList<Node> getStraightCards() {
        return straightCards;
    }

    public ObservableList<Node> getKindCards() {
        return kindCards;
    }

    public void takeDeadwoodCard(Card card) {
        deadwoodCards.add(card);
    }

    public void DeadwoodToBack(int index) {
        deadwoodCards.get(index).toBack();
    }

    public Node getDeadwoodNode(int index) {
        return deadwoodCards.get(index);
    }

    public Node DropDeadwoodCard(int index) {
        Node card = deadwoodCards.get(index);
        deadwoodCards.remove(index);
        return card;
    }

    public void reset() {
        deadwoodCards.clear();
        straightCards.clear();
        kindCards.clear();
    }

    public int Deadwood() {
        int deadwood = 0;
        for (int index = 0; index < deadwoodCards.size(); index++) {
            deadwood += this.getDeadwoodRankValue(index);
        }
        System.out.println(deadwood);
        return deadwood;
    }

    public int Score() {
        int score = 0;
        return score;
    }

    public int getDeadwoodSize() {
        return deadwoodCards.size();
    }

    public char getDeadwoodSuit(int index) {
        return deadwoodCards.get(index).toString().charAt(1);
    }

    public char getDeadwoodRank(int index) {
        return deadwoodCards.get(index).toString().charAt(0);
    }

    public int getDeadwoodRankValue(int index) {
        int cardValue = 0;
        if (this.getDeadwoodRank(index) == 'm'
                || this.getDeadwoodRank(index) == 'n'
                || this.getDeadwoodRank(index) == 'o'
                || this.getDeadwoodRank(index) == 'p') {
            cardValue = 10;
        } else {
            cardValue = Character.getNumericValue(this.getDeadwoodRank(index));
        }
        return cardValue;
    }

    public int getRankValueForCheckKind(int index) {
        int cardValue = 0;
        if (this.getDeadwoodRank(index) == 'm') {
            cardValue = 10;
        } else if (this.getDeadwoodRank(index) == 'n') {
            cardValue = 11;
        } else if (this.getDeadwoodRank(index) == 'o') {
            cardValue = 12;
        } else if (this.getDeadwoodRank(index) == 'p') {
            cardValue = 13;
        } else {
            cardValue = Character.getNumericValue(this.getDeadwoodRank(index));
        }
        return cardValue;
    }

    public String getDeadwoodValue(int index) {
        String card = deadwoodCards.get(index).toString();
        return card;
    }

    public static ArrayList<ArrayList<Integer>> getKindIndex(ArrayList<Integer> arr) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        int indexOfSubArray = 0;
        int kindCount = 0;

        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i) == arr.get(i + 1)) {
                kindCount++;
                if (kindCount == 2) {
                    result.add(new ArrayList<Integer>());
                    result.get(indexOfSubArray).add(i - 1);
                    result.get(indexOfSubArray).add(i);
                    result.get(indexOfSubArray).add(i + 1);
                } else if (kindCount > 2) {
                    result.get(indexOfSubArray).add(i + 1);
                }
            } else {
                if (kindCount >= 2) {
                    indexOfSubArray++;
                }
                kindCount = 0;
            }
        }
        return (result.isEmpty()) ? null : result;

    }

    public static ArrayList<ArrayList<Integer>> getStraightIndex(ArrayList<Integer> arr) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();

        int indexOfSubArray = 0;
        boolean continuity = false;
        int straightCount = 0;

        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i + 1) - arr.get(i) == 1 && i != arr.size() - 2) {
                /* If the next number is more than the current number for 1 (Sequencely) */
                continuity = true;
                straightCount++;
            } else {
                /* No more continue */
                if (continuity == true && arr.size() - 2 == i) {
                    /* case of the last 2 index of the array */
                    if (arr.get(arr.size() - 1) - arr.get(arr.size() - 2) == 1) {
                        /* They are straight */
                        straightCount++;
                        i++;
                    }
                }
                if (continuity == true) {
                    if (straightCount >= 2) {
                        /* They are straight */
                        result.add(new ArrayList<Integer>());
                        for (int k = 0; k < straightCount + 1; k++) {
                            /* Add index number from here until previous ex. 4 5 6 -> adding : 6 5 4 */
                            result.get(indexOfSubArray).add(i - k);
                        }
                        Collections.sort(result.get(indexOfSubArray));
                        /* Sort back to 4 5 6 */
                        indexOfSubArray++;
                        continuity = false;
                        straightCount = 0;
                    } else {
                        straightCount = 0;
                        /* Not continue and straight count is not more than 2 */
                    }
                    continuity = false;
                }

            }
        }

        return (result.isEmpty()) ? null : result;
    }

    public ArrayList<Integer> addRankToIntArraylist() {
        //convert rank card in deadwoodcards to integer
        ArrayList<Integer> cardsList = new ArrayList<>();
        for (int i = 0; i < deadwoodCards.size(); i++) {
            cardsList.add(this.getRankValueForCheckKind(i));
        }
        return cardsList;
    }

    public void sortCards(ObservableList<Node> card) {
        //sort card from rank and suit
        ArrayList<String> cardSorted = new ArrayList<>();
        for (int i = 0; i < card.size(); i++) {
            cardSorted.add(card.get(i).toString());
        }
        Collections.sort(cardSorted);
        for (int i = 0, j = cardSorted.size() - 1; j >= 0 && i < card.size(); i++) {
            if (card.get(i).toString().equals(cardSorted.get(j))) {
                card.get(i).toBack();
                j--;
                i = 0;
            }
        }
    }

    public void sortDeadwoodCards() {
        //sort card in deadwood card
        sortCards(deadwoodCards);

        //CHECK KIND
        kindInHand = this.getKindIndex(addRankToIntArraylist());

        if (kindInHand != null) {
            //add card from deadwood to kind observable list
            for (int i = kindInHand.size() - 1; i >= 0; i--) {
                for (int index = kindInHand.get(i).size() - 1; index >= 0; index--) {
                    kindCards.add(deadwoodCards.get(kindInHand.get(i).get(index)));
                }
            }
            sortCards(kindCards);
        }

        //CHECK STRAIGHT
        //keep suit on their own array list
        ArrayList<Integer> clubs = new ArrayList<>();
        ArrayList<Integer> diamonds = new ArrayList<>();
        ArrayList<Integer> hearts = new ArrayList<>();
        ArrayList<Integer> spades = new ArrayList<>();
        int straightCount = 0;
        int deadwoodRe = 0;
        for (int i = 0; i < deadwoodCards.size(); i++) {
            if (getDeadwoodSuit(i) == 'c') {
                clubs.add(i);
            } else if (getDeadwoodSuit(i) == 'd') {
                diamonds.add(i);
            } else if (getDeadwoodSuit(i) == 'h') {
                hearts.add(i);
            } else if (getDeadwoodSuit(i) == 's') {
                spades.add(i);
            }
        }
        System.out.println("clubs");
        System.out.println(clubs + " : " + clubs.size());
        System.out.println("diamonds");
        System.out.println(diamonds + " : " + diamonds.size());
        System.out.println("hearts");
        System.out.println(hearts + " : " + hearts.size());
        System.out.println("spades");
        System.out.println(spades + " : " + spades.size());
        if (clubs.size() >= 3) {
            ArrayList< ArrayList<Integer>> clubsStraightInHand = this.getStraightIndex(addRankToIntArraylist());
            System.out.println(clubsStraightInHand);
        }
    }

    public void sortStraigthOrKindInHand() {
        //clone observable list to array list
        ArrayList<Integer> rankCardsInHand = new ArrayList<>();
        for (int i = 0; i < this.getDeadwoodSize(); i++) {
            rankCardsInHand.add(this.getRankValueForCheckKind(i));
        }
        //push straight cards to back
        for (int i = 0, j = kindInHand.size() - 1; j >= 0 && i < this.getDeadwoodSize(); i++) {
            if (this.getDeadwoodValue(i).equals(kindInHand.get(j))) {
                this.DeadwoodToBack(i);
                j--;
                i = 0;
            }
        }
    }

    @Override
    public String toString() {
        return deadwoodCards.toString();
    }

}
