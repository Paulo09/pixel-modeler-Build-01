package teste

import grails.transaction.Transactional

@Transactional
class ModelerService {

    def serviceMethod() {

    }
	
	def camelCase(String textoCamel){
		textoCamel.toLowerCase().substring(0,1).toUpperCase()+textoCamel.substring(1,textoCamel.size()).toLowerCase()
	}
	
	def mensagem (){
	    println "------------------------ Service  Mensagem -----------------------------"
	}

}
