package uk.gav.batch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public abstract class BatchProcessor {
	private List<LineListener> lineListeners;

	private RecordConsumer recordConsumer;

	public abstract int performBatch();
	public abstract void purge();

	protected boolean processSingleBatch(List<String> lines) {

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

	@Autowired
	@Qualifier("lineListeners")
	public final void setLineListeners(List<LineListener> lineListeners) {
		this.lineListeners = lineListeners;
	}

	public RecordConsumer getRecordConsumer() {
		return recordConsumer;
	}

	@Autowired
	public final void setRecordConsumer(RecordConsumer recordConsumer) {
		this.recordConsumer = recordConsumer;
	}
	
	

}
