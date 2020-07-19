package rest.api.docs

import org.apache.commons.lang.builder.EqualsBuilder
import org.apache.commons.lang.builder.HashCodeBuilder

class GerararquivoBase implements Serializable {

	Long gerararquivoBasesId
	Long baseId
	Base base
	Gerararquivo gerararquivo

	int hashCode() {
		def builder = new HashCodeBuilder()
		builder.append gerararquivoBasesId
		builder.append baseId
		builder.toHashCode()
	}

	boolean equals(other) {
		if (other == null) return false
		def builder = new EqualsBuilder()
		builder.append gerararquivoBasesId, other.gerararquivoBasesId
		builder.append baseId, other.baseId
		builder.isEquals()
	}

	static belongsTo = [Base, Gerararquivo]

	static mapping = {
		id composite: ["gerararquivoBasesId", "baseId"]
		version false
	}

	static constraints = {
		baseId nullable: true
	}
}
