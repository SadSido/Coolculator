package sadsido.coolculator.pick;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;



// a class for encapsulating randomizer logic when creating the buttons:

public class RndValuePicker extends ValuePicker
{
	//*******************************************************************************************

	private LinkedList<Integer> m_vals;
	private Random m_rand;
	
	//*******************************************************************************************

	public RndValuePicker(int min, int max)
	{
		m_vals = new LinkedList<Integer>();
		m_rand = new Random();
		
		// fill in the set of possible values:
		
		for (int value = min; value <= max; ++ value)
		{ m_vals.addLast(value); }
		
		// prepare the set:
		
		reshuffle();
	}
	
	//*******************************************************************************************

	@Override
	public int pickValue()
	{ return m_vals.poll();	}
	
	@Override
	public void pushValue(int value)
	{
		m_vals.addLast(value);
		reshuffle();
	}
	
	//*******************************************************************************************
	
	private void reshuffle()
	{ Collections.shuffle(m_vals, m_rand); }

	//*******************************************************************************************

}
