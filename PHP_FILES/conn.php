<?php
// Version 1.4
// removed testing outputs
// Removed close and unused echos
// switched to object oriented design
require_once 'config.php';

class DB_Conn {

  private $conn;

  function __construct() {
    $this->conn = mysqli_connect(host, user, password, db_name) or die ("DB Connection failed");
  }

  public function get_connection() {
    return $this->conn;
  }

}
?>
