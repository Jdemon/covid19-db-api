package th.co.jayz.covid19.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/v2.0")
public class SummaryControllerV2 {
	
	@Autowired
	private ISummaryService summaryService;
	
	
	@GetMapping("/inf/sum")
	public ResponseEntity<List<SummaryListData>> summary() {
		return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
	}
	
	@GetMapping("/inf/graph")
	public ResponseEntity<List<SummaryGraph>> graph() {
		summaryService.getSummary();
		summaryService.getGraph();
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
	
	@GetMapping("/inf/cases/sum")
	public  ResponseEntity<SummaryPieList> casessum() {
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

}
