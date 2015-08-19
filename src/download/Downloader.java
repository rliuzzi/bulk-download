package download;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import org.apache.tika.exception.TikaException;

import download.bean.Document;

/**
 * Simple process that executes in the directory where the input .tsv file is located and:
 * 	1.- Reads from a source .tsv file with two columns: documentId, documentExtension 
 * 	2.- Downloads the document from a url formatted like: urlRoot + documentId
 * 	3.- Guesses the extension of the document if not informed
 * 	4.- Saves the the downloaded document to a target directory
 * 	5.- Generate logs with output: error, info and console
 * 
 * @author romina.liuzzi
 *
 */
public class Downloader {
	
	private static final String PROCESS_NAME = "Downloader";
	
	private static String filePath = null;
	private static String source = null;
	private static String target = null;
	
	private static int documentsProcessed = 0;
	private static int documentsFailed = 0;
	
	private static Logger logger;

	public static void main(String[] args) throws IOException {
		
		Logger.logHeader(PROCESS_NAME);
		Date init = new Date();
		
		if (args.length > 2) {
			Logger.printTooManyArgs(PROCESS_NAME);
			return;
		} else if (args.length < 2) {
			if (args.length == 1 && Logger.isHelp()) {
				Logger.printHelpDownloader();
				return;
			}
			Logger.printMissingRequiredArgs(PROCESS_NAME);
			return;
		} else {
			filePath = args[0];
			source = args[1];
			target = initTargetDirectory(filePath, source);
			
			logger = new Logger(target);
			logger.log(Logger.CONSOLE, "SOURCELINK: " + source);
			logger.log(Logger.CONSOLE, "SOURCEFILE: " + filePath);
			logger.log(Logger.CONSOLE, "OUTPUT DIR: " + target);

		}
		
		Reader reader = new Reader();
		List<Document> images = reader.readFromFile(filePath);
		logger.log(Logger.CONSOLE, "Documents count: " + String.valueOf(images.size()));
		
		for(Document image: images){
			try {
				InputStream stream = download(image);
				if(image.getExtension() == null){
					image.setExtension(process(stream));
				} 
				save(download(image),image);
				documentsProcessed++;
				if(documentsProcessed % 100 == 0){
					logger.log(Logger.CONSOLE, "Documents Processed: " + String.valueOf(documentsProcessed));
				}
				logger.log(Logger.INFO, image.getId() + "\t" + image.getExtension());
			} catch (IOException | TikaException e) {
				documentsFailed++;
				logger.log(Logger.CONSOLE, "Documents Failed: " + String.valueOf(documentsFailed) + " imgId: " + image.getId());
				logger.log(Logger.ERROR, image.getId() + "\t" + image.getExtension());
			}
			
		}
		
		long timeLapsed = ((new Date()).getTime() - init.getTime())/1000;
		
		logger.log(Logger.CONSOLE, "DOCUMENTS PROCESSED: " + String.valueOf(documentsProcessed));
		logger.log(Logger.CONSOLE, "DOCUMENTS FAILED: " + String.valueOf(documentsFailed));
		logger.log(Logger.CONSOLE, "PROCESS LASTED: " + String.valueOf(timeLapsed) + " seconds");

	}
	
	private static InputStream download(Document image) throws IOException {
		URL url = new URL(source + image.getId());
		BufferedInputStream stream = new BufferedInputStream(url.openStream());
		return stream;
	}
	
	private static String process (InputStream stream) throws IOException, TikaException{
		Processor processor = new Processor();
		return processor.guessExtension(new BufferedInputStream(stream));
	}
	
	private static void save(InputStream in, Document image) throws MalformedURLException, IOException, FileNotFoundException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int n = 0;
		while (-1!=(n=in.read(buf)))
		{
		   out.write(buf, 0, n);
		}
		out.close();
		in.close();
		byte[] response = out.toByteArray();
						
		FileOutputStream fos = new FileOutputStream(target + "/" + image.getId() + image.getExtension());
		fos.write(response);
		fos.close();
		
	}
	
	private static String initTargetDirectory(String filePath, String context) throws IOException{
		String dir = (System.getProperty("user.home").concat("/").concat(filePath));
		dir = dir.substring(0, dir.lastIndexOf("/") + 1);
		dir = dir.concat(context.replace("/", ".").replace(":",  "."));
		
		File output = new File(dir);
		output.mkdir();
		return dir;
		
	}
	
	
}
