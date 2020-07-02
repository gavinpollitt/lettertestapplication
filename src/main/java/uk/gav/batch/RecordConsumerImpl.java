package uk.gav.batch;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gav.records.Record;

/**
 * @author regen
 *
 */
@Component
public class RecordConsumerImpl implements RecordConsumer {

	@Autowired
	private SourceManager sourceManager;
	
	private final List<Record> records = new ArrayList<>();
	private final List<String> problems = new ArrayList<>();
	
	
	@Override
	public void acceptResult(Record r) {
		records.add(r);
	}

	@Override
	public void acceptResult(String problem) {
		this.problems.add(problem);
	}

	/**
	 * Determine if any problems have been found. If so, report them; otherwise, continue
	 * to deliver records to SourceManager
	 */
	@Override
	public boolean processResults() {
		boolean success = true;
		if (this.problems.size() > 0) {
			this.problems.stream().forEach(System.out::println);
			success = false;
			this.problems.clear();
			this.records.clear();
		}
		else {
			this.records.stream().forEach(this.sourceManager::addRecord);
			
			try {
				this.sourceManager.dumpSources();
			}
			catch (Exception e) {
				System.out.println(e);
				success = false;
			}
			finally {
				this.sourceManager.reset();
				this.problems.clear();
				this.records.clear();
			}
		}
		
		return success;
	}


}
