package oracle.sysman.emSDK.emaas.platform.savedsearch;

import java.io.IOException;

public class UpdateSavedSearch {

	private int m_intCommandNo;
	private String m_strEndPoint;
	private String m_strFilePath;
	private String m_strOutputPath;

	public static void main(String args[]) {
		UpdateSavedSearch obj = new UpdateSavedSearch();
		ImportSearchObject objUpdate = new ImportSearchObject();

		if (args.length == 0 || args.length == 6) {
			obj.configureFromArgs(args);
			if (obj.validateInput())
				return;
			if (obj.getOption() == 0) {
				obj.printHelp();
			}

			if (obj.getOption() == 1) {
				if (!objUpdate.isEndpointReachable(obj.getEndPoint())) {
					System.out.println("The endpoint was not reachable.");
					return;
				}
				obj.callService(obj.getEndPoint(), obj.getFilePath(),
						obj.getOutputPath());
			}
		} else {
			System.out.println("Invalid command line argument.");
		}

	}

	public int getOption() {
		return m_intCommandNo;
	}

	public void setOption(int iOption) {
		this.m_intCommandNo = iOption;
	}

	private void printHelp() {
		System.out.println("SavedSearch -url -inputfilepath  -outfilepath");
	}

	private void configureFromArgs(String[] args) {
		for (int index = 0; index < args.length; index = index + 2) {

			if (args[index].equalsIgnoreCase("-help")) {
				m_intCommandNo = 0;
			} else if (args[index].equalsIgnoreCase("-url")) {
				m_strEndPoint = args[index + 1];
			} else if (args[index].equalsIgnoreCase("-inputfilepath")) {
				m_strFilePath = args[index + 1];
			} else if (args[index].equalsIgnoreCase("-outputfilepath")) {
				m_strOutputPath = args[index + 1];
			}
		}
	}

	public String getEndPoint() {
		return m_strEndPoint;
	}

	public void setEndPoint(String url) {
		this.m_strEndPoint = url;
	}

	public String getFilePath() {
		return m_strFilePath;
	}

	public void setFilePath(String path) {
		this.m_strFilePath = path;
	}

	public String getOutputPath() {
		return m_strOutputPath;
	}

	public void setOutputPath(String path) {
		this.m_strOutputPath = path;
	}

	private boolean validateInput() {

		if (m_strEndPoint != null && m_strFilePath != null
				&& m_strOutputPath != null) {
			if (m_strEndPoint.equalsIgnoreCase("")) {
				System.out.println("Please enter valid endpoint.");
				return true;
			}

			if (m_strFilePath.equalsIgnoreCase("")) {
				System.out.println("Please enter valid file name.");
				return true;
			}

			if (m_strOutputPath.equalsIgnoreCase("")) {
				System.out.println("Please enter valid output file name.");
				return true;
			}
			m_intCommandNo = 1;
		}

		return false;
	}

	private void callService(String endpoint, String inputfile,
			String outputfile) {

		String data = "";
		String outputData = "";
		try {
			data = FileUtils.readFile(inputfile);

		} catch (IOException e) {
			System.out
					.println("An error occurred while reading the input file : "
							+ inputfile);
			return;
		} catch (Exception ex) {
			System.out.println("An error occurred while updating searches");
			return;
		}

		ImportSearchObject objUpdate = new ImportSearchObject();

		try {

			outputData = objUpdate.importSearches(endpoint, data);

		} catch (Exception e1) {

			System.out
					.println("An error occurred while creating or updating search object");
		}

		try {
			FileUtils.createOutputfile(outputfile, outputData);

		} catch (IOException e) {
			System.out
					.println("an error occurred while writing data to file : "
							+ outputfile);
			return;
		}

	}

}
