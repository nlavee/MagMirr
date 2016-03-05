use magmirr;

# INSERT TEST DATA FOR USER
insert into user (username, first_name, last_name) values ("test_david", "David", "Jones");
insert into user (username, first_name, last_name) values ("test_nujabes", "Nujabes", "Aiki");
insert into user (username, first_name, last_name) values ("test_admin", "Okawari", "Mirror");
insert into user (username, first_name, last_name) values ("test_user", "Wade", "Wilson");

# INSERT TEST DATA FOR PWD
#insert into password values ("", "");

# INSERT TEST DATA FOR PRIVILEGE
insert into privilege (privilege_description, privilege_name) values ("sudo user", "root");
insert into privilege (privilege_description, privilege_name) values ("admin user", "admin");
insert into privilege (privilege_description, privilege_name) values ("normal user", "norm");
insert into privilege (privilege_description, privilege_name) values ("guest user", "guest");

