package dao;

import entities.Address;
import entities.Contact;
import org.apache.log4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressDAOImpl extends BaseImpl {
    private static final String ADD_ADDRESS = "INSERT INTO address (contact_id, country, city, street, home_num, apartment, postcode) VALUES (?,?,?,?,?,?,?)";
    private static final String SELECT_ALL_ADDRESS_WITH_ID = "SELECT * FROM address WHERE contact_id = ?";
    private static final String UPDATE_ADDRESS_WITH_ID = "UPDATE address SET country = ?, city = ?, street = ?, home_num = ?, apartment = ?, postcode = ? WHERE contact_id = ?";
    private static final Logger log = Logger.getLogger(AddressDAOImpl.class);


    public void addAddress(Address address, int cont_id) throws SQLException {
        log.info("AddressDAO addAddress enter");
        PreparedStatement preparedStatement2 = connection.prepareStatement(ADD_ADDRESS);
        preparedStatement2.setInt(1, cont_id);
        preparedStatement2.setString(2, address.getCountry());
        preparedStatement2.setString(3, address.getCity());
        preparedStatement2.setString(4, address.getStreet());
        preparedStatement2.setString(5, address.getHomeNum());
        preparedStatement2.setString(6, address.getApartment());
        preparedStatement2.setString(7, address.getPostcode());
        preparedStatement2.executeUpdate();
        log.info("AddressDAO addAddress exit");
    }

    public Address findByID(int cont_id) throws SQLException {
        log.info("AddressDAO findByID enter");
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_ADDRESS_WITH_ID);
        preparedStatement.setInt(1, cont_id);
        Address address = new Address();
        try(ResultSet rs = preparedStatement.executeQuery()) {
            if(rs.next()) {
                address = new Address(rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8));
            }
        }
        log.info("AddressDAO findByID exit");
        return address;
    }

    public void updateAddress(Contact contact) throws SQLException {
        log.info("AddressDAO updateAddress enter");
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_ADDRESS_WITH_ID);
        preparedStatement.setString(1, contact.getAddress().getCountry());
        preparedStatement.setString(2, contact.getAddress().getCity());
        preparedStatement.setString(3, contact.getAddress().getStreet());
        preparedStatement.setString(4, contact.getAddress().getHomeNum());
        preparedStatement.setString(5, contact.getAddress().getApartment());
        preparedStatement.setString(6, contact.getAddress().getPostcode());
        preparedStatement.setInt(7, contact.getId());
        preparedStatement.executeUpdate();
        log.info("AddressDAO updateAddress exit");
    }
}
