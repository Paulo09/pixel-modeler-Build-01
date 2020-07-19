package rest.api.docs

class Base {

	String descricao
	String nome

	static hasMany = [gerararquivoBases: GerararquivoBase,
	                  gerararquivos: Gerararquivo]

	static mapping = {
		id generator: "assigned"
	}
}
