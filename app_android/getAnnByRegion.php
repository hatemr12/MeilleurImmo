<?php

$region = $_GET['region'];
//$sql="select id_bien,prix,titre,date_ajout,nom,prenom,tel from bien,utilisateur order by utilisateur.username;";
$sql="select * from bien,utilisateur where bien.username=utilisateur.username AND bien.region='$region'ORDER BY id_bien DESC;";
require_once("bd.php");

//array for JSON response
//format json 

$result=mysqli_query($conn,$sql);
      if (mysqli_num_rows($result)>0)  {
		  
		  $response=array();
		  
		  while($row=mysqli_fetch_assoc($result)){
			  $bien=array();
			  $bien["id_bien"]=$row["id_bien"];
			  $bien["prix"]=$row["prix"];
			  $bien["titre"]=$row["titre"];
			  $bien["date_ajout"]=$row["date_ajout"];
			  $bien["region"]=$row["region"];
			  $bien["ville"]=$row["ville"]; 
			  $bien["description"]=$row["description"];
			  $bien["nom"]=$row["nom"]; 
			  $bien["prenom"]=$row["prenom"];
		      $bien["tel"]=$row["tel"];
			  $bien["latitude"]=$row["latitude"]; 
			  $bien["longitude"]=$row["longitude"];
			 
	  
			  array_push ($response,$bien);
		  }
		  echo json_encode ($response);
		  
	  } else {
		  
		  echo "no";
	  }
	  mysqli_close($conn);  
	  
?> 	  