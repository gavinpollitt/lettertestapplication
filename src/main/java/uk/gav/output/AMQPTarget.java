package uk.gav.output;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import uk.gav.date.DateProvider;
import uk.gav.records.Record;

public class AMQPTarget implements OutputTarget {

	@Autowired
	@Lazy
	private AMQPClientManager amqpClientManager;
	
	@Autowired
	private OutputConfiguration outputConfiguration;

	@Autowired
	private DateProvider dateProvider;

	@Override
	public void forward(final Record r, final List<String> data) throws Exception {

		LocalDateTime ldt = dateProvider.getDate();
		String dt = String.format("%02d%02d%02d", ldt.getYear(), ldt.getMonthValue(), ldt.getDayOfMonth());							


	}

}
