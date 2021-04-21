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
    //echo "Connection ok\n";
} catch (Exception $e){
    print_r($e);
}

if($_SERVER['REQUEST_METHOD'] == 'DELETE'){
    $json = file_get_contents("php://input");
    $data = json_decode($json, TRUE);
    $sql = "DELETE FROM C WHERE CUSTOMER_ID = :customer_id";
    $stmt = oci_parse($conn, $sql);

    oci_bind_by_name($stmt, ":customer_id", $_GET['customer_id']);

    oci_execute($stmt);
    oci_close($conn);
}

