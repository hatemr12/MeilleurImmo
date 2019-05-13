<?php
require_once('bd.php');
	    $username=$_GET["username"];
	    $nom=$_GET["nom"];
	    $prenom=$_GET["prenom"];
	    $tel=$_GET["tel"];
	    $password=$_GET["password"];
//$sql = "update `utilisateur` set `nom`='$nom',`prenom`='$prenom',`tel`='$tel',`password`='$password' where `username`=".$username.";";		
$sql = "update `utilisateur` set `nom`='$nom',`prenom`='$prenom',`tel`='$tel',`password`='$password' where `username`='$username';";		

		// array for JSON response
		$result = mysqli_query($conn,$sql);
		
		if ($result) {
	
             echo "yes";
             
     } else {
 
    echo "no";
   
        }		
		mysqli_close($conn);

?>