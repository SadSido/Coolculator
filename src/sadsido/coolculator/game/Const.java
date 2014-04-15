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
	
	public final static Color SelectColor = new Color(0.50f, 0.50f, 0.50f);

	// background colors:
	
	public final static Color[] BackgroundColors = new Color[] 
	{
		new Color(0.20f, 0.20f, 0.50f),
		new Color(0.18f, 0.40f, 0.18f),
		new Color(0.45f, 0.20f, 0.20f),		
		new Color(0.40f, 0.40f, 0.20f),		
		new Color(0.40f, 0.20f, 0.40f),		
		new Color(0.20f, 0.35f, 0.35f)		
	};
	
	//*******************************************************************************************
	
	private Const()	{}
	
	//*******************************************************************************************
}
