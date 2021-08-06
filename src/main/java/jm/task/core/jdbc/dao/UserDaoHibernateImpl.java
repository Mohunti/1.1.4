package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import  static jm.task.core.jdbc.util.Util.getSessionFactory;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    public static Transaction transaction = null;
    public static Session session = null;

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            session.createSQLQuery("CREATE TABLE IF NOT EXISTS users " +
                    "(id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY, " +
                    "name VARCHAR(50) NOT NULL, lastName VARCHAR(50) NOT NULL, " +
                    "age TINYINT NOT NULL)");
            transaction.commit();
            session.close();
        } catch (HibernateException E) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                    System.out.println("Откат удался saveUser");
                } catch (HibernateException e) {
                    System.out.println("Откат не удался saveUser");
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dropUsersTable() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            session.createSQLQuery("DROP TABLE IF EXISTS users").addEntity(User.class).executeUpdate();
            transaction.commit();
            session.close();
        } catch (HibernateException E) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                    System.out.println("Откат удался saveUser");
                } catch (HibernateException e) {
                    System.out.println("Откат не удался saveUser");
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        User user = new User(name, lastName, age);
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            session.save(user);
            transaction.commit();
        } catch (HibernateException E) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                    System.out.println("Откат удался saveUser");
                } catch (HibernateException e) {
                    System.out.println("Откат не удался saveUser");
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void removeUserById(long id) {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            session.createSQLQuery("DELETE FROM users WHERE Id = :id").setParameter("id", id)
                .executeUpdate();
            transaction.commit();
        } catch (HibernateException E) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                    System.out.println("Откат удался saveUser");
                } catch (HibernateException e) {
                    System.out.println("Откат не удался saveUser");
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        session = getSessionFactory().openSession();
        transaction = session.beginTransaction();
        try {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<User> cq = cb.createQuery(User.class);
            Root<User> rootEntry = cq.from(User.class);
            CriteriaQuery<User> all = cq.select(rootEntry);
            TypedQuery<User> allQuery = session.createQuery(all);
            System.out.println(allQuery.getResultList());
        } catch (HibernateException E) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                    System.out.println("Откат удался saveUser");
                } catch (HibernateException e) {
                        System.out.println("Откат не удался saveUser");
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public void cleanUsersTable() {
        try {
            session = getSessionFactory().openSession();
            session.beginTransaction();
            session.createSQLQuery("DELETE FROM users").executeUpdate();
            session.getTransaction().commit();
        } catch (HibernateException E) {
            if (transaction != null) {
                try {
                    transaction.rollback();
                    System.out.println("Откат удался saveUser");
                } catch (HibernateException e) {
                    System.out.println("Откат не удался saveUser");
                }
            }
        } finally {
            try {
                session.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
