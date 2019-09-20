<?php
include_once 'conn.php';

$db = new DB_Conn();
$json['purpose'] = 'test connection';
if ($db->get_connection() != null) {
  $json['response'] = 'successful';
} else {
  $json['response'] = 'failed';
}
echo json_encode($json);
mysqli_close($this->conn);

?>

