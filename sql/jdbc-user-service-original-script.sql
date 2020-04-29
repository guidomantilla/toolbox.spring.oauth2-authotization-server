create table users(principal varchar_ignorecase(50) not null primary key,credentials varchar_ignorecase(50) not null);
create table roles (principal varchar_ignorecase(50) not null,role varchar_ignorecase(50) not null,constraint fk_roles_users foreign key(principal) references users(principal));
create unique index ix_auth_principal on roles (principal,role);
create table groups (id bigint generated by default as identity(start with 0) primary key,group_name varchar_ignorecase(50) not null);
create table group_authorities (group_id bigint not null,authority varchar(50) not null,constraint fk_group_authorities_group foreign key(group_id) references groups(id));
create table group_members (id bigint generated by default as identity(start with 0) primary key,username varchar(50) not null,group_id bigint not null,constraint fk_group_members_group foreign key(group_id) references groups(id));

insert into users values('user','{noop}password');
insert into roles values('user','USER');

insert into groups values(1,'OPERATIONS');
insert into group_authorities values(1,'DBA');
insert into group_members values(1,'user',1);
