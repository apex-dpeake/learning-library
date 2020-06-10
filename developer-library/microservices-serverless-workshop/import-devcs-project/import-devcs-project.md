# Importing a Developer Cloud Service Project


Once that we have a Kubernetes Cluster, let’s move on to next step which is importing a DevCS that contains all the microservices that we will have to deploy in this Kubernetes Cluster.

## **Step 1**: Import a Developer Cloud Service Project

1. Go to your DevCS instance and in Organization Menu option, under Projects tab, click **Create**:

  ![](./images/image101.png " ")

2. Enter a Name for your Project, for Security select Private and your Preferred Language. Then Click **Next**:

  ![](./images/image102.png " ")

3. In Template Section, select Import Project Option:

  ![](./images/image103.png " ")

4. For Properties section, we will provide you with details to connect to a preconfigured DevCS instance from which you an import the project in this url:

https://github.com/oraclespainpresales/GigisPizzaHOL/tree/master/microservices/Credentials

5. Click on Next:

  ![](./images/image104.png " ")

6. In next section, select the Container named: “DevCS\_Clone\_Wedodevops” and the zip File available with the Project export to be imported. Then Click **Finish**:

  ![](./images/image105.png " ")

7. Then project start importing process:

  ![](./images/image106.png " ")

8. Importing project goes on:

  ![](./images/image107.png " ")

9. Finally you will have project imported:

  ![](./images/image108.png " ")

10. Review different projects source code in Git menu:

  Note: We will work in next sections with all git but db\_management.git and PizzaDeliveryMobileApp.git

  ![](./images/image109.png " ")

11. Now go to Builds Menu option and check different Jobs

  ![](./images/image110.png " ")

12. And click **Pipelines** tab to check different Pipelines

  ![](./images/image111.png " ")

You can proceed to the next lab.

## Acknowledgements
* **Authors** -  Iván Postigo, Jesus Guerra, Carlos Olivares - Oracle Spain SE Team
* **Last Updated By/Date** - Tom McGinn, April 2020

See an issue?  Please open up a request [here](https://github.com/oracle/learning-library/issues). Please include the workshop name and lab in your request.
