// The creation of an oci_database_db_system requires that it be created with exactly one oci_database_db_home. Therefore the first db home will have to be a property of the db system resource and any further db homes to be added to the db system will have to be added as first class resources using "oci_database_db_home".
resource "oci_database_db_home" "tf_db_home" {
  db_system_id = var.dbsysid
  database {
    admin_password = "BEstr0ng1--"
    db_name        = "tfDb"
    pdb_name       = "tfPdb"
  }
//source       = "NONE"
  db_version   = "19.0.0.0"
  display_name = "Home for ${var.proj_id}"
}