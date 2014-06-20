package sadsido.coolculator.scenes;

import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import sadsido.coolculator.MainActivity;
import sadsido.coolculator.R;
import sadsido.coolculator.game.Background;
import sadsido.coolculator.game.Layout;



// this scene shows main menu and current high score:

public class StartScene extends MenuScene implements IOnMenuItemClickListener
{
	//*******************************************************************************************

	private static final int MENU_START = 0;
	private static final int MENU_HOWTO = 1;

	//*******************************************************************************************
	
	private MainActivity m_activity;
	private Background m_back;
	private Layout m_layout;
	
	//*******************************************************************************************

	public StartScene()
	{
		super(MainActivity.instance().getCamera());
		
		m_activity = MainActivity.instance();
		m_layout = m_activity.getLayout();
		
		// save some coding:
		
		final VertexBufferObjectManager VBO = m_activity.getVertexBufferObjectManager();
		
		// init background:
		
		m_back = new Background(m_layout.rcScreen(), m_layout.rcScreen(), m_activity.getBackgroundTexture(), m_activity.getGradientTexture(), VBO);
		attachChild(m_back);

		m_back.playFadeinAnimation();
		
		// init menu buttons: 
		
		IMenuItem start = createMenuItem(MENU_START, R.string.menu_start);
		IMenuItem howto = createMenuItem(MENU_HOWTO, R.string.menu_howto);
		
		start.setY(mCamera.getCenterY() - start.getHeight() / 2);
		start.registerEntityModifier(new MoveXModifier(0.5f, mCamera.getWidth(), mCamera.getWidth() / 3.0f));
		
		howto.setY(mCamera.getCenterY() + start.getHeight() / 2 + 20.0f);
		howto.registerEntityModifier(new MoveXModifier(0.6f, mCamera.getWidth(), mCamera.getWidth() / 3.0f));
		
		addMenuItem(start);
		addMenuItem(howto);
		
		setOnMenuItemClickListener(this);	
	}
	
	//*******************************************************************************************

	@Override
	public boolean onMenuItemClicked(MenuScene scene, IMenuItem item, float localx, float localy) 
	{
		switch (item.getID())
		{
		case MENU_START:
			
			m_activity.setScene(new GameScene());
			return true;
		}
		
		// unknown menu:
		return false;
	}

	//*******************************************************************************************

	private IMenuItem createMenuItem(int id, int textID)
	{ return new TextMenuItem(id, m_activity.getButtonFont(), m_activity.getString(textID), m_activity.getVertexBufferObjectManager());}
	
	//*******************************************************************************************
}
