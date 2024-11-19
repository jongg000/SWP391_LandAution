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
INSERT INTO dbo.status (statusid, description, name) VALUES (4, NULL, N'Chờ xét duyệt'); --asset-- user
INSERT INTO dbo.status (statusid, description, name) VALUES (5, NULL, N'Đang đấu giá'); -- asset
INSERT INTO dbo.status (statusid, description, name) VALUES (6, NULL, N'Đã đấu giá'); -- asset
INSERT INTO dbo.status (statusid, description, name) VALUES (7, NULL, N'Đấu giá thành công'); -- asset
INSERT INTO dbo.status (statusid, description, name) VALUES (8, NULL, N'Chưa đấu giá'); --asset
INSERT INTO dbo.status (statusid, description, name) VALUES (9, NULL, N'Đã hủy'); --asset
INSERT INTO dbo.status (statusid, description, name) VALUES (10, NULL, N'Chưa bắt đầu'); -- auction
INSERT INTO dbo.status (statusid, description, name) VALUES (11, NULL, N'Đã kết thúc'); -- auction
INSERT INTO dbo.status (statusid, description, name) VALUES (12, NULL, N'Đang tiến hành'); -- auction
INSERT INTO dbo.status (statusid, description, name) VALUES (13, NULL, N'Đã thanh toán'); --asset
INSERT INTO dbo.status (statusid, description, name) VALUES (14, NULL, N'Thông tin không hợp lệ'); -- user
INSERT INTO dbo.status (statusid, description, name) VALUES (15, NULL, N'Chưa hoàn thành phí'); 
INSERT INTO dbo.status (statusid, description, name) VALUES (17, NULL, N'Đấu giá thất bại'); -- asset
INSERT INTO dbo.status (statusid, description, name) VALUES (16, NULL, N'Chờ đấu giá lại'); -- asset
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
INSERT [dbo].[land] ([land_id], [contact], [description], [district], [length], [location], [name], [path], [price], [province], [square], [ward], [width], [user_user_id]) VALUES (6, N'023568967', N'hay quá', N'Quận Thốt Nốt', 40, N'https://www.google.com/maps/place/FPT+University/@21.0075648,105.5227904,14z/data=!4m14!1m7!3m6!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!2sFPT+University!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm!3m5!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm?entry=ttu&g_ep=EgoyMDI0MTAxNS4wIKXMDSoASAFQAw%3D%3D', N'Đất 4', N'DocumentSWE201c_PE_2024_Summer_Sample..pdf', CAST(369 AS Numeric(19, 0)), N'Thành phố Cần Thơ', 800, N'Phường Tân Hưng', 20, 9)
INSERT [dbo].[land] ([land_id], [contact], [description], [district], [length], [location], [name], [path], [price], [province], [square], [ward], [width], [user_user_id]) VALUES (7, N'023568967', N'hay quá', N'Quận Thốt Nốt', 40, N'https://www.google.com/maps/place/FPT+University/@21.0075648,105.5227904,14z/data=!4m14!1m7!3m6!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!2sFPT+University!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm!3m5!1s0x3135abc60e7d3f19:0x2be9d7d0b5abcbf4!8m2!3d21.0124167!4d105.5252892!16s%2Fm%2F02rsytm?entry=ttu&g_ep=EgoyMDI0MTAxNS4wIKXMDSoASAFQAw%3D%3D', N'Đất 4', N'DocumentSWE201c_PE_2024_Summer_Sample..pdf', CAST(369 AS Numeric(19, 0)), N'Thành phố Cần Thơ', 800, N'Phường Tân Hưng', 20, 9)

SET IDENTITY_INSERT [dbo].[land] OFF
GO
SET IDENTITY_INSERT [dbo].[asset_registration] ON 

INSERT [dbo].[asset_registration] ([document_id], [approval_date], [comments], [reason], [registration_date], [land_id], [statusid], [userid]) VALUES (1, NULL, NULL, NULL, CAST(N'2024-10-25T13:11:47.863' AS DateTime), 1, 10, 4)
INSERT [dbo].[asset_registration] ([document_id], [approval_date], [comments], [reason], [registration_date], [land_id], [statusid], [userid]) VALUES (2, NULL, NULL, NULL, CAST(N'2024-10-25T13:15:51.157' AS DateTime), 2, 10, 4)
INSERT [dbo].[asset_registration] ([document_id], [approval_date], [comments], [reason], [registration_date], [land_id], [statusid], [userid]) VALUES (3, NULL, NULL, NULL, CAST(N'2024-10-25T13:19:24.013' AS DateTime), 3, 10, 4)
INSERT [dbo].[asset_registration] ([document_id], [approval_date], [comments], [reason], [registration_date], [land_id], [statusid], [userid]) VALUES (4, NULL, NULL, NULL, CAST(N'2024-10-25T13:21:29.420' AS DateTime), 4, 10, 4	)
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
INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (7, N'land4.jpg', 3)
INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (8, N'land4.jpg', 4)
INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (9, N'land4.jpg', 6)
    INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (10, N'land4.jpg', 7)
    INSERT [dbo].[land_image] ([image_id], [image_url], [land_land_id]) VALUES (11, N'land4.jpg', 5)
SET IDENTITY_INSERT [dbo].[land_image] OFF
GO
SET IDENTITY_INSERT [dbo].[Auction] ON 
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid],[deposit_time]) VALUES (1, 1, CAST(N'2024-10-30T16:08:09.823' AS DateTime), CAST(N'2024-11-16T16:08:09.823' AS DateTime), 12, 0,CAST(N'2024-11-30T16:08:09.823' AS DateTime))
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid],[deposit_time]) VALUES (2, 2, CAST(N'2024-10-30T16:08:09.823' AS DateTime), CAST(N'2024-11-01T16:08:09.823' AS DateTime), 10, 0,CAST(N'2024-11-30T16:08:09.823' AS DateTime))
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid],[deposit_time]) VALUES (3, 3, CAST(N'2024-10-30T16:08:09.823' AS DateTime), CAST(N'2024-11-19T16:08:09.823' AS DateTime), 11, 0,CAST(N'2024-11-30T16:08:09.823' AS DateTime))
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid],[deposit_time]) VALUES (4, 4, CAST(N'2024-11-10T16:08:09.823' AS DateTime), CAST(N'2024-11-12T16:08:09.823' AS DateTime), 10, 0,CAST(N'2024-11-30T16:08:09.823' AS DateTime))
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid],[deposit_time]) VALUES (5, 5, CAST(N'2024-11-10T16:08:09.823' AS DateTime), CAST(N'2024-11-12T16:08:09.823' AS DateTime), 10, 0,CAST(N'2024-11-30T16:08:09.823' AS DateTime))
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid],[deposit_time]) VALUES (6, 6, CAST(N'2024-11-10T16:08:09.823' AS DateTime), CAST(N'2024-11-12T16:08:09.823' AS DateTime), 10, 0,CAST(N'2024-11-30T16:08:09.823' AS DateTime))
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
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (10,400000,CAST(N'2024-10-30 20:30:09.823' AS DateTime),3)
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (1,2000,CAST(N'2024-10-30T17:08:09.823' AS DateTime),3)
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (2,2600,CAST(N'2024-10-30T17:12:09.823' AS DateTime),4)
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (3,2800,CAST(N'2024-10-30T19:08:09.823' AS DateTime),3)
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (4,3000,CAST(N'2024-10-30T19:30:09.823' AS DateTime),4)
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (5,1000, CAST(N'2024-10-31T16:08:09.823' AS DateTime),1)
INSERT [dbo].[bids] (bid_id, bid_amount, bid_time, registrationid) values (6,2000, CAST(N'2024-10-31T16:18:09.823' AS DateTime),2)

SET IDENTITY_INSERT [dbo].[Bids] OFF
GO
GO
SET IDENTITY_INSERT [dbo].[news] ON 

INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (1, N'Chia sẻ với báo chí sáng 28/4, bà Đỗ Thị Hồng Hạnh, Tổng giám đốc Công ty Đấu giá hợp danh Lạc Việt, cho biết, pháp nhân vận hành hệ thống đấu giá trực tuyến là Công ty CP Đấu giá Trực tuyến Lạc Việt do bà Đỗ Chung Thủy làm đại diện pháp luật với chức danh Tổng giám đốc
Bà Đỗ Thị Hồng Hạnh, Tổng giám đốc Công ty Đấu giá hợp danh Lạc Việt, giới thiệu về hệ thống đấu giá trực tuyến Lạc Việt.
“Trong khi nhiều hoạt động bị đình trệ, các doanh nghiệp ngừng hoạt động do ảnh hưởng của dịch Covid- 19, thì Lạc Việt và các cán bộ kỹ thuật đã làm việc liên tục để hoàn thiện hệ thống đấu giá trực tuyến này…” - bà Hạnh cho hay.
Cũng theo bà Hạnh, hiện đơn vị đã làm các thủ tục gửi đề án lên Sở Tư pháp Hà Nội thẩm định. Theo quy định, Sở Tư pháp sẽ thành lập Hội đồng thẩm định trước khi cấp phép.
“Bằng việc đưa hệ thống đấu giá trực tuyến đi vào hoạt động trong bối cảnh dịch Covid-19 đang có nhiều diễn biến phức tạp và khó lường, chúng tôi tin rằng đây là phương pháp sẽ góp phần không nhỏ thúc đẩy các hoạt động phát triển kinh doanh, sản xuất của các doanh nghiệp Việt trong giai đoạn sắp tới…” - bà Hạnh khẳng định.
', N'NewsScreenshot 2024-11-09 153729..png', N'09-11-2024 15:37:37', N'Công bố hệ thống đấu giá', 3)
INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (2, N'Căn cứ Luật đấu giá tài sản số 01/2016/QH14 ngày 17/11/2016;
Căn cứ Nghị định số 62/2017/NĐ-CP ngày 16/05/2017 của Chính phủ quy định chi tiết một số điều và biện pháp thi hành Luật đấu giá tài sản;
Căn cứ Quyết định số 163/QĐ-STP ngày 17/06/2020 của Sở Tư pháp thành phố Hà Nội về việc phê duyệt tổ chức đấu giá tài sản đủ điều kiện thực hiện hình thức đấu giá trực tuyến;
Căn cứ xu hướng phát triển công nghệ và quá trình vận hành thực tế.
Công ty Đấu giá hợp danh Lạc Việt là đơn vị đã được Sở Tư pháp thành phố Hà Nội phê duyệt đủ điều kiện thực hiện hình thức đấu giá trực tuyến theo Quyết định số 163/QĐ-STP ngày 17/06/2020 của Sở Tư pháp thành phố Hà Nội.

', N'NewsScreenshot 2024-11-09 153828..png', N'09-11-2024 15:38:33', N'Nâng cấp hệ thống đấu giá', 3)
INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (3, N'Tổ chức đấu giá tài sản: Công ty Đấu giá hợp danh Lạc Việt, địa chỉ: số 49 Văn Cao, phường Liễu Giai, quận Ba Đình, Hà Nội.
Người có tài sản đấu giá: Cục Điều tra chống buôn lậu - Tổng cục Hải quan, địa chỉ: Tầng 17 Tòa nhà Tổng cục Hải quan, đường Dương Đình Nghệ, phường Yên Hòa, quận Cầu Giấy, TP Hà Nội.
1.Tài sản đấu giá, giá khởi điểm, bước giá, tiền mua hồ sơ, tiền đặt trước:
2. Điều kiện, cách thức đăng ký, thời gian bán, thu hồ sơ đấu giá và địa điểm xem tài sản đấu giá:
Khách hàng đủ điều kiện đăng ký tham gia đấu giá, xem tài sản đấu giá theo lịch trình dưới đây:
    - Đăng ký tham gia đấu giá: Từ 08 giờ 00 phút ngày 04/11/2024 đến 17h00 ngày 12/11/2024 (trong giờ hành chính, trừ thứ 7 và chủ nhật) bằng cách sau:
+ Khách hàng đăng ký tài khoản và sử dụng tài khoản truy cập để đăng ký tham gia đấu giá trực tuyến trên Trang thông tin điện tử đấu giá trực tuyến của Công ty Đấu giá hợp danh Lạc Việt - lacvietauction.vn theo thời hạn quy định.
+ Khách hàng nộp tiền mua hồ sơ (phí đăng ký tham gia đấu giá) thông qua các hình thức thanh toán trực tuyến.
+ Khách hàng sau khi hoàn tất việc đăng ký tham gia đấu giá trực tuyến, tải các mẫu đơn đăng ký, giấy xác nhận hiện trạng tài sản trên trang lacvietauction.vn để nộp lại hồ sơ. Mỗi hồ sơ bao gồm:
++ Đơn đăng ký tham gia đấu giá; Giấy xác nhận đã tìm hiểu rõ về nguồn gốc, tình trạng pháp lý và hiện trạng của tài sản đấu giá (theo mẫu);
++ Đơn đăng ký xem tài sản (theo mẫu) (nếu có nhu cầu xem tài sản, khách hàng nộp lại trước 17h00 ngày 06/11/2024);

', N'NewsScreenshot 2024-11-09 153925..png', N'09-11-2024 15:39:30', N' Tổng cục Hải quan', 3)
INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (4, N'Tài sản đấu giá, giá khởi điểm, bước giá, tiền mua hồ sơ, tiền đặt trước:
Tài sản đấu giá: Lô Cột Anten viễn thông hư hỏng đã qua sử dụng của Viễn thông Hà Tĩnh, cụ thể:
- Số lượng:
+ 04 Cột Anten (01 Cột Anten dây co 42m, 02 Cột Anten dây co 45m và 01 Cột Anten tự đứng 35m) đã qua sử dụng cũ, hỏng không sử dụng được, còn nguyên trạng tại trạm chưa tháo dỡ tập kết tài sản.
+ Cột Anten đã tháo dỡ thu hội tập kết nhập kho tại Viễn thông tỉnh (khối lượng tương ứng 7.908 kg).
- Chất lượng: Đã qua sử dụng, kém chất lượng, đã hư hỏng.
- Giá khởi điểm: 129.848.000 đồng (Bằng chữ: Một trăm hai mươi chín triệu, tám trăm bốn tám nghìn đồng) (Giá đã bao gồm thuế VAT).
- Tiền mua hồ sơ tham gia đấu giá (trên hệ thống đấu giá trực tuyến được coi là “phí đăng ký tham gia đấu giá”): 200.000 đồng/Hồ sơ (Bằng chữ: Hai trăm nghìn đồng trên hồ sơ).
- Tiền đặt trước: 25.000.000 đồng (Bằng chữ: Hai mươi lăm triệu đồng).
- Bước giá: 2.000.000 đồng (Bằng chữ: Hai triệu đồng).
2. Điều kiện, cách thức đăng ký, thời gian bán, thu hồ sơ đấu giá và địa điểm xem tài sản đấu giá:
Các tổ chức, cá nhân có nhu cầu tham gia đấu giá có đủ điều kiện và năng lực theo Quy chế đấu giá đăng ký tham gia đấu giá, xem tài sản đấu giá theo lịch trình dưới đây:
    - Đăng ký tham gia đấu giá: Từ 08h00 ngày 14/10/2024 đến 17h00 ngày 21/10/2024 bằng cách sau:
+ Khách hàng đăng ký tài khoản và sử dụng tài khoản truy cập để đăng ký tham gia đấu giá trực tuyến trên Trang thông tin điện tử đấu giá trực tuyến của Công ty Đấu giá hợp danh Lạc Việt - lacvietauction.vn.
+ Khách hàng nộp tiền mua hồ sơ (phí đăng ký tham gia đấu giá) thông qua hình thức chuyển khoản vào tài khoản công ty (tại Mục 3), nội dung chuyển khoản: “(Họ tên người tham gia đấu giá/Tên tổ chức)(Số CMND/CCCD/HC/ĐKKD) nộp phí đăng ký TGĐG Anten của VNPT Hà Tĩnh”.

', N'NewsScreenshot 2024-11-09 154013..png', N'09-11-2024 15:40:18', N' Viễn thông Hà Tĩnh', 3)
INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (5, N' 
Tài sản đấu giá, giá khởi điểm, bước giá, tiền mua hồ sơ, tiền đặt trước:
Tài sản đấu giá: Lô TSCĐ là Anten, Accu, MMTB các loại và vật tư hư hỏng ngành viễn thông của Viễn thông Bình Định, cụ thể:
- Số lượng: (chi tiết tại thông báo đấu giá).
- Chất lượng: Tài sản hư hỏng không còn sử dụng, đã thu hồi về kho Cầu Đôi; riêng các máy biến áp đã tiến hành cắt điện khỏi hệ thống điện lưới quốc gia, hiện đang ở trên trụ tại các địa điểm theo Danh mục đính kèm.
- Giá khởi điểm: 479.000.000 đồng (Bằng chữ: Bốn trăm bảy mươi chín triệu đồng) (đã bao gồm thuế VAT).
- Tiền mua hồ sơ tham gia đấu giá (trên hệ thống đấu giá trực tuyến được coi là “phí đăng ký tham gia đấu giá”): 200.000 đồng/Hồ sơ (Bằng chữ: Hai trăm nghìn đồng trên hồ sơ).
- Tiền đặt trước: 90.000.000 đồng (Bằng chữ: Chín mươi triệu đồng).
- Bước giá: 30.000.000 đồng/bước giá (Bằng chữ: Ba mươi triệu đồng trên bước giá).
2. Điều kiện, cách thức đăng ký, thời gian bán, thu hồ sơ đấu giá và địa điểm xem tài sản đấu giá:
Các tổ chức, cá nhân có nhu cầu tham gia đấu giá có đủ điều kiện và năng lực theo Quy chế đấu giá.
Khách hàng đăng ký tham gia đấu giá phải đáp ứng đủ điều kiện về xử lý chất thải nguy hại, có Giấy phép xử lý chất thải nguy hại (CTNH) còn thời hạn ít nhất 06 tháng kể từ ngày kết thúc cuộc đấu giá, địa bàn được phép hoạt động có bao gồm tỉnh Bình Định, có mã chất thải phù hợp với danh mục tài sản đấu giá gồm: Ắc quy chì thải (mã CTNH 19 06 01) (*) (Trường hợp có sự nghi ngờ về hồ sơ Giấy phép xử lý chất thải nguy hại, Công ty Đấu giá hợp danh Lạc Việt yêu cầu khách hàng đối chiếu bản gốc để so sánh).
Khách hàng đủ điều kiện đăng ký tham gia đấu giá, xem tài sản đấu giá theo lịch trình dưới đây:
    - Đăng ký tham gia đấu giá: Từ 08h00 ngày 07/10/2024 đến 17h00 ngày 14/10/2024 bằng cách sau:
+ Khách hàng đăng ký tài khoản và sử dụng tài khoản truy cập để đăng ký tham gia đấu giá trực tuyến trên Trang thông tin điện tử đấu giá trực tuyến của Công ty Đấu giá hợp danh Lạc Việt - lacvietauction.vn.
+ Khách hàng nộp tiền mua hồ sơ (phí đăng ký tham gia đấu giá) thông qua hình thức chuyển khoản vào tài khoản công ty (tại Mục 3), nội dung chuyển khoản: “(Họ tên người tham gia đấu giá/Tên tổ chức)(Số CMND/CCCD/HC/ĐKKD) nộp phí đăng ký TGĐG Accu của VNPT Bình Định”.

', N'NewsScreenshot 2024-11-09 154051..png', N'09-11-2024 15:40:58', N'Viễn thông Bình Định', 3)
INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (6, N'Các tổ chức, cá nhân có nhu cầu tham gia đấu giá có đủ điều kiện và năng lực theo Quy chế đấu giá đã ban hành.
Khách hàng đăng ký đấu giá Lô 2 phải đáp ứng đủ điều kiện về xử lý chất thải nguy hại, có Giấy phép xử lý chất thải nguy hại (CTNH) còn hiệu lực thời hạn ít nhất 06 tháng kể từ ngày kết thúc cuộc đấu giá, địa bàn được phép hoạt động có bao gồm tỉnh Nghệ An, có mã chất thải phù hợp với danh mục tài sản đấu giá gồm: Ắc quy chì thải (mã CTNH 19 06 01) (*). Người đăng ký tham gia đấu giá phải nộp lại bản sao y có chứng thực Giấy phép xử lý chất thải nguy hại (Công ty có thể yêu cầu đối chiếu bản gốc khi cần).
Khách hàng đăng ký tham gia đấu giá, xem tài sản đấu giá theo lịch trình dưới đây:
    - Đăng ký tham gia đấu giá: Từ 08h00 ngày 16/09/2024 đến 17h00 ngày 23/09/2024 bằng cách sau:
+ Khách hàng đăng ký tài khoản và sử dụng tài khoản truy cập để đăng ký tham gia đấu giá trực tuyến trên Trang thông tin điện tử đấu giá trực tuyến của Công ty Đấu giá hợp danh Lạc Việt - lacvietauction.vn.
+ Khách hàng nộp tiền mua hồ sơ (phí đăng ký tham gia đấu giá) thông qua hình thức chuyển khoản vào tài khoản công ty (tại Mục 3), nội dung chuyển khoản: “(Họ tên người tham gia đấu giá/Tên tổ chức)(Số CMND/CCCD/HC/ĐKKD) nộp phí đăng ký TGĐG Lô ..(1/2).. (Vật tư/Ắc quy) của VNPT Nghệ An”.
+ Khách hàng sau khi hoàn tất việc đăng ký tham gia đấu giá trực tuyến tải các mẫu đơn đăng ký, giấy xác nhận hiện trạng tài sản, cam kết xử lý chất thải nguy hại trên trang lacvietauction.vn tại mục “Tài liệu liên quan” của tài sản đấu giá đó, điền đầy đủ thông tin yêu cầu kê khai, rồi nộp hồ sơ đăng ký tham gia đấu giá. Mỗi hồ sơ bao gồm:
++ Đơn đăng ký tham gia đấu giá;
++ Giấy xác nhận hiện trạng và pháp lý tài sản (Đôi với khách hàng đăng ký Lô 1);
++ Cam kết đủ điều kiện xử lý chất thải nguy hại (Đối với khách hàng đăng ký Lô 2);
++ Đơn đăng ký xem tài sản (nếu có nhu cầu xem tài sản, khách hàng nộp lại trước 17h00 ngày 18/09/2024);
++ Bản sao y có công chứng/chứng thực các giấy tờ: Căn cước công dân/Hộ chiếu (đối với cá nhân); Đăng ký kinh doanh của doanh nghiệp, Căn cước công dân/Hộ chiếu của người đại diện pháp luật (đối với tổ chức); Giấy phép xử lý chất thải có mã chất thải nguy hại còn thời hạn phù hợp với danh mục tài sản đấu giá (mục (*)) (Đối với khách hàng đăng ký Lô 2).

', N'NewsScreenshot 2024-11-09 154141..png', N'09-11-2024 15:41:51', N'Vật tư, công cụ dụng cụ', 3)
INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (7, N'Quân đội Hàn Quốc cáo buộc Triều Tiên tiến hành hoạt động gây nhiễu GPS, làm ảnh hưởng đến tàu thuyền và máy bay dân sự ở Hoàng Hải.
"Triều Tiên đã tiến hành các hành động mang tính khiêu khích như gây nhiễu GPS ở khu vực Haeju và Kaesong trong ngày 8-9/11", Hội đồng Tham mưu trưởng Liên quân Hàn Quốc (JCS) cho biết, thêm rằng một số tàu biển và hàng chục máy bay dân sự đang gặp "tình trạng hoạt động bị gián đoạn".
Quân đội Hàn Quốc đã phát cảnh báo tới các tàu và máy bay hoạt động trên Hoàng Hải, yêu cầu kíp vận hành đề cao cảnh giác trước những động thái như vậy.
"Chúng tôi kêu gọi Triều Tiên ngừng ngay lập tức các hành động khiêu khích và cảnh báo họ sẽ phải chịu trách nhiệm về mọi vấn đề phát sinh từ quá trình này", tuyên bố của JCS có đoạn.
Giới chức Triều Tiên chưa bình luận về thông tin.
', N'NewsScreenshot 2024-11-09 154334..png', N'09-11-2024 15:43:39', N'Gây nhiễu tín hiệu GPS', 3)
INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (8, N'Ông Donald Trump đã đánh bại nhanh chóng ứng viên đảng Dân chủ Kamala Harris trong cuộc bầu cử tổng thống ngày 5/11 và chuẩn bị trở lại Nhà Trắng như ông từng tuyên bố.
Ngay cả khi các hãng thông tấn lớn chưa xướng tên ông Trump là người đắc cử, Tổng thư ký NATO Mark Rutte đã lên tiếng chúc mừng ứng viên đảng Cộng hòa, khi chứng kiến Trump liên tiếp thắng ở các bang chiến trường.
"Sự lãnh đạo của ông ấy một lần nữa là chìa khóa giữ cho liên minh của chúng ta mạnh mẽ. Tôi trông đợi được làm việc với ông ấy để thúc đẩy hòa bình thông qua NATO", ông Rutte nói.
Lãnh đạo Pháp, thành viên chủ chốt của NATO ở châu Âu, cũng nhanh chóng chúc mừng "Tổng thống Donald Trump". "Tôi sẵn sàng làm việc với ông Trump bằng sự tôn trọng và hoài bão như chúng ta từng làm trong 4 năm đó", Tổng thống Pháp EmmanuelMacron ngày 6/1 viết trên X. "Mối quan hệ của chúng tôi dựa trên niềm tin đôi bên và hợp tác vì hòa bình, thịnh vượng hơn nữa".
Thủ tướng Anh Keir Starmer thì gọi đây là "chiến thắng lịch sử" của ông Trump, đồng thời khẳng định tính bền chặt của mối quan hệ Anh - Mỹ.
', N'NewsScreenshot 2024-11-09 154405..png', N'09-11-2024 15:44:09', N' ông Trump trở lại', 3)
INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (9, N'Đảng Cộng hòa giành 53 ghế tại Thượng viện, cũng như dẫn trước trong cuộc đua ở Hạ viện, nhiều khả năng sẽ kiểm soát lưỡng viện quốc hội vào năm sau.
Truyền thông Mỹ ngày 8/11 xác nhận ứng viên thượng nghị sĩ Cộng hòa Dave McCormick đánh bại đối thủ Dân chủ Bob Casey tại Pennsylvania, giúp phe Cộng hòa giành được thêm một ghế tại Thượng viện.
Đảng của tổng thống đắc cử Donald Trump tới nay giành được ít nhất 53 trên tổng số 100 ghế Thượng viện, vượt qua mức quá bán 50 ghế để nắm quyền kiểm soát cơ quan này. Đây là lần đầu tiên đảng Cộng hòa giành lại Thượng viện trong 4 năm qua.
Số ghế đảng Cộng hòa nắm ở Thượng viện có thể tăng lên tới 55, nếu các ứng viên đảng này tiếp tục thắng trong hai cuộc đua đang rất sít sao ở Arizona và Nevada.
', N'NewsScreenshot 2024-11-09 154442..png', N'09-11-2024 15:44:48', N'Phe Cộng hòa giành lại Thượng viện', 3)
INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (10, N'Ngoài lựa chọn tổng thống, cử tri Mỹ ngày 5/11 còn bỏ phiếu bầu lại toàn bộ Hạ viện và hơn 30 ghế Thượng viện, có thể thay đổi thế kiểm soát tại quốc hội.
Trên lá phiếu ngày 5/11, ngoài các ứng viên cho ghế tổng thống, cử tri Mỹ còn lựa chọn người sẽ đại diện cho bang mình tại Hạ viện và Thượng viện Mỹ, điều sẽ định đoạt cục diện tại quốc hội trong vài năm tới. Họ còn bầu thống đốc tại 13 bang và vùng lãnh thổ, bỏ phiếu về 159 dự thảo luật, quy định tại 41 bang.
Quốc hội Mỹ gồm Hạ viện và Thượng viện, là nhánh lập pháp trong chính quyền liên bang, phụ trách soạn thảo và ban hành các luật. Hạ viện gồm 435 ghế nghị sĩ có nhiệm kỳ hai năm, Thượng viện gồm 100 ghế thượng nghị sĩ, nhiệm kỳ 6 năm.
Tại Thượng viện, đảng Dân chủ đang giữ thế đa số sít sao với 51 ghế, trong đó ba ghế là nghị sĩ độc lập nhưng chọn ủng hộ đảng này.
Các thượng nghị sĩ được chia thành ba nhóm I, II và III, với số thành viên lần lượt là 33, 33 và 34. Nhóm I được bầu trong cuộc bầu cử giữa kỳ tháng 11/2018, kết thúc nhiệm kỳ vào tháng 1/2025. Nhóm II được bầu trong cuộc bầu cử tổng thống tháng 11/2020, hết nhiệm kỳ vào đầu năm 2027. Nhóm III được bầu trong cuộc bầu cử giữa kỳ tháng 11/2022, nhiệm kỳ đến tháng 1/2029.
', N'NewsScreenshot 2024-11-09 154523..png', N'09-11-2024 15:45:30', N'Viện quốc hội Mỹ', 3)
INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (11, N'Tổng thống Lukashenko cho biết Belarus sẽ đề cử ông Trump cho giải Nobel Hòa bình, nếu ông có thể chấm dứt xung đột Ukraine như đã hứa.
"Ông Trump đã hứa chấm dứt xung đột ở Ukraine và Trung Đông. Mỹ không phải Belarus, họ có thể quên, nhưng chúng tôi hy vọng ông Trump sẽ giữ lời. Nếu ông Trump làm được, Belarus sẽ đề xuất trao giải Nobel Hòa bình cho ông ấy", Tổng thống Belarus Alexander Lukashenko tuyên bố ngày 7/11.
Ông Lukashenko lưu ý thêm không phải mọi thứ "đều phụ thuộc vào ông Trump do hòa bình đòi hỏi hai bên tham gia".
Bình luận về kết quả bầu cử tổng thống Mỹ, ông Lukashenko nói không quan tâm ai thắng, dù tin rằng nước Mỹ "sẵn sàng có tổng thống da màu mới, nhưng chưa sẵn sàng có nữ tổng thống".
', N'NewsScreenshot 2024-11-09 155152..png', N'09-11-2024 15:51:57', N'Belarus hứa đề cử giải Nobel ', 24)
INSERT [dbo].[news] ([news_id], [content], [image], [time], [title], [userid]) VALUES (12, N'Donald Trump Jr., con trai cả ông Trump, đăng "đơn nghỉ việc" dí dỏm của bố gửi đến chuỗi nhà hàng McDonald''s, nơi Tổng thống đắc cử Mỹ từng chiên khoai.
"Xin hãy chấp nhận thư này như thông báo chính thức về việc tôi từ chức khỏi vị trí của mình tại McDonald''s, từ ngày 20/1/2025", Donald Trump Jr. hôm nay đăng trên mạng xã hội hình ảnh bức thư có chèn tên, chữ ký của Donald Trump gửi phòng nhân sự McDonald''s.
Ngày "nghỉ việc" trong bức thư trùng với ngày ông Trump nhậm chức Tổng thống Mỹ thứ 47. Ông Trump đắc cử tổng thống sau hàng loạt chiến thắng ở các bang chiến trường trong ngày bầu cử 5/11, trở thành tổng thống Mỹ đầu tiên đảm nhận hai nhiệm kỳ không liên tiếp trong thế kỷ này.
', N'NewsScreenshot 2024-11-09 155223..png', N'09-11-2024 15:52:28', N'Con trai ông Trump đăng ảnh', 24)
SET IDENTITY_INSERT [dbo].[news] OFF
GO

