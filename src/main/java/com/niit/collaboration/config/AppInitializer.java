package com.niit.collaboration.config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
	private static final Logger logger = LoggerFactory.getLogger(AppInitializer.class);
	@Override
	protected Class[] getRootConfigClasses() {
		logger.debug("start of method config classes");
		return new Class[]{AppConfig.class,WebSocketConfig.class};
	}
	@Override
	protected Class[] getServletConfigClasses() {
		logger.debug("start of method servlet config");
		return new Class[] {AppConfig.class, WebSocketConfig.class};
	}
	@Override
	protected String[] getServletMappings() {
		logger.debug("start of method servlet mappings");
		return  new String[]{"/"};
	}

}
