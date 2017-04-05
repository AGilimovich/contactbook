package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.entity.Attachment;
import com.itechart.data.exception.DaoException;
import com.itechart.data.transaction.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public class JdbcAttachmentDao implements IAttachmentDao {
    private Logger logger = LoggerFactory.getLogger(JdbcAddressDao.class);

    private final String SELECT_ATTACHMENTS_FOR_CONTACT_QUERY = "SELECT attach_id, attach_name, upload_date, comment, file, contact_id FROM attachment WHERE contact_id = ?";
    private final String SELECT_ATTACHMENTS_BY_ID_QUERY = "SELECT attach_id, attach_name, upload_date, comment, file, contact_id FROM attachment WHERE attach_id = ?";
    private final String DELETE_FOR_USER_QUERY = "DELETE FROM attachment WHERE contact_id = ?";
    private final String INSERT_ATTACHMENT_QUERY = "INSERT INTO attachment(attach_name,upload_date,comment,file,contact_id) VALUES (?,?,?,?,?)";
    private final String UPDATE_ATTACHMENT_QUERY = "UPDATE attachment SET attach_name=?,comment=? WHERE attach_id=?";
    private final String DELETE_ATTACHMENT_QUERY = "DELETE FROM attachment WHERE attach_id = ?";
    private Transaction transaction;

    public JdbcAttachmentDao(Transaction transaction) {
        this.transaction = transaction;
    }


    @Override
    public ArrayList<Attachment> getAllForContact(long id) throws DaoException {
        logger.info("Fetch attachments for contact with id: {}", id);

        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<Attachment> attachments = new ArrayList<Attachment>();
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_ATTACHMENTS_FOR_CONTACT_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                Date uploadDate = new Date(rs.getTimestamp("upload_date").getTime());
                String attach_name = rs.getString("attach_name");
                long attach_id = rs.getLong("attach_id");
                String comment = rs.getString("comment");
                long file = rs.getLong("file");
                long contact = rs.getLong("contact_id");
                Attachment attachment = new Attachment(attach_id, attach_name, uploadDate, comment, file, contact);
                attachments.add(attachment);
            }

        } catch (SQLException e) {
            throw new DaoException("Exception during attachments retrieval from the database", e);

        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }
        return attachments;

    }

    @Override
    public Attachment getAttachmentById(long id) throws DaoException {
        logger.info("Fetch attachment with id: {}", id);
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Attachment attachment = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_ATTACHMENTS_BY_ID_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            Date uploadDate = new Date(rs.getTimestamp("upload_date").getTime());
            String attach_name = rs.getString("attach_name");
            long attach_id = rs.getLong("attach_id");
            String comment = rs.getString("comment");
            Long file = rs.getLong("file");
            long contact = rs.getLong("contact_id");
            attachment = new Attachment(attach_id, attach_name, uploadDate, comment, file, contact);

        } catch (SQLException e) {
            throw new DaoException("Exception during attachment retrieval from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }
        return attachment;

    }

    @Override
    public long save(Attachment attachment) throws DaoException {
        logger.info("Save attachment: {}", attachment);
        if (attachment == null) throw new DaoException("Attachment is null");
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        long id = 0;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(INSERT_ATTACHMENT_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, attachment.getName());
            st.setTimestamp(2, new Timestamp(attachment.getUploadDate().getTime()));
            st.setString(3, attachment.getComment());
            st.setLong(4, attachment.getFile());
            st.setLong(5, attachment.getContact());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getLong(1);

            }

        } catch (SQLException e) {
            throw new DaoException("Exception during saving attachment in the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, rs);
        }
        return id;
    }


    @Override
    public void delete(long id) throws DaoException {
        logger.info("Delete attachment with id: {}", id);
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_ATTACHMENT_QUERY);
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during deleting attachment from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, null);
        }

    }

    @Override
    public void update(Attachment attachment) throws DaoException {
        logger.info("Update attachment: {}", attachment);
        if (attachment == null) throw new DaoException("Attachment is null");
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(UPDATE_ATTACHMENT_QUERY);
            st.setString(1, attachment.getName());
            st.setString(2, attachment.getComment());
            st.setLong(3, attachment.getId());
            st.executeUpdate();

        } catch (SQLException e) {
            throw new DaoException("Exception during updating attachment in the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, null);
        }
    }

    @Override
    public void deleteForUser(long contactId) throws DaoException {
        logger.info("Delete attachments for contact with id: {}", contactId);
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_FOR_USER_QUERY);
            st.setLong(1, contactId);
            st.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("Exception during deleting attachments from the database", e);
        } finally {
            DBResourceManager.closeResources(null, st, null);
        }
    }
}



