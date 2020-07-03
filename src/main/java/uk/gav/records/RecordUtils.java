package uk.gav.records;

import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import uk.gav.CompanyCache;

/**
 * Holder of utility classes for Record manipulation
 * @author regen
 *
 */

@Component
public class RecordUtils {
	
	private static CompanyCache companyCache;
	
	public static CompanyCache getCompanyCache() {
		return companyCache;
	}

	@Autowired
	public void setCompanyCache(final CompanyCache cc) {
		companyCache = cc;
	}
	/**
	 * A Holder for the field value and the associated specification
	 * @author regen
	 *
	 */
	public static class Field {
		private FieldSpec fieldSpec;
		private String value;
		
		public Field(final FieldSpec fieldSpec, final String value) {
			this.value = value;
			this.fieldSpec = fieldSpec;
		}
		
		public FieldSpec getFieldSpec() {
			return fieldSpec;
		}

		public String getValue() {
			return value;
		}

	}
	
	/**
	 * A holder for the field name and its associated regular expression validation
	 * @author regen
	 *
	 */
	public static class FieldSpec {
		private String name;
		private Pattern regex;
		
		public FieldSpec(final String name, final Pattern regex) {
			this.name = name;
			this.regex = regex;
		}
		
		public Pattern getRegex() {
			return regex;
		}

		public String getName() {
			return name;
		}	

	}
}
