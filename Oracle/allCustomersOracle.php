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

$sql = "SELECT * FROM C";
$stmt = oci_parse($conn, $sql);
oci_execute($stmt);

$data = array();
while($res = oci_fetch_array($stmt)){
    $eachValue = array("CUSTOMER_ID" => $res['CUSTOMER_ID'], "CUSTOMER_FNAME" => $res['CUSTOMER_FNAME'],
        "CUSTOMER_SNAME" => $res['CUSTOMER_SNAME'], "CUSTOMER_JOIN_DATE" => $res['CUSTOMER_JOIN_DATE'], "CUSTOMER_EMAIL" => $res['CUSTOMER_EMAIL'],
        "CUSTOMER_NUMBER" => $res['CUSTOMER_NUMBER']);
    $data[] = $eachValue;
}

$name = array('Customers' => $data);

header("Content-type:application/json");
echo json_encode($name);
oci_close($conn);
