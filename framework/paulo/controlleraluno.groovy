package rest.api.docs
import com.wordnik.swagger.annotations.Api
import grails.rest.RestfulController
@Api(value = 'aluno', description = 'aluno Management  API')
class alunoController extends RestfulController {
static responseFormats = ['json', 'xml']
UserController(){
super(User)
}}