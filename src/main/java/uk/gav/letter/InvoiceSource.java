package uk.gav.letter;

import uk.gav.records.Record;
import uk.gav.records.Record3;

/**
 * LetterSource implementation for the Invoice Letter.
 * @author regen
 *
 */
public class InvoiceSource extends LetterSource<Record3> {

	private final static String TEMPLATE_LOC = "classpath:templates/Invoice.txt";
	private final static String OUTPUT_DIR = "file:///home/regen/temp/output";

	private final static String OUTPUT_FN = "Invoice";
	
	@Override
	protected String getTemplateURI() {
		return TEMPLATE_LOC;
	}
	
	@Override
	protected String getOutputDir() {
		return OUTPUT_DIR;
	}

	@Override
	protected String getFilename(Record3 letterRecord) {
		return OUTPUT_FN + "_" + letterRecord.getFields().get("companyName").getValue() + ".txt";
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

