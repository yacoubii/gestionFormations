export database:
pg_dump -U postgres --no-owner formations > formations.sql

import database:
drop database formations WITH(FROCE);
create database formations;
psql -U postgres formations < formations.sql

Admin:
login:medaliyacoubi
pswd:01060789

User:
login:medaliuser
pswd:01060789