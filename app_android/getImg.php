<?php

require_once("bd.php");
$id=$_GET["id"];

$sql="select id_img,image from image,bien where bien.id_bien=image.id_bien AND image.id_bien='$id' group by bien.id_bien;";



//array for JSON response
//format json 

$result=mysqli_query($conn,$sql);
      if (mysqli_num_rows($result)>0)  {
		  
		  $response=array();
		  
		  while($row=mysqli_fetch_assoc($result)){
			  $bien=array();
			  $bien["id_img"]=$row["id_img"];
			  $bien["image"]=$row["image"];
			  array_push ($response,$bien);
		  }
		  echo json_encode ($response);
		  
	  } else {
		  
		  echo "no";
	  }
	  mysqli_close($conn);  
	  
?> 	  