package uk.gav.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * JPA entity representing the record details
 * @author regen
 *
 */
@Entity
public class Record {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
	
	@NotNull
	private String company;
	
	@NotNull
	@Size(max=2, message="Record type can be no more than two characters")
    private String type;
	
	@NotNull
	private String content;
	
	@NotNull
	@Pattern(regexp="Y|N")
	private String processed;
	
	public Record() {}
	
	public Record(final long id, final String company, final String type, final String content, final String processed) {
        this.id = id;
        this.company = company;
        this.type = type;
        this.content = content;
        this.processed = processed;
    }

    public Long getId() {
        return this.id;
    }

    public String getCompany() {
    	return this.company;
    }
    
    public String getContent() {
    	return this.content;
    }
    
	public String getType() {
		return this.type;
	}
	
    public String getProcessed() {
		return this.processed;
	}

	public void setProcessed(String processed) {
		this.processed = processed;
	}
	
	public String getData() {
		return this.type + "|" + this.getCompany() + "|" + this.getContent();
	}

	public String toString() {
		return this.id + "(" + this.processed + ")->" + this.getData();
	}
}
