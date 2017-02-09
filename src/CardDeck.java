public class CardDeck {
	private int card_count;
	private Card[] deck = new Card[4 * Card.SIZE_OF_ONE_SUIT];

	private void createSuit(String which_suit) {
		for (int i = 1; i <= Card.SIZE_OF_ONE_SUIT; i++) {
			deck[card_count] = new Card(which_suit, i);
			card_count++;
		}
	}

	public CardDeck() {
		createSuit(Card.SPADES);
		createSuit(Card.HEARTS);
		createSuit(Card.CLUBS);
		createSuit(Card.DIAMONDS);
		System.out.println(deck.toString());
	}

	public Card newCard() {
		Card next_card = null;
		if (card_count != 0) {
			int index = (int) (Math.random() * card_count);
			next_card = deck[index];
			for (int i = index + 1; i < card_count; i++)
				deck[i - 1] = deck[i];
			card_count--;
		}
		return next_card;
	}
}