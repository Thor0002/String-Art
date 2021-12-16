package DataBase;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class UserDAO {
	
	public List<User> findByName(String userName) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		Query query = session.createQuery("Select u from " + User.class.getSimpleName() + " u " + "Where u.userName=:userName");
		query.setParameter("userName", userName);
		List<User> l =  query.getResultList();
        tx1.commit();
        session.close();
        return l;
	}
	
	public List<Image> findByImageNameAndUser(String userName, String imageName) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tx1 = session.beginTransaction();
		Query query = session.createQuery("Select i from " + Image.class.getSimpleName() + " i " + "Where i.imageName = :imageName and i.user.userName = :userName");
		query.setParameter("imageName", imageName);
		query.setParameter("userName", userName);
		List<Image> l = query.getResultList();
		tx1.commit();
        session.close();
		return l;
	}
	
    public void saveUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void updateUser(User user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void deleteUser(String userName, String userPassword) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
		Query query = session.createQuery("Delete from " +  User.class.getSimpleName() + " where u.userName=:userName and u.userPassword=:userPassword");
		query.setParameter("userName", userName);
		query.setParameter("userPassword", userPassword);
	    query.executeUpdate();
        tx1.commit();
        session.close();
    }
    
    public void saveImage(Image image) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(image);
        tx1.commit();
        session.close();
    }

    public void updateImage(Image image) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(image);
        tx1.commit();
        session.close();
    }

    public void deleteImage(String userName, String imageName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        Query query = session.createQuery("Select i from " + Image.class.getSimpleName() + " i " + "Where i.imageName = :imageName and i.user.userName = :userName");
		query.setParameter("imageName", imageName);
        query.setParameter("userName", userName);
        List<Image> l = query.getResultList();
	    Image image = l.get(0);
	    session.delete(image);
        tx1.commit();
        session.close();
    }

}
