package uk.gav;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
@ConfigurationProperties("company")
public class CompanyCache {
	
	@Autowired
	private RestTemplate restTemplate;

	private String host;
	private String port;
	private int	   portI;
	
	private final Map<String,Company> companies = new HashMap<>();
	
	public String getCompanyName(final String companyId) throws Exception {
		if (this.companies.get(companyId) != null) {
System.out.println("Acquiring company " + companyId + " from cache");
			return this.companies.get(companyId).name;
		}
		else {
System.out.println("Acquiring company " + companyId + " from API");
			Company c = this.locateCompany(companyId);
			
			if (c == null || c.getName().length() == 0) {
				throw new Exception("Unknown company:" + companyId);
			}
			else {
				this.companies.put(companyId, c);
				return c.getName();
			}
		}
	}


	private Company locateCompany(final String id) {
		Company c = restTemplate.getForEntity(this.getURI() + id, Company.class).getBody();
		
		return c;
	}
	
	public String getURI() {
		return "http://" + this.host + ":" + this.portI + "/companyManager/company/";
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
		this.portI = Integer.parseInt(port);
	}
	
	static class Company {
		private String id;
		private String name;
		
		public Company() {}
		
		public Company(final String id, final String name) {
			this.id = id;
			this.name = name;
		}
		
		public String getId() {
			return id;
		}
		public String getName() {
			return name;
		}

		public void setId(String id) {
			this.id = id;
		}

		public void setName(String name) {
			this.name = name;
		}
	}

}
