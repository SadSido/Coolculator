package sadsido.coolculator.game;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import sadsido.coolculator.game.Layout.Rect;

public class Score extends Text
{
	//*******************************************************************************************

	private int m_score;
	
	//*******************************************************************************************

	public Score(int score, Rect rect, IFont pFont, VertexBufferObjectManager pVBO) 
	{
		super(0.0f, 0.0f, pFont, formatScore(score), pVBO);
		m_score = score;
		
		final float posX = (rect.width()  - getWidth())  / 2.0f;
		final float posY = (rect.height() - getHeight()) / 2.0f;
		
		setPosition(rect.left + posX, rect.top + posY);
	}
	
	//*******************************************************************************************

	public int getScore()
	{ return m_score; }
	
	public void inc(int value)
	{
		m_score = m_score + value;
		setText(formatScore(m_score));
	}

	public void dec(int value)
	{
		m_score = Math.max(m_score - value, 0);
		setText(formatScore(m_score));
	}
	
	//*******************************************************************************************

	private static String formatScore(int score)
	{ return String.format("%05d", score); }
	
	//*******************************************************************************************
}
