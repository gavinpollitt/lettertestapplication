package uk.gav.db;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gav.batch.BatchProcessor;

@Component
public class DBBatchProcessor extends BatchProcessor {

	@Autowired
	private RecordRepository recordRepository;

	@Transactional
	public int performBatch() {
		List<Record> records = recordRepository.findByProcessedOrderByCompanyAscIdAsc("N");
		
		System.out.println("I've found: " + records.size() + " records");
		List<String> recs = records.stream().map(r -> r.getData()).collect(Collectors.toList());

		this.processSingleBatch(recs);
		
		records.stream().forEach(r -> {
			r.setProcessed("Y");
			this.recordRepository.save(r);
		});
		
		return records.size();
	}
	
	@Transactional
	public void purge() {
		this.recordRepository.deleteProcessed();
	}

}
