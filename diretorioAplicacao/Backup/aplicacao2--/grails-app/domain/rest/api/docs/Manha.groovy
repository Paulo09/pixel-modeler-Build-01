package rest.api.docs

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

class Manha implements Serializable {

	Long id
	Long version
	String nome

	int hashCode() {
		def builder = new HashCodeBuilder()
		builder.append id
		builder.append version
		builder.append nome
		builder.toHashCode()
	}

	boolean equals(other) {
		if (other == null) return false
		def builder = new EqualsBuilder()
		builder.append id, other.id
		builder.append version, other.version
		builder.append nome, other.nome
		builder.isEquals()
	}

	static mapping = {
		id composite: ["id", "version", "nome"]
		version false
	}
}
