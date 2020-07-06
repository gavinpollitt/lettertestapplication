package uk.gav.output;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.model.BucketLocationConstraint;
import software.amazon.awssdk.services.s3.model.CreateBucketConfiguration;
import software.amazon.awssdk.services.s3.model.CreateBucketRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import uk.gav.date.DateProvider;
import uk.gav.records.Record;

public class S3Target implements OutputTarget {

	@Autowired
	@Lazy
	private S3ClientManager s3ClientManager;

	@Autowired
	private OutputConfiguration outputConfiguration;

	@Autowired
	private DateProvider dateProvider;

	@Override
	public void forward(final Record r, final List<String> data) throws Exception {
		String bucket = outputConfiguration.getS3Target().getBucket();
		
		String content = data.stream().map(l -> l + "\n").reduce("", (a,v) -> a.concat(v));

		// Create bucket if it's not there!
		this.createBucket(bucket);

		LocalDateTime ldt = dateProvider.getDate();
		String dt = String.format("%02d%02d%02d", ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth());							

        // Put Object
		String fn = r.getType() + "_" + r.getFields().get("companyName").getValue() + ".txt";
        this.s3ClientManager.getS3Client().putObject(
        		PutObjectRequest.builder().bucket(bucket).key(dt + "/" + fn).build(),
                        RequestBody.fromString(content));

	}

	private void createBucket(String bucket) throws Exception {
		boolean bucketFound = true;
		try {
			bucketFound = s3ClientManager.getS3Client().listBuckets().buckets().stream().map(b -> b.name())
					.anyMatch(bn -> bn.equals(bucket));
		} catch (Exception e) {
			bucketFound = false;
		}

		if (!bucketFound) {
			// Create bucket
			CreateBucketRequest createBucketRequest = CreateBucketRequest.builder().bucket(bucket)
					.createBucketConfiguration(CreateBucketConfiguration.builder()
							.locationConstraint(
									BucketLocationConstraint.fromValue(outputConfiguration.getS3Target().getRegion()))
							.build())
					.build();
			s3ClientManager.getS3Client().createBucket(createBucketRequest);
		}
	}
	
}
