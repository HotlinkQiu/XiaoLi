package simulation;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URISyntaxException;
import java.util.Scanner;

public class OutputSimulator {
	public static void outputToLog() {
		InputStream input = OutputSimulator.class.getClassLoader().getResourceAsStream("simulation/log4Presentation.txt");
		Scanner scanner = null;
		String path = "";
		try {
			path = OutputSimulator.class.getResource("").toURI().getPath();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		
		
		try {
			BufferedWriter bufWClear = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(path+"/output.log"), "utf-8"));
			bufWClear.write("");
			bufWClear.close();
			scanner = new Scanner(input);
			while(scanner.hasNext()) {
				BufferedWriter bufW = new BufferedWriter(new OutputStreamWriter(
						new FileOutputStream(path+"/output.log", true), "utf-8"));
				String line = scanner.nextLine();
				bufW.write(line+"\n");
				try {
					int time = 0;
					if(line.contains("ProblemInfo") || line.contains("试题理解"))
						time = ((int)(Math.random()*3))*10;
					else
						time = ((int)(Math.random()*30))*5;
						
					Thread.sleep(time);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				bufW.close();
			}
		} catch(IOException e) {
			e.printStackTrace();
		} finally {
			if(scanner != null) {
				scanner.close();
			}
		}
	}
	
	public static void main(String[] args) {
		outputToLog();
	}
}
