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
if($_SERVER['REQUEST_METHOD'] == 'PUT') {
    $json = file_get_contents("php://input");
    $data = json_decode($json, TRUE);
    $orginalDate = new DateTime($data['joinDate']);
    $BSONDate = $orginalDate->getTimestamp();
    $date = new MongoDB\BSON\UTCDatetime($BSONDate*1000);
    $reviewIDObject = array();
    foreach ($data['reviews'] as $reviewID){
        $id = new MongoDB\BSON\ObjectId($reviewID['review_id']);
        $reviewIDObject[] = array('review_id' => $id);
    }
    $result = $collection->updateOne(['_id' => $oid], ['$set' => ['fName' => $data['fName'], 'sName' => $data['sName'], 'joinDate' => $date, 'email' => $data['email'], 'pNumber' => $data['pNumber'],
        'addresses' => $data['addresses'], 'reviews' => $reviewIDObject]]);
} else {
    echo "Incorrect request method";
}


