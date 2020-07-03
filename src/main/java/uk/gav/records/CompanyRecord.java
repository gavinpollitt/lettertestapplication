package uk.gav.records;

import java.util.Map;

import uk.gav.records.RecordUtils.Field;

public abstract class CompanyRecord extends Record {
	public CompanyRecord(final String line) throws Exception {
		super(line);
	}
	
	@Override
	protected void translateFields(Map<String, Field> fields) throws Exception {
		Field f = fields.get("companyName");
		String comp = f.getValue();
		
		String name = RecordUtils.getCompanyCache().getCompanyName(comp);
		
		fields.put("companyName",new Field(f.getFieldSpec(),name));
	}

}
