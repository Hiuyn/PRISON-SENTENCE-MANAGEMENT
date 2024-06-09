drop database prisoner_sentence;
create database prisoner_sentence;
use prisoner_sentence;
-- 1
create table users(
                      user_id int auto_increment primary key, -- id
                      username nvarchar(15) not null, -- username
                      password varchar(500) not null, -- hash password
                      full_name nvarchar(50) not null, -- full name
                      date_birth date not null -- date of birth
);

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
                          status boolean default false, -- false: tại giam, true: ra tù
                          user_id int not  null, -- id user
                          foreign key (user_id) references users(user_id)
);
-- 3
create table sentences( -- tù án
                          sentence_id int primary key auto_increment, -- id
                          prisoner_id int not null, -- id tù nhân
                          sentences_code int not null, -- mã tù án
                          sentence_type enum ("life_imprisonment",'limited_time'), -- kiểu tù án(chung thân hoặc có thời hạn)
                          crimes_code text,
                          start_date date, -- ngày bắt đầu
                          end_date date, -- ngày kết thúc dự kiến
                          release_date date, -- ngày kết thúc thực tế (chỉ khi đã ra tù)
                          status boolean default false,  -- trạng thái tù án (false: đang hiệu lực, true: đã kết thúc)
                          parole_eligibility nvarchar(500), -- đề xuất cho tù án: giảm án, tăng án, biệt giam...
                          update_date date default (current_date()), -- ngày update
                          user_id int not  null, -- user manage
                          foreign key (prisoner_id) references prisoners(prisoner_id),
                          foreign key (user_id) references users(user_id)
);
-- 4
create table crimes( -- tội danh
                       crime_id int primary key auto_increment, -- id
                       crime_name nvarchar(255) not null, -- tên tội danh
                       user_id int not null, -- user add
                       foreign key (sentence_id) references sentences(sentence_id),
                       foreign key (user_id) references users(user_id)
);
-- 4
create table disciplinary_measures( -- phê bình, khiển trách
                                      disciplinary_measure_id int primary key auto_increment, -- id
                                      sentence_id int not null, -- id tù án
                                      date_of_occurrence date default (curdate()), -- ngày xảy ra
                                      level int, -- 1: MILD, 2:MODERATE, 3:SEVERE, 4:EXTREMELY SEVERE
                                      note text,
                                      user_id int not null, -- user add
                                      foreign key (sentence_id) references sentences(sentence_id),
                                      foreign key (user_id) references users(user_id)
);
-- 4
create table commendations( -- tuyên dương, khen thưởng
                              commendation_id int primary key auto_increment, -- id
                              sentence_id int not null, -- id tù án
                              date_of_occurrence date default (curdate()), -- ngày xảy ra
                              level int, -- 1: MILD,2:MODERATE,3:Good,4:very good
                              note text,
                              user_id int not null, -- user add
                              foreign key (sentence_id) references sentences(sentence_id),
                              foreign key (user_id) references users(user_id)
);
create table incareration_process(
                                     process_id int primary key auto_increment,
                                     sentence_id int not null,
                                     prisoner_id int not null,
                                     date_of_occurrence date default (curdate()),
                                     event_type enum("Breach of discipline","Bonus"),
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
                          user_id int not null, -- user
                          foreign key (user_id) references users(user_id),
                          foreign key (sentence_id) references sentences(sentence_id),
                          foreign key (prisoner_id) references prisoners(prisoner_id)
);
-- 4
create table healths( -- lịch sử khám sức khỏe
                        health_id int primary key auto_increment, -- id
                        hearthCode varchar(10),
                        sentence_id int not null, -- id tù án
                        prisoner_id int not null, -- id tù nhân
                        weight double not null, -- kg
                        height double not null, -- mét
                        checkup_date date default (curdate()), -- ngày kiểm tra
                        status boolean default false,-- false: khỏe, true: bệnh
                        level int default 0,-- 0: không bệnh, 1: nhẹ, 2: nặng, 3: cần can thiệp
                        user_id int not null, -- user
                        foreign key (prisoner_id) references prisoners(prisoner_id),
                        foreign key (user_id) references users(user_id),
                        foreign key (sentence_id) references sentences(sentence_id)
);

CREATE TABLE update_log (
                            id INT PRIMARY KEY auto_increment,
                            date_update DATETIME
);

-- Thêm dữ liệu vào bảng users
INSERT INTO users (username, password, full_name, date_birth) VALUES
                                                                  ('admin', 'adminpass', 'Admin User', '1980-01-01'),
                                                                  ('officer1', 'pass123', 'John Doe', '1985-02-14'),
                                                                  ('officer2', 'pass456', 'Jane Smith', '1990-03-30'),
                                                                  ('officer3', 'pass789', 'Michael Johnson', '1987-04-10'),
                                                                  ('officer4', 'pass012', 'Emily Davis', '1992-05-25'),
                                                                  ('officer5', 'pass345', 'William Brown', '1988-06-18'),
                                                                  ('officer6', 'pass678', 'Linda Wilson', '1991-07-12'),
                                                                  ('officer7', 'pass901', 'Robert Miller', '1983-08-21'),
                                                                  ('officer8', 'pass234', 'Patricia Anderson', '1989-09-30'),
                                                                  ('officer9', 'pass567', 'Barbara Thomas', '1993-10-14');

-- Thêm dữ liệu vào bảng prisoners
INSERT INTO prisoners (prisoner_name, date_birth, gender, identity_card, contacter_name, contacter_phone, image, status, user_id) VALUES
                                                                                                                                      ('Nguyen Van A', '1980-05-15', 1, '123456789012', 'Nguyen Van B', '0987654321', 'image_url_1', false, 2),
                                                                                                                                      ('Tran Thi B', '1992-07-20', 2, '987654321012', 'Tran Van C', '0912345678', 'image_url_2', false, 2),
                                                                                                                                      ('Le Van C', '1978-03-10', 1, '112233445566', 'Le Thi D', '0908765432', 'image_url_3', false, 3),
                                                                                                                                      ('Pham Thi D', '1985-11-22', 2, '665544332211', 'Pham Van E', '0923456789', 'image_url_4', false, 3),
                                                                                                                                      ('Hoang Van E', '1990-01-01', 1, '998877665544', 'Hoang Thi F', '0934567890', 'image_url_5', false, 4),
                                                                                                                                      ('Vu Thi F', '1988-06-30', 2, '556677889900', 'Vu Van G', '0945678901', 'image_url_6', false, 4),
                                                                                                                                      ('Ngo Van G', '1975-09-18', 1, '334455667788', 'Ngo Thi H', '0956789012', 'image_url_7', false, 5),
                                                                                                                                      ('Dang Thi H', '1993-12-25', 2, '223344556677', 'Dang Van I', '0967890123', 'image_url_8', false, 5),
                                                                                                                                      ('Bui Van I', '1982-07-07', 1, '445566778899', 'Bui Thi J', '0978901234', 'image_url_9', false, 6),
                                                                                                                                      ('Tran Van J', '1991-03-15', 1, '778899112233', 'Tran Thi K', '0989012345', 'image_url_10', false, 6);

-- Thêm dữ liệu vào bảng sentences
INSERT INTO sentences (prisoner_id, sentences_code, sentence_type, crimes_code, start_date, end_date, status, parole_eligibility, user_id) VALUES
                                                                                                                                               (1, 101, 'life_imprisonment', 'Theft', '2020-01-01', '9999-12-31', false, 'Eligible for parole after 20 years', 2),
                                                                                                                                               (2, 102, 'limited_time', 'Fraud', '2021-05-10', '2031-05-10', false, 'Good behavior may reduce sentence', 2),
                                                                                                                                               (3, 103, 'limited_time', 'Assault', '2019-08-15', '2029-08-15', false, 'May apply for parole after 5 years', 3),
                                                                                                                                               (4, 104, 'life_imprisonment', 'Robbery', '2018-12-01', '9999-12-31', false, 'Parole possible after 25 years', 3),
                                                                                                                                               (5, 105, 'limited_time', 'Drug Trafficking', '2022-03-20', '2032-03-20', false, 'Parole eligibility in 3 years', 4),
                                                                                                                                               (6, 106, 'life_imprisonment', 'Murder', '2023-07-10', '9999-12-31', false, 'Parole possible after 15 years', 4),
                                                                                                                                               (7, 107, 'limited_time', 'Burglary', '2020-02-25', '2030-02-25', false, 'Good behavior may reduce sentence', 5),
                                                                                                                                               (8, 108, 'life_imprisonment', 'Kidnapping', '2017-06-30', '9999-12-31', false, 'Eligible for parole after 30 years', 5),
                                                                                                                                               (9, 109, 'limited_time', 'Arson', '2016-09-10', '2026-09-10', false, 'May apply for parole after 4 years', 6),
                                                                                                                                               (10, 110, 'life_imprisonment', 'Embezzlement', '2015-11-15', '9999-12-31', false, 'Parole possible after 20 years', 6);

-- Thêm dữ liệu vào bảng crimes
INSERT INTO crimes (crime_name, sentence_id, note, user_id) VALUES
INSERT INTO crimes (crime_name, user_id) VALUES
    ('Theft', 2),
    ('Fraud', 2),
    ('Assault', 3),
    ('Robbery', 3),
    ('Drug Trafficking', 4),
    ('Murder', 4),
    ('Burglary', 5),
    ('Kidnapping', 5),
    ('Arson', 6),
    ('Embezzlement', 6);

-- Thêm dữ liệu vào bảng disciplinary_measures
INSERT INTO disciplinary_measures (sentence_id, date_of_occurrence, level, note, user_id) VALUES
                                                                                              (1, '2022-03-15', 2, 'Insubordination', 3),
                                                                                              (2, '2023-01-20', 3, 'Violent behavior', 3),
                                                                                              (3, '2021-06-10', 1, 'Minor misconduct', 4),
                                                                                              (4, '2020-12-05', 4, 'Severe violation of rules', 4),
                                                                                              (5, '2019-07-25', 2, 'Disobeying orders', 5),
                                                                                              (6, '2018-04-30', 3, 'Fighting with other inmates', 5),
                                                                                              (7, '2022-10-15', 1, 'Late for roll call', 6),
                                                                                              (8, '2021-02-28', 4, 'Attempted escape', 6),
                                                                                              (9, '2020-08-20', 2, 'Possession of contraband', 2),
                                                                                              (10, '2019-11-10', 3, 'Threatening staff', 2);

-- Thêm dữ liệu vào bảng commendations
INSERT INTO commendations (sentence_id, date_of_occurrence, level, note, user_id) VALUES
                                                                                      (1, '2022-06-30', 4, 'Helped in prison work', 3),
                                                                                      (2, '2023-02-25', 3, 'Good behavior', 3),
                                                                                      (3, '2021-09-15', 2, 'Assisted in a charity event', 4),
                                                                                      (4, '2020-05-10', 1, 'Maintained cleanliness', 4),
                                                                                      (5, '2019-03-25', 4, 'Prevented an escape attempt', 5),
                                                                                      (6, '2018-08-30', 3, 'Participated in educational program', 5),
                                                                                      (7, '2022-11-15', 2, 'Showed leadership among inmates', 6),
                                                                                      (8, '2021-01-28', 1, 'Completed vocational training', 6),
                                                                                      (9, '2020-06-20', 4, 'Provided medical assistance', 2),
                                                                                      (10, '2019-12-10', 3, 'Good conduct during lockdown', 2);

-- Thêm dữ liệu vào bảng visit_log
INSERT INTO visit_log (sentence_id, prisoner_id, visitor_name, identity_card, relationship, visit_date, start_time, end_time, notes, user_id) VALUES
                                                                                                                                                  (1, 1, 'Nguyen Van B', '111122223333', 'Brother', '2023-04-10', '09:00:00', '10:00:00', 'Brought food and clothes', 3),
                                                                                                                                                  (2, 2, 'Tran Van C', '444455556666', 'Father', '2023-05-15', '14:00:00', '15:00:00', 'Discussed legal matters', 3),
                                                                                                                                                  (3, 3, 'Le Thi D', '777788889999', 'Wife', '2023-03-20', '10:00:00', '11:00:00', 'Discussed family issues', 4),
                                                                                                                                                  (4, 4, 'Pham Van E', '000011112222', 'Husband', '2023-02-25', '15:00:00', '16:00:00', 'Brought documents', 4),
                                                                                                                                                  (5, 5, 'Hoang Thi F', '333344445555', 'Sister', '2023-01-15', '11:00:00', '12:00:00', 'Provided moral support', 5),
                                                                                                                                                  (6, 6, 'Vu Van G', '666677778888', 'Brother', '2023-06-10', '09:30:00', '10:30:00', 'Discussed health issues', 5),
                                                                                                                                                  (7, 7, 'Ngo Thi H', '999900001111', 'Wife', '2023-07-20', '13:00:00', '14:00:00', 'Brought clothes', 6),
                                                                                                                                                  (8, 8, 'Dang Van I', '222233334444', 'Father', '2023-08-30', '14:30:00', '15:30:00', 'Discussed parole options', 6),
                                                                                                                                                  (9, 9, 'Bui Thi J', '555566667777', 'Mother', '2023-09-25', '10:00:00', '11:00:00', 'Discussed family matters', 2),
                                                                                                                                                  (10, 10, 'Tran Thi K', '888899990000', 'Wife', '2023-10-15', '16:00:00', '17:00:00', 'Brought legal documents', 2);

-- Thêm dữ liệu vào bảng healths
INSERT INTO healths (hearthCode, sentence_id, prisoner_id, weight, height, checkup_date, status, level, user_id) VALUES
                                                                                                                     ('H1', 1, 1, 70, 1.75, '2023-03-10', false, 0, 3),
                                                                                                                     ('H2', 2, 2, 65, 1.60, '2023-04-20', true, 1, 3),
                                                                                                                     ('H3', 3, 3, 80, 1.80, '2023-05-15', false, 0, 4),
                                                                                                                     ('H4', 4, 4, 55, 1.55, '2023-06-10', true, 2, 4),
                                                                                                                     ('H5', 5, 5, 75, 1.70, '2023-07-20', false, 0, 5),
                                                                                                                     ('H6', 6, 6, 68, 1.65, '2023-08-30', true, 1, 5),
                                                                                                                     ('H7', 7, 7, 85, 1.85, '2023-09-25', true, 3, 6),
                                                                                                                     ('H8', 8, 8, 60, 1.60, '2023-10-15', false, 0, 6),
                                                                                                                     ('H9', 9, 9, 72, 1.78, '2023-11-10', true, 2, 2),
                                                                                                                     ('H10', 10, 10, 67, 1.67, '2023-12-05', false, 0, 2);
INSERT INTO incarceration_process (sentence_id, prisoner_id, date_of_occurrence, event_type, level, note)
VALUES
    (1, 1, '2023-06-01', 'Breach of discipline', 1, 'Minor misconduct.'),
    (2, 2, '2023-06-02', 'Bonus', 4, 'Exemplary behavior.'),
    (3, 3, '2023-06-03', 'Breach of discipline', 2, 'Moderate misconduct.'),
    (4, 4, '2023-06-04', 'Bonus', 3, 'Good behavior.'),
    (5, 5, '2023-06-05', 'Breach of discipline', 1, 'Minor misconduct again.'),
    (6, 6, '2023-06-06', 'Bonus', 4, 'Very good behavior.'),
    (7, 7, '2023-06-07', 'Breach of discipline', 2, 'Moderate misconduct again.'),
    (8, 8, '2023-06-08', 'Bonus', 3, 'Good behavior again.'),
    (9, 9, '2023-06-09', 'Breach of discipline', 1, 'Another minor misconduct.'),
    (10, 10, '2023-06-10', 'Bonus', 4, 'Outstanding behavior.');