package uk.gav.output;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import uk.gav.date.DateProvider;
import uk.gav.records.Record;

public class FileTarget implements OutputTarget {
	
	@Autowired
	private OutputConfiguration configuration;
	
	@Autowired
	private DateProvider dateProvider;


	protected String getFilename(Record record) {
		return record.getType() + "_" + record.getFields().get("companyName").getValue() + ".txt";
	}


	@Override
	public void forward(final Record r, final List<String> data) throws Exception {
		LocalDateTime ldt = dateProvider.getDate();
		String dt = String.format("%02d%02d%02d", ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth());							
		Path parentD = Paths.get(configuration.getFileTarget().getDirectory() + "/" + dt + "/" + getFilename(r));
		Path fPath = Files.createDirectories(parentD.getParent());
	    
		Path p = Files.createFile(parentD);
		Files.write(p, data);
	}

}
