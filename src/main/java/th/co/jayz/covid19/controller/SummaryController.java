package th.co.jayz.covid19.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import th.co.jayz.covid19.api.model.SummaryGraph;
import th.co.jayz.covid19.api.model.SummaryListData;
import th.co.jayz.covid19.api.model.SummaryPieList;
import th.co.jayz.covid19.service.ISummaryService;;

@CrossOrigin
@RestController
@RequestMapping("/v1.0")
public class SummaryController {
	
	@Autowired
	private ISummaryService summaryService;
	
	
	@GetMapping("/inf/sum")
	public List<SummaryListData> summary() {
		return summaryService.getSummary();
	}
	
	@GetMapping("/inf/graph")
	public List<SummaryGraph> graph() {
		return summaryService.getGraph();
	}
	
	@GetMapping("/inf/cases/sum")
	public SummaryPieList casessum() {
		return summaryService.getCasesSum();
	}

}
