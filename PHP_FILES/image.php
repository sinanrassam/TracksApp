<?php
function upload_image($image_type, $name, $image) {
  $json['purpose'] = "upload-image";
  $decodedImage = base64_decode($image);
  delete_image($image_type, $name);
  if (file_put_contents("img/" . $image_type . "/" . $name . ".jpg", $decodedImage) === FALSE) {
    $json['response'] = 'failed';
    $json['reason'] = "Error saving image! to " . "img/" . $image_type . "/" . $name . ".jpg";
  } else {
    $json['response'] = 'successful';
  }
  return $json;
}

function delete_image($image_type, $name) {
  $json['purpose'] = "delete-image";
  $path = "img/" . $image_type . "/" . $name . ".jpg";
  if (file_exists($path)) {
    if (unlink($path)) {
      $json['response'] = 'successful';
    } else {
      $json['response'] = 'failed';
      $json['reason'] = 'Error deleting image';
    }
  } else {
    $json['response'] = 'successful';
  }
  return $json;
}

$type = $_POST['type'];
if(isset($_POST['type'])) {
  if ($type == "upload-image") {
    $json = upload_image($_POST['image_type'], $_POST['name'], $_POST['image']);
    echo json_encode($json);
  } else if ($type == "delete-image") {
    $json = delete_image($_POST['image_type'], $_POST['name']);
    echo json_encode($json);
  }
}

?>
