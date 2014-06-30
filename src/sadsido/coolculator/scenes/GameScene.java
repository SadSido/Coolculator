package sadsido.coolculator.scenes;

import java.util.HashSet;
import java.util.Set;

import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.color.Color;

import sadsido.coolculator.Layout;
import sadsido.coolculator.Layout.Rect;
import sadsido.coolculator.MainActivity;
import sadsido.coolculator.game.Background;
import sadsido.coolculator.game.Button;
import sadsido.coolculator.game.Const;
import sadsido.coolculator.game.Score;
import sadsido.coolculator.game.Sign;
import sadsido.coolculator.game.Timebar;
import sadsido.coolculator.pick.SignPicker;
import sadsido.coolculator.pick.ValuePicker;



public class GameScene extends Scene 
{
	//*******************************************************************************************

	private static final int NO_SELECTION   = -1;
	
	//*******************************************************************************************

	private MainActivity m_activity;
	private Layout m_layout;
	
	// button management:
	private Button[][] m_buttons;
	private int[] m_selections;
	private Set<Button> m_animationSet;
	
	// button generators:	
	private ValuePicker [] m_vpick;
	private SignPicker  [] m_spick;
	
	// score management:	
	private int   m_goal;
	private Score m_score;
	private Score m_record;
	
	// additional sprites:
	private Background m_back;
	private Timebar m_timebar;
		
	
	//*******************************************************************************************

	public GameScene()
	{
		m_activity      = MainActivity.instance();
		m_layout        = m_activity.getLayout();
		m_buttons       = new Button[Const.Rows][Const.Cols];
		m_selections    = new int[Const.Cols];
		m_animationSet  = new HashSet<Button>();
		m_vpick         = new ValuePicker[Const.Cols];
		m_spick         = new SignPicker[Const.Cols];
		
		// save some coding:
		final VertexBufferObjectManager VBO = m_activity.getVertexBufferObjectManager();
		
		// create background:
		m_back = new Background(m_layout.rcScreen(), m_layout.rcTopBar(), m_activity.getBackgroundTexture(), m_activity.getGradientTexture(), VBO);
		attachChild(m_back);
		
		// create timebar:
		m_timebar = new Timebar(this, m_layout.rcTimebar(), m_activity.getTimebarTexture(), VBO);
		attachChild(m_timebar);
		
		// create generators:
		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		{ 
			m_vpick[colNo] = ValuePicker.create(colNo);	
			m_spick[colNo] = SignPicker.create(colNo);	
		}
				
		// create buttons:
		for (int rowNo = 0; rowNo < Const.Rows; ++ rowNo)
		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		{
			Rect rc = m_layout.rcButton(colNo, rowNo);
			
			m_buttons[rowNo][colNo] = new Button(this, rowNo, colNo, rc, m_activity.getButtonTexture(), VBO);
		
			attachChild(m_buttons[rowNo][colNo]);
			registerTouchArea(m_buttons[rowNo][colNo]);
			
			// generate text for the button:
			
			final int  val = m_vpick[colNo].pickValue();
			final Sign sgn = m_spick[colNo].pickSign(val);
			
			m_buttons[rowNo][colNo].setValueSign(val, sgn);
		}

		// initialize selection:
		resetSelection();

		// create score areas:
		final int score  = 0;
		final int record = m_activity.getSettings().loadHighscore();
		
		m_goal   = Const.ScoreGoal;
		m_score  = new Score(score,  m_layout.rcScore(),  Score.Align.Left,  m_activity.getMenuFont(), m_activity.getScoreTexture(),  VBO);
		m_record = new Score(record, m_layout.rcRecord(), Score.Align.Right, m_activity.getMenuFont(), m_activity.getRecordTexture(), VBO);
	
		attachChild(m_score);
		attachChild(m_record);
		
		// run the timer:
		startTimebar();
	}

	//*******************************************************************************************

	// button interaction:
	
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
		// pop animation out of the set:		
		m_animationSet.remove(button);

		final int row = button.row();
		final int col = button.col();
		
		// remember selection in current col:		
		m_selections[col] = row;
		
		// if we have complete equation, remove it:		
		if (hasFullSelection())
		{
			final boolean isValid = isEquationValid();
			final Color color = isValid ? Const.CorrectColor : Const.WrongColor;
			
			// vanish selected buttons:
			for (int colNo = 0; colNo < Const.Cols; ++ colNo)
			{ 
				final Button selBtn = m_buttons[m_selections[colNo]][colNo];
				
				m_animationSet.add(selBtn);
				selBtn.playRemoveEquationAnimation(0.05f * colNo, color);
			}
		}
	}
	
	public void onRemoveEquationAnimationFinished(Button button)
	{
		// animation guard:				
		m_animationSet.remove(button);
		if (hasAnimation()) { return; }
		
		final boolean isValid = isEquationValid();		
		final boolean lastRow = (m_selections[0] == Const.LastRow) && singleRowSelected();
		
		final int delta  = m_buttons[m_selections[3]][3].value();
		final int bonus  = (isValid) ? calculateBonus() : 1;
		final int sign   = (isValid) ? +1 : -1; 
			
		// modify current score:
		m_score.change(sign * bonus * delta);
		
		// check, whether we reached high score:
		if (m_score.score() > m_record.score())
		{
			m_record.change(m_score.score() - m_record.score());
			m_activity.getSettings().saveHighscore(m_record.score());
		}
		
		// check, whether we reached new level:
		if (m_score.score() >= m_goal)
		{
			// calculate new level params:
			m_goal = (m_score.level() + 1) * Const.ScoreGoal;

			// stop the timer:
			stopTimebar();
			
			// generate end level animation:
			m_back.playLevelEndAnimation();
			
			for (int colNo = 0; colNo < Const.Cols; ++ colNo)
			for (int rowNo = 0; rowNo < Const.Rows; ++ rowNo)
			{
				m_animationSet.add(m_buttons[rowNo][colNo]);
				m_buttons[rowNo][colNo].playLevelCompleteAnimation(0.1f * colNo, m_buttons[rowNo][colNo].getX() - m_layout.width());
			}
			
			return;
		}
		
		// no new level, and no gaps to fill:
		if (lastRow)
		{
			onFillGapsAnimationFinished(null);
			return;
		}

		// must fill in the gaps by moving upwards:
		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		{
			final int selection = m_selections[colNo];
			for (int rowNo = (selection + 1); rowNo < Const.Rows; ++ rowNo)
			{
				m_animationSet.add(m_buttons[rowNo][colNo]);
				m_buttons[rowNo][colNo].playFillGapsAnimation((rowNo - selection - 1) * 0.03f, - m_layout.rcButton().height());
			}
		}
	}
		
	public void onLevelCompleteAnimationFinished(Button button)
	{
		// animation guard:		
		m_animationSet.remove(button);
		if (hasAnimation()) { return; }
		
		// manually update buttons:
		reinitSelectedButtons();		
		updateButtonIndices();
		updateButtonPositions();		

		resetSelection();
		resetTimebar();
		
		// generate new level animation:
		m_back.playLevelBeginAnimation(m_score.level());

		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		for (int rowNo = 0; rowNo < Const.Rows; ++ rowNo)
		{
			Rect rect = m_layout.rcButton(colNo, rowNo);
			
			m_animationSet.add(m_buttons[rowNo][colNo]);
			m_buttons[rowNo][colNo].playStartLevelAnimation(0.1f * colNo, m_layout.width() + rect.left, rect.left);
		}
	}
	
	public void onStartLevelAnimationFinished(Button button)
	{
		// animation guard:		
		m_animationSet.remove(button);
		if (hasAnimation()) { return; }
		
		// start the timer:		
		startTimebar();
	}
	
	public void onFillGapsAnimationFinished(Button button)
	{
		// animation guard:		
		if (button != null)
		{ 
			m_animationSet.remove(button);
			if (hasAnimation()) { return; }
		}

		// reuse selected buttons:
		reinitSelectedButtons();
		
		// move new buttons to bottom:
		final float flyFr = m_activity.getCamera().getHeight();
		final float flyTo = m_layout.rcButtons().bottom - m_layout.rcButton().height();

		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		{
			final Button btn = m_buttons[m_selections[colNo]][colNo];
			m_animationSet.add(btn);
						
			btn.setY(flyFr);
			btn.playPopupAnimation(0.03f * colNo, flyTo);				
		}
	}
	
	public void onPopupAnimationFinished(Button button)
	{
		// animation guard:
		m_animationSet.remove(button);
		if (hasAnimation()) { return; }

		// re-index button and selection:		
		updateButtonIndices();
		resetSelection();
	}
		
	public void onGameOverAnimationFinished(Button button)
	{
		// animation guard:		
		m_animationSet.remove(button);
		if (hasAnimation()) { return; }

		// reset to main menu:
		m_activity.setScene(new StartScene());
	}
	
	public void onTimeoutAnimationFinished()
	{
		// prepare to terminate:
		m_animationSet.clear();
		
		// seems like the game is over:
		float delay = 0.0f;
		for (int rowNo = Const.LastRow; rowNo >= 0; -- rowNo)
		for (int colNo = Const.LastCol; colNo >= 0; -- colNo)
		{
			Button button = m_buttons[rowNo][colNo];
			m_animationSet.add(button);
			
			button.clearEntityModifiers();
			button.playGameOverAnimation(delay, button.getY() + m_activity.getCamera().getHeight());
			
			delay += (float)Math.random() / 10.0f;
		}		
	}
	
	//*******************************************************************************************

	public void resetTimebar()
	{ m_timebar.reset(); }
	
	public void stopTimebar()
	{ m_timebar.stop(); }
	
	public void startTimebar()
	{
		// time limit decreases the first 10 levels:
		final int power  = Math.min(Const.MaxTimePow, m_score.level());
		final float time = Const.StartTime * (float) Math.pow(Const.TimeFactor, power);
		
		// must set timeout for the new level:
		m_timebar.start(time);
	}
	
	//*******************************************************************************************

	// animation helpers:
	
	private boolean hasAnimation()
	{ return !m_animationSet.isEmpty(); }
	
	private void reinitSelectedButtons()
	{
		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		{
			final Button button = m_buttons[m_selections[colNo]][colNo];
			
			// get new value for the button:			
			m_vpick[colNo].pushValue(button.value());				
			
			final int  val = m_vpick[colNo].pickValue();
			final Sign sgn = m_spick[colNo].pickSign(val);
			
			button.setValueSign(val, sgn);				

			// restore button geometry and color:			
			button.setColor(Color.WHITE);
			button.setScaleX(1.0f);
			button.setScaleY(1.0f);
		}
	}
	
	private void updateButtonIndices()
	{
		// when we push selection at the bottom, all buttons
		// after selection should shift indices:
		
		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		{
			final int selection = m_selections[colNo];
			final Button button = m_buttons[selection][colNo];
			
			for (int rowNo = selection; rowNo < (Const.Rows - 1); ++ rowNo)
			{ 
				m_buttons[rowNo][colNo] = m_buttons[rowNo + 1][colNo];
				m_buttons[rowNo][colNo].setRowCol(rowNo, colNo);
			}
		
			// selected buttons then should be placed at the bottom:			
			m_buttons[Const.Rows - 1][colNo] = button;
			m_buttons[Const.Rows - 1][colNo].setRowCol(Const.Rows - 1, colNo);
		}
	}

	private void updateButtonPositions()
	{
		// sometimes the buttons are re-initialized without animation,
		// so we must ensure, that all the positions are correct:
		
		for (int rowNo = 0; rowNo < Const.Rows; ++ rowNo)
		for (int colNo = 0; colNo < Const.Cols; ++ colNo)
		{
			Rect rect = m_layout.rcButton(colNo, rowNo);
			m_buttons[rowNo][colNo].setY(rect.top);
		}
	}
	
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
	
	private boolean singleRowSelected()
	{
		final int first = m_selections[0];
		
		for (int index : m_selections)
		{ if (index != first) return false; }
		
		return first != NO_SELECTION;
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
		
		final int result = (b1.sign() == Sign.Multiply)
				? b0.sign().apply(b0.value(), b1.sign().apply(b1.value(), b2.value()))
				: b1.sign().apply(b0.sign().apply(b0.value(), b1.value()), b2.value());
				
		// do we match the result?
				
		return result == b3.value();	
	}
		
	private int calculateBonus()
	{
		int bonus = 1;
		
		if (m_buttons[m_selections[0]][0].sign() == Sign.Multiply) { bonus *= 2; }
		if (m_buttons[m_selections[1]][1].sign() == Sign.Multiply) { bonus *= 2; }
		
		return bonus;
	}
	
	//*******************************************************************************************
}
