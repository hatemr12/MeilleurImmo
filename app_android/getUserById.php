<?php

$id=$_GET["id"];

$sql="select * from utilisateur where username='$id'";

require_once("bd.php");

//array for JSON response
//format json 


$result=mysqli_query($conn,$sql);

      if (mysqli_num_rows($result)>0)  {
		  
		  $response=array();
		  while($row=mysqli_fetch_assoc($result)){
			  $utilisateur=array();
			  $utilisateur["nom"]=$row["nom"];
			  $utilisateur["prenom"]=$row["prenom"];
			  $utilisateur["tel"]=$row["tel"];
			  $utilisateur["password"]=$row["password"];
			  array_push ($response,$utilisateur);
		  }
		  echo json_encode ($response);
		  
	  } else {
		  
		  echo "no";
	  }
	  mysqli_close($conn);  
	  
?> 	  