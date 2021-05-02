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
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class Player {

    private ObservableList<Node> deadwoodCards;
    private ObservableList<Node> straightCards;
    private ObservableList<Node> kindCards;
    ArrayList<Integer> clubs = new ArrayList<>();
    ArrayList<Integer> diamonds = new ArrayList<>();
    ArrayList<Integer> hearts = new ArrayList<>();
    ArrayList<Integer> spades = new ArrayList<>();
    ArrayList<ArrayList<Integer>> kindInHand = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> clubsStraightInHand = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> diamondsStraightInHand = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> heartsStraightInHand = new ArrayList<ArrayList<Integer>>();
    ArrayList<ArrayList<Integer>> spadesStraightInHand = new ArrayList<ArrayList<Integer>>();

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

    public void takeKindCard(Card card) {
        kindCards.add(card);
    }

    public void takeStraightCard(Card card) {
        straightCards.add(card);
    }

    public void DeadwoodToBack(int index) {
        deadwoodCards.get(index).toBack();
    }

    public Node getDeadwoodCardNode(int index) {
        return deadwoodCards.get(index);
    }

    public Node getStraightCardNode(int index) {
        return straightCards.get(index);
    }

    public Node getKindCardNode(int index) {
        return kindCards.get(index);
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
        return deadwood;
    }

    public int Score() {
        int score = 0;
        return score;
    }

    public int getDeadwoodSize() {
        return deadwoodCards.size();
    }

    public int getAllSize() {
        return deadwoodCards.size() + kindCards.size() + straightCards.size();
    }

    public char getDeadwoodSuit(int index) {
        return deadwoodCards.get(index).toString().charAt(1);
    }

    public static char getSuit(ObservableList<Node> cards, int index) {
        return cards.get(index).toString().charAt(1);
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

    public static int getRankValueForCheckKind(ObservableList<Node> cards, int index) {
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

    public static ArrayList<Integer> addRankToIntArraylistByCompare(ObservableList<Node> cardsRank, ArrayList<Integer> cardsIndex) {
        //convert rank in deadwoodcards to integer
        ArrayList<Integer> cardsList = new ArrayList<>();
        for (int i = 0; i < cardsIndex.size(); i++) {
            cardsList.add(getRankValueForCheckKind(cardsRank, cardsIndex.get(i)));
        }
        return cardsList;
    }

    public static int findIndexByString(ObservableList<Node> cards, String card) {
        for (int i = 0; i < cards.size(); i++) {
            if (card.equals(cards.get(i).toString())) {
                return i;
            }
        }
        return -1;
    }

    public static ArrayList<String> convertObListToArList(ObservableList<Node> cards) {
        ArrayList<String> cardsAll = new ArrayList<String>();
        for (int i = 0; i < cards.size(); i++) {
            cardsAll.add(cards.get(i).toString());
        }
        return cardsAll;
    }

    public static void sortCardsByRank(ObservableList<Node> card) {
        //sort card from rank before suit
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

    public static void sortCardsForStraight(ObservableList<Node> card) {
        //sort card from suit before suit
        ArrayList<String> cardSorted = new ArrayList<>();
        for (int i = 0; i < card.size(); i++) {
            cardSorted.add(card.get(i).toString());
        }
        Collections.sort(cardSorted);
        Collections.sort(cardSorted, (String a, String b) -> {
            return Character.toString(a.charAt(1)).compareTo(Character.toString((b.charAt(1))));
        });
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

    public void addStraightCardToOwnSuit() {
        clubs.clear();
        diamonds.clear();
        hearts.clear();
        spades.clear();
        for (int i = 0; i < straightCards.size(); i++) {
            if (getSuit(straightCards, i) == 'c') {
                clubs.add(i);
            } else if (getSuit(straightCards, i) == 'd') {
                diamonds.add(i);
            } else if (getSuit(straightCards, i) == 'h') {
                hearts.add(i);
            } else if (getSuit(straightCards, i) == 's') {
                spades.add(i);
            }
        }
    }

    public void sortDeadwoodCards() {

        //sort card in deadwood card
        sortCardsByRank(deadwoodCards);

        //CHECK KIND
        kindInHand = getKindIndex(addRankToIntArraylist(deadwoodCards));

        if (kindInHand != null) {
            //add card from deadwood to kind observable list
            for (int i = kindInHand.size() - 1; i >= 0; i--) {
                for (int index = kindInHand.get(i).size() - 1; index >= 0; index--) {
                    kindCards.add(deadwoodCards.get(kindInHand.get(i).get(index)));
                }
            }
            sortCardsByRank(kindCards);
        }

        //CHECK STRAIGHT
        addDeadwoodCardToOwnSuit();

        //check straight in club
        if (clubs.size() >= 3) {
            clubsStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(deadwoodCards, clubs));
            if (clubsStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = clubsStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = clubsStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        straightCards.add(deadwoodCards.get(clubs.get(clubsStraightInHand.get(i).get(index))));
                    }
                }
            }
            addDeadwoodCardToOwnSuit();
        }

        //check straight in diamonds
        if (diamonds.size() >= 3) {
            diamondsStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(deadwoodCards, diamonds));
            if (diamondsStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = diamondsStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = diamondsStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        straightCards.add(deadwoodCards.get(diamonds.get(diamondsStraightInHand.get(i).get(index))));
                    }
                }
            }
            addDeadwoodCardToOwnSuit();
        }

        //check straight in hearts
        if (hearts.size() >= 3) {
            heartsStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(deadwoodCards, hearts));
            if (heartsStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = heartsStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = heartsStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        straightCards.add(deadwoodCards.get(hearts.get(heartsStraightInHand.get(i).get(index))));
                    }
                }
            }
            addDeadwoodCardToOwnSuit();
        }

        //check straight in spades
        if (spades.size() >= 3) {
            spadesStraightInHand = getStraightIndex(addRankToIntArraylistByCompare(deadwoodCards, spades));
            if (spadesStraightInHand != null) {
                //add card from deadwood to kind observable list
                for (int i = spadesStraightInHand.size() - 1; i >= 0; i--) {
                    for (int index = spadesStraightInHand.get(i).size() - 1; index >= 0; index--) {
                        straightCards.add(deadwoodCards.get(spades.get(spadesStraightInHand.get(i).get(index))));
                    }
                }
            }
            addDeadwoodCardToOwnSuit();
        }
        sortCardsForStraight(straightCards);
        sortCardsByRank(kindCards);
    }

    public int checkUpcardTake(UpCard upcard) {

        int sameRank = 0;
        boolean isFourth = false;
        boolean isStraight = false;
        ArrayList<ArrayList<Integer>> hasStraight = new ArrayList<ArrayList<Integer>>();
        int upcardIndex = upcard.getSize() - 1;

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
        } else if (sameRank >= 2) {
            return 0;// keep in deadwood
        } else if (hasStraight != null) {
            if (!hasStraight.isEmpty()) {
                return 0; // keep in deadwood
            }
        }

        return -1;
    }

    public ArrayList<ArrayList<Integer>> getClubsStraightInHand() {
        return clubsStraightInHand;
    }

    public ArrayList<ArrayList<Integer>> getDiamondsStraightInHand() {
        return diamondsStraightInHand;
    }

    public ArrayList<ArrayList<Integer>> getHeartsStraightInHand() {
        return heartsStraightInHand;
    }

    public ArrayList<ArrayList<Integer>> getSpadesStraightInHand() {
        return spadesStraightInHand;
    }

    @Override
    public String toString() {
        return deadwoodCards.toString();
    }

}
