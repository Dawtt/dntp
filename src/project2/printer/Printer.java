package project2.printer;

import java.io.PrintStream;

/**
 * A printer that will allow printing only if the printer is on
 * 
 * @author Joshua Zierman [py1422xs@metrostate.edu]
 *
 */
public class Printer
{
	protected Boolean printerIsOn;
	protected PrintStream printStream;

	/**
	 * Constructs a Printer object
	 * 
	 * @param printerIsOn
	 *            a boolean value indicating that the printer is on or off
	 * @param printStream
	 *            the print stream to set the printer's print stream to
	 */
	public Printer(Boolean printerIsOn, PrintStream printStream)
	{
		this.printerIsOn = printerIsOn;
		this.printStream = printStream;
	}

	/**
	 * Prints a string if printer is on
	 * 
	 * @param s
	 *            the string to print
	 */
	public void print(String s)
	{
		if (printerIsOn)
		{
			printStream.print(s);
		}
	}

	/**
	 * checks if printer is off
	 * 
	 * @return true if printer is off else false
	 */
	public boolean printerIsOff()
	{
		return !printerIsOn;
	}

	/**
	 * checks if printer is on
	 * 
	 * @return true if printer is on else false
	 */
	public boolean printerIsOn()
	{
		return printerIsOn;
	}

	/**
	 * Prints a string as a new line if printer is on
	 * 
	 * @param s
	 *            the string to print
	 */
	public void println(String s)
	{
		if (printerIsOn)
		{
			printStream.println(s);
		}
	}

	/**
	 * sets the print stream of this printer to a new print stream
	 * 
	 * @param newPrintStream
	 *            the print stream to set the printer's print stream to
	 */
	public void setPrintStream(PrintStream newPrintStream)
	{
		this.printStream = newPrintStream;
	}

	/**
	 * Turns off printer
	 */
	public void turnOff()
	{
		printerIsOn = false;
	}

	/**
	 * Turns on printer
	 */
	public void turnOn()
	{
		printerIsOn = true;
	}
}
