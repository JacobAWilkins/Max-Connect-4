/*H****************************************************************
* FILENAME :        Move.java
*
* DESCRIPTION :
*       Stores information about a particular move 
*
* PUBLIC FUNCTIONS :
*       void     setScore( int )
*       int      getScore( )
*       void     setMove( int )
*       int      getMove( )
*
* NOTES :
*       Each move has a number representing the
*       column and a score value.
*
*       Copyright 2019, Jacob Wilkins.  All rights reserved.
* 
* AUTHOR :    Jacob Wilkins        START DATE :    4 Mar 19
*
*H*/

public class Move {
	int move;
	int score;
	
	public void setScore(int scoreSet) {
		score = scoreSet;
	}
	
	public int getScore() {
		return score;
	}
	
	public void setMove(int moveSet) {
		move = moveSet;
	}
	
	public int getMove() {
		return move;
	}
}
