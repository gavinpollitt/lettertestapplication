package uk.gav.output;

import java.net.URI;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import software.amazon.awssdk.auth.credentials.AwsCredentials;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;

/**
 * Only both loading if it's used!
 * 
 * @author regen
 *
 */
@Component
@Lazy
public class S3ClientManager {
	private S3Client s3Client;

	@Autowired
	private OutputConfiguration outputConfig;

	@PostConstruct
	public void openS3Client() throws Exception {
		URI endpoint = URI.create(outputConfig.getS3Target().getUri());
		String region = outputConfig.getS3Target().getRegion();
		String auth = outputConfig.getS3Target().getAuth();

		String k = null;
		String s = null;
		if (auth != null && auth.length() > 0) {
			k = auth.split(":")[0];
			s = auth.split(":")[1];
		}

		final String secret = s;
		final String key = k;

		if (k == null) {
			this.s3Client = S3Client.builder().endpointOverride(endpoint).region(Region.of(region)).build();
		} else {
			this.s3Client = S3Client.builder().endpointOverride(endpoint).region(Region.of(region))
					.credentialsProvider(() -> {
						return new AwsCredentials() {

							@Override
							public String secretAccessKey() {
								// TODO Auto-generated method stub
								return secret;
							}

							@Override
							public String accessKeyId() {
								// TODO Auto-generated method stub
								return key;
							}
						};
					}).build();
		}

	}

	public S3Client getS3Client() {
		return s3Client;
	}

}
