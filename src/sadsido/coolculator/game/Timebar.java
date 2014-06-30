package sadsido.coolculator.game;

import org.andengine.entity.Entity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import sadsido.coolculator.Layout.Rect;
import sadsido.coolculator.scenes.GameScene;



public class Timebar extends Entity
{
	//*******************************************************************************************

	private GameScene m_scene;
	
	private Rectangle m_topBar;
	private Sprite m_botBar;
	
	//*******************************************************************************************

	public Timebar(GameScene scene, Rect rect, ITextureRegion region, VertexBufferObjectManager pVBO) 
	{
		m_scene  = scene;	
		
		m_topBar = new Rectangle(0.0f, 0.0f, rect.width(), rect.height(), pVBO);
		m_botBar = new Sprite(0.0f, 0.0f, rect.width(), rect.height(), region, pVBO);

		attachChild(m_botBar);
		attachChild(m_topBar);

		// align root entity:
		setPosition(rect.left, rect.top);
	}

	//*******************************************************************************************

	public void start(float time)
	{
		IEntityModifier modifier = new ScaleAtModifier(time, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, m_botBar.getHeight() / 2.0f) 
		{
			@Override
			protected void onModifierFinished(org.andengine.entity.IEntity pItem)
			{ m_scene.onTimeoutAnimationFinished(); };
		};
		
		m_topBar.registerEntityModifier(modifier);
	}

	public void reset()
	{
		IEntityModifier modifier = new ScaleAtModifier(0.3f, m_topBar.getScaleX(), 1.0f, 1.0f, 1.0f, 0.0f, m_botBar.getHeight() / 2.0f) ;		
		m_topBar.registerEntityModifier(modifier);
	}
	
	public void stop()
	{
		m_topBar.clearEntityModifiers();
	}
	
	//*******************************************************************************************
}
