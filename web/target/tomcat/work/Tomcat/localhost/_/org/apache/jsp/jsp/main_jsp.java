/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2017-03-11 13:27:34 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class main_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <title>Title</title>\r\n");
      out.write("\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"/resources/css/bootstrap/bootstrap.css\">\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"/resources/css/main.css\">\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<nav class=\"navbar navbar-default\">\r\n");
      out.write("    <div class=\"container-fluid\">\r\n");
      out.write("        <div class=\"navbar-header\">\r\n");
      out.write("            <span class=\"navbar-brand\">Справочник контактов</span>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"collapse navbar-collapse\" id=\"myNavbar\">\r\n");
      out.write("            <ul class=\"nav navbar-nav navbar-right\">\r\n");
      out.write("                <li class=\"active\"><a href=\"/search\"><span class=\"glyphicon glyphicon-search\"></span> Поиск\r\n");
      out.write("                    контактов</a></li>\r\n");
      out.write("                <li><a href=\"/email\"><span class=\"glyphicon glyphicon-envelope\"></span> Отправка Email</a></li>\r\n");
      out.write("\r\n");
      out.write("            </ul>\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</nav>\r\n");
      out.write("<div class=\"container-fluid\">\r\n");
      out.write("    <form action=\"\">\r\n");
      out.write("        <div class=\"row\">\r\n");
      out.write("            <button type=\"button\" formaction=\"/delete\" formmethod=\"post\" class=\"btn btn-danger btn-delete\">Удалить\r\n");
      out.write("            </button>\r\n");
      out.write("            <button type=\"button\" formaction=\"/add\" formmethod=\"get\" class=\"btn btn-primary btn-add\">Добавить <span\r\n");
      out.write("                    class=\"glyphicon glyphicon-plus\"></span></button>\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"row\">\r\n");
      out.write("            <table class=\"table\">\r\n");
      out.write("                <tr valign=\"middle\">\r\n");
      out.write("                    <td id=\"table-checkbox\" width=\"5%\" align=\"middle\"><input type=\"checkbox\" name=\"isSelected\"></td>\r\n");
      out.write("\r\n");
      out.write("                    <td width=\"110px\">\r\n");
      out.write("                        <div class=\"photo-container\">\r\n");
      out.write("                            ");
      out.write("\r\n");
      out.write("                            <img src=\"/resources/images/male.jpg\" alt=\"\" height=\"100%\" class=\"photo\">\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </td>\r\n");
      out.write("                    <td>\r\n");
      out.write("                        <p><a href=\"/edit\" class=\"name\">Гилимович Александр Сергеевич <span\r\n");
      out.write("                                class=\"glyphicon glyphicon-pencil\"></span></a></p>\r\n");
      out.write("\r\n");
      out.write("                        <div class=\"row\">\r\n");
      out.write("                            <div class=\"col-md-2\">\r\n");
      out.write("                                <p><b>Дата рождения:</b></p>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            <div class=\"col-md-2\">\r\n");
      out.write("                                <p><b>Домашний адрес:</b></p>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            <div class=\"col-md-2\">\r\n");
      out.write("                                <p><b>Место работы:</b></p>\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                        <div class=\"row\">\r\n");
      out.write("                            <div class=\"col-md-2\">\r\n");
      out.write("                                <p>12.11.1990 г.</p>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            <div class=\"col-md-2\">\r\n");
      out.write("                                <p>г. Минск, ул.Селицкого, 77-112</p>\r\n");
      out.write("                            </div>\r\n");
      out.write("                            <div class=\"col-md-2\">\r\n");
      out.write("                                <p>Тунеядец</p>\r\n");
      out.write("                            </div>\r\n");
      out.write("                        </div>\r\n");
      out.write("                    </td>\r\n");
      out.write("                </tr>\r\n");
      out.write("            </table>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"row\">\r\n");
      out.write("            <div class=\"col-md-10\">\r\n");
      out.write("                <div class=\"pages\">\r\n");
      out.write("                    <ul class=\"pagination\">\r\n");
      out.write("                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">Previous</a></li>\r\n");
      out.write("                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">1</a></li>\r\n");
      out.write("                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">2</a></li>\r\n");
      out.write("                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">3</a></li>\r\n");
      out.write("                        <li class=\"page-item\"><a class=\"page-link\" href=\"#\">Next</a></li>\r\n");
      out.write("                    </ul>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"col-md-2\">\r\n");
      out.write("                <div class=\"items-display\">\r\n");
      out.write("                    <span>Отображать контактов:</span>\r\n");
      out.write("                    <select name=\"display-items\" class=\"form-control\">\r\n");
      out.write("                        <option value=\"ten\">10</option>\r\n");
      out.write("                        <option value=\"twenty\">20</option>\r\n");
      out.write("                    </select>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("        </div>\r\n");
      out.write("    </form>\r\n");
      out.write("</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try { out.clearBuffer(); } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
