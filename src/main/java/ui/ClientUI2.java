package ui;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.HttpClient;
import client.RequestProcessor;


public class ClientUI2 extends JFrame implements ListSelectionListener{
	
	private JTextField hostText;
	private JTextField fileNameText;
	private JList jlist;
	
	
	public ClientUI2(){
		setTitle("Cliente HTTP");
        setSize(390, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        
        JLabel hostLabel = new JLabel("URL: ");
        JLabel fileLabel = new JLabel("File: ");
        jlist = new JList(RequestProcessor.getInstance().getHistoric().toArray(new String[0]));
        jlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jlist.setLayoutOrientation(JList.VERTICAL);
        jlist.setVisibleRowCount(-1);
        jlist.addListSelectionListener(this);
        
        hostText = new JTextField(25);
        
        // Ejemplo de request para el dominio google.com: 
        // GET search?q=Java HTTP/1.1
        fileNameText = new JTextField(25);
        
        JButton requestButton = new JButton("Request");
        requestButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {     
			  String host = hostText.getText().toLowerCase();
			  String hoster = hostText.getText().toLowerCase();
			  if(host.startsWith("http"))
			  {
				  host = host.substring(host.indexOf("//")+2);
			  }
			  String hosted = host.substring(0, host.indexOf("/"));
			  int port = 80;
			  String hostedwoport = hosted;
			  if(hosted.contains(":"))
			  {
				  port = Integer.parseInt(hosted.substring(hosted.indexOf(":")+1));
				  hostedwoport = hosted.substring(0, hosted.indexOf(":"));
			  }
			  String fileName = fileNameText.getText();
			  String requestMessage = "GET "+host.substring(hosted.length());
			  
              try 
              {
				HttpClient client = new HttpClient(hostedwoport, port);
				//File file = client.processRequest(requestMessage, hostedwoport, fileName);
				// En caso de quere correr con apache comente la linea anterior y descomente la siguiente linea.
				File file = client.processRequestApache(hoster, fileName);
				System.out.println(file);
				Desktop.getDesktop().open(file.getAbsoluteFile());
				DefaultListModel model = new DefaultListModel<>();
				ArrayList a = RequestProcessor.getInstance().getHistoric();
				for (int i = 0; i < a.size(); i++) 
				{
					model.add(i, a.get(i));
				}
				jlist.setModel(model);
              } catch (Exception e1) {
					e1.printStackTrace();
              }
           }
        });
        JScrollPane listScroller = new JScrollPane(jlist);
        listScroller.setPreferredSize(new Dimension(250, 300));
        panel.add(hostLabel);
        panel.add(hostText);
        panel.add(fileLabel);
        panel.add(fileNameText);
        panel.add(requestButton);
        panel.add(listScroller);
        
        this.add(panel);
        
        
	}

	public static void main(String[] args) {
		ClientUI2 client = new ClientUI2();
		client.setVisible(true);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) 
	{
		try 
		{
			String ruta = "descarga/"+RequestProcessor.getInstance().getHistoric().get(jlist.getSelectedIndex()).split(" - ")[2];
			Desktop.getDesktop().open(new File(ruta).getAbsoluteFile());
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}