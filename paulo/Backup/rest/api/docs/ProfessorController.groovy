package rest.api.docs

import com.wordnik.swagger.annotations.Api
import grails.rest.RestfulController

@Api(value = 'professor', description = 'Professor Pixell  API')
class ProfessorController extends RestfulController {
    static responseFormats = ['json', 'xml']
    ProfessorController() {
        super(Professor)
    }
}