/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/7.0.47
 * Generated at: 2017-03-21 13:26:05 UTC
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

private static org.apache.jasper.runtime.ProtectedFunctionMapper _jspx_fnmap_0;

static {
  _jspx_fnmap_0= org.apache.jasper.runtime.ProtectedFunctionMapper.getMapForFunction("fn:length", org.apache.taglibs.standard.functions.Functions.class, "length", new Class[] {java.lang.Object.class});
}

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems;
  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fstep_005fend_005fbegin;

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public void _jspInit() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fstep_005fend_005fbegin = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.release();
    _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fstep_005fend_005fbegin.release();
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
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("    <title>Title</title>\r\n");
      out.write("\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"/resources/css/bootstrap/bootstrap.css\">\r\n");
      out.write("    <link rel=\"stylesheet\" href=\"/resources/css/main.css\">\r\n");
      out.write("    <script src=\"/resources/js/main.js\" defer></script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("<nav class=\"navbar navbar-default\">\r\n");
      out.write("    <div class=\"container-fluid\">\r\n");
      out.write("        <div class=\"navbar-header\">\r\n");
      out.write("            <a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/\" class=\"navbar-brand\">Справочник контактов</a>\r\n");
      out.write("        </div>\r\n");
      out.write("        <div class=\"collapse navbar-collapse\" id=\"myNavbar\">\r\n");
      out.write("            <ul class=\"nav navbar-nav navbar-right\">\r\n");
      out.write("                <li class=\"active\"><a href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/search\"><span\r\n");
      out.write("                        class=\"glyphicon glyphicon-search\"></span> Поиск\r\n");
      out.write("                    контактов</a></li>\r\n");
      out.write("                <li>\r\n");
      out.write("                    <a href=\"javascript:{}\" onclick=\"document.getElementById('main-form').submit();\">\r\n");
      out.write("                        <span class=\"glyphicon glyphicon-envelope\"></span> Отправка Email\r\n");
      out.write("                    </a>\r\n");
      out.write("                </li>\r\n");
      out.write("\r\n");
      out.write("            </ul>\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("    </div>\r\n");
      out.write("</nav>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<div class=\"container-fluid\">\r\n");
      out.write("    <form id=\"main-form\" action=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/email\">\r\n");
      out.write("        <div class=\"row\">\r\n");
      out.write("            <button type=\"submit\" formaction=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/delete\" formmethod=\"post\"\r\n");
      out.write("                    class=\"btn btn-danger btn-delete\">\r\n");
      out.write("                Удалить\r\n");
      out.write("            </button>\r\n");
      out.write("            <button type=\"submit\" formaction=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
      out.write("/add\" formmethod=\"get\"\r\n");
      out.write("                    class=\"btn btn-primary btn-add\">\r\n");
      out.write("                Добавить <span\r\n");
      out.write("                    class=\"glyphicon glyphicon-plus\"></span></button>\r\n");
      out.write("\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"row\">\r\n");
      out.write("            <table class=\"table\">\r\n");
      out.write("                ");
      if (_jspx_meth_c_005fforEach_005f0(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("            </table>\r\n");
      out.write("        </div>\r\n");
      out.write("\r\n");
      out.write("        <div class=\"row\">\r\n");
      out.write("            <div class=\"col-md-10\">\r\n");
      out.write("                <div class=\"pages\">\r\n");
      out.write("                    <ul class=\"pagination\">\r\n");
      out.write("                        <li class=\"page-item-prev hidden\"><a class=\"page-link\" href=\"javascript:{}\"\r\n");
      out.write("                                                             onclick=\"goToPrevPage();\">Previous</a>\r\n");
      out.write("                        </li>\r\n");
      out.write("                        ");
      if (_jspx_meth_c_005fforEach_005f1(_jspx_page_context))
        return;
      out.write("\r\n");
      out.write("                        <li class=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${fn:length(contacts)>10? 'page-item-next': 'page-item-next hidden'}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, _jspx_fnmap_0, false));
      out.write("\"><a\r\n");
      out.write("                                class=\"page-link\" href=\"javascript:{}\" onclick=\"goToNextPage();\">Next</a></li>\r\n");
      out.write("                    </ul>\r\n");
      out.write("                </div>\r\n");
      out.write("            </div>\r\n");
      out.write("            <div class=\"col-md-2\">\r\n");
      out.write("                <div class=\"items-display\">\r\n");
      out.write("                    <span>Отображать контактов:</span>\r\n");
      out.write("                    <select id=\"display-items\" class=\"form-control\" onchange=\"changeDisplayingItemsCount()\">\r\n");
      out.write("                        <option value=\"10\">10</option>\r\n");
      out.write("                        <option value=\"20\">20</option>\r\n");
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

  private boolean _jspx_meth_c_005fforEach_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f0 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f0.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f0.setParent(null);
    // /jsp/main.jsp(53,16) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setVar("contact");
    // /jsp/main.jsp(53,16) name = items type = javax.el.ValueExpression reqTime = true required = false fragment = false deferredValue = true expectedTypeName = java.lang.Object deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setItems(new org.apache.jasper.el.JspValueExpression("/jsp/main.jsp(53,16) '${contacts}'",_el_expressionfactory.createValueExpression(_jspx_page_context.getELContext(),"${contacts}",java.lang.Object.class)).getValue(_jspx_page_context.getELContext()));
    // /jsp/main.jsp(53,16) name = varStatus type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f0.setVarStatus("status");
    int[] _jspx_push_body_count_c_005fforEach_005f0 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f0 = _jspx_th_c_005fforEach_005f0.doStartTag();
      if (_jspx_eval_c_005fforEach_005f0 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("\r\n");
          out.write("                    <tr valign=\"middle\" class=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${status.index<10? 'contact-entry':'contact-entry hidden'}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("\">\r\n");
          out.write("                        <td class=\"table-checkbox\" width=\"5%\" align=\"middle\">\r\n");
          out.write("                            <input type=\"checkbox\" name=\"isSelected\" value=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.contactId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("\">\r\n");
          out.write("                        </td>\r\n");
          out.write("\r\n");
          out.write("                        <td width=\"110px\">\r\n");
          out.write("                            <div class=\"photo-container\">\r\n");
          out.write("                                <img src=\"/file?id=");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.photo}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("\" alt=\"\" height=\"100%\" class=\"photo\">\r\n");
          out.write("                            </div>\r\n");
          out.write("                        </td>\r\n");
          out.write("                        <td>\r\n");
          out.write("                            <p><a href=\"");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("/edit?id=");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.contactId}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("\"\r\n");
          out.write("                                  class=\"name\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.surname}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write(' ');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.name}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write(' ');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.patronymic}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("\r\n");
          out.write("                            </a>\r\n");
          out.write("                            </p>\r\n");
          out.write("\r\n");
          out.write("                            <div class=\"row\">\r\n");
          out.write("                                <div class=\"col-md-2\">\r\n");
          out.write("                                    <p><b>Дата рождения:</b></p>\r\n");
          out.write("                                </div>\r\n");
          out.write("                                <div class=\"col-md-2\">\r\n");
          out.write("                                    <p><b>Домашний адрес:</b></p>\r\n");
          out.write("                                </div>\r\n");
          out.write("                                <div class=\"col-md-2\">\r\n");
          out.write("                                    <p><b>Место работы:</b></p>\r\n");
          out.write("                                </div>\r\n");
          out.write("                            </div>\r\n");
          out.write("                            <div class=\"row\">\r\n");
          out.write("                                <div class=\"col-md-2\">\r\n");
          out.write("                                        ");
          out.write("\r\n");
          out.write("                                    <p>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.dateOfBirth}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("</p>\r\n");
          out.write("                                </div>\r\n");
          out.write("                                <div class=\"col-md-2\">\r\n");
          out.write("                                    <p>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.country}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write(',');
          out.write(' ');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.city}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write(", ул. ");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.street}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write(",\r\n");
          out.write("                                        д.");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.house}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write('-');
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.apartment}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("</p>\r\n");
          out.write("                                </div>\r\n");
          out.write("                                <div class=\"col-md-2\">\r\n");
          out.write("                                    <p>");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${contact.placeOfWork}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("</p>\r\n");
          out.write("\r\n");
          out.write("                                </div>\r\n");
          out.write("                            </div>\r\n");
          out.write("                        </td>\r\n");
          out.write("                    </tr>\r\n");
          out.write("                ");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f0.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f0[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f0.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f0.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fvar_005fitems.reuse(_jspx_th_c_005fforEach_005f0);
    }
    return false;
  }

  private boolean _jspx_meth_c_005fforEach_005f1(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:forEach
    org.apache.taglibs.standard.tag.rt.core.ForEachTag _jspx_th_c_005fforEach_005f1 = (org.apache.taglibs.standard.tag.rt.core.ForEachTag) _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fstep_005fend_005fbegin.get(org.apache.taglibs.standard.tag.rt.core.ForEachTag.class);
    _jspx_th_c_005fforEach_005f1.setPageContext(_jspx_page_context);
    _jspx_th_c_005fforEach_005f1.setParent(null);
    // /jsp/main.jsp(109,24) name = begin type = int reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f1.setBegin(1);
    // /jsp/main.jsp(109,24) name = end type = int reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f1.setEnd(((java.lang.Integer) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${fn:length(contacts)}", java.lang.Integer.class, (javax.servlet.jsp.PageContext)_jspx_page_context, _jspx_fnmap_0, false)).intValue());
    // /jsp/main.jsp(109,24) name = varStatus type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f1.setVarStatus("status");
    // /jsp/main.jsp(109,24) name = step type = int reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
    _jspx_th_c_005fforEach_005f1.setStep(10);
    int[] _jspx_push_body_count_c_005fforEach_005f1 = new int[] { 0 };
    try {
      int _jspx_eval_c_005fforEach_005f1 = _jspx_th_c_005fforEach_005f1.doStartTag();
      if (_jspx_eval_c_005fforEach_005f1 != javax.servlet.jsp.tagext.Tag.SKIP_BODY) {
        do {
          out.write("\r\n");
          out.write("                            <li class=\"page-item\"><a class=\"page-link\" href=\"javascript:{}\"\r\n");
          out.write("                                                     onclick=\"goToPage(");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${status.count}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write(");\">");
          out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${status.count}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null, false));
          out.write("</a></li>\r\n");
          out.write("                        ");
          int evalDoAfterBody = _jspx_th_c_005fforEach_005f1.doAfterBody();
          if (evalDoAfterBody != javax.servlet.jsp.tagext.BodyTag.EVAL_BODY_AGAIN)
            break;
        } while (true);
      }
      if (_jspx_th_c_005fforEach_005f1.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } catch (java.lang.Throwable _jspx_exception) {
      while (_jspx_push_body_count_c_005fforEach_005f1[0]-- > 0)
        out = _jspx_page_context.popBody();
      _jspx_th_c_005fforEach_005f1.doCatch(_jspx_exception);
    } finally {
      _jspx_th_c_005fforEach_005f1.doFinally();
      _005fjspx_005ftagPool_005fc_005fforEach_0026_005fvarStatus_005fstep_005fend_005fbegin.reuse(_jspx_th_c_005fforEach_005f1);
    }
    return false;
  }
}
