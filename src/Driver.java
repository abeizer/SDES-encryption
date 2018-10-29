import java.util.Arrays;

public class Driver {

	public static void main(String[] args) {
		SDES x = new SDES();
		
		System.out.println(Arrays.toString(x.lh(new boolean[] {true, false, true, true, true, true})));
		System.out.println(Arrays.toString(x.lh(new boolean[] {false, false, true, true, true, true})));
		System.out.println(Arrays.toString(x.lh(new boolean[] {false, true, false, true, true, true})));

	}

}
