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

if($_SERVER['REQUEST_METHOD'] == 'POST'){
    $json = file_get_contents("php://input");
    $data = json_decode($json, TRUE);
    $sql = "INSERT INTO C (CUSTOMER_FNAME, CUSTOMER_SNAME, CUSTOMER_JOIN_DATE, CUSTOMER_EMAIL, CUSTOMER_NUMBER) 
            VALUES (:fName, :sName, (TO_DATE(:joinDate, 'DD-MM-YYYY')), :email, :pNumber)";
    $stmt = oci_parse($conn, $sql);

    oci_bind_by_name($stmt, ":fName" /*Bind name*/, $data['fName'/*JSON name*/]);
    oci_bind_by_name($stmt, ":sName", $data['sName']);
    oci_bind_by_name($stmt, ":joinDate", $data['joinDate']);
    oci_bind_by_name($stmt, ":email", $data['email']);
    oci_bind_by_name($stmt, ":pNumber", $data['pNumber']);

    oci_execute($stmt);
    oci_close($conn);
}
