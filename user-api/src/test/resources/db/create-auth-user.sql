insert into auth_user
            (ID,
            USERNAME,
            PASSWORD)

values      (
            'c01300cc-7e78-4c2c-8889-6a8c742970c8',
            'admin',
            '12345');

insert into AUTH_USER_ROLE
    (USER_ID, ROLE_ID)
 values(
    'c01300cc-7e78-4c2c-8889-6a8c742970c8',
    '2545f384-7532-420a-a6f1-0e0a9802d183'
 );