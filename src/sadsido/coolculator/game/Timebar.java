package sadsido.coolculator.game;

import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import sadsido.coolculator.game.Layout.Rect;
import sadsido.coolculator.scenes.GameScene;



public class Timebar extends Rectangle
{
	//*******************************************************************************************

	private GameScene m_scene;
	
	private Rectangle m_topBar;
	private Rectangle m_botBar;
	
	//*******************************************************************************************

	public Timebar(GameScene scene, Rect rect, VertexBufferObjectManager pVBO) 
	{
		super(rect.left, rect.top, rect.width(), rect.height(), pVBO);
		
		m_scene  = scene;	
		m_topBar = new Rectangle(0.0f, 0.0f, rect.width(), rect.height(), pVBO);
		m_botBar = new Rectangle(0.0f, 0.0f, rect.width(), rect.height(), pVBO);
		
		attachChild(m_botBar);
		attachChild(m_topBar);
		
		m_topBar.setColor(Color.RED);
		m_botBar.setColor(Color.WHITE);
	}

	//*******************************************************************************************

	public void playTimeoutAnimation()
	{
		IEntityModifier modifier = new ScaleAtModifier(60.0f, 1.0f, 0.0f, 1.0f, 1.0f, 0.0f, getHeight() / 2.0f) 
		{
			@Override
			protected void onModifierFinished(org.andengine.entity.IEntity pItem)
			{ m_scene.onTimeoutAnimationFinished(); };
		};
		
		m_topBar.registerEntityModifier(modifier);
	}

	public void playResetAnimation()
	{
		m_topBar.clearEntityModifiers();
		
		IEntityModifier modifier = new ScaleAtModifier(0.3f, m_topBar.getScaleX(), 1.0f, 1.0f, 1.0f, 0.0f, getHeight() / 2.0f) 
		{
			@Override
			protected void onModifierFinished(org.andengine.entity.IEntity pItem)
			{  };
		};
		
		m_topBar.registerEntityModifier(modifier);
	}
	
	//*******************************************************************************************
}
