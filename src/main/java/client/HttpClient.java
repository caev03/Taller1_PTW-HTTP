package client;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

public class HttpClient {

	private Socket socket;
	
	public HttpClient(String host, int port) throws UnknownHostException, IOException{
		socket = new Socket(host,port);
	}
	
	public File processRequest(String requestMessage, String host, String fileName) throws IOException{
		
		//TODO: implementar de acuerdo con instrucciones en el enunciado del taller 1
		OutputStream ous = socket.getOutputStream();
		InputStream ins = socket.getInputStream();
		try
		{
			RequestProcessor.getInstance().sendRequestMessage(ous, requestMessage, host, fileName);
			return ResponseProcessor.getInstance().processResponse(ins, fileName);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public File processRequestApache(String URL, String fileName)
	{
		try
		{
			System.out.println(URL);
			CloseableHttpClient client = HttpClientBuilder.create().build();
			HttpGet request = new HttpGet(URL);
			HttpResponse response = client.execute(request);
			BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			File tempFile = new File("descarga/"+fileName);
			if(!tempFile.exists())
			{
				tempFile.createNewFile();
			}
			PrintWriter writer = new PrintWriter(tempFile);
			String line = "";
			while ((line = rd.readLine()) != null) {
				writer.println(line);
			}
			writer.close();
			return tempFile;
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}
	
}
