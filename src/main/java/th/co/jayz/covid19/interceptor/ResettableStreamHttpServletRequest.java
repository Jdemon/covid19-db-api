package th.co.jayz.covid19.interceptor;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.commons.io.IOUtils;

public class ResettableStreamHttpServletRequest extends HttpServletRequestWrapper {

	private byte[] rawData;
	private HttpServletRequest request;
	private ResettableServletInputStream servletStream;

	public ResettableStreamHttpServletRequest(HttpServletRequest request) throws IOException {
		super(request);
		this.request = request;
		init();
	}
	
	private void init() throws IOException {
		
		if (rawData == null) {
			this.rawData = IOUtils.toByteArray(this.request.getReader(),StandardCharsets.UTF_8);
		}
		closeStream();
		this.servletStream = new ResettableServletInputStream();
		this.servletStream.stream = new ByteArrayInputStream(this.rawData);
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		init();
		return this.servletStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		init();
		return new BufferedReader(new InputStreamReader(this.servletStream));
	}
	
	
	public void closeStream() {
		if (this.servletStream != null) {
			try {
				if (this.servletStream.stream != null) {
					this.servletStream.stream.close();
				}
			} catch (Exception e) {
			} finally {
				this.servletStream.stream = null;
			}
			try {
				this.servletStream.close();
			} catch (Exception e) {
			} finally {
				this.servletStream = null;
			}
		}
	}
	
	private class ResettableServletInputStream extends ServletInputStream {

		private InputStream stream;

		@Override
		public int read() throws IOException {
			return this.stream.read();
		}

		@Override
		public boolean isFinished() {
			return false;
		}

		@Override
		public boolean isReady() {
			return false;
		}

		@Override
		public void setReadListener(ReadListener listener) {
		}
		
	}

}
