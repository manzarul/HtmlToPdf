package pdf.sample;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class Chrome {

	private static final String htmlFilePath = "{htmlfilepath or url}";
	private static final String outPutFilePath = "{outputfilename}";//this can be .pdf, .png

	public static void main(String[] args) {
		try {
			
                    File htmlFile = new File (htmlFilePath);
                    File outPutFile = new File (outPutFilePath);
                   convertHtmlToPdf(htmlFile, outPutFile);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void convertHtmlToPdf(File htmlFile, File pdfFile) {
			 Process process = null;
			try {
				boolean isWindows = System.getProperty("os.name").toLowerCase().startsWith("windows");
				Runtime rt = Runtime.getRuntime();

				if (isWindows) {
					process = rt.exec(new String[] { "cmd.exe", "/c", "google-chrome --headless --print-to-pdf="
							+ pdfFile.getAbsolutePath() + " " + htmlFile.getAbsolutePath() });
				} else {
					process = rt.exec(new String[] { "sh", "-c", "google-chrome --headless --print-to-pdf="
							+ pdfFile.getAbsolutePath() + " " + htmlFile.getAbsolutePath() });
				}
				System.out.println(" Input stream value " + convertInputStreamToString(process.getInputStream()));
				System.out.println(" Error stream value " + convertInputStreamToString(process.getErrorStream()));
				process.waitFor(10, TimeUnit.SECONDS);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				System.out.println(" Is Process active : " + process.isAlive());
				
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
