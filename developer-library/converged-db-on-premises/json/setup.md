# Oracle JSON 

## Introduction

This lab walks you through the step to setup the environment for JSON lab . You can connect the Oracle Database instance using any client you wish. In this lab, you'll connect using Oracle SQLDeveloper

## Before You Begin


**What Do You Need?**

This lab assumes you have completed the following labs:
- Lab 1:  Login to Oracle Cloud
- Lab 2:  Generate SSH Key
- Lab 3:  Create Compute instance 
- Lab 4:  Environment setup
- Note :  All scripts for this lab are stored in the /u01/workshop/json folder and are run as the oracle user. 
  

## Step 1: Navigate to the path.
   
````
    <copy>
    cd /u01/workshop/json

   </copy>
   ````
    
## Step 2: Set your oracle environment and connect to PDB
       
  ````
    <copy>
     . oraenv
     ConvergedCDB
     sqlplus appjson/Oracle_4U@JXLPDB

    </copy>
````

## Step 3: Make a connection to sqldeveloper.Provide the details as below and click on connect.
   
````
    <copy>
	Name: JSON
    Username: appjson
    Password: Oracle_4U
    Hostname: <machine_IP_address>
    Port: 1521
    Service name: JXLPDB

    </copy>
   ````
 
  ![](./images/env_json.PNG " ") 

## Acknowledgements

- **Authors** - Balasubramanian Ramamoorthy, Arvind Bhope
- **Contributors** - Laxmi Amarappanavar, Kanika Sharma, Venkata Bandaru, Ashish Kumar, Priya Dhuriya, Maniselvan K.
- **Team** - North America Database Specialists.
- **Last Updated By** - Kay Malcolm, Director, Database Product Management, June 2020
- **Expiration Date** - June 2021   

**Issues-**
Please submit an issue on our [issues](https://github.com/oracle/learning-library/issues) page. We review it regularly.
      
 
