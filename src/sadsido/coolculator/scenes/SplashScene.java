package sadsido.coolculator.scenes;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import sadsido.coolculator.Layout;
import sadsido.coolculator.MainActivity;
import sadsido.coolculator.game.Background;



// this scene shows some intro, then pushes the main menu scene:

public class SplashScene extends Scene implements IOnSceneTouchListener 
{
	//*******************************************************************************************

	MainActivity m_activity;
	Layout m_layout;
	
	//*******************************************************************************************

	public SplashScene()
	{
		m_activity = MainActivity.instance();
		m_layout = m_activity.getLayout();
		
		// create background:
		Background back = new Background(m_layout.rcScreen(), Layout.Rect.Empty, m_activity.getBackgroundTexture(), m_activity.getGradientTexture(), m_activity.getVBO());
		attachChild(back);
		
		// handle touch event:
		setOnSceneTouchListener(this);
		
		// trigger initial animation:
		back.playFadeinAnimation();
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
