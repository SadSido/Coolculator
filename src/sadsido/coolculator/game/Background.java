package sadsido.coolculator.game;

import javax.microedition.khronos.opengles.GL10;
import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
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
	private Sprite m_grad;

	private int m_level;
	private int m_index;
	
	//*******************************************************************************************
		
	public Background(Rect rect, Rect rcTop, ITextureRegion texBlend, ITextureRegion texGrad, VertexBufferObjectManager pVBO) 
	{
		super(rect.left, rect.top, rect.width(), rect.height(), pVBO);	
		
		// creating blend over the background:
		
		m_blend = new Sprite(rect.left, rect.top, rect.width(), rect.height(), texBlend, pVBO);
		m_grad  = new Sprite(rcTop.left, rcTop.top + rcTop.height(), rcTop.width(), texGrad.getHeight(), texGrad, pVBO);
		
		attachChild(m_blend);
		attachChild(m_grad);
		
		// set initial score & color:
		
		m_level = 0;
		m_index = 0;
		
		setColor(Const.BackgroundColors[m_level]);
	}

	//*******************************************************************************************
	
	public void playLevelAnimation(int level)
	{
		if (level != m_level)
		{
			m_level = level;
			m_index = level % Const.BackgroundColors.length;
						
			IEntityModifier modifier = new SequenceEntityModifier
			(
				new ColorModifier(0.4f, getColor(),  Color.BLACK),
				new ColorModifier(0.2f, Color.BLACK, Color.BLACK),
				new ColorModifier(0.4f, Color.BLACK, Const.BackgroundColors[m_index])
			); 
			
			registerEntityModifier(modifier);		
		}
	}
	
	//*******************************************************************************************
}
