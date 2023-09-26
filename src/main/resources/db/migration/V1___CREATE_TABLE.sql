create table member
(
    membership_id   bigint       not null,
    current_streak  integer,
    discount_status boolean,
    last_attendance date         not null,
    name            varchar(255) not null,
    primary key (membership_id)
)