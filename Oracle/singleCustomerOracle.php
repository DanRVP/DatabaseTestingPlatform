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

$sql = "SELECT * FROM C WHERE CUSTOMER_ID = :customer_id";
$customer_id = $_GET['customer_id'];
$stmt = oci_parse($conn, $sql);
oci_bind_by_name($stmt, ":customer_id", $customer_id);
oci_execute($stmt);


$data = array();
while($res = oci_fetch_array($stmt)){
    $eachValue = array("CUSTOMER_ID" => $res['CUSTOMER_ID'], "CUSTOMER_FNAME" => $res['CUSTOMER_FNAME'],
        "CUSTOMER_SNAME" => $res['CUSTOMER_SNAME'], "CUSTOMER_JOIN_DATE" => $res['CUSTOMER_JOIN_DATE'], "CUSTOMER_EMAIL" => $res['CUSTOMER_EMAIL'],
        "CUSTOMER_NUMBER" => $res['CUSTOMER_NUMBER']);
    $data[] = $eachValue;
}

$sql2 = "SELECT a.first_line, a.second_line, a.city, a.postcode FROM C JOIN B ON (C.CUSTOMER_ID = B.CUSTOMER_ID) JOIN A ON (A.ADDRESS_ID = B.ADDRESS_ID) WHERE C.CUSTOMER_ID = :customer_id";
$customer_id = $_GET['customer_id'];
$stmt2 = oci_parse($conn, $sql2);
oci_bind_by_name($stmt2, ":customer_id", $customer_id);
oci_execute($stmt2);

$addresses = array();
while($res2 = oci_fetch_array($stmt2)){
    $eachValue = array("FIRST_LINE" => $res2['FIRST_LINE'], "SECOND_LINE" => $res2['SECOND_LINE'], "CITY" => $res2['CITY'], "POSTCODE" => $res2['POSTCODE']);
    $addresses[] = $eachValue;
}

$name = array('Customer' => $data, 'Addresses' => $addresses);


header("Content-type:application/JSON");
echo json_encode($name);
oci_close($conn);
