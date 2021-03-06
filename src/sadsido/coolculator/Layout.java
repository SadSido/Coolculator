package sadsido.coolculator;

import org.andengine.engine.camera.Camera;

import sadsido.coolculator.game.Const;



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
		
		public final float centerX()
		{ return (left + right) / 2f; }
		
		public final float centerY()
		{ return (top + bottom) / 2f; }
		
		public final static Rect Empty = new Rect(0f, 0f, 0f, 0f);
	}
	
	//*******************************************************************************************

	private final float m_width;
	private final float m_height;

	private Rect m_rcTopBar;
	private Rect m_rcScreen;
	private Rect m_rcButtons;
	private Rect m_rcButton;
	private Rect m_rcTimebar;
	private Rect m_rcScore;
	private Rect m_rcRecord;
	private Rect m_rcMenu;
	private Rect m_rcCCLogo;
	
	//*******************************************************************************************

	public Layout(Camera camera)
	{
		m_width  = camera.getWidth();
		m_height = camera.getHeight();
		
		m_rcScreen = new Rect(0.0f, 0.0f, m_width, m_height);
		
		float topBar = m_height / 7.0f;
		float lftBar = m_width  / 4.0f; 
		
		m_rcTopBar  = new Rect(0.0f, 0.0f, m_width, topBar);
		m_rcButtons = new Rect(0.0f, topBar, m_width, m_height);
		
		addMargins(m_rcButtons, 50.0f);
		makeAspect(m_rcButtons, 2.0f * Const.Cols / Const.Rows);
		
		m_rcButton = new Rect(0.0f, 0.0f, m_rcButtons.width() / Const.Cols, m_rcButtons.height() / Const.Rows);
		
		m_rcScore   = new Rect(0.0f, 0.0f, lftBar, topBar);
		m_rcRecord  = new Rect(m_width - lftBar, 0.0f, m_width, topBar);
		
		m_rcTimebar = new Rect(m_rcScore.right, topBar / 2.0f - 15.0f, m_rcRecord.left, topBar / 2.0f + 15.0f);
		
		m_rcMenu    = new Rect(0f, m_height / 2f, m_width, m_height / 2f + 2f * m_rcButton.height());
		m_rcCCLogo  = new Rect(0f, 0f, m_width, m_rcMenu.top);
		
		addMargins(m_rcCCLogo, 80f);
		makeAspect(m_rcCCLogo, 890f / 190f);
	}
	
	//*******************************************************************************************

	public float width()
	{ return m_width; }
	
	public float height()
	{ return m_height; }
	
	public Rect rcMenu()
	{ return m_rcMenu; }
	
	public Rect rcCCLogo()
	{ return m_rcCCLogo; }
	
	public Rect rcTopBar()
	{ return m_rcTopBar; }
	
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
	
	public Rect rcScore()
	{ return m_rcScore; }
	
	public Rect rcRecord()
	{ return m_rcRecord; }
	
	public Rect rcScreen()
	{ return m_rcScreen; }
	
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
