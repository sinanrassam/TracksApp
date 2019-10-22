<?php
include_once 'conn.php';

class Follow {

  private $db;
  private $conn;
  private $table;

  public function __construct() {
    $this->db = new DB_Conn();
    $this->conn = $this->db->get_connection();
    $this->table = "following";
  }

  public function follow_post($username, $post_id) {
    $json['purpose'] = 'follow-post';
    $sql = "SELECT * FROM $this->table WHERE username = '$username' AND post_id = '$post_id'";
    $number = mysqli_num_rows(mysqli_query($this->conn, $sql));
    if ($number > 0) {
      $json['response'] = 'successful';
    } else {
      $sql = "INSERT INTO $this->table (username, post_id) VALUES ('$username', '$post_id')";
      if ($this->conn->query($sql) === TRUE) {
        $json['response'] = 'successful';
      } else {
        $json['response'] = 'failed';
        $json['reason'] = "Post follow failed.<br>Error: ".$mysql_query."<br>".$this->conn->error;
      }
    }
    echo json_encode($json);
    mysqli_close($this->conn);
  }

  public function unfollow_post($username, $post_id) {
    $json['purpose'] = 'unfollow-post';
    $sql = "DELETE FROM $this->table WHERE username='$username' AND post_id='$post_id'";
    if ($this->conn->query($sql) === TRUE) {
      $json['response'] = 'successful';
    } else {
      $json['response'] = 'failed';
      $json['reason'] = "Post follow failed.<br>Error: ".$mysql_query."<br>".$this->conn->error;
    }
    echo json_encode($json);
    mysqli_close($this->conn);
  }
}

$follow = new Follow();

if(isset($_POST['type'])) {
  $type = $_POST['type'];
  if ($type == "follow-post") {
    $follow->follow_post($_POST['username'], $_POST['post_id']);
  } else if ($type == "unfollow-post") {
    $follow->unfollow_post($_POST['username'], $_POST['post_id']);
  }
}
?>
