## Setup a Linux Compute instance

*Notes:*  
 - *This is based on Practice 2 of the [OCI Lab L100](https://oracle.github.io/learning-library/oci-library/L100-LAB/Compute_Services/Compute_HOL.html#practice-2-creating-a-web-server-on-a-compute-instance) for setting up a web server on a compute.*  
 - *Some of the UIs might look a little different than the screen shots included in the instructions.*

This step assumes you have registered for a Cloud account and have cerated the SSH Keys.

### Create a free tier VM

An Oracle Cloud Infrastructure VM compute instance runs on the same hardware as a Bare Metal instance, leveraging the same cloud-optimized hardware, firmware, software stack, and networking infrastructure.

1. Navigate to the **Compute** tab and click **Create Instance**. We will launch a VM instance for this lab.

2. The Create Compute Instance wizard will launch. Set the name of the server to *Graph-Server* (instead of Web-Server as shown in the screenshot). Click on the *Show Shape, Networking, Storage Options* link to expand that area of the page. Choose an Always Free eligible shape (i.e. a Micro VM).
    ![Create step 1](https://oracle.github.io/learning-library/oci-library/L100-LAB/Compute_Services//media/Create1.png)

3. Most of the defaults are perfect for our purposes. However, you will need to scroll down to the Configure Networking area of the page and select the *Assign a public IP address* option.
    ![Create step 2](https://oracle.github.io/learning-library/oci-library/L100-LAB/Compute_Services/media/Create2.png)

    ***NOTE:*** *You need a public IP address so that you can SSH into the running instance later in this lab.*

4. Scroll down to the SSH area of the page. Choose the *id_rsa.pub* SSH key that you created earlier in this lab. Press the *Create* button to create your instance.

    Launching an instance is simple and intuitive with few options to select. The provisioning of the compute instance will complete in less than a minute and the instance state will change from provisioning to running.

5. Once the instance state changes to Running, you can SSH to the Public IP address of the instance.

    ![Create step 3](https://oracle.github.io/learning-library/oci-library/L100-LAB/Compute_Services/media/Create3.png)

6. Add an ingress rule for port 7007 (needed later for the graph server).  
   Using Using the menu, click on **Virtual Cloud Network** and then the VCN you created for this lab.

    ![Click on the VCN](https://oracle.github.io/learning-library/oci-library/L100-LAB/Compute_Services/media/vcn1.png)

   Now click on **Security Lists** on the left navigation bar for the VCN.
    ![Click on Security Lists](https://oracle.github.io/learning-library/oci-library/L100-LAB/Compute_Services/media/vcn2.png)

   Click on the **Default Security List**.

   Here you need to open port 7007. Click on **+ Another Ingress Rule** and add the following values as shown below:

    - **Source Type:** CIDR
    - **Source CIDR**: 0.0.0.0/0
    - **IP Protocol:** TCP
    - **Source Port Range:** All
    - **Destination Port Range:** 7007
    - Click on **Add Ingress Rules** at the bottom.

    **Note: The destination port must be 7007 and *not 80* as shown in the screenshot**
    ![Add Ingress Rule](https://oracle.github.io/learning-library/oci-library/L100-LAB/Compute_Services/media/addIngress1.png)


7. To connect to the instance, you can use `Terminal` if you are using MAC or `Gitbash` if you are using Windows. On your terminal or gitbash enter the following command:

    **Note:** For Oracle Linux VMs, the default username is **opc**

    ```shell
    ssh opc@<public_ip_address>
    ```

    If you have a different path for your SSH key enter the following:

    ```shell
    ssh -i <path_to_private_ssh_key> opc@<public_ip_address>
    ```



### Copy and deploy Graph Server and Client on the Linux Compute

The [Property Graph developer's guide](https://docs.oracle.com/en/database/oracle/oracle-database/20/spgdg/property-graph-overview-spgdg.html#GUID-FF149F69-574D-43B8-B888-4CCD019DAE56) describes the installation and configuration process in greater detail. 

The steps are as follows:
- Create a user (e.g. `oracle`). Set a passwd for that user.
- Create a group named `oraclegraph`.
- Add the user to the group.
- Install JDK8 and JDK11.
- Set the java executable to the one in JDK8.
- Copy the Graph Server and Client 20.2 rpm into the compute instance.
- Install the Graph Server and Client.
- Check if `numactl` is installed. If not install it too.
- Open the firewall for port 7007.
- Copy the ADB wallet zip file into the compute.
- Move it to the `oracle` user's home directory and change the file owner and group.
- Switch to the `oracle` user. Add `JAVA_HOME` and `JAVA11_HOME` to the bash_profile
- Create the `wallets` directory. Unzip the ADB wallet into that directory.

SSH into the compute using the private key you created earlier.

```
ssh -i <private_key> opc@<public_ip_for_compute>
```
  
Create the user, set a password, create the group, add user to group.

```
<copy>
sudo useradd oracle
sudo passwd oracle
sudo groupadd oraclegraph
sudo usermod -a -G oraclegraph oracle
</copy>
```

Install the JDK8 and 11.

```
<copy>
sudo yum install jdk1.8.x86_64 jdk-11.0.5.x86_64 
</copy>
```

Check which is the default java and change it using `alternatives`.

```
alternatives --list 
libnssckbi.so.x86_64	auto	/usr/lib64/pkcs11/p11-kit-trust.so
ld	auto	/usr/bin/ld.bfd
mta	auto	/usr/sbin/sendmail.postfix
cifs-idmap-plugin	auto	/usr/lib64/cifs-utils/cifs_idmap_sss.so
java	auto	/usr/java/jdk-11.0.5/bin/java
javac	auto	/usr/java/jdk1.8.0_251-amd64/bin/javac
```

Use alternatives set java executable to JDK8.

```
sudo alternatives --config java

There are 2 programs which provide 'java'.

  Selection    Command
-----------------------------------------------
*+ 1           /usr/java/jdk-11.0.5/bin/java
   2           /usr/java/jdk1.8.0_251-amd64/jre/bin/java

Enter to keep the current selection[+], or type selection number: 2
```

Check the java version now.
```
java -version
java version "1.8.0_251"
Java(TM) SE Runtime Environment (build 1.8.0_251-b08)
Java HotSpot(TM) 64-Bit Server VM (build 25.251-b08, mixed mode)
```

Next download the Graph Server and Client RPM and upload it and the ADB Wallet to the compute instance.

Click [this link](https://www.oracle.com/database/technologies/spatialandgraph/property-graph-features/graph-server-and-client/graph-server-and-client-downloads.html) to download the graph server and client. login and accept the license terms and downlaod the 20.2 Graph Server RPM.

Copy it to the compute. Let's assume the file (and the ADB Wallet file) are in ~/Downloads.

On your desktop or laptop (i.e. your machine):

```
## replace with specifics for your environment: private_key, location of downloaded rpm, ip for compute
scp -i <private_key> ~/Downloads/oracle-graph-20.2.0.x86_64.rpm opc@<public_ip_for_compute>:/home/opc

## copy the Wallet. Once again modify with correct values for your setup.
scp -i <private_key> <ADB_Wallet_Zip> opc@<public_ip_for_compute>:/home/opc
```

Now go back to the terminal window which is connected (via SSH) to the compute instance as `opc`.

```
## install numactl if it is not already installed
<copy>
sudo yum install numactl
## install the graph server
sudo yum install oracle-graph-20.2.0.x86_64.rpm

## Ignore the error about the `jar` executable. This lab does not use the `war` files.
</copy>
```
**Ignore the error about the `jar` executable. This lab does not use the `war` files.**
You can use `alternatives` or some other means to set the `jar` executable (to the one in JDK8) if you wish.

Move the ADB wallet to the `oracle` user. Modify the commands as appropriate for your environment and execute them as `opc`. 

```
sudo chown oracle ADB_Wallet.zip 
sudo chgrp oraclegraph ADB_Wallet.zip
sudo mv ADB_Wallet.zip /home/oracle

```

Open the firewall for port 7007.

```
<copy>
sudo firewall-cmd --permanent --zone=public --add-port=7007/tcp 
sudo firewall-cmd --reload
</copy>
```

Now `su` to `oracle` and complete the setup.
```

su - oracle

mkdir wallets
mv <ADB_Wallet>.zip /home/oracle/wallets
cd wallets
unzip <ADB_Wallet>.zip

## open .bash_profile in a text editor and add the env vars for JAVA_HOME and JAVA11_HOME  
vi /home/oracle/.bash_profile

## Add these lines in oracle's bash_profile 
JAVA_HOME=/usr/java/jdk1.8.0_251-amd64
JAVA11_HOME=/usr/java/jdk-11.0.5

export JAVA_HOME
export JAVA11_HOME

## save and exit the text editor

## open the tnsnames and get the service name you will use later.
cat /home/oracle/wallets/tnsnames.ora

## you will see something similar to
ADWC1_high = 
    (description=
        (address=
            (https_proxy=proxyhostname)(https_proxy_port=80)(protocol=tcps)(port=1522)
            (host=adwc.example.oraclecloud.com)
        )
         (connect_data=(service_name=adwc1_high.adwc.oraclecloud.com))
         (security=(ssl_server_cert_dn="adwc.example.oraclecloud.com,OU=Oracle BMCS US,O=Oracle Corporation,L=Redwood City,ST=California,C=US"))
 )
```

An entry in tnsnames.ora is of the form  
`<addressname> =`  
&nbsp;&nbsp;`(DESCRIPTION =`  
&nbsp;&nbsp;&nbsp;&nbsp;`(ADDRESS_LIST = `  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`(ADDRESS = (PROTOCOL = TCP)(Host = <hostname>)(Port = <port>)) `  
&nbsp;&nbsp;&nbsp;&nbsp;`) `  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`(CONNECT_DATA =  `  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;`(SERVICE_NAME = <service_name>)  `  
&nbsp;&nbsp;&nbsp;&nbsp;`) `  
&nbsp;&nbsp;`)`  

Note the `addressname`, e.g. `ADWC1_high` that you will use later when connecting to the databases using JDBC.




