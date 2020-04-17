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
@RequestMapping("/v1.0")
public class SummaryControllerV1 {
	
	@Autowired
	private ISummaryService summaryService;
	
	
	@GetMapping("/inf/sum")
	public ResponseEntity<List<SummaryListData>> summary() {
		return new ResponseEntity<>(summaryService.getSummary(),HttpStatus.OK);
	}
	
	@GetMapping("/inf/graph")
	public ResponseEntity<List<SummaryGraph>> graph() {
		return new ResponseEntity<>(summaryService.getGraph(),HttpStatus.OK);
	}
	
	@GetMapping("/inf/cases/sum")
	public  ResponseEntity<SummaryPieList> casessum() {
		return new ResponseEntity<>(summaryService.getCasesSum(),HttpStatus.OK);
	}

}
