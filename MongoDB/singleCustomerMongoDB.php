<?php
// This path should point to Composer's autoloader
require 'C:/laragon/www/ProjectAPI/vendor/autoload.php';

try{
    $client =  new MongoDB\Client("mongodb://localhost:27017");
    $collection = $client->FYP->customers;
} catch (Exception $e){
    print_r($e);
}
$customerID = $_GET['customer_id'];
$oid = new MongoDB\BSON\ObjectId($customerID);
$results = $collection->find(['_id' => $oid])->toArray();
$data = array();
foreach ($results as $result) {
    $id = (string) $result['_id'];
    $utcdatetime = new MongoDB\BSON\UTCDatetime((string) $result['joinDate']);
    $date = $utcdatetime->toDateTime()->format('d/m/Y');
    $data[] = array("customer_id" => $id, "fName" => $result['fName'], "sName" => $result['sName'],
        "joinDate" => $date, "email" => $result['email'], "pNumber" => $result['pNumber'], "addresses" => $result['addresses']);
}
$name = array('Customer' => $data);

header("Content-type:application/json");
echo json_encode($name);


