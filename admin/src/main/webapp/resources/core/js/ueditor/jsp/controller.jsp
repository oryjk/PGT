<%@ page language="java" contentType="text/html; charset=UTF-8"
	import="com.baidu.ueditor.ActionEnter,org.springframework.web.context.support.WebApplicationContextUtils,
	org.springframework.context.ApplicationContext,com.pgt.configuration.Configuration"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true" %>
<%
	ServletContext context = request.getSession().getServletContext();
	ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext(context);
	Configuration configuration = (Configuration) ctx.getBean("configuration");
	request.setAttribute("physicalPathPrefix", configuration.getImagePath());
    request.setCharacterEncoding( "utf-8" );
	response.setHeader("Content-Type" , "text/html");
	String rootPath = application.getRealPath( "/" );
	out.write( new ActionEnter( request, rootPath ).exec() );
	
%>