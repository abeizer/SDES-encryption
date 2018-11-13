import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import org.junit.Rule;
import org.junit.rules.ExpectedException;
import org.junit.jupiter.api.Test;


class UnitTests {

	@Rule
	public final ExpectedException exit = ExpectedException.none();
	
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
	
	@Test
	void expPermTest() {
		SDES x = new SDES();
		
		// Short selection permutation
		assertArrayEquals( x.expPerm(new boolean[] {true, true, false, false}, new int[] {0, 2, 1, 3}),		new boolean[] {true, false, true, false} );
		assertArrayEquals( x.expPerm(new boolean[] {true, true, false, false}, new int[] {1, 2, 3}),		new boolean[] {true, false, false} );
		
		// A permutation with redundant epv indeces
		assertArrayEquals( x.expPerm(new boolean[] {true, true, false, false}, new int[] {0, 0, 0, 0}),		new boolean[] {true, true, true, true} );
		
		// An expansion
		assertArrayEquals( x.expPerm(new boolean[] {true, true, false, false}, new int[] {1, 2, 3, 1, 0, 2}),		new boolean[] {true, false, false, true, true, false} );
		
		// The permutation array has an int that is out of input's index range. Should return null
		assertNull( x.expPerm(new boolean[] {true, true, false, false}, new int[] {0, 1, 2, 3, 4}) );
		assertNull( x.expPerm(new boolean[] {true, true, false, false}, new int[] {-1}) );
	}
	
	@Test
	void xorTest() {
		SDES x = new SDES();
		
		// 0 xor 0 = 0
		assertArrayEquals( x.xor(new boolean[] {false}, new boolean[] {false}), new boolean[] {false});
		
		// 1 xor 1 = 0
		assertArrayEquals( x.xor(new boolean[] {true}, new boolean[] {true}), new boolean[] {false});
		
		// 0 xor 1 = 1
		assertArrayEquals( x.xor(new boolean[] {false}, new boolean[] {true}), new boolean[] {true});
		
		// 1 xor 0 = 1
		assertArrayEquals( x.xor(new boolean[] {true}, new boolean[] {false}), new boolean[] {true});
		
		// xor works on arrays
		assertArrayEquals( x.xor(new boolean[] {false, false, true, true}, new boolean[] {false, true, false, true}), new boolean[] {false, true, true, false});
	}
	
	@Test
	void sbox0Test() {
		// 0000 should return 01
		assertArrayEquals( SBox0.getValue(new boolean[] {false, false, false, false}), new boolean[] {false, true} );
		
		// 1010 should return 10
		assertArrayEquals( SBox0.getValue(new boolean[] {true, false, true, false}), new boolean[] {true, false} );
		
		// Incorrect input size should return null
		assertNull( SBox0.getValue(new boolean[] {false, false}) );
		assertNull( SBox0.getValue(new boolean[] {false, false, false, false, false}) );
	}
	
	@Test
	void sbox1Test() {
		// 1111 should return 11
		assertArrayEquals( SBox1.getValue(new boolean[] {true, true, true, true}), new boolean[] {true, true} );
		
		// 0101 should return 01
		assertArrayEquals( SBox1.getValue(new boolean[] {false, true, false, true}), new boolean[] {false, true} );
		
		// Incorrect input size should return null
		assertNull( SBox1.getValue(new boolean[] {true, true}) );
		assertNull( SBox1.getValue(new boolean[] {true, true, true, true, true}) );
	}
	
	@Test
	void testGetKey10() {
		SDES x = new SDES();
		
		// Set input stream
		System.setIn(new ByteArrayInputStream("1010101010".getBytes()));
		
		x.getKey10();
		assertArrayEquals(new boolean[] {true, false, true, false, true, false, true, false, true, false}, x.showKey());
	}

}
