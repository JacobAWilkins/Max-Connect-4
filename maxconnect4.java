/*H****************************************************************
* FILENAME :        maxconnect4.java
*
* DESCRIPTION :
*       Controls the flow of the Max Connect 4 game
*
* NOTES :
*       There are two game modes;
*       interactive and one-move.
*
*       Copyright 2019, Jacob Wilkins.  All rights reserved.
* 
* AUTHOR :    Jacob Wilkins        START DATE :    4 Mar 19
*
*H*/

import java.io.*;
import java.util.Scanner;

public class maxconnect4 {
	public static void main(String[] args) {
		if (args.length != 4) {
			System.out.println("Four command-line arguments are needed:\n" + "Usage: java [program name] interactive [input_file] [computer-next / human-next] [depth]\n" + " or:  java [program name] one-move [input_file] [output_file] [depth]\n");
			
			exit_function(0);
		}
		
		String game_mode = args[0].toString();
		String input = args[1].toString();
		String nextPlayer = args[2].toString();
		int maxDepth = Integer.parseInt(args[3]);
		GameBoard currentGame = new GameBoard(input);
		AiPlayer calculon = new AiPlayer();
		int playColumn = 99;
		boolean playMade = false;
		int computerNum;
		
		if (nextPlayer.equalsIgnoreCase("computer-next")) {
			computerNum = 1;
		} else {
			computerNum = 2;
		}
		
		/// interactive mode ///
		if (game_mode.equalsIgnoreCase("interactive")) {
			System.out.print("\nMaxConnect-4 game\n");
			System.out.print("game state before move:\n");
			
			currentGame.printGameBoard();
			System.out.println("Score: Player 1 = " + currentGame.getScore(1) + ", Player 2 = " + currentGame.getScore(2) + "\n");
			
			while (currentGame.getPieceCount() < 42) {
				if (nextPlayer.equalsIgnoreCase("computer-next")) {
					int current_player = currentGame.getCurrentTurn();
					playColumn = calculon.findBestPlay(currentGame, maxDepth, computerNum);
					currentGame.playPiece(playColumn);
					nextPlayer = "human-next";
					
					System.out.println("move " + currentGame.getPieceCount() + ": Player " + current_player + ", column " + playColumn);
					System.out.print("game state after move:\n");
					currentGame.printGameBoard();
			
					System.out.println("Score: Player 1 = " + currentGame.getScore(1) + ", Player 2 = " + 				currentGame.getScore(2) + "\n ");
			
					currentGame.printGameBoardToFile("computer.txt");
				}
				if (nextPlayer.equalsIgnoreCase("human-next")) {
					int current_player = currentGame.getCurrentTurn();
					Scanner reader = new Scanner(System.in);
					System.out.print("Enter a column (1-7): ");
					playColumn = reader.nextInt();
					if ((playColumn > 0) && (playColumn < 8)) {
						if (currentGame.isValidPlay(playColumn - 1)) {
							currentGame.playPiece(playColumn - 1);
							nextPlayer = "computer-next";
						
							System.out.println("move " + currentGame.getPieceCount() + ": Player " + current_player + ", column " + playColumn);
							System.out.print("game state after move:\n");
							currentGame.printGameBoard();
			
							System.out.println("Score: Player 1 = " + currentGame.getScore(1) + ", Player 2 = " + 				currentGame.getScore(2) + "\n ");
			
							currentGame.printGameBoardToFile("human.txt");
						} else {
							System.out.println("\nThe moved entered is not valid.\nTry again.\n");
						}
					} else {
						System.out.println("\nThe moved entered is not valid.\nTry again.\n");
					}
				}
			}
			System.out.println("\nI can't play.\nThe Board is Full");
			
			if (currentGame.getScore(1) == currentGame.getScore(2)) {
				System.out.println("\nYou Tied!\n");
			} else if (currentGame.getScore(1) > currentGame.getScore(2)) {
				if (computerNum == 1) {
					System.out.println("\nComputer Won!\n");
				} else {
					System.out.println("\nYou Won!\n");
				}
			} else {
				if (computerNum == 2) {
					System.out.println("\nComputer Won!\n");
				} else {
					System.out.println("\nYou Won!\n");
				}
			}
			System.out.println("Game Over\n");
			
			return;
		} else if (!game_mode.equalsIgnoreCase("one-move")) {
			System.out.println("\n" + game_mode + " is an unrecognized game mode \ntry again. \n");
			return;
		}
		
		/// one-move mode ///
		String output = args[2].toString();
		System.out.print("\nMaxConnect-4 game\n");
		System.out.print("game state before move:\n");
		
		currentGame.printGameBoard();
		
		System.out.println("Score: Player 1 = " + currentGame.getScore(1) + ", Player 2 = " + currentGame.getScore(2) + "\n");
		
		if (currentGame.getPieceCount() < 42) {
			int current_player = currentGame.getCurrentTurn();
			playColumn = calculon.findBestPlay(currentGame, maxDepth, computerNum);
			currentGame.playPiece(playColumn);
			
			System.out.println("move " + currentGame.getPieceCount() + ": Player " + current_player + ", column " + playColumn);
			System.out.print("game state after move:\n");
			currentGame.printGameBoard();
			
			System.out.println("Score: Player 1 = " + currentGame.getScore(1) + ", Player 2 = " + currentGame.getScore(2) + "\n ");
			
			currentGame.printGameBoardToFile(output);
		} else {
			System.out.println("\nI can't play.\nThe Board is Full");
			
			if (currentGame.getScore(1) == currentGame.getScore(2)) {
				System.out.println("\nYou Tied!\n");
			} else if (currentGame.getScore(1) > currentGame.getScore(2)) {
				if (computerNum == 1) {
					System.out.println("\nComputer Won!\n");
				} else {
					System.out.println("\nYou Won!\n");
				}
			} else {
				if (computerNum == 2) {
					System.out.println("\nComputer Won!\n");
				} else {
					System.out.println("\nYou Won!\n");
				}
			}
			System.out.println("Game Over\n");
		}
		return;
	}
	
	private static void exit_function(int value) {
		System.out.println("exiting from MaxConnectFour.java!\n\n");
		System.exit(value);
	}
}
