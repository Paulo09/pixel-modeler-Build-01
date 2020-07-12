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
	    
		 try { 
		          def sql = Sql.newInstance("jdbc:postgresql://localhost:5432/robo","postgres", "root", "org.postgresql.Driver")
                  def selectBase = "select count(*) as cont from pg_database where datistemplate = 'f'".toString()
			      sql.rows(selectBase)
				  def selectBaseOrigem = "select count(*) as cont from base".toString()
				  sql.rows(selectBaseOrigem)
				  
				  println "------------------- Create -------------:"+sql.rows(selectBaseOrigem).toString().replace('[{cont=','').replace('}]','')
				  
				  if(sql.rows(selectBaseOrigem).toString().replace('[{cont=','').replace('}]','') != sql.rows(selectBase).toString().replace('[{cont=','').replace('}]','')){	
					println "-------------- Insert ------------------"
				    //def criar = "INSERT INTO base (select NEXTVAL('id'),0,datcollate,datname from pg_database where datistemplate = 'f')".toString()
			        //sql.rows(criar)
				  }else{
				   println "------1-------"+sql.rows(selectBaseOrigem).toString().replace('[{cont=','').replace('}]','')
				   println "------2-------"+sql.rows(selectBase).toString().replace('[{cont=','').replace('}]','')
				 }	

			}catch (Exception e){}
		
		
				  
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
	
		def cliente="";def tabelas=[];def cont=0;def selectCampos=""; def dataBase="";
		
		//if()
		
		//println "--------------------------------- Morre -----------------------"+params.nometabela
		
		if(params.nometabela != null && params.nometabela.size() > 1){
		
		
			def list = []
			list.add(params.nometabela.toString().replace("[","['").replace("]","']").replace(" ","").replace(",","','"))	
            if(list.size() == 1){			
				println "---------- Singular ------"+list.toString().replace("[","['").replace("]","']").replace("'']","").replace("[''","")
			}else{
			    println "------------------------- Plural ------------------"+list.size() //params.nometabela.toString().replace("[","['").replace("]","']").replace(" ","").replace(",","','")
			}	

		
		     //def sss = params.nometabela.size()
			 
			 //println "--------------- Arbusto -----------"+sss
		   
			 //cliente = params.nometabela
			 //println "------------------------- Salvo ------------------"+params.nometabela.toString().replace("[","['").replace("]","']").replace(" ","").replace(",","','")
			
		
        }
		
		try {
		
			def conexao = Sql.newInstance("jdbc:postgresql://localhost:5432/$gerararquivo.base.nome","postgres", "root", "org.postgresql.Driver")
			def selectBaseOrigem = "SELECT table_name FROM information_schema.tables WHERE table_schema = 'public'"
			tabelas = conexao.rows(selectBaseOrigem).table_name
			def gerarClasse=""
			dataBase = gerararquivo.base.nome	


			println "-------------------------------- Valor Size *** ----------------------------"+params.gerararquivo
			
			
			if(params.nometabela.size() > 1){
				
				params.nometabela.each{
				    def dd=0;def campos=""; 
					
					selectCampos = "SELECT data_type,column_name FROM information_schema.columns WHERE table_name in ('${params.nometabela[cont]}')".toString()
					gerarClasse=conexao.rows(selectCampos)
					
					    //Seleciona - maior que uma opção
						//Verifica as tabelas selecionadas e faz a transpilação da mesma.Mais de uma opção selecionada.
						//Obs: Para este caso, mais de uma tabela selecionada.
						//Autor: @PauloCastro 
						if(gerarClasse){
					
						   gerarClasse.each{
						  
						        gerarClasse
								
								def objClasse = "class "+"${params.nometabela[cont]}"+" {\n"+
									gerarClasse[dd].toString().replace('column_name=','').replace('{data_type=','').replace('character','String').replace('character varying','String').replace('varying,','').replace('integer','Integer').replace(', ',' ').replace('}','').replace('bigint','Integer') //.replace('version','').replace('{data_type=','').replace('integer','Integer').replace('character','String').replace('character varying','String').replace('{data_type=character','String').replace(' column_name=','').replace('}','').replace(',',' ')+
								"\n}";
								
								File file = new File("grails-app\\classes\\${params.nometabela[cont]}.groovy");					
								if(!file.exists())
								{						
								def criarapp = new FileWriter(new File("grails-app\\aplicacao\\${params.nometabela[cont]}.groovy"));	
								    criarapp.write(objClasse); 
								    criarapp.close();
								}
								dd++
							}
							
						}
						//Seleciona - uma única opção 
						//Verifica a tabela selecionada e faz a transpilação da mesma.Somente uma opção, somente uma tabela.
						//Obs: Para este caso, única tabela.
						//Autor: @PauloCastro
						if(!gerarClasse){
						    
							    File diretorio = new File("grails-app\\domain\\${gerararquivo.base.nome.replace('_','')}");
								diretorio.mkdir();
								
								println "-------------------------------- Valor 3 ----------------------------"+$gerararquivo.base.nome
			
							    selectCampos = "SELECT data_type,column_name FROM information_schema.columns WHERE table_name in ('${params.nometabela}')".toString()
								gerarClasse=conexao.rows(selectCampos)
								
								
								
								def con1=0;def res=""
								gerarClasse.each{
								   res += ' '+gerarClasse[con1].toString().replace('column_name=','').replace('{data_type=','').replace('character','String').replace('character varying','String').replace('varying,','').replace('integer','Integer').replace(', ',' ').replace('}','').replace('bigint','Integer').replace('_','').replace('timestamp without time zone','Date').replace('text','String')+"\n"
								   con1++
								}
								
								def objClasse = "package ${gerararquivo.base.nome.replace('_','')} \n"+"import grails.rest.* \n"+"@Resource(uri='/api/${params.nometabela.toLowerCase().replace('_','')}') \n"+"""class ${params.nometabela.substring(0,1).toUpperCase()}${params.nometabela.substring(1,params.nometabela.size()).toLowerCase().replace('_','')}{\n${res}}
												"""
								
								File file = new File("${diretorio}\\${params.nometabela.substring(0,1).toUpperCase()}${params.nometabela.substring(1,params.nometabela.size()).toLowerCase().replace('_','')}.groovy");					
								if(!file.exists())
								{						
								def criarapp = new FileWriter(new File("${diretorio}\\${params.nometabela.substring(0,1).toUpperCase()}${params.nometabela.substring(1,params.nometabela.size()).toLowerCase().replace('_','')}.groovy"));	
								    criarapp.write(objClasse); 
								    criarapp.close();
								}
						  
						 }
						
					}   
					
				}
		  
		}catch(Exception e){}	

		render view:'selecionartabelas.gsp', model:[tabelas:tabelas,dataBase:dataBase]
	
	}
	
}