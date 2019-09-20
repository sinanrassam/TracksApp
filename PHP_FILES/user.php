<?php
// Version 1.5
// switched to md5 encryption
// added register fucntion

// TODO: we can check user exists before login and register fucntions
include_once 'conn.php';

class User {

  private $db;
  private $conn;
  private $table;

  function __construct() {
    $this->db = new DB_Conn();
    $this->conn = $this->db->get_connection();
    $this->table = "user";
  }

  public function user_exists($username) {
    $sql = "Select * from $this->table where username ='$username'";
    $result = mysqli_query($this->conn, $sql);
    if(mysqli_num_rows($result) > 0) {
      return true;
    } else {
      $sql = "Select * from $this->table where email ='$username'";
      $result = mysqli_query($this->conn, $sql);
      if(mysqli_num_rows($result) > 0) {
        return true;
      } else {
        return false;
      }
    }
  }

  public function login($username, $password)
  {
    $json['purpose'] = 'login';
    $sql = "Select * from $this->table where username ='$username' and password = '$password'";
    $result = mysqli_query($this->conn, $sql);
    if(mysqli_num_rows($result) > 0) {
      $row = mysqli_fetch_assoc($result);
      $userDetails = array(
        "name" => "$row[name]",
        "username" => "$row[username]",
        "email" => "$row[email]"
      );
      $json['response'] = 'successful';
      $json['details'] = $userDetails;
    } else{
      $sql = "Select * from $this->table where email ='$username' and password = '$password'";
      $result = mysqli_query($this->conn, $sql);
      if(mysqli_num_rows($result)>0) {
        $row = mysqli_fetch_assoc($result);
        $userDetails = array(
          "name" => "$row[name]",
          "username" => "$row[username]",
          "email" => "$row[email]"
        );
        $json['response'] = 'successful';
        $json['details'] = $userDetails;
      } else {
        $json['response'] = 'failed';
        $json['reason'] = "Username or password are incorrect";
      }
    }
    echo json_encode($json);
    mysqli_close($this->conn);
  }

  public function register($name, $email, $username, $password)
  {
    $json['purpose'] = 'register';
    $sql = "INSERT INTO $this->table (name, email, username, password) VALUES ('$name', '$email', '$username', '$password')";
    if (!$this->user_exists($email)) {
      if (!$this->user_exists($username)) {
        if ($this->conn->query($sql) === TRUE) {
          $json['response'] = 'successful';
          $userDetails = array(
            "name" => "$name",
            "email" => "$email",
            "username" => "$username",
          );
          $json['details'] = $userDetails;
        } else {
          $json['response'] = 'failed';
          $json['reason'] = 'Account creation failed!';
        }
      } else {
        $json['response'] = 'failed';
        $json['reason'] = 'Username already exists, please use another one!';
      }
    } else {
      $json['response'] = 'failed';
      $json['reason'] = 'Email is already in use, please try to login or reset your password!';
    }
    echo json_encode($json);
    mysqli_close($this->conn);
  }

  public function update($name, $email, $password, $username)
  {
    $json['purpose'] = 'update';
    $sql = "UPDATE $this->table SET name='$name', email='$email', password = '$password' WHERE username='$username'";
    if ($this->conn->query($sql) === TRUE) {
      $json['response'] = 'successful';
      $userDetails = array(
        "name" => "$name",
        "email" => "$email",
        "username" => "$username",
      );
    } else {
      $json['response'] = 'failed';
      $json['reason'] = "Account creation failed. Error: ".$mysql_query." ".$this->conn->error;
    }
    echo json_encode($json);
    mysqli_close($this->conn);
  }
}

$user = new User();
if(isset($_POST['type'], $_POST['username'])) {
  $type = $_POST['type'];
  $username = strtolower($_POST['username']);
  $email = strtolower($_POST['email']);
  $encrypted_password = sha1($_POST['password']);
  if ($type == "login") {
    $user->login($username, $encrypted_password);
  } else if ($type == "register") {
    $name = $_POST['name'];
    $user->register($name, $email, $username, $encrypted_password);
  } else if ($type == "update") {
    $user->update($_POST['name'], $email, $encrypted_password, $username);
  }
} else {
  $json['response'] = 'failed';
  $json['reason'] = 'all fields are required';
  echo json_encode($json);
}

?>
