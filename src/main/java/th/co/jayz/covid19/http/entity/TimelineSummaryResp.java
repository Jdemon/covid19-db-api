package th.co.jayz.covid19.http.entity;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class TimelineSummaryResp implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@JsonProperty("UpdateDate")
	private String updateDate;
	@JsonProperty("Source")
	private String source;
	@JsonProperty("DevBy")
	private String devBy;
	@JsonProperty("SeverBy")
	private String severBy;
	@JsonProperty("Data")
	private List<SummaryModel> data;

}
