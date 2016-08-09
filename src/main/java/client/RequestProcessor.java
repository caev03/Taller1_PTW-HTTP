package client;

import java.io.*;
import java.util.*;

public class RequestProcessor{

	private ArrayList<String> historic;
	private static RequestProcessor instance = null;

	protected RequestProcessor (){
		historic = new ArrayList<String>();
	}

	public static RequestProcessor getInstance(){
		if (instance == null){
			instance = new RequestProcessor();
		}
		return instance;
	}

	public void sendRequestMessage(OutputStream output, String message, String host, String fileName) throws IOException
	{
		output.write((message+"\r\n").getBytes());
		output.write(("Host:"+host+"\r\n").getBytes());
		output.write("\r\n".getBytes());
		output.write("\r\n".getBytes());
		output.flush();
		historic.add(host + " - " + message + " - " + fileName);
	}

	public ArrayList<String> getHistoric(){
		return historic;
	}
	
	public void setHistoric(ArrayList<String> abc){
		historic = abc;
	}
}
