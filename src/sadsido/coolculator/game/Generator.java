package sadsido.coolculator.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;


// a class for encapsulating randomizer logic when creating the buttons:

public class Generator 
{
	//*******************************************************************************************

	private final int m_col;	
	private LinkedList<Integer> m_vals;
	private Random m_rand;
	
	//*******************************************************************************************

	public Generator(int col)
	{
		m_col  = col;
		m_vals = new LinkedList<Integer>();
		m_rand = new Random();
		
		// fill in the set of possible values:
		
		final int ceil = (col == 3) ? 21 : 10;
		for (int value = 0; value < ceil; ++ value)
		{
			m_vals.add(value);
		}
		
		// shuffle the set:
		
		Collections.shuffle(m_vals, m_rand);
	}
	
	//*******************************************************************************************

	public int pickValue()
	{
		return m_vals.poll();
	}
	
	public int pickSign(int value)
	{
		// these columns have fixed sign:
		
		if (m_col == 3)
		{ return Button.SIGN_RESULT; }

		if (m_col == 2)
		{ return Button.SIGN_EQUALS; }
		
		// these columns have variations:
		
		if (m_col == 0)
		{ return (value < 4) ? m_rand.nextInt(Button.SIGN_MINUS) : Button.SIGN_PLUS; }
		
		if (m_col == 1)
		{ return (value < 4) ? m_rand.nextInt(Button.SIGN_EQUALS) : m_rand.nextInt(Button.SIGN_MINUS) + 1; }
				
		// assert this never reached:
		
		return Button.SIGN_RESULT;
	}
	
	public void pushValue(int value)
	{
		m_vals.add(value);
		Collections.shuffle(m_vals, m_rand);
	}
	
	//*******************************************************************************************
}
