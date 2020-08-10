package modelo

import com.wordnik.swagger.annotations.Api
import grails.rest.RestfulController

@Api(value = 'professor', description = 'Professor Management  API')
class ProfessorController extends RestfulController {
    static responseFormats = ['json', 'xml']
    ProfessorController() {
        super(Professor)
    }
}