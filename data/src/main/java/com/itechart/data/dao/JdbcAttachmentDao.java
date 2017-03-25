package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.entity.Attachment;
import com.itechart.data.transaction.Transaction;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public class JdbcAttachmentDao implements IAttachmentDao {

    private final String SELECT_ATTACHMENTS_FOR_CONTACT_QUERY = "SELECT attach_id, attach_name, upload_date, comment, file, contact FROM attachment WHERE contact = ?";
    private final String SELECT_ATTACHMENTS_BY_ID_QUERY = "SELECT attach_id, attach_name, upload_date, comment, file, contact FROM attachment WHERE attach_id = ?";
    private final String DELETE_FOR_USER_QUERY = "DELETE FROM attachment WHERE contact = ?";
    private final String INSERT_ATTACHMENT_QUERY = "INSERT INTO attachment(attach_name,upload_date,comment,file,contact) VALUES (?,?,?,?,?)";
    private final String UPDATE_ATTACHMENT_QUERY = "UPDATE attachment SET attach_name=?,comment=? WHERE attach_id=?";
    private final String DELETE_ATTACHMENT_QUERY = "DELETE FROM attachment WHERE attach_id = ?";
    private Transaction transaction;

    public JdbcAttachmentDao(Transaction transaction) {
        this.transaction = transaction;
    }


    @Override
    public ArrayList<Attachment> getAllForContact(long id) {
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
                long contact = rs.getLong("contact");
                Attachment attachment = new Attachment(attach_id, attach_name, uploadDate, comment, file, contact);
                attachments.add(attachment);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }
        return attachments;

    }

    @Override
    public Attachment getAttachmentById(long id) {

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
            long contact = rs.getLong("contact");
            attachment = new Attachment(attach_id, attach_name, uploadDate, comment, file, contact);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }
        return attachment;

    }

    @Override
    public long save(Attachment attachment) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        long id = 0;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(INSERT_ATTACHMENT_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, attachment.getName());
            st.setDate(2, new java.sql.Date(attachment.getUploadDate().getTime()));
            st.setString(3, attachment.getComment());
            st.setLong(4, attachment.getFile());
            st.setLong(5, attachment.getContact());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            while (rs.next()) {
                id = rs.getLong(1);

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }
        return id;
    }


    @Override
    public void delete(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_ATTACHMENT_QUERY);
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, null);
        }

    }

    @Override
    public void update(Attachment attachment) {
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
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, null);
        }
    }

    @Override
    public void deleteForUser(long userId) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_FOR_USER_QUERY);
            st.setLong(1, userId);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, null);
        }
    }
}



