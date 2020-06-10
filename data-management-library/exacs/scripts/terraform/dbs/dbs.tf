// Work around to remember to adjust the timeout 60 min/new DB.
resource "oci_database_database" "test_database" {
  count = var.db_count
  #Required
  database {
    admin_password = "BEstr0ng--${count.index}"
    db_name        = "tfDb${count.index}"
    character_set  = "AL32UTF8"
    ncharacter_set = "AL16UTF16"
    db_workload    = "OLTP"
    pdb_name       = "tfPdb${count.index}"

    db_backup_config {
      auto_backup_enabled = false
    }
  }
  //It takes about 40 min to create 1 DB so make it simple to give an hour per DB
  timeouts {
      create = "${var.db_count}h"
      delete = "${var.db_count}h"
 }

  db_home_id = oci_database_db_home.tf_db_home.id
  source     = "NONE"
}
