#Provider for current region and home region that is needed to create users and groups
data "oci_identity_tenancy" "tenancy" {
  tenancy_id = var.tenancy_ocid
}

data oci_identity_regions regions {
}

locals {
  region_map = {
    for r in data.oci_identity_regions.regions.regions :
    r.key => r.name
  }
  home_region = lookup(
    local.region_map, 
    data.oci_identity_tenancy.tenancy.home_region_key
  )
}

provider "oci" {
  alias            = "home"
  #region           = "us-ashburn-1"
  region           = local.home_region
  tenancy_ocid     = var.tenancy_ocid
  user_ocid        = var.user_ocid
  fingerprint      = var.fingerprint
  private_key_path = var.private_key_path
}

provider "oci" {
  region           = var.region
  tenancy_ocid     = var.tenancy_ocid
  user_ocid        = var.user_ocid
  fingerprint      = var.fingerprint
  private_key_path = var.private_key_path
//retry_duration_seconds = 3600
}

# Token expired at Sat, Apr 10, 2021, 23:38:00 UTC, get new an OSS autho token and documented here
/*
terraform {
  backend "http" {
    update_method = "PUT"
   address       = "https://objectstorage.us-ashburn-1.oraclecloud.com/p/eCgGCyZF-A2Zp5OLRQcOrwQB0U-nMpaDRl6TX-lDx0U/n/orasenatdpltintegration02/b/exacsTfStates/o/terraform.tfstate"
  }
}
*/