package com.itechart.data.dao;

import com.itechart.data.dto.ContactDTO;
import com.itechart.data.entity.Phone;

import java.util.ArrayList;

/**
 * Created by Aleksandr on 13.03.2017.
 */
public interface IPhoneDao {

    ArrayList<Phone> getAllForContact(int id);

    Phone getPhoneById(int id);

    void save(Phone phone);

    void delete(Phone phone);

    void update(Phone phone);

}
