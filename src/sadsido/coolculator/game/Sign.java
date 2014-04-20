package sadsido.coolculator.game;

import org.andengine.util.color.Color;


public abstract class Sign 
{
	//*******************************************************************************************
	
	public abstract int apply(int lhv, int rhv);
	public abstract Color getColor();
	public abstract String getText();
	
	//*******************************************************************************************
	
	public static Sign Plus = new Sign() 
	{
		@Override public int apply(int lhv, int rhv) { return lhv + rhv; }		
		@Override public Color getColor() { return Const.SignColor; }
		@Override public String getText() { return "+"; }
	};
	public static Sign Minus = new Sign() 
	{
		@Override public int apply(int lhv, int rhv) { return lhv - rhv; }		
		@Override public Color getColor() { return Const.SignColor; }
		@Override public String getText() { return "-"; }
	};
	public static Sign Multiply = new Sign() 
	{
		@Override public int apply(int lhv, int rhv) { return lhv * rhv; }		
		@Override public Color getColor() { return Const.MultColor; }
		@Override public String getText() { return "*"; }
	};
	public static Sign Equals = new Sign() 
	{
		@Override public int apply(int lhv, int rhv) { return (lhv == rhv) ? 1 : 0; }		
		@Override public Color getColor() { return Const.SignColor; }
		@Override public String getText() { return "="; }
	};
	public static Sign None = new Sign() 
	{
		@Override public int apply(int lhv, int rhv) { return 0; }		
		@Override public Color getColor() { return Const.SignColor; }
		@Override public String getText() { return ""; }
	};

	//*******************************************************************************************
}
