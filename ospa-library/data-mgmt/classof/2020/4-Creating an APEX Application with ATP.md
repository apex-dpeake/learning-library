
![](./media/labs.jpg)

# Creating an Application Express Application with ATP

## Table of Contents

- [Module 1: Provisioning the Workspace](#module-1--provisioning-the-workspace)
- [Module 2: Creating an APEX Application](#module-2--creating-an-apex-application)


*****

There are times where your customer has a spreadsheet that they pass around and want everyone to update but there are issues with everyone locking the spreadsheet. Worse yet, who has the current copy. Wouldn't it be great to load the spreadsheet quickly into a database and throw a quick screen over it for people to input their data. Maybe they have departments that need to stand up a quick application to do something but don't want to go through a full application lifecycle process or don't have the skillset to code it. This lab will look at how someone with little to no code experience can stand up a quick application.

## Module 1:  Provisioning the Workspace

In this module we will create a workspace within Application Express using the user apex_app we created earlier.

1. You will need to be on the tools tab of your Autonomous Database details page as shown below.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

2. Click on Open APEX which will launch the Administration Services page if this is your first time logging into APEX.
**NOTE**- If this is not your first time logging into APEX it will bring up a login for your APEX application. There will be a link at the bottom of the login page that says administration. Click that link to get to this screen.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

3. Login with the ADMIN account you created when you initially created the ATP instance.

4. Either click on the Create Workspace button or go to the Manage Workspaces drop down and click Create Workspace.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

8. For the Database User click on the list button and select the database user you created earlier called "apex_app". Since we are not creating a new user you don't have to specify the password. You only specify a password if you want APEX to create a new database user. The workspace name will automatically get populated with apex_app. Click on create workspace.


9. After you create the workspace you will need to log into the workspace as the apex_app user to create the app. You will notice at the top of the page a notification saying to do this. Click on the apex_app hyperlink to do this.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

10. Login with the password you assigned to the apex_app user when you created it in sql developer web.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

11. When you log in you will set a button to Set APEX Account Password. Click on this button.

12. Fill in your email address and then scroll down and set a password to use when launching your application. Click on Apply Changes.

You are now logged into your workspace and ready to start creating your application!

![](media/noimage.png)
<p align="center">Figure 2-4</p>

[Back to Top](#table-of-contents)

## Module 2:  Creating an APEX Application

In the previous module you created a workspace. In this module you will create an APEX application. The application will be based on the sales.csv. It will have a data grid to allow data entry/editing, a data lookup, a report, and a dashboard.

1. Download the file called **sales.csv** ([from here](https://oradocs-corp.documents.us2.oraclecloud.com/documents/link/LD67BF60471BB8666A167A50F6C3FF17C1177A968060/fileview/D58165A8FC2BAB77F65D05CEF6C3FF17C1177A968060/_channels.csv)) Please note the local directory you save it in as you will need it in the next step. See screenshots on how to download and save the file.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

![](media/noimage.png)
<p align="center">Figure 2-4</p>

![](media/noimage.png)
<p align="center">Figure 2-4</p>

2. You should be on the main page of your workspace below. If not then launch APEX and log back into your workspace.

![](media/noimage.png)
<p align="center">Figure 2-4</p>


3. Click on App Builder.

![](media/noimage.png)
<p align="center">Figure 2-4</p>


4. Click on Create a New App.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

5. There are several ways to create a new application. Since we have the sales.csv we will choose From a File.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

6. Click on Choose File and navigate to where you saved the sales.csv and choose that to start the wizard.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

9. On the Load screen we will choose:
- Load To: New Table (We don't have to precreate the table)
- Table Owner: APEX_APP
- Table Name: SALES
- Error Table Name: (This is automatically filled in)
- Primary Keys: Identity Column
- Use Column Data Types (Make sure it's checked)
- First line contains headers (Make sure it's checked)
- Column Delimiter: ,
- Enclosed By: "
- File Encoding: Unicode UTF-8

![](media/noimage.png)
<p align="center">Figure 2-4</p>

10. The rest of the screen should be the data from the file. The columns should have the names from the file and the data should line up correctly. If everything looks ok click on Load Data.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

11. You should see a screen once it's done loading that says how many rows were loaded with a button that says Create Application. Click Create Application.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

12. Create a name for you application. There should be a standard set of pages listed. By default we will get a dashboard, search, report, and calendar. Under features click on Check all to include those features. Then scrool to the bottom and click on Create Application.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

13. Once it's done creating your application you should end up on a screen like this.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

14. Take a look at what you have so far by clicking on Run Application.

15. Login with the user apex_app and the password you created when you setup the workspace.

16. Take some time and explore your application. Click through the Dashboard, Sales Search, Sales Report, and Calendar. When you are finished go to the next step and we will add the data grid and look at how to fix one of the pages.

17. Click on the Dashboard.

18. If you look at the Units Sold graph closely some of the numbers don't really make sense. Hover over the 100. Group 100 has a value of 2. Remember, APEX doesn't know anything about your data, it was just trying to pull things together it thought might make sense. We will look at where to find the SQL that populates this graph and how to fix it as well as add our data grid.

19. At the bottom click on Application 100 to get back to your Workspace.

20. Down at the bottom you should see all of your pages. Let first start with fixing the Dashboard. Click on Dashboard.

21. This will bring up the Page Designer for the Dashboard. On the left you will see areas for the breadcrumbs, content body and other areas. In the content body are the various graphs that we care about, specifically the Units Sold graph. Click on Series 1 under Units Sold on the left side.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

22. Over on the right side of the screen you will see a box that says SQL Query. This is where you would define the query against the table to get the data for the graph. As you can see it's selecting UNITS_SOLD and doing a count. What would make more sense would be to select the product name and then a sum of the units sold. Take the SQL statement below and paste it into the window.

```
select product, sum(units_sold) total_sold
from SALES
group by product
order by 2 desc
```
![](media/noimage.png)
<p align="center">Figure 2-4</p>

23. After you paste the SQL statement in you will get an error message on Label. That is because we changed the name of one of the columns in the SQL statement and it no longer matches. So make the following changes:
- Series Name: Product
- Label: Product
- Value: Total_Sold

![](media/noimage.png)
<p align="center">Figure 2-4</p>

24. Click on Save and Run button in the upper right.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

25. Now go back to the Dashboard and look at the graph. Later if you want to you can look at the other graphs and adjust them if you want. Now let's add the data grid.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

26. Click on Application 100 at the bottom.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

27. Click on Create Page

![](media/noimage.png)
<p align="center">Figure 2-4</p>

28. Select Form

![](media/noimage.png)
<p align="center">Figure 2-4</p>

29. Select Editable Interactive Grid

![](media/noimage.png)
<p align="center">Figure 2-4</p>

30. Name the page "Data Grid" and leave the other options as default. Click Next.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

31. Click on "Create a new navigation menu entry". The default Entry name will be "Data Grid" which is fine and do not select a parent. If you do select a parent what will happen is it will nest the page under another page. Click Next.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

32. Select the following:
- Data Source: Local Database
- Editing Enabled: On
- Source Type: Table
- Table/View Owner: APEX_APP
- Table/View Name: SALES (click on the list and choose it)

Open up the column list and remove the ID column. It's important to do this because the ID column is a generated field and you don't want someone to edit this.

![](media/noimage.png)
<p align="center">Figure 2-4</p>

33. Click on Create.

34. Once your page has been created click on the save and run button in the upper right corner.

35. Notice you have a new page added to the left navigation called Data Grid. You can go to one of the records and modify one of the units sold. Maybe change it from 7 to 200. Click on save at the center top of your grid.

36. Click back over to your Dashboard and notice the change in the Dashboard.

37. Take some time and explore your application.

**You have successfully created an application!**

***END OF LAB***

[Back to Top](#table-of-contents)   
