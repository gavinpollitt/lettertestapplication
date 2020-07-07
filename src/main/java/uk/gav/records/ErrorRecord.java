package uk.gav.records;

public class ErrorRecord {

		private final Integer id;
		private final String  data;
		
		public ErrorRecord(final Integer id, final String data) {
			this.id = id;
			this.data = data;
		}

		public Integer getId() {
			return id;
		}

		public String getData() {
			return data;
		}
		
		public String toString() {
			return this.id + ":" + this.data;
		}
}
