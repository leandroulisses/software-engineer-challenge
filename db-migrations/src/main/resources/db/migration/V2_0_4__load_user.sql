insert into AUTH_USER
            (ID,
            USERNAME,
            PASSWORD)

values      (
            'c01300cc-7e78-4c2c-8889-6a8c742970c8',
            'integration',
            '12345');

insert into AUTH_USER_ROLE
    (USER_ID, ROLE_ID)
 values(
    'c01300cc-7e78-4c2c-8889-6a8c742970c8',
    '2545f384-7532-420a-a6f1-0e0a9802d183'
 );

 insert into AUTH_USER
             (ID,
             USERNAME,
             PASSWORD)

 values      (
             'c01300cc-7e78-4c2c-8889-6a8c742970c7',
             'user',
             '12345');

 insert into AUTH_USER_ROLE
     (USER_ID, ROLE_ID)
  values(
     'c01300cc-7e78-4c2c-8889-6a8c742970c7',
     '24521f60-e116-4c72-9939-72a09c13312e'
  );