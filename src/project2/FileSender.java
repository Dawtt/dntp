/**
 * File Created by Joshua Zierman on Oct 10, 2018
 */
package project2;

import java.io.File;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;

import project1.Chunk;
import project1.FileSplitter;
import project2.args.ArgList;
import project2.args.DebugModeArg;
import project2.args.ErrorChanceArg;
import project2.args.FileArg;
import project2.args.HelpArg;
import project2.args.IntroduceErrorArg;
import project2.args.MaxSizeOfChunkArg;
import project2.args.ProxyAddressArg;
import project2.args.ProxyPortArg;
import project2.args.ReceiverAddressArg;
import project2.args.ReceiverPortArg;
import project2.args.SenderAddressArg;
import project2.args.SenderPortArg;
import project2.args.TimeoutArg;
import project2.args.WindowSizeArg;
import project2.slidingWindow.SenderWindow;

/**
 * @author Joshua Zierman [py1422xs@metrostate.edu]
 *
 */
public class FileSender
{
	private final static String SENDER_PROGRAM_TITLE = "ChunkFrame Sender";
	private final static String SENDER_PROGRAM_DESCRIPTION = "<description not done>"; // TODO write description of the sender program
	
	//Args
	static FileArg fileArg = new FileArg("-f");	 
	static SenderAddressArg senderAddressArg = new SenderAddressArg("-sa");
	static ProxyAddressArg errorProxyAddressArg = new ProxyAddressArg("-pa"); 
	static ReceiverAddressArg receiverAddressArg = new ReceiverAddressArg("-ra"); 
	static SenderPortArg senderPortArg = new SenderPortArg("-sp");
	static ProxyPortArg errorProxyPortArg = new ProxyPortArg("-pp");
	static ReceiverPortArg receiverPortArg = new ReceiverPortArg("-rp"); 
	static WindowSizeArg windowSizeArg = new WindowSizeArg("-w");
	static TimeoutArg timeoutArg = new TimeoutArg("-t");
	static ErrorChanceArg errorChanceArg = new ErrorChanceArg("-d");
	static MaxSizeOfChunkArg maxSizeOfChunkArg = new MaxSizeOfChunkArg("-s");
	static IntroduceErrorArg introduceErrorArg = new IntroduceErrorArg("-e");
	
	//Toggle Args
	static DebugModeArg debugModeArg = new DebugModeArg("-debug");
	static HelpArg helpArg = new HelpArg("-help", ChunkFrameSender.SENDER_PROGRAM_TITLE, ChunkFrameSender.SENDER_PROGRAM_DESCRIPTION);
	
	//Destination vars
	static InetAddress destinationAddress;
	static int destinationPort;
	
	//Printer
	static final DebugPrinter debug = new DebugPrinter(debugModeArg, System.out); 
	
	public static void main(String[] args) throws Exception
	{
		// handle the command line arguments
		ArgList.updateFromMainArgs(args);
		
		// Determine Packet Size
		short packetSize = (short) (project2.Defaults.ACK_PACKET_LENGTH + 4 + maxSizeOfChunkArg.getValue());
		
		// set destination
		if(introduceErrorArg.getValue())
		{
			destinationAddress = errorProxyAddressArg.getValue();
			destinationPort = errorProxyPortArg.getValue();
		}
		else
		{

			destinationAddress = receiverAddressArg.getValue();
			destinationPort = receiverPortArg.getValue();
		}
		
		// gets the input file
		File inFile = fileArg.getValue();
		
		
		// split up the file into chunks
		LinkedList<Chunk> chunkList = new LinkedList<Chunk>();
		FileSplitter splitter = new FileSplitter(inFile.getAbsolutePath(), maxSizeOfChunkArg.getValue());
		splitter.overwrite(chunkList);
		
		// convert chunks into Frames and send
		int seqNum = 0;
		int ackNum = 0;
//		DatagramSocket socket = new DatagramSocket(destinationPort);
//		
//			SenderWindow window = new SenderWindow(chunkList, windowSizeArg.getValue(), packetSize, socket, timeoutArg.getValue(), destinationAddress, destinationPort);
//			window.run();
		
		ChunkFrameSender sender = new ChunkFrameSender();
	}
}
