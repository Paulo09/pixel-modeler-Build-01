package rest.api.docs

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ProfessorController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Professor.list(params), model:[professorCount: Professor.count()]
    }

    def show(Professor professor) {
        respond professor
    }

    def create() {
        respond new Professor(params)
    }

    @Transactional
    def save(Professor professor) {
        if (professor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (professor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond professor.errors, view:'create'
            return
        }

        professor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'professor.label', default: 'Professor'), professor.id])
                redirect professor
            }
            '*' { respond professor, [status: CREATED] }
        }
    }

    def edit(Professor professor) {
        respond professor
    }

    @Transactional
    def update(Professor professor) {
        if (professor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (professor.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond professor.errors, view:'edit'
            return
        }

        professor.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'professor.label', default: 'Professor'), professor.id])
                redirect professor
            }
            '*'{ respond professor, [status: OK] }
        }
    }

    @Transactional
    def delete(Professor professor) {

        if (professor == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        professor.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'professor.label', default: 'Professor'), professor.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'professor.label', default: 'Professor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
