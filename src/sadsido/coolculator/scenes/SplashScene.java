package sadsido.coolculator.scenes;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;

import sadsido.coolculator.MainActivity;



// this scene shows some intro, then pushes the main menu scene:

public class SplashScene extends Scene implements IOnSceneTouchListener 
{
	//*******************************************************************************************

	MainActivity m_activity;
	
	//*******************************************************************************************

	public SplashScene()
	{
		m_activity = MainActivity.instance();
		
		//setBackground(new Background(0.09804f, 0.6274f, 0));
		setOnSceneTouchListener(this);
	}

	//*******************************************************************************************

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) 
	{
		m_activity.setScene(new StartScene());
		return true;
	}
	
	//*******************************************************************************************
}
