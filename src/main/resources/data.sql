
-- User 1 DummyData
insert into TBL_USER (mod_dtm,mod_id,reg_dtm,reg_id,password,role,user_id,username)
values(now(),
       'ADMIN',
       now(),
       'ADMIN',
       '{bcrypt}$2a$10$4wQQ7vkiedFhwQr5MqqQAejyKCrbiEmlB8yKUWxJfdF5eKlYck8cu',
       'ROLE_ADMIN',
       'yhy1045',
       '유호연');

-- 1
insert into TBL_USER_ADDRESS (mod_dtm, mod_id,reg_dtm,reg_id,address,address_title,user_id)
values ( now(),'yhy1045',now(),'yhy1045','서울시 송파구 오금동 동남로 45-2 태양빌딩','우리집',1);
-- 2
insert into TBL_USER_ADDRESS (mod_dtm, mod_id,reg_dtm,reg_id,address,address_title,user_id)
values ( now(),'yhy1045',now(),'yhy1045','서울시 금천구 홍은2길','회사',1);
-- 3
insert into TBL_USER_ADDRESS (mod_dtm, mod_id,reg_dtm,reg_id,address,address_title,user_id)
values ( now(),'yhy1045',now(),'yhy1045','부산광역시 사상구 사상길 25','삼촌집',1);

insert into TBL_ORDER (mod_dtm, mod_id,reg_dtm,reg_id,menu,order_status,store_name,address,address_title,user_id)
values (now(),'yhy1045',now(),'yhy1045','신전떡볶이','ORDER_RECEIVING','신전떡볶이','서울시 금천구 홍은2길','우리집',1);
;

insert into TBL_ORDER (mod_dtm, mod_id,reg_dtm,reg_id,menu,order_status,store_name,address,address_title,user_id)
values (now(),'yhy1045',now(),'yhy1045','족발','DELIVERED','동경야시장','서울시 금천구 홍은2길','우리집',1);
;

insert into TBL_ORDER (mod_dtm, mod_id,reg_dtm,reg_id,menu,order_status,store_name,address,address_title,user_id)
values (now(),'yhy1045',now(),'yhy1045','매콤떡볶이','DELIVERED','골목떡볶이','서울시 금천구 홍은2길','우리집',1);
;

-- User 2 DummyData

insert into TBL_USER (mod_dtm,mod_id,reg_dtm,reg_id,password,role,user_id,username)
values(now(),
       'ADMIN',
       now(),
       'ADMIN',
       '{bcrypt}$2a$10$4wQQ7vkiedFhwQr5MqqQAejyKCrbiEmlB8yKUWxJfdF5eKlYck8cu',
       'ROLE_ADMIN',
       'barogo2023',
       '바로고');

insert into TBL_USER_ADDRESS (mod_dtm, mod_id,reg_dtm,reg_id,address,address_title,user_id)
values ( now(),'barogo2023',now(),'barogo2023','서울시 송파구 오금동 동남로 45-2 태양빌딩','우리집',2);

insert into TBL_USER_ADDRESS (mod_dtm, mod_id,reg_dtm,reg_id,address,address_title,user_id)
values ( now(),'barogo2023',now(),'barogo2023','서울시 금천구 홍은2길','회사',2);

insert into TBL_USER_ADDRESS (mod_dtm, mod_id,reg_dtm,reg_id,address,address_title,user_id)
values ( now(),'barogo2023',now(),'barogo2023','부산광역시 사상구 사상길 25','삼촌집',2);

insert into TBL_ORDER (mod_dtm, mod_id,reg_dtm,reg_id,menu,order_status,store_name,address,address_title,user_id)
values (now(),'barogo2023',now(),'barogo2023','신전떡볶이','DELIVERING','신전떡볶이','서울시 금천구 홍은2길','우리집',2);
;

insert into TBL_ORDER (mod_dtm, mod_id,reg_dtm,reg_id,menu,order_status,store_name,address,address_title,user_id)
values (now(),'barogo2023',now(),'barogo2023','족발','DELIVERED','동경야시장','서울시 금천구 홍은2길','우리집',2);
;

insert into TBL_ORDER (mod_dtm, mod_id,reg_dtm,reg_id,menu,order_status,store_name,address,address_title,user_id)
values (now(),'barogo2023',now(),'barogo2023','매콤떡볶이','DELIVERED','골목떡볶이','서울시 금천구 홍은2길','우리집',2);
;


-- User 3 DummyData (No Order)
insert into TBL_USER (mod_dtm,mod_id,reg_dtm,reg_id,password,role,user_id,username)
values(now(),
       'ADMIN',
       now(),
       'ADMIN',
       '{bcrypt}$2a$10$4wQQ7vkiedFhwQr5MqqQAejyKCrbiEmlB8yKUWxJfdF5eKlYck8cu',
       'ROLE_ADMIN',
       'barogo2022',
       '바로고');

insert into TBL_USER_ADDRESS (mod_dtm, mod_id,reg_dtm,reg_id,address,address_title,user_id)
values ( now(),'barogo2022',now(),'barogo2022','서울시 송파구 오금동 동남로 45-2 태양빌딩','우리집',3);

insert into TBL_USER_ADDRESS (mod_dtm, mod_id,reg_dtm,reg_id,address,address_title,user_id)
values ( now(),'barogo2022',now(),'barogo2022','서울시 금천구 홍은2길','회사',3);

insert into TBL_USER_ADDRESS (mod_dtm, mod_id,reg_dtm,reg_id,address,address_title,user_id)
values ( now(),'barogo2022',now(),'barogo2022','부산광역시 사상구 사상길 25','삼촌집',3);