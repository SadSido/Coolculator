package sadsido.coolculator;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import sadsido.coolculator.scenes.SplashScene;
import android.graphics.Color;



// main activity for the game here:

public class MainActivity extends SimpleBaseGameActivity 
{
	//*******************************************************************************************

	private static MainActivity s_instance;
	
	//*******************************************************************************************

	private Camera m_camera; 
	private Font m_menuFont;
	
	private ITextureRegion m_texButton;
	
	//*******************************************************************************************
	
	public MainActivity()
	{ s_instance = this; }

	public static MainActivity instance()
	{ return s_instance; }
	
	//*******************************************************************************************

	public Camera getCamera()
	{ return m_camera; }
	
	public Font getMenuFont()
	{ return m_menuFont; }
	
	public void setScene(Scene scene)
	{ getEngine().setScene(scene); }
	
	public ITextureRegion getButtonTexture()
	{ return m_texButton; }
	
	//*******************************************************************************************

	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		m_camera = new Camera(0, 0, 1920, 1080);
		return new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), m_camera);	
	}

	@Override
	protected void onCreateResources() 
	{
		m_menuFont = FontFactory.createFromAsset(getFontManager(), getTextureManager(), 512, 512, TextureOptions.BILINEAR, getAssets(), "airborne.ttf", 100, true, Color.WHITE);
		m_menuFont.load();
		
		BuildableBitmapTextureAtlas atlas = new BuildableBitmapTextureAtlas(getTextureManager(), 64, 64, TextureOptions.REPEATING_BILINEAR);
        m_texButton = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(atlas, getAssets(), "button.png", 1, 1);
        m_texButton.setTextureWidth(350.0f);
        m_texButton.setTextureHeight(180.0f);
        
        try 
        {
            atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
            atlas.load();
        } 
        catch (TextureAtlasBuilderException e) 
        {
            e.printStackTrace();
        }	
	}

	@Override
	protected Scene onCreateScene() 
	{
		return new SplashScene();
	}

	//*******************************************************************************************
}
