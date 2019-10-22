<?php
// Version 1.5
include_once 'conn.php';
include_once 'image.php';

class Post {

	private $db;
	private $conn;
	private $table;

	function __construct() {
		$this->db = new DB_Conn();
		$this->conn = $this->db->get_connection();
		$this->table = "post";
	}

	public function getPosts($number, $username, $id = null, $myPosts, $filters = null)
	{
		$query = "Select * from $this->table ";
		if ($id != null) {
			$query .= "WHERE id = '$id'";
		} else if ($myPosts == "1") {
			$query .= "WHERE username = '$username'";
		}
		$query .= $filters;
		$query .= "ORDER BY id DESC";

		$result = mysqli_query($this->conn, $query);
		$number = mysqli_num_rows($result);
		$json['purpose'] = 'get posts';
		$json['number'] = $number;
		if($number > 0) {
			$json['response'] = 'successful';
			$posts = array();
			$i = 0;
			while ($row = $result->fetch_assoc()) {
				$following = $this->get_follow_post($username, $row['id']);
				$date = date('d/m/Y', strtotime($row['post_date']));
				$time = date('h:i', strtotime($row['post_date']));
				$post = array(
					"id" => "$row[id]",
					"title" => "$row[title]",
					"description" => "$row[description]",
					"found" => "$row[found]",
					"username" => "$row[username]",
					"post_date" => "$row[post_date]",
					"post_date" => "$date",
					"post_time" => "$time",
					"date" => "$row[post_date]",
					"time" => "$row[post_time]",
					"micro_chipped" => "$row[micro_chipped]",
					"following" => "$following",
					"edited" => "$row[edited]",
					"location" => "$row[location]",
					"image_exists" => "$row[image_exists]",
					"stray" => "$row[stray]"
				);
				$posts[$i] = $post;
				$i++;
				$json['posts'] = $posts;
			}
		} else {
			$json['response'] = 'failed';
		}
		mysqli_close($this->conn);
		return $json;
	}

	private function get_follow_post($username, $post_Id) {
		$query = "Select * from following WHERE username = '$username' AND post_id = '$post_Id'";
		$number = mysqli_num_rows(mysqli_query($this->conn, $query));
		return $number;
	}

	public function createPost($username, $title, $description, $image, $location, $stray) {
		$json['purpose'] = 'new-post';
		$image_exists = ($image != null)? "1" : "0";
		$sql = "INSERT INTO $this->table (username, title, description, location, image_exists, stray) VALUES ('$username', '$title', '$description', '$location', '$image_exists', '$stray')";
		// Insert the data into the table
		if ($this->conn->query($sql) === TRUE) {
			$json['response'] = 'successful';
			if ($image != null) {
				$id = $this->conn->insert_id;
				$imageJson = upload_image("post", "pic_" . $id, $image);
				if ($imageJson["response"] != "successful") {
					$json['response'] = 'failed';
					$json['reason'] = "Post added but " . $imageJson["reason"];
				}
			}
		} else {
			$json['response'] = 'failed';
			$json['reason'] = "Post creation failed.<br>Error: ".$mysql_query."<br>".$this->conn->error;
		}
		mysqli_close($this->conn);
		return $json;
	}

	public function editPost($id, $title, $description, $found, $location, $image, $stray)
	{
		$json['purpose'] = 'edit-post';
		$edited=1;
		$sql = "UPDATE $this->table SET title='$title', description='$description', found='$found', edited='$edited', location='$location', stray='$stray' WHERE id='$id'";
		if ($this->conn->query($sql) === TRUE) {
			if ($image != null) {
				$imageJson = upload_image("post", "pic_" . $id, $image);
				if ($imageJson["response"] == "successful") {
					$json['response'] = 'successful';
					$sql = "UPDATE $this->table SET image_exists='1' WHERE id='$id'";
					$this->conn->query($sql);
				} else {
					$json['response'] = 'failed';
					$json['reason'] = "Post edited but " . $imageJson["reason"];
				}
			} else {
				$imageJson = delete_image("post", "pic_" . $id);
				$sql = "UPDATE $this->table SET image_exists='0' WHERE id='$id'";
				$this->conn->query($sql);
				$json['response'] = 'successful';
			}
		} else {
			$json['response'] = 'failed';
			$json['reason'] = "Failed to udpate entry.<br>Error: ".$mysql_query."<br>".$this->conn->error;
		}
		mysqli_close($this->conn);
		return $json;
	}

	public function deletePost($id)
	{
		$sql = "DELETE FROM $this->table WHERE id='$id'";
		if ($this->conn->query($sql) === TRUE) {
			$json['response'] = 'successful';
			include_once 'comment.php';
			$comment = new Comment();
			$comment->deletePostComments($id);
			$imageJson = delete_image("post", "pic_" . $id);
			if ($imageJson["response"] == "successful") {
				$json['response'] = 'successful';
			} else {
				$json['response'] = 'failed';
				$json['reason'] = "Post deleted but " . $imageJson["reason"];
			}
		} else {
			$json['response'] = 'failed';
			$json['reason'] = "Failed to udpate entry.<br>Error: ".$mysql_query."<br>".$this->conn->error;
		}
		mysqli_close($this->conn);
		return $json;
	}
}

$post = new Post();

if(isset($_POST['type'])) {
	$_POST['stray'] = 0;
	$type = $_POST['type'];
	if ($type == "get-posts") {
		$json = $post->getPosts($_POST['number'], $_POST['username'], $_POST['id'], $_POST['myPosts']);
	} else if ($type == "new-post") {
		$json = $post->createPost($_POST['username'], $_POST['title'], $_POST['description'], $_POST['image'], $_POST['location'], $_POST['stray']);
	} else if ($type == "edit-post") {
		$json = $post->editPost($_POST['id'], $_POST['title'], $_POST['description'], $_POST['found'], $_POST['location'], $_POST['image']);
	} else if ($type == "delete-post") {
		$json = $post->deletePost($_POST['id']);
	}
} else {
	$json['response'] = 'failed';
	$json['reason'] = "Some fields are missing!";
}
echo json_encode($json);
?>
