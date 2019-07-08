package dao;

import entities.Phone;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PhoneDAOImpl extends BaseImpl {
    private static final String ADD_PHONE = "INSERT INTO phone (contact_id, country_code, operator_code, number, type, comment) VALUES (?,?,?,?,?,?)";
    private static final String SELECT_ALL_PHONES_WITH_ID = "SELECT * FROM phone WHERE contact_id = ?";
    private static final String UPDATE_PHONES = "UPDATE phone SET country_code = ?, operator_code = ?, number = ?, type = ?, comment = ? WHERE id = ?";
    private static final String DELETE_PHONE = "DELETE FROM phone WHERE id = ?";
    private static final Logger log = Logger.getLogger(PhoneDAOImpl.class);


    public void addPhones(ArrayList<Phone> phones, int cont_id) throws SQLException {
        log.info("PhoneDAO addPhones enter");
        for(int i = 0; i < phones.size(); i++) {
            PreparedStatement preparedStatement3 = connection.prepareStatement(ADD_PHONE);
            preparedStatement3.setInt(1, cont_id);
            preparedStatement3.setInt(2, phones.get(i).getCountryCode());
            preparedStatement3.setInt(3, phones.get(i).getOperatorCode());
            preparedStatement3.setString(4, phones.get(i).getNumber());
            preparedStatement3.setString(5, phones.get(i).getType());
            preparedStatement3.setString(6, phones.get(i).getComment());
            preparedStatement3.executeUpdate();
        }
        log.info("PhoneDAO addPhones exit");
    }

    public ArrayList<Phone> findByID(int cont_id) throws SQLException {
        log.info("PhoneDAO findByID enter");
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_PHONES_WITH_ID);
        preparedStatement.setInt(1, cont_id);
        ArrayList<Phone> phones = new ArrayList<>();
        try(ResultSet rs3 = preparedStatement.executeQuery()){
            while (rs3.next()){
                Phone phone = new Phone(rs3.getInt(1), rs3.getInt(3), rs3.getInt(4),
                        rs3.getString(5), rs3.getString(6), rs3.getString(7));
                phones.add(phone);
            }
        }
        log.info("PhoneDAO findByID exit");
        return phones;
    }

    public void updatePhones(ArrayList<Phone> phones, int cont_id) throws SQLException {
        log.info("PhoneDAO updatePhones enter");
        ArrayList<Phone> oldPhones = oldPhones(phones, cont_id);//которые нужно удалить
        ArrayList<Phone> newPhones = new ArrayList<>();
        for(int i = 0; i < phones.size(); i++){
            if(phones.get(i).getId() > 0){//тогда обновим
                PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_PHONES);
                preparedStatement.setInt(1, phones.get(i).getCountryCode());
                preparedStatement.setInt(2, phones.get(i).getOperatorCode());
                preparedStatement.setString(3, phones.get(i).getNumber());
                preparedStatement.setString(4, phones.get(i).getType());
                preparedStatement.setString(5, phones.get(i).getComment());
                preparedStatement.setInt(6, phones.get(i).getId());
                preparedStatement.executeUpdate();
            }
            else{//тогда добавим
                newPhones.add(phones.get(i));
            }
        }
        //удалим старые
        deletePhones(oldPhones);
        addPhones(newPhones, cont_id);
        log.info("PhoneDAO updatePhones exit");
    }

    public void deletePhones(ArrayList<Phone> phones) throws SQLException {
        log.info("PhoneDAO deletePhones enter");
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_PHONE);
        for(int i = 0; i < phones.size(); i++) {
            preparedStatement.setInt(1, phones.get(i).getId());
            preparedStatement.executeUpdate();
        }
        log.info("PhoneDAO deletePhones exit");
    }

    private ArrayList<Phone> oldPhones(ArrayList<Phone> phones, int cont_id) throws SQLException {
        ArrayList<Phone> oldPhones = findByID(cont_id);
        for(int i = 0; i < phones.size(); i++){
            for(int j = 0; j < oldPhones.size(); j++){
                if(oldPhones.get(j).getId().equals(phones.get(i).getId())){
                    oldPhones.remove(j);
                    break;
                }
            }
        }
        return oldPhones;
    }
}
