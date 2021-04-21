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

if($_SERVER['REQUEST_METHOD'] == 'PUT'){
    $json = file_get_contents("php://input");
    $data = json_decode($json, TRUE);
    $sql = "UPDATE C SET CUSTOMER_FNAME = :fName, CUSTOMER_SNAME = :sName, CUSTOMER_JOIN_DATE = (TO_DATE(:join_date, 'DD-MM-YYYY')), CUSTOMER_EMAIL = :email, CUSTOMER_NUMBER = :pNumber WHERE CUSTOMER_ID = :customer_id";
    $stmt = oci_parse($conn, $sql);

    oci_bind_by_name($stmt, ":fName" /*Bind name*/, $data['fName'/*JSON name*/]);
    oci_bind_by_name($stmt, ":sName", $data['sName']);
    oci_bind_by_name($stmt, ":join_date", $data['join_date']);
    oci_bind_by_name($stmt, ":email", $data['email']);
    oci_bind_by_name($stmt, ":pNumber", $data['pNumber']);
    oci_bind_by_name($stmt, ":customer_id", $_GET['customer_id']);

    oci_execute($stmt);
    oci_close($conn);
}
