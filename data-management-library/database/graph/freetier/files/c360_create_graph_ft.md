## Create the Customer_360 graph from the tables

Now that the tables are created and populated let's create a graph represnetation of them.

The steps are:
- Modify the graph server configuration to disable TLS (SSL) for this lab
- Start the graph server
- Start a client (JShell) that connects to the server
- Setup a PGQL connection to the database
- Use PGQL DDL (CREATE PROPERTY GRAPH) to instantiate a graph


### Modify the graph server config file

SSH into the compute instance where you installed the graph server.  
Switch to the user account (e.g. `oracle`) that has the database wallet and will run the server and client instances. 

```
<copy>
su - oracle 
</copy>
```

Then edit the `/etc/oracle/graph/server.conf` file. 
```
<copy>
vi /etc/oracle/graph/server.conf
</copy>
```

Change the line  
` "enable_tls": true,`
to  
` "enable_tls": false,`  
Save the file and exit.

### Start the graph server

Check that JAVA_HOME and JAVA11_HOME env variables are set and correct. That is, JAVA_HOME points to JDK1.8 and Java11_HOME to jdk1.11.  
Then, as the `oracle` user, start the server using 
```
<copy>
/opt/oracle/graph/pgx/bin/start-server
</copy>
```

### Start a client shell 

Once the graph server is up and running, open a new SSH connection to the same compute instance.  
Check that the exploded database wallet is in the compute instance and accessible from the user account which will run the graph client.

Assuming the user is named `oracle` and the wallet is in `/home/oracle/wallets` check that it exists and has the right permissions. 

```
<copy>
su - oracle
ls -l /home/oracle/wallets
</copy>
```

Then start a client shell instance that connects to the server.

```
<copy>
/opt/oracle/graph/bin/opg-jshell --base_url http://localhost:7007
</copy>
```

### Create the graph

Enter the following sets of commands once the JShell has started and is ready.

First setup the database connection. Enter the following into the JShell.  
Replace {db_tns_name} with the appropriate database service name in the tnsnames.ora file of the wallet (e.g. `db_adb_af_high`).  
Replace {wallet_location} with the full path to the directory which has the unzipped wallet (e.g. `/home/oracle/wallets`).

```
// Copy the following line too but replace the placeholders with the correct values
var jdbcUrl = "jdbc:oracle:thin:@{db_tns_name}?TNS_ADMIN={wallet_location}";
<copy>
var user = "customer_360";
var pass = "Welcome1_C360";
var conn = DriverManager.getConnection(jdbcUrl, user, pass) ;
</copy>
// example jshell output
// conn ==> oracle.jdbc.driver.T4CConnection@54d11c70
```

Then create a PGQL connection and set its properties.

```
<copy>
// Get a PGQL connection in order to run PGQL statements
// Set auto commit to false for PGQL
conn.setAutoCommit(false);
var pgql = PgqlConnection.getConnection(conn);
</copy>

// example jshell output
// pgql ==> oracle.pg.rdbms.pgql.PgqlConnection@6493f780
```

Next set up the create property graph statement.

```
<copy>
// create the graph from the existing tables
var cpgStmtStr = "CREATE PROPERTY GRAPH customer_360 " +
 "    VERTEX TABLES (" +
 "     customer " +
 "     PROPERTIES (type, name, age, location, gender, student) " +
 "   , account " +
 "     PROPERTIES (type, account_no, balance) " +
 " , merchant " +
 "     PROPERTIES (type, name) " +
 " ) " +
 " EDGE TABLES ( " +
 "   owned_by " +
 "     SOURCE KEY(from_id) REFERENCES account " +
 "     DESTINATION KEY(to_id) REFERENCES customer " +
 "     LABEL owned_by " +
 "     PROPERTIES (since) " +
 " , parent_of " +
 "     SOURCE KEY(from_id) REFERENCES customer " +
 "     DESTINATION KEY(to_id) REFERENCES customer " +
 "     LABEL parent_of " +
 " , purchased " +
 "     SOURCE KEY(from_id) REFERENCES account " +
 "     DESTINATION KEY(to_id) REFERENCES merchant " +
 "     LABEL purchased " +
 "     PROPERTIES (amount) " +
 " , transfer " +
 "     SOURCE KEY(from_id) REFERENCES account " +
 "     DESTINATION KEY(to_id) REFERENCES account " +
 "     LABEL transfer " +
 "     PROPERTIES (amount, date)" +
 " )" ;
</copy>
// example jshell output
// cpgStmtStr ==> "CREATE PROPERTY GRAPH customer_360     VERTEX TABLES (     customer      PROPERTIES (type, name, age, location, gender, student)    , account      PROPERTIES (type, account_no, balance)  , merchant      PROPERTIES (type, name)  )  EDGE TABLES (    owned_by      SOURCE KEY(from_id) REFERENCES account      DESTINATION KEY(to_id) REFERENCES customer      LABEL owned_by      PROPERTIES (since)  , parent_of      SOURCE KEY(from_id) REFERENCES customer      DESTINATION KEY(to_id) REFERENCES customer      LABEL parent_of  , purchased      SOURCE KEY(from_id) REFERENCES account      DESTINATION KEY(to_id) REFERENCES merchant      LABEL purchased      PROPERTIES (amount)  , transfer      SOURCE KEY(from_id) REFERENCES account      DESTINATION KEY(to_id) REFERENCES account      LABEL transfer      PROPERTIES (amount, date) )"
```

Now execute the PGQL DDL to first DROP any existing graph of the same name before CREATEing it.

```
<copy>

var dropPgStmt = "DROP PROPERTY GRAPH customer_360";
// drop any existing garph
pgql.prepareStatement(dropPgStmt).execute();
// Now create the graph 
pgql.prepareStatement(cpgStmtStr).execute();


<copy>
// example jshell output
// $12 ==> false
```

The create graph process can take 3-4 minutes depending on various factors such as network bandwidth and database load.

### Check the newly created graph

Check that the graph was created. Copy, paste, and run the following statements in the JShell.

Copy the following code, paste, and execute it in the JShell.

```

// create a helper method for preparing, executing, and printing the results of PGQL statements
Consumer<String> query = q -> { try(var s = pgql.prepareStatement(q)) { s.execute(); s.getResultSet().print(); } catch(Exception e) { throw new RuntimeException(e); } }

// sample jshell output
// query ==> $Lambda$583/0x0000000800695c40@65021bb4
```

Then copy, paste, and execute the following.

```
<copy>
// query the graph 

// what are the edge labels i.e. categories of edges
query.accept("select distinct label(e) from customer_360 match ()-[e]->()");

// what are the vertex types i.e. values of the property named "type"
query.accept("select distinct v.type from customer_360 match (v)-[e]->()");

// how many vertices with each type/category
query.accept("select count(distinct v), v.type from customer_360 match (v) group by v.type");

// how many edges with each label/category 
query.accept("select count(e), label(e) from customer_360 match ()-[e]->() group by label(e)");

</copy>
```

Continue on to the next section of this lab, i.e. query and analyse the graph in JShell.