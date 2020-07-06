package uk.gav.output;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="output")
public class OutputConfiguration {
	private final FileTarget fileTarget = new FileTarget();
	private final S3Target s3Target = new S3Target();
	
	public FileTarget getFileTarget() {
		return fileTarget;
	}
	
	public S3Target getS3Target() {
		return s3Target;
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
	
	public static class S3Target {
		private String uri;
		private String bucket;
		private String region;
		private String auth;
		
		public String getUri() {
			return uri;
		}
		public void setUri(String uri) {
			this.uri = uri;
		}
		public String getBucket() {
			return bucket;
		}
		
		public void setBucket(String bucket) {
			this.bucket = bucket;
		}
		
		public String getRegion() {
			return region;
		}
		public void setRegion(String region) {
			this.region = region;
		}
		public String getAuth() {
			return auth;
		}
		public void setAuth(String auth) {
			this.auth = auth;
		}
		
	}
}
