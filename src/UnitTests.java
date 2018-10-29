import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class UnitTests {

	@Test
	void lhTest() {
		SDES x = new SDES();
		
		// Short arrays first
		assertArrayEquals( x.lh(new boolean[]{true, false, true, true, true, true}), new boolean[]{true, false, true} );
		assertArrayEquals( x.lh(new boolean[]{false, false, true, true, true, true}), new boolean[]{false, false, true} );
		assertArrayEquals( x.lh(new boolean[]{false, true, false, true, true, true}), new boolean[]{false, true, false} );
		
		// Longer arrays
		assertArrayEquals( x.lh(new boolean[]{false, true, false, false, false, true, false, true, true, false}), new boolean[]{false, true, false, false, false} );
		assertArrayEquals( x.lh(new boolean[]{true, true, true, false, true, true, false, true, true, false, false, false}), new boolean[]{true, true, true, false, true, true} );
	}
	
	@Test
	void rhTests() {
		SDES x = new SDES();
		
		// Short arrays first
		assertArrayEquals( x.rh(new boolean[]{true, true, true, true, false, true}), new boolean[]{true, false, true} );
		assertArrayEquals( x.rh(new boolean[]{true, true, true, false, false, true}), new boolean[]{false, false, true} );
		assertArrayEquals( x.rh(new boolean[]{true, true, true, false, true, false}), new boolean[]{false, true, false} );
		
		// Longer arrays
		assertArrayEquals( x.rh(new boolean[]{false, true, false, false, false, true, false, true, true, false}), new boolean[]{true, false, true, true, false} );
		assertArrayEquals( x.rh(new boolean[]{true, true, true, false, true, true, false, true, true, false, false, false}), new boolean[]{false, true, true, false, false, false} );
	}
	
	@Test
	void concatTest() {
		SDES x = new SDES();
		
		// Two arrays of equal size
		assertArrayEquals( x.concat(new boolean[] {true, true, false, false}, new boolean[] {true, false, true, false}), new boolean[] {true, true, false, false, true, false, true, false} );
		
		// Arrays of differing lengths
		assertArrayEquals( x.concat(new boolean[] {false}, new boolean[] {true, true, false}), new boolean[] {false, true, true, false} );
		
		// One of the arrays is empty
		assertArrayEquals(  x.concat(new boolean[] {}, new boolean[] {true}), new boolean[] {true} );
		
		// Both arrays are empty
		assertArrayEquals(  x.concat(new boolean[] {}, new boolean[] {}), new boolean[] {} );
		
	}

}
