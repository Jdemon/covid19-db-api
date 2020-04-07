package th.co.jayz.covid19.http.entity;

import java.io.Serializable;
import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummaryCasesSum implements Serializable{
	
	private static final long serialVersionUID = 1L;
	@JsonProperty("Gender")
	private HashMap<String,Integer> Gender;
	@JsonProperty("Nation")
	private HashMap<String,Integer> Nation;
	@JsonProperty("Province")
	private HashMap<String,Integer> Province;
	@JsonProperty("LastData")
	private String LastData;
	@JsonProperty("Source")
	private String source;
	@JsonProperty("DevBy")
	private String devBy;
	@JsonProperty("SeverBy")
	private String severBy;
}
