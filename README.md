## Max-Connect-4

Author: Jacob Wilkins

Language: Java

### Structure:
The code controlling the gameboard and all the actions of the gameboard is stored in the GameBoard class. The main control flow of the program is controlled by the maxconnect4 class. An instance of GameBoard is created in maxconnect4 and is used for the rest of the execution. The AIPlayer and Move classes are responsible for determining the best move for the computer using depth limited alpha-beta pruning minimax. The Move class simply keeps track of which moves correspond to which score.

### Compile:
javac *.java

### Run (one-move):
java maxconnect4 one-move [input_file] [output_file] [depth]
### Run (one-move) Example:
java maxconnect4 one-move input1.txt output1.txt 10
### Run (interactive):
java maxconnect4 interactive [input_file] [computer-next/human-next] [depth]
### Run (interactive) Example: 
java maxconnect4 interactive input1.txt computer-next 10

### Description of Classes:
#### AiPlayer class:
In the AiPlayer class, there is a function called findBestPlay() which takes the current gameboard, the max depth, and the computer number. This function returns an integer representing which column the computer chose. The computer's choice is determined by the minimax function unless minimax return an invalid move. In this case, a random valid move is chosen.

#### minimax function:
This function calls another function called minimax that takes the max depth, current depth of search, current gameboard, computer number, alpha, beta, and a boolean representing max move(true) or min move(false). This function returns a Move object which stores the score and the column used to get that score. This way, I can keep track of the highest score while keeping the move information paired with it for easy access.

#### Move class:
The Move class stores moves with their corresponding scores. It uses setScore(), getScore(), setMove(), and getMove() functions to implement this.

#### eval function:
Lastly, the eval() function determines the score for a particular gameboard by getting a gameboard and the computer number as parameters and returns the score calculated for that gameboard. The initial score is evaluated by using the getScore() function. From here, the computer gets additional points depending on which pieces it has where. For this, I created an evaluation table to determine which points correspond to each game board position. For every piece the computer's opponent has on the board, the corresponding points are subtracted from the total score. This is how each move is scored. In the evaluation table, more points are given for pieces that are closer to the center of the game board. This is because there are more chances to score when the pieces are near the center.

#### minimax explanation:
In minimax, each valid move is put into an ArrayList of Integers and tested individually. The move is made, the move is stored in a variable called reply, and then the move is removed to keep the game board clean. Once it has the reply, it can test to see if the reply score is greater than the best score (for the max player). If so, the alpha value is set to the reply score. If it's evaluating for the min player, it does the opposite and tests if the best score is greater than the reply score. If so, the beta value is set to the reply score. At the end of each loop, it tests to see if alpha is greater than or equal to beta. If this is the case, it prunes and returns the best move.

#### base case:
The base case for the minimax search is determined by testing if the current depth of the search is equal to the max depth specified. When this condition is met, the function returns the score for the leaf nodes.
