import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class SplayTree implements Tree {

	private NodeSP root;
	private long comparisons;
	private long mods;
	private int inserted;
	private int maxInserted;
	private int insertion;
	private int removal;
	private int search;
	private int load;
	private int inorder;
	
	public SplayTree() {
		root = null;
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
		insertSplay(s);
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
	
	public boolean search(String s) {
		search++;
		if(root == null) {
			return false;
		}
		searchSplay(s);
		return compare(root.getValue(),s) == 0;
	}
	
	public void delete(String s) {
		removal++;
		if(compareRef(root,null)) {
			return;
		}
		searchSplay(s);
		if(compare(root.getValue(),s) == 0) {
			inserted--;
			NodeSP rootLeft = root.getLeft();
			NodeSP rootRight = root.getRight();
			if(compareRef(rootLeft,null) && compareRef(rootRight,null)) {
				mods++;
				root = null;
				return;
			}
			if(compareRef(rootLeft,null)) {
				mods++;
				rootRight.setParent(null);
				mods++;
				root = rootRight;
				return;
			}
			if(compareRef(rootRight,null)) {
				mods++;
				rootLeft.setParent(null);
				mods++;
				root = rootLeft;
				return;
			}
			mods++;
			rootLeft.setParent(null);
			mods++;
			root = rootLeft;
			NodeSP temp = root;
			while(!compareRef(temp.getRight(),null)) {
				temp = temp.getRight();
			}
			splay(temp);
			mods++;
			root.setRight(rootRight);
			mods++;
			rootRight.setParent(root);
		}
	}
	
	public void inorder() { 
		inorder++;
        if (compareRef(root,null)) {
        	return; 
        }
        Stack<NodeSP> s = new Stack<>(); 
        NodeSP curr = root; 
        while (!compareRef(curr,null) || s.size() > 0) { 
            while (!compareRef(curr,null)) { 
                s.push(curr); 
                curr = curr.getLeft(); 
            } 
            curr = s.pop(); 
            System.out.print(curr.getValue()+" "); 
            curr = curr.getRight(); 
        } 
		System.out.println("");
    } 
	
	private void insertSplay(String s) {
		NodeSP curr = root;
		NodeSP prev = null;
        while (!compareRef(curr,null)) {
        	prev = curr;
            if (compare(s,prev.getValue()) > 0) {
            	curr = curr.getRight();
            } else {
            	curr = curr.getLeft();
            }	
        }
        curr = new NodeSP(s);
        mods++;
        curr.setParent(prev);
        if (compareRef(prev,null)) {
        	mods++;
        	root = curr;
        } else if (compare(s,prev.getValue()) > 0) {
        	mods++;
        	prev.setRight(curr);
        } else {
        	mods++;
        	prev.setLeft(curr);
        }
        splay(curr);
    }
	
	private void splay(NodeSP x) {
        while (!compareRef(x.getParent(),null)) {
        	NodeSP parent = x.getParent();
        	NodeSP grandParent = parent.getParent();
            if (compareRef(grandParent,null)) {
                if (compareRef(x,parent.getLeft())) {
                	rightRotate(x);
                } else {
                	leftRotate(x); 
                }            
            } else {
                if (compareRef(x,parent.getLeft())) {
                    if (compareRef(parent,grandParent.getLeft())) {
                        rightRotate(parent);
                        rightRotate(x);
                    } else {
                        rightRotate(x);
                        leftRotate(x);
                    }
                } else {
                    if (compareRef(parent,grandParent.getLeft())) {
                        leftRotate(x);
                        rightRotate(x);
                    } else {
                        leftRotate(parent);
                        leftRotate(x);
                    }
                }
            }
        }
        mods++;
        root = x;
    }
	
	private void rightRotate(NodeSP node) {
		NodeSP parent = node.getParent();
        if (!compareRef(parent.getParent(),null)) {
            if (compareRef(parent,parent.getParent().getLeft())) {
            	mods++;
            	parent.getParent().setLeft(node);
            } else {
            	mods++;
            	parent.getParent().setRight(node);
            }
        }
        if (!compareRef(node.getRight(),null)) {
        	mods++;
        	node.getRight().setParent(parent);
        }
        mods++;
        node.setParent(parent.getParent());
        mods++;
        parent.setParent(node);
        mods++;
        parent.setLeft(node.getRight());
        mods++;
        node.setRight(parent);
    }

	private void leftRotate(NodeSP node) {
		NodeSP parent = node.getParent();
        if (!compareRef(parent.getParent(),null)) {
            if (compareRef(parent,parent.getParent().getLeft())) {
            	mods++;
            	parent.getParent().setLeft(node);
            } else {
            	mods++;
            	parent.getParent().setRight(node);
            }	
        }
        if (!compareRef(node.getLeft(),null)) {
        	mods++;
        	node.getLeft().setParent(parent);
        }
        mods++;
        node.setParent(parent.getParent());
        mods++;
        parent.setParent(node);
        mods++;
        parent.setRight(node.getLeft());
        mods++;
        node.setLeft(parent);
    }

	private void searchSplay(String s) {
		NodeSP curr = root;
		NodeSP prev = null;
        while (!compareRef(curr,null)) {
        	prev = curr;
            if (compare(s,curr.getValue()) > 0) {
            	curr = curr.getRight();
            } else if (compare(s,curr.getValue()) < 0) {
            	curr = curr.getLeft();
            } else {
                splay(curr);
                return;
            }
        }
        if(!compareRef(prev,null)) {
            splay(prev);
        }
    }
	
	private int compare(String i, String j) {
		comparisons++;
		return i.compareTo(j);
	}
	
	private boolean compareRef(Object n1, Object n2) {
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
