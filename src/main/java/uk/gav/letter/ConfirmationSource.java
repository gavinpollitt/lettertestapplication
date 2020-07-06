package uk.gav.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import uk.gav.output.OutputTarget;
import uk.gav.records.Record;
import uk.gav.records.Record1;

/**
 * LetterSource implementation for the Confirmation Letter.
 * @author regen
 *
 */
public class ConfirmationSource extends LetterSource<Record1> {

	private final static String TEMPLATE_LOC = "classpath:templates/Confirmation.txt";

	@Autowired
	@Qualifier("confirmationTarget")
	private OutputTarget outputTarget;
	
	@Override
	protected String getTemplateURI() {
		return TEMPLATE_LOC;
	}
	
	@Override
	protected OutputTarget getTarget() {
		return this.outputTarget;
	}

	/**
	 * Convenience method to correctly cast record to the type supported by this class.
	 */
	@Override
	public void consumeRecord(Record r) {
		if (r.getClass() == Record1.class) {
			this.addSource(Record1.class.cast(r));
		}
	}

}

