package rest.api.docs
import com.wordnik.swagger.annotations.Api
import grails.rest.RestfulController
@Api(value = 'gerararquivo', description = 'gerararquivo Management  API')
class gerararquivoController extends RestfulController {
static responseFormats = ['json', 'xml']
UserController(){
super(User)
}}