package rest.api.docs

class Professor {

	String matricula
	String nome

	static mapping = {
		id generator: "assigned"
	}
}
