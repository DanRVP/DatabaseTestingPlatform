import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

public class Customer extends APIFunction{

    int dbType;

    public Customer(int selectedDB) {
        dbType = selectedDB;
        //db type 1 = Oracle
        //db type 2 = MongoDB
    }

    void insert() throws IOException, InterruptedException {
        String[] custHeaders = {"first name", "surname", "email"};
        InsertMenus i = new InsertMenus();
        i.loopEntryMenu(custHeaders);
        i.dateCheck(1);
        i.pNumber();
        if(dbType == 1){
            try {
                //setUrl(this.url = new URL("http://localhost/ProjectAPI/MongoDB/newCustomerMongoDB.php?"));
                setUrl(this.url = new URL("http://localhost/ProjectAPI/Oracle/newCustomerOracleJSON.php?"));
                //this.data = new HashMap<>();
                this.json = new JSONObject();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            /*data.put("fName", i.getFname());
            data.put("sName", i.getSname());
            data.put("join_date", i.getStart_date());
            data.put("email", i.getEmail());
            data.put("pNumber", i.getpNumber());*/
            json.put("fName", i.getFname());
            json.put("sName", i.getSname());
            json.put("joinDate", i.getStart_date());
            json.put("email", i.getEmail());
            json.put("pNumber", i.getpNumber());
            long startTime = System.currentTimeMillis();
            //post();
            postJSON();
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            System.out.println("\nTime taken to insert record: " + duration + " milliseconds");
        } else if (dbType == 2){
            try {
                setUrl(this.url = new URL("http://localhost/ProjectAPI/MongoDB/newCustomerMongoDB.php?"));
                this.json = new JSONObject();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            i.addresses();
            json.put("fName", i.getFname());
            json.put("sName", i.getSname());
            json.put("join_date", i.getStart_date());
            json.put("email", i.getEmail());
            json.put("pNumber", i.getpNumber());
            json.put("addresses", i.getAddresses());
            JSONObject review = new JSONObject();
            review.put("review_id", "60685e0885c015315ec8c3e2");
            JSONObject review2 = new JSONObject();
            review2.put("review_id", "606c38b96765b0c7d237fe0e");
            JSONArray j = new JSONArray();
            j.add(review);
            j.add(review2);
            json.put("reviews", j);
            long startTime = System.currentTimeMillis();
            postJSON();
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            System.out.println("\nTime taken to insert record: " + duration + " milliseconds");
        }
    }

    void fixedInsert() throws IOException, InterruptedException {
        if (dbType == 1) {
            try {
                //setUrl(this.url = new URL("http://localhost/ProjectAPI/Oracle/newCustomerOracle.php?"));
                setUrl(this.url = new URL("http://localhost/ProjectAPI/Oracle/newCustomerOracleJSON.php?"));
                //this.data = new HashMap<>();
                this.json = new JSONObject();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            /*data.put("fName", "Test");
            data.put("sName", "Test");
            data.put("join_date", "01/01/2021");
            data.put("email", "test");
            data.put("pNumber", "01234567890");
            post();*/
            json.put("fName", "Test");
            json.put("sName", "Test");
            json.put("joinDate", "01-01-2021");
            json.put("email", "Test");
            json.put("pNumber", "01234567890");
            postJSON();
        } else if (dbType == 2) {
            try {
                setUrl(this.url = new URL("http://localhost/ProjectAPI/MongoDB/newCustomerMongoDB.php?"));
                this.json = new JSONObject();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            json.put("fName", "Test");
            json.put("sName", "Test");
            json.put("join_date", "04/03/2021");
            json.put("email", "Test");
            json.put("pNumber", "01234567890");
            JSONObject address = new JSONObject();
            address.put("fLine", "Test");
            address.put("sLine", "Test");
            address.put("city", "Test");
            address.put("postcode", "Test");
            JSONObject address2 = new JSONObject();
            address2.put("fLine", "Test2");
            address2.put("sLine", "Test2");
            address2.put("city", "Test2");
            address2.put("postcode", "Test2");
            JSONArray j1 = new JSONArray();
            j1.add(address);
            j1.add(address2);
            json.put("addresses", j1);
            JSONObject review = new JSONObject();
            review.put("review_id", "60685e0885c015315ec8c3e2");
            JSONObject review2 = new JSONObject();
            review2.put("review_id", "606c38b96765b0c7d237fe0e");
            JSONArray j = new JSONArray();
            j.add(review);
            j.add(review2);
            json.put("reviews", j);
            postJSON();
        }
    }

    void getAllCustomers() throws IOException, ParseException {
        if (dbType == 1) {
            try {
                setUrl(this.url = new URL("http://localhost/ProjectAPI/Oracle/allCustomersOracle.php?"));
                this.data = new HashMap<>();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Starting get");
            long startTime = System.currentTimeMillis();
            get();
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            JSONParser parser = new JSONParser();
            this.json = (JSONObject) parser.parse(getInline());
            JSONArray a = (JSONArray) json.get("Customers");
            System.out.println("\nAll Customers:\n");
            for (int i = 0; i < a.size(); i++) {
                JSONObject obj = (JSONObject) a.get(i);
                System.out.println("Customer ID: " + obj.get("CUSTOMER_ID"));
                System.out.println("First Name: " + obj.get("CUSTOMER_FNAME"));
                System.out.println("Surname: " + obj.get("CUSTOMER_SNAME"));
                System.out.println("Join Date: " + obj.get("CUSTOMER_JOIN_DATE"));
                System.out.println("Email: " + obj.get("CUSTOMER_EMAIL"));
                System.out.println("Phone Number: " + obj.get("CUSTOMER_NUMBER") + "\n\n-------------------------------------\n");
            }
            System.out.println("Finished");
            System.out.println("Time taken to get data from database: " + duration + " milliseconds");
        } else if (dbType == 2) {
            try {
                setUrl(this.url = new URL("http://localhost/ProjectAPI/MongoDB/allCustomersMongoDB.php?"));
                this.json = new JSONObject();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Starting get");
            long startTime = System.currentTimeMillis();
            get();
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            JSONParser parser = new JSONParser();
            this.json = (JSONObject) parser.parse(getInline());
            JSONArray a = (JSONArray) json.get("Customers");
            System.out.println("\nAll Customers:\n");
            for (int i = 0; i < a.size(); i++) {
                JSONObject obj = (JSONObject) a.get(i);
                System.out.println("Customer ID: " + obj.get("customer_id"));
                System.out.println("First Name: " + obj.get("fName"));
                System.out.println("Surname: " + obj.get("sName"));
                System.out.println("Join Date: " + obj.get("joinDate"));
                System.out.println("Email: " + obj.get("email"));
                System.out.println("Phone Number: " + obj.get("pNumber"));
                JSONArray addresses = (JSONArray) obj.get("addresses");
                for (int i2 = 0; i2<addresses.size(); i2++){
                    JSONObject address = (JSONObject) addresses.get(i2);
                    System.out.println("\nAddress " + (i2+1) + ": ");
                    System.out.println("First Line: " + address.get("fLine"));
                    System.out.println("Second Line: " + address.get("sLine"));
                    System.out.println("City: " + address.get("city"));
                    System.out.println("Postcode: " + address.get("postcode"));
                }
                System.out.println("\n-------------------------------------\n");
            }
            System.out.println("Finished");
            System.out.println("Time taken to get data from database: " + duration + " milliseconds");
        }
    }

    void getSingleCustomer() throws ParseException, IOException {
        //just use fixed customer ID for consistency and ease of testing
        if (dbType == 1) {
            try {
                setUrl(this.url = new URL("http://localhost/ProjectAPI/Oracle/singleCustomerOracle.php?customer_id=62"));
                this.data = new HashMap<>();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Starting...");
            long startTime = System.currentTimeMillis();
            get();
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            JSONParser parser = new JSONParser();
            this.json = (JSONObject) parser.parse(getInline());
            JSONArray details = (JSONArray) json.get("Customer");
            JSONArray addresses = (JSONArray) json.get("Addresses");
            System.out.println("\nCustomer details:\n");
            for (int i2 = 0; i2 < details.size(); i2++) {
                JSONObject obj = (JSONObject) details.get(i2);
                System.out.println("Customer ID: " + obj.get("CUSTOMER_ID"));
                System.out.println("First Name: " + obj.get("CUSTOMER_FNAME"));
                System.out.println("Surname: " + obj.get("CUSTOMER_SNAME"));
                System.out.println("Join Date: " + obj.get("CUSTOMER_JOIN_DATE"));
                System.out.println("Email: " + obj.get("CUSTOMER_EMAIL"));
                System.out.println("Phone Number: " + obj.get("CUSTOMER_NUMBER"));
            }
            for (int i3 = 0; i3 < addresses.size(); i3++) {
                JSONObject obj = (JSONObject) addresses.get(i3);
                System.out.println("\nAddress " + (i3 + 1) + "\n-------------------------------------");
                System.out.println("First Line: " + obj.get("FIRST_LINE"));
                System.out.println("Second Line: " + obj.get("SECOND_LINE"));
                System.out.println("City: " + obj.get("CITY"));
                System.out.println("Postcode: " + obj.get("POSTCODE") + "\n-------------------------------------");
            }
            System.out.println("\nFinished");
            System.out.println("\nTime taken to get data from database: " + duration + " milliseconds");
        } else if (dbType == 2) {
            try {
                setUrl(this.url = new URL("http://localhost/ProjectAPI/MongoDB/singleCustomerMongoDB.php?customer_id=60719718d55d000050000935"));
                this.json = new JSONObject();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            System.out.println("Starting...");
            long startTime = System.currentTimeMillis();
            get();
            long endTime = System.currentTimeMillis();
            long duration = (endTime - startTime);
            JSONParser parser = new JSONParser();
            this.json = (JSONObject) parser.parse(getInline());
            JSONArray a = (JSONArray) json.get("Customer");
            System.out.println("\nAll Customers:\n");
            for (int i = 0; i < a.size(); i++) {
                JSONObject obj = (JSONObject) a.get(i);
                System.out.println("Customer ID: " + obj.get("customer_id"));
                System.out.println("First Name: " + obj.get("fName"));
                System.out.println("Surname: " + obj.get("sName"));
                System.out.println("Join Date: " + obj.get("joinDate"));
                System.out.println("Email: " + obj.get("email"));
                System.out.println("Phone Number: " + obj.get("pNumber"));
                JSONArray addresses = (JSONArray) obj.get("addresses");
                for (int i2 = 0; i2<addresses.size(); i2++){
                    JSONObject address = (JSONObject) addresses.get(i2);
                    System.out.println("\nAddress " + (i2+1) + ": ");
                    System.out.println("First Line: " + address.get("fLine"));
                    System.out.println("Second Line: " + address.get("sLine"));
                    System.out.println("City: " + address.get("city"));
                    System.out.println("Postcode: " + address.get("postcode"));
                }
                System.out.println("\n-------------------------------------\n");
            }
            System.out.println("Finished");
            System.out.println("Time taken to get data from database: " + duration + " milliseconds");
        }
    }

    void updateCustomer() throws IOException, InterruptedException {
        InsertMenus i = new InsertMenus();
        i.fNameForUpdate();
        String updateName = i.getFname();
        if (dbType == 1) {
            try {
                setUrl(this.url = new URL("http://localhost/ProjectAPI/Oracle/editCustomerOracle.php?customer_id=62"));
                this.json = new JSONObject();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
            json.put("fName", updateName);
            json.put("sName", "test");
            json.put("join_date", "04-03-1998");
            json.put("email", "test");
            json.put("pNumber", "01234567890");
        } else if (dbType == 2) {
        try {
            setUrl(this.url = new URL("http://localhost/ProjectAPI/MongoDB/editCustomerMongoDB.php?customer_id=60719718d55d000050000935"));
            this.json = new JSONObject();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        }
        json.put("fName", updateName);
        json.put("sName", "Test");
        json.put("join_date", "04/03/2021");
        json.put("email", "Test");
        json.put("pNumber", "01234567890");
        JSONObject address = new JSONObject();
        address.put("fLine", "Test");
        address.put("sLine", "Test");
        address.put("city", "Test");
        address.put("postcode", "Test");
        JSONObject address2 = new JSONObject();
        address2.put("fLine", "Test2");
        address2.put("sLine", "Test2");
        address2.put("city", "Test2");
        address2.put("postcode", "Test2");
        JSONArray j1 = new JSONArray();
        j1.add(address);
        j1.add(address2);
        json.put("addresses", j1);
        JSONObject review = new JSONObject();
        review.put("review_id", "60685e0885c015315ec8c3e2");
        JSONObject review2 = new JSONObject();
        review2.put("review_id", "606c38b96765b0c7d237fe0e");
        JSONArray j = new JSONArray();
        j.add(review);
        j.add(review2);
        json.put("reviews", j);
    }
        long startTime = System.currentTimeMillis();
        put();
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("\nTime taken to update record: " + duration + " milliseconds");
    }

    void deleteCustomer() throws IOException, InterruptedException {
        InsertMenus i = new InsertMenus();
        i.idEntry();
        String id = i.getId();
        if (dbType == 1) {
            try {
                setUrl(this.url = new URL("http://localhost/ProjectAPI/Oracle/deleteCustomerOracle.php?customer_id=" + id));
                this.json = new JSONObject();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }

        } else if (dbType == 2) {
            try {
                setUrl(this.url = new URL("http://localhost/ProjectAPI/MongoDB/deleteCustomerMongoDB.php?customer_id=" + id));
                this.json = new JSONObject();
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            }
        }
        long startTime = System.currentTimeMillis();
        delete();
        long endTime = System.currentTimeMillis();
        long duration = (endTime - startTime);
        System.out.println("\nTime taken to update record: " + duration + " milliseconds");
    }
}
