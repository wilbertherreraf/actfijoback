package gob.gamo.activosf.app.dto;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import jakarta.persistence.Query;

public class QueryParams implements Serializable{

	
	private StringBuilder query;
	private Map<String,Object> parameter;
	private String orderBy;
	
	public QueryParams(StringBuilder query, Map<String, Object> parameter, String orderBy) {
		super();
		this.query = query;
		this.parameter = parameter;
		this.orderBy = orderBy;
	}

	public static String prepareStringForLikeSufix(String str) {
		String result = str.replace('%', '\u0000').replace('_', '\u0000');
		return "%" + result + "%";
	} 
	
	public static String prepareStringForLikePrefixSufix(String str) {
		String result = str.replace('%', '\u0000').replace('_', '\u0000');
		return "%" + result + "%";
	}
	
	public static String prepareStringArrayForIn(String[] str) {
		if (str != null) {
			String[] result = new String[str.length];
			for (int i = 0; i < str.length; i++) {
				result[i] = '\'' + str[i] + '\'';
			}
			return " ( "+StringUtils.join(result, ",")+" ) ";
		} else {
			return null;
		}
	}
	
	public static String prepareNumberArrayForIn(Number[] ints) {
		if (ints != null) {
			return " ( "+StringUtils.join(ints, ",")+" ) ";
		} else {
			return null;
		}
	}
	
	public String getQueryForCount(){
		StringBuilder sb = new StringBuilder(" SELECT count (*) FROM ( ");
		sb.append(query);
		sb.append(" ) as data");
		return sb.toString();
		
	}
	
	public String getQueryForPagination(Integer limit, Integer offset){
		StringBuilder sb = new StringBuilder(query);
		
		if (orderBy != null) {
			sb.append(" ").append(orderBy).append(" ");
		}
		if(limit != null) {
    		sb.append(" LIMIT ").append(limit);
    	}
    	if(offset != null) {
    		sb.append(" OFFSET ").append(offset);
    	}
    	return sb.toString();
	}
	
	public void populateParams(Query query) {
		Set<String> params = parameter.keySet();
		for (String paramName : params) {
			query.setParameter(paramName, parameter.get(paramName));
		}
	}
	
	
	public StringBuilder getQuery() {
		return query;
	}

	public void setQuery(StringBuilder query) {
		this.query = query;
	}

	public Map<String, Object> getParameter() {
		return parameter;
	}

	public void setParameter(Map<String, Object> parameter) {
		this.parameter = parameter;
	}
	
	
	
	

}
