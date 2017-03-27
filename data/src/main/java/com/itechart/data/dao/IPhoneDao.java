package com.itechart.data.dao;

import com.itechart.data.entity.Phone;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public interface IPhoneDao {

    ArrayList<Phone> getAllForContact(long id);

    Phone getPhoneById(long id);

    long save(Phone phone);

    void delete(long id);

    void update(Phone phone);

    void deleteForContact(long contactId);

}
