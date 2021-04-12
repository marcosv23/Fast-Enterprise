package api.external.response;

public class EnterpriseResponse {

	private String data_situacao;
   // private ActivityInput atividade_principal;
	private String  nome;
	private String tipo;
	private String uf;
	private String telefone;
	private String situacao;
    private String email;
    private String bairro;
    private String logradouro;
    private String numero;
    private String cep;
    private String municipio;
    private String porte;
    private String fantasia;
    private String natureza_juridica;
    private String cnpj;
    private String ultima_avaliacao;
    private String status;
    private String efr;
    private String motivo_situacao;
    private String situacao_especial;
    private String data_situacao_especial;
    private String capital_social;

    
    
    
	public EnterpriseResponse() {
		super();
	}



	public EnterpriseResponse(String data_situacao, String nome, String tipo, String uf, String telefone,
			String situacao, String email, String bairro, String logradouro, String numero, String cep,
			String municipio, String porte, String fantasia, String natureza_juridica, String cnpj,
			String ultima_avaliacao, String status, String efr, String motivo_situacao, String situacao_especial,
			String data_situacao_especial, String capital_social) {
		super();
		this.data_situacao = data_situacao;
		this.nome = nome;
		this.tipo = tipo;
		this.uf = uf;
		this.telefone = telefone;
		this.situacao = situacao;
		this.email = email;
		this.bairro = bairro;
		this.logradouro = logradouro;
		this.numero = numero;
		this.cep = cep;
		this.municipio = municipio;
		this.porte = porte;
		this.fantasia = fantasia;
		this.natureza_juridica = natureza_juridica;
		this.cnpj = cnpj;
		this.ultima_avaliacao = ultima_avaliacao;
		this.status = status;
		this.efr = efr;
		this.motivo_situacao = motivo_situacao;
		this.situacao_especial = situacao_especial;
		this.data_situacao_especial = data_situacao_especial;
		this.capital_social = capital_social;
	
	}


	/*public ActivityInput getAtividade_principal() {
		return atividade_principal;
	}


	public void setAtividade_principal(ActivityInput atividade_principal) {
		this.atividade_principal = atividade_principal;
	}*/



	public String getData_situacao() {
		return data_situacao;
	}



	public void setData_situacao(String data_situacao) {
		this.data_situacao = data_situacao;
	}



	public String getNome() {
		return nome;
	}



	public void setNome(String nome) {
		this.nome = nome;
	}



	public String getTipo() {
		return tipo;
	}



	public void setTipo(String tipo) {
		this.tipo = tipo;
	}



	public String getUf() {
		return uf;
	}



	public void setUf(String uf) {
		this.uf = uf;
	}



	public String getTelefone() {
		return telefone;
	}



	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}



	public String getSituacao() {
		return situacao;
	}



	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}



	public String getEmail() {
		return email;
	}



	public void setEmail(String email) {
		this.email = email;
	}



	public String getBairro() {
		return bairro;
	}



	public void setBairro(String bairro) {
		this.bairro = bairro;
	}



	public String getLogradouro() {
		return logradouro;
	}



	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}



	public String getNumero() {
		return numero;
	}



	public void setNumero(String numero) {
		this.numero = numero;
	}



	public String getCep() {
		return cep;
	}



	public void setCep(String cep) {
		this.cep = cep;
	}



	public String getMunicipio() {
		return municipio;
	}



	public void setMunicipio(String municipio) {
		this.municipio = municipio;
	}



	public String getPorte() {
		return porte;
	}



	public void setPorte(String porte) {
		this.porte = porte;
	}



	public String getFantasia() {
		return fantasia;
	}



	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}



	public String getNatureza_juridica() {
		return natureza_juridica;
	}



	public void setNatureza_juridica(String natureza_juridica) {
		this.natureza_juridica = natureza_juridica;
	}



	public String getCnpj() {
		return cnpj;
	}



	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}



	public String getUltima_avaliacao() {
		return ultima_avaliacao;
	}



	public void setUltima_avaliacao(String ultima_avaliacao) {
		this.ultima_avaliacao = ultima_avaliacao;
	}



	public String getStatus() {
		return status;
	}



	public void setStatus(String status) {
		this.status = status;
	}



	public String getEfr() {
		return efr;
	}



	public void setEfr(String efr) {
		this.efr = efr;
	}



	public String getMotivo_situacao() {
		return motivo_situacao;
	}



	public void setMotivo_situacao(String motivo_situacao) {
		this.motivo_situacao = motivo_situacao;
	}



	public String getSituacao_especial() {
		return situacao_especial;
	}



	public void setSituacao_especial(String situacao_especial) {
		this.situacao_especial = situacao_especial;
	}



	public String getData_situacao_especial() {
		return data_situacao_especial;
	}



	public void setData_situacao_especial(String data_situacao_especial) {
		this.data_situacao_especial = data_situacao_especial;
	}



	public String getCapital_social() {
		return capital_social;
	}



	public void setCapital_social(String capital_social) {
		this.capital_social = capital_social;
	}

	
	
	
	
	public class ActivityInput {

		String text;
		
		String code;

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public String getCode() {
			return code;
		}

		public void setCode(String code) {
			this.code = code;
		}
	}


	
}
