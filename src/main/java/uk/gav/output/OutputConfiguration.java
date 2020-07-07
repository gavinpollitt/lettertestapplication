package uk.gav.output;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix="output")
public class OutputConfiguration {
	private final FileTarget fileTarget = new FileTarget();
	private final S3Target s3Target = new S3Target();
	private final AMQPTarget amqpTarget = new AMQPTarget();
	
	public FileTarget getFileTarget() {
		return this.fileTarget;
	}
	
	public S3Target getS3Target() {
		return this.s3Target;
	}

	public AMQPTarget getAMQPTarget() {
		return this.amqpTarget;
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
	
	public static class AMQPTarget {
		private String host;
		private Integer port;
		private String user;
		private String password;
		private String virtualHost;
		public String getHost() {
			return host;
		}
		public void setHost(String host) {
			this.host = host;
		}
		public Integer getPort() {
			return port;
		}
		public void setPort(Integer port) {
			this.port = port;
		}
		public String getUser() {
			return user;
		}
		public void setUser(String user) {
			this.user = user;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		public String getVirtualHost() {
			return virtualHost;
		}
		public void setVirtualHost(String virtualHost) {
			this.virtualHost = virtualHost;
		}
		
	}
}
