package pixel.modeler
import grails.util.Holders
import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

import javax.servlet.http.HttpServletResponse
import grails.converters.*

import java.sql.*;
import groovy.sql.Sql;

@Transactional(readOnly = true)
class GerararquivoController {


    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Gerararquivo.list(params), model:[gerararquivoCount: Gerararquivo.count()]
    }

    def show(Gerararquivo gerararquivo) {
        respond gerararquivo
    }

    def create() {				  
        respond new Gerararquivo(params)
    }

    @Transactional
    def save(Gerararquivo gerararquivo) {
        if (gerararquivo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (gerararquivo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond gerararquivo.errors, view:'create'
            return
        }

        gerararquivo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'gerararquivo.label', default: 'Gerararquivo'), gerararquivo.id])
                redirect gerararquivo
            }
            '*' { respond gerararquivo, [status: CREATED] }
        }
    }

    def edit(Gerararquivo gerararquivo) {
        respond gerararquivo
    }

    @Transactional
    def update(Gerararquivo gerararquivo) {
        if (gerararquivo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (gerararquivo.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond gerararquivo.errors, view:'edit'
            return
        }

        gerararquivo.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'gerararquivo.label', default: 'Gerararquivo'), gerararquivo.id])
                redirect gerararquivo
            }
            '*'{ respond gerararquivo, [status: OK] }
        }
    }

    @Transactional
    def delete(Gerararquivo gerararquivo) {

        if (gerararquivo == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        gerararquivo.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'gerararquivo.label', default: 'Gerararquivo'), gerararquivo.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'gerararquivo.label', default: 'Gerararquivo'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
	
	def camelCase(String textoCamel){
	    textoCamel.toLowerCase().substring(0,1).toUpperCase()+textoCamel.substring(1,textoCamel.size()).toLowerCase()
	}
	def createController(){
	}
	
	def selecionartabelas (Gerararquivo gerararquivo) {
	
		def lista2=[];def list = [];def cliente="";def tabelas=[];def cont=0;def selectCampos=""; def dataBase="";def bases="";def urlLista="";def caminhoAbs = new File("").getAbsolutePath();
		
		if(params.nometabela != null && params.nometabela.size() > 1){		
			
			list.add(params.nometabela.toString().replace("[","['").replace("]","']").replace(" ","").replace(",","','"))
		
			println "cmd /c gradlew -PnomeApp=${grailsApplication.config.getProperty('pixelApp.nomeApp')} gerarApp".execute().text
			
			
			//println "------------------ Camel Case -----------------"+camelCase(${params.nometabela[0]}.toString())
			  
			
			if(params.nometabela.toString().contains(']')){
			     
			    //@PauloCastro
				//Criar file:					
				def criarapp = new FileWriter(new File("${caminhoAbs}\\diretorioAplicacao\\${grailsApplication.config.getProperty('pixelApp.nomeApp')}\\grails-app\\conf\\application.groovy"));	
				criarapp.write("grails.plugin.reveng.includeTables = "+list.toString().replace("[","['").replace("]","']").replace("'']","").replace("[''","")+"\n");
				criarapp.write("grails.plugin.reveng.destDir = 'diretorioAplicacao/${grailsApplication.config.getProperty('pixelApp.nomeApp')}/grails-app/domain'");	
				criarapp.close();

				def contControler=0
				params.nometabela.each{
					urlLista += '   "/'+params.nometabela[contControler]+'"'+"(resources:"+" '"+params.nometabela[contControler]+"')"+"\n"				
					def criarConrtoller = new FileWriter(new File("${caminhoAbs}\\diretorioAplicacao\\${grailsApplication.config.getProperty('pixelApp.nomeApp')}\\grails-app\\controllers\\rest\\api\\docs\\"+camelCase(params.nometabela[contControler])+"Controller.groovy"));	
					criarConrtoller.write("package ${grailsApplication.config.getProperty("pixelApp.pacote")}\nimport com.wordnik.swagger.annotations.Api\nimport grails.rest.RestfulController\n@Api(value = '${params.nometabela[contControler]}', description = '${camelCase(params.nometabela[contControler])} Pixel  API')\nclass "+camelCase(params.nometabela[contControler])+"Controller extends RestfulController {\nstatic responseFormats = ['json', 'xml']\n"+camelCase(params.nometabela[contControler])+"Controller(){\nsuper("+camelCase(params.nometabela[contControler])+")\n}}");	
					criarConrtoller.close();					
					contControler++
				}					
					def criarUrlMaps = new FileWriter(new File("${caminhoAbs}\\diretorioAplicacao\\${grailsApplication.config.getProperty('pixelApp.nomeApp')}\\grails-app\\controllers\\rest\\api\\docs\\UrlMappings.groovy"));	
					criarUrlMaps.write('package '+grailsApplication.config.getProperty("pixelApp.pacote")+'\nclass UrlMappings {\n static mappings = {\n   "/$controller/$action?/$id?(.$format)?"{constraints{}}\n   "/"(controller: "application", action:"index")\n   "500"(view: "/application/serverError")\n   "404"(view: "/application/notFound")\n'+urlLista+' }\n}');
					criarUrlMaps.close();
					//println "cmd /c gradlew assemble -Dgrails.env=development".execute().text
					
					//Gerar Aplica��o - Passar par�metro - nome Aplica��o
					//gradlew -PnomeApp="nomeApp" gerarApp
					  
					  
					  
			}else{			
				//@PauloCastro
				//Criar file:				
				def criarapp = new FileWriter(new File("${caminhoAbs}\\diretorioAplicacao\\${grailsApplication.config.getProperty('pixelApp.nomeApp')}\\grails-app\\conf\\application.groovy"));
				criarapp.write("grails.plugin.reveng.includeTables = "+list.toString().replace("[","['").replace("]","']").replace("'']","").replace("[''","")+"\n");
				criarapp.write("grails.plugin.reveng.destDir = 'diretorioAplicacao/${grailsApplication.config.getProperty('pixelApp.nomeApp')}/grails-app/domain'");	
				criarapp.close();
				
				//@PauloCastro
				//Criar Controller - dinamicamente
				urlLista += '   "/'+list.toString().replace("[","").replace("]","")+'"'+"(resources:"+" '"+list.toString().replace("[","").replace("]","")+"')"+"\n"				
				def criarConrtoller = new FileWriter(new File("${caminhoAbs}\\diretorioAplicacao\\${grailsApplication.config.getProperty('pixelApp.nomeApp')}\\grails-app\\controllers\\rest\\api\\docs\\${camelCase(list.toString().replace("[","").replace("]",""))}Controller.groovy"));	
				criarConrtoller.write("package ${grailsApplication.config.getProperty('pixelApp.pacote')}\nimport com.wordnik.swagger.annotations.Api\nimport grails.rest.RestfulController\n@Api(value = '${list.toString().replace("[","").replace("]","")}', description = '${list.toString().replace("[","").replace("]","")} Management  API')\nclass ${list.toString().replace("[","").replace("]","")}Controller extends RestfulController {\nstatic responseFormats = ['json', 'xml']\nUserController(){\nsuper(User)\n}}");	
				criarConrtoller.close();
				//Criar UrlMaps					
				def criarUrlMaps = new FileWriter(new File("${caminhoAbs}\\diretorioAplicacao\\${grailsApplication.config.getProperty('pixelApp.nomeApp')}\\grails-app\\controllers\\rest\\api\\docs\\UrlMappings.groovy"));	
				criarUrlMaps.write('package '+grailsApplication.config.getProperty("pixelApp.pacote")+'\nclass UrlMappings {\n static mappings = {\n   "/$controller/$action?/$id?(.$format)?"{constraints{}}\n   "/"(controller: "application", action:"index")\n   "500"(view: "/application/serverError")\n   "404"(view: "/application/notFound")\n'+urlLista+' }\n}');
				criarUrlMaps.close();
				//println "cmd /c gradlew assemble -Dgrails.env=development".execute().text	
			}

        }
		
		if(request.method == "GET"){
		    bases = ""
			def listaDatabase = Sql.newInstance("jdbc:postgresql://localhost:5432/${grailsApplication.config.getProperty('pixelApp.nomeDB')}","postgres", "root", "org.postgresql.Driver")
			def selectBases = "SELECT datname FROM pg_database WHERE datistemplate = 'f'"
			bases = listaDatabase.rows(selectBases).datname			
		}
		
		try {
		    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
            // Issue: https://github.com/Paulo09/pixel-modeler-Build-01/issues/1			
		    //Trecho c�digo respons�vel por pegar informa��es: Data Base, Tabela, campos, constraints, relacionamento e etc...
			//obs: Essa implementa��o ser� din�mica, sendo cadastrada pelo usu�rio. 
			//Respons�vel codificado em 10/07/2020 por @PauloCastro
			///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			def conexao = Sql.newInstance("jdbc:postgresql://localhost:5432/$params.tbBases","postgres", "root", "org.postgresql.Driver")
			def selectBaseOrigem = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'"
			def selectBases = "SELECT datname FROM pg_database WHERE datistemplate = 'f'"
			bases = conexao.rows(selectBases).datname
			tabelas = conexao.rows(selectBaseOrigem).table_name
				
		}catch(Exception e){}	

		println "------------------------------------------ Nome Base -------------------------------------------"+dataBase	

		render view:'selecionartabelas.gsp', model:[tabelas:tabelas,bases:bases]
	
	}
	
}