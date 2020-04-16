package th.co.jayz.covid19.interceptor;

import java.io.IOException;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.filter.OncePerRequestFilter;

import th.co.jayz.covid19.util.ExceptionUtil;
import th.co.jayz.covid19.util.JsonUtil;


@Configuration
public class TxnLogFilter extends OncePerRequestFilter {

	
	private static Logger txnLog = LogManager.getLogger("txn_log");
	private static String log_type = "log_type=txn_log";


	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		String contextPath = request.getContextPath() + "/v";

		if(request.getRequestURI().contains(contextPath)) {
			ResettableStreamHttpServletRequest wrapperReq = new ResettableStreamHttpServletRequest(request);
	
			Long processTime = System.currentTimeMillis();
			String reqMsg = getReqBody(wrapperReq);
			String clientIP = getClientIp(wrapperReq);
			
			filterChain.doFilter(wrapperReq, response);
			
			processTime = System.currentTimeMillis() - processTime;
		
			
			if(StringUtils.isNotBlank(reqMsg)) {
				txnLog.info(log_type+"|uri=" + request.getRequestURI()+"|statusCode="+response.getStatus()+"|clientIP=" + clientIP +"|method=" + request.getMethod()+"|reqBody="+reqMsg+"|processTime="+processTime);
			}else {
				txnLog.info(log_type+"|uri=" + request.getRequestURI()+"|statusCode="+response.getStatus()+"|clientIP=" + clientIP +"|method=" + request.getMethod() +"|processTime="+processTime);
			}
		}else {
			filterChain.doFilter(request, response);
		}

	}

	private String getReqBody(HttpServletRequest request) {
		if (!RequestMethod.GET.name().equalsIgnoreCase(request.getMethod())) {
			String reqMsg = "";
			try {
				reqMsg = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
				reqMsg = StringUtils.replace(reqMsg,"\n", "");
				reqMsg = StringUtils.replace(reqMsg,"\r", "");
				reqMsg = StringUtils.trim(reqMsg);
			} catch (Exception e) {
				txnLog.warn(log_type+"|exception="+ExceptionUtil.getStackTrace(e), e);
			}

			if (StringUtils.isBlank(reqMsg)) {
				reqMsg = getParam(request);
			}

			return reqMsg;
		} else {
			return getParam(request);
		}
	}
	

	private String getClientIp(HttpServletRequest request) {

		String remoteAddr = "";

		if (request != null) {
			remoteAddr = request.getHeader("X-FORWARDED-FOR");
			if (StringUtils.isBlank(remoteAddr)) {
				remoteAddr = request.getRemoteAddr();
			}
		}

		return remoteAddr;
	}

	public String getParam(HttpServletRequest request) {
		if (request.getParameterMap() != null && !request.getParameterMap().isEmpty()) {
			Map<String, String[]> paramMap = request.getParameterMap();
			return JsonUtil.getStrJsonByObj(paramMap);
		} else {
			return "";
		}
	}

}
