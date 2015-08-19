package download;

import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.config.TikaConfig;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MediaType;
import org.apache.tika.mime.MimeType;

public class Processor {
	
	public String guessExtension(InputStream stream) throws IOException, TikaException  {
	    
	    TikaConfig config = TikaConfig.getDefaultConfig();
	    MediaType mediaType = config.getMimeRepository().detect(stream, new Metadata());
	    MimeType mimeType = config.getMimeRepository().forName(mediaType.toString());
	    String extension = mimeType.getExtension();
	    return extension;	    
    
	}
	
	
	
}
