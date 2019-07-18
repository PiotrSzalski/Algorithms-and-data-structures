public class QueueElement<T,S extends Comparable<S>> {
	private T value;
	private S priority;
	
	public QueueElement(T v, S p) {
		setValue(v);
		setPriority(p);
	}

	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public S getPriority() {
		return priority;
	}

	public void setPriority(S priority) {
		this.priority = priority;
	}	
}
