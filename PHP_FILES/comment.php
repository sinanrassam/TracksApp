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

	public function createComment($postId, $username, $description)
	{
		$json['purpose'] = 'new comment';
		$sql = "INSERT INTO $this->table (post_id, username, description) VALUES ('$postId', '$username', '$description')";
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

	public function getComments($id)
	{
		$json['purpose'] = 'get comments';
		$query = "Select * from $this->table ";
		if ($id != null) {
			$query .= "WHERE post_id = '$id'";
		}
		$result = mysqli_query($this->conn, $query);
		$number = mysqli_num_rows($result);
		$json['number'] = $number;
		if ($number > 0) {
			$json['response'] = 'successful';
			$comments = array();
			$i = 0;
			while ($row = $result->fetch_assoc()) {
				$comment = array(
					"id" => "$row[id]",
					"post_id" => "$row[post_id]",
					"username" => "$row[username]",
					"description" => "$row[description]"
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
		echo json_encode($json);
		mysqli_close($this->conn);
	}

}

$comment = new Comment();
if(isset($_POST['type'])) {
	$type = $_POST['type'];
	if ($type == "get-comments") {
		$comment->getComments($_POST['post_id']);
	} else if ($type == "new-comment") {
		$comment->createComment($_POST['post_id'], $_POST['username'], $_POST['description']);
	} else if ($type == "delete-post-comments") {
		$comment->deletePostComments($_POST['post_id']);
	}
}


?>
