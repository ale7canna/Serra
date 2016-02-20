package it.polimi.serra.utils;

public class IntegerRange {
	private int left, right;
	
	public IntegerRange(int _left, int _right)
	{
		left = _left;
		right = _right;
	}
	
	public boolean contains(int d)
	{
		return left <= d && d <= right;
	}
}
