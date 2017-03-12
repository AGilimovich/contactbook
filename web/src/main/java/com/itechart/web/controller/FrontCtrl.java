package com.itechart.web.controller;

import com.itechart.data.dao.ContactDao;
import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.entity.Contact;
import com.itechart.web.command.Command;
import com.itechart.web.command.CommandFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * This is controller of all requests on the main form.
 */
public class FrontCtrl extends HttpServlet {
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        CommandFactory factory = new CommandFactory();
        Command command = factory.getCommand(request);
        String page = command.execute(request, response);
        request.setAttribute("param", "Alex");//todo delete
        dispatch(request, response, page);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest(request, response);
    }

    protected void dispatch(HttpServletRequest request, HttpServletResponse response, String page) throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
        dispatcher.forward(request, response);
    }

}


