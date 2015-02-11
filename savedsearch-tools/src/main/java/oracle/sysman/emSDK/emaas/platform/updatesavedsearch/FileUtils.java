package oracle.sysman.emSDK.emaas.platform.updatesavedsearch;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils
{

	public static void createOutputfile(String outputfile, String Data) throws IOException
	{
		File file = null;
		FileWriter fw = null;
		BufferedWriter bw = null;
		// if file doesnt exists, then create it
		try {
			file = new File(outputfile);
			if (!file.exists()) {
				file.createNewFile();
			}
			fw = new FileWriter(file.getAbsoluteFile());
			bw = new BufferedWriter(fw);
			bw.write(Data);
		}
		finally {
			if (bw != null) {
				bw.close();
			}
			if (fw != null) {
				fw.close();
			}
		}
	}

	public static boolean deleteFile(String filePath) throws Exception
	{
		File file = new File(filePath);
		return file.delete();
	}

	public static boolean fileExist(String path)
	{
		return new File(path).exists();

	}

	public static String readFile(InputStream is) throws IOException
	{
		StringBuilder stringBuilder = new StringBuilder();
		InputStreamReader isReader = new InputStreamReader(is);
		BufferedReader bufferedReader = new BufferedReader(isReader);
		try {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("--") || line.startsWith("Rem") || line.trim().length() == 0) {
					continue;
				}
				stringBuilder.append(line + System.getProperty("line.separator"));
			}
			System.out.print(stringBuilder.toString());
		}
		catch (IOException ex) {

		}
		finally {

			try {
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (is != null) {
					is.close();
				}
			}
			catch (IOException e) {
				e.printStackTrace();
			}

		}
		return stringBuilder.toString();

	}

	public static String readFile(String filePath) throws IOException
	{
		BufferedReader reader = null;
		FileReader fReader = null;
		StringBuffer fileData = new StringBuffer();
		try {
			fReader = new FileReader(filePath);
			reader = new BufferedReader(fReader);
			char[] buf = new char[1024];
			int numRead = 0;
			if (reader != null) {
				while ((numRead = reader.read(buf)) != -1) {
					String readData = String.valueOf(buf, 0, numRead);
					fileData.append(readData);
				}
			}
		}
		finally {
			if (reader != null) {
				reader.close();
			}
			if (fReader != null) {
				fReader.close();
			}
		}
		return fileData.toString();
	}

}
