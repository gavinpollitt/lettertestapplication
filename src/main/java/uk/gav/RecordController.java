package uk.gav;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import uk.gav.LetterService.Metric;
import uk.gav.date.DateProvider;

/**
 * 
 * @author regen
 *
 *         curl -i -X POST -d @cc.json -H "Content-Type: application/json"
 *         http://localhost:8080/queueManager/addCompany?timeout=xxx curl -i -X
 *         GET -H "Content-Type: application/json"
 *         http://localhost:8080/queueManager/getCompany?timeout=xxx curl -i -X
 *         GET -H "Content-Type: application/json"
 *         http://localhost:8080/queueManager/getCompanies/3 cc.json-->
 *         {"name":"GavWebCo2","description":"The final
 *         description","number":"07543334532"} rm ~/temp/data/q.mv.db
 */
@RestController
@RequestMapping("/recordManager")
public class RecordController {

	@Autowired
	private LetterService lService;
	
	@Autowired
	private DateProvider dateProvider;

	/**
	 */
	@RequestMapping(path = { "/scan", "/scan/{sleep}" }, method = RequestMethod.GET)
	public void start(@PathVariable(required = false) Integer sleep) throws Exception {
		if (lService.isRunning()) {
			throw new Exception("Scan is already running");
		}

		System.out.println("Started scanning at:" + dateProvider.getDate());
		try {
			lService.scanRecords(sleep == null ? 10 : sleep);
		} catch (Throwable e) {
			throw new Exception("Cannot start scanning process: " + e);
		}
	}

	/**
	 */
	@RequestMapping(path = "/stop", produces = "application/json", method = RequestMethod.GET)
	public Metric stop() throws Exception {
		System.out.println("Stopped scanning at:" + dateProvider.getDate());
		return lService.stopScan();
	}

	@RequestMapping(path = "/purge", produces = "application/json", method = RequestMethod.DELETE)
	public void purge() throws Exception {
		lService.purge();
	}
}
