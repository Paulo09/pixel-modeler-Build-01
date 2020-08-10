package rest.api.docs
class UrlMappings {
		static mappings = {
		   "/$controller/$action?/$id?(.$format)?"{constraints{}}
		    "/adm"(resources: 'adm')
		   "/base"(resources: 'base')
		   "/gerararquivo"(resources: 'gerararquivo')
		   "/"(controller: "application", action:"index")
		   "500"(view: "/application/serverError")
		   "404"(view: "/application/notFound")		  
		}
}