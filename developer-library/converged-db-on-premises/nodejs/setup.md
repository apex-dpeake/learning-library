# Oracle Node.js 

## Introduction

This lab walks you through the steps to start the Docker as well as Node.js Retail application .
You can connect to an Node.js running in a Docker container on an Oracle Cloud Compute instance. You can connect the Oracle Database instance using any client you wish. In this lab, you'll connect using Oracle SQLDeveloper

## Before You Begin


**What Do You Need?**

This lab assumes you have completed the following labs:
- Lab 1: Login to Oracle Cloud
- Lab 2: Generate SSH Key
- Lab 3: All scripts for this lab are stored in the /u01/workshop/json folder and are run as the oracle user.
- Lab 4: Environment Setup 
## Step 1: Start Docker and Run Node.js application

1. Start Docker by using below command 
   
    ````
    <copy>
    systemctl start docker
    systemctl status docker

    </copy>
    ````
    
2. Run Script to start the Node-js Application.     Check the directory Name Docker script 
        
    ````
    <copy>
	cd /docker
    </copy>
    ````

3. Check the script is available and run the script by using below command.
   
    ````
    <copy>
	./ApplicationScript.sh
    </copy>
    ````

## Step 2:  Verify Application 
1. Verify the Application in your browser.  Open up a web browser on your PC and visit this Application URL. *http://Public-IP:3000/*

2. Verify the Application API in your browser.  Open up a web browser on your PC and visit this Application URL. *http://Public-IP:3001/*

    ![](./images/env_setup_nodejs.PNG " ") 

## Acknowledgements

- **Authors** - Balasubramanian Ramamoorthy, Arvind Bhope
- **Contributors** - Laxmi Amarappanavar, Kanika Sharma, Venkata Bandaru, Ashish Kumar, Priya Dhuriya, Maniselvan K.
- **Team** - North America Database Specialists.
- **Last Updated By** - Kay Malcolm, Director, Database Product Management, June 2020
- **Expiration Date** - June 2021   

**Issues-**
Please submit an issue on our [issues](https://github.com/oracle/learning-library/issues) page. We review it regularly.
      