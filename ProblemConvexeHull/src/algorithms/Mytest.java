package algorithms;

public class Mytest<T> implements Comparable{
	public int numTest;
	public T data;
	public Mytest(int numTest, T data) {
		this.numTest = numTest;
		this.data = data;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public int compareTo(Object o) {
		return this.numTest - ((Mytest<T>)o).numTest;
	}
}
