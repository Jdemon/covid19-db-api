package th.co.jayz.covid19.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class LogUtil {
	
	private static Logger log = LogManager.getLogger("application_log");
	private static String log_type = "log_type=application_log";
	
	private static String message(String msg) {
		return log_type+"|log_data="+msg;
	}
	
	private static String message(String msg,Exception e) {
		return log_type+"|log_data="+msg+"|exception="+ExceptionUtil.getStackTrace(e);
	}
	
	public static void debug(String msg,Exception e) {
		log.debug(message(msg,e),e);
	}
	public static void debug(String msg) {
		log.debug(message(msg));
	}
	
	public static void info(String msg,Exception e) {
		log.info(message(msg,e),e);
	}
	public static void info(String msg) {
		log.info(message(msg));
	}
	
	public static void warn(String msg,Exception e) {
		log.warn(message(msg,e),e);
	}
	public static void warn(String msg) {
		log.warn(message(msg));
	}
	
	public static void error(String msg,Exception e) {
		log.error(message(msg,e),e);
	}
	public static void error(String msg) {
		log.error(message(msg));
	}
	
	public static void fatal(String msg,Exception e) {
		log.fatal(message(msg,e),e);
	}
	public static void fatal(String msg) {
		log.fatal(message(msg));
	}

}
