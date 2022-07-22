# wumpus_game_AI

The objective of this projet is to create a Wumpus World program containing your AI
agent. The program should be runnable so that the player agent can compute the likelihood
of its next move being safe or risky based on the basic knowledge provided.

The wumpus world is a NxN board with one wumpus, pits, stenches, and breezes. The pits are surrounded by stenches, and 
the wumpus is surrounded by winds that serve as warnings. The naive bayes theorem requires the player agent to compute the 
likelihood of its next move being safe or risky based on the basic knowledge provided.
To achieve this goal, the best option is to have the agent go through a self-learning process during 
the game. The probabilities of experiencing threats produced by the supplied precepts, such as 
stench, breeze, pit, and Wumpus, are utilized to construct a decision tree. The agent's movements 
are determined by a probabilistic decision tree. Throughout the game, the agent is forced to learn 
about the present momentary hazards as that the game continues. If the agent drops through into 
Wumpus, the game's status will be game-over, which means the game will come to an end.
