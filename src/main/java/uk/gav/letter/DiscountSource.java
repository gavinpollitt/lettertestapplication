package uk.gav.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import uk.gav.output.OutputTarget;
import uk.gav.records.Record;
import uk.gav.records.Record2;

/**
 * LetterSource implementation for the Confirmation Letter.
 * @author regen
 *
 */
public class DiscountSource extends LetterSource<Record2> {

	private final static String TEMPLATE_LOC = "classpath:templates/Discount.txt";

	@Autowired
	@Qualifier("discountTarget")
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
		if (r.getClass() == Record2.class) {
			this.addSource(Record2.class.cast(r));
		}
	}

}

