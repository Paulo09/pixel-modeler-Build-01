package rest.api.docs
import com.wordnik.swagger.annotations.Api
import grails.rest.RestfulController
@Api(value = 'pessoa', description = 'pessoa Management  API')
class pessoaController extends RestfulController {
static responseFormats = ['json', 'xml']
UserController(){
super(User)
}}