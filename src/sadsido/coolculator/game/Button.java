package sadsido.coolculator.game;

import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveYModifier;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.ScaleAtModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;
import org.andengine.util.modifier.ease.EaseQuadIn;
import org.andengine.util.modifier.ease.EaseQuadOut;

import sadsido.coolculator.MainActivity;
import sadsido.coolculator.scenes.GameScene;
import android.annotation.SuppressLint;



public class Button extends Sprite
{
	//*******************************************************************************************

	public static final int SIGN_MULT   = 0;
	public static final int SIGN_PLUS   = 1;
	public static final int SIGN_MINUS  = 2;
	public static final int SIGN_EQUALS = 3;
	public static final int SIGN_RESULT = 4;
		
	public static final String SignToString(int sign)
	{
		switch (sign)
		{
		case SIGN_MULT: return "x";
		case SIGN_PLUS: return "+";
		case SIGN_MINUS: return "-";
		case SIGN_EQUALS: return "=";
		}
		
		return "";
	}
	
	//*******************************************************************************************

	private GameScene m_scene;
	private int       m_row;
	private int       m_col;
	private Text      m_text;
	private int       m_value;
	private int       m_sign;
	
	//*******************************************************************************************

	public Button(GameScene scene, int row, int col, float pX, float pY, float pWidth, float pHeight, ITextureRegion region, VertexBufferObjectManager pVBO) 
	{
		super(pX, pY, pWidth, pHeight, region, pVBO);
				
		m_scene  = scene;
		m_row    = row;
		m_col    = col;
		
		setColor(Color.WHITE);
		
		
		m_text = new Text(0, 0, MainActivity.instance().getMenuFont(), "xxx", pVBO);
		m_text.setColor(Color.BLACK);
		attachChild(m_text);
	}
	
	//*******************************************************************************************

	public int row()
	{ return m_row; }
	
	public int col()
	{ return m_col; }
	
	public int value()
	{ return m_value; }
	
	public int sign()
	{ return m_sign; }
	
	public void setRowCol(int row, int col)
	{ m_row = row; m_col = col; }
	
	@SuppressLint("DefaultLocale")
	public void setValueSign(int value, int sign)
	{
		m_value = value;
		m_sign  = sign;
		
		String text = String.format("%d %s", m_value, SignToString(m_sign));
		m_text.setText(text);
	}
	
	//*******************************************************************************************

	@Override
	public boolean onAreaTouched(TouchEvent pSceneTouchEvent, float localX, float localY) 
	{
		m_scene.onButtonTouched(this);
		return super.onAreaTouched(pSceneTouchEvent, localX, localY);
	}
	
	//*******************************************************************************************

	public void playSelectAnimation()
	{
		// that's insane!
		IEntityModifier modifier = new SequenceEntityModifier
		(
			new ParallelEntityModifier
			(
				new ScaleAtModifier(0.08f, 1.0f, 0.9f, getWidth() / 2.0f, getHeight() / 2.0f),
				new ColorModifier(0.08f, Color.WHITE, Color.RED)
			),
			new ScaleAtModifier(0.08f, 0.9f, 1.0f, getWidth() / 2.0f, getHeight() / 2.0f)
		) 
		{
			@Override
			protected void onModifierFinished(org.andengine.entity.IEntity pItem)
			{ m_scene.onSelectAnimationFinished(Button.this); };
		};
		
		registerEntityModifier(modifier);
	}
	
	public void playUnselectAnimation()
	{
		// that's insane!
		IEntityModifier modifier = new ColorModifier(0.3f, Color.RED, Color.WHITE) 
		{
			@Override
			protected void onModifierFinished(org.andengine.entity.IEntity pItem)
			{  };
		};
		
		registerEntityModifier(modifier);
	}
	
	public void playVanishAnimation(float delay)
	{
		// that's insane!
		IEntityModifier modifier = new SequenceEntityModifier
		(			
			new ScaleAtModifier(delay, 1.0f, 1.0f, getWidth() / 2.0f, getHeight() / 2.0f),	
			new ScaleModifier(0.1f, 1.0f, 1.0f, 1.00f, 0.05f),
			new ScaleModifier(0.1f, 1.0f, 0.0f, 0.05f, 0.05f)
		)
		{
			@Override
			protected void onModifierFinished(org.andengine.entity.IEntity pItem)
			{ m_scene.onVanishAnimationFinished(Button.this); };
		};
		
		registerEntityModifier(modifier);
	}
	
	public void playFallingAnimation(float delay, float distance)
	{
		IEntityModifier modifier = new SequenceEntityModifier
		(			
			new MoveYModifier(delay, getY(), getY()),	
			new MoveYModifier(0.25f, getY(), getY() + distance, EaseQuadIn.getInstance())	
		)
		{
			@Override
			protected void onModifierFinished(org.andengine.entity.IEntity pItem)
			{ m_scene.onFallingAnimationFinished(Button.this); };
		};
		
		registerEntityModifier(modifier);		
	}
	
	public void playEmergeAnimation(float delay, float toY)
	{
		// that's insane!
		IEntityModifier modifier = new SequenceEntityModifier
		(			
			new MoveYModifier(delay, getY(), getY()),	
			new MoveYModifier(0.15f, getY(), toY, EaseQuadOut.getInstance())
		)
		{
			@Override
			protected void onModifierFinished(org.andengine.entity.IEntity pItem)
			{ m_scene.onEmergeAnimationFinished(Button.this); };
		};
		
		registerEntityModifier(modifier);
	}

	public void playEndgameAnimation(float delay)
	{
		// that's insane!
		IEntityModifier modifier = new SequenceEntityModifier
		(			
			new ScaleAtModifier(delay, 1.0f, 1.0f, getWidth() / 2.0f, getHeight() / 2.0f),	
			new ScaleModifier(0.1f, 1.0f, 1.0f, 1.00f, 0.05f),
			new ScaleModifier(0.1f, 1.0f, 0.0f, 0.05f, 0.05f)
		)
		{
			@Override
			protected void onModifierFinished(org.andengine.entity.IEntity pItem)
			{ m_scene.onEndgameAnimationFinished(Button.this); };
		};
		
		registerEntityModifier(modifier);
	}
	
	//*******************************************************************************************
}
