package uk.gav.batch;

import org.springframework.beans.factory.annotation.Autowired;

import uk.gav.records.ErrorRecord;
import uk.gav.records.Record2;

/** 
 * Listener class to intercept record type 2 and perform the appropriate processing
 * depending on the event received.
 * @author regen
 *
 */
public class DiscountListener implements LineListener {

	private RecordConsumer recordListener;

	@Override
	public void acceptCandidate(final int lineNumber, final String recordLine) {
		String rec = LineListener.getRecType(recordLine);

		try {
			if (rec.equals("2")) {
				// Hit a record 2
				this.recordListener.acceptResult(new Record2(recordLine));
			}
		} catch (Exception e) {
			this.recordListener.acceptResult(new ErrorRecord(lineNumber,"Line " + lineNumber + "->" + e.getMessage()));
		}
	}

	@Autowired
	@Override
	public void setRecordListener(RecordConsumer recordListener) {
		this.recordListener = recordListener;
	}

}
