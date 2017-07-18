package com.application.interceptor;

import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ControllerAndActionNameInterceptor extends HandlerInterceptorAdapter {

	private static final Logger LOGGER = Logger.getLogger(ControllerAndActionNameInterceptor.class.getName());

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
//		LOGGER.info("****************** ControllerAndActionNameInterceptor preHandle START ******************");
		this.setControllerAndActionName(request, response, handler);
//		LOGGER.info("****************** ControllerAndActionNameInterceptor preHandle END ******************");
		return super.preHandle(request, response, handler);
	}

	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
//		LOGGER.info("****************** ControllerAndActionNameInterceptor postHandle START ******************");
		this.setControllerAndActionName(request, response, handler);
//		LOGGER.info("****************** ControllerAndActionNameInterceptor postHandle END ******************");
		super.postHandle(request, response, handler, modelAndView);

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
//		LOGGER.info("****************** ControllerAndActionNameInterceptor afterCompletion START ******************");
		this.setControllerAndActionName(request, response, handler);
//		LOGGER.info("****************** ControllerAndActionNameInterceptor afterCompletion END ******************");
		super.afterCompletion(request, response, handler, ex);
	}

	public void setControllerAndActionName(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String controllerName = "";
		String actionName = "";

		if (handler instanceof HandlerMethod) {
			// there are cases where this handler isn't an instance of
			// HandlerMethod, so the cast fails.
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			// controllerName =
			// handlerMethod.getBean().getClass().getSimpleName().replace("Controller",
			// "");
			controllerName = handlerMethod.getBeanType().getSimpleName().replace("Controller", "");
			controllerName = (!controllerName.isEmpty()
					? controllerName.substring(0, 1).toLowerCase() + controllerName.substring(1)
					: "");
			actionName = handlerMethod.getMethod().getName();
		}

//		LOGGER.info("****************** Controller Name:> " + controllerName);
//		LOGGER.info("****************** Action Name:> " + actionName);
		request.setAttribute(com.application.constants.AppConstant.CONTROLLER_NAME, controllerName);
		request.setAttribute(com.application.constants.AppConstant.ACTION_NAME, actionName);
	}

}
