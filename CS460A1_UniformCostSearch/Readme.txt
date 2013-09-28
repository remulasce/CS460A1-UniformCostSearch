Fintan O'Grady
5209598270
fogrady@usc.edu

Build Instructions:

From within A1 folder:
Compile:
javac ogrady/fintan/cs460a1/*.java
Run:
java ogrady/fintan/cs460a1/*.java

input.txt and output.txt will be searched at the same level the ogrady directory, and readme
are located.

1. The size of the state space is n^4: The "A" has freedom of direction in two axes, x+y, 
and "B" has freedom in X+Y as well.
2. There is not always a solution. If A starts in the top-left corner, and B in the 
bottom-left corner, then there are no available moves that do not put either A or B 
out of bounds.


Program Structure:
Main execution takes place in the "Main" class, under package ogrady.fintan.cs460a1
A Node class is included as well for the decision tree.

Execution begins by reading input, creating a Main object, and calling its findPath(...) 
function.
This executes the main AI component of the program, and returns a Node.
To prevent infinite loop, there is a GIVEUP_COST defined at the top of main. If the cheapest
available decision is more expensive than GIVEUP_COST, then the program will give up and write
"-1" to file.

A single Node represents a possible state of the board, including A position, B position,
the cost it takes to get to that state, and the possible Nodes that can be reached immediately
after this node.
A Node also has a referenc to its parent- the state that was immediately before this Node.

findPath returns the Node that represents the final state of the game, when A and B are
at the same location.
By following the Node's parent, and its parent and so on, the entire sequence of events
can be recreated using only this final node.


FindNode works by continually executing the next Node with the least cost.
It keeps an ordered Tree of all possible decisions that have not been explored yet
The order is by lowest cost
Every iteration, findPath simply takes the head of the tree, which should always be the
cheapest next move, and executes it in TakeDecision()
TakeDecision takes the Node and fills in its next possible decisions- usually the cardinal
directions, minus any edges. It also fills in the total cost of those next decisions.

Once a Node has been Taken, it is removed from the AvailableDecision tree, and the new nodes
that can be reached, filled in by TakeDecision(), are added to the availableDecision tree.

The tree maintains its order by using its Comparator, which is defined anonymously when
the tree is initialized. The Comparator simply determines whether a node is less than (before)
or greater than (after) another node, by returning their relative costs.


When a possible decision yields a state with A and B in the same place, the main loop
is broken, and that possible decision (Node) which would yield victory is returned.
The static main function then steps back through that Node's parents until it reaches
the root to determine the actual cheapest steps.