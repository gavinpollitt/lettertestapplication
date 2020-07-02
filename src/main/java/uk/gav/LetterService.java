package uk.gav;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import uk.gav.batch.LineListener;
import uk.gav.batch.RecordConsumer;
import uk.gav.db.Record;
import uk.gav.db.RecordRepository;

/**
 * 
 * @author regen
 *
 * The service responsible maintaining the queue and its related backing store
 */
@Component
public class LetterService {
	private boolean running = false;
	private int recordsProcessed = 0;

	@Autowired
	@Qualifier("lineListeners")
	private List<LineListener> lineListeners;

	@Autowired
	private RecordConsumer recordConsumer;

	@Autowired
	private RecordRepository recordRepository;
	
	@Async
	public void scanRecords(final int sleep) {
		this.recordsProcessed = 0;
		this.running = true;
		
		while (this.running) {
			System.out.println("Performing a record scan");
			this.performBatch();
			int cnt = 0;
			while (cnt < sleep && this.running) {
				cnt++;
				try {
					Thread.sleep(1000);
				}
				catch (Exception e) {
					throw new RuntimeException("Oh no! " + e);
				}
			}
		}
	}
	
	public Metric stopScan() {
		System.out.println("Run has keep terminated");
		this.running = false;
		return new Metric(this.recordsProcessed);
	}
	
	@Transactional
	public void purge() {
		this.recordRepository.deleteProcessed();
	}
	
	public boolean isRunning() {
		return this.running;
	}
	
	@Transactional
	private void performBatch() {
		List<Record> records = recordRepository.findByProcessedOrderByCompanyAscIdAsc("N");
		
		System.out.println("I've found: " + records.size() + " records");
		List<String> recs = records.stream().map(r -> r.getData()).collect(Collectors.toList());

		this.processSingleBatch(recs);
		this.recordsProcessed += records.size();
		
		
		records.stream().forEach(r -> {
			r.setProcessed("Y");
			this.recordRepository.save(r);
		});
	}
	
	private boolean processSingleBatch(List<String> lines) {

		int lineNumber = 1;
		for (String line : lines) {
			final int lno = lineNumber;
			this.lineListeners.stream().forEach(ll -> ll.acceptCandidate(lno, line));
			lineNumber++;
		}
		//Send an EOF event in case any post-processing is required
		this.lineListeners.stream().forEach(ll -> ll.acceptCandidate(-1, "EOF|"));
		
		return this.recordConsumer.processResults();
	}
	
	public List<LineListener> getLineListeners() {
		return lineListeners;
	}

	protected static class Metric {
		private final int recordsProcessed;
		
		public Metric(final int rp) {
			this.recordsProcessed = rp;
		}
		public int getRecordsProcessed() {
			return this.recordsProcessed;
		}
	}
}
