package uk.gav.date;


import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

public class TestDateProvider extends DateProvider {

	private static boolean cached = false;

	@Autowired
	private DateRepository dateRepository;

	public LocalDateTime getDate() {
		LocalDateTime ldt = super.getDate();
		if (!cached) {
			Optional<TestDate> td = this.dateRepository.findById(1L);
			if (td.isPresent()) {
				cached = true;
				TestDate dt = td.get();
				
				String tm = dt.getTime();				
				if (tm == null || tm.length() == 0) {
					tm = String.format("%02d:%02d:%02d", ldt.getHour(), ldt.getMinute(), ldt.getSecond());							
				}
				String baseDate = dt.getDate() + "T" + tm + "Z";

				try {
					Long epoch = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(baseDate).getTime();
					Instant i = Instant.ofEpochMilli(epoch);

					String off = dt.getOffset() != null ? dt.getOffset() : "UTC";
					
					ldt = LocalDateTime.ofInstant(i,ZoneId.of(off));
				} catch (Exception e) {
					System.out.println("Invalid DB date set: " + baseDate);
				}
			}
		}
		return ldt;
	}
}
