package rest.api.docs

import com.wordnik.swagger.annotations.Api
import grails.rest.RestfulController

@Api(value = 'manha', description = 'Manha Pixell  API')
class ManhaController extends RestfulController {
    static responseFormats = ['json', 'xml']
    ManhaController() {
        super(Manha)
    }
}