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
    ArrayList<Integer> deadwoodInHand = new ArrayList<>();
    ArrayList<Integer> straightInHand = new ArrayList<>();
    ArrayList<Integer> kindInHand = new ArrayList<>();

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
        for (int index = 0; index < deadwoodInHand.size(); index++) {
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
        boolean isContinue = false;

        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i) == arr.get(i + 1)) {
                kindCount++;
                isContinue = true;
                System.out.println("KIND COUNT ADDED");
            } else {
                /* No more continue. */
                System.out.println("NO MORE CONTINUE" + i);
                /* added 1-5-2021 */
                if (isContinue == true && arr.size() - 2 == i) {
                    /* case of the last 2 index of the array */
                    System.out.println("CASE OF LAST 2 INDEX OF THE ARRAY OCCURED");
                    if (arr.get(i) == arr.get(i + 1)) {
                        /* They are kind */
                        kindCount++;
                        i++;
                    }
                }
                
                if (isContinue && kindCount >= 2) {
                    /* They are kind. */
                    result.add(new ArrayList<Integer>());
                    System.out.println("NEW SUB ARRAYLIST OF KIND INDEX ADDED");
                    /* Add the continue cards into the result array */
                    for (int k = 0; k < kindCount + 1; k++) {
                        /* Add index in the sub array */
                        result.get(indexOfSubArray).add(i - k);
                    }
                    Collections.sort(result.get(indexOfSubArray));
                    indexOfSubArray++;
                }

                isContinue = false;
                kindCount = 0;
            }

        }
System.out.println("KINDINDEX SIZE :: " + result.size());
        return (result.isEmpty()) ? null : result;

    }

    public static int getAmountOfKind(ArrayList<Integer> arr) {
        Collections.sort(arr);

        int amountOfKind = 0;
        int kindCount = 0;
        boolean isContinue = false;

        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i) == arr.get(i + 1)) {
                kindCount++;
                isContinue = true;
            } else {
                /* No more continue. */
                if (isContinue && kindCount >= 2) {
                    /* They are kind. */
                    amountOfKind++;
                }

                isContinue = false;
                kindCount = 0;
            }

        }

        return amountOfKind;

    }

    public static int getAmountOfStraigth(ArrayList<Integer> arr) {

        /* Sort before matching */
        Collections.sort(arr);

        boolean isContinue = false;

        /*
         * Straight count will track the amount of pair which are consequently match ex.
         * if straightCount is 3 -> means the amount of card has straight is 4.
         */
        int straightCount = 0;

        /* the amount of set that are being straight */
        int amountOfMatched = 0;

        for (int i = 0; i < arr.size() - 1; i++) {
            if (arr.get(i + 1) - arr.get(i) == 1 && i != arr.size() - 2) {
                /* If the next number is more than the current number for 1 (Sequencely) */
                isContinue = true;
                straightCount++;
                /* track amount of straight */
            } else {
                /* No more continue */
                if (isContinue == true) {
                    if (straightCount >= 2) {
                        /* They are straighted */
                        amountOfMatched++;
                        straightCount = 0;
                    } else {
                        straightCount = 0;
                        /* reset count back to 0 */
                    }
                    isContinue = false;
                    /* Not continue. */
                }

            }
        }
        return amountOfMatched;
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

    public void sortDeadwoodCards() {
        //sort card from rank
        ArrayList<String> cardSorted = new ArrayList<>();
        for (int i = 0; i < this.getDeadwoodSize(); i++) {
            cardSorted.add(this.getDeadwoodValue(i));
        }
        Collections.sort(cardSorted);
        for (int i = 0, j = cardSorted.size() - 1; j >= 0 && i < this.getDeadwoodSize(); i++) {
            if (this.getDeadwoodValue(i).equals(cardSorted.get(j))) {
                this.DeadwoodToBack(i);
                j--;
                i = 0;
            }
        }

        //CHECK KIND
        //convert rank card in hand to integer
        ArrayList<Integer> rankCardsInHand = new ArrayList<>();
        for (int i = 0; i < this.getDeadwoodSize(); i++) {
            rankCardsInHand.add(this.getRankValueForCheckKind(i));
        }
        System.out.println(rankCardsInHand);
        ArrayList<ArrayList<Integer>> kindIndex = this.getKindIndex(rankCardsInHand);
        ArrayList<ArrayList<Integer>> straightIndex = this.getStraightIndex(rankCardsInHand);
        //check of a kind
        System.out.println("kindindex size : " + kindIndex.size());
        if (kindIndex != null) {
            //if has kind get kind in array list
            for (int i = 0; i < kindIndex.size(); i++) {
                for (int k = 0; k < kindIndex.get(i).size(); k++) {
                    kindInHand.add(kindIndex.get(i).get(k));
                }
            }
        }
        System.out.println("before");
        System.out.println(kindInHand);
        System.out.println(deadwoodCards);
        System.out.println(kindCards);
        //add card from deadwood to kind observable list
        for (int index = 0; index < kindInHand.size(); index++) {
            kindCards.add(deadwoodCards.get(kindInHand.get(0)));
            
        }
        System.out.println("after");
        System.out.println(kindInHand);
        System.out.println(deadwoodCards);
        System.out.println(kindCards);
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
