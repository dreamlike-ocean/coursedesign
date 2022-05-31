create table bfz_record
(
    record_id int auto_increment
        primary key,
    user_id   int          not null,
    record    varchar(255) null,
    timestamp mediumtext   null,
    constraint bfz_record_login_user_user_id_fk
        foreign key (user_id) references login_user (user_id)
);

