import java.util.Arrays;

public class Driver {

	public static void main(String[] args) {
		SDES x = new SDES();
		
		// Left Half
		System.out.println(Arrays.toString(x.lh(new boolean[] {true, false, true, true, true, true})));
		System.out.println(Arrays.toString(x.lh(new boolean[] {false, false, true, true, true, true})));
		System.out.println(Arrays.toString(x.lh(new boolean[] {false, true, false, true, true, true})));

		
		// Right Half
		System.out.println(Arrays.toString(x.rh(new boolean[] {true, true, true, false, true, false})));
		
		// Concatenation
		System.out.println( Arrays.toString( x.concat(new boolean[] {true, true, false, false}, new boolean[] {true, false, true, false}) ));
		System.out.println( Arrays.toString( x.concat(new boolean[] {false}, new boolean[] {true, true, false}) ));
		System.out.println( Arrays.toString( x.concat(new boolean[] {}, new boolean[] {true}) ));
		
		// expPerm
		System.out.println( Arrays.toString( x.expPerm(new boolean[] {true, true, false, false}, new int[] {0, 2, 1, 3})));
	}

}
