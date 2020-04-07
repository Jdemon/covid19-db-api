package th.co.jayz.covid19.api.model;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import lombok.Data;

@Data
public class SummaryGraph implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private LinkedList<List<Object>> data;

}
