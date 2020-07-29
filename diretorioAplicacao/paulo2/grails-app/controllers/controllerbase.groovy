package rest.api.docs
import com.wordnik.swagger.annotations.Api
import grails.rest.RestfulController
@Api(value = 'base', description = 'base Management  API')
class baseController extends RestfulController {
static responseFormats = ['json', 'xml']
UserController(){
super(User)
}}