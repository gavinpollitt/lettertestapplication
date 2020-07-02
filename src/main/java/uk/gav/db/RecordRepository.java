package uk.gav.db;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

/**
 * Standard Spring Data repository to allow persistence/retrieval of the  information
 * @author regen
 *
 */
public interface RecordRepository extends CrudRepository<Record, Long> {
	public List<Record> findByProcessedOrderByCompanyAscIdAsc(final String processed);
	
	@Modifying
	@Query("delete from Record r where r.processed='Y'")
	void deleteProcessed();
}
