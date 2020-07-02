package uk.gav.letter;

import uk.gav.records.Record;
import uk.gav.records.Record1;

/**
 * LetterSource implementation for the Confirmation Letter.
 * @author regen
 *
 */
public class ConfirmationSource extends LetterSource<Record1> {

	private final static String TEMPLATE_LOC = "file:///home/regen/Documents/letters/Confirmation.txt";
	private final static String OUTPUT_DIR = "file:///home/regen/temp/output";

	private final static String OUTPUT_FN = "Confirmation";
	
	@Override
	protected String getTemplateURI() {
		return TEMPLATE_LOC;
	}
	
	@Override
	protected String getOutputDir() {
		return OUTPUT_DIR;
	}

	@Override
	protected String getFilename(Record1 letterRecord) {
		return OUTPUT_FN + "_" + letterRecord.getFields().get("companyName").getValue() + ".txt";
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

