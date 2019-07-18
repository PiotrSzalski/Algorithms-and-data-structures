import java.util.ArrayList;
import java.util.Collections;
import java.util.TreeMap;

public class PriorityQueue<T,S extends Comparable<S>> {
	
	private ArrayList<QueueElement<T,S>> list;
	private TreeMap<T,ArrayList<Integer>> map;
	
	public PriorityQueue() {
		list = new ArrayList<>();
		map = new TreeMap<>();
	}
	
	public void insert(T value, S priority) {
		int iterator = list.size();
		list.add(new QueueElement<T,S>(value,priority));
		if(map.get(value) == null) {
			ArrayList<Integer> al = new ArrayList<>();
			al.add(list.size() - 1);
			map.put(value, al);
		} else {
			map.get(value).add(list.size() - 1);
		}
		while(iterator > 0 && list.get(getParent(iterator)).getPriority().compareTo(priority) == 1) {
			swap(iterator,getParent(iterator));
        	iterator = getParent(iterator);
		}
	}
	
	public boolean empty() {
		return list.isEmpty();
	}
	
	public T top() {
		if(empty()) {
			return null;
		} else {
			return list.get(0).getValue();
		}
	}
	
	public T pop() {
		if(empty()) {
			return null;
		} else {
			T value = list.get(0).getValue();
			swap(0,list.size()-1);
			list.remove(list.size()-1);
			map.get(value).remove(Integer.valueOf(list.size()));
			if(map.get(value).isEmpty()) {
				map.remove(value);
			}
			heapify(0,true);
			return value;
		}
	}
	
	public void priority(T value, S priority) {
		
		ArrayList<Integer> indexes = map.get(value);
		if(indexes == null) {
			return;
		}
		for(int i = 0; i < indexes.size(); i++) {
			Integer index = indexes.get(i);
			if(list.get(index).getPriority().compareTo(priority) == 1) {
				list.get(index).setPriority(priority);
				int iterator = index;
				while (iterator > 0 && list.get(getParent(iterator)).getPriority().compareTo(priority) == 1) {
	                swap(iterator, getParent(iterator));
	                iterator = getParent(iterator);
	            }
				i = -1;
			}
		}	
	}
	
	public void print() {
		ArrayList<QueueElement<T,S>> list2 = (ArrayList<QueueElement<T, S>>) list.clone();
		while(!list.isEmpty()) {
			System.out.print("("+list.get(0).getValue()+", "+list.get(0).getPriority()+") ");
			Collections.swap(list, 0, list.size()-1);
			list.remove(list.size()-1);
			heapify(0,false);
		}
		System.out.println("");
		list = list2;
	}	
	
	private int getParent(int index) {
		return (int) (Math.ceil(((double) index)/2)-1);
	}
	
	private void swap(int i, int j) {
		map.get(list.get(i).getValue()).add(j);
		map.get(list.get(i).getValue()).remove(Integer.valueOf(i));
		map.get(list.get(j).getValue()).add(i);
		map.get(list.get(j).getValue()).remove(Integer.valueOf(j));
		Collections.swap(list, i, j);
	}
	
	private void heapify(int i, boolean onMap) { 
        int lowest = i;
        int left = 2*i + 1;
        int right = 2*i + 2;
        
        if (left < list.size()) {
        	if (list.get(left).getPriority().compareTo(list.get(lowest).getPriority()) == -1) {
        		lowest = left; 
            }
        }
        if(right < list.size()) {
        	if (list.get(right).getPriority().compareTo(list.get(lowest).getPriority()) == -1) {
        		lowest = right; 
            }
        }
        if (lowest != i)  { 
        	if(onMap) {
        		swap(i, lowest);
        	} else {
        		Collections.swap(list, i, lowest);
        	}
        	heapify(lowest,onMap);
        } 
    } 
	
	public void myprint() {
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getPriority()+" "+list.get(i).getValue());
		}
	}
	
	public void printMap() {
		for(int i = 0; i < list.size(); i++) {
			System.out.println(list.get(i).getValue()+" "+map.get(list.get(i).getValue()));
		}
	}
	
	public boolean emptyMap() {
		return map.isEmpty();
	}
}