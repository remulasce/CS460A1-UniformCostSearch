package ogrady.fintan.cs460a1;

import java.util.Set;
import java.util.TreeSet;


public class Main {
	//If its impossible to solve, just give up.
	private static final int GIVEUP_COST = 1000;
	
	
	public static void main(String[] args) {

		
	}

	
	
	
	//DoIt!
	//This will return a Node that is on top of B at the victory condition.
	//The entire path can be determined by backtracking through all the parents
	// of the returned node, until we reach the root.
	Node findPath(int boardSize, int ax, int ay, int bx, int by) {
		
		//Make the root node; the one we start at.
		//Note that this decision has not been taken yet, just set as the next
		//	cheapest node via "cheapest"
		//We won't keep track of the actual "root", because we're going to walk
		//	the chain back up from the final node anyway.
		//We will reuse this variable instead of making a temporary one, though.
		Node cheapest = new Node();
		cheapest.x = ax; cheapest.y = ay; cheapest.tx = bx; cheapest.ty = by; cheapest.cost = 0;
		
		//Keep a sorted list of the next-best decisions we can make, so we
		//	don't have to do a big tree traversal every time we need to make
		//	a next decision.
		//Whee CS102 decisions: What datastructure to use?
		//Treeset looks good. It's strictly ordered, with constant time access
		//	to first object, and guaranteed log(n) insertion time.
		//Though we still have to log(n) remove the "cheapest" node, meaning
		//	2log(n). Hmm. There's no built-in OrderedQueue, though, and I don't
		//	want to have to roll my own just to have a constant-time pop().
		TreeSet<Node> availableDecisions = new TreeSet<Node>();
		availableDecisions.add(cheapest);
		
		
		//We keep going until the next decision would get us a win.
		//Then after we deal with that next winning decision.
		while (!IsWin(cheapest)) {
			TryTakeDecision(cheapest);
			
			cheapest = availableDecisions.first();
			availableDecisions.remove(cheapest);
		}
		
		return null;	//No path found, shouldn't really happen.
	}
	
	//Returns true if at the node, A and B are at the same place
	//	That would mean we won!
	private boolean IsWin(Node n) {
		return (n.x == n.tx && n.y == n.ty);
	}
	
	/** Executes the decision at next
	 * Fills out next's possible decisions & costs, then returns
	 *  the valid ones in the set.
	 * @param next The node that should be taken.
	 * @return The set of new nodes that can be taken as a result of this decision.
	 */
	private Set<Node> TryTakeDecision(Node next)
	
	
	
	
}
