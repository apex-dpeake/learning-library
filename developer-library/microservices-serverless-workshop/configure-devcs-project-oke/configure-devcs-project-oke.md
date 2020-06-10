# Configure the Project to Match the Kubernetes Cluster

## Before You Begin

What we have to do now is to adapt parameters and code in project we have just imported to fit with our OKE deployment in your OCI tenancy.

Before we have to create DNS Zones in OCI. A zone is a portion of the DNS namespace. A Start of Authority record (SOA) defines a zone. A zone contains all labels underneath itself in the tree, unless otherwise specified.

## **Step 1**: Create DNS Zone

1. So let’s create a couple of DNS Zones. These will be used later to modify DNSZONE parameter in project. In OCI Dashboard Menu go to: **Networking \> DNS Zone Management**

  ![](./images/image112.png " ")

2. If not selected yet, select Compartment we created in List Scope Area. Then, click **Create Zone**:

  ![](./images/image113.png " ")

3. And create a Manual Zone of type Primary named `hol5967` (for example ) and your username.com:

  ```
  hol5967-carlos.j.olivares.com
  ```

4. Then click **Submit**:

  ![](./images/image114.png " ")

  ![](./images/image115.png " ")

5. You have to create a second DNS zone with same parameters but named like previous one prefixed with `front-`:

  ```
  front-hol5967-carlos.j.olivares.com
  ```

6. You should have a DNS Zone Management like this:

  ![](./images/image116.png " ")

Now let’s go back to DevCS instance and let’s configure Build Jobs and Git.

## **Step 2**: Configuring Builds

In this project we have three types of builds, one for Fn Function (Serverless) deployment, other 4 for docker build jobs and finally 4 others for OKE build jobs that will deploy previously generated docker images in OKE cluster.

### Fn Function Jobs modification

1. In DevCs interface, Click **Build Menu**, then select the Job named `fn_discount_to_FaaS_CK`. Then click **Configure** (from the right side of screen):

  ![](./images/image117.png " ")

2. The screen will appear and will take you to Software tab where you have to select a Software template. Make sure you select `Vm_basic_Template_FN` so that Fn function build process will work:

  ![](./images/image118.png " ")

3. Now click ![](./images/image119.png), then in Git tab and make sure that `discount-func.git` is selected as Repository

  ![](./images/image120.png " ")

4. Then Select Parameter and add your tenancy parameters:

  ![](./images/image121.png " ")

5. Finally select Steps tab and enter details with my tenancy details:

  Remember that for Docker Login you have to enter as user:

  ```
  <object storage namespace>/<OCI tenancy user>
  ```

6. And password is:

  ```
  Authtoken for OCI Tenancy user
  ```

7. Also for Fn OCI section our Passphrase is empty. This has to be reflected as two single quotation marks:

  ```
  ''
  ```

8. Finally check Unix Shell and modify it accordingly with your tenancy details:

  ![](./images/image122.png " ")

  ![](./images/image123.png " ")

  Please don’t forget to click Save button.

### Docker Jobs modification

1. Now let’s change the 4 Docker Jobs. Go back to Builds menu and select the Job named:

  `front_order_docker_create`

3. Then click **Configure**:

  ![](./images/image124.png " ")

4. The screen will appear and will take you to Software tab where you have to select a Software template. Make sure you select `Vm_basic_Template` so that microservices and docker build process will work:

  ![](./images/image125.png " ")

5. Now click ![](./images/image119.png), then the Git tab and make sure that `gigis-order-front.git` is selected as Repository

  ![](./images/image126.png " ")

6. Then select Steps tab and enter details with my tenancy details:
From:

  ![](./images/image127.png " ")

  To your tenancy details:

7. To check the name of your region identifier go to the table in this url:

  [https://docs.cloud.oracle.com/iaas/Content/General/Concepts/regions.htm](https://docs.cloud.oracle.com/iaas/Content/General/Concepts/regions.htm)

8. Remember that for Docker Login you have to enter as user:

  ```
  <object storage namespace>/<OCI tenancy user>
  ```

  And password is:

  ```
  Authtoken for OCI Tenancy user
  ```

  ![](./images/image128.png " ")

  ![](./images/image129.png " ")

9. And click **Save**

10. Change the three other docker Jobs in the same way:

  ```
    - ms_orchestrator_docker_create
    - ms_order_docker_create
    - ms_payment_docker_create
  ```

### OKE Jobs modification

1. Now let’s change the 4 Docker Jobs. Go back to Builds menu and select the Job named:
  ```
  Front_order_to_OKE
  ```

2. Then click **Configure**:

  ![](./images/image130.png " ")

3. The screen will appear and will take you to Software tab where you have to select a Software template. Make sure you select `Vm_basic_Template` so that microservices and docker build process will work:

  ![](./images/image131.png " ")

4. Now click ![](./images/image119.png), then the Git tab and make sure that `gigis_order_front.git` is selected as Repository:

  ![](./images/image132.png " ")

5. Click **Parameters** tab and change Parameters from:

  ![](./images/image133.png " ")

  Changed to (your tenancy details):

6. Leave demozone as it is (default)

  Important Note: for DNSZONE in this service select value with DNS Zone previously created with front- as prefix. For the three other OKE Jobs to modify later, select the DNS Zone name created without -front prefix.

  ![](./images/image134.png " ")

7. Click **Steps** tab and Change steps from:

  ![](./images/image135.png " ")

8. Changed to (your tenancy details):

  ![](./images/image136.png " ")

9. And Click **Save**

10. Important Note: modify the three other docker Jobs in the same way as
previous job:

  - ms\_orchestrator\_to\_OKE    
      - VM Template: VM\_Basic\_Template    
      - Git Repo: microservice\_orchestrator.git

  - ms\_order\_to\_OKE    
      - VM Template: VM\_Basic\_Template    
      - Git Repo: gigis-order-front.git

  - ms\_payment\_to\_OKE:    
      - VM Template: VM\_Basic\_Template    
      - Git Repo: microservice-payment.git

## **Step 3**: Configuring Git repositories

Now let’s change the yaml in different GIT repositories to fit with your Tenancy details (review all but `db\_management.git`, `discount-func.git` and `PizzaDeliveryMobileapp.git`).

1. Let’s get started by selecting `microservice_orchestrator.git`:

  ![](./images/image137.png " ")

2. Select only `microservice-orchestrator.yaml` (the two other .yaml don’t require to be modified):

  ![](./images/image138.png " ")

3. Now change references in yaml from:

  ![](./images/image139.png " ")

4. To your tenancy details in line 34 by clicking in edit button . Don’t forget to commit changes:

  ![](./images/image140.png " ")

5. Important Note: modify the three other .yaml files in the same way in each git:

  ![](./images/image141.png " ")

6. Now it is time to manually launch the build process, but before we have to do a couple of things:

    - Create the application for the Fn function in OCI
    - Create a policy so that the Fn function Managed Service(FaaS) can manage all the resources in the tenancy

7. Let’s start creating the application for the Fn function in OCI. Go back to OCI Dashboard console and go to: **Developer Services \> Functions**:

  ![](./images/image142.png " ")

8. There select your compartment:

  ![](./images/image143.png " ")

9. Click **Create Application**:

  ![](./images/image144.png " ")

10. Important Note: So that we don’t have to modify source code, the application name must be: `gigis-fn`. Also remember to add the three subnets.

  ![](./images/image145.png " ")

11. And click **Create**

## **Step 4**: Create Policy
1. Now let’s create the policy above mentioned. In OCI Console Menu go to: **Identity \> Policies**:

  ![](./images/image146.png " ")

2. If not selected yet, select root Compartment in List Scope Area and click **Create Policy**:

  ![](./images/image147.png " ")

3. Fill in Name: `Fn_Tenancy_Policy_Resources` add a Description, this statement below:

  ```
  <copy>allow service FaaS to manage all-resources in tenancy</copy>
  ```

  ![](./images/image148.png " ")

4. Click **Create**:

  ![](./images/image149.png " ")

  A new policy is created.

## **Step 5**: Run the Build Process
1. Now let’s run the build process to check if all the changes have been done correctly. Go back to DevCS Dashboard and select Builds menu. There select Pipelines tab:

  ![](./images/image150.png " ")

2. Then in `gigispizza_CD` pipeline click **Build** so that build process starts: ![](./images/image151.png)

  ![](./images/image152.png " ")

3. Check parameters are correct and click **Build Now**. Shortly afterwards The build process will start and you will have to wait for executor to start (an executor is one of the VM build servers that you previously configured):

  ![](./images/image153.png " ")

4. Once an executor is assigned, you will have to check progress:

  ![](./images/image154.png " ")

5. If job ends successfully you will see an screen like this:

  ![](./images/image155.png " ")

  Note: If not successful, Click **View Recent Build History** and check what Job failed.

6. Now let’s launch the second pipeline named `gigispizza_front` in the same way, check parameters in popup window and click **Build now**:

  ![](./images/image156.png " ")

7. If everything goes fine you should see an screen like this:

  ![](./images/image157.png " ")

8. Finally go back to jobs tab, select the one named `fn_discount_to_FaaS_CK` and manually launch the build process for the
Fn Function service by clicking **Build Now**:

  ![](./images/image158.png " ")

9. Check that deployment is successful:

  ![](./images/image159.png " ")

  You can proceed to the next lab.

## Acknowledgements
* **Authors** -  Iván Postigo, Jesus Guerra, Carlos Olivares - Oracle Spain SE Team
* **Last Updated By/Date** - Tom McGinn, April 2020

See an issue?  Please open up a request [here](https://github.com/oracle/learning-library/issues). Please include the workshop name and lab in your request.
