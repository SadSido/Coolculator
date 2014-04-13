package sadsido.coolculator.game;

import org.andengine.entity.Entity;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import sadsido.coolculator.game.Layout.Rect;

public class Score extends Entity
{
	//*******************************************************************************************

	public static enum Align { Left, Right }
	
	//*******************************************************************************************

	private int    m_score;
	private Text   m_text;
	private Sprite m_icon;
	
	//*******************************************************************************************

	public Score(int score, Rect rect, Align align, IFont pFont, ITextureRegion texIcon, VertexBufferObjectManager pVBO) 
	{
		m_score = score;
		m_text  = new Text(0.0f, 0.0f, pFont, formatScore(score), pVBO);
		m_icon  = new Sprite(0.0f, 0.0f, 1.5f * m_text.getHeight(), m_text.getHeight(), texIcon, pVBO);
		
		final float posY = (rect.height() - m_text.getHeight()) / 2.0f;
		
		attachChild(m_icon);
		attachChild(m_text);
		
		if (align == Align.Left)
		{
			m_icon.setPosition(0.0f, posY);
			m_text.setPosition(m_icon.getWidth(), posY);			
		} 
		else 
		{
			m_icon.setPosition(rect.width() - m_icon.getWidth(), posY);
			m_text.setPosition(m_icon.getX() - m_text.getWidth(), posY);
		}
		
		// set pos for root entity:
		setPosition(rect.left, rect.top);
	}
	
	//*******************************************************************************************

	public int getScore()
	{ return m_score; }
	
	public void inc(int value)
	{
		m_score = m_score + value;
		m_text.setText(formatScore(m_score));
	}

	public void dec(int value)
	{
		m_score = Math.max(m_score - value, 0);
		m_text.setText(formatScore(m_score));
	}
	
	//*******************************************************************************************

	private static String formatScore(int score)
	{ return String.format("%05d", score); }
	
	//*******************************************************************************************
}
