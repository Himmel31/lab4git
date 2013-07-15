package edu.sumdu.group5.lab3.dao;


public class DAOFactory {
    private DAO resultDao;

    public Object newInstance(String dao) throws DaoException {
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
