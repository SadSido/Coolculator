package sadsido.coolculator.gens;



public class LineGenerator extends ValueGenerator 
{
	//*******************************************************************************************

	private int m_min;
	private int m_max;
	private int m_next;
	
	//*******************************************************************************************

	public LineGenerator(int min, int max)
	{
		m_min  = min;
		m_max  = max;
		m_next = min;
	}
	
	//*******************************************************************************************

	@Override
	public int pickValue() 
	{
		final int res = m_next;
		m_next = (m_next == m_max) ? m_min : m_next + 1;
		return res;
	}

	@Override
	public void pushValue(int value) 
	{ /* nothing */	}

	//*******************************************************************************************
}
