<?php
$tns = "LOCALHOST/PROJECTPDB";
$username = "Creator";
$password = "Password";

try{
    $conn = oci_connect($username, $password, $tns);
    if(!$conn){
        $e = oci_error();
        throw new Exception($e['message']);
    }
} catch (Exception $e){
    print_r($e);
}

$fName = $_POST["fName"];
$sName = $_POST["sName"];
$join_date = $_POST["join_date"];
$email = $_POST["email"];
$pNumber = $_POST["pNumber"];

$sql = "INSERT INTO C (CUSTOMER_FNAME, CUSTOMER_SNAME, CUSTOMER_JOIN_DATE, CUSTOMER_EMAIL, CUSTOMER_NUMBER) 
VALUES (:fName, :sName, (TO_DATE(:join_date, 'DD/MM/YYYY')), :email, :pNumber)";

$stmt = oci_parse($conn, $sql);

oci_bind_by_name($stmt, ":fName", $fName);
oci_bind_by_name($stmt, ":sName", $sName);
oci_bind_by_name($stmt, ":join_date", $join_date);
oci_bind_by_name($stmt, ":email", $email);
oci_bind_by_name($stmt, ":pNumber", $pNumber);

oci_execute($stmt);
oci_close($conn);

