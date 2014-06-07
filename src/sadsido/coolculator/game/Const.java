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
	
	public final static Color SelectColor = new Color(0.55f, 0.55f, 0.55f);
	public final static Color ErrorColor  = new Color(1.00f, 0.25f, 0.25f);

	// background colors:
	
	public final static Color[] BackgroundColors = new Color[] 
	{
		new Color(050f / 255f,     050f / 255f,     130f / 255f),
		new Color(135f / 255f,     070f / 255f,     035f / 255f),
		new Color(015f / 255f,     090f / 255f,     060f / 255f),
		new Color(120f / 255f,     050f / 255f,     055f / 255f),
		new Color(030f / 255f,     075f / 255f,     115f / 255f),
		new Color(095f / 255f,     050f / 255f,     120f / 255f),
		new Color(075f / 255f,     095f / 255f,     030f / 255f),
	};
	
	// level timing:
	
	public final static int   ScoreGoal  = 100;
	public final static float StartTime  = 5.0f * 60.0f;
	public final static float TimeFactor = 0.85f;
	
	//*******************************************************************************************
	
	private Const()	{}
	
	//*******************************************************************************************
}
