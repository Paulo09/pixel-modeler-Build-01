package pixel.modeler

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class BaseController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Base.list(params), model:[baseCount: Base.count()]
    }

    def show(Base base) {
        respond base
    }

    def create() {
        respond new Base(params)
    }

    @Transactional
    def save(Base base) {
        if (base == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (base.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond base.errors, view:'create'
            return
        }

        base.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'base.label', default: 'Base'), base.id])
                redirect base
            }
            '*' { respond base, [status: CREATED] }
        }
    }

    def edit(Base base) {
        respond base
    }

    @Transactional
    def update(Base base) {
        if (base == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (base.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond base.errors, view:'edit'
            return
        }

        base.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'base.label', default: 'Base'), base.id])
                redirect base
            }
            '*'{ respond base, [status: OK] }
        }
    }

    @Transactional
    def delete(Base base) {

        if (base == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        base.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'base.label', default: 'Base'), base.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'base.label', default: 'Base'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
