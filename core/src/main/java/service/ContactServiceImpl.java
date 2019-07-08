package service;

import command.ErrorType;
import dao.ContactDAOImpl;
import dto.ContactsAndNum;
import dto.EmailHelp;
import entities.Contact;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;
import java.util.ArrayList;

public class ContactServiceImpl {
    private static final Logger log = Logger.getLogger(ContactServiceImpl.class);
    public ErrorType addContact(Contact contact){
        ContactDAOImpl contactDAO = new ContactDAOImpl();
        try {
            contactDAO.setConnection();
            contactDAO.getConnection().setAutoCommit(false);
            log.info("Begin of transaction");
            ErrorType status = contactDAO.addContact(contact);
            contactDAO.getConnection().commit();
            log.info("Transaction is succesful");
            return status;
        }
        catch (Exception e) {
            log.info("Can't add contact " + contact);
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            try {
                log.info("try to rollback addContact");
                contactDAO.getConnection().rollback();
                log.info("Rollback successful!");
                return ErrorType.ROLLBACK;
            } catch (SQLException e1) {
                log.info("Bad rollback");
                StringWriter errors2 = new StringWriter();
                e.printStackTrace(new PrintWriter(errors2));
                log.error(errors2);
                return ErrorType.BAD_ROLLBACK;
            }
        }
        finally {
            contactDAO.closeConnection();
        }
    }

    public ContactsAndNum getContacts(int pageNumber, int contactsPerPage){
        ContactDAOImpl contactDAO = new ContactDAOImpl();
        try{
            contactDAO.setConnection();
            return contactDAO.getContacts(pageNumber, contactsPerPage);
        }
        catch (SQLException e) {
            log.info("Can't get contacts for pageNumber " + pageNumber + " with contactsPerPage = " + contactsPerPage);
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            return null;
        }
        finally {
            contactDAO.closeConnection();
        }
    }

    public ArrayList<Contact> getContactsById(ArrayList<Integer> ids){
        ContactDAOImpl contactDAO = new ContactDAOImpl();
        try{
            contactDAO.setConnection();
            return contactDAO.getContactsById(ids);
        }
        catch (Exception e) {
            log.info("Can't get contacts by ids " + ids);
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            return null;
        }
        finally {
            contactDAO.closeConnection();
        }
    }

    public void deleteContacts(ArrayList<Integer> ids){
        ContactDAOImpl contactDAO = new ContactDAOImpl();
        try{
            contactDAO.setConnection();
            contactDAO.getConnection().setAutoCommit(false);
            log.info("Begin of transaction");
            contactDAO.deleteContacts(ids);
            contactDAO.getConnection().commit();
            log.info("Transaction is succesful");
        }
        catch (SQLException e) {
            log.info("Can't delete contacts by ids " + ids);
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            try {
                log.info("try to rollback deleteContacts");
                contactDAO.getConnection().rollback();
                log.info("Rollback successful!");
            } catch (SQLException e1) {
                log.info("Bad rollback");
                StringWriter errors2 = new StringWriter();
                e.printStackTrace(new PrintWriter(errors2));
                log.error(errors2);
            }
        }
        finally {
            contactDAO.closeConnection();
        }
    }

    public Contact findContactByID(int id){
        ContactDAOImpl contactDAO = new ContactDAOImpl();
        try{
            contactDAO.setConnection();
            return contactDAO.findContactByID(id);
        }
        catch (Exception e) {
            log.info("Can't find contacts by id " + id);
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            return null;
        }
        finally {
            contactDAO.closeConnection();
        }
    }

    public String getStandartPhoto(){
        ContactDAOImpl contactDAO = new ContactDAOImpl();
        try{
            contactDAO.setConnection();
            return contactDAO.getStandartPhoto();
        } catch (IOException e) {
            log.info("Can't get standart photo");
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            return null;
        } finally {
            contactDAO.closeConnection();
        }
    }

    public void sendEmail(EmailHelp emailHelp){
        ContactDAOImpl contactDAO = new ContactDAOImpl();
        try{
            contactDAO.setConnection();
            contactDAO.sendEmail(emailHelp);
        } catch (Exception e) {
            log.info("Can't send the email with parametrs " + emailHelp);
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
        } finally {
            contactDAO.closeConnection();
        }
    }

    public ErrorType updateContact(Contact contact){
        ContactDAOImpl contactDAO = new ContactDAOImpl();
        try{
            contactDAO.setConnection();
            contactDAO.getConnection().setAutoCommit(false);
            log.info("Begin of transaction");
            ErrorType status = contactDAO.updateContact(contact);
            contactDAO.getConnection().commit();
            log.info("Transaction is succesful");
            return status;
        }
        catch (Exception e) {
            log.info("Can't update contact " + contact);
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            try {
                log.info("try to rollback updateContact");
                contactDAO.getConnection().rollback();
                log.info("Rollback successful!");
                return ErrorType.ROLLBACK;
            } catch (SQLException e1) {
                log.info("Bad rollback");
                StringWriter errors2 = new StringWriter();
                e.printStackTrace(new PrintWriter(errors2));
                log.error(errors2);
                return ErrorType.BAD_ROLLBACK;
            }
        }
        finally {
            contactDAO.closeConnection();
        }
    }

    public ArrayList<Integer> searchContacts(Contact contact){
        ContactDAOImpl contactDAO = new ContactDAOImpl();
        try{
            contactDAO.setConnection();
            return contactDAO.searchContacts(contact);
        }
        catch (SQLException e) {
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            return null;
        }
        finally {
            contactDAO.closeConnection();
        }
    }

    public ArrayList<String> getEmails(ArrayList<Integer> ids){
        ContactDAOImpl contactDAO = new ContactDAOImpl();
        try{
            contactDAO.setConnection();
            return contactDAO.getEmails(ids);
        }
        catch (SQLException e) {
            log.info("Can't get emails of contacts by ids " + ids);
            StringWriter errors = new StringWriter();
            e.printStackTrace(new PrintWriter(errors));
            log.error(errors);
            return null;
        }
        finally {
            contactDAO.closeConnection();
        }
    }
}
