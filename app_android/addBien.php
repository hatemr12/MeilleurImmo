<?php
//acce a la bd
require_once("bd.php");

//récupere les paramétres
//ajouter un nouveau bien/annonce
	 $username=$_GET["username"];
     $titre=$_GET["titre"];
	 $prix=$_GET["prix"];
	 $region=$_GET["region"];
	 $ville=$_GET["ville"];
	 $description=$_GET["description"];
	 $latitude=$_GET["latitude"];
	 $longitude=$_GET["longitude"];

	 
	
$sql="INSERT INTO `bien`(`username`,`titre`,`prix`,`date_ajout`,`region`,`ville`,`description`,`latitude`,`longitude`) VALUES ('$username','$titre','$prix',CURRENT_TIMESTAMP,'$region','$ville','$description','$latitude','$longitude');";

   $result=mysqli_query($conn,$sql);
      if ($result)  {

      	$result=mysqli_query($conn,"select MAX(id_bien) as idb from bien;");
  		    if (mysqli_num_rows($result)>0)  {while($row=mysqli_fetch_array($result)){echo $row["idb"];}}
} else {
     echo "no";
}

 	mysqli_close($conn);
	 
?>