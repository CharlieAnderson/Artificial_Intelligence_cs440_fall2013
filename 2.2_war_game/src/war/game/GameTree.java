package war.game;

import war.game.Main.Agent;
import war.game.Main.Move;

public class GameTree {
    
	public long maxNodes = 0;
    
    public int minimax(Agent ag, GameTreeNode node, boolean getMax, int maxDepth, int moves) {
//    	System.out.println("Depth: "+node.depth);
    	Agent enemy;
    	if (ag == Agent.BLUE) {
    		enemy = Agent.GREEN;
    	} else {
    		enemy = Agent.BLUE;
    	}
    	
    	maxNodes++;

    	if ((node.depth >= maxDepth) || ((node.wg.size*node.wg.size) == moves)) {
//    		System.out.println("At max depth" +node.depth);
    		// No more recursion. This is a leaf node. 
    		node.value = calculateScore(enemy, !getMax, node);
//    		System.out.println(node.value);
    		return node.value;
    	}
    	
    	int currentMinMaxValue = -1;
    	Move currentMinMaxMove = Move.NONE;
    	int currentMinMaxRow = -1;
    	int currentMinMaxCol = -1;
//    	System.out.println("Printing the node's map");
//    	node.wg.printOccupiedMap();
    	for (int i = 0; i < node.wg.map.length; i++) {
    		for (int j = 0; j < node.wg.map[i].length; j++) {
    			Move mv = checkWhichMove(ag, node, i, j);
    			if (mv == Move.NONE) {
    				continue;
    			}
    	    	GameTreeNode child = new GameTreeNode(node, false);
    	    	child.move = mv;
    	    	child.moveRow = i;
    	    	child.moveCol = j;
    			child = performMove(ag, enemy, child);
//    			System.out.println("Printing the child's map");
//    			child.wg.printOccupiedMap();
//    			System.out.println("Printing the child parent's map");
//    			child.parent.wg.printOccupiedMap();
//    			System.out.println("Node's value: "+ node.value);
    			int value = minimax(enemy, child, !getMax, maxDepth, moves+1);
				if (currentMinMaxValue == -1 || (getMax && currentMinMaxValue < value) || (!getMax && currentMinMaxValue > value)) {
//					if (currentMinMax != null) {
//						System.out.println("Setting child because its value is " + child.value + " which is larger/smaller(true/false)[" + getMax + "] than " + currentMinMax.value);
//					}
					currentMinMaxValue = value;
	    	    	currentMinMaxMove = mv;
	    	    	currentMinMaxRow = i;
	    	    	currentMinMaxCol = j;
				}
    		}
    	}
    	
//    	System.out.println("Returning from non-leaf");
    	
    	if (currentMinMaxValue != -1) {
	    	node.value = currentMinMaxValue;
	    	if (node.depth == 0) {
		    	node.move = currentMinMaxMove;
		    	node.moveRow = currentMinMaxRow;
		    	node.moveCol = currentMinMaxCol;
	    		node = performMove(ag, enemy, node);
	    	}
    	} else {
    		System.out.println("Something weird happened");
    	}
    	
    	return currentMinMaxValue;
    }
    
    public int alpha_beta(Agent ag, GameTreeNode node, boolean getMax, int maxDepth, int moves, int alpha, int beta) {
//    	System.out.println("Depth: "+node.depth);
    	Agent enemy;
    	if (ag == Agent.BLUE) {
    		enemy = Agent.GREEN;
    	} else {
    		enemy = Agent.BLUE;
    	}
    	
    	maxNodes++;

    	if ((node.depth >= maxDepth) || ((node.wg.size*node.wg.size) == moves)) {
//    		System.out.println("At max depth" +node.depth);
    		// No more recursion. This is a leaf node. 
    		node.value = calculateScore(enemy, !getMax, node);
//    		System.out.println(node.value);
    		return node.value;
    	}
    	
    	int currentMinMaxValue;
    	if (getMax) {
    		currentMinMaxValue = Integer.MIN_VALUE;
    	} else {
    		currentMinMaxValue = Integer.MAX_VALUE;
    	}
    	Move currentMinMaxMove = Move.NONE;
    	int currentMinMaxRow = -1;
    	int currentMinMaxCol = -1;
//    	System.out.println("Printing the node's map");
//    	node.wg.printOccupiedMap();
    	for (int i = 0; i < node.wg.map.length; i++) {
    		for (int j = 0; j < node.wg.map[i].length; j++) {
    			Move mv = checkWhichMove(ag, node, i, j);
    			if (mv == Move.NONE) {
    				continue;
    			}
    	    	GameTreeNode child = new GameTreeNode(node, false);
    	    	child.move = mv;
    	    	child.moveRow = i;
    	    	child.moveCol = j;
    			child = performMove(ag, enemy, child);
//    			System.out.println("Printing the child's map");
//    			child.wg.printOccupiedMap();
//    			System.out.println("Printing the child parent's map");
//    			child.parent.wg.printOccupiedMap();
//    			System.out.println("Node's value: "+ node.value);
    			int value = alpha_beta(enemy, child, !getMax, maxDepth, moves+1, alpha, beta);
				if ((getMax && currentMinMaxValue < value) || (!getMax && currentMinMaxValue > value)) {
					currentMinMaxValue = value;
	    	    	currentMinMaxMove = mv;
	    	    	currentMinMaxRow = i;
	    	    	currentMinMaxCol = j;
				}
    			if (getMax) {
    				if (currentMinMaxValue >= beta) {
    					return currentMinMaxValue;
    				}
    				alpha = Math.max(currentMinMaxValue, alpha);
    			} else {
    				if (currentMinMaxValue <= alpha) {
    					return currentMinMaxValue;
    				}
    				beta = Math.min(currentMinMaxValue, beta);
    			}
    		}
    	}
    	
//    	System.out.println("Returning from non-leaf");
    	
    	node.value = currentMinMaxValue;
    	if (node.depth == 0) {
	    	node.move = currentMinMaxMove;
	    	node.moveRow = currentMinMaxRow;
	    	node.moveCol = currentMinMaxCol;
    		node = performMove(ag, enemy, node);
    	}
    	
    	return currentMinMaxValue;
    }

    public Move checkWhichMove(Agent ag, GameTreeNode node, int row, int col) {
    	if (node.wg.occupied[row][col] != Agent.NEUTRAL) {
    		return Move.NONE;
    	} else {
    		if (row != 0) {
    			if (node.wg.occupied[row-1][col] == ag) {
    				return Move.BLITZ;
    			}
    		}
    		if (row < node.wg.map.length-1) {
    			if (node.wg.occupied[row+1][col] == ag) {
    				return Move.BLITZ;
    			}
    		}
    		if (col != 0) {
    			if (node.wg.occupied[row][col-1] == ag) {
    				return Move.BLITZ;
    			}
    		}
    		if (col < node.wg.map[row].length-1) {
    			if (node.wg.occupied[row][col+1] == ag) {
    				return Move.BLITZ;
    			}
    		}
    	}
    	return Move.PARADROP;
    }
    
    public GameTreeNode performMove(Agent ag, Agent enemy, GameTreeNode node) {
    	Move mv = node.move;
    	int row = node.moveRow;
    	int col = node.moveCol;

		node.wg.occupied[row][col] = ag;
    	if (mv == Move.BLITZ) {
    		if (row != 0) {
    			if (node.wg.occupied[row-1][col] == enemy) {
    				node.wg.occupied[row-1][col] = ag;
    			}
    		}
    		if (row < node.wg.map.length-1) {
    			if (node.wg.occupied[row+1][col] == enemy) {
    				node.wg.occupied[row+1][col] = ag;
    			}
    		}
    		if (col != 0) {
    			if (node.wg.occupied[row][col-1] == enemy) {
    				node.wg.occupied[row][col-1] = ag;
    			}
    		}
    		if (col < node.wg.map[row].length-1) {
    			if (node.wg.occupied[row][col+1] == enemy) {
    				node.wg.occupied[row][col+1] = ag;
    			}
    		}
    	}
    	
    	return node;
    }
    
    public int calculateScore(Agent req_ag, boolean req_getMax, GameTreeNode node) {
    	int greenScore = 0;
    	int blueScore = 0;
    	for (int i = 0; i < node.wg.map.length; i++) {
    		for (int j = 0; j < node.wg.map[i].length; j++) {
    			if (node.wg.occupied[i][j] == Agent.BLUE) {
    				blueScore += node.wg.map[i][j];
    			}
    			else if (node.wg.occupied[i][j] == Agent.GREEN) {
    				greenScore += node.wg.map[i][j];
    			}
    		}
    	}
    	
    	if ((req_ag == Agent.BLUE && req_getMax) || (req_ag == Agent.GREEN && !req_getMax)) {
    		return blueScore;
    	} else {
    		return greenScore;
    	}
    }
}
