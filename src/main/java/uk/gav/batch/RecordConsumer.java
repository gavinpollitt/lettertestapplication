package uk.gav.batch;

import uk.gav.records.ErrorRecord;
import uk.gav.records.Record;

/**
 * Interface specifying the behaviour of classes responding to Record events.
 * @author regen
 *
 */
public interface RecordConsumer {
	public void acceptResult(Record r);
	public void acceptResult(ErrorRecord problem);
	public boolean processResults();
}
