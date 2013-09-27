package ogrady.fintan.cs460a1;

/** A node represents a possible decision in the tree.
 * As these are discovered, they are added to the total tree with the 
 * total (uniform) cost to reach them included.
 * 
 * At every node, A can decide to move in one of the 4 cardinal directions,
 *  so we can just hardcode them, and just do a little sanity checking to
 *  not go through walls. An excessively large cost, perhaps.
 *  
 *  Note that multiple nodes can occur for a single (x,y) position. If A
 *   moves away and then returns to this same position, or if a different
 *   decision tree brings us to this position, this node would not be 
 *   reused.
 *  
 * @author Remulasce
 */
public class Node {
	//Where this node is on the board
	public int x, y;
	
	//Where our target is at this point in time, while we are at x,y
	public int tx, ty;
	
	//How much it cost, total, to reach this node
	public int cost;
	
	//Where we were a step before. This will chain a node to all its previous
	//	steps, so once we find a solution we can backtrack and figure out what
	//	we actually did.
	//Null means we're the root node.
	public Node parent = null;	
	
	//The next possible nodes.
	//These will be filled with non-null Nodes once they
	// are actually pursued.
	public Node N;
	public Node S;
	public Node E;
	public Node W;
}
