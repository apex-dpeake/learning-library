# ExaCS Module

## Modules summery

The following TF var need to be setup for your account:

```
export TF_VAR_fingerprint=xx:xx:xx:xx:xx:xx:xx:xx:xx:xx:xx:xx:xx:xx:xx:xx
export TF_VAR_private_key_path=/homenfs/<your_OSC_LDAP_ID>/.oci/oci_api_key.pem
export TF_VAR_user_ocid=ocid1.user.oc1..xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
```

You will need to set the Project ID (proj_id) by editing terrafrom.tfvars in additional to the usual API login info as described above.

1. Create 3 groups, admin, DBA, and ViewOnly.
    * source = "./modules/idcs/group"
    * The viewOnly group also allows upload of file to object storage so that we don't need to assign DBA admin group for lift and shift database.
2. Give DBA or admin authority to list of users to the compartment
    * source = "./modules/idcs/dbaGroup"
    * source = "./modules/idcs/adminGroup"
3. Create a list of user.
    * source = "./modules/idcs/user"
    * *Note:* the user will be assigned to viewOnly group for the compartment.

## Examples:

See main.tf for examples.

The following is the recommended practice to grant/upgrade authority of user:

1. ID will only have viewonly access to the compartment initially.
2. Once the ID owner confirm that they have login and change the initial password, you can add them to groups with more authority, e.g. DBA, by copy/pasting the OCID of the ID to the group OCIDs list, e.g. dbaOCIDs in terraform.tfvars, then run terraform apply.
3. You will probably only need to grant admin authority for person need to create other instance such as VCN, compute, Data Safe Private Endpoint, etc.

## Instructor/Administrator
For the instructor or admin person who setup the environment intially, you will also need to get the OCID of the following in terraform.tfvars:
* tenancy_ocid
* compartment_ocid
* region
* dbsysid
    1. Login to OCI UI/console.
    2. Select hamburger manual on the left top.
    3. Select "Bare Metal, VM, and Exadata"
    4. Click on the link the the DB System.
    5. Select Copy of the OCID in the General Information section.
    6. Edit terraform.tfvars and paste and replace the OCID, then save. The OCID should look something like ocid1.dbsystem.oc1.iad.xxx where xxx is a long hex string.

### Build initial environment

You need to run the following:
1. Initialize Terraform run time environment.
    * terraform init
2. See what terraform needs to do to build the environment.
    * terraform plan
3. Check to make sure it is not destroying anything you don't want
    * terraform plan \-no\-color \| egrep '^\[ \]\*\-'
4. Run one of the following command to build the environment in the cloud.
    - Interactively, **note:** make sure you don't interrupt or shutdown you computer and it will take about 30 minutes to an hour to build one database so prepare to leave you computer on for n hours where n is the number of database that need to be provision and remember to answer yes to confirm.
        * terraform apply
    - Run remote on an OL instance in the cloud.
        * nohup terraform apply -auto-approve -no-color &
        * tail -f nohup.out
5. Upload/push the terraform.tfstate to the repos so that participant will have the current statfile. Alternatively you can continue to the next section to use remote statefile instead.
    - **Note:** you may need to update .gitignore to remove terraform.tfstate so that git push will upload the file.

### Setup Remote State

When working with Terraform in a team, use of a local file makes Terraform usage complicated because each user must make sure they always have the latest state data before running Terraform and make sure that nobody else runs Terraform at the same time.

With **remote state**, **Terraform** writes the **state** data to a **remote** data store, which can then be shared between all members of a team. **Terraform** supports storing **state** in [**Terraform** Cloud](https://www.hashicorp.com/products/terraform/), HashiCorp Consul, Amazon S3, [**OCI OSS**](https://docs.cloud.oracle.com/en-us/iaas/Content/Object/Concepts/objectstorageoverview.htm), and more. **Remote state** is a feature of backends.

The following is the code template to be added to provider.tf.

```
# Get new OSS autho token and documented here
# e.g. Token expired at Sat, Apr 10, 2021, 23:38:00 UTC
terraform {
  backend "http" {
    update_method = "PUT"
   address       = ""
  }
}
```
1. Edit the file provider.tf.
2. Clipboard Copy the terraform backend template code from the above and append/paste it to the end of the file.
    - **Note:** leave the editor open as you need to paste the OSS token to the address variable/parameter later.
3. Login to OCI UI/console.
4. Select hamburger manual on the left top.
5. Select "Object Storage" then "Object Storage"
6. Click "Create Bucket" button near the top
7. Enter the Bucket Name, e.g. tfStatebucket, the select "Create Bucket" button on bottom
8. Click on the link to the newly created bucket.
9. Click on Upload Objects, then upload the statefile generated from the last section.
10. Back to the bucket Details page, click on the 3 dots on the right of the uploaded object to expand the submenu.
11. Select "Create Pre-Authenticated Request".
12. You may select a differ expiration date.
13. Note down or copy/paste the expiration of the token and document it in the comment of the provider so that you or other know when you need a new token
14. Click on the "Create Pre-Authenticated Request" button at the bottom.
15. Select Copy to copy the token to the clipboard.
    - **Note:** this is the only chance to get the token out otherwise, you have to create another one. The token should look something like "https://objectstorage.us-ashburn-1.oraclecloud.com/.....".
16. Paste the token string between the two double quoate (") for variable/parameter "address" in the provider.tf 
17. Save the file.
18. Exit the editor.