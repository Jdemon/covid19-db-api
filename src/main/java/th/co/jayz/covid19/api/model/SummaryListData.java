package th.co.jayz.covid19.api.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class SummaryListData implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String title;
	private int total;
	private int newAdd;
	private String graph;
	private LinkedList<List<Object>> data;
	private String percentage;
	private String updateDate;
}
