package it.polimi.serra.utils;

public class DoubleRange {
	private double left, right;
	
	public DoubleRange(double _left, double _right)
	{
		left = _left;
		right = _right;
	}
	
	public boolean contains(double d)
	{
		return left <= d && d <= right;
	}
}
