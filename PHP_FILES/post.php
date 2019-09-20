<?php
// Version 1.2
include_once 'conn.php';

class Post {

	private $db;
	private $conn;
	private $table;

	function __construct() {
		$this->db = new DB_Conn();
		$this->conn = $this->db->get_connection();
		$this->table = "post";
	}

	public function getPosts($number, $username = null)
	{
		$query = "Select * from $this->table ";
		if ($username != null) {
			$query .= "WHERE username = '$username'";
		}

		$result = mysqli_query($this->conn, $query);
		$number = mysqli_num_rows($result);
		$json['purpose'] = 'get posts';
		$json['number'] = $number;
		if($number > 0) {
			$json['response'] = 'successful';
			$posts = array();
			$i = 0;
			while ($row = $result->fetch_assoc()) {
				$post = array(
					"id" => "$row[id]",
					"title" => "$row[title]",
					"description" => "$row[description]",
					"found" => "$row[found]",
					"username" => "$row[username]",
					"date" => "$row[post_date]",
					"time" => "$row[post_time]"
				);
				$posts[$i] = $post;
				$i++;
				$json['posts'] = $posts;
			}
		} else {
			$json['response'] = 'failed';
		}
		echo json_encode($json);
		mysqli_close($this->conn);
	}

	public function createPost($username, $title, $description)
	{
		$json['purpose'] = 'new post';
		$sql = "INSERT INTO $this->table (username, title, description) VALUES ('$username', '$title', '$description')";
		// Insert the data into the table
		if ($this->conn->query($sql) === TRUE) {
			$json['response'] = 'successful';
		} else {
			$json['response'] = 'failed';
			$json['reason'] = "Post creation failed.<br>Error: ".$mysql_query."<br>".$this->conn->error;
		}
		echo json_encode($json);
		mysqli_close($this->conn);
	}

	public function editPost($id, $title, $description, $found)
	{
		$json['purpose'] = 'edit';
		$sql = "UPDATE $this->table SET title='$title', description='$description', found='$found' WHERE id='$id'";
		if ($this->conn->query($sql) === TRUE) {
			$json['response'] = 'successful';
		} else {
			$json['response'] = 'failed';
			$json['reason'] = "Failed to udpate entry.<br>Error: ".$mysql_query."<br>".$this->conn->error;
		}
		echo json_encode($json);
		mysqli_close($this->conn);
	}

	public function deletePost($id)
	{
		$json['purpose'] = 'delete';
		$sql = "DELETE FROM $this->table WHERE id='$id'";
		if ($this->conn->query($sql) === TRUE) {
			$json['response'] = 'successful';
		} else {
			$json['response'] = 'failed';
			$json['reason'] = "Failed to udpate entry.<br>Error: ".$mysql_query."<br>".$this->conn->error;
		}
		echo json_encode($json);
		mysqli_close($this->conn);
	}
}


$post = new Post();

if(isset($_POST['type'])) {
	$type = $_POST['type'];
	if ($type == "get-posts") {
		$post->getPosts($_POST['number'], $_POST['username']);
	} else if ($type == "new-post") {
		$post->createPost($_POST['username'], $_POST['title'], $_POST['description']);
	} else if ($type == "edit-post") {
		$post->editPost($_POST['id'], $_POST['title'], $_POST['description'], $_POST['found']);
	} else if ($type == "delete-post") {
		$post->deletePost($_POST['id']);
	}
}
?>
