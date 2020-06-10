# Oracle Spatial 

## Introduction
This lab will show how to setup Environment for Spatial Use cases


## Before You Begin

**What Do You Need?**

This lab assumes you have completed the following labs:
- Lab 1:  Login to Oracle Cloud
- Lab 2:  Generate SSH Key
- Lab 3:  Create Compute instance 
- Lab 4:  Environment setup
    
## Step 1: Set your oracle environment and connect to PDB
       
  ````
    <copy>
     . oraenv
     ConvergedCDB
     sqlplus appstat/Oracle_4U@SGRPDB


    </copy>
````

## Step 2: Make a connection to sqldeveloper.Provide the details as below and click on connect.
   
````
    <copy>
	Name: Spatial
    Username: appspat
    Password: Oracle_4U
    Hostname: <machine_IP_address>
    Port: 1521
    Service name: SGRPDB

    </copy>
   ````
 
  ![](./images/spatial_env.PNG " ") 

## Acknowledgements

- **Authors** - Balasubramanian Ramamoorthy, Arvind Bhope
- **Contributors** - Laxmi Amarappanavar, Kanika Sharma, Venkata Bandaru, Ashish Kumar, Priya Dhuriya, Maniselvan K.
- **Team** - North America Database Specialists.
- **Last Updated By** - Kay Malcolm, Director, Database Product Management, June 2020
- **Expiration Date** - June 2021   

**Issues-**
Please submit an issue on our [issues](https://github.com/oracle/learning-library/issues) page. We review it regularly.
      
 
