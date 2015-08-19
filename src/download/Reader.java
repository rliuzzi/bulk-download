package download;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import download.bean.Document;

/**
 * Simple process that executes in the directory where the input .tsv file is located 
 * reads from a source .tsv file with two columns: documentId, documentExtension 
 * and returns a Document list.
 * 	
 * @author romina.liuzzi
 *
 */
public class Reader {
	
	private static String PROCESS_NAME = "Reader";

	private static int OK = 1;
	private static int KO = -1;

	private static File fileToRead = null;

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String... args) throws Exception {

		String filePath = "";
		if (args.length > 1) {
			Logger.printTooManyArgs(PROCESS_NAME);
			return;
		} else if (args.length == 0) {
			Logger.printMissingRequiredArgs(PROCESS_NAME);
			return;
		} else {
			if (Logger.isHelp(args)) {
				Logger.printHelpReader();
				return;
			} else {
				filePath = (args[0]);
			}
		}

		Reader reader = new Reader();

		if (Reader.KO == initExecutionDirectory(filePath)) {
			System.out.println("Missing fileToRead argument");
			System.out.println("Check help: java Reader help");
			return;
		}

		reader.readFromFile(filePath);
		
	}
	
	/**
	 * Example: filePath = Desktop/test/test.tsv
	 * */
	public static int initExecutionDirectory(String filePath) {
		String path = System.getProperty("user.home").concat("/");
		if (filePath.isEmpty()) {
			return Reader.KO;
		}
		fileToRead = new File(path.concat(filePath));
		return Reader.OK;
	}
	
	public List<Document> readFromFile(String filePath) throws IOException {
		Logger.logHeader(PROCESS_NAME);
		Reader reader = new Reader();
		if (Reader.OK == initExecutionDirectory(filePath)) {
			return reader._readFromFile(fileToRead.getAbsolutePath());
		} 
		return null;
	}

	private List<Document> _readFromFile(String filePath) throws IOException {
		
		List<Document> images = new ArrayList<Document>();
		
		BufferedReader in = new BufferedReader(new FileReader(fileToRead));
		String line;
		while((line = in.readLine()) != null)
		{
		    String[] words = line.split("\t");
		    Document image;
		    if(words.length > 1){
		    	image = new Document(words[0], words[1]);
		    } else {
		    	image = new Document(words[0]);
		    }
		    images.add(image);
		    //System.out.println(image.toString());
		}
		in.close();
		return images;
	}

}
