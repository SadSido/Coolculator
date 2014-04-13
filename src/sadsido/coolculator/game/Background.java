package sadsido.coolculator.game;

import javax.microedition.khronos.opengles.GL10;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import sadsido.coolculator.game.Layout.Rect;



public class Background extends Rectangle
{
	//*******************************************************************************************

	private Sprite m_blend;
	private int m_index;
	
	//*******************************************************************************************
		
	public Background(Rect rect, ITextureRegion region, VertexBufferObjectManager pVBO) 
	{
		super(rect.left, rect.top, rect.width(), rect.height(), pVBO);	
		
		// creating blend over the background:
		
		m_blend = new Sprite(0.0f, 0.0f, rect.width(), rect.height(), region, pVBO);
		m_blend.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE);
		
		attachChild(m_blend);
		
		// set initial score & color:
		
		m_index = 0;
		setColor(Const.BackgroundColors[m_index]);
	}

	//*******************************************************************************************
	
	public void animateScoreChange(int newScore)
	{
		final int factor = 100;
		final int index  = (newScore / factor) % Const.BackgroundColors.length;
		
		if (index != m_index)
		{
			m_index = index;
			registerEntityModifier(new ColorModifier(0.3f, getColor(), Const.BackgroundColors[m_index]));		
		}
	}
	
	//*******************************************************************************************
}
