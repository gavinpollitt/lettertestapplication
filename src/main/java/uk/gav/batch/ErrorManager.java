package uk.gav.batch;

import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import uk.gav.date.DateProvider;
import uk.gav.output.AMQPClientManager;
import uk.gav.records.ErrorRecord;

/**
 *
 */

@Component
public class ErrorManager {

	private static final String Q_NAME = "badrecords";
	
	@Autowired
	@Lazy
	private AMQPClientManager amqpManager;
	
	@Autowired
	private DateProvider dateProvider;

	private List<ErrorRecord> errors = new ArrayList<>();
	

	
	public void addRecord(final ErrorRecord r) {
		this.errors.add(r);
	}

	public void dumpErrors() {
		ErrorDump dump = new ErrorDump(dateProvider.getDate(), errors);
		
		System.out.println("Writing exceptions to Q: \n" + dump.toString());
		try {
			amqpManager.createQueue(Q_NAME);
			amqpManager.writeMessage(Q_NAME, dump.getJsonString());
		}
		catch (Exception e) {
			System.out.println("Cannot write to Q: " + e);
		}
		
		this.errors.clear();
	}
	
	
	protected static class ErrorDump {
		private final static DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;

		private String time;
		private List<ErrorRecord> records;
		private String jsonString;
		
		public ErrorDump(final LocalDateTime time, final List<ErrorRecord> records) {
			super();
			this.time = time.format(formatter);
			this.records = records;

			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			
			try {
				mapper.writeValue(sw, this);
				this.jsonString = sw.toString();
			}
			catch (IOException e) {
				throw new RuntimeException("Cannot write to Q: " + e);
			}

		}

		public String getTime() {
			return time;
		}

		public List<ErrorRecord> getRecords() {
			return records;
		}
		
		public String getJsonString() {
			return jsonString;
		}
		
		public String toString() {
			return this.time + "->" + this.records;
		}
		
	}
	
}
