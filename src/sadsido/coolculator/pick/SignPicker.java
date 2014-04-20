package sadsido.coolculator.pick;

import sadsido.coolculator.game.Const;
import sadsido.coolculator.game.Sign;



public abstract class SignPicker 
{
	//*******************************************************************************************

	public abstract Sign pickSign (int value);
	
	//*******************************************************************************************

	public static SignPicker create(int column)
	{
		switch (column)
		{
		case Const.Cols - 1 : return NonePicker;
		case Const.Cols - 2 : return EqualsPicker;
		}
		
		return new RndSignPicker();
	}	

	//*******************************************************************************************
	
	private static SignPicker EqualsPicker = new SignPicker()
	{
		@Override public Sign pickSign(int value) { return Sign.Equals; }
	};
	
	private static SignPicker NonePicker = new SignPicker()
	{
		@Override public Sign pickSign(int value) { return Sign.None; }
	};

	//*******************************************************************************************
}
