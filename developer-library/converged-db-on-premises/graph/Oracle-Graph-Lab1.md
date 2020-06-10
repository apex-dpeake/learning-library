
# Oracle Graph 

## Introduction

This Lab will show case how to query graph using PGQL

## Before You Begin

This lab assumes you have completed the following labs:
- Lab 1:  Login to Oracle Cloud
- Lab 2:  Generate SSH Key
- Lab 3:  Create Compute instance 
- Lab 4:  Environment setup
- Note :  Below steps are pre-configured in the image.
- The Oracle Graph Server and Graph Client must be installed.
- Max string size parameter should be set to extended.
- AL16UTF16 (instead of UTF8) must be specified as the NLS NCHAR CHARACTERSET.
- AL32UTF8 (UTF8) should be the default character set, but AL16UTF16 must be the NLS NCHAR CHARACTERSET.
  

## Step 1: Querying graph using PGQL

Below are some of the examples where we can query against the graph we created using PGQL:

Find the edge labels. We used labels here to tag an edge with a relationship type

````
<copy>
query.accept("select distinct label(e) from oe_sample_graph match ()-[e]->(m)");
</copy>
````

![](./images/g3.png " ") 

## Step 2: Finding vertex label using PGQL
Find the vertex labels. We used labels here to tag a vertex as an entity type.
````
<copy>
query.accept("select distinct label(v) from oe_sample_graph match (v)") ;
</copy>
````
![](./images/g4.png " ") 


## Step 3: Identifying the customers using PGQL
Lets look at some of the examples about customers and their orders. 

**Scenario 1 : Getting count from customer table**

````
<copy>
query.accept("select count(v) from oe_sample_graph match (v:CUSTOMERS)");
</copy>
````

![](./images/g5.png " ") 


**Scenario 2 : Identifying the store using PGQL**

 ````
 <copy>
 query.accept("select s.STORE_NAME from oe_sample_graph match (c:CUSTOMERS)->(o:ORDERS)->(s:STORES) where c.CUSTOMER_ID=202");
 </copy>
 ````
  
![](./images/IMGG11.PNG " ") 


**Scenario 3 : Identifying customer's purchases using PGQL**

````
<copy>
query.accept(
"select o.ORDER_STATUS, op.QUANTITY, p.UNIT_PRICE, p.PRODUCT_NAME from oe_sample_graph match (c)-[co]->(o:ORDERS)-[op]->(p:PRODUCTS) where c.FULL_NAME='Dale Hughes'");
</copy>
````
 
![](./images/IMGG12.PNG)



**Scenario 4 : What did people buy from the Online Store. Return first 50 results.**

 ````
 <copy>
 query.accept(
"select c.FULL_NAME, p.PRODUCT_NAME from oe_sample_graph match (o)-[os:ORDERED_FROM_STORE]->(s:STORES),(c)-[co]->(o:ORDERS)-[op]->(p:PRODUCTS) where s.STORE_ID=1 limit 50");
 </copy>
 ````
  
![](./images/IMGG13.PNG " ") 



**Scenario 5 : Who bought how much of product  with id 19**

````
<copy>
query.accept("select c.FULL_NAME, op.QUANTITY from oe_sample_graph match (c)-[co]->(o:ORDERS)-[op]->(p:PRODUCTS) where p.PRODUCT_ID=19 order by op.QUANTITY desc");
</copy>
````
 
![](./images/IMGG14.PNG)



**Scenario 6 : Which customers bought products that customer 202 bought? Return the first 10 results  that had the most products in common with 202**

````
<copy>
var qStr = 
"select c1.FULL_NAME " + 
"FROM oe_sample_graph " + 
"MATCH (c:CUSTOMERS)->(:ORDERS)-[:ORDER_HAS_PRODUCT]->(p:PRODUCTS)," +
"(c1:CUSTOMERS)->(:ORDERS)-[:ORDER_HAS_PRODUCT]->(p:PRODUCTS) " +
"WHERE c.CUSTOMER_ID=202 " + 
"AND c.CUSTOMER_ID <> c1.CUSTOMER_ID " +
"GROUP BY c1 " + 
"ORDER BY count(DISTINCT p) DESC " + 
"LIMIT 10";

query.accept(qStr);
</copy>
````
 
![](./images/IMGG15.PNG)

- Note : All of the above 5  queries are run against the database tables. Let’s load the graph into memory and perform that same set of PGQL queries against the in-memory graph.

## Acknowledgements
- **Authors** - Balasubramanian Ramamoorthy, Arvind Bhope
- **Contributors** - Laxmi Amarappanavar, Kanika Sharma, Venkata Bandaru, Ashish Kumar, Priya Dhuriya, Maniselvan K.
- **Team** - North America Database Specialists
- **Last Updated By** - Kay Malcolm, Director, Database Product Management, June 2020
- **Expiration Date** - June 2021

**Issues-**
Please submit an issue on our [issues](https://github.com/oracle/learning-library/issues) page. We review it regularly.