package project2;

import java.io.PrintStream;
import java.util.Date;

import project2.args.LogPrintingArg;
import project2.frame.AckFrame;
import project2.frame.ChunkFrame;
import project2.frame.Frame;


public class LogPrinter
{
	private Boolean printerIsOn;
	private PrintStream logPrintStream;
	private Integer maxChunkPakageSize, ackPackageSize;
	private Long fileSize;
	private Integer lastSentSeq = null;
	private Integer lastSentAck = null;
	private Long startTime;
	
	
	public LogPrinter(Boolean printerIsOn, PrintStream logPrintStream, Integer maxChunkPakageSize, Long filesize, Long startTime)
	{
		this.printerIsOn = printerIsOn;
		this.logPrintStream = logPrintStream;
		this.maxChunkPakageSize = maxChunkPakageSize;
		this.ackPackageSize = ackPackageSize;
		this.fileSize = filesize;
		this.startTime = startTime;
	}
	
	public void sent(ChunkFrame f)
	{
		if(printerIsOn)
		{
			long start = startByteOffset(f);
			long end = endByteOffset(start, f);
			if(lastSentSeq == null || f.getSequenceNumber() >= lastSentSeq)
			{
				println("SENDing " + f.getSequenceNumber() + " " + start + ":" + end + " " + time() + " " + sendErr(f));
				lastSentSeq = f.getSequenceNumber();
			}
			else
			{
				println("ReSend. " + f.getSequenceNumber() + " " + start + ":" + end + " " + time() + " " + sendErr(f));
			}
		}
	}
	
	public void sent(AckFrame f, int sequenceNumber)
	{
		if(printerIsOn)
		{
			
			if(lastSentAck == null || f.getAckNumber() != lastSentSeq)
			{
				println("SENDing ACK " + sequenceNumber + " " + time() + " " + sendErr(f));
				lastSentAck = f.getAckNumber();
			}
			else
			{
				println("ReSend. ACK " + sequenceNumber + " " + time() + " " + sendErr(f));
			}
		}
	}
	
	public void chunkReceived(ChunkFrame f, int expectedSequenceNumber)
	{
		if(printerIsOn)
		{
			// if there is an error we output differently
			if(f.failedCheckSum())
			{
				println("RECV " + f.getSequenceNumber() + " " + "CRPT");
			}
			else if (f.getSequenceNumber() != expectedSequenceNumber)
			{
				if(f.getSequenceNumber() < expectedSequenceNumber)
				{
					//duplicate
					
					println("DUPL " + time() + " "  + f.getSequenceNumber() + " " + "!Seq");
				}
				else
				{
					println("RECV " + time() + " "  + f.getSequenceNumber() + " " + "!Seq"); // we throw away all packets received out of sequence even if not duplicates
				}
			}
			// if there is no error we move window because this is stop and wait
			else
			{
				println("RECV " + time() + " " + f.getSequenceNumber() + " " + "RECV");
			}
				
		}
	}
	
	public void ackReceived(AckFrame ackFrame, int expectedAckNumber, int sequenceNumber)
	{
		if(printerIsOn)
		{
			try
			{
				// if the ack matches we have to assume that the ack was for the current chunkFrame
				if(ackFrame.getAckNumber() == expectedAckNumber)
				{
					// if there is an error we output differently
					if(ackFrame.failedCheckSum())
					{
						println("AckRcvd " + sequenceNumber + " " + "ErrAck.");
					}
					// if there is no error we move window because this is stop and wait
					else
					{
						println("AckRcvd " + sequenceNumber + " " + "MoveWnd");
					}
				}
				// if the ack number does not match what was expected we assume that it was for the previously received chunkFrame
				else
				{
					int machedSequenceNumber = sequenceNumber - 1; // TODO This will need to change if window size is not fixed at 1
					
					// if the matched sequence number is negitive we throw an exception
					if(machedSequenceNumber < 0)
					{
						throw new Exception("ack mismatch corrilates to negitive sequence number");
					}
					
					// we print our output for a duplicate ack
					println("AckRcvd " + machedSequenceNumber + " " + "DuplAck");
				}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private String sendErr(Frame f)
	{
		String s;
		
		switch (f.getError())
		{
		case CORRUPT:
			s = "ERRR";
			break;
			
		case DROP:
			s = "DROP";
			break;
			
		case DELAY:
			s = "DLAY";
			break;

		default:
			s = "SENT";
			break;
		}
		
		return s;
	}
	
	

	private Long time()
	{
		return new Date().getTime() - startTime;
	}

	private long endByteOffset(long startOffset, ChunkFrame f)
	{
		long endByteOffset = (long) f.getLength() + startOffset;
		
		try
		{
			if(endByteOffset > fileSize )
			{
				throw new Exception("end byte offset(" + endByteOffset + ") too great given the filesize (" + fileSize + ")");
			}
			else if(endByteOffset < 0)
			{
				throw new Exception("end byte offset was less than zero");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		

		return endByteOffset;
	}

	public void print(String s)
	{
		if(printerIsOn)
			logPrintStream.print(s);
	}
	
	public void println(String s)
	{
		if(printerIsOn)
			logPrintStream.println(s);
	}
	
	private long startByteOffset(ChunkFrame f) 
	{

		long startByteOffset = (long) f.getSequenceNumber() * (long) maxChunkPakageSize;
		
		try
		{
			if(startByteOffset > fileSize - maxChunkPakageSize)
			{
				throw new Exception("start byte offset(" + startByteOffset + ") too great given the filesize (" + fileSize + ") and max chunk packet size(" + maxChunkPakageSize + ")");
			}
			else if(startByteOffset < 0)
			{
				throw new Exception("start byte offset was less than zero");
			}
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		

		return startByteOffset;
	}
}