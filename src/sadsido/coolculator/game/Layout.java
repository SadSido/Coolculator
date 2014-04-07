package sadsido.coolculator.game;

import org.andengine.engine.camera.Camera;



public class Layout 
{
	//*******************************************************************************************

	public static class Rect
	{
		public float top, bottom;
		public float left, right;
		
		public Rect(float l, float t, float r, float b)
		{ left = l; top = t; right = r; bottom = b; }
		
		public final float width()
		{ return (right - left); }
		
		public final float height()
		{ return (bottom - top); }
	}
	
	//*******************************************************************************************

	private final float m_width;
	private final float m_height;

	private Rect m_rcButtons;
	private Rect m_rcButton;
	private Rect m_rcTimebar;
	
	//*******************************************************************************************

	public Layout(Camera camera)
	{
		m_width  = camera.getWidth();
		m_height = camera.getHeight();
		
		float topBar = m_height / 6.0f;
		
		m_rcButtons = new Rect(0.0f, topBar, m_width, m_height);
		
		addMargins(m_rcButtons, 40.0f);
		makeAspect(m_rcButtons, 2.0f * Const.Cols / Const.Rows);
		
		m_rcButton = new Rect(0.0f, 0.0f, m_rcButtons.width() / Const.Cols, m_rcButtons.height() / Const.Rows);
		
		final float delta = (topBar - 30.0f) / 2.0f;
		m_rcTimebar = new Rect(m_rcButtons.left, delta, m_rcButtons.right, topBar - delta);
	}
	
	//*******************************************************************************************

	public Rect rcButtons()
	{ return m_rcButtons; }
	
	public Rect rcButton()
	{ return m_rcButton; }
	
	public Rect rcButton(int col, int row)
	{
		final float left = m_rcButtons.left + col * m_rcButton.width();
		final float top  = m_rcButtons.top  + row * m_rcButton.height();
		
		return new Rect(left, top, left + m_rcButton.width(), top + m_rcButton.height()); 
	}
	
	public Rect rcTimebar()
	{ return m_rcTimebar; }
	
	//*******************************************************************************************

	// rect helpers:
	
	private static void addMargins(Rect rc, float margins)
	{
		rc.top    += margins;
		rc.left   += margins;
		rc.bottom -= margins;
		rc.right  -= margins;
	}
	
	private static void makeAspect(Rect rc, float aspect)
	{
		final float myaspect = rc.width() / rc.height();
		
		if (myaspect > aspect)
		{
			final float delta = (rc.width() - rc.height() * aspect) / 2.0f;
			rc.left += delta; rc.right -= delta;
		}
		else
		{
			final float delta = (rc.height() - rc.width() / aspect) / 2.0f;
			rc.top += delta; rc.bottom -= delta;
		}
	}

	//*******************************************************************************************
}
