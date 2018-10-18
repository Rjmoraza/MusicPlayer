import java.io.File;
import javax.swing.JFileChooser;

/** 
 * Clase Biblioteca encargada de contener y administrar la biblioteca de musica, tambien esta encargada de crear la biblioteca explorando el arbol de directorios
 * 
 * @author Rodolfo
 *
 */
public class Library 
{
	/**
	 * Almacena la biblioteca de canciones
	 */
	private Track[] library;
	
	/**
	 * Guarda la posicion que ocupara la proxima pista a agregar a la biblioteca
	 */
	private int nextTrack;
	
	/** 
	 * Si la biblioteca ha sido previamente creada en un archivo, se carga
	 * Si la biblioteca no ha sido previamente creada se debe construir a partir de una ruta indicada por el usuario
	 */
	public Library()
	{
		library = new Track[100];
		nextTrack = 0;
		// TODO
	}
	
	/** 
	 * Carga la biblioteca a partir de un archivo de texto previamente creado
	 */
	public void loadLibrary()
	{
		// TODO
	}
	
	public void buildLibrary()
	{
		// La raiz se obtiene de un explorador de archivos usando la clase correspondiente de la biblioteca swing
		// Documentacion del JFileChooser: https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html
		JFileChooser chooser = new JFileChooser();
		
		// Debemos garantizar que el explorador muestre unicamente directorios
		// https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html#setFileSelectionMode(int)
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		// El showOpenDialog recibe como parametro un componente de javax.swing, en este caso no nos interesa por que vamos a usar JavaFX por eso ponemos null
		// El showOpenDialog retorna un valor distinto segun el modo en que se cerro el dialogo
		// https://docs.oracle.com/javase/7/docs/api/javax/swing/JFileChooser.html#showOpenDialog(java.awt.Component)
		if(chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
		{
			// Si el FileChooser se cerro correctamente, necesitamos el directorio que fue seleccionado para empezar a explorar
			File root = chooser.getSelectedFile();
			
			// Se explora el directorio seleccionado por el usuario de forma recursiva buscando todos los archivos con extension .mp3
			exploreLibrary(root);
		}
	}
	
	/**
	 * Explora de forma recursiva el directorio seleccionado por el usuario
	 * Agrega a la biblioteca unicamente los archivos de tipo .mp3
	 * 
	 * @param root
	 */
	private void exploreLibrary(File root)
	{
		File[] subFiles = root.listFiles();
		
		for(int i = 0; i < subFiles.length; ++i)
		{
			if(subFiles[i].isDirectory())
			{
				exploreLibrary(subFiles[i]);
			}
			else if(subFiles[i].getName().endsWith(".mp3"))
			{
				addToLibrary(subFiles[i]);
			}
		}
	}
	
	/**
	 * Agrega una nueva cancion a la biblioteca a partir del archivo que se encontro
	 * Si la biblioteca esta llena, se agregan 100 espacios nuevos a la biblioteca
	 * @param trackFile
	 */
	private void addToLibrary(File trackFile)
	{
		// Si la biblioteca esta llena, tenemos que agrandarla
		if(nextTrack == library.length)
		{
			// Cada vez que tenemos que agrandar la biblioteca, agregamos 100 espacios mas
			Track[] newLibrary = new Track[library.length + 100];
			for(int i = 0; i < library.length; ++i)
			{
				// Movemos una por una las canciones que estan almacenadas en la biblioteca actual, hacia la nueva biblioteca
				// Se estan pasando las canciones por referencia, eso significa que no se estan copiando (no se esta haciendo memoria nueva para cada cancion independiente)
				newLibrary[i] = library[i];
			}
			// Reemplazamos la biblioteca actual por la nueva biblioteca
			library = newLibrary;
		}
		
		// Se almacena la nueva cancion en la posicion correspondiente y posteriormente se incrementa el indice de la cancion siguiente
		// Notese el uso del ++ y el uso del constructor de canciones que ya hace todo solo
		library[nextTrack++] = new Track(trackFile);
	}
	
	/**
	 * Imprime el largo de la biblioteca y todos los titulos de sus canciones
	 */
	public void printLibrary()
	{
		System.out.println("Library size: " + library.length);
		System.out.println("Track count: " + nextTrack);
		System.out.println("Wasted slots: " + (library.length - nextTrack));
		for(int i = 0; i < nextTrack; ++i)
		{
			System.out.println("Track No " + (i+1) + " : " + library[i].getTitle());
		}
	}
	
	/**
	 * Busca una canción por título
	 * 
	 * @param title El título de la canción a buscar
	 * @return La canción encontrada o null si no se encontró en la biblioteca
	 */
	public Track searchByTitle(String title) {
		return searchByTitle(title, 0, library.length);
	}
	
	private Track searchByTitle(String title, int limI, int limS) {
		if(limI > limS)
			return null;
		
		int midPoint = ((limS - limI) / 2) + limI;
		
		if(library[midPoint].getTitle().equalsIgnoreCase(title))
		{
			return library[midPoint];
		}
		else
		{
			int compare = library[midPoint].getTitle().compareToIgnoreCase(title);
			if(compare < 0)
			{
				return searchByTitle(title, limI, midPoint);
			}
			else
			{
				return searchByTitle(title, midPoint+1, limS);
			}
		}
	}
}
