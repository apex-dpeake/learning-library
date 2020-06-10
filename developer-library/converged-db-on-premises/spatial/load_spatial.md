# Oracle Spatial  

## Introduction

This lab will show how to load Spatial data.

## Before You Begin

This lab assumes you have completed the following labs:
- Lab 1:  Login to Oracle Cloud
- Lab 2:  Generate SSH Key
- Lab 3:  Create Compute instance 
- Lab 4:  Environment setup
  
## Step 1: Create Table Customers,Warehouses and Warehouses_dtp
   
   ````
    <copy>
    CREATE TABLE CUSTOMERS                                             
(
  CUSTOMER_ID NUMBER(6, 0),
  CUST_FIRST_NAME VARCHAR2(20 CHAR),
  CUST_LAST_NAME VARCHAR2(20 CHAR), 
  GENDER VARCHAR2(1 CHAR), 
  CUST_GEO_LOCATION SDO_GEOMETRY, 
  ACCOUNT_MGR_ID NUMBER(6, 0)
);  


CREATE TABLE WAREHOUSES                                           
(
WAREHOUSE_ID NUMBER(3,0), 
WAREHOUSE_NAME VARCHAR2(35 CHAR), 
LOCATION_ID NUMBER(4,0), 
WH_GEO_LOCATION SDO_GEOMETRY); 



CREATE TABLE "WAREHOUSES_DTP" 
(       "WAREHOUSE_ID" NUMBER, 
	"WAREHOUSE_NAME" VARCHAR2(30), 
	"LOCATION_ID" NUMBER, 
	"DRIVE_TIME_MIN" NUMBER, 
	"GEOMETRY" "SDO_GEOMETRY"
);

    </copy>
````
## Step 2 : Add Spatial metadata for the CUSTOMERS and WAREHOUSES tables to the **USER-SDO-GEOM-METADATA** view. 

Each SDO-GEOMETRY column is registered      with a row in   USER-SDO-GEOM-METADATA.

````
    <copy>
 EXECUTE SDO_UTIL.INSERT_SDO_GEOM_METADATA (sys_context('userenv','current_user'), -
 'CUSTOMERS', 'CUST_GEO_LOCATION', -  SDO_DIM_ARRAY(SDO_DIM_ELEMENT('X',-180, 180, 0.05), - SDO_DIM_ELEMENT('Y', -90, 90, 0.05)),-  4326);

EXECUTE SDO_UTIL.INSERT_SDO_GEOM_METADATA (sys_context('userenv','current_user'), -
'WAREHOUSES', 'WH_GEO_LOCATION', - SDO_DIM_ARRAY(SDO_DIM_ELEMENT('X',-180, 180, 0.05), - SDO_DIM_ELEMENT('Y', -90, 90, 0.05)),-  4326);

Insert into user_sdo_geom_metadata values (
'WAREHOUSES_DTP','GEOMETRY',MDSYS.SDO_DIM_ARRAY(MDSYS.SDO_DIM_ELEMENT('X', -180, 180, 0.05), MDSYS.SDO_DIM_ELEMENT('Y', -90, 90, 0.05)),4326);
      </copy>
    
````

     
**Here is a description of the items that were entered:**

-	TABLE-NAME: Name of the table which contains the spatial data.
-	COLUMN-NAME: Name of the SDO-GEOMETRY column which stores the spatial data.
-	 MDSYS.SDO-DIM-ARRAY: Constructor which holds the MDSYS.SDO-DIM-ELEMENT object,which in turn stores the extents of the spatial data  in each dimension (-180.0, 180.0), and a tolerance value (0.05). The tolerance is a round-off error value used by Oracle Spatial, and is in meters for longitude and latitude data. In this example, the tolerance is 5 mm.
-	4326: Spatial reference system id (SRID): a foreign key to an Oracle dictionary table  (MDSYS.CS-SRS) tha  contains all the     supported coordinate systems. It is important to associate your customer's location to a coordinate system. In this example, 4326    corresponds to "Longitude / Latitude (WGS 84).".

## Step 3: Insert Spatial data using spatial functions     
- we use sdo_cs.transform() to convert to our desired coordinate system SRID of 4326, and 
- we use sdo-geom.validate-geometry() to insert only valid geometries.

````
    <copy>
Insert into WAREHOUSES (WAREHOUSE_ID,WAREHOUSE_NAME,LOCATION_ID,WH_GEO_LOCATION) values (1,'Speedway Facility',1400,MDSYS.SDO_GEOMETRY(2001, 4326, MDSYS.SDO_POINT_TYPE(-86.2508, 39.7927, NULL), NULL, NULL));
    </copy>
    
```` 

The elements of the constructor are: 
-	2001: SDO_GTYPE attribute and it is set to 2001 when storing a two-dimensional single point such as a customer's location.
-	4326: This is the spatial reference system ID (SRID): a foreign key to an Oracle dictionary table (MDSYS.CS_SRS) that contains all the supported coordinate systems. It is important to associate your customer's location to a coordinate system. In this example, 4326 corresponds to "Longitude / Latitude (WGS 84)."
-	MDSYS.SDO-POINT-TYPE: This is where you store your longitude and latitude values within the SDO_GEOMETRY constructor. Note that you can store a third value also, but for these tutorials, all the customer data is two-dimensional.
-	NULL, NULL: The last two null values are for storing linestrings, polygons, and geometry collections. For more information on all the fields of the SDO_GEOMETRY object, please refer to the Oracle Spatial Developer's Guide. For this tutorial with point data, these last two fields should be set to NULL.





## Acknowledgements

- **Authors** - Balasubramanian Ramamoorthy, Arvind Bhope
- **Contributors** - Laxmi Amarappanavar, Kanika Sharma, Venkata Bandaru, Ashish Kumar, Priya Dhuriya, Maniselvan K.
- **Team** - North America Database Specialists.
- **Last Updated By** - Kay Malcolm, Director, Database Product Management, June 2020
- **Expiration Date** - June 2021   

**Issues-**
Please submit an issue on our [issues](https://github.com/oracle/learning-library/issues) page. We review it regularly.
      
