package sadsido.coolculator.gens;

import sadsido.coolculator.game.Const;



public abstract class ValueGenerator 
{
	//*******************************************************************************************

	public abstract int  pickValue ();
	public abstract void pushValue (int value);
	
	//*******************************************************************************************
	
	public static ValueGenerator create(int column)
	{
		switch (column)
		{
		case (Const.Cols - 1): return new LineGenerator(1, 20);
		case (Const.Cols - 2): return new RandGenerator(0, 06);
		}
		
		// otherwise random digit:
		return new RandGenerator(0, 9);
	}	
	
	//*******************************************************************************************
}
