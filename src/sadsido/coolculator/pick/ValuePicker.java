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
		case (Const.Cols - 1): return createLinearPicker();
		}
		
		// otherwise random digit:
		return new RndValuePicker(1, 9);
	}	
	
	//*******************************************************************************************
	
	private static ValuePicker createLinearPicker()
	{
		return new ValuePicker()
		{
			private static final int min = 05;
			private static final int max = 20;
			private int m_next = min;
			
			@Override public int pickValue() { final int res = m_next; m_next = (m_next == max) ? min : m_next + 1; return res; }
			@Override public void pushValue(int value) {}
			
		};
	}
	
	//*******************************************************************************************
}
