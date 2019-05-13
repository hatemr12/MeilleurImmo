<?php

 $id = $_GET['id'];
$sql="select * from bien,utilisateur where bien.username=utilisateur.username AND id_bien=".$id.";";


require_once("bd.php");

//array for JSON response
//format json 

$result=mysqli_query($conn,$sql);
      if (mysqli_num_rows($result)>0)  {
		  
		  $response=array();
		  
		  while($row=mysqli_fetch_assoc($result)){
			  $ann=array();
			  $ann["id_bien"]=$row["id_bien"];
			  $ann["prix"]=$row["prix"];
			  $ann["titre"]=$row["titre"];
			  $ann["date_ajout"]=$row["date_ajout"];
			  $ann["region"]=$row["region"];
			  $ann["ville"]=$row["ville"]; 
			  $ann["description"]=$row["description"];
			  $ann["nom"]=$row["nom"]; 
			  $ann["prenom"]=$row["prenom"];
			  $ann["tel"]=$row["tel"];
			  //$bien["latitude"]=$row["latitude"]; 
			 // $bien["longitude"]=$row["longitude"];
			 
	  
			  array_push ($response,$ann);
		  }
		  echo json_encode ($response);
		  
	  } else {
		  
		  echo "no";
	  }
	  mysqli_close($conn);  
	  
?> 	  