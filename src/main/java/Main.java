import jakarta.persistence.*;
import model.User;

public class Main {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("library");

    public static void main(String[] args) {
        User user = new User();
        user.setUsername("anita");
        user.setEmail("anita@gmail.com");

        persistUser(user);
        detachUser(user);
        user = reattachUser(user);
        deleteUser(user);

        emf.close();
    }

    public static void persistUser(User user) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            em.persist(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static void detachUser(User user) {

        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.detach(user);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    public static User reattachUser(User user) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            user = em.merge(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return user;
    }

    public static void deleteUser(User user) {

        EntityManager em = emf.createEntityManager();
        EntityTransaction transaction = em.getTransaction();

        try {
            transaction.begin();
            user = em.merge(user);
            em.remove(user);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
}
