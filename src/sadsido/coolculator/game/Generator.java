package sadsido.coolculator.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;


// a class for encapsulating randomizer logic when creating the buttons:

public class Generator 
{
	//*******************************************************************************************

	private LinkedList<Integer> m_vals;
	private Random m_rand;
	
	//*******************************************************************************************

	public Generator(int low, int high, boolean random)
	{
		m_vals = new LinkedList<Integer>();
		m_rand = (random) ? new Random() : null;
		
		// fill in the set of possible values:
		
		for (int value = low; value <= high; ++ value)
		{ m_vals.addLast(value); }
		
		// prepare the set:
		
		reshuffle();
	}
	
	//*******************************************************************************************

	public int pickValue()
	{
		return m_vals.poll();
	}
	
	public void pushValue(int value)
	{
		m_vals.addLast(value);
		
		// keep the stuff shuffled:
		
		reshuffle();
	}
	
	//*******************************************************************************************
	
	private void reshuffle()
	{ 
		if (m_rand != null) 
		{ Collections.shuffle(m_vals, m_rand); } 
	}
	
	//*******************************************************************************************

	public static Generator getGenerator(int column)
	{
		switch (column)
		{
		case (Const.Cols - 1): return new Generator(1, 20, false);
		case (Const.Cols - 2): return new Generator(0, 7, true);
		}
		
		// otherwise random digit:
		return new Generator(0, 9, true);
	}

	//*******************************************************************************************

}
