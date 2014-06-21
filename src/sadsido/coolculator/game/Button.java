package sadsido.coolculator.game;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.entity.modifier.AlphaModifier;
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
import org.andengine.util.modifier.ease.EaseQuadInOut;
import org.andengine.util.modifier.ease.EaseQuadOut;
import sadsido.coolculator.MainActivity;
import sadsido.coolculator.Layout.Rect;
import sadsido.coolculator.scenes.GameScene;



public class Button extends Sprite
{
	//*******************************************************************************************

	private GameScene m_scene;
	
	private int       m_row;
	private int       m_col;
	
	private int       m_value;
	private Sign      m_sign;

	private Text      m_textval;
	private Text      m_textsig;
	
	//*******************************************************************************************

	public Button(GameScene scene, int row, int col, Rect rect, ITextureRegion region, VertexBufferObjectManager pVBO) 
	{
		super(rect.left, rect.top, rect.width(), rect.height(), region, pVBO);
				
		m_scene  = scene;
		m_row    = row;
		m_col    = col;
				
		m_textval = new Text(0, 0, MainActivity.instance().getButtonFont(), "xxx", pVBO);
		m_textsig = new Text(0, 0, MainActivity.instance().getButtonFont(), "xxx", pVBO);
		
		attachChild(m_textval);
		attachChild(m_textsig);
	}
	
	//*******************************************************************************************

	public int row()
	{ return m_row; }
	
	public int col()
	{ return m_col; }
	
	public int value()
	{ return m_value; }
	
	public Sign sign()
	{ return m_sign; }
	
	public void setRowCol(int row, int col)
	{ m_row = row; m_col = col; }
	
	public void setValueSign(int value, Sign sign)
	{
		m_value = value;
		m_sign  = sign;
		
		m_textval.setText(Integer.toString(value));
		m_textsig.setText(sign.getText());
		m_textsig.setColor(sign.getColor());
		
		final float halfw = getWidth() / 2.0f;
		final float halfh = getHeight() / 2.0f;
		final float space = halfw / 4.0f;
		
		if (sign == Sign.None)
		{
			m_textval.setPosition(halfw - m_textval.getWidth() / 2.0f, halfh - m_textval.getHeight() / 2.0f); 
		}
		else
		{
			m_textval.setPosition(halfw - m_textval.getWidth() - space, halfh - m_textval.getHeight() / 2.0f);
			m_textsig.setPosition(halfw + space, halfh - m_textval.getHeight() / 2.0f);
		}
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
				new ColorModifier(0.08f, Color.WHITE, Const.SelectColor)
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
		IEntityModifier modifier = new ColorModifier(0.3f, Const.SelectColor, Color.WHITE) 
		{
			@Override
			protected void onModifierFinished(org.andengine.entity.IEntity pItem)
			{  };
		};
		
		registerEntityModifier(modifier);
	}
	
	public void playVanishAnimation(float delay, Color color)
	{
		// that's insane!
		IEntityModifier modifier = new SequenceEntityModifier
		(						
			new ColorModifier(0.20f, getColor(), color),
			new ScaleModifier(delay, 1.00f, 1.00f, 1.00f, 1.00f),
			new ScaleModifier(0.10f, 1.00f, 1.00f, 1.00f, 0.05f),
			new ScaleModifier(0.10f, 1.00f, 0.00f, 0.05f, 0.05f)
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
			new MoveYModifier(0.25f, getY(), toY, EaseQuadInOut.getInstance())
		)
		{
			@Override
			protected void onModifierFinished(org.andengine.entity.IEntity pItem)
			{ m_scene.onEmergeAnimationFinished(Button.this); };
		};
		
		registerEntityModifier(modifier);
	}

	public void playEndgameAnimation(float delay, float toY)
	{
		// that's insane!
		IEntityModifier modifier = new SequenceEntityModifier
		(			
			new MoveYModifier(delay, getY(), getY()),	
			new MoveYModifier(0.6f, getY(), toY, EaseQuadInOut.getInstance())
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
