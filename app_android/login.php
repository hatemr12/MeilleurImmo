<?php


$username=$_GET["username"];
$password=$_GET["password"];
 


$sql="select * from utilisateur where username='$username' and password='$password'";
require_once("bd.php");

//array for JSON response
//format json 


$result=mysqli_query($conn,$sql);

      if (mysqli_num_rows($result)>0)  {
		  
		  while($row=mysqli_fetch_assoc($result)){
			  $client=array();
			  $client["username"]=$row["username"];
			  $client["nom"]=$row["nom"];
			  $client["prenom"]=$row["prenom"];
			  $client["tel"]=$row["tel"];
			  $client["password"]=$row["password"];

			  
			 // array_push ($response,$client);
		  }
		  echo json_encode ($client);
		  
	  } else {
		  
		  echo "no";
	  }
	  mysqli_close($conn);  
	  
?> 	  