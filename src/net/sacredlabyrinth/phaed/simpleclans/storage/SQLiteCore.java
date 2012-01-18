package net.sacredlabyrinth.phaed.simpleclans.storage;

import net.sacredlabyrinth.phaed.simpleclans.SimpleClans;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 *
 * @author cc_madelg
 */
public class SQLiteCore implements DBCore
{
    private Logger log;
    private Connection connection;
    private String dbLocation;
    private String dbName;
    private File file;

    /**
     *
     * @param dbLocation
     */
    public SQLiteCore(String dbLocation)
    {
        this.dbName = "SimpleClans";
        this.dbLocation = dbLocation;
        this.log = SimpleClans.getLog();

        initialize();
    }

    private void initialize()
    {
        if (file == null)
        {
            File dbFolder = new File(dbLocation);

            if (dbName.contains("/") || dbName.contains("\\") || dbName.endsWith(".db"))
            {
                log.severe("The database name can not contain: /, \\, or .db");
                return;
            }
            if (!dbFolder.exists())
            {
                dbFolder.mkdir();
            }

            file = new File(dbFolder.getAbsolutePath() + File.separator + dbName + ".db");
        }

        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
        }
        catch (SQLException ex)
        {
            log.severe("SQLite exception on initialize " + ex);
        }
        catch (ClassNotFoundException ex)
        {
            log.severe("You need the SQLite library " + ex);
        }
    }

    /**
     * @return connection
     */
    public Connection getConnection()
    {
        if (connection == null)
        {
            initialize();
        }

        return connection;
    }

    /**
     * @return whether connection can be established
     */
    public Boolean checkConnection()
    {
        return getConnection() != null;
    }

    /**
     * Close connection
     */
    public void close()
    {
        try
        {
            if (connection != null)
            {
                connection.close();
            }
        }
        catch (Exception e)
        {
            log.severe("Failed to close database connection! " + e.getMessage());
        }
    }

    /**
     * Execute a select statement
     * @param query
     * @return
     */
    public ResultSet select(String query)
    {
        try
        {

            return getConnection().createStatement().executeQuery(query);
        }
        catch (SQLException ex)
        {
            log.severe("Error at SQL Query: " + ex.getMessage());
        }
        return null;
    }

    /**
     * Execute an insert statement
     * @param query
     */
    public void insert(String query)
    {
        try
        {
            getConnection().createStatement().executeQuery(query);
        }
        catch (SQLException ex)
        {
            if (!ex.toString().contains("not return ResultSet"))
            {
                log.severe("Error at SQL INSERT Query: " + ex);
            }
        }
    }

    /**
     * Execute an update statement
     * @param query
     */
    public void update(String query)
    {
        try
        {
            getConnection().createStatement().executeQuery(query);
        }
        catch (SQLException ex)
        {
            if (!ex.toString().contains("not return ResultSet"))
            {
                log.severe("Error at SQL UPDATE Query: " + ex);
            }
        }
    }

    /**
     * Execute a delete statement
     * @param query
     */
    public void delete(String query)
    {
        try
        {
            getConnection().createStatement().executeQuery(query);
        }
        catch (SQLException ex)
        {
            if (!ex.toString().contains("not return ResultSet"))
            {
                log.severe("Error at SQL DELETE Query: " + ex);
            }
        }
    }

    /**
     * Execute a statement
     * @param query
     * @return
     */
    public Boolean execute(String query)
    {
        try
        {
            getConnection().createStatement().execute(query);
            return true;
        }
        catch (SQLException ex)
        {
            log.severe(ex.getMessage());
            return false;
        }
    }

    /**
     * Check whether a table exists
     * @param table
     * @return
     */
    public Boolean existsTable(String table)
    {
        try
        {
            ResultSet tables = getConnection().getMetaData().getTables(null, null, table, null);
            return tables.next();
        }
        catch (SQLException e)
        {
            log.severe("Failed to check if table '" + table + "' exists: " + e.getMessage());
            return false;
        }
    }
}
