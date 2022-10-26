create sequence IF NOT EXISTS employee_sequence
    as bigint;

create table IF NOT EXISTS employee
(
    id            bigint  default nextval('employee_sequence'::regclass) not null
        constraint employee_pk
            primary key,
    name          varchar(100)                                           not null,
    email         varchar(100)                                           not null,
    department_id bigint                                                 not null
        constraint employee_department_fk
            references department,
    salary        numeric default 0                                      not null
);

create unique index IF NOT EXISTS employee_email_uindex
    on employee (email);