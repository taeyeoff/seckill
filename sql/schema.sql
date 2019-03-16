create table seckill (
    seckill_id number(20) not null,
    name varchar(120) not null,
    quantity number(10) not null, 
    start_time timestamp not null,
    end_time timestamp not null,
    create_time timestamp default current_timestamp  not null,
    constraint pk_seckill primary key (seckill_id)
);
comment on table SECKILL is '秒杀库存表';
comment on column SECKILL.seckill_id is '商品库存id';
comment on column SECKILL.quantity is '数量';
comment on column SECKILL.name is '商品名称';
comment on column SECKILL.start_time is '秒杀开始时间';
comment on column SECKILL.end_time is '秒杀结束时间';
comment on column SECKILL.create_time is '创建时间';


CREATE INDEX idx_start_time ON seckill(start_time);
CREATE INDEX idx_end_time ON seckill(end_time);
CREATE INDEX idx_create_time ON seckill(create_time);

默认约束,非空约束同时存在,非空约束写在后面


create table success_seckilled(
    seckill_id number(20) not null,
    phone varchar(15) not null ,
    state number(1) not null,
    create_time timestamp not null ,
    constraint PK_SUCCESS_SECKILLED primary key(seckill_id,phone)
);

comment on column SUCCESS_SECKILLED.phone is '用户手机号';
comment on column SUCCESS_SECKILLED.state is '状态标示:-1:无效 0:成功 1:已付款';
comment on column SUCCESS_SECKILLED.create_time is '创建时间';