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
		
		// Input the encryption key
		x.getKey10();
		
		
		// Show the encryption key I just entered
		System.out.println(Arrays.toString(x.showKey()));
		
		
		// Decrypt prompt
		byte[] y = {};
		x.show(x.decrypt(new byte[]{-115, -17, -47, -113, -43, -47, 15, 84, -43, -113, -17, 84, -43, 79, 58, 15, 64, -113, -43, 65, -47, 
				127, 84, 64, -43, -61, 79, -43, 93, -61, -14, 15, -43, -113, 84, -47, 127, -43, 127, 84, 127, 10, 84, 15, 64, 43}) );
		
		System.out.println("\n");
		
		// Encrypt result of decryption to compare to original prompt
		x.show( x.encrypt("100 -17 24 115 93 24 21 11 93 115 -17 11 93 -127 55 21 64 115 93 29 24 -74 11 64 93 116 -127 93 -43 116 46 21 93 115 11 24 -74 93 -74 11 -74 31 11 21 64 -8"));
	}

}
