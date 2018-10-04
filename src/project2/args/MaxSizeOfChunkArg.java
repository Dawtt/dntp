/**
 * File Created by Joshua Zierman on Oct 4, 2018
 */
package project2.args;

/**
 * @author Joshua Zierman [py1422xs@metrostate.edu]
 *
 */
public class MaxSizeOfChunkArg extends Arg<Integer>
{

	private static final String HELP_MSG = "The maximum size of a chunk in bytes.";
	
	/**
	 * @param flag
	 */
	public MaxSizeOfChunkArg(String flag) {
		super(flag);
	}

	/* (non-Javadoc)
	 * @see project2.args.Arg#processInlineArg(java.lang.String)
	 */
	@Override
	public void processInlineArg(String s) throws Exception
	{
		value = Integer.parseInt(s);
		
	}

	/* (non-Javadoc)
	 * @see project2.args.Arg#getDefault()
	 */
	@Override
	public Integer getDefault()
	{
		return project2.Defaluts.MAX_CHUNK_LENGTH;
	}

	/* (non-Javadoc)
	 * @see project2.args.Arg#getHelpString()
	 */
	@Override
	protected String getHelpString()
	{
		return HELP_MSG;
	}

}
