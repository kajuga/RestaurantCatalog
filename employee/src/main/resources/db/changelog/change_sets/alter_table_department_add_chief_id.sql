alter table department
    add COLUMN IF NOT EXISTS chief_id bigint;

alter table department
    add constraint department_chief_fk
        foreign key (chief_id) references employee;
