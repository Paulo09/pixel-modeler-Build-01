package rest.api.docs
import com.wordnik.swagger.annotations.Api
import grails.rest.RestfulController
@Api(value = 'gerararquivo', description = 'Gerararquivo Pixel  API')
class GerararquivoController extends RestfulController {
static responseFormats = ['json', 'xml']
GerararquivoController(){
super(Gerararquivo)
}}