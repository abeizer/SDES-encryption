import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.Scanner;

public class SDES {
    boolean[] key = new boolean[10];
    public SDES()
    {
        
    }
    
     /**
     * @Author Dominic Nolt
     * Convert the given bit array to a single byte
     * @param input - A bit array, max length is 8 bits
     * @return
     */
    public byte bitArrayToByte(boolean[] input)
    {
        String stringByte = "";
        int[] temp = new int[8];
        if (input.length > 8) {
            System.out.println("bitArrayToByte: parameter longer than 8 bits");
            System.exit(0);
        }
        else {
            int j = 7;
            for (int i = input.length-1; i >= 1; i--) {
               if (input[i] == true) { 
                    temp[j] = 1;
               }
               j--;
            }
        }
        
        for (int i = 0; i <= temp.length-1; i++) {
            stringByte = stringByte + temp[i];
        }
        
        byte b = Byte.parseByte(stringByte, 2);
        
        if (input[0] == true) {
            int a = b;
            int exp = 1;
            for (int i = 1; i <= input.length-1; i++) {
                exp = exp * 2;
            }
            a = a - exp;
            b = (byte) a;
        }
        
        return (new Byte(b));
    }
    
    
    /**
     * @Author Dominic Nolt
     * Convert the given byte array to a String
     * @param input - An array of bytes, storing the codes of printable characters
     * @return The characters as a String
     */
    public String byteArrayToString(byte[] input)
    {
        String s = "";
        if (input.length > 0) {
            s = s + input[0];
            for (int i = 1; i <= input.length-1; i++) {
                s = s + " " + input[i];
            }
        }
        return new String(s);
    }
    
    
    /**
     * @author Dominic Nolt
     * Convert the given byte to a bit array, of the given size
     * @param b
     * @param size - The size of the resulting bit array. The operator >>> can be used for an unsigned right shift.
     * @return
     */
    public boolean[] byteToBitArray(byte b, int size)
    {
        // Check if the given size can accommodate the requested byte
        int check = 1;
        for (int i = 0; i < size-1; i++) {
            check = check * 2;
        }
        if (b > 0) {
            if (b > check-1) {
                System.out.println("byteToBitArray: Size is too small to accommodate the byte, proceeding anyway");
                //System.exit(0);
            }
        }
        else if (b < 0) {
            if (b < check*-1) {
                System.out.println("byteToBitArray: Size is too small to accommodate the byte, proceeding anyway");
                //System.exit(0);
            }
        }
        
        // Takes byte, converts to binary array of boolean values
        boolean[] temp = new boolean[size];
        for (int i = 0; i < size; i++) {
            temp[i] = (b & (1 << i)) == 0 ? false : true;
        }

        // reverses the array so that's in the correct order
        boolean[] result = new boolean[size];
        for (int i = 0; i < temp.length; i++) {
            result[result.length-1-i] = temp[i];
        }
        return result;
    }
    
    
    /**
     * @author Abby Beizer
     * Concatenate the two bit arrays, x || y
     * @param x
     * @param y
     * @return The concatenation of x and y
     */
    public boolean[] concat(boolean[] x, boolean[] y)
    {
        int xLen = x.length, yLen = y.length;
        boolean[] concatenation = new boolean[xLen + yLen];
        
        int index = 0;
        
        // First loop for x
        for(int i = 0; i < xLen; i++, index++)
        {
            concatenation[i] = x[i];
        }
        
        // Second loop for y, starting at index
        for(int j = 0; j < yLen; j++, index++)
        {
            concatenation[index] = y[j];
        }
        
        return concatenation;
    }
    
    
	/**
	 * @author Geoffrey Cohen
	 * Decrypt the given byte array
	 * @param cipher - An array of bytes representing the cipher text
	 * @return An array of bytes representing the original plain text
	 */
	public byte[] decrypt(byte[] cipher)
	{
		byte[] x = new byte[cipher.length]; // instantiate plain text (same length as cipher text)
		for (int i=0; i<cipher.length; i++) { // for each byte in cipher text, decrypt
			x[i] = decryptByte(cipher[i]);
		}
		return x;
	}
    
    
    /**
	 * @author Geoffrey Cohen
     * Decrypt a single byte using SDES
     * @param b the byte to be decrypted
     * @return a decrypted byte
     */
    public byte decryptByte(byte b)
    {
        boolean[] bitArr = byteToBitArray(b, 8); // load byte into array of bits

		int[] iniPer = {1, 5, 2, 0, 3, 7, 4, 6};
		int[] iniPerInv = {3, 0, 2, 4, 6, 1, 7, 5};
		int[] k1Per = {0, 6, 8, 3, 7, 2, 9, 5};
		int[] k2Per = {7, 2, 5, 4, 9, 1, 8, 0};

		bitArr = expPerm(bitArr, iniPer); // IP
		boolean[] k2 = expPerm(key, k2Per); // fsubk2 -- key should be a field
		bitArr = f(bitArr, k2);
		bitArr = concat(rh(bitArr), lh(bitArr)); // swap
		boolean[] k1 = expPerm(key, k1Per); // fsubk1
		bitArr = f(bitArr, k1);
		bitArr = expPerm(bitArr, iniPerInv); // IP^-1

		return bitArrayToByte(bitArr);		
		
    }
    
    
	/**
	 * @author Geoffrey Cohen
	 * Encrypt the given string using SDES Each character produces a byte of cipher
	 * @param msg
	 * @return An array of bytes representing the cipher text
	 */
	public byte[] encrypt(String msg)
	{
		byte[] x = {};
		try 
		{
			x = msg.getBytes("US-ASCII"); // get plain text as bytes
		}
		catch(UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		byte[] y = new byte[x.length]; // instantiate cipher text
		for (int i=0; i<x.length; i++) { // for each byte in plain text, encrypt and assign to same byte in plain text
			y[i] = encryptByte(x[i]);
        }
		return y;     
	}
    
    
    /**
	 * @author Geoffrey Cohen
     * encrypt a single byte using SDES
     * @param b the byte to be encrypted
     * @return an encrypted byte
     */
    public byte encryptByte(byte b)
    {
        boolean[] bitArr = byteToBitArray(b, 8); // load byte into array of bits

		int[] iniPer = {1, 5, 2, 0, 3, 7, 4, 6};
		int[] iniPerInv = {3, 0, 2, 4, 6, 1, 7, 5};
		int[] k1Per = {0, 6, 8, 3, 7, 2, 9, 5};
		int[] k2Per = {7, 2, 5, 4, 9, 1, 8, 0};

		bitArr = expPerm(bitArr, iniPer); // IP
		boolean[] k1 = expPerm(key, k1Per); // fsubk1 -- key should be a field
		bitArr = f(bitArr, k1);
		bitArr = concat(rh(bitArr), lh(bitArr)); // swap
		boolean[] k2 = expPerm(key, k2Per); // fsubk2
		bitArr = f(bitArr, k2);
		bitArr = expPerm(bitArr, iniPerInv); // IP^-1

		return bitArrayToByte(bitArr);	
    }
    
    
    /**
     * @author Abby Beizer
     * Expand and/or permute and/or select from the bit array, inp, producing an expanded/permuted/selected bit array
     * @param input - A bit array represented as booleans (true = 1, false = 0)
     * @param epv - An expansion and/or permutation and/or selection vector. All numbers in epv must be a valid input index.
     * @return The permuted/expanded/selected bit array, or null if there is an error
     */
    public boolean[] expPerm(boolean[] input, int[] epv)
    {
        // The result will be the same length as the epv array
        int len = epv.length;
        boolean[] result = new boolean[len];
        
        for(int i = 0; i < len; i++)
        {
            try
            {
                // Use the int at epv[i] to index into the input array
                result[i] = input[epv[i]];
            }
            catch(IndexOutOfBoundsException e)
            {
                // This exception will be thrown if any int in epv exceeds
                // the range of input's indeces. In this case, return null.
                return null;
            }
        }
        
        return result;
    }
    
    
    /**
     * @author Geoffrey Cohen
     * This is the 'round' function
     * @param x bitArray to be passed through function
     * @param k key to be used in feistel function
     * @return bitArray to be used in successive functions
     */
    public boolean[] f(boolean[] x, boolean[] k)
    {
        return concat(xor(lh(x), feistel(k, rh(x))), rh(x)); //self explanatory
    }
    
    
    /**
     * @author Geoffrey Cohen
     * F(k,x) is a Feistel function F(k,x) = P4 (s0 (L (k xor EP(x))) || s1 (R (k xor EP(x)))
     * @param k key to be used
     * @param x 4-bit array, right half of initial x
     * @return 4-bit array to be used in round
     */
    public boolean[] feistel(boolean[] k, boolean[] x)
    {
    	int[] expPer = {3, 0, 1, 2, 1, 2, 3, 0};
    	int[] p4Per = {1, 3, 2, 0};
    	boolean[] kXorEpX = xor(k, expPerm(x, expPer)); // going to use this twice, so instantiate
    	
    	return expPerm(concat(SBox0.getValue(lh(kXorEpX)), SBox1.getValue(rh(kXorEpX))), p4Per); //self explanatory    	
    }
    
    /**
     * @author Abby Beizer
     * Get a 10 bit key from the keyboard, such as 1010101010
     */
    public void getKey10()
    {
        java.util.Scanner in = new java.util.Scanner(System.in);
        
        // Prompt for input
        System.out.println("Please enter your 10 bit key:");
        String bits = in.nextLine();
        bits = bits.replace(" ", "");	// Remove any spaces in input
        
        // The input has to be 10 chars long
        if(bits.length() != 10)
        {
        	System.out.println("Key must be 10 bits");
        	System.exit(0);
        }
        
        // Convert the 1's and 0's to booleans
        for (int i = 0; i < 10; i++) 
        {
        	// 1 = true
            if ( Character.getNumericValue(bits.charAt(i)) == 1) 
            {
                this.key[i] = true;
            }
            // 0 = false
            else if( Character.getNumericValue(bits.charAt(i)) == 0) 
            {
            	this.key[i] = false;
            }
            // If not 0 or 1, incorrect input
            else
            {
                System.out.println("getKey10: Only ones and zero's allowed");
                System.exit(0);
            }
        }
    }
    
    public boolean[] showKey()
    {
    	return(this.key);
    }
    
    /**
     * @author Abby Beizer
     * Left half of x, L(x)
     * @param input
     * @return A bit array which is the left half of the parameter, input
     */
    public boolean[] lh(boolean[] input)
    {
        int leftHalfSize = input.length/2;
        boolean[] leftHalf = new boolean[leftHalfSize];
        for(int i = 0; i < leftHalfSize; i++)
        {
            leftHalf[i] = input[i];
        }
        
        return leftHalf;
    }
    
    
    /**
     * @author Abby Beizer
     * Right half of x, R(x)
     * @param input
     * @return A bit array which is the right half of the parameter, input
     */
    public boolean[] rh(boolean[] input)
    {
        int len = input.length;
        boolean[] rightHalf = new boolean[len/2];
        for(int i = 0, j = len/2; j < len; i++, j++)
        {
            rightHalf[i] = input[j];
        }
        
        return rightHalf;
    }
    
    
    /**
     * @author Dominic Nolt
     * Send the bitArray to stdout as 1's and 0's
     * @param input
     */
    public void show(boolean[] input)
    {
        String stringByte = "";
        int[] temp = new int[input.length];
        if (input.length > 8) {
            System.out.println("bitArrayToByte: parameter longer than 8 bits");
            System.exit(0);
        }
        else {
            int j = input.length-1;
            for (int i = input.length-1; i >= 0; i--) {
               if (input[i] == true) { 
                    temp[j] = 1;
               }
               j--;
            }
        }
        
        for (int i = 0; i <= temp.length-1; i++) {
            stringByte = stringByte + temp[i];
        }
        
        System.out.println(stringByte);
    }
    
    /**
     * @author Dominic Nolt
     * Send the byteArray to stdout
     * @param byteArray
     */
    public void show(byte[] byteArray)
    {
        String s = byteArrayToString(byteArray);
        System.out.println(s);
    }
    
    /**
     * @author Abby Beizer
     * Exclusive OR 
     * x and y must have the same length
     * x XOR y is the same as x != y
     * @param x
     * @param y
     * @return
     */
    public boolean[] xor(boolean[] x, boolean[] y)
    {
        int len = x.length;
        
        if(len != y.length)
        {
            // The two boolean arrays must have the same length
            return null;
        }
        
        boolean[] result = new boolean[len];
        for(int i = 0; i < len; i++)
        {
            if(x[i] == y[i])        // 0
            {
                result[i] = false;
            }
            else                    // 1
            {
                result[i] = true;
            }
        }
        
        return result;
    }
    
}
