<?php
		$name=$_POST["name"];
		$image=$_POST["image"];
		$decimg=base64_decode("$image");
		file_put_contents("images/".$name.".JPG",$decimg);
?>
