package pdf.sample;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class PhantomTest {

	private static final String htmlFilePath = "{htmlfilepath or url}";
	private static final String jsFilePath = "{path to rasterize.js}";
	private static final String phantomJsFilePath = "{path to phantomjs}";
	private static final String outPutFile = "{outputfilename}";//this can be .pdf, .png

	public static void main(String[] args) {
		try {
			new PhantomTest().convertHtmlToPdf(htmlFilePath, jsFilePath, phantomJsFilePath, outPutFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void convertHtmlToPdf(String htmlFilePath, String jsFilePath, String phantomJsPath, String outPutFile)
			throws Exception {
		// load html file
		File htmlFile = new File(htmlFilePath);
		// load js file that will handle render html and convert to pdf
		File configFile = new File(jsFilePath);
		// tmp pdf file for output
		File pdfReport = new File(outPutFile);
		ProcessBuilder renderProcess = new ProcessBuilder(phantomJsPath, configFile.getAbsolutePath(),
				htmlFile.getAbsolutePath(), pdfReport.getAbsolutePath());
		Process phantom = renderProcess.start();
		// you need to read phantom.getInputStream() and phantom.getErrorStream()
		// otherwise if they output something the process won't end

		int exitCode = phantom.waitFor();

		if (exitCode != 0) {
			System.out.println("Not able to generate reports.");
			System.out.println(convertInputStreamToString(phantom.getErrorStream()));
			

		} else {
			System.out.println("Pdf generated: " + pdfReport.getAbsolutePath());
			
		}

	}

	private static String convertInputStreamToString(InputStream inputStream) throws IOException {

		ByteArrayOutputStream result = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int length;
		while ((length = inputStream.read(buffer)) != -1) {
			result.write(buffer, 0, length);
		}
		inputStream.close();
		return result.toString(StandardCharsets.UTF_8.name());

	}

}
