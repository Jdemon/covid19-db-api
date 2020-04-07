package th.co.jayz.covid19.http.entity;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SummaryModel implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("Confirmed")
	private int confirmed;
	@JsonProperty("Recovered")
	private int recovered;
	@JsonProperty("Hospitalized")
	private int hospitalized;
	@JsonProperty("Deaths")
	private int deaths;
	@JsonProperty("NewConfirmed")
	private int newConfirmed;
	@JsonProperty("NewRecovered")
	private int newRecovered;
	@JsonProperty("NewHospitalized")
	private int newHospitalized;
	@JsonProperty("NewDeaths")
	private int newDeaths;
	@JsonProperty("Date")
	private String date;
	@JsonProperty("UpdateDate")
	private String updateDate;
	@JsonProperty("Source")
	private String source;
	@JsonProperty("DevBy")
	private String devBy;
	@JsonProperty("SeverBy")
	private String severBy;
}
