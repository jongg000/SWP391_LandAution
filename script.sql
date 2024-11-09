--create database LandAuction
USE [LandAuction]

GO
--role
SET IDENTITY_INSERT [dbo].[Roles] ON 
INSERT [dbo].[Roles] ([RoleID], [Role_Name], description) VALUES (1, N'ROLE_CUSTOMER',N'Người Dùng')
INSERT [dbo].[Roles] ([RoleID], [Role_Name], description) VALUES (2, N'ROLE_ADMIN',N'Quản lý')
INSERT [dbo].[Roles] ([RoleID], [Role_Name], description) VALUES (3, N'ROLE_STAFF',N'Nhân viên')
INSERT [dbo].[Roles] ([RoleID], [Role_Name], description) VALUES (4, N'ROLE_CUSTOMER_CARE',N'Chăm sóc khách hàng')
SET IDENTITY_INSERT [dbo].[Roles] OFF
GO
--status
SET IDENTITY_INSERT [dbo].[status] ON 
INSERT INTO dbo.status (statusid, description, name) VALUES (1, NULL, N'Chưa xác minh'); -- user
INSERT INTO dbo.status (statusid, description, name) VALUES (2, NULL, N'Đã xác minh'); -- user
INSERT INTO dbo.status (statusid, description, name) VALUES (3, NULL, N'Khóa');-- all user-admin
INSERT INTO dbo.status (statusid, description, name) VALUES (4, NULL, N'Chờ xét duyệt'); --asset
INSERT INTO dbo.status (statusid, description, name) VALUES (5, NULL, N'Đang đấu giá'); -- asset
INSERT INTO dbo.status (statusid, description, name) VALUES (6, NULL, N'Đã đấu giá'); -- asset
INSERT INTO dbo.status (statusid, description, name) VALUES (7, NULL, N'Đấu giá thành công'); -- asset
INSERT INTO dbo.status (statusid, description, name) VALUES (8, NULL, N'Chưa đấu giá'); --asset
INSERT INTO dbo.status (statusid, description, name) VALUES (9, NULL, N'Đã hủy'); --asset
INSERT INTO dbo.status (statusid, description, name) VALUES (10, NULL, N'Chưa bắt đầu'); -- auction
INSERT INTO dbo.status (statusid, description, name) VALUES (11, NULL, N'Đã kết thúc'); -- auction
INSERT INTO dbo.status (statusid, description, name) VALUES (12, NULL, N'Đang tiến hành'); -- auction
INSERT INTO dbo.status (statusid, description, name) VALUES (13, NULL, N'Đã hủy'); --asset
INSERT INTO dbo.status (statusid, description, name) VALUES (14, NULL, N'Chưa cập nhập'); 
SET IDENTITY_INSERT [dbo].[status] OFF
GO

--user
SET IDENTITY_INSERT [dbo].[users] ON 
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid])
VALUES (1, N'Ha Noi', N'4.jpg', CAST(N'1990-01-01' AS Date), N'admin@gmail.com', N'Lê Đoàn Đức','Nam', N'Chung',  N'backendcccd.png', N'frontendcccd.png', N'0123456789', N'$2a$10$Al9nRp.z0gjmUG1n1NOA0eBRoS/JBbwo1HCzFFV8rYeLXoV1ae7yS', N'0358210806', CAST(0 AS Numeric(19, 0)), 2, 2)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid])
VALUES (2, NULL, NULL, NULL, N'tungthptvixuyen@gmail.com', N'Tung', NULL, N'Customer', NULL, NULL, NULL, N'$2a$10$Al9nRp.z0gjmUG1n1NOA0eBRoS/JBbwo1HCzFFV8rYeLXoV1ae7yS', N'0862423204', CAST(0 AS Numeric(19, 0)), 1, 1)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid])
VALUES (3,N'Ha Noi', N'4.jpg', CAST(N'1990-01-01' AS Date), N'customercare@gmail.com', N'Le ', 'Nam', N'Chung',  N'backendcccd.png', N'frontendcccd.png', '01302526891', N'$2a$10$Al9nRp.z0gjmUG1n1NOA0eBRoS/JBbwo1HCzFFV8rYeLXoV1ae7yS', N'045214221', CAST(0 AS Numeric(19, 0)), 4, 2)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid])
VALUES (4,N'Ha Noi', N'4.jpg', CAST(N'1990-01-01' AS Date), N'staff@gmail.com', N'Staf', 'Nam', N'Agent',  N'backendcccd.png', N'frontendcccd.png', '013025891', N'$2a$10$Al9nRp.z0gjmUG1n1NOA0eBRoS/JBbwo1HCzFFV8rYeLXoV1ae7yS', N'023568963', CAST(0 AS Numeric(19, 0)), 3, 2)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid])
VALUES (5, N'Ha Noi', N'4.jpg', CAST(N'1990-01-01' AS Date), N'user@gmail.com', N'Họ', 'Nam', N'Tên User', N'backendcccd.png', N'frontendcccd.png', N'0123456678978', N'$2a$10$XDVenY.7wuqc4V/LRDhEi.KYbv5A3HwxTikEw.pF9UuCUEPxdbjfy', N'023568963', 0, 1, 2)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid])
VALUES (6, N'TP HCM', N'4.jpg', CAST(N'1990-01-01' AS Date), N'user1@gmail.com', N'Họ', 'Nam', N'Tên User 2', N'backendcccd.png', N'frontendcccd.png', N'0123456678977', N'$2a$10$XDVenY.7wuqc4V/LRDhEi.KYbv5A3HwxTikEw.pF9UuCUEPxdbjfy', N'023568964', 0, 1, 2)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid]) 
VALUES (7, N'Da Nang', N'4.jpg', CAST(N'1990-01-01' AS Date), N'user2@gmail.com', N'Họ', 'Nu', N'Tên User 3', N'backendcccd.png', N'frontendcccd.png', N'0123456678976', N'$2a$10$XDVenY.7wuqc4V/LRDhEi.KYbv5A3HwxTikEw.pF9UuCUEPxdbjfy', N'023568965', 0, 1, 2)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid])
VALUES (8, N'Da Nang', N'4.jpg', CAST(N'1990-01-01' AS Date), N'user3@gmail.com', N'Họ', 'Nam', N'Tên User 3', N'backendcccd.png', N'frontendcccd.png', N'0123456678975', N'$2a$10$XDVenY.7wuqc4V/LRDhEi.KYbv5A3HwxTikEw.pF9UuCUEPxdbjfy', N'023568966', 0, 1, 1)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid])
VALUES (9, N'Da Nang', N'4.jpg', CAST(N'1990-01-01' AS Date), N'seller@gmail.com', N'Họ', 'Nu', N'Seller', N'backendcccd.png', N'frontendcccd.png', N'0123456678974', N'$2a$10$XDVenY.7wuqc4V/LRDhEi.KYbv5A3HwxTikEw.pF9UuCUEPxdbjfy', N'023168967', 0, 1, 2)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid])
VALUES (10, N'Da Nang', N'4.jpg', CAST(N'1990-01-01' AS Date), N'seller1@gmail.com', N'Họ', 'Nu', N'Seller', N'backendcccd.png', N'frontendcccd.png', N'0123456678974', N'$2a$10$XDVenY.7wuqc4V/LRDhEi.KYbv5A3HwxTikEw.pF9UuCUEPxdbjfy', N'022568967', 0, 3, 2)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid])
VALUES (11, N'Da Nang', N'4.jpg', CAST(N'1990-01-01' AS Date), N'seller2@gmail.com', N'Họ', 'Nu', N'Seller', N'backendcccd.png', N'frontendcccd.png', N'0123456678974', N'$2a$10$XDVenY.7wuqc4V/LRDhEi.KYbv5A3HwxTikEw.pF9UuCUEPxdbjfy', N'023768967', 0, 4, 2)
SET IDENTITY_INSERT [dbo].[users] OFF
GO
SET IDENTITY_INSERT [dbo].[land] ON 
INSERT [dbo].[land] ([land_id], [contact], [description], [district], [length], [location], [name], [path], [price], [province], [square], [ward], [width], [user_user_id]) VALUES (1, N'023568967', N'aa', N'Huyện Bác Ái', 10, N'https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d499699.54686142836!2d108.53019549916499!3d11.911858794180532!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3170e2259bdd297b%3A0x2b94fccebb59765f!2zQsOhYyDDgWksIE5pbmggVGh14bqtbiwgVmnhu4d0IE5hbQ!5e0!3m2!1svi!2s!4v1730688263414!5m2!1svi!2s', N'Đất 1', N'DocumentFPT help..doc', CAST(10000 AS Numeric(19, 0)), N'Tỉnh Ninh Thuận', 100, N'Xã Phước Bình', 10, 9)
INSERT [dbo].[land] ([land_id], [contact], [description], [district], [length], [location], [name], [path], [price], [province], [square], [ward], [width], [user_user_id]) VALUES (2,N'023568967', N'aaa', N'Thị xã Duy Tiên', 20, N'https://www.google.com/maps/place/FPT+University/@21.0075648,105.5227904,14z/data=!4m14!1m7!3m6!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!2sFPT+University!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm!3m5!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm?entry=ttu&g_ep=EgoyMDI0MTAxNS4wIKXMDSoASAFQAw%3D%3D', N'Đất 2', N'Document_FPT help(1).doc', CAST(20000 AS Numeric(19, 0)), N'Tỉnh Hà Nam', 800, N'Phường Yên Bắc', 40, 9)
INSERT [dbo].[land] ([land_id], [contact], [description], [district], [length], [location], [name], [path], [price], [province], [square], [ward], [width], [user_user_id]) VALUES (3, N'023568967', N'Hay quá', N'Huyện Đăk Đoa', 30, N'https://www.google.com/maps/place/FPT+University/@21.0075648,105.5227904,14z/data=!4m14!1m7!3m6!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!2sFPT+University!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm!3m5!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm?entry=ttu&g_ep=EgoyMDI0MTAxNS4wIKXMDSoASAFQAw%3D%3D', N'Đất số 3', N'Document_FPT help(2).doc', CAST(300000 AS Numeric(19, 0)), N'Tỉnh Gia Lai', 600, N'Xã Đăk Sơmei', 20, 9)
INSERT [dbo].[land] ([land_id], [contact], [description], [district], [length], [location], [name], [path], [price], [province], [square], [ward], [width], [user_user_id]) VALUES (4, N'023568967', N'hay quá', N'Quận Thốt Nốt', 40, N'https://www.google.com/maps/place/FPT+University/@21.0075648,105.5227904,14z/data=!4m14!1m7!3m6!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!2sFPT+University!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm!3m5!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm?entry=ttu&g_ep=EgoyMDI0MTAxNS4wIKXMDSoASAFQAw%3D%3D', N'Đất 4', N'DocumentSWE201c_PE_2024_Summer_Sample..pdf', CAST(369 AS Numeric(19, 0)), N'Thành phố Cần Thơ', 800, N'Phường Tân Hưng', 20, 9)
INSERT [dbo].[land] ([land_id], [contact], [description], [district], [length], [location], [name], [path], [price], [province], [square], [ward], [width], [user_user_id]) VALUES (5, N'023568967', N'hehhehe', N'Huyện Sóc Sơn', 50, N'https://www.google.com/maps/place/FPT+University/@21.0075648,105.5227904,14z/data=!4m14!1m7!3m6!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!2sFPT+University!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm!3m5!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm?entry=ttu&g_ep=EgoyMDI0MTAxNS4wIKXMDSoASAFQAw%3D%3D', N'Thành phố của những giấc mơ', N'DocumentBT3..docx', CAST(13215321 AS Numeric(19, 0)), N'Thành phố Hà Nội',1000, N'Xã Thanh Xuân', 20, 9)
SET IDENTITY_INSERT [dbo].[land] OFF
GO

SET IDENTITY_INSERT [dbo].[asset_registration] ON 

INSERT [dbo].[asset_registration] ([document_id], [approval_date], [comments], [reason], [registration_date], [land_id], [statusid], [userid]) VALUES (1, NULL, NULL, NULL, CAST(N'2024-10-25T13:11:47.863' AS DateTime), 1, 8, 4)
INSERT [dbo].[asset_registration] ([document_id], [approval_date], [comments], [reason], [registration_date], [land_id], [statusid], [userid]) VALUES (2, NULL, NULL, NULL, CAST(N'2024-10-25T13:15:51.157' AS DateTime), 2, 8, 4)
INSERT [dbo].[asset_registration] ([document_id], [approval_date], [comments], [reason], [registration_date], [land_id], [statusid], [userid]) VALUES (3, NULL, NULL, NULL, CAST(N'2024-10-25T13:19:24.013' AS DateTime), 3, 8, 4)
INSERT [dbo].[asset_registration] ([document_id], [approval_date], [comments], [reason], [registration_date], [land_id], [statusid], [userid]) VALUES (4, NULL, NULL, NULL, CAST(N'2024-10-25T13:21:29.420' AS DateTime), 4, 8, 4	)
INSERT [dbo].[asset_registration] ([document_id], [approval_date], [comments], [reason], [registration_date], [land_id], [statusid], [userid]) VALUES (5, NULL, NULL, NULL, CAST(N'2024-10-25T13:27:29.570' AS DateTime), 5, 4, NULL)
SET IDENTITY_INSERT [dbo].[asset_registration] OFF
GO
SET IDENTITY_INSERT [dbo].[land_image] ON 

INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (1, N'Land.jpg', 1)
INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (2, N'Land_1.jpg', 1)
INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (3, N'land2.jpg', 1)
INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (4, N'land3.jpg', 2)
INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (5, N'land2.jpg', 2)
INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (6, N'land4.jpg', 3)
INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (7, N'Asset_Screenshot 2024-10-29 202205.png', 3)
INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (8, N'Asset_Screenshot 2024-10-21 160159.png', 4)
INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (9, N'Asset_Screenshot 2024-10-21 160141.png', 5)
SET IDENTITY_INSERT [dbo].[land_image] OFF
GO

SET IDENTITY_INSERT [dbo].[Auction] ON 
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (1, 1, CAST(N'2024-10-30T16:08:09.823' AS DateTime), CAST(N'2024-11-16T16:08:09.823' AS DateTime), 12, 0)
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (2, 2, CAST(N'2024-10-30T16:08:09.823' AS DateTime), CAST(N'2024-11-01T16:08:09.823' AS DateTime), 10, 0)
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (3, 3, CAST(N'2024-10-30T16:08:09.823' AS DateTime), CAST(N'2024-11-19T16:08:09.823' AS DateTime), 11, 0)
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (4, 4, CAST(N'2024-11-10T16:08:09.823' AS DateTime), CAST(N'2024-11-12T16:08:09.823' AS DateTime), 10, 0)
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (6, 4, CAST(N'2024-11-10T16:08:09.823' AS DateTime), CAST(N'2024-11-12T16:08:09.823' AS DateTime), 10, 0)
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (7, 4, CAST(N'2024-11-10T16:08:09.823' AS DateTime), CAST(N'2024-11-12T16:08:09.823' AS DateTime), 10, 0)


SET IDENTITY_INSERT [dbo].[Auction] OFF
GO

SET IDENTITY_INSERT [dbo].[Auction_Registration] ON 
INSERT [dbo].[Auction_Registration] (registrationid, auctionid, userid) values (1,1,6)
INSERT [dbo].[Auction_Registration] (registrationid, auctionid, userid) values (2,1,7)
INSERT [dbo].[Auction_Registration] (registrationid, auctionid, userid) values (3,2,7)
INSERT [dbo].[Auction_Registration] (registrationid, auctionid, userid) values (4,2,6)
INSERT [dbo].[Auction_Registration] (registrationid, auctionid, userid) values (5,3,7)
INSERT [dbo].[Auction_Registration] (registrationid, auctionid, userid) values (6,3,6)
SET IDENTITY_INSERT [dbo].[Auction_Registration] OFF
GO

SET IDENTITY_INSERT [dbo].[Payment] ON	
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (1, 6, 100, CAST(N'2024-10-29T16:08:09.823' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (2, 6, 100, CAST(N'2024-10-29T16:08:09.823' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (3, 6, 100, CAST(N'2024-10-29T16:08:09.823' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (4, 7, 100, CAST(N'2024-10-29T16:08:09.823' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (5, 7, 100, CAST(N'2024-10-29T16:08:09.823' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (6, 7, 100, CAST(N'2024-10-29T16:08:09.823' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (7, 8, 150, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (8, 8, 200, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (9, 9, 300, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (10, 10, 150, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (11, 10, 200, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (12, 11, 100, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (13, 11, 150, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (14, 6, 200, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (15, 6, 300, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (16, 6, 250, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (17, 6, 100, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (18, 6, 100, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (19, 6, 100, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [payment_date], [payment_description]) VALUES (20, 6, 100, CAST(N'2024-10-30T10:15:00' As datetime), N'phi tham gia')
SET IDENTITY_INSERT [dbo].[Payment] OFF

GO

SET IDENTITY_INSERT [dbo].[Wishlist] ON 
INSERT [dbo].[wishlist] (wishlist_id, auctionid, userid) values (1,1,6)
INSERT [dbo].[wishlist] (wishlist_id, auctionid, userid) values (2,2,6)
INSERT [dbo].[wishlist] (wishlist_id, auctionid, userid) values (3,3,6)
INSERT [dbo].[wishlist] (wishlist_id, auctionid, userid) values (4,1,7)
INSERT [dbo].[wishlist] (wishlist_id, auctionid, userid) values (5,2,7)
INSERT [dbo].[wishlist] (wishlist_id, auctionid, userid) values (6,3,7)
SET IDENTITY_INSERT [dbo].[Wishlist] OFF
GO

SET IDENTITY_INSERT [dbo].[Bids] ON 
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (1,2000,CAST(N'2024-10-30T17:08:09.823' AS DateTime),3)
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (2,2600,CAST(N'2024-10-30T17:12:09.823' AS DateTime),4)
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (3,2800,CAST(N'2024-10-30T19:08:09.823' AS DateTime),3)
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (4,3000,CAST(N'2024-10-30T19:30:09.823' AS DateTime),4)
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (5,1000, CAST(N'2024-10-31T16:08:09.823' AS DateTime),1)
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (6,2000, CAST(N'2024-10-31T16:18:09.823' AS DateTime),2)

SET IDENTITY_INSERT [dbo].[Bids] OFF
GO

