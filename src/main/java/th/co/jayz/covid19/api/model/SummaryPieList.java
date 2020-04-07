package th.co.jayz.covid19.api.model;

import java.util.List;

import lombok.Data;

@Data
public class SummaryPieList {
	
	private String latestDate;
	private List<SummaryPie> gender;
	private List<SummaryPie> province;
	private List<SummaryPie> nation;

}
