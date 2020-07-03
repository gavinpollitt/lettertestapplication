package uk.gav.date;

import org.springframework.data.repository.CrudRepository;

/**
 * Standard Spring Data repository to allow persistence/retrieval of the  information
 * @author regen
 *
 */
public interface DateRepository extends CrudRepository<TestDate, Long> {
	

}
