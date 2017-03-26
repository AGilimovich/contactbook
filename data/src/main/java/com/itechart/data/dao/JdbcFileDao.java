package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.entity.File;
import com.itechart.data.transaction.Transaction;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class JdbcFileDao implements IContactFileDao {
    private Transaction transaction;

    private final String SELECT_BY_ID_QUERY = "SELECT * FROM file WHERE file_storage_id = ?";

    private final String SELECT_BY_NAME_QUERY = "SELECT * FROM file WHERE name = ?";

    private final String INSERT_FILE_QUERY = "INSERT INTO file(name,stored_name, file_storage_id) VALUES (?, ?, ?)";

    private final String UPDATE_FILE_QUERY = "UPDATE file SET name = ?, stored_name = ? WHERE file_id = ?";

    private final String DELETE_FILE_QUERY = "DELETE FROM file WHERE file_id = ?";

    private String DELETE_FILE_STORAGE_QUERY = "DELETE FROM file_storage WHERE file_storage_id IN (SELECT f.file_storage_id FROM file AS f WHERE f.file_id = ?)";
    private String INSERT_FILE_STORAGE_QUERY = "INSERT INTO file_storage VALUES()";


    public JdbcFileDao(Transaction transaction) {
        this.transaction = transaction;
    }

    @Override
    public File getFileById(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        File file = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_BY_ID_QUERY);
            st.setLong(1, id);
            rs = st.executeQuery();
            while (rs.next()) {
                long foundId = rs.getLong("file_id");
                String fileName = rs.getString("name");
                String storedName = rs.getString("stored_name");
                file = new File(foundId, fileName, storedName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }

        return file;
    }

    @Override
    public ArrayList<File> getFilesByName(String name) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<File> files = new ArrayList<>();
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_BY_NAME_QUERY);
            st.setString(1, name);
            rs = st.executeQuery();
            while (rs.next()) {
                long id = rs.getLong("file_id");
                String fileName = rs.getString("name");
                String storedName = rs.getString("stored_name");
                File file = new File(id, fileName, storedName);
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
    public void update(File file) {
        Connection cn = null;
        PreparedStatement st = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(UPDATE_FILE_QUERY);
            st.setString(1, file.getName());
            st.setString(2, file.getStoredName());
            st.setLong(3, file.getId());
            st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, null);
        }
    }

    @Override
    public long save(File file) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        long id = 0, fileStorageId = 0;
        try {
            cn = transaction.getConnection();
            //insert file_storage entry
            st = cn.prepareStatement(INSERT_FILE_STORAGE_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next())
                fileStorageId = rs.getLong(1);

            st = cn.prepareStatement(INSERT_FILE_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, file.getName());
            st.setString(2, file.getStoredName());
            st.setLong(3, fileStorageId);
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
        ResultSet rs = null;
        try {

            st = cn.prepareStatement(DELETE_FILE_QUERY);
            st.setLong(1, id);
            st.executeUpdate();
            //delete corresponding entry in file_storage table
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_FILE_STORAGE_QUERY, Connection.TRANSACTION_READ_UNCOMMITTED);
            st.setLong(1, id);
            st.executeUpdate();



        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }
    }
}
