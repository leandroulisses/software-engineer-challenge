create table relevance
(
    id               uuid            not null,
    user_id          varchar(255)    not null,
    rank             bigint          not null
);