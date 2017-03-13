package com.itechart.data.dao;

import com.itechart.data.db.JdbcDataSource;
import com.itechart.data.entity.Attachment;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public class JdbcAttachmentDao implements IAttachmentDao {

    private final String SELECT_ATTACHMENTS_FOR_CONTACT_QUERY = "SELECT attachId, attachName, uploadDate, comment, file, contact FROM attachments WHERE contact = ?";
    private final String SELECT_ATTACHMENTS_BY_ID_QUERY = "SELECT attachId, attachName, uploadDate, comment, file, contact FROM attachments WHERE attachId = ?";

    private JdbcDataSource ds;

    public JdbcAttachmentDao(JdbcDataSource ds) {
        this.ds = ds;
    }


    @Override
    public ArrayList<Attachment> getAllForContact(int id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<Attachment> attachments = new ArrayList<Attachment>();
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_ATTACHMENTS_FOR_CONTACT_QUERY);
            st.setInt(1, id);
            rs = st.executeQuery();
            rs.next();
            Date uploadDate = new Date(rs.getTimestamp("uploadDate").getTime());
            String attach_name = rs.getString("attachName");
            int attach_id = rs.getInt("attachId");
            String comment = rs.getString("comment");
            String file = rs.getString("file");
            int contact = rs.getInt("contact");
            Attachment attachment = new Attachment(attach_id, attach_name, uploadDate, comment, file, contact);
            attachments.add(attachment);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (st != null) try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (cn != null) try {
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return attachments;

    }

    @Override
    public Attachment getAttachmentById(int id) {

        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Attachment attachment = null;
        try {
            cn = ds.getConnection();
            st = cn.prepareStatement(SELECT_ATTACHMENTS_BY_ID_QUERY);
            st.setInt(1, id);
            rs = st.executeQuery();
            Date uploadDate = new Date(rs.getTimestamp("uploadDate").getTime());
            String attach_name = rs.getString("attachName");
            int attach_id = rs.getInt("attachId");
            String comment = rs.getString("comment");
            String file = rs.getString("file");
            int contact = rs.getInt("contact");
            attachment = new Attachment(attach_id, attach_name, uploadDate, comment, file, contact);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (rs != null) try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (st != null) try {
                st.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if (cn != null) try {
                cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return attachment;

    }

    @Override
    public void save(Attachment attachment) {

    }

    @Override
    public void delete(Attachment attachment) {

    }

    @Override
    public void update(Attachment attachment) {

    }


}
