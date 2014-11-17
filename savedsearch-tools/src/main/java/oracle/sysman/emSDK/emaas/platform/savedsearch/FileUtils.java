package oracle.sysman.emSDK.emaas.platform.savedsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtils {

	public static void createOutputfile(String outputfile, String Data)
			throws IOException {

		File file = new File(outputfile);
		// if file doesnt exists, then create it
		if (!file.exists()) {
			file.createNewFile();
		}
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(Data);
		bw.close();
	}

	public static String readFile(String filePath) throws IOException {
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData);
		}
		reader.close();
		return fileData.toString();
	}
	
	public static boolean deleteFile(String filePath) throws Exception
	{
		File file = new File(filePath);		 
		return file.delete();
	}
	
	
	public static boolean fileExist(String path )
	{
		return new File(path).exists();
		
	}

}
