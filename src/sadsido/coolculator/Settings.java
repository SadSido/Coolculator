package sadsido.coolculator;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


public class Settings 
{
	//*******************************************************************************************
	
	private static final String HighscoreName = "Coolculator::Highscore";
	private static final int HighscoreDef = 100;
	
	//*******************************************************************************************

	private SharedPreferences m_shap;
	
	//*******************************************************************************************
	
	public Settings(MainActivity activity)
	{
		m_shap = PreferenceManager.getDefaultSharedPreferences(activity);
	}

	//*******************************************************************************************

	public int loadHighscore()
	{ 
		return m_shap.getInt(HighscoreName, HighscoreDef); 
	}
	
	public void saveHighscore(int val)
	{ 
		Editor editor = m_shap.edit(); 
		editor.putInt(HighscoreName, val);
		editor.commit();
	}
	
	//*******************************************************************************************
}
