import java.io.File;
import java.util.regex.Pattern;

/**
 * Implementa la clase Cancion
 * 
 * @author Rodolfo
 *
 */
public class Track {
	private String title;
	private String album;
	private String artist;
	private int trackNo;
	private File file;
	
	/**
	 * Constructor de la clase Track que construye todas las partes de la cancion a partir de la informacion contenida en el archivo
	 * 
	 * @param file contiene la referencia al archivo que tiene la cancion
	 */
	public Track(File file)
	{
		// El nombre del archivo tiene el formato ## - [Titulo].mp3
		String fileName = file.getName();
		
		// Se divide la cancion usando como divisor el guion con espacios ( - )
		String[] nameParts = fileName.split("-");
		
		// La primera parte de la division es el numero de pista
		trackNo = Integer.parseInt(nameParts[0].trim());
		
		// La segunda parte de la division es el titulo de la pista, pero tiene 4 caracteres de mas: .mp3
		title = nameParts[1].substring(0, nameParts[1].length() - 4);
		title = title.trim();
		
		// La ruta absoluta del archivo es .../[artista]/[album]/[nombre de la cancion]
		String filePath = file.getAbsolutePath();
		
		// Al dividir la ruta se obtiene una lista con las partes individuales de la ruta
		// nos interesan la penultima (album) y antepenultima (artista)
		// https://stackoverflow.com/questions/10336293/splitting-filenames-using-system-file-separator-symbol
		String separatorPattern = Pattern.quote(System.getProperty("file.separator"));
		String[] pathParts = filePath.split(separatorPattern);
		
		// Penultima parte
		album = pathParts[pathParts.length - 2];
		
		// Antepenultima parte
		artist = pathParts[pathParts.length - 3];
		
		// Finalmente no debemos olvidar el archivo propiamente
		this.file = file;
	}
	
	/**
	 * Metodo de acceso para el atributo title
	 * 
	 * @return El titulo de la cancion
	 */
	public String getTitle() {
		return title;
	}
	
}
