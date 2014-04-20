package sadsido.coolculator.pick;

import sadsido.coolculator.game.Const;



public abstract class ValuePicker 
{
	//*******************************************************************************************

	public abstract int  pickValue ();
	public abstract void pushValue (int value);
	
	//*******************************************************************************************
	
	public static ValuePicker create(int column)
	{
		switch (column)
		{
		case (Const.Cols - 1): return LinearPicker;
		case (Const.Cols - 2): return new RndValuePicker(0, 06);
		}
		
		// otherwise random digit:
		return new RndValuePicker(0, 9);
	}	
	
	//*******************************************************************************************
	
	private static ValuePicker LinearPicker = new ValuePicker()
	{
		private static final int min = 01;
		private static final int max = 20;
		private int m_next = min;
		
		@Override public int pickValue() { final int res = m_next; m_next = (m_next == max) ? min : m_next + 1; return res; }
		@Override public void pushValue(int value) {}
		
	};
	
	//*******************************************************************************************
}
