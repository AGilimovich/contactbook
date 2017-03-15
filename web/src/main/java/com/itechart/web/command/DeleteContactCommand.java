package com.itechart.web.command;

import com.itechart.data.dao.JdbcAddressDao;
import com.itechart.data.dao.JdbcAttachmentDao;
import com.itechart.data.dao.JdbcContactDao;
import com.itechart.data.dao.JdbcPhoneDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Command for deleting selected contacts.
 */
public class DeleteContactCommand implements Command {
    private JdbcContactDao contactDao;
    private JdbcPhoneDao phoneDao;
    private JdbcAttachmentDao attachmentDao;
    private JdbcAddressDao addressDao;

    public DeleteContactCommand(JdbcContactDao contactDao, JdbcPhoneDao phoneDao, JdbcAttachmentDao attachmentDao, JdbcAddressDao addressDao) {
        this.contactDao = contactDao;
        this.phoneDao = phoneDao;
        this.attachmentDao = attachmentDao;
        this.addressDao = addressDao;
    }

    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        String[] selectedContactsId = request.getParameterValues("isSelected");
        if (selectedContactsId != null) {
            for (String c : selectedContactsId) {
                contactDao.delete(Long.valueOf(c));
                return (new ShowContactsViewCommand(contactDao, addressDao)).execute(request, response);
            }
        }
        return (new ShowContactsViewCommand(contactDao, addressDao)).execute(request, response);
    }
}
