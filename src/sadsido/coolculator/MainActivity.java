package sadsido.coolculator;

import java.io.IOException;

import org.andengine.audio.sound.Sound;
import org.andengine.audio.sound.SoundFactory;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
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
	private Settings m_settings;
	private Layout m_layout;
	
	private Font m_menuFont;
	private Font m_btnFont;
	
	private ITextureRegion m_texGradient;
	private ITextureRegion m_texButton;
	private ITextureRegion m_texBackgr;
	private ITextureRegion m_texScore;
	private ITextureRegion m_texRecord;
	private ITextureRegion m_texTimebar;
	private ITextureRegion m_texCCLogo;
	
	private Sound m_sndButton;
	private Sound m_sndEquation;
	
	//*******************************************************************************************
	
	public MainActivity()
	{ s_instance = this; }

	public static MainActivity instance()
	{ return s_instance; }
	
	//*******************************************************************************************

	public VertexBufferObjectManager getVBO()
	{ return getVertexBufferObjectManager(); }
	
	public Camera getCamera()
	{ return m_camera; }

	public Settings getSettings()
	{ return m_settings; }
	
	public Layout getLayout()
	{ return m_layout; }
	
	public Font getMenuFont()
	{ return m_menuFont; }
	
	public Font getButtonFont()
	{ return m_btnFont; }
	
	public void setScene(Scene scene)
	{ getEngine().setScene(scene); }
	
	public ITextureRegion getGradientTexture()
	{ return m_texGradient; }

	public ITextureRegion getButtonTexture()
	{ return m_texButton; }

	public ITextureRegion getBackgroundTexture()
	{ return m_texBackgr; }

	public ITextureRegion getScoreTexture()
	{ return m_texScore; }
	
	public ITextureRegion getRecordTexture()
	{ return m_texRecord; }

	public ITextureRegion getTimebarTexture()
	{ return m_texTimebar; }

	public ITextureRegion getCCLogoTexture()
	{ return m_texCCLogo; }
	
	public void playButtonSound()
	{ m_sndButton.play(); }
	
	public void playEquationSound()
	{ m_sndEquation.play(); }

	//*******************************************************************************************

	@Override
	public EngineOptions onCreateEngineOptions() 
	{
		m_camera = new Camera(0, 0, 1920, 1080);
		
		EngineOptions eop = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, new FillResolutionPolicy(), m_camera);
		eop.getAudioOptions().setNeedsSound(true);
		
		return eop;
	}

	@Override
	protected void onCreateResources() 
	{
		// load settings:		
		m_settings = new Settings(this);		
		
		// prepare layout:
		m_layout = new Layout(m_camera);
		
		// prepare fonts:		
		final int ftmenuSize = 2 * (int)m_layout.rcTopBar().height() / 3;
		final int ftbtnSize  = 2 * (int)m_layout.rcButton().height() / 3;
		
		m_menuFont = FontFactory.createFromAsset(getFontManager(), getTextureManager(), 512, 512, TextureOptions.BILINEAR, getAssets(), "cheeseusauceu.ttf", ftmenuSize, true, Color.WHITE);
		m_menuFont.load();
		
		m_btnFont = FontFactory.createFromAsset(getFontManager(), getTextureManager(), 512, 512, TextureOptions.BILINEAR, getAssets(), "cheeseusauceu.ttf", ftbtnSize, true, Color.WHITE);
		m_btnFont.load();

		// prepare textures:		
		try
		{
			loadBilinearTextures();
			loadRepeatingTextures();
		}
		catch (TextureAtlasBuilderException ex)
		{
			ex.printStackTrace();
		}
		
		// prepare sounds:
		try
		{
			m_sndButton = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(), "button.wav");		
			m_sndEquation = SoundFactory.createSoundFromAsset(this.getSoundManager(), this.getApplicationContext(), "equation.wav");		
		}
		catch (IOException ex)
		{
			ex.printStackTrace();
		}
	}

	@Override
	protected Scene onCreateScene() 
	{
		return new SplashScene();
	}

	//*******************************************************************************************
	
	private void loadBilinearTextures() throws TextureAtlasBuilderException
	{
		BuildableBitmapTextureAtlas atlas = new BuildableBitmapTextureAtlas(getTextureManager(), 1024, 1024, TextureOptions.BILINEAR);
        
		m_texGradient = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, getAssets(), "gradient.png");
		m_texButton   = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, getAssets(), "button.png");
        m_texScore    = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, getAssets(), "score.png");
        m_texRecord   = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, getAssets(), "record.png");
        m_texCCLogo   = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas, getAssets(), "cclogo.png");
        
        atlas.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
        atlas.load();
	}
	
	private void loadRepeatingTextures() throws TextureAtlasBuilderException
	{
		BuildableBitmapTextureAtlas atlas1 = new BuildableBitmapTextureAtlas(getTextureManager(),  64,  16, TextureOptions.REPEATING_BILINEAR);
		BuildableBitmapTextureAtlas atlas2 = new BuildableBitmapTextureAtlas(getTextureManager(), 512, 512, TextureOptions.REPEATING_BILINEAR);
	
		m_texTimebar = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas1, getAssets(), "timebar.png");
        m_texBackgr  = BitmapTextureAtlasTextureRegionFactory.createFromAsset(atlas2, getAssets(), "background.png");

        m_texTimebar.setTextureSize(200.0f, 200.0f);
        m_texBackgr.setTextureSize(m_camera.getWidth(), m_camera.getHeight());

		atlas1.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
        atlas1.load();
        
		atlas2.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(0, 0, 0));
        atlas2.load();
	}

	//*******************************************************************************************
}
