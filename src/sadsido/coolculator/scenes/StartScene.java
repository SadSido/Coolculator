package sadsido.coolculator.scenes;

import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import sadsido.coolculator.MainActivity;
import sadsido.coolculator.R;



// this scene shows main menu and current high score:

public class StartScene extends MenuScene implements IOnMenuItemClickListener
{
	//*******************************************************************************************

	private static final int MENU_START = 0;
	private static final int MENU_HOWTO = 1;

	//*******************************************************************************************
	
	private MainActivity m_activity;
	private Rectangle m_back;
	
	//*******************************************************************************************

	public StartScene()
	{
		super(MainActivity.instance().getCamera());
		
		m_activity = MainActivity.instance();
		
		// setBackground(new Background(0.09804f, 0.6274f, 0));
		
		m_back = new Rectangle(0, 0, mCamera.getWidth(), mCamera.getHeight(), m_activity.getVertexBufferObjectManager());
        m_back.registerEntityModifier(new ColorModifier(0.5f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f));
		
		IMenuItem start = createMenuItem(MENU_START, R.string.menu_start);
		IMenuItem howto = createMenuItem(MENU_HOWTO, R.string.menu_howto);
		
		start.setY(mCamera.getCenterY() - start.getHeight() / 2);
		start.registerEntityModifier(new MoveXModifier(0.5f, - start.getWidth(), mCamera.getCenterX()- start.getWidth() - 20.0f));
		
		howto.setY(mCamera.getCenterY() - start.getHeight() / 2);
		howto.registerEntityModifier(new MoveXModifier(0.5f, mCamera.getWidth(), mCamera.getCenterX() + 20.0f));
		
		attachChild(m_back);
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
	{ return new TextMenuItem(id, m_activity.getMenuFont(), m_activity.getString(textID), m_activity.getVertexBufferObjectManager());}
	
	//*******************************************************************************************
}
