import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class InsertMenus {
    Scanner scanner = new Scanner(System.in);
    private String id;
    private String fName;
    private String sName;
    private String start_date;
    private String email;
    private String pNumber;
    private JSONArray addresses = new JSONArray();

    void idEntry(){
        System.out.println("Enter a customer ID");
        String input = scanner.nextLine();
        setId(input);
    }

    void fNameForUpdate(){
        System.out.println("Enter a new first name");
        String input = scanner.nextLine();
        setFname(input);
    }

    void loopEntryMenu(String[] headers){
        ArrayList<String> setters = new ArrayList<String>();
        int i;
        for (i = 0; i < headers.length; i++){
            System.out.println("Enter " + headers[i]  + ":");
            setters.add(scanner.nextLine());
        }
        setFname(setters.get(0));
        setSname(setters.get(1));
        setEmail(setters.get(2));
    }

    void dateCheck(int dateType){
        String filler = null;
        int checker = 0;
        if (dateType == 1){
            filler = "join";
            checker = 1;
        } else if (dateType == 2){
            filler = "order received";
            checker = 2;
        } else if (dateType == 3){
            filler = "review date";
        } else if (dateType == 4){
            filler = "start";
            checker = 4;
        }
        System.out.println("Enter a " + filler + " date (DD-MM-YYYY)");
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        sdf.setLenient(false);
        Date d = new Date();
        String s = scanner.nextLine();
        try{
            d = sdf.parse(s.trim());
        } catch (ParseException p){
            dateCheck(checker);
        }
        setStart_date(s);
    }

    void pNumber(){
        System.out.println("Enter a phone number (no spaces e.g. 01234567890)");
        String number = scanner.nextLine();
        int nCheckBytes = String.valueOf(number).length();
        if (nCheckBytes != 11){
            pNumber();
        } else if (nCheckBytes == 11){
            setpNumber(number);
        }
    }

    void addresses(){
        String fLine;
        String sLine;
        String city;
        String postcode;
        System.out.println("Please enter the number of addresses you would like to add");
        int addressNumber = scanner.nextInt();
        for ( int i = 0; i < addressNumber; i++){
            JSONObject address = new JSONObject();
            Scanner s = new Scanner(System.in);
            System.out.println("Enter the first line");
            fLine = s.nextLine();
            address.put("fLine", fLine);
            System.out.println("Enter the second line");
            sLine = s.nextLine();
            address.put("sLine", sLine);
            System.out.println("Enter the city");
            city = s.nextLine();
            address.put("city", city);
            System.out.println("Enter the postcode");
            postcode = s.nextLine();
            address.put("postcode", postcode);
            addresses.add(address);
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFname() {
        return fName;
    }

    public void setFname(String fname) {
        this.fName = fname;
    }

    public String getSname() {
        return sName;
    }

    public void setSname(String sname) {
        this.sName = sname;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getpNumber() {
        return pNumber;
    }

    public void setpNumber(String pNumber) {
        this.pNumber = pNumber;
    }

    public JSONArray getAddresses() {
        return addresses;
    }

    public void setAddresses(JSONArray addresses) {
        this.addresses = addresses;
    }
}
