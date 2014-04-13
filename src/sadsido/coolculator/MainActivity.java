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
	private Font m_btnFont;
	
	private ITextureRegion m_texButton;
	private ITextureRegion m_texBackgr;
	private ITextureRegion m_texScore;
	private ITextureRegion m_texRecord;
	
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
	
	public Font getButtonFont()
	{ return m_btnFont; }
	
	public void setScene(Scene scene)
	{ getEngine().setScene(scene); }
	
	public ITextureRegion getButtonTexture()
	{ return m_texButton; }

	public ITextureRegion getBackgroundTexture()
	{ return m_texBackgr; }

	public ITextureRegion getScoreTexture()
	{ return m_texScore; }
	
	public ITextureRegion getRecordTexture()
	{ return m_texRecord; }
	
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
		m_menuFont = FontFactory.createFromAsset(getFontManager(), getTextureManager(), 512, 512, TextureOptions.BILINEAR, getAssets(), "inconsolata-bold.ttf", 100, true, Color.WHITE);
		m_menuFont.load();
		
		m_btnFont = FontFactory.createFromAsset(getFontManager(), getTextureManager(), 512, 512, TextureOptions.BILINEAR, getAssets(), "inconsolata-bold.ttf", 140, true, Color.WHITE);
		m_btnFont.load();

		BuildableBitmapTextureAtlas atlas = new BuildableBitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.BILINEAR);
        
		m_texButton = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, getAssets(), "button.png");
        m_texBackgr = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, getAssets(), "background.png");
        m_texScore  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, getAssets(), "score.png");
        m_texRecord = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, getAssets(), "record.png");
        
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
