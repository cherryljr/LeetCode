/*
In a deck of cards, every card has a unique integer.  You can order the deck in any order you want.
Initially, all the cards start face down (unrevealed) in one deck.
Now, you do the following steps repeatedly, until all cards are revealed:

Take the top card of the deck, reveal it, and take it out of the deck.
If there are still cards in the deck, put the next top card of the deck at the bottom of the deck.
If there are still unrevealed cards, go back to step 1.  Otherwise, stop.
Return an ordering of the deck that would reveal the cards in increasing order.

The first entry in the answer is considered to be the top of the deck.

Example 1:
Input: [17,13,11,2,3,5,7]
Output: [2,13,3,11,5,17,7]
Explanation:
We get the deck in the order [17,13,11,2,3,5,7] (this order doesn't matter), and reorder it.
After reordering, the deck starts as [2,13,3,11,5,17,7], where 2 is the top of the deck.
We reveal 2, and move 13 to the bottom.  The deck is now [3,11,5,17,7,13].
We reveal 3, and move 11 to the bottom.  The deck is now [5,17,7,13,11].
We reveal 5, and move 17 to the bottom.  The deck is now [7,13,11,17].
We reveal 7, and move 13 to the bottom.  The deck is now [11,17,13].
We reveal 11, and move 17 to the bottom.  The deck is now [13,17].
We reveal 13, and move 17 to the bottom.  The deck is now [17].
We reveal 17.
Since all the cards revealed are in increasing order, the answer is correct.

Note:
    1. 1 <= A.length <= 1000
    2. 1 <= A[i] <= 10^6
    3. A[i] != A[j] for all i != j
 */

/**
 * Approach: Simulate The Process with A Queue
 * 1. Sort the deck, it is actually the "final sequence" we want to get according to the question.
 * 2. Then put it back to the result array, we just need to deal with the index now!
 * 3. Simulate the process with a queue (initialized with 0,1,2...(n-1)), now how do we pick the card?
 * 4. We first pick the index at the top: rst[q.poll()] = deck[i]
 * 5. Then we put the next index to the bottom: q.add(q.poll());
 * 6. Repeat it n times, and you will have the result array!
 *
 * Let's walk through the example:
 *    Input: [17,13,11,2,3,5,7]
 *    Output: [2,13,3,11,5,17,7]
 * 1. Sort the deck: [2,3,5,7,11,13,17], this is the increasing order we want to generate
 * 2. Initialize the queue: [0,1,2,3,4,5,6], this is the index of the result array
 * 3. The first card we pick is rst[0], observe the deck, it should be deck[0]==2, so assign rst[0]=2
 * 4. Then we put rst[1] to the bottom, so we re-insert 1 to the queue at the end
 * 5. The second card we pick is rst[2], which should be deck[1]==3, so assign rst[2]=3
 * 6. Then we re-insert 3 to the queue at the end
 * 7. Each time we assign 1 value to the rst, so we repeat this n times.
 *
 * Time Complexity: O(nlogn)
 * Space Complexity: O(n)
 */
class Solution {
    public int[] deckRevealedIncreasing(int[] deck) {
        int n = deck.length;
        Queue<Integer> index = new LinkedList<>();
        for (int i = 0; i < n; i++) {
            index.add(i);
        }

        Arrays.sort(deck);
        int[] rst = new int[n];
        for (int card : deck) {
            // assign one value to the rst each time
            rst[index.poll()] = card;
            // do the simulation (remove the next value to the queue's end)
            if (!index.isEmpty()) {
                index.add(index.poll());
            }
        }
        return rst;
    }
}