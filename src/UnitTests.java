import static org.junit.jupiter.api.Assertions.*;

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

}
