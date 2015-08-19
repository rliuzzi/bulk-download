package download;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class Logger {
	
	public static final int ERROR = 0;
	public static final int CONSOLE = 1;
	public static final int INFO = 2;	
	
	private static String errorLog = "/error.txt";
	private static String consoleLog = "/execution.txt";
	private static String infoLog = "/info.txt";
	
	//loggin root directory
	private String loggingDirectory;
	//log documents with errors
	private BufferedWriter errorWriter;
	//log console output: execution variables, execution time, number of documents processed, etc
	private BufferedWriter consoleWriter;
	//log documents processed
	private BufferedWriter infoWriter;
	
	public Logger(String loggingDirectory) throws IOException {
		super();
		this.loggingDirectory = loggingDirectory;
		this.errorWriter = new BufferedWriter(new FileWriter(new File(loggingDirectory + errorLog)));
		this.consoleWriter = new BufferedWriter(new FileWriter(new File(loggingDirectory + consoleLog)));
		this.infoWriter = new BufferedWriter(new FileWriter(new File(loggingDirectory + infoLog)));
	}

	static void logHeader(String processName){
		System.out.println("*****".concat(processName).concat("*****"));
	}
	
	static void printHelpDownloader(){
		System.out.println("call syntax: java Downloader fileToRead urlRoot");
		System.out.println("call i.e:");
		System.out.println("java Downloader Desktop/test/test.tsv http://www.google.com?image=");
		System.out.println("NOTE: fileToRead path is realtive to user.home: "
						.concat(System.getProperty("user.home").concat(
								"/")));
	}
	
	static void printHelpReader(){
		System.out.println("call syntax: java Reader fileToRead");
		System.out.println("call i.e:");
		System.out.println("java Reader Desktop/test/test.tsv");
		System.out.println("NOTE: fileToRead path is realtive to user.home: "
				.concat(System.getProperty("user.home").concat("/")));
	}
	
	static boolean isHelp(String ... args){
		return "help".equals(args[0]) || "-help".equals(args[0]);
	}
	
	static void printMissingRequiredArgs(String processName){
		System.out.println("Missing required argument!");
		showCheckHelpMessage(processName);
	}
	
	static void printTooManyArgs(String processName){
		System.out.println("Too many args");
		showCheckHelpMessage(processName);
		
	}
	
	static void showCheckHelpMessage(String processName){
		System.out.println("Check help: java ".concat(processName).concat(" help"));
	}
	
	void log(int level, String message) throws IOException {
		
		switch(level){ 
			case INFO:
				//System.out.println(message);
				this.infoWriter = new BufferedWriter(new FileWriter(new File(loggingDirectory + infoLog), true));
				this.infoWriter.append(message + "\n");
				this.infoWriter.flush();
				this.infoWriter.close();
				break;
			case CONSOLE:
				System.out.println(message);
				this.consoleWriter = new BufferedWriter(new FileWriter(new File(loggingDirectory + consoleLog), true));
				this.consoleWriter.append(message + "\n");
				this.consoleWriter.flush();
				this.consoleWriter.close();
				break;
			case ERROR:
				this.errorWriter = new BufferedWriter(new FileWriter(new File(loggingDirectory + errorLog), true));
				this.errorWriter.append(message + "\n");
				this.errorWriter.flush();
				this.errorWriter.close();
				break;
		}
		
	}
	
}
