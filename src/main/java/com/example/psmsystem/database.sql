drop database prisoner_sentence;
create database if not exists prisoner_sentence;
use prisoner_sentence;

create table users(
	user_id int primary key auto_increment,
    name nvarchar(50) not null,
    username nvarchar(15) not null, 
    hash_password varchar(500) not null
);

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
(101, 'Nguyễn Văn A', '1980-05-12', 'female', 'Trần Thị B', '0123456789', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img_1.png'),
(102, 'Lê Thị B', '1990-08-20', 'male', 'Nguyễn Văn C', '0987654321', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img_2.png'),
(103, 'Phạm Văn C', '1975-12-30', 'female', 'Nguyễn Thị D', '0345678901', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img_2.png'),
(104, 'Trần Thị D', '1985-04-15', 'other', 'Trần Văn E', '0789123456', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img_1.png'),
(105, 'Nguyễn Văn E', '1965-01-25', 'female', 'Lê Thị F', '0456789012','src/main/resources/com/example/psmsystem/imagesPrisoner/img.png'),
(106, 'Lê Thị F', '1995-07-10', 'male', 'Phạm Văn G', '0321654987', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img.png'),
(107, 'Trần Văn G', '1987-02-22', 'other', 'Nguyễn Thị H', '0465789012','src/main/resources/com/example/psmsystem/imagesPrisoner/img_1.png'),
(108, 'Nguyễn Thị H', '1992-09-13', 'male', 'Trần Văn I', '0145678390', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img.png'),
(109, 'Phạm Văn I', '1983-11-17', 'female', 'Lê Thị J', '0654789123', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img_2.png'),
(110, 'Lê Thị J', '1988-03-04', 'female', 'Nguyễn Văn K', '0234567890', 'src/main/resources/com/example/psmsystem/imagesPrisoner/img.png');

create table incareration_process(
	process_id int primary key auto_increment,
    prisoner_id int not null,
    event_date date default (curdate()),
    event_type enum("Vbreach of discipline","Bonus","Participate in renovation"),
    desctiption nvarchar(200),
    note text,
   foreign key (prisoner_id) references prisoners(prisoner_id)
);
INSERT INTO incareration_process (prisoner_id, event_date, event_type, desctiption, note)
VALUES
(1, '2022-12-01', 'Vbreach of discipline', 'Vi phạm kỷ luật', 'Cảnh cáo'),
(2, '2023-01-15', 'Bonus', 'Được thưởng vì cải tạo tốt', 'Thêm giờ tham quan'),
(3, '2023-02-20', 'Participate in renovation', 'Tham gia cải tạo', 'Xây dựng tường'),
(4, '2023-03-10', 'Vbreach of discipline', 'Vi phạm kỷ luật', 'Phạt lao động'),
(5, '2023-04-05', 'Bonus', 'Được thưởng vì cải tạo tốt', 'Tặng quà'),
(6, '2023-05-12', 'Participate in renovation', 'Tham gia cải tạo', 'Trồng cây xanh'),
(7, '2023-06-15', 'Vbreach of discipline', 'Vi phạm kỷ luật', 'Tước quyền lợi'),
(8, '2023-07-20', 'Bonus', 'Được thưởng vì cải tạo tốt', 'Gia hạn quyền lợi'),
(9, '2023-08-30', 'Participate in renovation', 'Tham gia cải tạo', 'Làm vườn rau'),
(10, '2023-09-10', 'Vbreach of discipline', 'Vi phạm kỷ luật', 'Báo cáo kỷ luật');

create table Visitation(
	Visitation_id int primary key auto_increment,
    prisoner_id int not null,
    visitor_name nvarchar(50) not null,
    national_identification_number nvarchar(15) not null,
    relationship nvarchar(50) not null,
    visit_date date default (curdate()),
    notes text,
    foreign key (prisoner_id) references prisoners(prisoner_id)
);
INSERT INTO Vistitation (prisoner_id, visitor_name, national_identification_number, relationship, visit_date, notes)
VALUES
(1, 'Nguyễn Thị X', '0123456789', 'vợ', '2023-01-10', 'Thăm nom hàng tháng'),
(2, 'Trần Văn Y', '0987654321', 'chồng', '2023-02-15', 'Mang đồ dùng cá nhân'),
(3, 'Lê Văn Z', '0345678901', 'anh trai', '2023-03-20', 'Mang thực phẩm'),
(4, 'Phạm Thị W', '0789123456', 'em gái', '2023-04-25', 'Thăm định kỳ'),
(5, 'Nguyễn Văn V', '0456789012', 'cha', '2023-05-05', 'Thăm nom'),
(6, 'Trần Thị U', '0321654987', 'mẹ', '2023-06-10', 'Thăm định kỳ'),
(7, 'Lê Văn T', '0465789012', 'anh trai', '2023-07-15', 'Mang quà'),
(8, 'Nguyễn Thị S', '0145678390', 'chị gái', '2023-08-20', 'Thăm nom hàng tháng'),
(9, 'Trần Văn R', '0654789123', 'bạn bè', '2023-09-25', 'Mang quà và thực phẩm'),
(10, 'Lê Thị Q', '0234567890', 'bạn bè', '2023-10-10', 'Thăm định kỳ');

create table healths(
	health_id int primary key auto_increment,
    prisoner_id int not null,
    weight double not null, -- kg
    height double not null, -- mét
    checkup_date date default (curdate()),
    physical_condition text,
    psychological_signs text,
    situation text,
    notes text,
    foreign key (prisoner_id) references prisoners(prisoner_id)
);
INSERT INTO healths (prisoner_id, weight, height, checkup_date, physical_condition, psychological_signs, situation, notes)
VALUES
(1, 70, 1.75, '2023-03-15', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
(2, 55, 1.60, '2023-04-10', 'Ổn định', 'Bình thường', 'Không vấn đề gì', 'Kiểm tra sức khỏe'),
(3, 80, 1.85, '2023-05-20', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
(4, 60, 1.65, '2023-06-05', 'Ổn định', 'Bình thường', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
(5, 90, 1.80, '2023-07-15', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
(6, 50, 1.55, '2023-08-10', 'Ổn định', 'Bình thường', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
(7, 85, 1.90, '2023-09-25', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
(8, 45, 1.50, '2023-10-05', 'Ổn định', 'Bình thường', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
(9, 100, 2.00, '2023-11-10', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ'),
(10, 65, 1.70, '2023-12-15', 'Khỏe mạnh', 'Ổn định', 'Không vấn đề gì', 'Kiểm tra sức khỏe định kỳ');



INSERT INTO users (name, username, hash_password)
VALUES
('Nguyễn Văn Admin', 'admin', 'hashed_password1'),
('Lê Thị User1', 'user1', 'hashed_password2'),
('Trần Văn User2', 'user2', 'hashed_password3'),
('Phạm Thị User3', 'user3', 'hashed_password4'),
('Nguyễn Văn User4', 'user4', 'hashed_password5'),
('Lê Thị User5', 'user5', 'hashed_password6'),
('Trần Văn User6', 'user6', 'hashed_password7'),
('Phạm Thị User7', 'user7', 'hashed_password8'),
('Nguyễn Văn User8', 'user8', 'hashed_password9'),
('Lê Thị User9', 'user9', 'hashed_password10');

create table sentences(
	sentence_id int primary key auto_increment,
    prisoner_id int not null,
    sentence_type enum ("life imprisonment",'limited time'),
    start_date date default (curdate()),
    end_date date,   -- if any
    status nvarchar(50), 
    parole_eligibility text,
    foreign key (prisoner_id) references prisoners(prisoner_id)
);
INSERT INTO sentences (prisoner_id, sentence_type, start_date, end_date, status, parole_eligibility)
VALUES
(1, 'life imprisonment', '2020-01-01', NULL, 'Đang chấp hành án', 'Không đủ điều kiện'),
(2, 'limited time', '2021-02-15', '2031-02-15', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 5 năm'),
(3, 'limited time', '2022-03-20', '2027-03-20', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 3 năm'),
(4, 'life imprisonment', '2023-04-05', NULL, 'Đang chấp hành án', 'Không đủ điều kiện'),
(5, 'limited time', '2024-05-10', '2034-05-10', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 6 năm'),
(6, 'limited time', '2025-06-15', '2035-06-15', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 4 năm'),
(7, 'limited time', '2026-07-20', '2036-07-20', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 5 năm'),
(8, 'limited time', '2027-08-30', '2037-08-30', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 7 năm'),
(9, 'limited time', '2028-09-10', '2038-09-10', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 6 năm'),
(10, 'limited time', '2029-10-15', '2039-10-15', 'Đang chấp hành án', 'Có thể đủ điều kiện sau 4 năm');

create table crimes(
	crime_id int primary key auto_increment,
    sentence_id int not null,
    note text,
    foreign key (sentence_id) references sentences(sentence_id)
);

INSERT INTO crimes (sentence_id, note)
VALUES
(1, 'Tội danh 1'),
(2, 'Tội danh 2'),
(3, 'Tội danh 3'),
(4, 'Tội danh 4'),
(5, 'Tội danh 5'),
(6, 'Tội danh 6'),
(7, 'Tội danh 7'),
(8, 'Tội danh 8'),
(9, 'Tội danh 9'),
(10, 'Tội danh 10');