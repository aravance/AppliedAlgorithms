package com.radadev.applied;

import java.io.PrintStream;
import java.util.*;

public class Cribbage extends AppliedAlgorithm {

    @SafeVarargs
    static <T> T[] concat(T[] array, T... others) {
        int length = array.length + others.length;
        T[] result = Arrays.copyOf(array, length);
        for (int i = array.length, j = 0; i < length && j < others.length; ++i, ++j) {
            result[i] = others[j];
        }
        return result;
    }

    int score(Card[] hand, Card starter) {
        Arrays.sort(hand);
        int score = 0;

        if (isFlush(hand)) score += hand[0].getSuit() == starter.getSuit() ? 5 : 4;
        if (hasJack(starter.getSuit(), hand)) score += 1;

        hand = concat(hand, starter);
        Arrays.sort(hand);

        score += 2 * countPairs(hand);
        score += 2 * countFifteens(null, hand);
        score += 3 * countStraights(3, hand);
        score += 4 * countStraights(4, hand);
        score += 5 * countStraights(5, hand);

        return score;
    }

    private int countStraights(int length, Card... hand) {

        int count = 0;

        Map<Integer, Integer> multiplier = new HashMap<>();
        List<Card> cards = new ArrayList<>();

        int value = -1;
        for (Card card : hand) {
            if (card.getStraightValue() == value) {
                if (!multiplier.containsKey(value)) {
                    multiplier.put(value, 2);
                } else {
                    multiplier.put(value, multiplier.get(value) + 1);
                }
            } else {
                if (card.getStraightValue() != value + 1) {
                    if (cards.size() == length) {
                        int c = 1;
                        for (int m : multiplier.values()) {
                            c *= m;
                        }
                        count += c;
                    }
                    multiplier.clear();
                    cards.clear();
                }
                cards.add(card);
            }
            value = card.getStraightValue();
        }
        if (cards.size() == length) {
            int c = 1;
            for (int m : multiplier.values()) {
                c *= m;
            }
            count += c;
        }
        return count;
    }

    private boolean hasJack(Suit suit, Card... cards) {
        boolean found = false;
        for (int i = 0; !found && i < cards.length; ++i) {
            found = cards[i].getFace() == 'J' && cards[i].getSuit() == suit;
        }
        return found;
    }

    private boolean isFlush(Card... hand) {
        boolean flush = true;
        Suit suit = hand[0].getSuit();
        for (int i = 1; flush && i < hand.length; ++i) {
            flush = suit == hand[i].getSuit();
        }
        return flush;
    }

    private int sumValue(Collection<Card> cards) {
        int total = 0;
        for (Card card : cards) {
            total += card.getValue();
        }
        return total;
    }

    private int countFifteens(List<Card> partial, Card... others) {
        if (partial == null) partial = new ArrayList<>();
        int sum = sumValue(partial);
        if (sum == 15) return 1;
        if (sum > 15) return 0;
        int total = 0;
        for (int i = 0; i < others.length; ++i) {
            partial.add(others[i]);
            total += countFifteens(partial, Arrays.copyOfRange(others, i + 1, others.length));
            partial.remove(others[i]);
        }
        return total;
    }

    private int countPairs(Card... hand) {

        int count = 0;

        for (int i = 0; i < hand.length; ++i) {
            for (int j = i + 1; j < hand.length; ++j) {
                if (hand[i].getFace() == hand[j].getFace()) ++count;
            }
        }
        return count;
    }

    @Override
    void execute(Scanner in, PrintStream out) {
        for (String line = in.nextLine(); !line.equals("0"); line = in.nextLine()) {
            StringTokenizer t = new StringTokenizer(line);

            Card[] hand = new Card[4];
            for (int i = 0; i < hand.length; ++i) {
                hand[i] = Card.fromString(t.nextToken());
            }
            Card starter = Card.fromString(t.nextToken());

            out.println(score(hand, starter));
        }
    }
}

enum Suit {
    Clubs,
    Hearts,
    Diamonds,
    Spades;

    public static Suit fromChar(char c) {
        switch (c) {
            case 'c':
            case 'C':
                return Clubs;
            case 'h':
            case 'H':
                return Hearts;
            case 'd':
            case 'D':
                return Diamonds;
            case 's':
            case 'S':
                return Spades;
            default:
                return null;
        }
    }

    public char toChar() {
        switch (this) {
            case Clubs:
                return 'C';
            case Hearts:
                return 'H';
            case Diamonds:
                return 'D';
            case Spades:
                return 'S';
            default:
                throw new NoSuchElementException("Suit.toChar should never have gotten here");
        }
    }
}

class Card implements Comparable<Card> {

    private Suit mSuit;
    private int mValue;

    public Card(char value, char suit) {
        switch (value) {
            case 'A':
                mValue = 1;
                break;
            case '2':
                mValue = 2;
                break;
            case '3':
                mValue = 3;
                break;
            case '4':
                mValue = 4;
                break;
            case '5':
                mValue = 5;
                break;
            case '6':
                mValue = 6;
                break;
            case '7':
                mValue = 7;
                break;
            case '8':
                mValue = 8;
                break;
            case '9':
                mValue = 9;
                break;
            case 'T':
                mValue = 10;
                break;
            case 'J':
                mValue = 11;
                break;
            case 'Q':
                mValue = 12;
                break;
            case 'K':
                mValue = 13;
                break;
            default:
                throw new IllegalArgumentException("Invalid value: " + value);
        }
        mSuit = Suit.fromChar(suit);
    }

    public Suit getSuit() {
        return mSuit;
    }

    public int getValue() {
        if (mValue > 10) {
            return 10;
        } else {
            return mValue;
        }
    }

    public int getStraightValue() {
        return mValue;
    }

    public char getFace() {
        switch (mValue) {
            case 1:
                return 'A';
            case 10:
                return 'T';
            case 11:
                return 'J';
            case 12:
                return 'Q';
            case 13:
                return 'K';
            default:
                return Character.forDigit(mValue, 10);
        }
    }

    static Card fromString(String s) {
        return new Card(s.charAt(0), s.charAt(1));
    }

    @Override
    public String toString() {
        return getFace() + "" + mSuit.toChar();
    }

    @Override
    public int compareTo(Card o) {
        int value = Integer.compare(mValue, o.mValue);
        if (value == 0) {
            value = mSuit.compareTo(o.mSuit);
        }
        return value;
    }
}
