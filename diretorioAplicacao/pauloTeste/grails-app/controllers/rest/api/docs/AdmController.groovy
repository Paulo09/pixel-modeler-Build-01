package rest.api.docs
import com.wordnik.swagger.annotations.Api
import grails.rest.RestfulController
@Api(value = 'adm', description = 'Adm Pixel  API')
class AdmController extends RestfulController {
static responseFormats = ['json', 'xml']
AdmController(){
super(Adm)
}}