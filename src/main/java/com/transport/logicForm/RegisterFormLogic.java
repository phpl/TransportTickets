package com.transport.logicForm;

import com.transport.DatabaseService;
import com.transport.dao.AddressDao;
import com.transport.dao.PersonalDataDao;
import com.transport.dao.UserDao;
import com.transport.entity.AddressEntity;
import com.transport.entity.PersonalDataEntity;
import com.transport.entity.UserEntity;
import com.transport.view.controllers.ControllerHelper;

import java.sql.SQLException;

public class RegisterFormLogic {
    private DatabaseService databaseService;
    private UserDao userDao;
    private PersonalDataDao personalDataDao;
    private AddressDao addressDao;

    public RegisterFormLogic() {
        databaseService = new DatabaseService();
        userDao = new UserDao(databaseService);
        personalDataDao = new PersonalDataDao(databaseService);
        addressDao = new AddressDao(databaseService);
        databaseService.connectToDatabase();
    }

    public void dispose() {
        databaseService.closeConnection();
    }

    public void addUser(String username,
                        String password,
                        String firstName,
                        String lastName,
                        int phone,
                        String city,
                        String street,
                        String houseNumber) {

        AddressEntity addressEntity = new AddressEntity(city, street, houseNumber);
        if (!userDao.checkIfUserExist(username)) {
            addAddress(addressEntity);
        }

        UserEntity userEntity = new UserEntity(username, password);
        addUser(userEntity);

        PersonalDataEntity personalDataEntity =
                createPersonalDataEntity(addressEntity, username, firstName, lastName, phone);
        addPersonalData(personalDataEntity);
    }

    private void addAddress(AddressEntity addressEntity) {
        databaseService.setAutoCommit(false);
        try {
            addressDao.insertAddress(addressEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
        databaseService.setAutoCommit(true);
    }

    private void addUser(UserEntity userEntity) {
        databaseService.setAutoCommit(false);
        try {
            userDao.insertUser(userEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
        databaseService.setAutoCommit(true);
    }

    private PersonalDataEntity createPersonalDataEntity(AddressEntity addressEntity,
                                                        String username,
                                                        String firstName,
                                                        String lastName,
                                                        int phone) {
        int addressId = addressDao.getAddressId(addressEntity);
        int userId = userDao.getUserId(username);

        return new PersonalDataEntity(userId, firstName, lastName, phone, addressId);
    }

    private void addPersonalData(PersonalDataEntity personalDataEntity) {
        databaseService.setAutoCommit(false);
        try {
            personalDataDao.insertPersonalData(personalDataEntity);
        } catch (SQLException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.errorWhileRecordAdd();
        }
        databaseService.setAutoCommit(true);
    }
}
