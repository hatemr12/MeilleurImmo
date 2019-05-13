<?php
//acce a la bd
require_once("bd.php");

//récupere les paramétres
//ajouter un nouveau produit

     $nom=$_GET["nom"];
	 $prenom=$_GET["prenom"];
	 $username=$_GET["username"];
	 $tel=$_GET["tel"];
	 $password=$_GET["password"];
	 
	
$sql="INSERT INTO `utilisateur`(`username`,`nom`,`prenom`,`tel`,`password`) VALUES ('$username','$nom','$prenom','$tel','$password');";

   $result=mysqli_query($conn,$sql);
      if ($result)
	{

			echo "yes";

	} 
	else
	 {
     echo "no";
	 }	 

 	mysqli_close($conn);
?>
