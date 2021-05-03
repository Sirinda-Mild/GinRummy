package code;

public class LayoffCard implements Comparable {

    Integer value;
    String suit = "";

    public LayoffCard(int value, String suit) {
        this.value = value;
        this.suit = suit;

    }

    public String getSuit() {
        return suit;
    }

    /* Get DeadwoodRank */
    public Integer getValue() {
        return value;
    }

    public int getScore() {
        int score = 0;
        if (this.getValue() <= 10)
            score = this.getValue();
        else if (this.getValue() > 10)
            score = 10;
        return score;
    }

    @Override
    public int compareTo(Object o) {
        return this.getValue().compareTo(((LayoffCard) o).getValue());
    }

    @Override
    public String toString() {
        return "value:" + this.getValue() + " suit:" + this.getSuit();
    }

}