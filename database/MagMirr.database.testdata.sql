use magmirr;

# INSERT TEST DATA FOR USER
insert into user (username, first_name, last_name, email, password_id) values ("test_david", "David", "Jones", "djones@magmirr.com", 1);
insert into user (username, first_name, last_name, email, password_id) values ("test_nujabes", "Nujabes", "Aiki", "naiki@magmirr.com", 1);
insert into user (username, first_name, last_name, email, password_id) values ("test_admin", "Okawari", "Mirror", "omirror@magmirr.com", 1);
insert into user (username, first_name, last_name, email, password_id) values ("test_user", "Wade", "Wilson", "wwilson@magmirr.com", 1);

# INSERT TEST DATA FOR PWD
insert into password (pw_hash, salt) values ("d43e5e577e46a25d7645cdb2f7793017", "[B@239cf00a");

# INSERT TEST DATA FOR PRIVILEGE
insert into privilege (privilege_description, privilege_name) values ("sudo user", "root");
insert into privilege (privilege_description, privilege_name) values ("admin user", "admin");
insert into privilege (privilege_description, privilege_name) values ("normal user", "norm");
insert into privilege (privilege_description, privilege_name) values ("guest user", "guest");

# INSERT DATA FOR NEWS
insert into news (section_name) value ("home");
insert into news (section_name) value ("world");
insert into news (section_name) value ("national");
insert into news (section_name) value ("politics");
insert into news (section_name) value ("ny region");
insert into news (section_name) value ("business");
insert into news (section_name) value ("opinion");
insert into news (section_name) value ("technology");
insert into news (section_name) value ("science");
insert into news (section_name) value ("health");
insert into news (section_name) value ("sports");
insert into news (section_name) value ("arts");
insert into news (section_name) value ("fashion");
insert into news (section_name) value ("dining");
insert into news (section_name) value ("travel");
insert into news (section_name) value ("magazine");
insert into news (section_name) value ("real estate");