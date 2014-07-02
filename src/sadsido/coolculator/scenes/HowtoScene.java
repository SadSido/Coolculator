package sadsido.coolculator.scenes;

import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.text.Text;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.modifier.SequenceModifier;
import org.andengine.util.modifier.ease.EaseQuadIn;

import sadsido.coolculator.R;
import sadsido.coolculator.Layout;
import sadsido.coolculator.MainActivity;
import sadsido.coolculator.game.Background;


// this scene show brief instructions about the game:

public class HowtoScene extends Scene implements IOnSceneTouchListener 
{
	//*******************************************************************************************

	MainActivity m_activity;
	Layout m_layout;
	
	Text [] m_texts;
	int m_index;
	
	boolean m_uilock;
	
	//*******************************************************************************************

	public HowtoScene()
	{
		m_activity = MainActivity.instance();
		m_layout = m_activity.getLayout();
		m_uilock = false;
		m_index  = 0;
		
		// create background:
		Background back = new Background(m_layout.rcScreen(), Layout.Rect.Empty, m_activity.getBackgroundTexture(), m_activity.getGradientTexture(), m_activity.getVBO());
		attachChild(back);
		
		// create texts:
		String [] instructions = m_activity.getResources().getStringArray(R.array.instructions);
		m_texts = new Text[instructions.length];
		
		for (int no = 0; no < instructions.length; ++ no)
		{
			m_texts[no] = new Text(0,0, m_activity.getMenuFont(), instructions[no], m_activity.getVBO());
			attachChild(m_texts[no]);
			
			m_texts[no].setX(m_layout.width());
			m_texts[no].setY((m_layout.height() - m_texts[no].getHeight()) / 2f);
		}		
		
		// handle touch event:
		setOnSceneTouchListener(this);
		
		// play initial animation:
		playSwitchTextAnimation(null, m_texts[0]);
	}

	//*******************************************************************************************

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) 
	{
		if (m_uilock)
		{ return false; }
		
		// proceed to next text:
		
		++ m_index;
		
		Text awayText = (m_index > 0) ? m_texts[m_index - 1] : null;
		Text intoText = (m_index < m_texts.length) ? m_texts[m_index] : null;
		
		playSwitchTextAnimation(awayText, intoText);
		return true;
	}
	
	//*******************************************************************************************

	public void onAwayAnimationComplete()
	{
		// check, whether this was the last text:		
		if (m_index == m_texts.length)
		{ m_activity.setScene(new StartScene()); }
	}

	public void onIntoAnimationComplete()
	{
		// release ui lock, can handle touch event:
		m_uilock = false;
	}
	
	//*******************************************************************************************

	private void playSwitchTextAnimation(Text awayText, Text intoText)
	{
		m_uilock = true;
		if (awayText != null)
		{
			IEntityModifier modif = new MoveXModifier(0.35f, awayText.getX(), - awayText.getWidth(), EaseQuadIn.getInstance())
			{
				@Override
				protected void onModifierFinished(IEntity pItem)
				{ onAwayAnimationComplete(); };
			};
			
			awayText.registerEntityModifier(modif);
		}		
		if (intoText != null)
		{
			IEntityModifier modif = new SequenceEntityModifier
			( 
				new MoveXModifier(0.20f, intoText.getX(), intoText.getX()),
				new MoveXModifier(0.35f, intoText.getX(), (m_layout.width() - intoText.getWidth()) / 2f, EaseQuadIn.getInstance())
			)
			{
				@Override
				protected void onModifierFinished(IEntity pItem)
				{ onIntoAnimationComplete(); };
			};
			
			intoText.registerEntityModifier(modif);
		}
	}
	
	//*******************************************************************************************
}