CREATE TABLE TB_USER
(
    ID            UUID            NOT NULL,
    NAME          VARCHAR(255)    NOT NULL,
    USERNAME      VARCHAR(500)    NOT NULL
);

CREATE INDEX ON tb_user (name, username);
