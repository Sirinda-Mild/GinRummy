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

    private ObservableList<Node> cards;
    ArrayList<String> cardInHand = new ArrayList<>();
    ArrayList<String> straightInHand = new ArrayList<>();
    ArrayList<String> kindInHand = new ArrayList<>();

    public Player(ObservableList<Node> cards) {
        this.cards = cards;
    }

    public ObservableList<Node> getCards() {
        return cards;
    }

    public void setCards(ObservableList<Node> cards) {
        this.cards = cards;
    }

    public void takeCard(Card card) {
        cards.add(card);
    }

    public void toBack(int index) {
        cards.get(index).toBack();
    }

    public Node getNode(int index) {
        return cards.get(index);
    }

    public Node DropCard(int index) {
        Node card = cards.get(index);
        cards.remove(index);
        return card;
    }

    public void reset() {
        cards.clear();
    }

    public int Deadwood() {
        int deadwood = 0;
        for (int index = 0; index < cardInHand.size(); index++) {
            deadwood += this.getRankValue(index);
        }
        System.out.println(deadwood);
        return deadwood;
    }

    public int Score() {
        int score = 0;
        return score;
    }

    public int getSize() {
        return cards.size();
    }

    public char getSuit(int index) {
        return cards.get(index).toString().charAt(1);
    }

    public char getRank(int index) {
        return cards.get(index).toString().charAt(0);
    }

    public int getRankValue(int index) {
        int cardValue = 0;
        if (this.getRank(index) == 'm'
                || this.getRank(index) == 'n'
                || this.getRank(index) == 'o'
                || this.getRank(index) == 'p') {
            cardValue = 10;
        } else {
            cardValue = Character.getNumericValue(this.getRank(index));
        }
        return cardValue;
    }

    public int getRankValueForCheckKind(int index) {
        int cardValue = 0;
        if (this.getRank(index) == 'm') {
            cardValue = 10;
        } else if (this.getRank(index) == 'n') {
            cardValue = 11;
        } else if (this.getRank(index) == 'o') {
            cardValue = 12;
        } else if (this.getRank(index) == 'p') {
            cardValue = 13;
        } else {
            cardValue = Character.getNumericValue(this.getRank(index));
        }
        return cardValue;
    }

    public String getValue(int index) {
        String card = cards.get(index).toString();
        return card;
    }

    public static ArrayList<ArrayList<Integer>> hasKind(ArrayList<Integer> arr) {

        ArrayList<ArrayList<Integer>> result = new ArrayList<ArrayList<Integer>>();
        Collections.sort(arr);

        int indexOfSubArray = 0;
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
                    result.add(new ArrayList<Integer>());
                    /* Add the continue cards into the result array */
                    for (int k = kindCount + 1; k > 0 + 1; k--) {
                        /* Add index in the sub array */
                        result.get(indexOfSubArray).add(i - k);
                    }
                    indexOfSubArray++;
                }

                isContinue = false;
                kindCount = 0;
            }

        }

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

    public static int hasStraight(ArrayList<Integer> arr) {

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

    public static ArrayList<ArrayList<Integer>> getStraightMatchedIndex(ArrayList<Integer> arr) {

        Collections.sort(arr);

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

    public void sortCards() {
        ArrayList<String> rankValue = new ArrayList<>();
        for (int i = 0; i < this.getSize(); i++) {
            rankValue.add(this.getValue(i));
        }
        Collections.sort(rankValue);
        for (int i = 0, j = rankValue.size() - 1; j >= 0 && i < this.getSize(); i++) {
            if (this.getValue(i).equals(rankValue.get(j))) {
                this.toBack(i);
                j--;
                i = 0;
            }
        }
    }

    public void sortStraigthOrKindInHand() {
        //clone observable list to array list
        ArrayList<Integer> rankCardsInHand = new ArrayList<>();
        for (int i = 0; i < this.getSize(); i++) {
            rankCardsInHand.add(this.getRankValueForCheckKind(i));
        }

        //get straight cards to back
        for (int i = 0, j = kindInHand.size() - 1; j >= 0 && i < this.getSize(); i++) {
            if (this.getValue(i).equals(kindInHand.get(j))) {
                this.toBack(i);
                j--;
                i = 0;
            }
        }
    }

    @Override
    public String toString() {
        return cards.toString();
    }

}
