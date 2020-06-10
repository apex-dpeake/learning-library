import java.io.File;
import oracle.nosql.driver.NoSQLHandle;
import oracle.nosql.driver.NoSQLHandleConfig;
import oracle.nosql.driver.NoSQLHandleFactory;
import oracle.nosql.driver.Region;
import oracle.nosql.driver.iam.SignatureProvider;
import oracle.nosql.driver.ops.GetRequest;
import oracle.nosql.driver.ops.GetResult;
import oracle.nosql.driver.ops.PutRequest;
import oracle.nosql.driver.ops.PutResult;
import oracle.nosql.driver.ops.TableLimits;
import oracle.nosql.driver.ops.TableRequest;
import oracle.nosql.driver.ops.TableResult;
import oracle.nosql.driver.values.MapValue;

public class HelloWorld {
    /* Name of your table */
    final static String tableName = "HelloWorldTable";

    public static void main(String[] args) throws Exception {
        NoSQLHandle handle = generateNoSQLHandle();
        try {
            createTable(handle);
            writeRows(handle);
            readRows(handle);
            //dropTable(handle);
        } catch (Exception e) {
            System.err.print(e);
        } finally {
            handle.close();
        }
    }

    /* Create a NoSQL handle to access the cloud service */
    private static NoSQLHandle generateNoSQLHandle() throws Exception {

        SignatureProvider ap = new SignatureProvider();

        /* Create a NoSQL handle to access the cloud service */
        NoSQLHandleConfig config = new NoSQLHandleConfig(
			Region.US_ASHBURN_1, ap);
        NoSQLHandle handle = NoSQLHandleFactory.createNoSQLHandle(config);
        return handle;
    }

    /**
     * Create a simple table with an integer key
     * and a single string data field
     * and set your desired table capacity
     */
    private static void createTable(NoSQLHandle handle) throws Exception {
        String createTableDDL = "CREATE TABLE IF NOT EXISTS " +
            tableName + "(employeeid INTEGER, name STRING, " +
            "PRIMARY KEY(employeeid))";

        TableLimits limits = new TableLimits(1, 2, 1);
        TableRequest treq = new TableRequest()
            .setStatement(createTableDDL).setTableLimits(limits);

        System.out.println("Creating table " + tableName);
        TableResult tres = handle.tableRequest(treq);

        /* The request is async,
         * so wait for the table to become active.
        */
        System.out.println("Waiting for "
		    + tableName + " to become active");
        tres.waitForCompletion(handle, 60000, /* wait 60 sec */
            1000); /* delay ms for poll */
        System.out.println("Table " + tableName + " is active");
    }

    /**
     * Make a row in the table and write it
     */
    private static void writeRows(NoSQLHandle handle) throws Exception {
        MapValue value =
            new MapValue().put("employeeid", 1).put("name", "Tracy");
        PutRequest putRequest =
            new PutRequest().setValue(value).setTableName(tableName);
        PutResult putResult = handle.put(putRequest);
        if (putResult.getVersion() != null) {
            System.out.println("Wrote " + value);
        } else {
            System.out.println("Put failed");
        }
    }

    /**
     * Make a key and read the row from the table
     */
    private static void readRows(NoSQLHandle handle) throws Exception {
        MapValue key = new MapValue().put("employeeid", 1);
        GetRequest getRequest =
            new GetRequest().setKey(key).setTableName(tableName);
        GetResult getRes = handle.get(getRequest);
        System.out.println("Read " + getRes.getValue());
    }

    /**
     * Drop the table and wait for the table to move to dropped state
     */
    private static void dropTable(NoSQLHandle handle) throws Exception {
        System.out.println("Dropping table " + tableName);
        TableRequest treq = new TableRequest()
            .setStatement("DROP TABLE IF EXISTS " + tableName);
        TableResult tres = handle.tableRequest(treq);
        System.out.println("Waiting for " + tableName + " to be dropped");
        tres.waitForCompletion(handle, 60000, /* wait 60 sec */
            1000); /* delay ms for poll */
        System.out.println("Table " + tableName + " has been dropped");
    }
}
