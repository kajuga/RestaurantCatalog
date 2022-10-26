create table  IF NOT EXISTS department
(
    id                  serial
        constraint department_pk
            primary key,
    name                 varchar                                               not null,
    parent_department_id integer
        constraint department_parent_fk
            references department
);