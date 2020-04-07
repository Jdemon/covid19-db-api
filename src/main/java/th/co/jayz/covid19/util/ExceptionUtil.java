package th.co.jayz.covid19.util;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.springframework.stereotype.Component;


@Component
public class ExceptionUtil {


	public static String getStackTrace(Throwable e) {
		String errorMsg = null;
		try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw);) {
			e.printStackTrace(pw);
			errorMsg = sw.toString();
		} catch (Throwable ignoreex) {
			errorMsg = e.getMessage();
		}
		return errorMsg;
	}
}
