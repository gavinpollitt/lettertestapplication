package uk.gav.batch;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import uk.gav.letter.LetterSource;
import uk.gav.records.Record;

/**
 * Simple utility class to hold, in memory, the successful records used to issue
 * to the letter generation.
 * @author regen
 *
 */

@Component
public class SourceManager {

	@Autowired
	@Qualifier("letterSources")
	private List <LetterSource<? extends Record>> letterSources;

	public void addRecord(Record r) {
		this.letterSources.stream().forEach(ls -> ls.consumeRecord(r));
	}

	public void dumpSources() {
		this.letterSources.stream().forEach(this::gl);
	}
	
	public void reset() {
		this.letterSources.stream().forEach(ls -> ls.reset());
	}
	
	/**
	 * Utilityu method to convert checked to unchecked exception due to lambda not catering
	 * for Checked Exception
	 * @param ls
	 */
	private void gl(final LetterSource<? extends Record> ls) {
		try {
			ls.generateLetters();
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public List<LetterSource<? extends Record>> getLetterSources() {
		return letterSources;
	}

	public void setLetterSources(List<LetterSource<? extends Record>> letterSources) {
		this.letterSources = letterSources;
	}
}
