create table score_record
(
    record_id int auto_increment
        primary key,
    user_id   int        not null,
    count     int        null,
    timestamp mediumtext not null,
    type      int        null,
    constraint score_record_login_user_user_id_fk
        foreign key (user_id) references login_user (user_id)
);

create index score_record_user_id_index
    on score_record (user_id);

