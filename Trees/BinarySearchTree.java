import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

public class BinarySearchTree implements Tree {
	
	private NodeBST root;
	private long comparisons;
	private long mods;
	private int inserted;
	private int maxInserted;
	private int insertion;
	private int removal;
	private int search;
	private int load;
	private int inorder;
	
	
	public BinarySearchTree() {
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
		if(compareRef(root,null)) {
			mods++;
			root = insertBST(s);
		} else {
			insertBST(s);
		}
	}
	
	public void delete(String s) {
		removal++;
		NodeBST parent = null;
        NodeBST current = root;
        if (compareRef(root, null)) {
        	return;
        }
        while (!compareRef(current,null)) {
            if(compare(current.getValue(),s) == 0) {
            	inserted--;
                break;
            }
            parent = current;
            if (compare(s,current.getValue()) < 0) {
                current = current.getLeft();
            } else {
                current = current.getRight();
            }
        }
        if(compareRef(current,null)) {
        	return;
        }
        if(compareRef(parent,null)) {
        	mods++;
        	root = deleteBST(current);
        	return;
        }
        if(compareRef(parent.getLeft(),current)) {
        	mods++;
            parent.setLeft(deleteBST(current));
        } else {
        	mods++;
            parent.setRight(deleteBST(current));
        }
	}
	
	public boolean search(String s) { 
		search++;
		NodeBST node = root;
        while (!compareRef(node,null)) {  
            if (compare(s,node.getValue()) > 0) {
            	node = node.getRight();
            } else if (compare(s, node.getValue()) < 0) {
            	node = node.getLeft(); 
            } else {
            	return true; 
            }
        } 
        return false; 
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
        if (compareRef(root,null)) {
        	return; 
        }
        Stack<NodeBST> s = new Stack<NodeBST>(); 
        NodeBST curr = root; 
        while (!compareRef(curr,null) || s.size() > 0) { 
            while (!compareRef(curr, null)) { 
                s.push(curr); 
                curr = curr.getLeft(); 
            } 
            curr = s.pop(); 
            System.out.print(curr.getValue()+" "); 
            curr = curr.getRight(); 
        } 
		System.out.println("");
    } 
	
	
	private NodeBST insertBST(String s) {
		NodeBST newnode = new NodeBST(s); 
		NodeBST curr = root;   
		NodeBST prev = null;  
	    
	    while (!compareRef(curr,null)) {  
	    	prev = curr;  
	        if (compare(s,curr.getValue()) < 0)  {
	        	curr = curr.getLeft(); 
	        } else {
	        	curr = curr.getRight(); 
	        } 
	    }    
	    if (compareRef(prev,null)) {
	    	prev = newnode;  
	    } else if (compare(s, prev.getValue()) < 0)  {
	    	mods++;
	    	prev.setLeft(newnode);
	    } else {
	    	mods++;
	    	prev.setRight(newnode); 
	    }  
	    return prev;
	}  
	
	private NodeBST deleteBST(NodeBST node) {
		if (compareRef(node.getLeft(),null) && compareRef(node.getRight(),null)) {
            return null;
        }
        if (!compareRef(node.getLeft(),null) && !compareRef(node.getRight(),null)) {
        	NodeBST parent = node;
        	NodeBST current = node.getRight();
            while (!compareRef(current.getLeft(),null)) {
                parent = current;
                current = current.getLeft();
            }
            if (compareRef(node.getRight().getLeft(),null)) {
            	mods++;
                parent.setRight(current.getRight());
            } else {
            	mods++;
                parent.setLeft(current.getRight());
            }
            mods++;
            current.setRight(null);
            mods++;
            node.setValue(current.getValue());
        } else if (!compareRef(node.getLeft(),null)) {
            node = node.getLeft();
        } else {
            node = node.getRight();
        }
        return node;
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
