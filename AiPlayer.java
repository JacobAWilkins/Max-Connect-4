import java.util.*;
import static java.lang.Integer.*;

public class AiPlayer {
	public AiPlayer() {}
	public int findBestPlay(GameBoard currentGame, int maxDepth, int computerNum) {
		Move playChoice;
		Random randy = new  Random();
		int MAX = MAX_VALUE;
		int MIN = MIN_VALUE;
		
		playChoice = minimax(currentGame, 0, maxDepth, true, MIN, MAX, computerNum);
		
		while (!currentGame.isValidPlay(playChoice.getMove())) {
			playChoice.move = randy.nextInt(7);
		}

		return playChoice.getMove();
	}
	
	// Find best move
	private Move minimax(GameBoard currentGame, final int depth, int maxDepth, final boolean player, int alpha, int beta, int computerNum) {
		final Move best = new Move();
		Move reply;
		
		// Base case
		if (depth == maxDepth) {
			final Move fakeBest = new Move();
			fakeBest.setScore(eval(currentGame, computerNum));
			return fakeBest;
		}
		
		if (player) {
			best.setScore(alpha);
		} else {
			best.setScore(beta);
		}
		
		// Make ArrayList of valid moves to consider
		ArrayList<Integer> moves = new ArrayList<Integer>();
		for (int i = 0; i < 7; i++) {
			if (currentGame.isValidPlay(i)) {
				moves.add(i);
			}
		}
		
		for (int move = 0; move < moves.size(); move++) {
			currentGame.playPiece(moves.get(move));
			reply = minimax(currentGame, depth + 1, maxDepth, !player, alpha, beta, computerNum);
			currentGame.removePiece(moves.get(move));
			if (player && reply.getScore() > best.getScore()) {
				best.setMove(moves.get(move));
				best.setScore(reply.getScore());
				alpha = reply.getScore();
			} else if (!player && reply.getScore() < best.getScore()) {
				best.setMove(moves.get(move));
				best.setScore(reply.getScore());
				beta = reply.getScore();
			}
			if (alpha >= beta) {
				return best;
			}
		}
		return best;
	}
	
	// Calculate score of game board
	private int eval(GameBoard currentGame, int computerNum) {
		int totScore = currentGame.getScore(computerNum);
		int opponentNum;
		final int[][] evaluationTable = new int[][] {
			{3, 4, 5, 7, 5, 4, 3},
			{4, 6, 8, 10, 8, 6, 4},
			{5, 8, 11, 13, 11, 8, 5},
			{5, 8, 11, 13, 11, 8, 5},
			{4, 6, 8, 10, 8, 6, 4},
			{3, 4, 5, 7, 5, 4, 3}
		};
		
		if (computerNum == 1) {
			opponentNum = 2;
		} else {
			opponentNum = 1;
		}
		
		for (int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				if (currentGame.playBoard[i][j] == computerNum) {
					totScore += evaluationTable[i][j];
				} else if (currentGame.playBoard[i][j] == opponentNum) {
					totScore -= evaluationTable[i][j];
				}
			}
		}
		return totScore;
	}
	
}
