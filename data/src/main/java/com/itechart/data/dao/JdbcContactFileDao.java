package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.entity.ContactFile;
import com.itechart.data.transaction.Transaction;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class JdbcContactFileDao implements IContactFileDao {
    private Transaction transaction;

    private final String SELECT_BY_ID_QUERY = "SELECT * FROM file WHERE file_id = ?";

    private final String SELECT_BY_NAME_QUERY = "SELECT * FROM file WHERE name = ?";

    private final String INSERT_FILE_QUERY = "INSERT INTO file VALUES (?)";

    private final String UPDATE_FILE_QUERY = "UPDATE file SET name = ? WHERE file_id = ?";

    private final String DELETE_FILE_QUERY = "DELETE FROM file WHERE id = ?";


    public JdbcContactFileDao(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public ContactFile getFileById(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ContactFile file = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_BY_NAME_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                long foundId = rs.getLong("file_id");
                String foundName = rs.getString("name");
                file = new ContactFile(foundId, foundName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }

        return file;
    }

    @Override
    public ArrayList<ContactFile> getFilesByName(String name) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<ContactFile> files = new ArrayList<>();
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_BY_NAME_QUERY);
            st.setString(1, name);
            rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("file_id");
                String foundName = rs.getString("name");
                ContactFile file = new ContactFile(id, foundName);
                files.add(file);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }

        return files;
    }

    @Override
    public void update(ContactFile file) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(UPDATE_FILE_QUERY);
            st.setString(1, file.getName());
            st.setLong(1, file.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, null);
        }
    }

    @Override
    public long save(ContactFile file) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        long id = 0;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(INSERT_FILE_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, file.getName());
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next())
                id = rs.getLong(1);

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
            st = cn.prepareStatement(DELETE_FILE_QUERY);
            st.setLong(1, id);
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, null);
        }
    }
}
