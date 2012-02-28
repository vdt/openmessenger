package openmessenger

class UserController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]
	def userService

    def index = {
        redirect(action: "list", params: params)
    }

    def list = {
        params.max = Math.min(params.max ? params.int('max') : 10, 100)
        [userInstanceList: User.list(params), userInstanceTotal: User.count()]
    }

    def create = {
        def userInstance = new User()
        //userInstance.properties = params
        return [userInstance: userInstance]
    }

    def save = {
		def paramEvents = params.remove('events')
		def paramRoles = params.remove('roles')
		
		def userInstance = new User(params)
		
        if (userInstance.validate()) { //save(flush: true)) {
			def roles = []
			paramEvents.each { roles.add it.toLong() }
			
			def events = []
			paramRoles.each { events.add it.toLong() }
			
			userService.save(userInstance, roles, events)
			
			flash.message = "${message(code: 'default.created.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
            redirect(action: "view", id: userInstance.id)
        }
        else {
            render(view: "create", model: [userInstance: userInstance])		
        }
    }

    def view = {
        def userInstance = User.get(params.id)
        if (!userInstance) {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
            [userInstance: userInstance]
        }
    }

    def edit = {
        def userInstance = User.get(params.id)
        if (!userInstance) {			
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
        else {
            return [userInstance: userInstance]
        }
    }

    def update = {
		def userInstance = User.get(params.id)
		
        if (userInstance) {
            if (params.version) {
                def version = params.version.toLong()
                if (userInstance.version > version) {                    
                    userInstance.errors.rejectValue("version", "default.optimistic.locking.failure", [message(code: 'user.label', default: 'User')] as Object[], "Another user has updated this User while you were editing")
                    render(view: "edit", model: [userInstance: userInstance])
                    return
                }
            }
            userInstance.properties = params
            if (userInstance.validate()) {
				try {
					def roles = []
					params.roles.each { roles.add it.toLong() }
					def events = []
					params.events.each { events.add it.toLong() }					
					
					userService.save(userInstance, roles, events)
					flash.message = "${message(code: 'default.updated.message', args: [message(code: 'user.label', default: 'User'), userInstance.id])}"
	                redirect(action: "view", id: userInstance.id)
				} catch (Exception e) {
					log.error(e)
					render(view: "edit", model: [userInstance: userInstance])
				}
            }
            else {
                render(view: "edit", model: [userInstance: userInstance])
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
    }

    def delete = {
        def userInstance = User.get(params.id)
		def username = userInstance.username
        if (userInstance) {
            try {				
                userService.delete(userInstance)
                flash.message = "${message(code: 'default.deleted.message', args: [message(code: 'user.label', default: 'User'), username])}"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${message(code: 'default.not.deleted.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
                redirect(action: "view", id: params.id)
            }
        }
        else {
            flash.message = "${message(code: 'default.not.found.message', args: [message(code: 'user.label', default: 'User'), params.id])}"
            redirect(action: "list")
        }
    }
}
