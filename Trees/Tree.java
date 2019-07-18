import java.io.File;

public interface Tree {
	void insert(String s);
	void delete(String s);
	boolean search(String s);
	void load(File f);
	void inorder();
	int getInserion();
	int getRemoval();
	int getSearch();
	int getLoad();
	int getInorder();
	int getMaxInserted();
	int getInserted();
	long getComparisons();
	long getMods();
}
