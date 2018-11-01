
/**
 * @author Abby Beizer
 * The SBox accepts a 4-digit binary number as input and returns a two-digit binary number.
 */
public class SBox0 {
	
	public static boolean[] getValue(boolean[] input)
	{
		// The SBox requires exactly 4 digits in the input
		if(input.length != 4)
		{
			return null;
		}
		
		// Switch statements don't work with arrays, so convert it to a String.
		StringBuilder val = new StringBuilder();
		for(int i = 0; i < 4; i++)
		{
			if(input[i])
			{
				val.append(1);
			}
			else
			{
				val.append(0);
			}
		}
		
		// 1 = true, 0 = false
		switch(val.toString())
		{
		case "0000": return new boolean[] {false, true};		// 01
		case "0001": return new boolean[] {true, true};			// 11
		case "0010": return new boolean[] {false, false};		// 00
		case "0011": return new boolean[] {true, false};		// 10
		case "0100": return new boolean[] {true, true};			// 11
		case "0101": return new boolean[] {false, true};		// 01
		case "0110": return new boolean[] {true, false};		// 10
		case "0111": return new boolean[] {false, false};		// 00
		case "1000": return new boolean[] {false, false};		// 00
		case "1001": return new boolean[] {true, true};			// 11
		case "1010": return new boolean[] {true, false};		// 10
		case "1011": return new boolean[] {false, true};		// 01
		case "1100": return new boolean[] {false, true};		// 01
		case "1101": return new boolean[] {true, true};			// 11
		case "1110": return new boolean[] {true, true};			// 11
		case "1111": return new boolean[] {true, false};		// 10
		default: return null;
		}
	}

}
