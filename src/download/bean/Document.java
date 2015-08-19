package download.bean;


public class Document{

	private String id;
	private String extension;
	
	
	public Document(String id, String extension) {
		super();
		this.id = id;
		this.extension = extension;
	}
	
	public Document(String id) {
		super();
		this.id = id;
	}
	
	/**
	 * @return the id
	 */
	
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the file extension
	 */
	public String getExtension() {
		return extension;
	}
	/**
	 * @param extension the extension to set
	 */
	public void setExtension(String matrix) {
		this.extension = matrix;
	}

	@Override
	public String toString(){
		return new StringBuffer(" id: ").append(this.id == null ? "" : this.id).append(", extension: ").append(this.extension == null ? "" : this.extension).toString();
	}
	
	
}
