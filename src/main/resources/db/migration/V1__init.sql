create table book_type
(
    id           bigint       not null  auto_increment,
    type         varchar(255) not null unique,
    created_date timestamp    not null default now(),
    updated_date timestamp    not null default now(),
    PRIMARY KEY (id)
);

create table book
(
    id           bigint              not null auto_increment,
    name         varchar(255)        not null,
    description  varchar(255)        not null,
    author       varchar(255)        not null,
    book_type_id bigint              not null,
    price        double precision    not null,
    isbn         varchar(255) unique not null,
    created_date timestamp           not null default now(),
    updated_date timestamp           not null default now(),
    foreign key (book_type_id) references book_type (id),
    PRIMARY KEY (id)

);

create table promotion
(
    id               bigint           not null  auto_increment,
    code             varchar(255)     not null unique,
    book_type_id     bigint           not null,
    discount_percent double precision not null,
    created_date     timestamp        not null default now(),
    updated_date     timestamp        not null default now(),
    foreign key (book_type_id) references book_type (id),
    PRIMARY KEY (id)
);
