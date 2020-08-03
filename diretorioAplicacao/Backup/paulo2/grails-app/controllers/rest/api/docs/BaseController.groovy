package rest.api.docs

import com.wordnik.swagger.annotations.Api
import grails.rest.RestfulController

@Api(value = 'base', description = 'Base Pixell  API')
class BaseController extends RestfulController {
    static responseFormats = ['json', 'xml']
    BaseController() {
        super(Base)
    }
}