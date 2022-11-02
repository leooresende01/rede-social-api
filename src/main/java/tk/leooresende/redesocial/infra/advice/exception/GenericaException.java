package tk.leooresende.redesocial.infra.advice.exception;

import org.springframework.http.HttpStatus;

public class GenericaException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private HttpStatus statusHTTP;

	public GenericaException(String msg, HttpStatus statusHTTP) {
		super(msg);
		this.statusHTTP = statusHTTP;
	}

	public HttpStatus getStatusHTTP() {
		return statusHTTP;
	}

	public void setStatusHTTP(HttpStatus statusHTTP) {
		this.statusHTTP = statusHTTP;
	}
}
