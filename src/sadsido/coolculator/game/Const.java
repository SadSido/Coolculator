package sadsido.coolculator.game;

import org.andengine.util.color.Color;

public class Const 
{
	//*******************************************************************************************
	
	// number of buttons on the field:
	
	public final static int Rows = 4;
	public final static int Cols = 4;
	
	// colors of the button signs:
	
	public final static Color SignColor = new Color(0.85f, 0.85f, 0.85f);
	public final static Color MultColor = new Color(1.00f, 0.70f, 0.70f);
	
	// colors of the buttons:
	
	public final static Color SelectColor = new Color(0.30f, 0.30f, 0.30f);

	// background colors:
	
	public final static Color[] BackgroundColors = new Color[] 
	{
		new Color(0.20f, 0.20f, 0.50f),
		new Color(0.30f, 0.50f, 0.30f),
		new Color(0.50f, 0.30f, 0.30f)		
	};
	
	//*******************************************************************************************
	
	private Const()	{}
	
	//*******************************************************************************************
}
