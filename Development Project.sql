--User creation
CREATE USER Creator IDENTIFIED BY Password;
CREATE USER Emp IDENTIFIED BY Password;
CREATE USER Mgmt IDENTIFIED BY Password;
CREATE USER Cust IDENTIFIED BY Password;

--Role creation
CREATE ROLE EMPLOYEE;
CREATE ROLE MANAGER1;
CREATE ROLE CUSTOMER;

--Creator grants
GRANT DBA TO Creator;

--Employee grants
GRANT CREATE SESSION TO EMP;
GRANT SELECT ON R TO EMP;
GRANT SELECT, UPDATE, INSERT ON C TO EMP;
GRANT SELECT, UPDATE, INSERT ON O TO EMP;
GRANT SELECT ON P TO EMP;
GRANT SELECT ON CAT TO EMP;
GRANT SELECT ON L TO EMP;
GRANT SELECT ON RVW TO EMP;
GRANT SELECT, UPDATE, INSERT ON A TO EMP;

--Manager grants
GRANT CREATE SESSION TO MGMT;
GRANT SELECT, UPDATE, INSERT ON R TO MGMT;
GRANT SELECT, UPDATE, INSERT ON C TO MGMT;
GRANT SELECT, UPDATE, INSERT ON O TO MGMT;
GRANT SELECT, UPDATE, INSERT ON P TO MGMT;
GRANT SELECT, UPDATE, INSERT ON CAT TO MGMT;
GRANT SELECT, UPDATE, INSERT ON E TO MGMT;
GRANT SELECT ON L TO MGMT;
GRANT SELECT ON RVW TO MGMT;
GRANT SELECT, UPDATE, INSERT ON A TO MGMT;

--Customer grants
GRANT CREATE SESSION TO CUST;
GRANT SELECT ON O TO CUST;
GRANT SELECT ON P TO CUST;
GRANT SELECT ON CAT TO CUST;
GRANT SELECT ON L TO CUST;
GRANT SELECT, INSERT, DELETE ON RVW TO CUST;

--Sequence Creation
CREATE SEQUENCE LOCATION_SEQ;
CREATE SEQUENCE EMPLOYEE_SEQ;
CREATE SEQUENCE ORDER_SEQ;
CREATE SEQUENCE ROLE_SEQ;
CREATE SEQUENCE REVIEW_SEQ;
CREATE SEQUENCE CUSTOMER_SEQ;
CREATE SEQUENCE PRODUCT_SEQ;
CREATE SEQUENCE CATEGORY_SEQ;
CREATE SEQUENCE ADDRESS_SEQ;
CREATE SEQUENCE BOOK_SEQ;

--Table creation
CREATE TABLE LOCATIONS(
    LOCATION_ID NUMBER DEFAULT LOCATION_SEQ.NEXTVAL,
    LOCATION_NAME VARCHAR2(25) NOT NULL,
    CONSTRAINT LOCATION_PK PRIMARY KEY (LOCATION_ID)
);

INSERT INTO L (lOCATION_NAME) VALUES ('Bath');
SELECT * FROM L;

CREATE TABLE EMP_ROLES(
    ROLE_ID NUMBER DEFAULT ROLE_SEQ.NEXTVAL,
    ROLE_NAME VARCHAR2(100) NOT NULL,
    ROLE_DESCRIPTION VARCHAR2(500) NOT NULL,
    CONSTRAINT ROLE_PK PRIMARY KEY (ROLE_ID)
);

INSERT INTO R (ROLE_NAME, ROLE_DESCRIPTION) VALUES ('Employee', 'Test');
SELECT * FROM R;

CREATE TABLE CATEGORIES(
    CATEGORY_ID NUMBER DEFAULT CATEGORY_SEQ.NEXTVAL,
    CATEGORY_NAME VARCHAR2(75) NOT NULL,
    CONSTRAINT CATEGORY_PK PRIMARY KEY (CATEGORY_ID)
);

CREATE TABLE EMPLOYEES(
    EMPLOYEE_ID NUMBER DEFAULT EMPLOYEE_SEQ.NEXTVAL,
    EMPLOYEE_FNAME VARCHAR2(100) NOT NULL,
    EMPLOYEE_SNAME VARCHAR2(100) NOT NULL,
    EMPLOYEE_START_DATE DATE NOT NULL,
    EMPLOYEE_EMAIL VARCHAR2(200) NOT NULL,
    EMPLOYEE_NUMBER NUMBER(11) NOT NULL,
    ROLE_ID NUMBER,
    LOCATION_ID NUMBER,
    CONSTRAINT EMPLOYEE_PK PRIMARY KEY (EMPLOYEE_ID),
    CONSTRAINT ROLE_ID_FK FOREIGN KEY (ROLE_ID) REFERENCES EMP_ROLES (ROLE_ID),
    CONSTRAINT LOCATION_ID_FK FOREIGN KEY (LOCATION_ID) REFERENCES LOCATIONS (LOCATION_ID)
);

SELECT * FROM E;

CREATE TABLE PRODUCTS(
    PRODUCT_ID NUMBER DEFAULT PRODUCT_SEQ.NEXTVAL,
    PRICE NUMBER NOT NULL,
    DESCRIPTION VARCHAR2(1000) NOT NULL,
    CATEGORY_ID NUMBER NOT NULL,
    CONSTRAINT PRODUCT_PK PRIMARY KEY (PRODUCT_ID),
    CONSTRAINT CATERGORY_ID_FK FOREIGN KEY (CATEGORY_ID) REFERENCES CATEGORIES (CATEGORY_ID)
);

CREATE TABLE CUSTOMERS(
    CUSTOMER_ID NUMBER DEFAULT CUSTOMER_SEQ.NEXTVAL,
    CUSTOMER_FNAME VARCHAR2(100) NOT NULL,
    CUSTOMER_SNAME VARCHAR2(100) NOT NULL,
    CUSTOMER_JOIN_DATE DATE NOT NULL,
    CUSTOMER_EMAIL VARCHAR2(200) NOT NULL,
    CUSTOMER_NUMBER NUMBER(11) NOT NULL,
    CONSTRAINT CUSTOMER_PK PRIMARY KEY (CUSTOMER_ID)
);

Select * from C;

COMMIT;

INSERT INTO C (CUSTOMER_FNAME, CUSTOMER_SNAME, CUSTOMER_JOIN_DATE, CUSTOMER_EMAIL, CUSTOMER_NUMBER) VALUES ('test', 'test', (TO_DATE('04/03/2021', 'DD/MM/YYYY')), 'test', '01234567890');

UPDATE C SET CUSTOMER_FNAME = 'Dan' WHERE CUSTOMER_ID = 34;

DELETE FROM C where CUSTOMER_ID !=62;

ALTER TABLE CUSTOMERS MODIFY CUSTOMER_NUMBER VARCHAR2(11);

CREATE TABLE ORDERS(
    ORDER_ID NUMBER DEFAULT ORDER_SEQ.NEXTVAL,
    ORDER_RECIEVED_DATE DATE NOT NULL,
    PAYMENT_RECEIVED VARCHAR2(1) NOT NULL,
    ORDER_DISPATCHED VARCHAR2(1) NOT NULL,
    ORDER_DELIVERED VARCHAR2(1) NOT NULL,
    ORDER_DELIVERED_DATE DATE NOT NULL,
    CUSTOMER_ID NUMBER NOT NULL,
    PRODUCT_ID NUMBER NOT NULL,
    LOCATION_ID NUMBER NOT NULL, 
    CONSTRAINT ORDER_PK PRIMARY KEY (ORDER_ID),
    CONSTRAINT O_CUSTOMER_ID_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS (CUSTOMER_ID),
    CONSTRAINT O_PRODUCT_ID_FK FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCTS (PRODUCT_ID),
    CONSTRAINT O_LOCATION_ID_FK FOREIGN KEY (LOCATION_ID) REFERENCES LOCATIONS (LOCATION_ID),
    CONSTRAINT PAYMENT_CHECK CHECK(PAYMENT_RECEIVED IN ('Y', 'N')),
    CONSTRAINT DISPATCH_CHECK CHECK(ORDER_DISPATCHED IN ('Y', 'N')),
    CONSTRAINT DELIVER_CHECK CHECK(ORDER_DELIVERED IN ('Y', 'N'))
);

--You won't have a delivered date on order creation
ALTER TABLE ORDERS MODIFY (ORDER_DELIVERED_DATE NULL);

CREATE TABLE REVIEWS(
    REVIEW_ID NUMBER DEFAULT REVIEW_SEQ.NEXTVAL,
    REVIEW_BODY VARCHAR2(1000) NOT NULL,
    STAR_RATING NUMBER NOT NULL,
    REVIEW_DATE DATE NOT NULL,
    CUSTOMER_ID,
    PRODUCT_ID,
    CONSTRAINT REVIEW_PK PRIMARY KEY (REVIEW_ID),
    CONSTRAINT CUSTOMER_ID_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS (CUSTOMER_ID),
    CONSTRAINT PRODUCT_ID_FK FOREIGN KEY (PRODUCT_ID) REFERENCES PRODUCTS (PRODUCT_ID),
    CONSTRAINT RATING_CHECK CHECK(0 <= STAR_RATING and STAR_RATING >= 5)
);


CREATE TABLE ADDRESSES(
    ADDRESS_ID NUMBER DEFAULT ADDRESS_SEQ.NEXTVAL,
    FIRST_LINE VARCHAR2(50),
    SECOND_LINE VARCHAR2(50),
    CITY VARCHAR2(50), 
    POSTCODE VARCHAR2(8) NOT NULL,
    CONSTRAINT ADDRESS_PK PRIMARY KEY (ADDRESS_ID)
);

CREATE TABLE ADDRESS_BOOK(
    BOOK_ID NUMBER DEFAULT BOOK_SEQ.NEXTVAL,
    CUSTOMER_ID NUMBER NOT NULL,
    ADDRESS_ID NUMBER NOT NULL,
    CONSTRAINT BOOK_PK PRIMARY KEY (BOOK_ID),
    CONSTRAINT B_CUSTOMER_ID_FK FOREIGN KEY (CUSTOMER_ID) REFERENCES CUSTOMERS (CUSTOMER_ID) ON DELETE CASCADE,
    CONSTRAINT B_ADDRESS_ID_FK FOREIGN KEY (ADDRESS_ID) REFERENCES ADDRESSES (ADDRESS_ID) ON DELETE CASCADE 
);

SELECT * FROM C;

SELECT a.first_line, a.second_line, a.city, a.postcode FROM C JOIN B ON (C.CUSTOMER_ID = B.CUSTOMER_ID) JOIN A ON (A.ADDRESS_ID = B.ADDRESS_ID) WHERE C.CUSTOMER_ID = 61; 

INSERT INTO A (FIRST_LINE, SECOND_LINE, CITY, POSTCODE) VALUES ('Test2', 'Test2', 'Test2', 'Test2');

INSERT INTO B (CUSTOMER_ID, ADDRESS_ID) VALUES (62, 29);

--Synonym creation
CREATE PUBLIC SYNONYM L FOR LOCATIONS;
CREATE PUBLIC SYNONYM CTG FOR CATEGORIES;
CREATE PUBLIC SYNONYM E FOR EMPLOYEES;
CREATE PUBLIC SYNONYM R FOR EMP_ROLES;
CREATE PUBLIC SYNONYM RVW FOR REVIEWS;
CREATE PUBLIC SYNONYM C FOR CUSTOMERS;
CREATE PUBLIC SYNONYM P FOR PRODUCTS;
CREATE PUBLIC SYNONYM O FOR ORDERS;
CREATE PUBLIC SYNONYM A FOR ADDRESSES;
CREATE PUBLIC SYNONYM B FOR ADDRESS_BOOK;

DROP TABLE ADDRESS_BOOK;
DROP TABLE ADDRESSES;
DROP PUBLIC SYNONYM B;

