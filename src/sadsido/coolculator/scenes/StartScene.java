package sadsido.coolculator.scenes;

import org.andengine.entity.modifier.ColorModifier;
import org.andengine.entity.modifier.MoveXModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import sadsido.coolculator.Layout;
import sadsido.coolculator.Layout.Rect;
import sadsido.coolculator.MainActivity;
import sadsido.coolculator.R;
import sadsido.coolculator.game.Background;



// this scene shows main menu and current high score:

public class StartScene extends MenuScene implements IOnMenuItemClickListener
{
	//*******************************************************************************************

	private static final int MENU_START = 0;
	private static final int MENU_HOWTO = 1;

	//*******************************************************************************************

	private MainActivity m_activity;
	
	//*******************************************************************************************

	public StartScene()
	{
		super(MainActivity.instance().getCamera());		
		m_activity = MainActivity.instance();
		
		final Layout layout = m_activity.getLayout();
		final VertexBufferObjectManager VBO = m_activity.getVBO();
		
		// create background:
		
		Background back = new Background(layout.rcScreen(), layout.rcScreen(), m_activity.getBackgroundTexture(), m_activity.getGradientTexture(), VBO);
		attachChild(back);
		
		// create rectangle:
		
		Rect rcMenu = layout.rcMenu();
		
		Rectangle mrect = new Rectangle(rcMenu.left, rcMenu.top, rcMenu.width(), rcMenu.height(), VBO);
		mrect.setAlpha(0.1f);

		attachChild(mrect);

		// create menu items: 
		
		IMenuItem start = new TextMenuItem(MENU_START, m_activity.getButtonFont(), m_activity.getString(R.string.menu_start), VBO);
		IMenuItem howto = new TextMenuItem(MENU_HOWTO, m_activity.getButtonFont(), m_activity.getString(R.string.menu_howto), VBO);
		
		start.setY(rcMenu.centerY() - start.getHeight() - 10f);
		howto.setY(rcMenu.centerY() + 10f);
		
		start.setAlpha(0.8f);
		howto.setAlpha(0.8f);

		addMenuItem(start);
		addMenuItem(howto);
		
		// assign menu listener:
		setOnMenuItemClickListener(this);	

		// trigger initial animations:
		mrect.registerEntityModifier(new ScaleModifier(0.20f, 1f, 1f, 0f, 1f));
		start.registerEntityModifier(new MoveXModifier(0.30f, mCamera.getWidth(), mCamera.getWidth() / 3.0f));		
		howto.registerEntityModifier(new MoveXModifier(0.35f, mCamera.getWidth(), mCamera.getWidth() / 3.0f));
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
}
