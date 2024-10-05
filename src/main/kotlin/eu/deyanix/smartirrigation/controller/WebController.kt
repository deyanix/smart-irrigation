package eu.deyanix.smartirrigation.controller

import jakarta.servlet.http.HttpServletRequest
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.servlet.ModelAndView

@Component
class ForwardErrorsToIndex : ErrorViewResolver {
	override fun resolveErrorView(request: HttpServletRequest?, status: HttpStatus?, model: MutableMap<String, Any>?): ModelAndView {
		return ModelAndView("forward:/index.html")
	}
}
