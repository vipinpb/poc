create table metric_source (
    id          bigint    NOT NULL,
    name        varchar(100)  NOT NULL,
    description  varchar(100)  null,
    classname     varchar(200)    NOT NULL,
    configuration   varchar(2000)    NOT NULL,
    disable  BOOLEAN DEFAULT FALSE NOT NULL,
    primary key (id)
);
create table metric_schedule (
    id          bigint    NOT NULL,
    source_id   bigint  NOT NULL,
    thread     varchar(200)    NOT NULL,
    schedule   varchar(2000)    NOT NULL,
    primary key (id)
);
create table metric_rules (
    id          bigint    NOT NULL,
    name        varchar(100)  NOT NULL,
    exp  varchar(300)  NOT NULL,
    waitfor     varchar(50)    NOT NULL,
    source_id   bigint  NULL,
    type   varchar(100)    NOT NULL,
    primary key (id)
);

create table metric_alerts (
    id          bigint    NOT NULL,
    name        varchar(100)  NOT NULL,
    rule_id   bigint NOT NULL,
    primary key (id)
);
create table time_series_metric (
    id          bigint    NOT NULL,
    key        varchar(150)  NOT NULL,
    labels  varchar(1000)  null,
    source_id     bigint    NOT NULL,
    timestamp   bigint    NOT NULL,
    value  bigint NOT NULL,
    primary key (id)
);
