package dao;

import command.ErrorType;
import dto.ContactInfoForAutoPosting;
import dto.ContactsAndNum;
import dto.EmailHelp;
import entities.Contact;
import org.apache.log4j.Logger;
import org.stringtemplate.v4.ST;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Properties;

public class ContactDAOImpl extends BaseImpl {
    private Properties mainProperties = new Properties();
    private InputStream mainPropStream = ContactDAOImpl.class.getClassLoader().getResourceAsStream("main.properties");
    private Properties mailProperties = new Properties();
    private InputStream mailPropStream = ContactDAOImpl.class.getClassLoader().getResourceAsStream("mail.properties");
    private static final String SELECT_ALL_CONTACT = "SELECT * FROM contact ORDER BY id LIMIT ? OFFSET ?";
    private static final String SELECT_CONTACT_BY_ID = "SELECT * FROM contact WHERE id = ?";
    private static final String ADD_NEW_CONTACT = "INSERT INTO contact (name, surname, fathers_name, birthdate, sex, citizenship, family_Status, web_site, email, current_workspace, photo) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
    private static final String FIND_CONTACT_ID = "SELECT id FROM contact WHERE web_site = ?";
    private static final String DELETE_CONTACT = "DELETE FROM contact WHERE id = ?";
    private String FIND_CONTACT_BY_FIELDS = "SELECT contact.id FROM contact INNER JOIN address ON contact.id = address.contact_id";
    private static final String GET_EMAIL_BY_ID = "SELECT email FROM contact WHERE id = ?";
    private static final String UPDATE_CONTACT_BY_ID = "UPDATE contact SET name = ?, surname = ?, fathers_name = ?, birthdate = ?, sex = ?, citizenship = ?, family_Status = ?, web_site = ?, email = ?, current_workspace = ?, photo = ? WHERE id = ?";
    private static final String GET_CONTACTS_NUM = "SELECT COUNT(*) FROM contact";
    private static final String GET_ALL_EMAILS_AND_SITES = "SELECT id, email, web_site FROM contact";
    private static final String GET_PHOTO_OF_CONTACT = "SELECT photo FROM contact WHERE id = ?";
    private static final String GET_NAME_SNAME_FNAME_EMAIL_BY_ID = "SELECT name, surname, fathers_name, email FROM contact WHERE id = ?";
    private static final String GET_CONTACTS_BY_CURRENT_DATE = "SELECT name, surname, fathers_name, birthdate, email FROM contact";
    private static final Logger log = Logger.getLogger(ContactDAOImpl.class);

    public ErrorType addContact(Contact contact) throws SQLException, IOException {
        log.info("ContactDAO addContact enter");
        mainProperties.load(mainPropStream);
        if(checkForExist(contact.getId(), contact.getEmail(), contact.getWebSite())){
            log.error("Contact with such email or web-site is already exists");
            return ErrorType.CONTACT_EXIST;
        }
        PreparedStatement preparedStatement = connection.prepareStatement(ADD_NEW_CONTACT);
        preparedStatement.setString(1, contact.getName());
        preparedStatement.setString(2, contact.getSurName());
        preparedStatement.setString(3, contact.getFathersName());
        preparedStatement.setString(4, contact.getBirthdate().replace("-",""));
        preparedStatement.setString(5, contact.getSex());
        preparedStatement.setString(6, contact.getCitizenship());
        preparedStatement.setString(7, contact.getFamilyStatus());
        preparedStatement.setString(8, contact.getWebSite());
        preparedStatement.setString(9, contact.getEmail());
        preparedStatement.setString(10, contact.getCurrentWorkspase());
        if(contact.getPhoto() != null){
            String img = contact.getPhoto().split(",")[1];
            byte[] mas = Base64.getDecoder().decode(img);
            OutputStream os = new FileOutputStream(new File(mainProperties.getProperty("pictures.path")+contact.getId()+".png"));
            os.write(mas);
            os.flush();
            os.close();
            preparedStatement.setString(11, contact.getId()+".png");
        }
        else{
            preparedStatement.setString(11, null);
        }
        preparedStatement.executeUpdate();
        //------------------------------------------------------------------------------------------
        int cont_id = 0;
        PreparedStatement preparedStatement1 = connection.prepareStatement(FIND_CONTACT_ID);
        preparedStatement1.setString(1, contact.getWebSite());
        try(ResultSet resultSet = preparedStatement1.executeQuery()){
            if(resultSet.next()){
                cont_id = resultSet.getInt(1);
            }
        }
        //------------------------------------------------------------------------------------------
        AddressDAOImpl addressDAO = new AddressDAOImpl();
        PhoneDAOImpl phoneDAO = new PhoneDAOImpl();
        phoneDAO.setCurrentConnection(this.getConnection());
        phoneDAO.addPhones(contact.getPhones(), cont_id);
        addressDAO.setCurrentConnection(this.getConnection());
        addressDAO.addAddress(contact.getAddress(), cont_id);
        AttachmentDAOImpl attachmentDAO = new AttachmentDAOImpl();
        attachmentDAO.setCurrentConnection(this.getConnection());
        attachmentDAO.addAttachments(contact.getAttachments(), cont_id);
        log.info("ContactDAO addContact exit");
        return ErrorType.SUCCESS;
    }

    public int getContactsNum() throws SQLException {
        log.info("ContactDAO getContactsNum enter");
        int i = 0;
        try(ResultSet resultSet = connection.prepareStatement(GET_CONTACTS_NUM).executeQuery()){
            if(resultSet.next()){
                i = resultSet.getInt(1);
            }
        }
        log.info("ContactDAO getContactsNum exit");
        return i;
    }

    public void sendEmail(String email, String subject, String text) throws IOException, MessagingException {
        mailProperties.load(mailPropStream);
        Session session = Session.getDefaultInstance(mailProperties);
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailProperties.getProperty("mail.smtp.user")));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        message.setSubject(subject, "UTF-8");
        message.setText(text, "UTF-8");
        Transport transport = session.getTransport("smtp");
        transport.connect(mailProperties.getProperty("mail.smtp.host"), mailProperties.getProperty("mail.smtp.user"), mailProperties.getProperty("mail.smtp.password"));
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

    public void sendEmail(EmailHelp emailHelp) throws SQLException, IOException, MessagingException {
        ST stringTemplate = null;
        for (int i = 0; i < emailHelp.getIds().size(); i++) {
            String name = null, surname = null, fname = null, email = null;
            PreparedStatement preparedStatement = connection.prepareStatement(GET_NAME_SNAME_FNAME_EMAIL_BY_ID);
            preparedStatement.setInt(1, emailHelp.getIds().get(i));
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    name = resultSet.getString(1);
                    surname = resultSet.getString(2);
                    fname = resultSet.getString(3);
                    email = resultSet.getString(4);
                }
            }
            stringTemplate = new ST(emailHelp.getMessage());
            stringTemplate.add("name", name);//имя
            stringTemplate.add("surname", surname);//фамилия
            stringTemplate.add("fname", fname);//отчество
            sendEmail(email, emailHelp.getHeader(), stringTemplate.render());
        }
    }

    private boolean checkBirthdate(String checkDate, String now){
        return checkDate.substring(4).equals(now.substring(4));
    }

    public ArrayList<ContactInfoForAutoPosting> getContactsByCurrentDate(LocalDate localDate) throws SQLException {
        ArrayList<ContactInfoForAutoPosting> contacts = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(GET_CONTACTS_BY_CURRENT_DATE);
        try(ResultSet resultSet = preparedStatement.executeQuery()){
            while (resultSet.next()){
                if(checkBirthdate(resultSet.getString(4).replace("-", ""), localDate.toString().replace("-", ""))) {
                    ContactInfoForAutoPosting contact = new ContactInfoForAutoPosting();
                    contact.setName(resultSet.getString(1));
                    contact.setSurName(resultSet.getString(2));
                    contact.setFathersName(resultSet.getString(3));
                    contact.setEmail(resultSet.getString(5));
                    contacts.add(contact);
                }
            }
        }
        return contacts;
    }

    public ArrayList<String> getEmails(ArrayList<Integer> ids) throws SQLException {
        log.info("ContactDAO getEmails enter");
        ArrayList<String> emails = new ArrayList<>();
        for(int i = 0; i < ids.size(); i++){
            PreparedStatement preparedStatement = connection.prepareStatement(GET_EMAIL_BY_ID);
            preparedStatement.setInt(1, ids.get(i));
            try(ResultSet resultSet = preparedStatement.executeQuery()){
                if(resultSet.next()){
                    emails.add(resultSet.getString(1));
                }
            }
        }
        log.info("ContactDAO getEmails exit");
        return emails;
    }

    public ArrayList<Integer> searchContacts(Contact contact) throws SQLException {
        log.info("ContactDAO searchContacts enter");
        if(contact.getName() != null){
            my_concat_like("name", contact.getName());
        }
        if(contact.getSurName() != null){
            my_concat_like("surname", contact.getSurName());
        }
        if(contact.getFathersName() != null){
            my_concat_like("fathers_name", contact.getFathersName());
        }
        if(contact.getBirthdate() != null){
            String birth = contact.getBirthdate().replace("-","").replace(".","");
            if(!FIND_CONTACT_BY_FIELDS.equals("SELECT contact.id FROM contact INNER JOIN address ON contact.id = address.contact_id"))
                FIND_CONTACT_BY_FIELDS += " WHERE ";
            else
                FIND_CONTACT_BY_FIELDS += " AND ";
            if(birth.charAt(birth.length()-1) == '+'){//больше
                birth = birth.replace("+","");
                FIND_CONTACT_BY_FIELDS += "birthdate >= " + birth ;
            }
            else{//меньше
                FIND_CONTACT_BY_FIELDS += "birthdate <= " + birth ;
            }
        }
        if(contact.getSex() != null){
            if(!contact.getSex().equals("all")) {
                my_concat("sex", contact.getSex());
            }
        }
        if(contact.getFamilyStatus() != null){
            if(!contact.getFamilyStatus().equals("all")) {
                my_concat("family_Status", contact.getFamilyStatus());
            }
        }
        if(contact.getCitizenship() != null){
            my_concat_like("citizenship", contact.getCitizenship());
        }
        if(contact.getAddress().getCountry() != null){
            my_concat_like("country", contact.getAddress().getCountry());
        }
        if(contact.getAddress().getCity() != null){
            my_concat_like("city", contact.getAddress().getCity());
        }
        if(contact.getAddress().getStreet() != null){
            my_concat_like("street", contact.getAddress().getStreet());
        }
        if(contact.getAddress().getHomeNum() != null){
            my_concat("home_num", contact.getAddress().getHomeNum());
        }
        if(contact.getAddress().getApartment() != null){
            my_concat("apartment", contact.getAddress().getApartment());
        }
        if(contact.getAddress().getPostcode() != null){
            my_concat("postcode", contact.getAddress().getPostcode());
        }
        ArrayList<Integer> ids = new ArrayList<>();
        log.info("SQL statement for find conatact: " + FIND_CONTACT_BY_FIELDS);
        try(ResultSet rs = connection.createStatement().executeQuery(FIND_CONTACT_BY_FIELDS)){
            while (rs.next()){
                ids.add(rs.getInt(1));
            }
        }
        log.info("ContactDAO searchContacts exit");
        return ids;
    }

    private <T> void my_concat(String col_name, T value){
        if(FIND_CONTACT_BY_FIELDS.equals("SELECT contact.id FROM contact INNER JOIN address ON contact.id = address.contact_id"))
            FIND_CONTACT_BY_FIELDS += " WHERE ";
        else
            FIND_CONTACT_BY_FIELDS += " AND ";
        if(value instanceof String) {
            FIND_CONTACT_BY_FIELDS += col_name + " = '" + value + "'";
        } else {
            FIND_CONTACT_BY_FIELDS += col_name + " = " + value;
        }
    }

    private <T> void my_concat_like(String col_name, T value){
        if(FIND_CONTACT_BY_FIELDS.equals("SELECT contact.id FROM contact INNER JOIN address ON contact.id = address.contact_id"))
            FIND_CONTACT_BY_FIELDS += " WHERE ";
        else
            FIND_CONTACT_BY_FIELDS += " AND ";
        FIND_CONTACT_BY_FIELDS += col_name + " like '" + value + "%'";
    }

    public void deleteContacts(ArrayList<Integer> ids) throws SQLException {
        log.info("ContactDAO deleteContacts enter");
        PreparedStatement preparedStatement = connection.prepareStatement(DELETE_CONTACT);
        for(int i = 0; i < ids.size(); i++) {
            preparedStatement.setInt(1, ids.get(i));
            preparedStatement.executeUpdate();
        }
        log.info("ContactDAO deleteContacts exit");
    }

    public String getStandartPhoto() throws IOException {
        log.info("ContactDAO getStandartPhoto enter");
        mainProperties.load(mainPropStream);
        File file = new File(mainProperties.getProperty("pictures.path")+"0.png");
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] mas = new byte[(int) file.length()];
        fileInputStream.read(mas);
        log.info("ContactDAO getStandartPhoto exit");
        return "data:image/png;base64," + Base64.getEncoder().encodeToString(mas);
    }

    public Contact findContactByID(int id) throws SQLException, IOException {
        log.info("ContactDAO findContactByID enter");
        mainProperties.load(mainPropStream);
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_CONTACT_BY_ID);
        preparedStatement.setInt(1, id);
        Contact contact = new Contact();
        try(ResultSet rs = preparedStatement.executeQuery()) {
            if(rs.next()) {
                int cont_id = rs.getInt(1);
                contact = new Contact(cont_id, rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                        null, null, null);
                //---------------------------------------------------
                File file;
                if(contact.getPhoto() == null){
                    file = new File(mainProperties.getProperty("pictures.path")+"0.png");
                }
                else{
                    file = new File(mainProperties.getProperty("pictures.path")+cont_id+".png");
                }
                FileInputStream fileInputStream = new FileInputStream(file);
                byte[] mas2 = new byte[(int) file.length()];
                fileInputStream.read(mas2);
                contact.setPhoto("data:image/png;base64," + Base64.getEncoder().encodeToString(mas2));
                //---------------------------------------------------
                AddressDAOImpl addressDAO = new AddressDAOImpl();
                try{
                    addressDAO.setConnection();
                    contact.setAddress(addressDAO.findByID(cont_id));
                } finally {
                    addressDAO.closeConnection();
                }
                //---------------------------------------------------
                PhoneDAOImpl phoneDAO = new PhoneDAOImpl();
                try{
                    phoneDAO.setConnection();
                    contact.setPhones(phoneDAO.findByID(cont_id));
                } finally {
                    phoneDAO.closeConnection();
                }
                //---------------------------------------------------
                AttachmentDAOImpl attachmentDAO = new AttachmentDAOImpl();
                try{
                    attachmentDAO.setConnection();
                    contact.setAttachments(attachmentDAO.findByID(cont_id));
                } finally {
                    attachmentDAO.closeConnection();
                }
            }
        }
        log.info("ContactDAO findContactByID exit");
        return contact;
    }

    public ErrorType updateContact(Contact contact) throws SQLException, IOException {
        log.info("ContactDAO updateContact enter");
        mainProperties.load(mainPropStream);
        if(checkForExist(contact.getId(), contact.getEmail(), contact.getWebSite())){
            log.error("Contact with such email or web-site is already exists");
            return ErrorType.CONTACT_EXIST;
        }
        PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_CONTACT_BY_ID);
        preparedStatement.setString(1, contact.getName());
        preparedStatement.setString(2, contact.getSurName());
        preparedStatement.setString(3, contact.getFathersName());
        preparedStatement.setString(4, contact.getBirthdate().replace("-",""));
        preparedStatement.setString(5, contact.getSex());
        preparedStatement.setString(6, contact.getCitizenship());
        preparedStatement.setString(7, contact.getFamilyStatus());
        preparedStatement.setString(8, contact.getWebSite());
        preparedStatement.setString(9, contact.getEmail());
        preparedStatement.setString(10, contact.getCurrentWorkspase());
        preparedStatement.setInt(12, contact.getId());
        if(contact.getPhoto() != null){
            String img = contact.getPhoto().split(",")[1];
            byte[] mas = Base64.getDecoder().decode(img);
            System.out.println(contact.getId());
            OutputStream os = new FileOutputStream(new File(mainProperties.getProperty("pictures.path")+contact.getId()+".png"));
            os.write(mas);
            os.flush();
            os.close();
            preparedStatement.setString(11, contact.getId()+".png");
        }
        else if(checkForPhoto(contact.getId())){//есть фото
            preparedStatement.setString(11, contact.getId()+".png");
        }
        else {
            preparedStatement.setString(11, null);
        }
        preparedStatement.executeUpdate();
        //--------------------------------------------------------------------------------
        AddressDAOImpl addressDAO = new AddressDAOImpl();
        addressDAO.setCurrentConnection(this.getConnection());
        addressDAO.updateAddress(contact);
        PhoneDAOImpl phoneDAO = new PhoneDAOImpl();
        phoneDAO.setCurrentConnection(this.getConnection());
        phoneDAO.updatePhones(contact.getPhones(), contact.getId());
        AttachmentDAOImpl attachmentDAO = new AttachmentDAOImpl();
        attachmentDAO.setCurrentConnection(this.getConnection());
        attachmentDAO.updateAttachments(contact.getAttachments(), contact.getId());
        log.info("ContactDAO updateContact exit");
        return ErrorType.SUCCESS;
    }

    private boolean checkForPhoto(int id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(GET_PHOTO_OF_CONTACT);
        preparedStatement.setInt(1, id);
        try(ResultSet resultSet = preparedStatement.executeQuery()){
            if(resultSet.next()){
                if(resultSet.getString(1) == null)
                    return false;
                else
                    return true;
            }
        }
        return false;
    }

    private boolean checkForExist(int id, String email, String site) throws SQLException {
        try(ResultSet resultSet = connection.prepareStatement(GET_ALL_EMAILS_AND_SITES).executeQuery()){
            while (resultSet.next()){
                if(resultSet.getInt(1) != id && (resultSet.getString(2).equals(email) || resultSet.getString(3).equals(site))){
                    return true;
                }
            }
        }
        return false;
    }

    public ContactsAndNum getContacts(int pageNumber, int contactsPerPage) throws SQLException {
        log.info("ContactDAO getContacts enter");
        ContactsAndNum contactsAndNum = new ContactsAndNum();
        ArrayList<Contact> contacts = new ArrayList<>();
        PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_CONTACT);
        preparedStatement.setInt(1, contactsPerPage);
        preparedStatement.setInt(2, (pageNumber - 1)*contactsPerPage);
        try (ResultSet rs = preparedStatement.executeQuery()) {
            while (rs.next()) {
                int cont_id = rs.getInt(1);
                Contact contact = new Contact(cont_id, rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8),
                        rs.getString(9), rs.getString(10), rs.getString(11), rs.getString(12),
                        null, null, null);
                //---------------------------------------------------
                AddressDAOImpl addressDAO = new AddressDAOImpl();
                try{
                    addressDAO.setConnection();
                    contact.setAddress(addressDAO.findByID(cont_id));
                } finally {
                    addressDAO.closeConnection();
                }
                //---------------------------------------------------
                PhoneDAOImpl phoneDAO = new PhoneDAOImpl();
                try{
                    phoneDAO.setConnection();
                    contact.setPhones(phoneDAO.findByID(cont_id));
                } finally {
                    phoneDAO.closeConnection();
                }
                //---------------------------------------------------
                contacts.add(contact);
            }
        }
        contactsAndNum.setContacts(contacts);
        contactsAndNum.setNum(getContactsNum());
        log.info("ContactDAO getContacts exit");
        return contactsAndNum;
    }

    public ArrayList<Contact> getContactsById(ArrayList<Integer> ids) throws SQLException, IOException {
        log.info("ContactDAO getContactsById enter");
        ArrayList<Contact> contacts = new ArrayList<>();
        for(int i = 0; i < ids.size(); i++){
            contacts.add(findContactByID(ids.get(i)));
        }
        log.info("ContactDAO getContactsById exit");
        return contacts;
    }
}