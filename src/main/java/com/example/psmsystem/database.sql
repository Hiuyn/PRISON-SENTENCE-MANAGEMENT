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
CREATE TABLE sentence_crimes (
                                 sentence_id int,
                                 crime_id int,
                                 years int, -- số năm tù cho tội danh cụ thể trong sentences
                                 PRIMARY KEY (sentence_id, crime_id),
                                 FOREIGN KEY (sentence_id) REFERENCES sentences(sentence_id),
                                 FOREIGN KEY (crime_id) REFERENCES crimes(crime_id)
);
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
                            date_update DATETIME
);

-- Thêm dữ liệu vào bảng prisoners
INSERT INTO prisoners (prisoner_name, date_birth, gender, identity_card, contacter_name, contacter_phone, image, status) VALUES
                                                                                                                             ('Nguyen Van A', '1980-05-15', 1, '123456789012', 'Nguyen Van B', '0987654321', 'image_url_1', false),
                                                                                                                             ('Tran Thi B', '1992-07-20', 2, '987654321012', 'Tran Van C', '0912345678', 'image_url_2', false),
                                                                                                                             ('Le Van C', '1978-03-10', 1, '112233445566', 'Le Thi D', '0908765432', 'image_url_3', false),
                                                                                                                             ('Pham Thi D', '1985-11-22', 2, '665544332211', 'Pham Van E', '0923456789', 'image_url_4', false),
                                                                                                                             ('Hoang Van E', '1990-01-01', 1, '998877665544', 'Hoang Thi F', '0934567890', 'image_url_5', false),
                                                                                                                             ('Vu Thi F', '1988-06-30', 2, '556677889900', 'Vu Van G', '0945678901', 'image_url_6', false),
                                                                                                                             ('Ngo Van G', '1975-09-18', 1, '334455667788', 'Ngo Thi H', '0956789012', 'image_url_7', false),
                                                                                                                             ('Dang Thi H', '1993-12-25', 2, '223344556677', 'Dang Van I', '0967890123', 'image_url_8', false),
                                                                                                                             ('Bui Van I', '1982-07-07', 1, '445566778899', 'Bui Thi J', '0978901234', 'image_url_9', false),
                                                                                                                             ('Tran Van J', '1991-03-15', 1, '778899112233', 'Tran Thi K', '0989012345', 'image_url_10', false);

-- Thêm dữ liệu vào bảng sentences
INSERT INTO sentences (prisoner_id, sentences_code, sentence_type, crimes_code, start_date, end_date, status, parole_eligibility) VALUES
                                                                                                                                      (1, 101, 'life imprisonment', 'Theft', '2020-01-01', '9999-12-31', false, 'Eligible for parole after 20 years'),
                                                                                                                                      (2, 102, 'limited time', 'Fraud', '2021-05-10', '2031-05-10', false, 'Good behavior may reduce sentence'),
                                                                                                                                      (3, 103, 'limited time', 'Assault', '2019-08-15', '2029-08-15', false, 'May apply for parole after 5 years'),
                                                                                                                                      (4, 104, 'life imprisonment', 'Robbery', '2018-12-01', '9999-12-31', false, 'Parole possible after 25 years'),
                                                                                                                                      (5, 105, 'limited time', 'Drug Trafficking', '2022-03-20', '2032-03-20', false, 'Parole eligibility in 3 years'),
                                                                                                                                      (6, 106, 'life imprisonment', 'Murder', '2023-07-10', '9999-12-31', false, 'Parole possible after 15 years'),
                                                                                                                                      (7, 107, 'limited time', 'Burglary', '2020-02-25', '2030-02-25', false, 'Good behavior may reduce sentence'),
                                                                                                                                      (8, 108, 'life imprisonment', 'Kidnapping', '2017-06-30', '9999-12-31', false, 'Eligible for parole after 30 years'),
                                                                                                                                      (9, 109, 'limited time', 'Arson', '2016-09-10', '2026-09-10', false, 'May apply for parole after 4 years'),
                                                                                                                                      (10, 110, 'life imprisonment', 'Embezzlement', '2015-11-15', '9999-12-31', false, 'Parole possible after 20 years');

-- Thêm dữ liệu vào bảng crimes
INSERT INTO crimes (crime_name) VALUES
                                    ('Theft'),
                                    ('Fraud'),
                                    ('Assault'),
                                    ('Robbery'),
                                    ('Drug Trafficking'),
                                    ('Murder'),
                                    ('Burglary'),
                                    ('Kidnapping'),
                                    ('Arson'),
                                    ('Embezzlement');

-- Thêm dữ liệu vào bảng disciplinary_measures
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
                                                                                     (10, '2019-11-10', 3, 'Threatening staff');

-- Thêm dữ liệu vào bảng commendations
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
                                                                             (10, '2019-12-10', 3, 'Good conduct during lockdown');

-- Thêm dữ liệu vào bảng visit_log
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
                                                                                                                                         (10, 10, 'Tran Thi K', '888899990000', 'Wife', '2023-10-15', '16:00:00', '17:00:00', 'Brought legal documents');

-- Thêm dữ liệu vào bảng healths
INSERT INTO healths (health_code, sentence_id, prisoner_id, weight, height, checkup_date, status, level) VALUES
                                                                                                             ('H1',1, 1, 70, 1.75, '2023-03-10', false, 0),
                                                                                                             ('H2', 2, 2, 65, 1.60, '2023-04-20', true, 1),
                                                                                                             ('H3', 3, 3, 80, 1.80, '2023-05-15', false, 0),
                                                                                                             ('H4', 4, 4, 55, 1.55, '2023-06-10', true, 2),
                                                                                                             ('H5', 5, 5, 75, 1.70, '2023-07-20', false, 0),
                                                                                                             ('H6', 6, 6, 68, 1.65, '2023-08-30', true, 1),
                                                                                                             ('H7', 7, 7, 85, 1.85, '2023-09-25', true, 3),
                                                                                                             ('H8', 8, 8, 60, 1.60, '2023-10-15', false, 0),
                                                                                                             ('H9', 9, 9, 72, 1.78, '2023-11-10', true, 2),
                                                                                                             ('H10', 10, 10, 67, 1.67, '2023-12-05', false, 0);

INSERT INTO incareration_process (process_code, sentence_id, prisoner_id, date_of_occurrence, event_type, level, note)
VALUES
    ('P1', 1, 1, '2023-06-01', 'Breach of discipline', 1, 'Minor misconduct.'),
    ('P2', 2, 2, '2023-06-02', 'Bonus', 4, 'Exemplary behavior.'),
    ('P3', 3, 3, '2023-06-03', 'Breach of discipline', 2, 'Moderate misconduct.'),
    ('P4', 4, 4, '2023-06-04', 'Bonus', 3, 'Good behavior.'),
    ('P5', 5, 5, '2023-06-05', 'Breach of discipline', 1, 'Minor misconduct again.'),
    ('P6', 6, 6, '2023-06-06', 'Bonus', 4, 'Very good behavior.'),
    ('P7', 7, 7, '2023-06-07', 'Breach of discipline', 2, 'Moderate misconduct again.'),
    ('P8', 8, 8, '2023-06-08', 'Bonus', 3, 'Good behavior again.'),
    ('P9', 9, 9, '2023-06-09', 'Breach of discipline', 1, 'Another minor misconduct.'),
    ('P10', 10, 10, '2023-06-10', 'Bonus', 4, 'Outstanding behavior.');