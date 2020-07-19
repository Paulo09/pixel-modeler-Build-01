package rest.api.docs

class Gerararquivo {

	String caminhoArquivo
	String nomeArquivo
	String tipoArquivo
	Base base

	static hasMany = [gerararquivoBases: GerararquivoBase]
	static belongsTo = [Base]

	static mapping = {
		id generator: "assigned"
	}
}
