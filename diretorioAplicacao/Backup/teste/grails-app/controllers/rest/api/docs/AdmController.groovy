package rest.api.docs

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AdmController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Adm.list(params), model:[admCount: Adm.count()]
    }

    def show(Adm adm) {
        respond adm
    }

    def create() {
        respond new Adm(params)
    }

    @Transactional
    def save(Adm adm) {
        if (adm == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (adm.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond adm.errors, view:'create'
            return
        }

        adm.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'adm.label', default: 'Adm'), adm.id])
                redirect adm
            }
            '*' { respond adm, [status: CREATED] }
        }
    }

    def edit(Adm adm) {
        respond adm
    }

    @Transactional
    def update(Adm adm) {
        if (adm == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (adm.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond adm.errors, view:'edit'
            return
        }

        adm.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'adm.label', default: 'Adm'), adm.id])
                redirect adm
            }
            '*'{ respond adm, [status: OK] }
        }
    }

    @Transactional
    def delete(Adm adm) {

        if (adm == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        adm.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'adm.label', default: 'Adm'), adm.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'adm.label', default: 'Adm'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
