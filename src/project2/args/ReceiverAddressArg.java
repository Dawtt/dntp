/**
 * File Created by Joshua Zierman on Oct 4, 2018
 */
package project2.args;

import java.net.InetAddress;

/**
 * command-line argument for receiver's address
 * 
 * @author Joshua Zierman [py1422xs@metrostate.edu]
 *
 */
public class ReceiverAddressArg extends AddressArg
{

	private final static String CLIENT_NAME = "receiver";

	/**
	 * Constructs an instance of this class
	 * 
	 * @param flag
	 *            the string that is a flag in the command line argument
	 */
	public ReceiverAddressArg(String flag)
	{
		super(flag);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see project2.args.AddressArg#getClientName()
	 */
	@Override
	protected String getClientName()
	{
		return CLIENT_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see project2.args.Arg#getDefault()
	 */
	@Override
	protected InetAddress getDefault()
	{
		return project2.Defaults.RECEIVER_ADDRESS;
	}

}
