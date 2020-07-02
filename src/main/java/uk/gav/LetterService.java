package uk.gav;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import uk.gav.batch.BatchProcessor;

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

	// Expect this to become a list of front-end sources
	@Autowired
	private BatchProcessor batchProcessor;
	
	@Async
	public void scanRecords(final int sleep) {
		this.recordsProcessed = 0;
		this.running = true;
		
		while (this.running) {
			System.out.println("Performing a record scan");
			recordsProcessed += this.batchProcessor.performBatch();
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
		System.out.println("Run has been terminated");
		this.running = false;
		return new Metric(this.recordsProcessed);
	}
	
	@Transactional
	public void purge() {
		this.batchProcessor.purge();
	}
	
	public boolean isRunning() {
		return this.running;
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
