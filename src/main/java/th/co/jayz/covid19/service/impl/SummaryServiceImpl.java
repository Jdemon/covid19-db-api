package th.co.jayz.covid19.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import th.co.jayz.covid19.api.model.SummaryGraph;
import th.co.jayz.covid19.api.model.SummaryListData;
import th.co.jayz.covid19.api.model.SummaryPie;
import th.co.jayz.covid19.api.model.SummaryPieList;
import th.co.jayz.covid19.http.entity.SummaryCasesSum;
import th.co.jayz.covid19.http.entity.SummaryModel;
import th.co.jayz.covid19.http.entity.TimelineSummaryResp;
import th.co.jayz.covid19.service.ISummaryService;
import th.co.jayz.covid19.util.ExceptionUtil;
import th.co.jayz.covid19.util.RestUtil;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Service
public class SummaryServiceImpl implements ISummaryService{
	
	private static Logger log = LogManager.getLogger(SummaryServiceImpl.class);
	
	@Autowired
	private RestUtil restUtil;
	
	@Value("${data.api.summary.url}")
	private String urlSummary;
	
	@Value("${data.api.timeline.url}")
	private String urlTimeline;
	
	@Value("${data.api.cases.sum.url}")
	private String urlCasesSum;
	
	@Value("${data.api.summary.key}")
	private String[] keys;
	
	@Autowired
	private TimelineSummaryResp cacheTimeline;
	
	@Override
	public List<SummaryGraph> getGraph() {
		
		log.info("start getGraph()");
		
		List<SummaryGraph> list = new ArrayList<>();
		
		if(StringUtils.isBlank(this.cacheTimeline.getUpdateDate())){
        	this.cacheTimeline = restUtil.exchange(urlTimeline, HttpMethod.GET, TimelineSummaryResp.class).getBody();
        }
		List<SummaryModel> data = cacheTimeline.getData();
		
		for(String title : keys) {
			SummaryGraph graph = new SummaryGraph();
			graph.setName(title);
			LinkedList<List<Object>> dataValue = new LinkedList<>();
			for(SummaryModel summaryData : data) {
				List<Object> dataString = new ArrayList<>();
				dataString.add(summaryData.getDate());
				if("Confirmed".equals(title)) {
					dataString.add(summaryData.getConfirmed());
				}else if("Recovered".equals(title)) {
					dataString.add(summaryData.getRecovered());
				}else if("Hospitalized".equals(title)) {
					dataString.add(summaryData.getHospitalized());
				}else if("Deaths".equals(title)) {
					dataString.add(summaryData.getDeaths());
				}
				dataValue.add(dataString);
			}
			graph.setData(dataValue);
			list.add(graph);
		}
		
		log.info("end getGraph()");
		
		return list;
	}
	
	private String percentage(int max,int newVal) {
		
		double oldData = max - newVal;
		
		DecimalFormat df = new DecimalFormat("#.#");
		double answer = ((double)newVal*100.00)/(double)oldData;
		
		return df.format(answer);
		
	}
	
	@Override
	public List<SummaryListData> getSummary() {
		
		log.info("start getSummary()");
		
		List<SummaryListData> list = new ArrayList<>();
		
		ResponseEntity<HashMap<String, String>> sumModelRespEnt = null;
		try {
            sumModelRespEnt =  restUtil.exchange(urlSummary, HttpMethod.GET);
            this.cacheTimeline = restUtil.exchange(urlTimeline, HttpMethod.GET, TimelineSummaryResp.class).getBody();
        }catch(Exception e) {
			log.error(ExceptionUtil.getStackTrace(e));
		}
		List<SummaryModel> data = cacheTimeline.getData();
		if(sumModelRespEnt.getStatusCode() == HttpStatus.OK) {
			
			HashMap<String, String> summaryModel = sumModelRespEnt.getBody();
			SimpleDateFormat sdfUS = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			SimpleDateFormat sdfTH = new SimpleDateFormat("dd MMMM yyyy HH:mm");
			for(String title : keys) {
				SummaryListData dataList = new SummaryListData();
				dataList.setTitle(title);
				dataList.setTotal(Integer.parseInt(summaryModel.get(title)));
				dataList.setNewAdd(Integer.parseInt(summaryModel.get("New"+title)));
				if(dataList.getNewAdd() == 0) {
					dataList.setGraph("trending_flat");
				}else if(dataList.getNewAdd() > 0){
					dataList.setGraph("trending_up");
				}else {
					dataList.setGraph("trending_down");
				}
				
				LinkedList<List<Object>> dataValue = new LinkedList<>();
				for(SummaryModel summaryData : data) {
					List<Object> dataString = new ArrayList<>();
					dataString.add(summaryData.getDate());
					if("Confirmed".equals(title)) {
						dataString.add(summaryData.getConfirmed());
					}else if("Recovered".equals(title)) {
						dataString.add(summaryData.getRecovered());
					}else if("Hospitalized".equals(title)) {
						dataString.add(summaryData.getHospitalized());
					}else if("Deaths".equals(title)) {
						dataString.add(summaryData.getDeaths());
					}
					dataValue.add(dataString);
				}
				dataList.setData(dataValue);
				
				dataList.setPercentage(percentage(dataList.getTotal(), dataList.getNewAdd()));
				
				String updateDate = summaryModel.get("UpdateDate");
				Date date = new Date();
				try {
					date = sdfUS.parse(updateDate);
				} catch (ParseException e) {
					e.printStackTrace();
				}
				
				
				dataList.setUpdateDate(sdfTH.format(date));
				list.add(dataList);
			}
			
		}
		
		log.info("end getSummary()");
		
		return list;
		
	}
	
	private LinkedList<SummaryPie> getSummaryPieList(HashMap<String, Integer> datas){
		
		LinkedList<SummaryPie> linkedlist = new LinkedList<>();
		for(Entry<String, Integer> data  : datas.entrySet()) {
			SummaryPie summaryPieGender = new SummaryPie();
			summaryPieGender.setName(data.getKey());
			summaryPieGender.setY(data.getValue());
			linkedlist.add(summaryPieGender);
		}
		
		return linkedlist;
	}

	@Override
	public SummaryPieList getCasesSum() {
		
		log.info("start getCasesSum()");
		SummaryCasesSum summaryCasesSum = restUtil.exchange(urlCasesSum, HttpMethod.GET, SummaryCasesSum.class).getBody();
		SummaryPieList summaryPieList = new SummaryPieList();
		summaryPieList.setLatestDate(summaryCasesSum.getLastData());
		LinkedList<SummaryPie> listGender = getSummaryPieList(summaryCasesSum.getGender());
		LinkedList<SummaryPie> listProvince = getSummaryPieList(summaryCasesSum.getProvince());
		LinkedList<SummaryPie> listNation = getSummaryPieList(summaryCasesSum.getNation());
		summaryPieList.setGender(listGender);
		summaryPieList.setProvince(listProvince);
		summaryPieList.setNation(listNation);
		log.info("end getCasesSum()");
		return summaryPieList;
	}

}
