/**
 * File Created by Joshua Zierman on Sep 25, 2018
 */
package project1;

import java.io.IOException;
import java.net.InetAddress;
import java.util.LinkedList;

/**
 * @author Joshua Zierman [py1422xs@metrostate.edu]
 *
 */
public class FileSender implements Sender
{
	private InetAddress destinationIp;
	private int destinationPort, bytesPerChunk;
	private String filename;
	private LinkedList<Chunk> chunks = new LinkedList<Chunk>();
	private int next = 0; // index of next
	
	FileSender(String filename, InetAddress ip, int port, int bytesPerChunk)
	{
		setBytesPerChunk(bytesPerChunk);
		setFilename(filename);
		setToAddress(ip);
		setToPort(port);
	}

	/** sets the number of bytes per chunk
	 * @param bytesPerChunk an integer value that represents the number of bytes per chunk
	 */
	private void setBytesPerChunk(int bytesPerChunk)
	{
		if(bytesPerChunk < 1 )
		{
			throw new IllegalArgumentException();
		}
		this.bytesPerChunk = bytesPerChunk;
	}

	/** sets the filename
	 * @param filename the String containing the filename
	 */
	private void setFilename(String filename)
	{
		if(filename.length() < 1 || filename.startsWith(".") || filename.endsWith("."))
		{
			throw new IllegalArgumentException();
		}
		this.filename = filename;
	}

	/* (non-Javadoc)
	 * @see project1.Sender#setToAddress(java.net.InetAddress)
	 */
	@Override
	public void setToAddress(InetAddress ip)
	{
		destinationIp = ip;
	}

	/* (non-Javadoc)
	 * @see project1.Sender#setToPort(int)
	 */
	@Override
	public void setToPort(int port)
	{
		destinationPort = port;
	}

	/* (non-Javadoc)
	 * @see project1.Sender#sendNext()
	 */
	@Override
	public void sendNext()
	{
		send();
	}

	/** Sends the file
	 * 
	 */
	private void send()
	{
		
		try
		{
			FileSplitter splitter = new FileSplitter(filename, bytesPerChunk);
			splitter.overwrite(chunks);
			ChunkSender chunkSender = new ChunkSender(destinationIp, destinationPort);
			chunkSender.load(chunks);
			while(chunkSender.hasNext())
			{
				chunkSender.sendNext();
			}
			
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 * @see project1.Sender#resetNextPosition()
	 */
	@Override
	public void resetNextPosition()
	{
		next = 0; // there is never more than one file to send 
	}

	/* (non-Javadoc)
	 * @see project1.Sender#hasNext()
	 */
	@Override
	public boolean hasNext()
	{
		return false; // there is never more than one file to send
	}

}