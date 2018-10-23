package project2;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;
import java.util.PriorityQueue;
import java.util.Queue;

import project2.frame.Frame;


/** A collection of delayed frames that can be retrieved only after the trigger time
 * @author Joshua Zierman [py1422xs@metrostate.edu]
 *
 * @param <F> The type of frame collected
 */
public class DelayedFrameCollection <F extends Frame>
{
	private class Node implements Comparable<Node>
	{
		F frame;
		Long triggerTime;
		
		Node(F frame, Long triggerTime)
		{
			this.frame = frame;
			this.triggerTime = triggerTime;
		}
		
		@Override
		public int compareTo(Node arg0)
		{
			return this.triggerTime.compareTo(arg0.triggerTime);
		}
	}
	
	public class DelayedFrameSender implements Runnable
	{
		boolean running = false;
		private InetAddress destinationAddress;
		private Integer destinationPort;
		private DatagramSocket socket;

		DelayedFrameSender(DatagramSocket socket, InetAddress destinationAddress, int destinationPort)
		{
			this.socket = socket;
			this.destinationAddress = destinationAddress;
			this.destinationPort = destinationPort;
			run();
		}
		
		@Override
		public void run()
		{
			running = true;
			Queue<F> frames;
			while(running)
			{
				System.out.println("hi");
				frames = getAllReadyFrames();
				for(F frame : frames)
				{
					try
					{
						send(frame);
					}
					catch (IOException e)
					{
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
		public void stop()
		{
			running = false;
		}
		
		private void send(F frame) throws IOException
		{
			DatagramPacket packet = frame.toDatagramPacket(destinationAddress, destinationPort);
			socket.send(packet);
		}
		
	}
	
	public DelayedFrameCollection(DatagramSocket socket, InetAddress destinationAddress, int destinationPort)
	{
		delayedFrameSender = new DelayedFrameSender(socket, destinationAddress, destinationPort);
	}
	
	private PriorityQueue<Node> queue = new PriorityQueue<>();
	private DelayedFrameSender delayedFrameSender;
	
	/** Add a frame with a specified delay
	 * @param frame the frame to add to the delayed frame collection
	 * @param msDelay how long to delay the frame in ms
	 */
	public void add(F frame, int msDelay)
	{
		long triggerTime = new Date().getTime() + msDelay;
		queue.add(new Node(frame, triggerTime));
	}
	
	/** Get all ready frames as a queue
	 * @return a Queue of Frames that are ready.
	 */
	public Queue<F> getAllReadyFrames()
	{
		PriorityQueue<F> triggered = new PriorityQueue<>();
		
		// while the queue is not empty and the lowest timed value is greater than the current time
		while(!queue.isEmpty() && queue.peek().triggerTime.compareTo(new Date().getTime()) > 0)
		{
			// add the dequeued frame to the results that will be returned
			triggered.add(queue.poll().frame);
		}
		return triggered;
	}
	
	/** Get the next ready frame
	 * @return a Frame if one is ready or null otherwise
	 */
	public F getNextReadyFrame()
	{
		F frame = null;
		
		// if the queue is not empty and the lowest timed value is greater than the current time
		if(!queue.isEmpty() && queue.peek().triggerTime.compareTo(new Date().getTime()) > 0)
		{
			// set the return Frame to the dequeued frame
			frame = queue.poll().frame;
		}
		
		return frame;
	}
}