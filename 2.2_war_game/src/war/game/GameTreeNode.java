package war.game;

import war.game.Main.Move;

public class GameTreeNode {
    
    public int value;
    public int moveRow;
    public int moveCol;
    public WarGame wg;
    public Move move;
    public GameTreeNode parent;
    public int depth = 0;
    
    public GameTreeNode(GameTreeNode p, boolean root) {
    	this(p.wg);
    	this.parent = p;
    	if (root) {
    		this.depth = 0;
    	}
    	else {
    		this.depth = p.depth+1;
    	}
    }
    
    public GameTreeNode(WarGame w) {
    	this.wg = new WarGame(w);
    }
    
}
