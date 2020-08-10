package rest.api.docs

class Base {

	String descricao
	String nome

	static mapping = {
		id generator: "assigned"
	}
}
