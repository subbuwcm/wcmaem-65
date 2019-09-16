<%@page import="java.util.stream.Collectors"%>
<%@page import="java.io.PrintWriter"%>
<%@include file="/libs/foundation/global.jsp" %>
<%@page contentType="text/html" pageEncoding="utf-8"
   import="java.util.*,
   			org.apache.sling.api.resource.*,
   			com.day.cq.wcm.api.*,
           com.day.cq.commons.TidyJSONWriter" %><%
%>
<cq:defineObjects/>
<%
	String root = "/content/we-retail/us/en";
	String templatePath = "/conf/we-retail/settings/wcm/templates/hero-page";
	String cmpPath = "weretail/components/content/title";
	//list all components under the page
	Resource rootRes = resourceResolver.getResource(root);
	//PrintWriter out = response.getWriter();
	Page rootPage = rootRes.adaptTo(Page.class);
	out.println("-=-=-=-"+rootPage.getPath());
	//List<Page> result = rootPage.listChildren().forEachRemaining(childPage-> "weretail/components/structure/page".equals(childPage.getResourceType())).collect(Collectors.toList());
	List<Page> results = Optional.ofNullable(resourceResolver)
  	.map(resourceResolver -> resourceResolver.getResource(root))
  .map(pageResource -> pageResource.adaptTo(Page.class))
  .map(Page::getTemplate)
  .map(Template::getPath)
  .map(path -> StringUtils.contains(path, templatePath)).collect(Collectors.toList());
%>