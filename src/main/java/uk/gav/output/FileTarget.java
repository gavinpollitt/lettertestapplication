package uk.gav.output;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import uk.gav.records.Record;

public class FileTarget implements OutputTarget {
	
	@Autowired
	private OutputConfiguration configuration;
	
	protected String getFilename(Record record) {
		return record.getType() + "_" + record.getFields().get("companyName").getValue() + ".txt";
	}


	@Override
	public void forward(final Record r, final List<String> data) throws Exception {
		Path p = Paths.get(URI.create(configuration.getFileTarget().getDirectory() + "/" + getFilename(r)));
		p = Files.createFile(p);
		Files.write(p, data);
	}

}
