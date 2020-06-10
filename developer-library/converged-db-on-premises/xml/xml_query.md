# Oracle XML 

## Introduction

 This lab will show you how to retrieve the XML contents.
 There are many ways to retrieve the XML Contents. 

## Before You Begin

This lab assumes you have completed the following labs:
- Lab 1:  Login to Oracle Cloud
- Lab 2:  Generate SSH Key
- Lab 3:  Create Compute instance 
- Lab 4:  Environment setup
- Note :  All scripts for this lab are stored in the /u01/workshop/xml folder and are run as the oracle user.
  
 
## Step 1: Login to PDB: Below are the details
   
  ````
    <copy>

   Username: appxml
   Password: Oracle_4U
   PDB name: pdbjxl
   PORT: 1530
    </copy>
  ````

## Step 2: Retrieving XML documents
   
````
        <copy>
       SELECT Count(*)FROM   purchaseorder p,  XMLTABLE('for $r in /PurchaseOrder return $r' passing object_value) t;    )
        /
       </copy>
````
     
   ![](./images/xml_m1.PNG " ")

## Step 3: Retrieving the content of an XML document using pseudocolumn OBJECT_VALUE

    
  ````
    <copy>
    SELECT t.object_value.getclobval()FROM   purchaseorder t
    WHERE  rownum = 1;  

 
       </copy>
 ````
  ![](./images/xml_query_m2.PNG " ")
  ![](./images/xml_m2.PNG " ")

## Step 4:  Accessing fragments or nodes of an XML document
 
    
  ````
    <copy>
    SELECT Xmlquery('/PurchaseOrder/Reference' passing object_value returning
    content)
    FROM   purchaseorder
    WHERE  ROWNUM<= 5
    /
    
    </copy>
  ````

  ![](./images/xml_m3.PNG " ")

## Step 5:  Accessing text node value

  ````
    <copy>
    SELECT xmlcast(xmlquery('$p/PurchaseOrder/Reference/text()' passing object_value AS "p" returning content) AS varchar2(30))
    FROM   purchaseorder
    WHER ROWNUM<= 5
    /

    </copy>
   ````

   ![](./images/xml_m4.PNG " ")
   ![](./images/xml_query_meth4.PNG " ")


## Step 6: Searching xml document
     
 ````
    <copy>
   SELECT t.object_value.getclobval() FROM   purchaseorder t   WHERE  xmlexists('/PurchaseOrder[Reference/text()=$REFERENCE]' 
   passing    object_value, 'AsniHUNOLD-20141130' AS "REFERENCE" ); )
       /
       
       </copy>
  ````
  
     
    
  ![](./images/xml_query_meth5.PNG " ")
  ![](./images/xml_m5.PNG " ")
        

## Acknowledgements

- **Authors** - Balasubramanian Ramamoorthy, Arvind Bhope
- **Contributors** - Laxmi Amarappanavar, Kanika Sharma, Venkata Bandaru, Ashish Kumar, Priya Dhuriya, Maniselvan K.
- **Team** - North America Database Specialists.
- **Last Updated By** - Kay Malcolm, Director, Database Product Management, June 2020
- **Expiration Date** - June 2021   

**Issues-**
Please submit an issue on our [issues](https://github.com/oracle/learning-library/issues) page. We review it regularly.
  


