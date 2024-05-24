drop database prisoner_sentence;
create database if not exists prisoner_sentence;
use prisoner_sentence;

create table users(
  user_id int primary key auto_increment,
  name nvarchar(50) not null,
  username nvarchar(15) not null,
  hash_password varchar(500) not null
);

<<<<<<< Updated upstream
create table prisoners( 
	 prisoner_id int primary key auto_increment,
     prisoner_code int not null unique,
     prisoner_name nvarchar(50) not null,
     date_birth date not null,
     gender enum('male','female','other') not null,
     contact_name nvarchar(50),
     contact_phone nvarchar(15),
     image_path text not null
);
INSERT INTO prisoners (prisoner_code, prisoner_name, date_birth, gender, contact_name, contact_phone, image_path)
VALUES
(101, 'Nguyễn Văn A', '1980-05-12', 'female', 'Trần Thị B', '0123456789', 'src/main/resources/com/example/psmsystem/imagesPrisoner/filterIcon.png'),
(102, 'Lê Thị B', '1990-08-20', 'male', 'Nguyễn Văn C', '0987654321', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img_2.png'),
(103, 'Phạm Văn C', '1975-12-30', 'female', 'Nguyễn Thị D', '0345678901', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img_2.png'),
(104, 'Trần Thị D', '1985-04-15', 'other', 'Trần Văn E', '0789123456', 'src/main/resources/com/example/psmsystem/imagesPrisoner/filterIcon.png'),
(105, 'Nguyễn Văn E', '1965-01-25', 'female', 'Lê Thị F', '0456789012','src/main/resources/com/example/psmsystem/imagesPrisoner/imgPrison.png'),
(106, 'Lê Thị F', '1995-07-10', 'male', 'Phạm Văn G', '0321654987', 'src/main/resources/com/example/psmsystem/imagesPrisoner/imgPrison.png'),
(107, 'Trần Văn G', '1987-02-22', 'other', 'Nguyễn Thị H', '0465789012','src/main/resources/com/example/psmsystem/imagesPrisoner/filterIcon.png'),
(108, 'Nguyễn Thị H', '1992-09-13', 'male', 'Trần Văn I', '0145678390', 'src/main/resources/com/example/psmsystem/imagesPrisoner/imgPrison.png'),
(109, 'Phạm Văn I', '1983-11-17', 'female', 'Lê Thị J', '0654789123', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img_2.png'),
(110, 'Lê Thị J', '1988-03-04', 'female', 'Nguyễn Văn K', '0234567890', 'src/main/resources/com/example/psmsystem/imagesPrisoner/imgPrison.png');
create table prisoners(
  prisoner_id int primary key auto_increment,
  prisoner_code int not null unique,
  prisoner_name nvarchar(50) not null,
  date_birth date not null,
  gender enum('male','female','other') not null,
  contact_name nvarchar(50),
  contact_phone nvarchar(15),
  image_path text not null
);
INSERT INTO prisoners (prisoner_code, prisoner_name, date_birth, gender, contact_name, contact_phone, image_path)
VALUES
    (101, 'Nguyễn Văn A', '1980-05-12', 'female', 'Trần Thị B', '0123456789', 'src/main/resources/com/example/psmsystem/imagesPrisoner/filterIcon.png'),
    (102, 'Lê Thị B', '1990-08-20', 'male', 'Nguyễn Văn C', '0987654321', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img_2.png'),
    (103, 'Phạm Văn C', '1975-12-30', 'female', 'Nguyễn Thị D', '0345678901', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img_2.png'),
    (104, 'Trần Thị D', '1985-04-15', 'other', 'Trần Văn E', '0789123456', 'src/main/resources/com/example/psmsystem/imagesPrisoner/filterIcon.png'),
    (105, 'Nguyễn Văn E', '1965-01-25', 'female', 'Lê Thị F', '0456789012','src/main/resources/com/example/psmsystem/imagesPrisoner/prisonerIcon.png'),
    (106, 'Lê Thị F', '1995-07-10', 'male', 'Phạm Văn G', '0321654987', 'src/main/resources/com/example/psmsystem/imagesPrisoner/prisonerIcon.png'),
    (107, 'Trần Văn G', '1987-02-22', 'other', 'Nguyễn Thị H', '0465789012','src/main/resources/com/example/psmsystem/imagesPrisoner/filterIcon.png'),
    (108, 'Nguyễn Thị H', '1992-09-13', 'male', 'Trần Văn I', '0145678390', 'src/main/resources/com/example/psmsystem/imagesPrisoner/prisonerIcon.png'),
    (109, 'Phạm Văn I', '1983-11-17', 'female', 'Lê Thị J', '0654789123', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img_2.png'),
    (110, 'Lê Thị J', '1988-03-04', 'female', 'Nguyễn Văn K', '0234567890', 'src/main/resources/com/example/psmsystem/imagesPrisoner/prisonerIcon.png');

create table incareration_process(
     process_id int primary key auto_increment,
     prisoner_code int not null,
     event_date date default (curdate()),
     event_type enum("Vbreach of discipline","Bonus","Participate in renovation"),
     desctiption nvarchar(200),
     note text,
     foreign key (prisoner_code) references prisoners(prisoner_code)
);
INSERT INTO incareration_process (prisoner_code, event_date, event_type, desctiption, note)
VALUES
    (101, '2022-12-01', 'Vbreach of discipline', 'Vi phạm kỷ luật', 'Cảnh cáo'),
    (102, '2023-01-15', 'Bonus', 'Được thưởng vì cải tạo tốt', 'Thêm giờ tham quan'),
    (103, '2023-02-20', 'Participate in renovation', 'Tham gia cải tạo', 'Xây dựng tường'),
    (104, '2023-03-10', 'Vbreach of discipline', 'Vi phạm kỷ luật', 'Phạt lao động'),
    (105, '2023-04-05', 'Bonus', 'Được thưởng vì cải tạo tốt', 'Tặng quà'),
    (106, '2023-05-12', 'Participate in renovation', 'Tham gia cải tạo', 'Trồng cây xanh'),
    (107, '2023-06-15', 'Vbreach of discipline', 'Vi phạm kỷ luật', 'Tước quyền lợi'),
    (108, '2023-07-20', 'Bonus', 'Được thưởng vì cải tạo tốt', 'Gia hạn quyền lợi'),
    (109, '2023-08-30', 'Participate in renovation', 'Tham gia cải tạo', 'Làm vườn rau'),
    (110, '2023-09-10', 'Vbreach of discipline', 'Vi phạm kỷ luật', 'Báo cáo kỷ luật');

create table Visitation(
   Visitation_id int primary key auto_increment,
   prisoner_code int not null,
   visitor_name nvarchar(50) not null,
   national_identification_number nvarchar(15) not null,
   relationship nvarchar(50) not null,
   visit_date date default (curdate()),
   notes text,
   foreign key (prisoner_code) references prisoners(prisoner_code)
);
INSERT INTO Visitation (prisoner_code, visitor_name, national_identification_number, relationship, visit_date, notes)
VALUES
    (101, 'Nguyễn Thị X', '0123456789', 'vợ', '2023-01-10', 'Thăm nom hàng tháng'),
    (102, 'Trần Văn Y', '0987654321', 'chồng', '2023-02-15', 'Mang đồ dùng cá nhân'),
    (103, 'Lê Văn Z', '0345678901', 'anh trai', '2023-03-20', 'Mang thực phẩm'),
    (104, 'Phạm Thị W', '0789123456', 'em gái', '2023-04-25', 'Thăm định kỳ'),
    (105, 'Nguyễn Văn V', '0456789012', 'cha', '2023-05-05', 'Thăm nom'),
    (106, 'Trần Thị U', '0321654987', 'mẹ', '2023-06-10', 'Thăm định kỳ'),
    (107, 'Lê Văn T', '0465789012', 'anh trai', '2023-07-15', 'Mang quà'),
    (108, 'Nguyễn Thị S', '0145678390', 'chị gái', '2023-08-20', 'Thăm nom hàng tháng'),
    (109, 'Trần Văn R', '0654789123', 'bạn bè', '2023-09-25', 'Mang quà và thực phẩm'),
    (110, 'Lê Thị Q', '0234567890', 'bạn bè', '2023-10-10', 'Thăm định kỳ');

create table healths(
    health_id int primary key auto_increment,
    prisoner_code int not null,
    weight double not null, -- kg
    height double not null, -- mét
    checkup_date date default (curdate()),
    physical_condition text,
    psychological_signs text,
    situation text,
    notes text,
    foreign key (prisoner_code) references prisoners(prisoner_code)
);
INSERT INTO healths (prisoner_code, weight, height, checkup_date, physical_condition, psychological_signs, situation, notes)
VALUES
    (101, 70, 1.75, '2023-03-15', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
    (102, 55, 1.60, '2023-04-10', 'Ổn định', 'Bình thường', 'Không vấn đề gì', 'Kiểm tra sức khỏe'),
    (103, 80, 1.85, '2023-05-20', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
    (104, 60, 1.65, '2023-06-05', 'Ổn định', 'Bình thường', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
    (105, 90, 1.80, '2023-07-15', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
    (106, 50, 1.55, '2023-08-10', 'Ổn định', 'Bình thường', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
    (107, 85, 1.90, '2023-09-25', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
    (108, 45, 1.50, '2023-10-05', 'Ổn định', 'Bình thường', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
    (109, 100, 2.00, '2023-11-10', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
    (110, 65, 1.70, '2023-12-15', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ');

create table sentences(
  sentence_id int primary key auto_increment,
  prisoner_code int not null,
  sentence_type enum ("life_imprisonment",'limited_time'),
  start_date date default (curdate()),
  end_date date,   
  status nvarchar(50),
  parole_eligibility text,
  foreign key (prisoner_code) references prisoners(prisoner_code)
);
INSERT INTO sentences (sentence_code, prisoner_code, sentence_type, start_date, end_date, status, parole_eligibility)
VALUES
    (1, 101, 'life imprisonment', '2020-01-01', NULL, 'Đang chấp hành án', 'Không đủ điều kiện'),
    (2, 102, 'limited time', '2021-02-15', '2031-02-15', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 5 năm'),
    (3, 103, 'limited time', '2022-03-20', '2027-03-20', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 3 năm'),
    (4, 104, 'life imprisonment', '2023-04-05', NULL, 'Đang chấp hành án', 'Không đủ điều kiện'),
    (5, 105, 'limited time', '2024-05-10', '2034-05-10', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 6 năm'),
    (6, 106, 'limited time', '2025-06-15', '2035-06-15', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 4 năm'),
    (7, 107, 'limited time', '2026-07-20', '2036-07-20', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 5 năm'),
    (8, 108, 'limited time', '2027-08-30', '2037-08-30', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 7 năm'),
    (9, 109, 'limited time', '2028-09-10', '2038-09-10', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 6 năm'),
    (10, 110, 'limited time', '2029-10-15', '2039-10-15', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 4 năm');
use prisoner_sentence
ALTER TABLE sentences
    ADD INDEX idx_sentence_code (sentence_code);
create table crimes(
   crime_id int primary key auto_increment,
   crime_name nvarchar(255),
   note text
);

INSERT INTO crimes (crime_name)
VALUES
    ('Cướp của'),
    ('Giết người'),
    ('Hành hung'),
    ('Trộm'),
    ('Bạo hành'),
    ('Cố ý gây thương tích'),
    ('Nhận hối lộ'),
    ('Tội danh 8'),
    ('Tội danh 9'),
    ('Tội danh 10');