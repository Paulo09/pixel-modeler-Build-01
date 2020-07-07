<html>
    <head>       
		 <link type="text/css" rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/0.98.0/css/materialize.min.css"/>
	     <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		 
		 <!--Materilize embarcado-->
	      <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
	      <!--Import materialize.css-->
	      <link type="text/css" rel="stylesheet" href="${createLinkTo(dir:'css',file:'materialize.min.css')}" media="screen,projection"/>
	      <!--Let browser know website is optimized for mobile-->
	      <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
		  <link rel="stylesheet" href="${createLinkTo(dir:'css',file:'materialize.css')}"/>
		  <!--Materilize embarcado-->
		
        <title>Cadastrar Gerararquivo</title>         
    </head>
	<nav class="nav-extended btn waves-effect waves-light" style="background-image:url(${createLinkTo(dir:'images/view/create/barraMenu',file:'barraMenu.jpg')});">  
		<div class="nav-content">
		  <ul class="tabs tabs-transparent">
			<li class="tab"><a href="#test1">Sair</a></li>
			<li class="tab"><a class="active" href="#test2">Menu</a></li>
			<li class="tab disabled"><a href="#test3">Buscar</a></li>
		  </ul>
		</div>
	</nav>
	<body class="teal lighten-2">   
	<div class="container" style="margin-top:40px;">
    <body style="background-image:url(${createLinkTo(dir:'images/view/create/backgroundPagina',file:'backgroundPagina.jpg')});background-repeat:no-repeat;background-size:cover;">
        <div class="card">
		  <div class="card-image"><br>
			 <% if(!tabelas){ %> 
			  <span class="card-title"><b>Selecionar Data Base</b></span>
			 <% } %>
			 <% if(tabelas){ %> 
			  <span class="card-title"><b>Database Selecionado: ${dataBase} - Selecionar Tabela</b></span>
			 <% } %> 
		</div>
        <div class="body">
            <g:if test="${flash.message}">
				<nav class="nav-extended btn waves-effect waves-light" style="background-image:url(${createLinkTo(dir:'images/view/create/message',file:'message.jpg')});">${flash.message}</nav>
            </g:if>
            <g:hasErrors bean="${gerararquivo}">
            <div class="errors">
                <g:renderErrors bean="${gerararquivo}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="selecionartabelas" method="post" >
                <div class="dialog">
                    <table>
                        <tbody>
						
							<% if(!tabelas){ %>                        
	                            <tr class="prop">
	                                <td valign="top" class="name"></td>
	                                <td valign="top" class="value ${hasErrors(bean:gerararquivo,field:'base','errors')}">
	                                    <g:select class="form-control" optionKey="id" from="${Base.list()}" name="base.id" value="${gerararquivo?.base?.id}" ></g:select>
										<!--<label for="base"><font size="3"><b>Data Base *</b></font></label>-->
	                                </td>
	                            </tr>
							<% } %>
							
							<% if(tabelas){ %>							
								<tr class="prop">
	                                <td valign="top" class="name"></td>
	                                <td valign="top" class="value ${hasErrors(bean:gerararquivo,field:'base','errors')}">
	                                    <g:select class="form-control" from="${tabelas}" name="nometabela" multiple="true"></g:select>
										<!--<label for="base"><font size="3"><b>Base ${dataBase}</b></font></label>-->
	                                </td>
	                            </tr>							
							<% } %>
								
                        
                           <!--<tr class="prop">
                                <td valign="top" class="name"></td>
                                <td valign="top" class="value ${hasErrors(bean:gerararquivo,field:'caminhoArquivo','errors')}">
                                    <i class="fa fa-text-width"></i><input type="text" class="form-control" size="50" placeholder="Digite caminhoArquivo " id="caminhoArquivo" name="caminhoArquivo" size="50" value="${fieldValue(bean:gerararquivo,field:'caminhoArquivo')}"/>
									<label for="caminhoArquivo"><font size="3"><b>Caminho Arquivo</b></font></label>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name"></td>
                                <td valign="top" class="value ${hasErrors(bean:gerararquivo,field:'nomeArquivo','errors')}">
                                    <i class="fa fa-text-width"></i><input type="text" class="form-control" size="50" placeholder="Digite nomeArquivo " id="nomeArquivo" name="nomeArquivo" size="50" value="${fieldValue(bean:gerararquivo,field:'nomeArquivo')}"/>
									<label for="nomeArquivo"><font size="3"><b>Nome Arquivo</b></font></label>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name"></td>
                                <td valign="top" class="value ${hasErrors(bean:gerararquivo,field:'tipoArquivo','errors')}">
                                    <i class="fa fa-text-width"></i><input type="text" class="form-control" size="50" placeholder="Digite tipoArquivo " id="tipoArquivo" name="tipoArquivo" size="50" value="${fieldValue(bean:gerararquivo,field:'tipoArquivo')}"/>
									<label for="tipoArquivo"><font size="3"><b>Tipo Arquivo</b></font></label>
                                </td>
                            </tr>-->
                        
                        </tbody>
                    </table>
                </div>
	            <div class="buttons" align="center">
                    <span class="button"><input  class="btn waves-effect waves-light" style="background-image:url(${createLinkTo(dir:'images/view/create/botao',file:'botao.jpg')});padding:10px;margin:10px;size:30px;width:110px;" type="submit" value="Gerar"/></span>
					<% if(tabelas){ %><span class="button"><input  class="btn waves-effect waves-light" style="background-image:url(${createLinkTo(dir:'images/view/create/botao',file:'botao.jpg')});padding:10px;margin:10px;size:30px;width:110px;" type="reset" onClick="history.go(-1)" value="Voltar"/></span><% } %>
                </div><br>
            </g:form>
        </div>
		   <!--Import jQuery before materialize.js-->
	       <script type="text/javascript" src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
	       <script type="text/javascript" src="${createLinkTo(dir:'js',file:'materialize.js')}"></script>
		   <script>
			  $('.datepicker').pickadate({format: 'dd/mm/yy',selectMonths: true,selectYears: 15,today: 'Hoje',clear: 'Limpar',close:'Ok',closeOnSelect: false});
			  $(document).ready(function() {$('select').material_select();});
		   </script>
    </body>
</html>
