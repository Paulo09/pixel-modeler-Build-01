package rest.api.docs
import com.wordnik.swagger.annotations.Api
import grails.rest.RestfulController
@Api(value = 'adm', description = 'adm Management  API')
class admController extends RestfulController {
static responseFormats = ['json', 'xml']
UserController(){
super(User)
}}