package com.itechart.data.dao;

import com.itechart.data.db.DBResourceManager;
import com.itechart.data.entity.File;
import com.itechart.data.transaction.Transaction;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Aleksandr on 25.03.2017.
 */
public class JdbcFileDao implements IFileDao {
    private Transaction transaction;

    private final String SELECT_BY_ID_QUERY = "SELECT * FROM file WHERE file_storage_id = ?";

    private final String SELECT_BY_NAME_QUERY = "SELECT * FROM file WHERE name = ?";

    private final String INSERT_FILE_QUERY = "INSERT INTO file(name,stored_name, file_storage_id) VALUES (?, ?, ?)";

    private final String UPDATE_FILE_QUERY = "UPDATE file SET name = ?, stored_name = ? WHERE file_id = ?";

    private final String DELETE_FILE_QUERY = "DELETE FROM file WHERE file_id = ?";

    private String DELETE_FILE_STORAGE_BY_FILE_ID_QUERY = "DELETE FROM file_storage WHERE file_storage_id IN (SELECT f.file_storage_id FROM file AS f WHERE f.file_id = ?)";

    private String INSERT_FILE_STORAGE_QUERY = "INSERT INTO file_storage VALUES()";
    private String SELECT_BY_STORAGE_ID_QUERY = "SELECT * FROM file WHERE file_storage_id = ?";

    private String DELETE_FILE_STORAGE_FOR_CONTACT_ID_QUERY = "DELETE FROM file_storage AS f_s WHERE f_s.file_storage_id IN ((SELECT c.file_storage_id FROM contact AS c WHERE c.contact_id = ?)" +
            " OR(SELECT a.file_storage_id FROM attachment AS a WHERE a.contact_id = ?))";
    private String DELETE_FILES_FOR_CONTACT_QUERY = "DELETE FROM file WHERE file.file_storage_id IN ((SELECT c.file_storage_id FROM contact AS c WHERE c.contact_id = ?)" +
            " OR (SELECT a.file_storage FROM attachments AS a WHERE a.contact = ?))";

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
    public ArrayList<File> getFilesByIdInFileStorage(long file_storage_id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        ArrayList<File> files = new ArrayList<>();
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(SELECT_BY_STORAGE_ID_QUERY);
            st.setLong(1, file_storage_id);
            rs = st.executeQuery();
            while (rs.next()) {
                long file_id = rs.getLong("file_id");
                String fileName = rs.getString("name");
                String storedName = rs.getString("stored_name");
                File file = new File(file_id, fileName, storedName);
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
            //insert into file_storage table
            st = cn.prepareStatement(INSERT_FILE_STORAGE_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.executeUpdate();
            rs = st.getGeneratedKeys();
            if (rs.next())
                fileStorageId = rs.getLong(1);
            //insert into file table
            st = cn.prepareStatement(INSERT_FILE_QUERY, Statement.RETURN_GENERATED_KEYS);
            st.setString(1, file.getName());
            st.setString(2, file.getStoredName());
            st.setLong(3, fileStorageId);
            st.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }

        return fileStorageId;
    }

    @Override
    public void delete(long id) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_FILE_QUERY);
            st.setLong(1, id);
            st.executeUpdate();
            //delete corresponding entry in file_storage table
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_FILE_STORAGE_BY_FILE_ID_QUERY);
            st.setLong(1, id);
            st.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }
    }

    @Override
    public void deleteForContact(long contactId) {
        Connection cn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        try {
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_FILE_STORAGE_FOR_CONTACT_ID_QUERY);
            st.setLong(1, contactId);
            st.setLong(2, contactId);
            st.executeUpdate();
            //delete corresponding entry in file_storage table
            cn = transaction.getConnection();
            st = cn.prepareStatement(DELETE_FILES_FOR_CONTACT_QUERY);
            st.setLong(1, contactId);
            st.setLong(2, contactId);
            st.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DBResourceManager.closeResources(cn, st, rs);
        }
    }
}
