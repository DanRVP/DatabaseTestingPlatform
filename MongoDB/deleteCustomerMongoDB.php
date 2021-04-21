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
if($_SERVER['REQUEST_METHOD'] == 'DELETE') {
    $json = file_get_contents("php://input");
    $data = json_decode($json, TRUE);
    $result = $collection->deleteOne(['_id' => $oid]);
} else {
    echo "Incorrect request method";
}


