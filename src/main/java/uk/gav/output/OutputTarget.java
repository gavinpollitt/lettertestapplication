package uk.gav.output;

import java.util.List;

import uk.gav.records.Record;

public interface OutputTarget {
	public void forward(final Record r, final List<String> data) throws Exception;
}
