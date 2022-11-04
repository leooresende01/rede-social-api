package tk.leooresende.redesocial.infra.dto.v1;

import java.util.List;

public class PaginacaoUsuarioDto {
	private List<UsuarioDto> content;
	private boolean last;

	public PaginacaoUsuarioDto(List<UsuarioDto> content, boolean last) {
		this.content = content;
		this.last = last;
	}

	public List<UsuarioDto> getContent() {
		return content;
	}

	public void setContent(List<UsuarioDto> content) {
		this.content = content;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

}
