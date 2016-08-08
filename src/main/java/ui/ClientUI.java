package ui;

import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import javax.swing.*;

import client.HttpClient;


public class ClientUI extends JFrame{
	
	private JTextField hostText;
	private JTextField portText;
	private JTextField requestText;
	private JTextField fileNameText;
	
	
	public ClientUI(){
		setTitle("Cliente HTTP");
        setSize(390, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        JPanel panel = new JPanel();
        
        JLabel hostLabel = new JLabel("Host: ");
        JLabel portLabel = new JLabel("Port: ");
        JLabel requestLabel = new JLabel("Request: ");
        JLabel fileLabel = new JLabel("File: ");
        
        hostText = new JTextField(25);
        portText = new JTextField(25);
        
        // Ejemplo de request para el dominio google.com: 
        // GET search?q=Java HTTP/1.1
        requestText = new JTextField(25);
        fileNameText = new JTextField(25);
        
        JButton requestButton = new JButton("Request");
        requestButton.addActionListener(new ActionListener() {
           public void actionPerformed(ActionEvent e) {     
			  String host = hostText.getText();
			  int port = Integer.parseInt(portText.getText());
			  String requestMessage = requestText.getText();
			  String fileName = fileNameText.getText();
              
              try {
				HttpClient client = new HttpClient(host, port);
				File file = client.processRequest(requestMessage, host, fileName);
				Desktop.getDesktop().open(file.getAbsoluteFile());
              } catch (Exception e1) {
					e1.printStackTrace();
              }
           }
        });
        panel.add(hostLabel);
        panel.add(hostText);
        panel.add(portLabel);
        panel.add(portText);
        panel.add(requestLabel);
        panel.add(requestText);
        panel.add(fileLabel);
        panel.add(fileNameText);
        panel.add(requestButton);
        
        this.add(panel);
        
        
	}

	public static void main(String[] args) {
		ClientUI client = new ClientUI();
		client.setVisible(true);
	}

}
