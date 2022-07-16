# wumpus_game_AI

The objective of this projet is to create a Wumpus World program containing your AI
agent. The program should be runnable so that the player agent can compute the likelihood
of its next move being safe or risky based on the basic knowledge provided.

Minimax is used, along with an Iterative Deepening search and a 5-second Alpha-Beta pruning 
stop time. After more investigation, it was discovered that the Kalaha is a game that includes a 
board and a quantity of seeds. The board is divided into six tiny pits, known as homes, on each 
side and a large pit, known as an end zone, at either end, with each hole on a player's side 
connected to the other and both shops. 
According to the Minimax, it works by establishing a tree structure with nodes comprising of 
game-states that branch out. In Kalaha, each node can branch out into a maximum of six new 
nodes since a player has six moves to choose from, each of which changes the state of the board. 
The nodes are assigned a value that indicates their likelihood of being a winning move. 
At the start of the assignment, a server-client-based Kalaha application built in Java was given 
and has been utilized entirely for implementing the AIs. As the software runs within a java 
application, a graphical interface will appear for the user to play the game. Because Kalaha is a 
two-player game, two clients must run concurrently and connect to the server in order for the 
game to begin. The server is in charge of ensuring that none of the clients attempt to cheat. 
Two clients and the server were all running on the same machine at the same time throughout 
the program execution. Instead of registering the draw in the unusual instance of a draw, AI did 
not receive a win or loss increment. For each move, each AI was given a time constraint of 5 
seconds. 
We implemented the AI-Client by using the following features: 
• Min-Max algorithm with depth-first search implemented 
• Iterative Deepening 
• Time to AI move is 5 seconds 
• Alpha-Beta Pruning (with move ordering in order to improve pruning) 
