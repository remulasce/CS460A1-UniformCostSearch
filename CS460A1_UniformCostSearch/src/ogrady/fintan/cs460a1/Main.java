package ogrady.fintan.cs460a1;


import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;


public class Main {
	//If its impossible to solve, just give up.
	private static final int GIVEUP_COST = 1000;
	
	
	public static void main(String[] args) {

		List<String> inputLines;
		
		Path inPath = FileSystems.getDefault().getPath("input.txt");
		Path outPath = FileSystems.getDefault().getPath("output.txt");
		
		try {
			inputLines = Files.readAllLines(
					inPath,
					Charset.defaultCharset());
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		int boardSize 	= Integer.parseInt(inputLines.get(0));
		String line2	= inputLines.get(1);
		int ax			= Integer.parseInt(line2.split(" ")[0]);
		int ay			= Integer.parseInt(line2.split(" ")[1]);
		String line3	= inputLines.get(2);
		int bx			= Integer.parseInt(line3.split(" ")[0]);
		int by			= Integer.parseInt(line3.split(" ")[1]);
		
		Main m = new Main();
		Node end = m.findPath(boardSize, ax, ay, bx, by);

		if (end == null) {
			System.out.println("Could not find a path");
			return;
		}
		
		//With the end node, we can follow the parent nodes to get the
		//	path that got us here.
		//It's in reverse order, though, so we have to reverse it before
		// 	writing.
		Deque<Node> out = new ArrayDeque<Node>();
		
		out.add(end);
		Node cur = end;
		while (cur.parent != null) {
			//addFirst puts it in correct order, with root first0
			out.addFirst(cur.parent);
			cur = cur.parent;
		}
		
		//Now with a reverse-sorted queue, we iterate backwards and 
		// write to the out file 
		for (Node n : out) {
			try {
				Files.write(outPath, 
						(n.x+" "+n.y+System.getProperty("line.separator")).getBytes(),
						//Charset.defaultCharset(),
						StandardOpenOption.APPEND);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
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
		//We have to set the comparator upon creation, to set how things will get
		//	ordered upon insertion
		TreeSet<Node> availableDecisions = new TreeSet<Node>(new Comparator<Node>() {
			//Upon insertion, nodes with a lower cost should go first.
			//Upon same cost, we could do something here to make it more deterministic,
			//	but we won't bother.
			@Override
			public int compare(Node arg0, Node arg1) {
				return arg0.cost - arg1.cost;
			}
			
		});
		
		
		availableDecisions.add(cheapest);
		
		
		//We keep going until the next decision would get us a win.
		//Then after we deal with that next winning decision.
		while (!IsWin(cheapest)) {
			//Do the decision taking
			TakeDecision(cheapest, boardSize);
			
			//Add the new decision possibilities to the availablelist
			availableDecisions.addAll(cheapest.children);
			
			//Setup for the next decisions
			cheapest = availableDecisions.first();
			if (cheapest.cost > GIVEUP_COST) { 
				System.out.println("Could not find a solution, gave up");
				return null;
			}
			availableDecisions.remove(cheapest);
		}
		
		return cheapest;
		
	}
	
	//Returns true if at the node, A and B are at the same place
	//	That would mean we won!
	private boolean IsWin(Node n) {
		return (n.x == n.tx && n.y == n.ty);
	}
	
	/** Executes the decision at next
	 * Fills out next's possible decisions & costs
	 * Check the new possibilities by just accessing next's
	 * children
	 * @param next The node that should be taken.
	 * 
	 */
	private void TakeDecision(Node next, int boardSize) {
		//Move left
		if (next.x > 1) {
			//Note: the Node(node) constructor already has copied
			// most of next's data, including setting next as parent.
			Node westNode 	= new Node(next);
			westNode.cost 	= next.cost + 4;
			westNode.x 		= next.x  - 1;
			westNode.ty 	= next.ty - 1;
			next.children	.add(westNode);
		}
		//Move right
		if (next.x < boardSize) {
			Node eastNode	= new Node(next);
			eastNode.cost 	= next.cost + 5;
			eastNode.x		= next.x  + 1;
			eastNode.ty		= next.ty + 1;
			next.children	.add(eastNode);
		}
		//Move up
		if (next.y > 1) {
			Node northNode	= new Node(next);
			northNode.cost	= next.cost + 3;
			northNode.y		= next.y  - 1;
			northNode.tx	= next.tx + 1;
			next.children	.add(northNode);
		}
		if (next.y < boardSize) {
			Node southNode	= new Node(next);
			southNode.cost	= next.cost + 6;
			southNode.y 	= next.y  + 1;
			southNode.tx	= next.tx - 1;
			next.children	.add(southNode);
		}
		
		
	}
		
	
	
	
}
