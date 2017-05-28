package presenter;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Manages the game savings. Files are saved in the local application folder.
 * Files have name of the following format 'syyyy-MM-dd_HH-mm-ss.gol' ('s2010-10-20_20-50-50.gol')
 * Next: 'simple' name = name of format 'yyyy-MM-dd_HH-mm-ss'
 */
public class FileSystem {
	
	/**
	 * No instances.
	 */
	private FileSystem() {}
	
	/**
	 * Find all saved files.
	 *
	 * @return the list of 'simple' names of files
	 */
	public static List<String> findAllSavedFiles() {
		List<String> files = new ArrayList<>();
		File dir = new File(".");
		File[] filesList = dir.listFiles();
		for (File file : filesList) {
		    if (file.isFile() && file.getName().indexOf(".gol") > 0) {
		        files.add(file.getName().replaceFirst("s", "").replace(".gol", ""));
		    }
		}
		return files;
	}
	
	/**
	 * Save to file.
	 *
	 * @param matrix the matrix to save
	 * @return the 'simple' name of the saved file
	 */
	public static String saveToFile(byte[][] matrix) {
		Date now = new Date();
		String filename = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(now);
		File toSave = new File("s" + filename + ".gol");
		try {
			toSave.createNewFile();
			
			BufferedWriter writer = new BufferedWriter(new FileWriter(toSave));
			
			for (byte[] row : matrix) {
				for (byte b : row) {
					writer.write(b + ",");
				}
				writer.newLine();
			}
			writer.flush();
			writer.close();
		} catch (IOException e) {
			System.out.println("writing file:" + e.getMessage());
		}
		
		return filename;
	}
	
	/**
	 * Read from file to matrix.
	 *
	 * @param filename the 'simple' filename
	 * @param matrix the matrix to write the saving
	 */
	public static void readFromFileToMatrix(String filename, byte[][] matrix) {
		filename = "s" + filename + ".gol";
		try(BufferedReader breader = new BufferedReader(new FileReader(new File(filename)))) {
			String line;
			int i = 0; int j = 0;
			while ( (line = breader.readLine()) != null) {
				for (String c : line.split(",")) {
					
					matrix[i][j] = Byte.parseByte(c);
					
					if (++j >= matrix[0].length) {
						j = 0;
						if (++i >= matrix.length) break;
					}
				}
			}
		} catch (IOException e) {
			System.out.println("File reading error" + e.getMessage());
		}
	}
	
	/**
	 * Delete file.
	 *
	 * @param filename the 'simple' filename
	 */
	public static void deleteFile(String filename) {
		filename = "s" + filename + ".gol";
		File file = new File(filename);
		file.delete();
	}
}
