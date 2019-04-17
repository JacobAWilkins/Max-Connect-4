import java.io.*;

public class GameBoard {
	
	public int[][] playBoard;
	private int pieceCount;
	private int currentTurn;
	
	public GameBoard(String inputFile) {
		this.playBoard = new int[6][7];
		this.pieceCount = 0;
		int counter = 0;
		BufferedReader input = null;
		String gameData = null;
		
		try {
			input = new BufferedReader(new FileReader(inputFile));
		} catch (IOException ioe) {
			System.out.println("Error opening the input file!");
			ioe.printStackTrace();
		}
		
		for(int i = 0; i < 6; i++) {
			try {
				gameData = input.readLine();
				for (int j = 0; j < 7; j++) {
					this.playBoard[i][j] = gameData.charAt(counter++) - 48;
					
					if (!(this.playBoard[i][j] == 0) || (this.playBoard[i][j] == 1) || (this.playBoard[i][j] == 2)) {
						System.out.println("Pieces other than 0, 1, or 2!");
						this.exit_function(0);
					}
							
					if (this.playBoard[i][j] > 0) {
						this.pieceCount++;
					}
				}
			} catch (Exception e) {
				System.out.println("Error reading the input file!");
				e.printStackTrace();
				this.exit_function(0);
			} 
			counter = 0;
		}
		try {
			gameData = input.readLine();
		} catch (Exception e) {
			System.out.println("Error reading the next turn!");
			e.printStackTrace();
		}
		this.currentTurn = gameData.charAt(0) - 48;
		
		if (!((this.currentTurn == 1 || this.currentTurn == 2))) {
			System.out.println("Current turn read is not 1 or 2!");
			this.exit_function(0);
		} else if (this.getCurrentTurn() != this.currentTurn) {
			System.out.println("Current turn read does not correspond to number of pieces played!");
			this.exit_function(0);
		}
	}
	
	public GameBoard(int masterGame[][]) {
		this.playBoard = new int[6][7];
		this.pieceCount = 0;
		
		for (int i = 0; i < 6; i++) {
			for(int j = 0; j < 7; j++) {
				this.playBoard[i][j] = masterGame[i][j];
				if (this.playBoard[i][j] > 0) {
					this.pieceCount++;
				}
			}
		}
	}
	
	public int getScore(int player) {
		int playerScore = 0;
		
		// Horizontal check
		for (int i = 0; i < 6; i++) {
			for(int j = 0; j < 4; j++) {
				if ((this.playBoard[i][j] == player) && (this.playBoard[i][j+1] == player) && (this.playBoard[i][j+2] == player) && (this.playBoard[i][j+3] == player)) {
					playerScore++;
				}
			}
		}
		
		// Vertical check
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 7; j++) {
				if ((this.playBoard[i][j] == player) &&
		    (this.playBoard[i+1][j] == player) &&
		    (this.playBoard[i+2][j] == player) &&
		    (this.playBoard[i+3][j] == player)) {
		    playerScore++;
				}
			}
		}
		
		// Back slash diagonal check
		for (int i = 0; i < 3; i++) {
			for(int j = 0; j < 4; j++) {
		    if((this.playBoard[ i ][ j ] == player) && 
					 (this.playBoard[ i+1 ][ j+1 ] == player) && 
					 (this.playBoard[ i+2 ][ j+2 ] == player) &&
					 (this.playBoard[ i+3 ][ j+3 ] == player)) {
					playerScore++;
		    }
			}
	  }
		
		// Forward slash diagonal check
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 4; j++) {
		    if((this.playBoard[i+3][j] == player) &&
					 (this.playBoard[i+2][j+1] == player) &&
					 (this.playBoard[i+1][j+2] == player) &&
					 (this.playBoard[i][j+3] == player)) {
					playerScore++;
		    }
			}
	  }
		return playerScore;
	}
	
	public int getCurrentTurn() {
		return (this.pieceCount % 2) + 1;
	}
	
	public int getPieceCount() {
		return this.pieceCount;
	}
	
	public int[][] getGameBoard() {
		return this.playBoard;
	}
	
	public boolean isValidPlay(int column) {
		if (!(column >= 0 && column <=7)) {
			return false;
		} else if (this.playBoard[0][column] > 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public boolean playPiece(int column) {
		if (!this.isValidPlay(column)) {
			return false;
		} else {
			for (int i = 5; i >= 0; i--) {
				if (this.playBoard[i][column] == 0) {
					if (this.pieceCount % 2 == 0) {
						this.playBoard[i][column] = 1;
						this.pieceCount++;
					} else {
						this.playBoard[i][column] = 2;
						this.pieceCount++;
					}
					return true;
				}
			}
			System.out.println("Something went wrong with playPiece()");
			return false;
		}
	}
	
	public void removePiece(int column) {
		for (int i = 0; i < 6; i++) {
			if (this.playBoard[i][column] > 0) {
				this.playBoard[i][column] = 0;
				this.pieceCount--;
				break;
			}
		}
	}
	
	public void printGameBoard() {
		System.out.println(" -----------------");
		for (int i = 0; i < 6; i++) {
			System.out.print(" | ");
			for (int j = 0; j < 7; j++) {
				System.out.print(this.playBoard[i][j] + " ");
			}
			System.out.println("| ");
		}
		System.out.println(" -----------------");
	}
	
	public void printGameBoardToFile(String outputFile) {
		try {
			BufferedWriter output = new BufferedWriter(new FileWriter(outputFile));
			
			for (int i = 0; i < 6; i++) {
				for (int j = 0; j < 7; j++) {
					output.write(this.playBoard[i][j] + 48);
				}
				output.write("\r\n");
			}
			output.write(this.getCurrentTurn() + "\r\n");
			output.close();
		} catch (IOException ioe) {
			System.out.println("Error writing to the output file!");
			ioe.printStackTrace();
		}
	}
	
	private void exit_function(int value) {
		System.out.println("Exiting from GameBoard.java!\n\n");
		System.exit(value);
	}
	
}
