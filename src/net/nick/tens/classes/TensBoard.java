package net.nick.tens.classes;

import java.util.List;

/**
 * The ElevensBoard class represents the board in a game of Elevens.
 */
public class TensBoard extends Board {

	//instance variables
	/**
	 * The size (number of cards) on the board.
	 */
	private static final int BOARD_SIZE = 13;

	/**
	 * The ranks of the cards for this game to be sent to the deck.
	 */
	private static final String[] RANKS =
			{"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};

	/**
	 * The suits of the cards for this game to be sent to the deck.
	 */
	private static final String[] SUITS =
			{"Spades", "Hearts", "Diamonds", "Clubs"};

	/**
	 * The values of the cards for this game to be sent to the deck.
	 */
	private static final int[] POINT_VALUES =
			{1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0, 0, 0};


	//constructor for board

	/**
	 * Creates a new <code>ElevensBoard</code> instance.
	 */
	public TensBoard() {
		super(BOARD_SIZE, RANKS, SUITS, POINT_VALUES);
	}

	//checks if move is legal

	/**
	 * Determines if the selected cards form a valid group for removal.
	 * In Elevens, the legal groups are (1) a pair of non-face cards
	 * whose values add to 11, and (2) a group of three cards consisting of
	 * a jack, a queen, and a king in some order.
	 * @param selectedCards the list of the indices of the selected cards.
	 * @return true if the selected cards form a valid group for removal;
	 *         false otherwise.
	 */
	@Override
	public boolean isLegal(List<Integer> selectedCards) {
		if (selectedCards.size() == 2) {
			return containsPairSum10(selectedCards);
		} else if (selectedCards.size() == 4) {
			System.out.println("seeing 4");
			return containsFour(selectedCards);
		} else {
			return false;
		}
	}

	/**
	 * Determine if there are any legal plays left on the board.
	 * In Elevens, there is a legal play if the board contains
	 * (1) a pair of non-face cards whose values add to 11, or (2) a group
	 * of three cards consisting of a jack, a queen, and a king in some order.
	 * @return true if there is a legal play left on the board;
	 *         false otherwise.
	 */
	@Override
	public boolean anotherPlayIsPossible() {
		List<Integer> cIndexes = cardIndexes();
		return containsFour(cIndexes) || containsPairSum10(cIndexes);
	}

	//cheskc if 10 sum is possibe

	/**
	 * Check for an 11-pair in the selected cards.
	 *
	 * @param selectedCards selects a subset of this board.  It is list
	 *                      of indexes into this board that are searched
	 *                      to find an 11-pair.
	 * @return true if the board entries in selectedCards
	 * contain an 11-pair; false otherwise.
	 */
	private boolean containsPairSum10(List<Integer> selectedCards) {
		for (int sk1 = 0; sk1 < selectedCards.size(); sk1++) {
			int k1 = selectedCards.get(sk1).intValue();
			for (int sk2 = sk1 + 1; sk2 < selectedCards.size(); sk2++) {
				int k2 = selectedCards.get(sk2).intValue();
				if (cardAt(k1).pointValue() + cardAt(k2).pointValue() == 10) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Check for a JQK in the selected cards.
	 * @param selectedCards selects a subset of this board.  It is list
	 *                      of indexes into this board that are searched
	 *                      to find a JQK group.
	 * @return true if the board entries in selectedCards
	 *              include a jack, a queen, and a king; false otherwise.
	 */
//	private boolean containsJQK(List<Integer> selectedCards) {
//		boolean foundJack = false;
//		boolean foundQueen = false;
//		boolean foundKing = false;
//		for (Integer kObj : selectedCards) {
//			int k = kObj.intValue();
//			if (cardAt(k).rank().equals("jack")) {
//				foundJack = true;
//			} else if (cardAt(k).rank().equals("queen")) {
//				foundQueen = true;
//			} else if (cardAt(k).rank().equals("king")) {
//				foundKing = true;
//			}
//		}
//		return foundJack && foundQueen && foundKing;
//	}

	//checks to see if there is a possible combination of 4
	private boolean containsFour(List<Integer> selectedCards) {
		int Jcnt = 0;
		int Qcnt = 0;
		int Kcnt = 0;
		int Tcnt = 0;
		for (Integer kObj : selectedCards) {
			int k = kObj.intValue();
			if (cardAt(k).rank().equals("Jack")) {
				Jcnt++;
			} else if (cardAt(k).rank().equals("Queen")) {
				Qcnt++;
			} else if (cardAt(k).rank().equals("10")) {
				Tcnt++;
			} else if (cardAt(k).rank().equals("King")) {
				Kcnt++;
			}
		}
		return (Jcnt == 4 || Qcnt == 4 || Kcnt == 4 || Tcnt == 4);
	}
}
