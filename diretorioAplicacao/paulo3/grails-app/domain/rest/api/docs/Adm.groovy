package rest.api.docs

class Adm {

	String matricula
	String nome

	static mapping = {
		id generator: "assigned"
	}
}
