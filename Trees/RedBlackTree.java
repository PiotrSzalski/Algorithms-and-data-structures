import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class RedBlackTree implements Tree {

	private NodeRBT root;
	private long comparisons;
	private long mods;
	private int inserted;
	private int maxInserted;
	private int insertion;
	private int removal;
	private int search;
	private int load;
	private int inorder;
	private NodeRBT nil = new NodeRBT("");
	
	public RedBlackTree() {
		nil.setRed(false);
		root = nil;
		root.setLeft(nil);
		root.setRight(nil);
		root.setParent(nil);
	}
	
	public void insert(String s) {
		insertion++;
		while(s.length() > 0 && !Character.isLetter(s.charAt(0))) {
			s = s.substring(1);
		}
		while(s.length() > 0 && !Character.isLetter(s.charAt(s.length()-1))) {
			s = s.substring(0,s.length()-1);
		}
		if(s.length() == 0) {
			return;
		}
		inserted++;
		if(inserted > maxInserted) {
			maxInserted = inserted;
		}
		NodeRBT ins = insertIter(s);
		if(compareRef(root,nil)) {
			mods++;
			root = ins;
		}
		afterInsert(ins);
	}
	
	public void delete(String s){
		removal++;
		NodeRBT nodeToRemove = searchIter(s);
		if(compareRef(nodeToRemove,nil)) {
			return;
		}
		inserted--;
		NodeRBT x = nil;
		NodeRBT y = nil;
		if (compareRef(nodeToRemove.getLeft(),nil) || compareRef(nodeToRemove.getRight(),nil)) {
			y = nodeToRemove;
		} else {
			y = nodeToRemove.getRight();
			while (!compareRef(y.getLeft(),nil)) {
				y = y.getLeft();
			}	
		} 
		if (!compareRef(y.getLeft(),nil)) {
			x = y.getLeft();
		} else {
			x = y.getRight();
		}
		mods++;
		x.setParent(y.getParent());
		if (compareRef(y.getParent(),nil)) {
			mods++;
			root = x;
		} else if (!compareRef(y.getParent().getLeft(),nil) && compareRef(y.getParent().getLeft(),y)) {
			mods++;
			y.getParent().setLeft(x);
		} else if (!compareRef(y.getParent().getRight(),nil) && compareRef(y.getParent().getRight(),y)) {
			mods++;
			y.getParent().setRight(x);
		}
		if (!compareRef(y,nodeToRemove)){
			mods++;
			nodeToRemove.setValue(y.getValue());
		}
		if (!isRed(y)) {
			afterRemove(x);
		}	
	}
	
	public boolean search(String v) {
		search++;
		return !compareRef(searchIter(v),nil);
	}
	
	public void load(File f) {
		load++;
		Scanner input = null;
		try {
			input = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("The file can not be found");
			return;
		}
		while(input.hasNextLine()) {
			String[] arr = input.nextLine().split("[\\.,\\s!;?:\"]+");
			for(int i = 0; i < arr.length; i++) {
				insert(arr[i]);
			}
		}
		input.close();
	}
	
	public void inorder() { 
		inorder++;
        if (compareRef(root,nil)) {
        	return; 
        }
        Stack<NodeRBT> s = new Stack<>(); 
        NodeRBT curr = root; 
        while (!compareRef(curr,nil) || s.size() > 0) { 
            while (!compareRef(curr,nil)) { 
                s.push(curr); 
                curr = curr.getLeft(); 
            } 
            curr = s.pop(); 
            System.out.print(curr.getValue()+" ");  
            curr = curr.getRight(); 
        } 
		System.out.println("");
    } 
	
	private void afterRemove(NodeRBT node) {
		NodeRBT nodeBrother;

		while (!compareRef(node,root) && !isRed(node)) {
			if (compareRef(node,node.getParent().getLeft())) {
				nodeBrother = node.getParent().getRight();
				if (isRed(nodeBrother)){
					mods++;
					nodeBrother.setRed(false);
					mods++;
					node.getParent().setRed(true);
					leftRotate(node.getParent());
					nodeBrother = node.getParent().getRight();
				}
				if (!isRed(nodeBrother.getLeft()) && !isRed(nodeBrother.getRight())) {
					mods++;
					nodeBrother.setRed(true);
					node = node.getParent();
				}
				else {
					if (!isRed(nodeBrother.getRight())){
						mods++;
						nodeBrother.getLeft().setRed(false);
						mods++;
						nodeBrother.setRed(true);
						rightRotate(nodeBrother);
						nodeBrother = node.getParent().getRight();
					}
					mods++;
					nodeBrother.setRed(node.getParent().isRed());
					mods++;
					node.getParent().setRed(false);
					mods++;
					nodeBrother.getRight().setRed(false);
					leftRotate(node.getParent());
					node = root;
				}
			}
			else{
				nodeBrother = node.getParent().getLeft();
				if (isRed(nodeBrother)){
					mods++;
					nodeBrother.setRed(false);
					mods++;
					node.getParent().setRed(true);
					rightRotate(node.getParent());
					nodeBrother = node.getParent().getLeft();
				}
				if (!isRed(nodeBrother.getRight()) && !isRed(nodeBrother.getLeft())) {
					mods++;
					nodeBrother.setRed(true);
					node = node.getParent();
				}
				else {
					 if (!isRed(nodeBrother.getLeft())){
						mods++;
						nodeBrother.getRight().setRed(false);
						mods++;
						nodeBrother.setRed(true);
						leftRotate(nodeBrother);
						nodeBrother = node.getParent().getLeft();
					}
					mods++;
					nodeBrother.setRed(node.getParent().isRed());
					mods++;
					node.getParent().setRed(false);
					mods++;
					nodeBrother.getLeft().setRed(false);
					rightRotate(node.getParent());
					node = root;
				}
			}
		}
		mods++;
		node.setRed(false);
	}
	
	private void afterInsert(NodeRBT node){
		NodeRBT y = nil;
		while (isRed(node.getParent())){
			if (compareRef(node.getParent(),node.getParent().getParent().getLeft())) {
				y = node.getParent().getParent().getRight();
				if (isRed(y)) {
					mods++;
					node.getParent().setRed(false);
					mods++;
					y.setRed(false);
					mods++;
					node.getParent().getParent().setRed(true);
					node = node.getParent().getParent();
				} else if (compareRef(node,node.getParent().getRight())){
					node = node.getParent();
					leftRotate(node);
				} else {
					mods++;
					node.getParent().setRed(false);
					mods++;
					node.getParent().getParent().setRed(true);
					rightRotate(node.getParent().getParent());
				}
			} else {
				y = node.getParent().getParent().getLeft();
				if (isRed(y)) {
					mods++;
					node.getParent().setRed(false);
					mods++;
					y.setRed(false);
					mods++;
					node.getParent().getParent().setRed(true);
					node = node.getParent().getParent();
				} else if (compareRef(node,node.getParent().getLeft())){
					node = node.getParent();
					rightRotate(node);
				} else {
					mods++;
					node.getParent().setRed(false);
					mods++;
					node.getParent().getParent().setRed(true);
					leftRotate(node.getParent().getParent());
				}
			}
		}
		mods++;
		root.setRed(false);
	}
	
	private void leftRotate(NodeRBT x){
		NodeRBT y = x.getRight();
		mods++;
		x.setRight(y.getLeft());
		if (!compareRef(y.getLeft(),nil)) {
			mods++;
			y.getLeft().setParent(x);
		}
		mods++;
		y.setParent(x.getParent());
		if (compareRef(x.getParent(),nil)) {
			mods++;
			root = y;
		} else if (compareRef(x.getParent().getLeft(),x)) {
			mods++;
			x.getParent().setLeft(y);
		} else {
			mods++;
			x.getParent().setRight(y);
		} 
		mods++;
		y.setLeft(x);
		mods++;
		x.setParent(y);
	}
	
	private void rightRotate(NodeRBT y){
		NodeRBT x = y.getLeft();
		mods++;
        y.setLeft(x.getRight());
        if (!compareRef(x.getRight(),nil)) {
        	mods++;
        	x.getRight().setParent(y);
        }
        mods++;
        x.setParent(y.getParent());
        if (compareRef(y.getParent(),nil)) {
        	mods++;
        	root = x;
        } else if (compareRef(y.getParent().getRight(),y)) {
        	mods++;
        	y.getParent().setRight(x);
        } else {
        	mods++;
        	y.getParent().setLeft(x);
        }
        mods++;
        x.setRight(y);
        mods++;
        y.setParent(x);
	}
	
	
	private NodeRBT insertIter(String s) {
		NodeRBT newnode = new NodeRBT(s);
	    newnode.setLeft(nil);
	    newnode.setRight(nil);
	    NodeRBT curr = root;   
	    NodeRBT prev = nil;  
	    
	    while (!compareRef(curr,nil)) {  
	    	prev = curr;  
	        if (compare(s, curr.getValue()) < 0)  {
	        	curr = curr.getLeft(); 
	        } else {
	        	curr = curr.getRight(); 
	        } 
	    }    
	    if (compareRef(prev,nil)) {
	    	mods++;
	    	newnode.setParent(nil);
	    } else if (compare(s, prev.getValue()) < 0)  {
	    	mods++;
	    	prev.setLeft(newnode);
	    	mods++;
	    	newnode.setParent(prev);
	    } else {
	    	mods++;
	    	prev.setRight(newnode); 
	    	mods++;
	    	newnode.setParent(prev);
	    } 
	    return newnode;
	}

	private NodeRBT searchIter(String s) { 
		NodeRBT node = root;
        while (!compareRef(node,nil)) {  
            if (compare(s,node.getValue()) > 0) {
            	node = node.getRight();
            } else if (compare(s, node.getValue()) < 0) {
            	node = node.getLeft(); 
            } else {
            	return node; 
            }
        } 
        return nil; 
    } 
	
	private int compare(String i, String j) {
		comparisons++;
		return i.compareTo(j);
	}
	
	private boolean isRed(NodeRBT node) {
		comparisons++;
		return node.isRed();
	}
	
	private boolean compareRef(NodeRBT n1, NodeRBT n2) {
		comparisons++;
		return n1 == n2;
	}
	
	public long getComparisons() {
		return comparisons;
	}
	public long getMods() {
		return mods;
	}
	public int getMaxInserted() {
		return maxInserted;
	}
	public int getInserted() {
		return inserted;
	}
	public int getRemoval() {
		return removal;
	}
	public int getInserion() {
		return insertion;
	}
	public int getSearch() {
		return search;
	}
	public int getInorder() {
		return inorder;
	}
	public int getLoad() {
		return load;
	}
}
