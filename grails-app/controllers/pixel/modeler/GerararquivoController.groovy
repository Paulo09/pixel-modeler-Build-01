package pixel.modeler

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
	
	def selecionartabelas (Gerararquivo gerararquivo) {
	
		def lista2=[];def list = [];def cliente="";def tabelas=[];def cont=0;def selectCampos=""; def dataBase="";def bases="";def urlLista="";
		
		if(params.nometabela != null && params.nometabela.size() > 1){		
			
			list.add(params.nometabela.toString().replace("[","['").replace("]","']").replace(" ","").replace(",","','"))
			
			
			println "------------------- Novo App -----------------"+list			
			  
			
			if(params.nometabela.toString().contains(']')){
			    //
				//Criar file:
				//File file = new File("C:\\walter\\application.groovy");					
				def criarapp = new FileWriter(new File("C:\\walter\\application.groovy"));	
				criarapp.write("grails.plugin.reveng.includeTables = "+list.toString().replace("[","['").replace("]","']").replace("'']","").replace("[''","")+"\n");
				criarapp.write("grails.plugin.reveng.destDir = 'diretorioAplicacao/chico/grails-app/domain'");	
				criarapp.close();
				
				//
				//Criar Controller - dinamicamente
				def contControler=0
				params.nometabela.each{
					urlLista += '   "/'+params.nometabela[contControler]+'"'+"(resources:"+" '"+params.nometabela[contControler]+"')"+"\n"
					//File file2 = new File("C:\\walter\\controller${params.nometabela[contControler]}.groovy");					
					def criarConrtoller = new FileWriter(new File("C:\\walter\\controller${params.nometabela[contControler]}.groovy"));	
					criarConrtoller.write("package rest.api.docs\nimport com.wordnik.swagger.annotations.Api\nimport grails.rest.RestfulController\n@Api(value = '${params.nometabela[contControler]}', description = '${params.nometabela[contControler]} Management  API')\nclass ${params.nometabela[contControler]}Controller extends RestfulController {\nstatic responseFormats = ['json', 'xml']\nUserController(){\nsuper(User)\n}}");	
					criarConrtoller.close();
					contControler++
				}
				
					//File file3 = new File("C:\\walter\\UrlMappings.groovy");					
					def criarUrlMaps = new FileWriter(new File("C:\\walter\\UrlMappings.groovy"));	
					criarUrlMaps.write('package rest.api.docs\nclass UrlMappings {\n static mappings = {\n   "/$controller/$action?/$id?(.$format)?"{constraints{// apply constraints here}}\n   "/"(controller: "application", action:"index")\n   "500"(view: "/application/serverError")\n   "404"(view: "/application/notFound")\n'+urlLista+' }\n}');
					criarUrlMaps.close();
			}else{
			     def caminhoAbs = new File("").getAbsolutePath()
			     println "----------------- Caminho -----------------"+caminhoAbs
			
				 //
				//Criar file:
				//File file = new File("C:\\walter\\application.groovy");					
				def criarapp = new FileWriter(new File("${caminhoAbs}\\applicationPaulo.groovy"));	
				//def criarapp = new FileWriter(new File("C:\\walter\\application.groovy"));
				criarapp.write("grails.plugin.reveng.includeTables = "+list.toString().replace("[","['").replace("]","']").replace("'']","").replace("[''","")+"\n");
				criarapp.write("grails.plugin.reveng.destDir = 'diretorioAplicacao/chico/grails-app/domain'");	
				criarapp.close();
				
				//
				//Criar Controller - dinamicamente
					urlLista += '   "/'+list.toString().replace("[","").replace("]","")+'"'+"(resources:"+" '"+list.toString().replace("[","").replace("]","")+"')"+"\n"
					//File file2 = new File("C:\\walter\\controller${list.toString().replace("[","").replace("]","")}.groovy");					
					def criarConrtoller = new FileWriter(new File("C:\\walter\\controller${list.toString().replace("[","").replace("]","")}.groovy"));	
					criarConrtoller.write("package rest.api.docs\nimport com.wordnik.swagger.annotations.Api\nimport grails.rest.RestfulController\n@Api(value = '${list.toString().replace("[","").replace("]","")}', description = '${list.toString().replace("[","").replace("]","")} Management  API')\nclass ${list.toString().replace("[","").replace("]","")}Controller extends RestfulController {\nstatic responseFormats = ['json', 'xml']\nUserController(){\nsuper(User)\n}}");	
					criarConrtoller.close();
				//Criar UrlMaps
					//File file3 = new File("C:\\walter\\UrlMappings.groovy");					
					def criarUrlMaps = new FileWriter(new File("C:\\walter\\UrlMappings.groovy"));	
					criarUrlMaps.write('package rest.api.docs\nclass UrlMappings {\n static mappings = {\n   "/$controller/$action?/$id?(.$format)?"{constraints{// apply constraints here}}\n   "/"(controller: "application", action:"index")\n   "500"(view: "/application/serverError")\n   "404"(view: "/application/notFound")\n'+urlLista+' }\n}');
					criarUrlMaps.close();
			}

        }
		
		try {
			def conexao = Sql.newInstance("jdbc:postgresql://localhost:5432/$gerararquivo.base.nome","postgres", "root", "org.postgresql.Driver")
			def selectBaseOrigem = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'"
			def selectBases = "SELECT datname FROM pg_database WHERE datistemplate = 'f'"
			bases = conexao.rows(selectBases).datname
			tabelas = conexao.rows(selectBaseOrigem).table_name
			def gerarClasse=""
			dataBase = gerararquivo.base.nome			
		}catch(Exception e){}		

		render view:'selecionartabelas.gsp', model:[tabelas:tabelas,dataBase:dataBase,bases:bases]
	
	}
	
}