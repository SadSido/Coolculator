package sadsido.coolculator.game;

import org.andengine.entity.text.Text;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class Score extends Text
{
	//*******************************************************************************************

	private int m_score;
	
	//*******************************************************************************************

	public Score(float pX, float pY, IFont pFont, int score, VertexBufferObjectManager pVBO) 
	{
		super(pX, pY, pFont, formatScore(score), pVBO);
		m_score = score;
	}
	
	//*******************************************************************************************

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
