package byteNumberConverter;

/**
 * A tool that allows for easy conversion from int to byte[] and byte[] to int
 * 
 * @author Joshua Zierman [py1422xs@metrostate.edu]
 *
 */
public class ByteIntConverter
{
	/**
	 * converts from byte to int
	 * 
	 * @param b
	 *            byte to be converted
	 * @return integer value of the byte
	 */
	public static int convert(byte b)
	{
		return b & 0xff;
	}

	/**
	 * converts from a byte array to an int
	 * 
	 * @param bytes
	 *            a byte array with the least significant byte stored at index 0
	 * @return an int that was stored in the bytes.
	 */
	public static int convert(byte[] bytes)
	{
		if (bytes.length > 4)
		{
			throw new IllegalArgumentException();
		}
		int val = 0;
		for (int i = 0; i < bytes.length; i++)
		{
			val |= (bytes[i] << (i * 8)) & (0xff << (i * 8));
		}
		return val;
	}

	/**
	 * converts from int to byte[]
	 * 
	 * @param integer
	 *            the integer to be converted
	 * @return byte array converted from the int where the lowest significant
	 *         byte is in stored at index 0
	 */
	public static byte[] convert(int integer)
	{
		byte[] b = new byte[4];
		for (int i = 0; i < b.length; i++)
		{
			b[i] = (byte) ((integer >>> i * 8) & 0xff);
		}
		return b;
	}
}
