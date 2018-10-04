/**
 * File Created by Joshua Zierman on Oct 4, 2018
 */
package project2.args;

import java.net.InetAddress;

/**
 * @author Joshua Zierman [py1422xs@metrostate.edu]
 *
 */
public class ProxyAddressArg extends AddressArg
{

	private final static String CLIENT_NAME = "proxy";
	
	/**
	 * @param flag
	 */
	public ProxyAddressArg(String flag) {
		super(flag);
	}

	/* (non-Javadoc)
	 * @see project2.args.AddressArg#getClientName()
	 */
	@Override
	protected String getClientName()
	{
		return CLIENT_NAME;
	}

	/* (non-Javadoc)
	 * @see project2.args.Arg#getDefault()
	 */
	@Override
	public InetAddress getDefault()
	{
		return project2.Defaluts.PROXY_ADDRESS;
	}

}