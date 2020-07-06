# Test Time
mysql -u testu --password=testp -h 192.168.56.103 --port 6603 -D test_db

curl http://localhost:8080/recordManager/scan/5
curl http://localhost:8080/recordManager/stop

Date Record:
insert into test_date values (1, '2020-04-01',null,null);

Data Set:
insert into records (id, type, company, content, processed)  values(1,'1','OneCo','10 One Road|TF1 6SO|Jeff','N');
insert into records (id, type, company, content, processed)  values(2,'1A','OneCo','Mark Shark|01952 234543','N');
insert into records (id, type, company, content, processed)  values(3,'1A','OneCo','Jeff Wing|01952 277354','N');
insert into records (id, type, company, content, processed)  values(4,'1A','OneCo','Gary Grunt|01952 742354','N');
insert into records (id, type, company, content, processed)  values(5,'2','TwoCo','Jenny|20','N');
insert into records (id, type, company, content, processed)  values(6,'3','ThreeCo','Lesley','N');
insert into records (id, type, company, content, processed)  values(7,'3A','ThreeCo','Sledgehammer|29.99','N');
insert into records (id, type, company, content, processed)  values(8,'3A','ThreeCo','Lawnmower|89.99','N');
insert into records (id, type, company, content, processed)  values(9,'3A','ThreeCo','Hammer|9.50','N');
insert into records (id, type, company, content, processed)  values(10,'2','FourCo','Brad|5','N');
insert into records (id, type, company, content, processed)  values(11,'2','FiveCo','Tina|7.5','N');
insert into records (id, type, company, content, processed)  values(12,'1','SixCo','66 Six Road|BL6 1NY|Mary','N');
insert into records (id, type, company, content, processed)  values(13,'1A','SixCo','Mary Contrary|01204 324445','N');
insert into records (id, type, company, content, processed)  values(14,'1','EightCo','888 Eights Road|NN11 5TN|Samit','N');
insert into records (id, type, company, content, processed)  values(15,'1A','EightCo','Prakash Poye|01792 200100','N');
insert into records (id, type, company, content, processed)  values(16,'1A','EightCo','Martin Fallout|01792 700100','N');
insert into records (id, type, company, content, processed)  values(17,'1A','EightCo','Bal Findu|0791 244111','N');
insert into records (id, type, company, content, processed)  values(18,'1A','EightCo','Carl Cobol|01792 700100','N');
insert into records (id, type, company, content, processed)  values(19,'1A','EightCo','Deepak Singh|0792 334442','N');
insert into records (id, type, company, content, processed)  values(20,'3','SevenCo','Lesley','N');
insert into records (id, type, company, content, processed)  values(21,'3A','SevenCo','Big Shed|299.99','N');
insert into records (id, type, company, content, processed)  values(22,'2','NineCo','Jack|1.5','N');
insert into records (id, type, company, content, processed)  values(23,'3','TenCo','Benny','N');
insert into records (id, type, company, content, processed)  values(24,'3A','TenCo','Astroturf|750.00','N');
insert into records (id, type, company, content, processed)  values(25,'3A','TenCo','Bricks|247.40','N');
insert into records (id, type, company, content, processed)  values(26,'3A','TenCo','Mortar|49.99','N');
insert into records (id, type, company, content, processed)  values(27,'3A','TenCo','Concrete|35.99','N');