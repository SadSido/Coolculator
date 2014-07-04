package sadsido.coolculator.game;

import android.annotation.SuppressLint;
import org.andengine.entity.Entity;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.opengl.font.IFont;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import sadsido.coolculator.Layout.Rect;

public class Score extends Entity
{
	//*******************************************************************************************

	public static enum Align { Left, Right }
	
	//*******************************************************************************************

	private int    m_score;
	private int    m_level;
	private Text   m_text;
	private Sprite m_icon;
	private float  m_width;
	private float  m_height;
	
	//*******************************************************************************************

	public Score(int score, Rect rect, Align align, IFont pFont, ITextureRegion texIcon, VertexBufferObjectManager pVBO) 
	{
		m_score = score;
		m_level = score / Const.ScoreGoal;
		
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
		
		// remember dimensions:
		m_width = rect.width();
		m_height = rect.height();
		
		// set pos for root entity:
		setPosition(rect.left, rect.top);
	}
	
	//*******************************************************************************************

	public int score()
	{ return m_score; }
	
	public int level()
	{ return m_level; }
	
	public void change(int delta)
	{
		m_score = Math.max(m_score + delta, 0);
		m_level = m_score / Const.ScoreGoal;
		
		m_text.setText(formatScore(m_score));
		
		if (delta > 0)
		{ playChangeAnimation(); }
	}
	
	
	
	//*******************************************************************************************

	@SuppressLint("DefaultLocale")
	private static String formatScore(int score)
	{ return String.format("%05d", score); }
	
	public void playChangeAnimation()
	{
		IEntityModifier modif = new SequenceEntityModifier
		(
			new ScaleAtModifier(0.15f, 1.0f, 1.2f, m_width / 2f, m_height / 2f),
			new ScaleAtModifier(0.15f, 1.2f, 1.0f, m_width / 2f, m_height / 2f)
		);
		registerEntityModifier(modif);
	}

	//*******************************************************************************************
}
