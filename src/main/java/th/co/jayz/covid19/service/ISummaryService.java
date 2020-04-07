package th.co.jayz.covid19.service;

import java.util.List;

import th.co.jayz.covid19.api.model.SummaryGraph;
import th.co.jayz.covid19.api.model.SummaryListData;
import th.co.jayz.covid19.api.model.SummaryPieList;

public interface ISummaryService {

	List<SummaryListData> getSummary();

	List<SummaryGraph> getGraph();
	
	SummaryPieList getCasesSum();

}
