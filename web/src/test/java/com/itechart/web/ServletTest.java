package com.itechart.web;

import com.itechart.data.dao.IContactDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dto.FullContactDTO;
import com.itechart.data.entity.Address;
import com.itechart.data.entity.Contact;
import com.itechart.data.entity.File;
import com.itechart.data.exception.DaoException;
import com.itechart.data.transaction.TransactionManager;
import com.itechart.web.controller.FrontCtrl;
import org.junit.Before;
import org.junit.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Integration test.
 */
public class ServletTest {
//    private HttpServletRequest request;
//    private HttpServletResponse response;
//    private TransactionManager transactionManager;
//
//    @Before
//    public void initialize() {
//        request = mock(HttpServletRequest.class);
//        response = mock(HttpServletResponse.class);
////        DataSource dataSource = null;
////        try {
////            Context initContext = new InitialContext();
////            Context envContext = (Context) initContext.lookup("java:/comp/env");
////            dataSource = (DataSource) envContext.lookup("jdbc/MySQLDatasource");
////        } catch (NamingException e) {
////            e.printStackTrace();
////        }
////        transactionManager = new TransactionManager(dataSource);
//
//    }
//
//    @Test
//    public void servletTest() throws ServletException, IOException {
//        FrontCtrl servlet = new FrontCtrl();
//        servlet.doGet(request,response);
//        assertEquals("0", response.getStatus());
//    }
//
//
//    @Test
//    public void dataTest() throws ServletException, IOException {
////        Contact contact = new Contact();
////        contact.setName("Александр");
////        contact.setSurname("Гилимович");
////        Address address = new Address();
////        File photo = new File();
////        FullContactDTO fullContactDTO = new FullContactDTO(contact, address, photo);
////
////        IContactDao contactDao = new JdbcContactDao(transactionManager.getTransaction());
////        try {
////            long id = contactDao.save(contact);
////            Contact fetchedContact = contactDao.getContactById(id);
////            assertEquals("Strings are not equal", fetchedContact.getName(), "Александр");
////            assertEquals("Strings are not equal", fetchedContact.getSurname(), "Гилимович");
////        } catch (DaoException e) {
////            e.printStackTrace();
////        }
//
//
//    }


}


