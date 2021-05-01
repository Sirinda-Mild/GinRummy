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
    ArrayList<Integer> clubs = new ArrayList<>();
    ArrayList<Integer> diamonds = new ArrayList<>();
    ArrayList<Integer> hearts = new ArrayList<>();
    ArrayList<Integer> spades = new ArrayList<>();
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

    public static char getRank(ObservableList<Node> cards, int index) {
        return cards.get(index).toString().charAt(0);
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

    public int getRankValueForCheckKind(ObservableList<Node> cards, int index) {
        int cardValue = 0;
        if (getRank(cards, index) == 'm') {
            cardValue = 10;
        } else if (getRank(cards, index) == 'n') {
            cardValue = 11;
        } else if (getRank(cards, index) == 'o') {
            cardValue = 12;
        } else if (getRank(cards, index) == 'p') {
            cardValue = 13;
        } else {
            cardValue = Character.getNumericValue(getRank(cards, index));
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

    public ArrayList<Integer> addRankToIntArraylist(ObservableList<Node> cards) {
        //convert rank in deadwoodcards to integer
        ArrayList<Integer> cardsList = new ArrayList<>();
        for (int i = 0; i < cards.size(); i++) {
            cardsList.add(getRankValueForCheckKind(cards, i));
        }
        return cardsList;
    }

    public ArrayList<Integer> addRankToIntArraylistByCompare(ObservableList<Node> cardsRank, ArrayList<Integer> cardsIndex) {
        System.out.println("kao add jaa");
        //convert rank in deadwoodcards to integer
        ArrayList<Integer> cardsList = new ArrayList<>();
        for (int i = 0; i < cardsIndex.size(); i++) {
            System.out.println("kao for laew" + i);
            cardsList.add(getRankValueForCheckKind(cardsRank, cardsIndex.get(i)));
            System.out.println(cardsList);
        }
        System.out.println("return dai jaa");
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

    public void addDeadwoodCardToOwnSuit() {
        clubs.clear();
        diamonds.clear();
        hearts.clear();
        spades.clear();
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
    }

    public void sortDeadwoodCards() {
        //sort card in deadwood card
        sortCards(deadwoodCards);

        //CHECK KIND
        kindInHand = getKindIndex(addRankToIntArraylist(deadwoodCards));

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
        addDeadwoodCardToOwnSuit();
        
        //check straight in club
        if (clubs.size() >= 3) {
            ArrayList< ArrayList<Integer>> clubsStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(deadwoodCards, clubs));
            if (clubsStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = clubsStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = clubsStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        straightCards.add(deadwoodCards.get(clubs.get(clubsStraightInHand.get(i).get(index))));
                    }
                }
                sortCards(straightCards);
            }
            addDeadwoodCardToOwnSuit();
        }

        //check straight in diamonds
        if (diamonds.size() >= 3) {
            ArrayList< ArrayList<Integer>> diamondsStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(deadwoodCards, diamonds));
            if (diamondsStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = diamondsStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = diamondsStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        straightCards.add(deadwoodCards.get(diamonds.get(diamondsStraightInHand.get(i).get(index))));
                    }
                }
                sortCards(straightCards);
            }
            addDeadwoodCardToOwnSuit();
        }

        //check straight in hearts
        if (hearts.size() >= 3) {
            ArrayList< ArrayList<Integer>> heartsStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(deadwoodCards, hearts));
            if (heartsStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = heartsStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = heartsStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        straightCards.add(deadwoodCards.get(hearts.get(heartsStraightInHand.get(i).get(index))));
                    }
                }
                sortCards(straightCards);
            }
            addDeadwoodCardToOwnSuit();
        }

        //check straight in spades
        if (spades.size() >= 3) {
            System.out.println("deadwood cards : " + deadwoodCards);
            System.out.println("straight cards : " + straightCards);
            ArrayList< ArrayList<Integer>> spadesStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(deadwoodCards, spades));
            if (spadesStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = spadesStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = spadesStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        straightCards.add(deadwoodCards.get(spades.get(spadesStraightInHand.get(i).get(index))));
                    }
                }
                sortCards(straightCards);
            }
            addDeadwoodCardToOwnSuit();
        }
    }

    @Override
    public String toString() {
        return deadwoodCards.toString();
    }

}
