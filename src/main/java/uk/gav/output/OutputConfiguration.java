package uk.gav.output;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="output")
public class OutputConfiguration {
	private final FileTarget fileTarget = new FileTarget();
	
	public FileTarget getFileTarget() {
		return fileTarget;
	}

	public static class FileTarget {
		private String directory;

		public String getDirectory() {
			return directory;
		}

		public void setDirectory(final String outputDir) {
			this.directory = outputDir;
		}
	}
}
