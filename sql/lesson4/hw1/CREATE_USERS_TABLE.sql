CREATE TABLE USERS (
    ID NUMBER,
    CONSTRAINT USERS_PK PRIMARY KEY(ID),
    USER_NAME NVARCHAR2(50) UNIQUE,
    PASSWORD NVARCHAR2(50) NOT NULL,
    COUNTRY NVARCHAR2(50),
    USER_TYPE NVARCHAR2(10) DEFAULT 'USER' NOT NULL,
    CHECK (USER_TYPE = 'ADMIN' OR USER_TYPE = 'USER')
);