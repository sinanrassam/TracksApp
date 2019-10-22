<?php
// version 0.1
include_once 'conn.php';

class Comment {

	private $db;
	private $conn;
	private $table;

	function __construct() {
		$this->db = new DB_Conn();
		$this->conn = $this->db->get_connection();
		$this->table = "comment";
	}

	public function createComment($postId, $username, $description, $parent_id = null)
	{
		$json['purpose'] = 'new comment';
		$sql = "INSERT INTO $this->table (post_id, username, description) VALUES ('$postId', '$username', '$description')";
		// Insert the data into the table
		if ($this->conn->query($sql) === TRUE) {
			$id = $this->conn->insert_id;
			if ($parent_id != null) {
				$sql = "UPDATE $this->table SET parent_id='$parent_id' WHERE id='$id'";
				if ($this->conn->query($sql) === TRUE) {
					$json['response'] = 'successful';
				} else {
					$json['response'] = 'failed';
					$json['reason'] = "Comment creation failed.<br>Error: ".$mysql_query."<br>".$this->conn->error;
				}
			} else {
				$json['response'] = 'successful';
			}
		} else {
			$json['response'] = 'failed';
			$json['reason'] = "Comment creation failed.<br>Error: ".$mysql_query."<br>".$this->conn->error;
		}
		echo json_encode($json);
		mysqli_close($this->conn);
	}

	public function getComments($post_id, $parent_id = null)
	{
		$json['purpose'] = 'get comments';
		$query = "Select * from $this->table WHERE post_id = '$post_id'";
		if ($parent_id != null) {
			$query .= " AND parent_id = '$parent_id'";
		}
		$result = mysqli_query($this->conn, $query);
		$number = mysqli_num_rows($result);
		$json['number'] = $number;
		if ($number > 0) {
			$json['response'] = 'successful';
			$comments = array();
			$i = 0;
			while ($row = $result->fetch_assoc()) {
				$date = date('d/m/Y', strtotime($row['date']));
				$time = date('h:i', strtotime($row['date']));
				$comment = array(
					"id" => "$row[id]",
					"post_id" => "$row[post_id]",
					"username" => "$row[username]",
					"description" => "$row[description]",
					"date" => "$date",
					"time" => "$time"
				);
				$comments[$i] = $comment;
				$i++;
			}
			$json['comments'] = $comments;
		}  else {
			$json['response'] = 'failed';
			$json['reason'] = 'No Comments for this post yet, be the first to comment';
		}
		echo json_encode($json);
		mysqli_close($this->conn);
	}

	public function deletePostComments($post_id) {
		$json['purpose'] = 'delete';
		$sql = "DELETE FROM $this->table WHERE post_id='$post_id'";
		if ($this->conn->query($sql) === TRUE) {
			$json['response'] = 'successful';
		} else {
			$json['response'] = 'failed';
			$json['reason'] = "Failed to udpate entry.<br>Error: ".$mysql_query."<br>".$this->conn->error;
		}
		// echo json_encode($json);
		mysqli_close($this->conn);
	}
}

$comment = new Comment();
if(isset($_POST['type'])) {
	$type = $_POST['type'];
	if ($type == "get-comments") {
		$comment->getComments($_POST['post_id']);
	} else if ($type == "new-comment") {
		$comment->createComment($_POST['post_id'], $_POST['username'], $_POST['description'], $_POST['parent_id']);
	}
}


?>
