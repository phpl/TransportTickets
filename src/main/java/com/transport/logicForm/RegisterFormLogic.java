package com.transport.logicForm;

import com.transport.DatabaseService;
import com.transport.dao.AddressDao;
import com.transport.dao.PersonalDataDao;
import com.transport.dao.UserDao;
import com.transport.entity.AddressEntity;
import com.transport.entity.PersonalDataEntity;
import com.transport.entity.UserEntity;
import com.transport.exceptions.DatabaseException;
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
        databaseService.setAutoCommit(false);

        try {
            validateUserExist(username);
            AddressEntity addressEntity = new AddressEntity(city, street, houseNumber);
            addressDao.insertAddressTransaction(addressEntity);

            UserEntity userEntity = new UserEntity(username, password);
            userDao.insertUserTransaction(userEntity);

            PersonalDataEntity personalDataEntity =
                    createPersonalDataEntity(addressEntity, username, firstName, lastName, phone);
            personalDataDao.insertPersonalDataTransaction(personalDataEntity);

            databaseService.commitTransaction();
        } catch (SQLException | DatabaseException e) {
            e.printStackTrace();
            databaseService.rollbackTransaction();
            ControllerHelper.showErrorAlertMessage(e.getMessage());
        }

        databaseService.setAutoCommit(true);
    }

    private void validateUserExist(String username) throws DatabaseException {
        if (userDao.checkIfUserExist(username)) {
            throw new DatabaseException();
        }
    }

    private PersonalDataEntity createPersonalDataEntity(AddressEntity addressEntity,
                                                        String username,
                                                        String firstName,
                                                        String lastName,
                                                        int phone) throws DatabaseException {
        int addressId = addressDao.getAddressId(addressEntity);
        int userId = userDao.getUserId(username);

        ControllerHelper.validateIds(addressId);
        ControllerHelper.validateIds(userId);

        return new PersonalDataEntity(userId, firstName, lastName, phone, addressId);
    }
}
