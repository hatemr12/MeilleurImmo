<?php

 require_once("bd.php");


	$id_bien=$_POST['id_bien'];
 	$image=$_POST['image'];
 	//$upload_path="img_uploads/$title.jpg";

 	$sql="INSERT INTO `image`(`id_bien`,`image`) VALUES('$id_bien','$image');";

	$result=mysqli_query($conn,$sql);


 	if ($result) {

 		// file_put_contents($upload_path,base64_decode($image));
 		//echo json_encode(array('response'=>"Image uploaded successfully..."));
 		echo "yes";
 	}
 	else
 	{
 		//echo json_encode(array('response'=>"Image uploaded failed..."));
 		echo "no";
 	}



 	

 	mysqli_close($conn);
 


?>