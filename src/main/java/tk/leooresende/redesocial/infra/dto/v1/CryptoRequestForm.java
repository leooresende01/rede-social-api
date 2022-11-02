package tk.leooresende.redesocial.infra.dto.v1;

public class CryptoRequestForm {
	private String payload;

	public CryptoRequestForm(String payload) {
		this.payload = payload;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}
}
