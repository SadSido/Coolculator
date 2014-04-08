package sadsido.coolculator.scenes;

import java.util.HashSet;
import java.util.Set;

import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import sadsido.coolculator.MainActivity;
import sadsido.coolculator.game.Button;
import sadsido.coolculator.game.Const;
import sadsido.coolculator.game.Generator;
import sadsido.coolculator.game.Layout;
import sadsido.coolculator.game.Layout.Rect;
import sadsido.coolculator.game.Score;
import sadsido.coolculator.game.Timebar;



public class GameScene extends Scene 
{
	//*******************************************************************************************

	MainActivity m_activity;
	
	private static final int NO_SELECTION   = -1;
	
	//*******************************************************************************************

	// layouter:
	
	private Layout m_layout;
	
	// array of coolculator buttons:
	
	private Button[][] m_buttons;
	
	// selection index in every column:
	
	private int[] m_selections;
	
	// set of buttons, currently animated:
	
	private Set<Button> m_animationSet;
	
	// set of available digits to generate:
	
	private Generator[] m_gens;
	
	// sprite to show score:
	
	private Score m_score;
	private Score m_record;
	
	// private Score m_record;

	// timebar to show remaining time:
	
	private Timebar m_timebar;
	
	//*******************************************************************************************

	public GameScene()
	{
		m_activity     = MainActivity.instance();
		m_layout       = new Layout(m_activity.getCamera());
		m_buttons      = new Button[Const.Rows][Const.Cols];
		m_selections   = new int[Const.Cols];
		m_animationSet = new HashSet<Button>();
		m_gens         = new Generator[Const.Cols];
		
		setBackground(new Background(0.3f, 0.3f, 0.5f));
		
		Rect rcTime    = m_layout.rcTimebar();
		m_timebar      = new Timebar(this, rcTime.left, rcTime.top, rcTime.width(), rcTime.height(), m_activity.getVertexBufferObjectManager());

		attachChild(m_timebar);
		m_timebar.playTimeoutAnimation();
		
		// init generators:
		
		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		{ m_gens[colNo] = new Generator(colNo);	}
				
		// init buttons:
		
		for (int rowNo = 0; rowNo < Const.Rows; ++ rowNo)
		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		{
			Rect rc = m_layout.rcButton(colNo, rowNo);
			
			m_buttons[rowNo][colNo] = new Button(this, rowNo, colNo, rc, m_activity.getButtonTexture(), m_activity.getVertexBufferObjectManager());
		
			attachChild(m_buttons[rowNo][colNo]);
			registerTouchArea(m_buttons[rowNo][colNo]);
			
			// generate text for the button:
			
			final int val = m_gens[colNo].pickValue();
			final int sgn = m_gens[colNo].pickSign(val);
			
			m_buttons[rowNo][colNo].setValueSign(val, sgn);
		}

		// init select indices:
		
		resetSelection();

		// init score areas:
		
		m_score  = new Score(000, m_layout.rcScore(),  MainActivity.instance().getMenuFont(), m_activity.getVertexBufferObjectManager());
		m_record = new Score(999, m_layout.rcRecord(), MainActivity.instance().getMenuFont(), m_activity.getVertexBufferObjectManager());
	
		attachChild(m_score);
		attachChild(m_record);
	}

	//*******************************************************************************************

	// handle interaction with buttons here:
	
	public void onButtonTouched(Button button)
	{
		final int col = button.col();
		final int row = button.row();
		
		// we have an animation - reject all interaction:
		
		if (hasAnimation())
		{ return; }
		
		// this button is already selected, do nothing:
		
		if (m_selections[col] == row)
		{ return; }
		
		// we have a valid selection, drop it:
		
		if (m_selections[col] != NO_SELECTION)
		{ m_buttons[m_selections[col]][col].playUnselectAnimation(); }
		
		// start animating button selection:
		
		m_animationSet.add(button);
		button.playSelectAnimation();
	}
	
	//*******************************************************************************************

	// button animation callbacks:
	
	public void onSelectAnimationFinished(Button button)
	{
		final int row = button.row();
		final int col = button.col();

		// pop animation out of the set:
		
		m_animationSet.remove(button);
		
		// remember selection in current col:
		
		m_selections[col] = row;
		
		// if we have full selection, vanish the buttons:
		
		if (hasFullSelection())
		{
			// reset timebar anyway:
			m_timebar.playResetAnimation();
			
			// vanish selected buttons:
			for (int colNo = 0; colNo < Const.Cols; ++ colNo)
			{ 
				m_animationSet.add(m_buttons[m_selections[colNo]][colNo]);
				m_buttons[m_selections[colNo]][colNo].playVanishAnimation(0.03f * colNo);
			}
		}
	}
	
	public void onVanishAnimationFinished(Button button)
	{
		// pop animation out of the set:
		
		m_animationSet.remove(button);
		
		// vanish animation complete, 
		
		if (!hasAnimation())
		{
			// modify current score:
			final int delta = m_buttons[m_selections[3]][3].value();
			if (isEquationValid()) { m_score.inc(delta); } else { m_score.dec(delta); }
			
			// must run fall animation:
			if (firstRowSelected())
			{
				// no falling animation required:
				onFallingAnimationFinished(null);
			}
			else
			{
				// must fill in the gaps by falling down:
				for (int colNo = 0; colNo < Const.Cols; ++ colNo)
				{
					final int selection = m_selections[colNo];
					for (int rowNo = 0; rowNo < selection; ++ rowNo)
					{
						m_animationSet.add(m_buttons[rowNo][colNo]);
						m_buttons[rowNo][colNo].playFallingAnimation((selection - rowNo - 1) * 0.03f, m_layout.rcButton().height());
					}
				}
			}
		}
	}
		
	public void onFallingAnimationFinished(Button button)
	{
		// pop animation out of the set:
		
		if (button != null)
		{ m_animationSet.remove(button); }

		// falling animation complete, must run emerge animation
		
		if (!hasAnimation())
		{
			for (int colNo = 0; colNo < Const.Cols; ++ colNo)
			{
				final Button    btn = m_buttons[m_selections[colNo]][colNo];
				final Generator gen = m_gens[colNo];
				
				// get new value for the button:
				
				gen.pushValue(btn.value());				
				final int val = gen.pickValue();
				final int sgn = gen.pickSign(val);
				
				// reuse button as a top-level one:
				
				btn.setScaleX(1.0f);
				btn.setScaleY(1.0f);
				btn.setY(-btn.getHeight());
				btn.setColor(Color.WHITE);
				btn.setValueSign(val, sgn);				
				
				// play emerge animation:
				m_animationSet.add(btn);
				btn.playEmergeAnimation(0.03f * colNo, m_layout.rcButtons().top);				
			}
		}
	}
	
	public void onEmergeAnimationFinished(Button button)
	{
		// pop animation out of the set:
		
		m_animationSet.remove(button);

		// ...
		
		if (!hasAnimation())
		{
			// we have to re-assign correct indices to all buttons:
			for (int colNo = 0; colNo < Const.Cols; ++ colNo)
			{
				final int selection = m_selections[colNo];
				Button btn = m_buttons[selection][colNo];
				
				for (int rowNo = selection; rowNo > 0; -- rowNo)
				{ 
					m_buttons[rowNo][colNo] = m_buttons[rowNo - 1][colNo];
					m_buttons[rowNo][colNo].setRowCol(rowNo, colNo);
				}
			
				m_buttons[0][colNo] = btn;
				m_buttons[0][colNo].setRowCol(0, colNo);
			}
			
			// reset current selection:
			resetSelection();
			
			// restart timebar:
			m_timebar.playTimeoutAnimation();
		}
	}
	
	public void onEndgameAnimationFinished(Button button)
	{
		// pop animation out of the set:
		
		m_animationSet.remove(button);

		// ...
		
		if (!hasAnimation())
		{
			m_activity.setScene(new StartScene());
		}
	}
	
	public void onTimeoutAnimationFinished()
	{
		// prepare to terminate:
		m_animationSet.clear();
		
		// seems like the game is over:
		for (int rowNo = 0; rowNo < Const.Rows; ++ rowNo)
		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		{
			m_animationSet.add(m_buttons[rowNo][colNo]);
			m_buttons[rowNo][colNo].clearEntityModifiers();
			m_buttons[rowNo][colNo].playEndgameAnimation((rowNo + colNo) * 0.1f);
		}		
	}
	
	//*******************************************************************************************

	// animation helpers:
	
	private boolean hasAnimation()
	{ return !m_animationSet.isEmpty(); }
	
	// selection helpers:
	
	private void resetSelection()
	{
		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		{ m_selections[colNo] = NO_SELECTION; }	
	}
	
	private boolean hasFullSelection()
	{
		for (int index : m_selections)
		{ if (index == NO_SELECTION) return false; }
		
		return true;
	}
	
	private boolean firstRowSelected()
	{
		for (int index : m_selections)
		{ if (index != 0) return false; }
		
		return true;
	}
	
	private int applySign(int lval, int sign, int rval)
	{
		switch (sign)
		{
		case Button.SIGN_MULT:  return lval * rval;
		case Button.SIGN_PLUS:  return lval + rval;
		case Button.SIGN_MINUS: return lval - rval;
		}
		
		return 0;
	}
	
	private boolean isEquationValid()
	{
		if (!hasFullSelection())
		{ return false; }
		
		Button b0 = m_buttons[m_selections[0]][0];
		Button b1 = m_buttons[m_selections[1]][1];
		Button b2 = m_buttons[m_selections[2]][2];
		Button b3 = m_buttons[m_selections[3]][3];
		
		// here operator priority affects evaluation:
		
		final int result = (b1.sign() == Button.SIGN_MULT)
				? applySign(b0.value(), b0.sign(), applySign(b1.value(), b1.sign(), b2.value()))
				: applySign(applySign(b0.value(), b0.sign(), b1.value()), b1.sign(), b2.value());
				
		// do we match the result?
				
		return result == b3.value();	
	}
		
	//*******************************************************************************************
}
