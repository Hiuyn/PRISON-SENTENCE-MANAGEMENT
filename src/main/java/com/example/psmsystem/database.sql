drop database prisoner_sentence;
create database prisoner_sentence;
use prisoner_sentence;
-- 1
create table users(
                      user_id int auto_increment primary key, -- id
                      full_name nvarchar(50) not null, -- full name
                      username nvarchar(15) BINARY not null, -- username
                      password nvarchar(500) not null -- hash password
);

insert into users(full_name,username,password) values ('admin nè','admin123','5994471abb01112afcc18159f6cc74b4f511b99806da59b3caf5a9c173cacfc5');

-- 2
create table prisoners(  -- tù nhân
                          prisoner_id int primary key auto_increment, -- id
                          prisoner_name nvarchar(50) not null, -- tên tù nhân
                          date_birth date not null, -- ngày sinh
                          gender int not null, -- 1: male,2:female,3:other
                          identity_card nvarchar(12) not null,
                          contacter_name nvarchar(50), -- người liên hệ
                          contacter_phone nvarchar(15), -- số điện thoại người liên hệ
                          image text not null, -- ảnh tù nhân
                          status boolean default false -- false: tại giam, true: ra tù
);
-- 3
create table sentences( -- tù án
                          sentence_id int primary key auto_increment, -- id
                          prisoner_id int not null, -- id tù nhân
                          sentences_code int not null, -- mã tù án
                          sentence_type enum ("life imprisonment",'limited time'), -- kiểu tù án(chung thân hoặc có thời hạn)
                          crimes_code text,
                          start_date date, -- ngày bắt đầu
                          end_date date, -- ngày kết thúc dự kiến
                          release_date date, -- ngày kết thúc thực tế (chỉ khi đã ra tù)
                          status boolean default false,  -- trạng thái tù án (false: đang hiệu lực, true: đã kết thúc)
                          parole_eligibility nvarchar(500), -- đề xuất cho tù án: giảm án, tăng án, biệt giam...
                          update_date date default (current_date()), -- ngày update
                          foreign key (prisoner_id) references prisoners(prisoner_id)
);
-- 4

create table crimes( -- tội danh
                       crime_id int primary key auto_increment, -- id
                       crime_name nvarchar(255) not null -- tên tội danh
);
-- 4
create table disciplinary_measures( -- phê bình, khiển trách
                                      disciplinary_measure_id int primary key auto_increment, -- id
                                      sentence_id int not null, -- id tù án
                                      date_of_occurrence date default (curdate()), -- ngày xảy ra
                                      level int, -- 1: MILD, 2:MODERATE, 3:SEVERE, 4:EXTREMELY SEVERE
                                      note text,
                                      foreign key (sentence_id) references sentences(sentence_id)
);
-- 4
create table commendations( -- tuyên dương, khen thưởng
                              commendation_id int primary key auto_increment, -- id
                              sentence_id int not null, -- id tù án
                              date_of_occurrence date default (curdate()), -- ngày xảy ra
                              level int, -- 1: MILD,2:MODERATE,3:Good,4:very good
                              note text,
                              foreign key (sentence_id) references sentences(sentence_id)
);
-- 4
create table visit_log(
                          Visit_log_id int primary key auto_increment, -- id
                          sentence_id int not null, -- id tù án
                          prisoner_id int not null, -- id tù nhân
                          visitor_name nvarchar(50) not null, -- tên người thăm
                          identity_card nvarchar(15) not null, -- CCCD
                          relationship nvarchar(50) not null, -- quan hệ
                          visit_date date default (curdate()), -- ngày thăm
                          start_time time not null , -- giờ bắt đầu thăm
                          end_time time not null, -- giờ kết thúc
                          notes text,
                          foreign key (sentence_id) references sentences(sentence_id),
                          foreign key (prisoner_id) references prisoners(prisoner_id)
);
-- 4
create table healths( -- lịch sử khám sức khỏe
                        health_id int primary key auto_increment, -- id
                        health_code varchar(10),
                        sentence_id int not null, -- id tù án
                        prisoner_id int not null, -- id tù nhân
                        weight double not null, -- kg
                        height double not null, -- mét
                        checkup_date date default (curdate()), -- ngày kiểm tra
                        status boolean default false,-- false: khỏe, true: bệnh
                        level int default 0,-- 0: không bệnh, 1: nhẹ, 2: nặng, 3: cần can thiệp
                        foreign key (prisoner_id) references prisoners(prisoner_id),
                        foreign key (sentence_id) references sentences(sentence_id)
);

create table incareration_process(
                                     process_id int primary key auto_increment,
                                     process_code varchar(10),
                                     sentence_id int not null,
                                     prisoner_id int not null,
                                     date_of_occurrence date default (curdate()),
                                     event_type enum("Breach of discipline","Bonus"),
                                     level int, -- 1: MILD,2:MODERATE,3:Good,4:very good
                                     note text,
                                     foreign key (sentence_id) references sentences(sentence_id)
);

CREATE TABLE update_log (
                            id INT PRIMARY KEY auto_increment,
                            date_update DATETIME,
                            note text,
                            user_id int,
                            foreign key (user_id) references users(user_id)
);

-- Thêm dữ liệu vào bảng prisoners
INSERT INTO prisoners (prisoner_name, date_birth, gender, identity_card, contacter_name, contacter_phone, image, status) VALUES
                                                                                                                             ('Nguyen Van A1', '1979-01-01', 1, '023456349011', 'Nguyen Van B1', '0987654321', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man1.png', false),
                                                                                                                             ('Nguyen Van A2', '1980-02-02', 1, '023456459012', 'Nguyen Van B2', '0987654322', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man2.png', false),
                                                                                                                             ('Nguyen Van A3', '2003-03-03', 1, '823456679013', 'Nguyen Van B3', '0987654323', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man3.png', false),
                                                                                                                             ('Nguyen Van A4', '1982-04-04', 1, '028756789014', 'Nguyen Van B4', '0987654324', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man4.png', false),
                                                                                                                             ('Nguyen Van A5', '1983-05-05', 1, '623756789015', 'Nguyen Van B5', '0987654325', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man5.png', false),
                                                                                                                             ('Nguyen Van A6', '1984-06-06', 1, '323456789016', 'Nguyen Van B6', '0987344326', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man6.png', false),
                                                                                                                             ('Nguyen Van A7', '1999-07-07', 1, '423456789017', 'Nguyen Van B7', '0987564327', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man7.png', false),
                                                                                                                             ('Nguyen Van A8', '1997-08-08', 1, '723456789018', 'Nguyen Van B8', '0987234328', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man8.png', false),
                                                                                                                             ('Nguyen Van A9', '1987-09-09', 1, '543456789019', 'Nguyen Van B9', '0987654329', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man9.png', false),
                                                                                                                             ('Nguyen Van A10', '1988-10-10', 1, '823456789010', 'Nguyen Van B10', '0912654330', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man10.png', false),
                                                                                                                             ('Nguyen Van A11', '1999-11-11', 1, '623456789011', 'Nguyen Van B11', '0987654331', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man11.png', false),
                                                                                                                             ('Nguyen Van A12', '1990-12-12', 1, '623456789012', 'Nguyen Van B12', '0987654332', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man12.png', false),
                                                                                                                             ('Nguyen Van A13', '1988-01-13', 1, '623456789013', 'Nguyen Van B13', '0987654333', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man13.png', false),
                                                                                                                             ('Nguyen Van A14', '1992-02-14', 1, '323456789014', 'Nguyen Van B14', '0987654334', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man14.png', false),
                                                                                                                             ('Nguyen Van A15', '1993-03-15', 1, '223456789015', 'Nguyen Van B15', '0987654335', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man15.png', false),
                                                                                                                             ('Nguyen Van A16', '1994-04-16', 1, '123456789016', 'Nguyen Van B16', '0987654336', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man16.png', false),
                                                                                                                             ('Nguyen Van A17', '1995-05-17', 1, '123456789017', 'Nguyen Van B17', '0987654337', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man17.png', false),
                                                                                                                             ('Nguyen Van A18', '1996-06-18', 1, '123456789018', 'Nguyen Van B18', '0987654338', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man18.png', false),
                                                                                                                             ('Nguyen Van A19', '1997-07-19', 1, '123456789019', 'Nguyen Van B19', '0987654339', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man19.png', false),
                                                                                                                             ('Nguyen Van A20', '1998-08-20', 1, '123456789020', 'Nguyen Van B20', '0987654340', 'src/main/resources/com/example/psmsystem/imagesPrisoner/man20.png', false);


INSERT INTO sentences (prisoner_id, sentences_code, sentence_type, crimes_code, start_date, end_date, status, parole_eligibility) VALUES
                                                                                                                                      (1, 101, 'life imprisonment', 'Theft', '2020-01-01', '9999-12-31', false, 'Eligible for parole after 20 years'),
                                                                                                                                      (2, 102, 'limited time', 'Fraud', '2012-05-10', '2031-05-10', false, 'Good behavior may reduce sentence'),
                                                                                                                                      (3, 103, 'limited time', 'Assault', '2024-08-15', '2026-08-15', false, 'May apply for parole after 5 years'),
                                                                                                                                      (4, 104, 'life imprisonment', 'Robbery', '2018-12-01', '9999-12-31', false, 'Parole possible after 25 years'),
                                                                                                                                      (5, 105, 'limited time', 'Drug Trafficking', '2022-03-20', '2027-03-20', false, 'Parole eligibility in 3 years'),
                                                                                                                                      (6, 106, 'life imprisonment', 'Murder', '2023-07-10', '9999-12-31', false, 'Parole possible after 15 years'),
                                                                                                                                      (7, 107, 'limited time', 'Burglary', '2020-02-25', '2030-02-25', false, 'Good behavior may reduce sentence'),
                                                                                                                                      (8, 108, 'life imprisonment', 'Kidnapping', '2017-06-30', '9999-12-31', false, 'Eligible for parole after 30 years'),
                                                                                                                                      (9, 109, 'limited time', 'Arson', '1997-09-10', '2026-09-10', false, 'May apply for parole after 4 years'),
                                                                                                                                      (10, 110, 'life imprisonment', 'Embezzlement', '2015-11-15', '9999-12-31', false, 'Parole possible after 20 years'),
                                                                                                                                      (11, 111, 'limited time', 'Forgery', '2020-04-12', '2030-04-12', false, 'Parole eligibility in 5 years'),
                                                                                                                                      (12, 112, 'life imprisonment', 'Human Trafficking', '2019-11-03', '9999-12-31', false, 'Eligible for parole after 25 years'),
                                                                                                                                      (13, 113, 'limited time', 'Money Laundering', '2018-01-09', '2034-01-09', false, 'Parole eligibility in 4 years'),
                                                                                                                                      (14, 114, 'life imprisonment', 'Extortion', '2022-02-20', '9999-12-31', false, 'Eligible for parole after 20 years'),
                                                                                                                                      (15, 115, 'limited time', 'Bribery', '2022-09-14', '2031-09-14', false, 'Good behavior may reduce sentence'),
                                                                                                                                      (16, 116, 'limited time', 'Cybercrime', '2019-03-25', '2029-03-25', false, 'Parole eligibility in 6 years'),
                                                                                                                                      (17, 117, 'life imprisonment', 'Manslaughter', '2017-07-17', '9999-12-31', false, 'Eligible for parole after 20 years'),
                                                                                                                                      (18, 118, 'limited time', 'Domestic Violence', '2016-12-11', '2026-12-11', false, 'Parole eligibility in 3 years'),
                                                                                                                                      (19, 119, 'life imprisonment', 'Terrorism', '2015-05-05', '9999-12-31', false, 'Eligible for parole after 30 years'),
                                                                                                                                      (20, 120, 'limited time', 'Vandalism', '2014-10-10', '2024-10-10', false, 'Good behavior may reduce sentence');

INSERT INTO disciplinary_measures (sentence_id, date_of_occurrence, level, note) VALUES
                                                                                     (1, '2022-03-15', 2, 'Insubordination'),
                                                                                     (2, '2023-01-20', 3, 'Violent behavior'),
                                                                                     (3, '2021-06-10', 1, 'Minor misconduct'),
                                                                                     (4, '2020-12-05', 4, 'Severe violation of rules'),
                                                                                     (5, '2019-07-25', 2, 'Disobeying orders'),
                                                                                     (6, '2018-04-30', 3, 'Fighting with other inmates'),
                                                                                     (7, '2022-10-15', 1, 'Late for roll call'),
                                                                                     (8, '2021-02-28', 4, 'Attempted escape'),
                                                                                     (9, '2020-08-20', 2, 'Possession of contraband'),
                                                                                     (10, '2019-11-10', 3, 'Threatening staff'),
                                                                                     (11, '2018-05-22', 1, 'Unauthorized communication'),
                                                                                     (12, '2021-03-14', 4, 'Assaulting an officer'),
                                                                                     (13, '2022-06-19', 2, 'Disrespecting authority'),
                                                                                     (14, '2020-10-07', 3, 'Inciting a riot'),
                                                                                     (15, '2019-12-03', 1, 'Failure to comply'),
                                                                                     (16, '2018-08-18', 4, 'Damage to property'),
                                                                                     (17, '2023-04-09', 2, 'Contraband smuggling'),
                                                                                     (18, '2022-11-30', 3, 'Forgery of documents'),
                                                                                     (19, '2021-09-21', 1, 'Misuse of privileges'),
                                                                                     (20, '2020-07-14', 4, 'Creating disturbances');
INSERT INTO commendations (sentence_id, date_of_occurrence, level, note) VALUES
                                                                             (1, '2022-06-30', 4, 'Helped in prison work'),
                                                                             (2, '2023-02-25', 3, 'Good behavior'),
                                                                             (3, '2021-09-15', 2, 'Assisted in a charity event'),
                                                                             (4, '2020-05-10', 1, 'Maintained cleanliness'),
                                                                             (5, '2019-03-25', 4, 'Prevented an escape attempt'),
                                                                             (6, '2018-08-30', 3, 'Participated in educational program'),
                                                                             (7, '2022-11-15', 2, 'Showed leadership among inmates'),
                                                                             (8, '2021-01-28', 1, 'Completed vocational training'),
                                                                             (9, '2020-06-20', 4, 'Provided medical assistance'),
                                                                             (10, '2019-12-10', 3, 'Good conduct during lockdown'),
                                                                             (11, '2018-10-05', 2, 'Excellent work in kitchen duty'),
                                                                             (12, '2021-07-14', 1, 'Participated in sports activities'),
                                                                             (13, '2020-02-11', 4, 'Helped diffuse a conflict'),
                                                                             (14, '2019-11-03', 3, 'Outstanding performance in workshop'),
                                                                             (15, '2018-05-22', 2, 'Acted as mediator among inmates'),
                                                                             (16, '2022-09-19', 1, 'Volunteer in prison library'),
                                                                             (17, '2021-04-30', 4, 'Developed a training program'),
                                                                             (18, '2020-08-15', 3, 'Helped organize an event'),
                                                                             (19, '2019-06-25', 2, 'Excellent behavior'),
                                                                             (20, '2018-12-21', 1, 'Assisted with clerical tasks');

INSERT INTO visit_log (sentence_id, prisoner_id, visitor_name, identity_card, relationship, visit_date, start_time, end_time, notes) VALUES
                                                                                                                                         (1, 1, 'Nguyen Van B', '111122223333', 'Brother', '2023-04-10', '09:00:00', '10:00:00', 'Brought food and clothes'),
                                                                                                                                         (2, 2, 'Tran Van C', '444455556666', 'Father', '2023-05-15', '14:00:00', '15:00:00', 'Discussed legal matters'),
                                                                                                                                         (3, 3, 'Le Thi D', '777788889999', 'Wife', '2023-03-20', '10:00:00', '11:00:00', 'Discussed family issues'),
                                                                                                                                         (4, 4, 'Pham Van E', '000011112222', 'Husband', '2023-02-25', '15:00:00', '16:00:00', 'Brought documents'),
                                                                                                                                         (5, 5, 'Hoang Thi F', '333344445555', 'Sister', '2023-01-15', '11:00:00', '12:00:00', 'Provided moral support'),
                                                                                                                                         (6, 6, 'Vu Van G', '666677778888', 'Brother', '2023-06-10', '09:30:00', '10:30:00', 'Discussed health issues'),
                                                                                                                                         (7, 7, 'Ngo Thi H', '999900001111', 'Wife', '2023-07-20', '13:00:00', '14:00:00', 'Brought clothes'),
                                                                                                                                         (8, 8, 'Dang Van I', '222233334444', 'Father', '2023-08-30', '14:30:00', '15:30:00', 'Discussed parole options'),
                                                                                                                                         (9, 9, 'Bui Thi J', '555566667777', 'Mother', '2023-09-25', '10:00:00', '11:00:00', 'Discussed family matters'),
                                                                                                                                         (10, 10, 'Tran Thi K', '888899990000', 'Wife', '2023-10-15', '16:00:00', '17:00:00', 'Brought legal documents'),
                                                                                                                                         (11, 11, 'Nguyen Van L', '111122220001', 'Brother', '2023-11-20', '09:00:00', '10:00:00', 'Brought food and clothes'),
                                                                                                                                         (12, 12, 'Tran Van M', '444455556002', 'Father', '2023-12-15', '14:00:00', '15:00:00', 'Discussed legal matters'),
                                                                                                                                         (13, 13, 'Le Thi N', '777788889003', 'Wife', '2023-12-20', '10:00:00', '11:00:00', 'Discussed family issues'),
                                                                                                                                         (14, 14, 'Pham Van O', '000011112004', 'Husband', '2023-12-25', '15:00:00', '16:00:00', 'Brought documents'),
                                                                                                                                         (15, 15, 'Hoang Thi P', '333344445005', 'Sister', '2023-12-15', '11:00:00', '12:00:00', 'Provided moral support'),
                                                                                                                                         (16, 16, 'Vu Van Q', '666677778006', 'Brother', '2023-12-10', '09:30:00', '10:30:00', 'Discussed health issues'),
                                                                                                                                         (17, 17, 'Ngo Thi R', '999900001007', 'Wife', '2023-12-20', '13:00:00', '14:00:00', 'Brought clothes'),
                                                                                                                                         (18, 18, 'Dang Van S', '222233334008', 'Father', '2023-12-30', '14:30:00', '15:30:00', 'Discussed parole options'),
                                                                                                                                         (19, 19, 'Bui Thi T', '555566667009', 'Mother', '2023-12-25', '10:00:00', '11:00:00', 'Discussed family matters'),
                                                                                                                                         (20, 20, 'Tran Thi U', '888899990010', 'Wife', '2023-12-15', '16:00:00', '17:00:00', 'Brought legal documents');

INSERT INTO healths (health_code, sentence_id, prisoner_id, weight, height, checkup_date, status, level) VALUES
                                                                                                             ('H1', 1, 1, 70, 1.75, '2023-03-10', false, 0),
                                                                                                             ('H2', 2, 2, 65, 1.60, '2023-04-20', true, 1),
                                                                                                             ('H3', 3, 3, 80, 1.80, '2023-05-15', false, 0),
                                                                                                             ('H4', 4, 4, 55, 1.55, '2023-06-10', true, 2),
                                                                                                             ('H5', 5, 5, 75, 1.70, '2023-07-20', false, 0),
                                                                                                             ('H6', 6, 6, 68, 1.65, '2023-08-30', true, 1),
                                                                                                             ('H7', 7, 7, 85, 1.85, '2023-09-25', true, 3),
                                                                                                             ('H8', 8, 8, 60, 1.60, '2023-10-15', false, 0),
                                                                                                             ('H9', 9, 9, 72, 1.78, '2023-11-10', true, 2),
                                                                                                             ('H10', 10, 10, 67, 1.67, '2023-12-05', false, 0),
                                                                                                             ('H11', 11, 11, 73, 1.76, '2024-01-20', true, 1),
                                                                                                             ('H12', 12, 12, 66, 1.62, '2024-02-15', false, 0),
                                                                                                             ('H13', 13, 13, 82, 1.82, '2024-03-10', true, 2),
                                                                                                             ('H14', 14, 14, 57, 1.58, '2024-04-25', false, 0),
                                                                                                             ('H15', 15, 15, 78, 1.72, '2024-05-15', true, 1),
                                                                                                             ('H16', 16, 16, 70, 1.66, '2024-06-10', true, 3),
                                                                                                             ('H17', 17, 17, 87, 1.88, '2024-07-20', false, 0),
                                                                                                             ('H18', 18, 18, 62, 1.63, '2024-08-30', true, 2),
                                                                                                             ('H19', 19, 19, 75, 1.79, '2024-09-25', false, 0),
                                                                                                             ('H20', 20, 20, 69, 1.68, '2024-10-15', true, 1);


INSERT INTO incareration_process (process_code, sentence_id, prisoner_id, date_of_occurrence, event_type, level, note) VALUES
                                                                                                                           ('P1', 1, 1, '2024-06-01', 'Breach of discipline', 1, 'Minor misconduct.'),
                                                                                                                           ('P1', 1, 1, '2024-06-01', 'Breach of discipline', 1, 'Minor misconduct.'),
                                                                                                                           ('P1', 1, 1, '2024-06-01', 'Breach of discipline', 1, 'Minor misconduct.'),
                                                                                                                           ('P1', 1, 1, '2024-06-01', 'Breach of discipline', 1, 'Minor misconduct.'),
                                                                                                                           ('P1', 1, 1, '2024-06-01', 'Breach of discipline', 1, 'Minor misconduct.'),
                                                                                                                           ('P2', 2, 2, '2024-06-02', 'Bonus', 4, 'Exemplary behavior.'),
                                                                                                                           ('P2', 2, 2, '2024-06-02', 'Bonus', 4, 'Exemplary behavior.'),
                                                                                                                           ('P2', 2, 2, '2024-06-02', 'Bonus', 4, 'Exemplary behavior.'),
                                                                                                                           ('P2', 2, 2, '2024-06-02', 'Bonus', 4, 'Exemplary behavior.'),
                                                                                                                           ('P2', 2, 2, '2024-06-02', 'Bonus', 4, 'Exemplary behavior.'),
                                                                                                                           ('P3', 3, 3, '2024-06-03', 'Breach of discipline', 2, 'Moderate misconduct.'),
                                                                                                                           ('P3', 3, 3, '2024-06-03', 'Breach of discipline', 2, 'Moderate misconduct.'),
                                                                                                                           ('P3', 3, 3, '2024-06-03', 'Breach of discipline', 2, 'Moderate misconduct.'),
                                                                                                                           ('P3', 3, 3, '2024-06-03', 'Breach of discipline', 2, 'Moderate misconduct.'),
                                                                                                                           ('P3', 3, 3, '2024-06-03', 'Breach of discipline', 2, 'Moderate misconduct.'),
                                                                                                                           ('P3', 3, 3, '2024-06-03', 'Breach of discipline', 2, 'Moderate misconduct.'),
                                                                                                                           ('P3', 3, 3, '2024-06-03', 'Breach of discipline', 2, 'Moderate misconduct.'),
                                                                                                                           ('P4', 4, 4, '2024-06-04', 'Bonus', 3, 'Good behavior.'),
                                                                                                                           ('P6', 6, 6, '2024-06-06', 'Bonus', 4, 'Very good behavior.'),
                                                                                                                           ('P7', 7, 7, '2024-06-07', 'Breach of discipline', 4, 'Moderate misconduct again.'),
                                                                                                                           ('P7', 7, 7, '2024-06-07', 'Breach of discipline', 4, 'Moderate misconduct again.'),
                                                                                                                           ('P7', 7, 7, '2024-06-07', 'Breach of discipline', 4, 'Moderate misconduct again.'),
                                                                                                                           ('P7', 7, 7, '2024-06-07', 'Breach of discipline', 4, 'Moderate misconduct again.'),
                                                                                                                           ('P7', 7, 7, '2024-06-07', 'Breach of discipline', 4, 'Moderate misconduct again.'),
                                                                                                                           ('P7', 7, 7, '2024-06-07', 'Breach of discipline', 4, 'Moderate misconduct again.'),
                                                                                                                           ('P7', 7, 7, '2024-06-07', 'Breach of discipline', 4, 'Moderate misconduct again.'),
                                                                                                                           ('P7', 7, 7, '2024-06-07', 'Breach of discipline', 4, 'Moderate misconduct again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P8', 5, 5, '2024-06-08', 'Bonus', 4, 'Good behavior again.'),
                                                                                                                           ('P9', 9, 9, '2024-06-09', 'Breach of discipline', 1, 'Another minor misconduct.'),
                                                                                                                           ('P10', 10, 10, '2024-06-10', 'Bonus', 4, 'Outstanding behavior.'),
                                                                                                                           ('P11', 11, 11, '2024-06-11', 'Breach of discipline', 1, 'Minor misconduct.'),
                                                                                                                           ('P12', 12, 12, '2024-06-12', 'Bonus', 4, 'Excellent behavior.'),
                                                                                                                           ('P13', 13, 13, '2024-06-13', 'Breach of discipline', 2, 'Moderate misconduct.'),
                                                                                                                           ('P14', 14, 14, '2024-06-14', 'Bonus', 3, 'Good behavior.'),
                                                                                                                           ('P15', 15, 15, '2023-06-15', 'Breach of discipline', 1, 'Minor misconduct again.'),
                                                                                                                           ('P16', 16, 16, '2024-06-16', 'Bonus', 4, 'Very good behavior.'),
                                                                                                                           ('P17', 17, 17, '2024-06-17', 'Breach of discipline', 2, 'Moderate misconduct again.'),
                                                                                                                           ('P18', 18, 18, '2024-06-18', 'Bonus', 3, 'Good behavior again.'),
                                                                                                                           ('P19', 19, 19, '2024-06-19', 'Breach of discipline', 1, 'Another minor misconduct.'),
                                                                                                                           ('P20', 20, 20, '2024-06-20', 'Bonus', 4, 'Outstanding behavior.');


-- Thêm dữ liệu vào bảng crimes
INSERT INTO crimes (crime_name) VALUES
                                    ('Theft (Ăn trộm)'),
                                    ('Fraud (Gian lận)'),
                                    ('Assault (Tấn công)'),
                                    ('Robbery (Cướp)'),
                                    ('Drug Trafficking (Buôn bán ma túy)'),
                                    ('Murder (Giết người)'),
                                    ('Burglary (Trộm cắp)'),
                                    ('Kidnapping (Bắt cóc)'),
                                    ('Arson (Phóng hỏa)'),
                                    ('Embezzlement (Tham ô)'),
                                    ('Extortion (Tống tiền)'),
                                    ('Bribery (Hối lộ)'),
                                    ('Cybercrime (Tội phạm mạng)'),
                                    ('Manslaughter (Ngộ sát)'),
                                    ('Domestic Violence (Bạo lực gia đình)'),
                                    ('Terrorism (Khủng bố)'),
                                    ('Vandalism (Phá hoại tài sản công)'),
                                    ('Tax Evasion (Trốn thuế)'),
                                    ('Human Trafficking (Buôn người)'),
                                    ('Money Laundering (Rửa tiền)');

-