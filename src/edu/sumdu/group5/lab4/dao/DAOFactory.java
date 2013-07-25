package edu.sumdu.group5.lab4.dao;
import org.apache.log4j.Logger;

public class DAOFactory {
    private DAO resultDao;    
    /** The logger */
    private static final Logger log = Logger.getLogger(DAOFactory.class);
    
    public Object newInstance(String dao) throws DaoException {
        if (log.isDebugEnabled())
            log.debug("Method call");
        if ("jdbcDAO".equals(dao)) {
            resultDao = new JdbcDAO();
        } else if ("ejbDAO".equals(dao)) {
        	resultDao = new EjbDAO();
        }else{
        	throw new DaoException("Undefined DAO: " + dao);
        }
        return resultDao;
    }

}
