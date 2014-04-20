package sadsido.coolculator.pick;

import java.util.Random;
import sadsido.coolculator.game.Sign;



public class RndSignPicker extends SignPicker 
{
	//*******************************************************************************************

	private Random m_rand = new Random();
	private Sign   m_last = Sign.Minus;
	
	private static Sign m_nominus [] = { Sign.Plus, Sign.Multiply };
	private static Sign m_nomult  [] = { Sign.Plus, Sign.Minus };
	
	//*******************************************************************************************

	@Override 
	public Sign pickSign(int value) 
	{ 
		m_last = (value < 4) ? pickFrom(m_nominus, m_last) : (value > 7) ? pickFrom(m_nomult, m_last) : Sign.Plus;
		return m_last;
	}

	//*******************************************************************************************

	private Sign pickFrom(Sign [] options, Sign last)
	{
		final int index = m_rand.nextInt(options.length);
		return (options[index] == last) ? options[(index + 1) % options.length] : options[index];
	}

	//*******************************************************************************************

}
