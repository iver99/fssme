package oracle.sysman.emSDK.emaas.platform.updatesavedsearch;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileUtils
{

	public static void createOutputfile(String outputfile, String Data) throws IOException
	{
		File file = null;
		FileOutputStream out = null;
		OutputStreamWriter outputStreamWriter = null;
		// if file doesnt exists, then create it
		try {
			file = new File(outputfile);
			if (!file.exists()) {
				file.createNewFile();
			}
			out = new FileOutputStream(file.getAbsoluteFile());
			outputStreamWriter = new OutputStreamWriter(out, "UTF-8");
			outputStreamWriter.write(Data);

		}
		finally {
			if (outputStreamWriter != null) {
				outputStreamWriter.close();
			}
			if (out != null) {
				out.close();
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
		InputStreamReader isReader = new InputStreamReader(is, "UTF-8");
		BufferedReader bufferedReader = new BufferedReader(isReader);
		try {
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				if (line.startsWith("--") || line.startsWith("Rem") || line.trim().length() == 0) {
					continue;
				}
				stringBuilder.append(line + System.getProperty("line.separator"));
			}
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
		InputStream in = null;
		InputStreamReader reader = null;
		StringBuffer fileData = new StringBuffer();
		try {
			in = new FileInputStream(filePath);
			reader = new InputStreamReader(in, "UTF-8");

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
			if (in != null) {
				in.close();
			}
		}
		return fileData.toString();
	}

}
