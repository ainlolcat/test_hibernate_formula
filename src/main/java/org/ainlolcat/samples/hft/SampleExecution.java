package org.ainlolcat.samples.hft;

import org.ainlolcat.samples.hft.model.Item;
import org.ainlolcat.samples.hft.model.Relation;
import org.ainlolcat.samples.hft.model.RelationPK;
import org.ainlolcat.samples.hft.model.Storage;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.File;
import java.math.BigInteger;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

/**
 * Created by ain on 26.12.2015.
 */
public class SampleExecution {

    private static String SCRIPT_PATH;
    private static int ID_COUNTER = 1;
    protected static SessionFactory factory;
    protected Session session;

    public static void main(String[] args) throws Exception
    {
        SampleExecution execution = new SampleExecution();
        execution.execute();
    }

    BigInteger storageId;

    public void execute() throws Exception {
        try {
            initContext();

            prepareTestObject();

            validate();
        } finally {
            if ( factory!= null && !factory.isClosed())
                factory.close();
        }
    }

    /**
     * Checks if we can load relation by query
     */
    private void validate() {
        openSession();
        Storage result = (Storage) session.load(Storage.class, storageId);
        assertEquals(20, result.getIetms().size());
        assertEquals(19, result.getRelations().size());
        assertEquals(19, result.getExtRelation().size());
        closeSession();
    }

    /**
     * Creates object for test
     * One storage with 20 items and relation between them
     */
    private void prepareTestObject() {
        openSession();
        Storage storage = new Storage(getId(), "Storage#1");
        storage.setIetms(new ArrayList<Item>());
        storage.setRelations(new ArrayList<Relation>());

        Item previous = null;
        for (int i=0; i<20; i++){
            Item item = new Item(getId(), "Item#" + i);
            item.setStorageId(storage.getStorageId());
            storage.getIetms().add(item);
            if (previous != null){
                Relation relation = new Relation();
                relation.setPk(new RelationPK(previous.getItemId(), item.getItemId()));
                relation.setStorageId(storage.getStorageId());
                storage.getRelations().add(relation);
            } else {
                previous = item;
            }
        }
        session.persist(storage);
        storageId = storage.getStorageId();
        closeSession();
    }

    /**
     * Initializes H2 DB with script resources/org/ainlolcat/samples/hft/install_sample_tables.sql
     * Initializes Hibernate SessionFactory
     * @throws URISyntaxException
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    private void initContext() throws URISyntaxException, ClassNotFoundException, SQLException {
        URL initScript = SampleExecution.class.getResource("install_sample_tables.sql");
        URI root = new File("./").toURI();
        SCRIPT_PATH = root.relativize(initScript.toURI()).getPath();
        Class.forName("org.h2.Driver");
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test;INIT=RUNSCRIPT FROM '" + SCRIPT_PATH + "'");
        conn.close();

        Properties properties = new Properties();
        properties.put("hibernate.show_sql", true);
        properties.put("hibernate.format_sql", false);
        properties.put("use_sql_comments", false);
        properties.put("hibernate.cache.use_second_level_cache", false);
        properties.put("hibernate.order_inserts", true);
        properties.put("hibernate.order_updates", true);
        properties.put("hibernate.jdbc.batch_size", 20);
        properties.put("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.put("hibernate.connection.driver_class", "org.h2.Driver");
        properties.put("hibernate.connection.url", "jdbc:h2:~/test");
        //properties.put("hibernate.connection.user", "sa");
        properties.put("hibernate.flushMode", "FLUSH_MANUAL");
        properties.put("hibernate.hbm2ddl.auto", "none");
        properties.put("hibernate.current_session_context_class", "thread");
//		properties.put("hibernate.classloading.use_current_tccl_as_parent", "false");

        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration();
        configuration.addProperties(properties);

        configuration.addAnnotatedClass(Storage.class);
        configuration.addAnnotatedClass(Item.class);
        configuration.addAnnotatedClass(Relation.class);

        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
                configuration.getProperties()).build();

        factory = configuration.buildSessionFactory(serviceRegistry);
    }

    public void openSession(){
        session = factory.getCurrentSession();
        session.getTransaction().begin();
    }

    public void closeSession(){
        if (session.isOpen()) {
            session.getTransaction().commit();
        }
    }

    public BigInteger getId() {
        return BigInteger.valueOf(ID_COUNTER++);
    }
}
