package uk.gav.letter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import uk.gav.output.OutputTarget;
import uk.gav.records.Record;
import uk.gav.records.Record3;

/**
 * LetterSource implementation for the Invoice Letter.
 * @author regen
 *
 */
public class InvoiceSource extends LetterSource<Record3> {

	private final static String TEMPLATE_LOC = "classpath:templates/Invoice.txt";
	
	@Autowired
	@Qualifier("invoiceTarget")
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
		if (r.getClass() == Record3.class) {
			this.addSource(Record3.class.cast(r));
		}
	}

}

