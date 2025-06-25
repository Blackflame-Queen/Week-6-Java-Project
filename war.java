import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class war {
    // constants for suits and ranks
    private static final String[] SUITS = {"Spades (S)", "Hearts (H)", "Diamonds (D)", "Clubs (C)"};
    private static final String[] RANKS = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    
    public static void main(String[] args) {
        // create and start the game
        Game game = new Game();
        game.playGame();
    }
    
    // card class represents a single card
    static class Card {
        private String suit;
        private String rank;
        
        public Card(String suit, String rank) {
            this.suit = suit;
            this.rank = rank;
        }
        
        public int getValue() {
            return Arrays.asList(RANKS).indexOf(rank);
        }
        
        @Override
        public String toString() {
            return rank + " of " + suit;
        }
    }
    
    // deck class represents the card deck
    static class Deck {
        private ArrayList<Card> cards;
        
        public Deck() {
            cards = new ArrayList<>();
            for (String suit : SUITS) {
                for (String rank : RANKS) {
                    cards.add(new Card(suit, rank));
                }
            }
            shuffle();
        }
        
        public void shuffle() {
            Collections.shuffle(cards);
        }
        
        public Card dealCard() {
            if (cards.isEmpty()) {
                return null;
            }
            return cards.remove(cards.size() - 1);
        }
    }
    
    // player class represents a player
    static class Player {
        private String name;
        private ArrayList<Card> hand;
        private int score;
        
        public Player(String name) {
            this.name = name;
            this.hand = new ArrayList<>();
            this.score = 0;
        }
        
        public void addCard(Card card) {
            hand.add(card);
        }
        
        public Card playCard() {
            if (hand.isEmpty()) {
                return null;
            }
            return hand.remove(0); // remove and return the first card (equivalent to shift in JS)
        }
        
        public void addPoint() {
            score++;
        }
        
        public int getScore() {
            return score;
        }
        
        public int getHandSize() {
            return hand.size();
        }
        
        @Override
        public String toString() {
            return name + " (Score: " + score + ")";
        }
    }
    
    // game class contains the main game logic
    static class Game {
        private Deck deck;
        private Player player1;
        private Player player2;
        
        public Game() {
            deck = new Deck();
            player1 = new Player("Diva 1");
            player2 = new Player("Diva 2");
            dealCards();
        }
        
        private void dealCards() {
            for (int i = 0; i < 26; i++) {
                player1.addCard(deck.dealCard());
                player2.addCard(deck.dealCard());
            }
        }
        
        public void playRound() {
            Card card1 = player1.playCard();
            Card card2 = player2.playCard();
            
            System.out.println(player1.name + " plays: " + card1);
            System.out.println(player2.name + " plays: " + card2);
            
            String roundResult;
            if (card1.getValue() > card2.getValue()) {
                player1.addPoint();
                roundResult = player1.name + " wins the battle!!";
            } else if (card1.getValue() < card2.getValue()) {
                player2.addPoint();
                roundResult = player2.name + " wins the battle!!";
            } else {
                roundResult = "Tie! No one wins!!";
            }
            
            System.out.println(roundResult);
            System.out.println("Score: " + player1.getScore() + " to " + player2.getScore());
            System.out.println();
        }
        
        public void playGame() {
            System.out.println("WAR!! Game Started\n");
            
            // play until one player runs out of cards
            while (player1.getHandSize() > 0 && player2.getHandSize() > 0) {
                playRound();
                
                // add a small delay between rounds for readability
                try {
                    Thread.sleep(1250); // 1.25 seconds delay between rounds
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            
            endGame();
        }
        
        private void endGame() {
            String result;
            if (player1.getScore() > player2.getScore()) {
                result = player1.name + " wins the war!!";
            } else if (player1.getScore() < player2.getScore()) {
                result = player2.name + " wins the war!!";
            } else {
                result = "Stalemate!! lame...";
            }
            
            System.out.println("Game Over!! " + result);
        }
        
        public void resetGame() {
            player1 = new Player("Diva 1");
            player2 = new Player("Diva 2");
            deck = new Deck();
            dealCards();
            System.out.println("Game Reset\n");
            playGame();
        }
    }
}