create table auth_user (

    ID          UUID            NOT NULL,
    PASSWORD    VARCHAR(250)    NOT NULL,
    USERNAME    VARCHAR(250)    NOT NULL,
    PRIMARY KEY (ID, USERNAME)
);