
SET IDENTITY_INSERT [dbo].[Roles] ON 
INSERT [dbo].[Roles] ([RoleID], [Role_Name]) VALUES (1, N'ROLE_CUSTOMER')
INSERT [dbo].[Roles] ([RoleID], [Role_Name]) VALUES (2, N'ROLE_ADMIN')
INSERT [dbo].[Roles] ([RoleID], [Role_Name]) VALUES (3, N'ROLE_STAFF')
INSERT [dbo].[Roles] ([RoleID], [Role_Name]) VALUES (4, N'ROLE_CUSTOMER_CARE')
SET IDENTITY_INSERT [dbo].[Roles] OFF
GO
SET IDENTITY_INSERT [dbo].[status] ON 
INSERT INTO dbo.status (statusid, description, name) VALUES (1, NULL, N'Chưa xác minh');
INSERT INTO dbo.status (statusid, description, name) VALUES (2, NULL, N'Đã xác minh');
INSERT INTO dbo.status (statusid, description, name) VALUES (3, NULL, N'Khóa');
INSERT INTO dbo.status (statusid, description, name) VALUES (4, NULL, N'Chờ xét duyệt');
INSERT INTO dbo.status (statusid, description, name) VALUES (5, NULL, N'Đang đấu giá');
INSERT INTO dbo.status (statusid, description, name) VALUES (6, NULL, N'Đã đấu giá');
INSERT INTO dbo.status (statusid, description, name) VALUES (7, NULL, N'Đấu giá thành công');
INSERT INTO dbo.status (statusid, description, name) VALUES (8, NULL, N'Chưa đấu giá');
INSERT INTO dbo.status (statusid, description, name) VALUES (9, NULL, N'Đã hủy');
INSERT INTO dbo.status (statusid, description, name) VALUES (10, NULL, N'Chưa hoàn thành');
INSERT INTO dbo.status (statusid, description, name) VALUES (11, NULL, N'Đã hoàn thành');
INSERT INTO dbo.status (statusid, description, name) VALUES (12, NULL, N'Đang tiến hành');
SET IDENTITY_INSERT [dbo].[status] OFF
GO

SET IDENTITY_INSERT [dbo].[Users] ON 
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid]) VALUES (1, NULL, NULL, NULL, N'chung0110204@gmail.com', N'Lê Đoàn Đức', N'1', N'Chung', NULL, NULL, N'0123456789', N'$2a$10$XDVenY.7wuqc4V/LRDhEi.KYbv5A3HwxTikEw.pF9UuCUEPxdbjfy', N'0358210806', CAST(0 AS Numeric(19, 0)), 2, 1)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid]) VALUES (2, 'Adress1', 'https://images2.thanhnien.vn/528068263637045248/2023/4/23/dv-truc-anh-1682251770601542649392.jpeg',CAST(N'1990-01-01' AS Date), N'user@gmail.com', N'User', N'1', N'1', 'staff_avatar2.png', 'staff_avatar2.png', N'0123456789', N'$2a$10$XDVenY.7wuqc4V/LRDhEi.KYbv5A3HwxTikEw.pF9UuCUEPxdbjfy', N'0358210806', CAST(0 AS Numeric(19, 0)), 1, 2)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid]) VALUES (3, NULL, NULL, NULL, N'chung0110203@gmail.com', N'Lê Đoàn Đức', N'1', N'Chung', NULL, NULL, N'0123456789', N'$2a$10$XDVenY.7wuqc4V/LRDhEi.KYbv5A3HwxTikEw.pF9UuCUEPxdbjfy', N'0358210806', CAST(0 AS Numeric(19, 0)), 2, 1)
INSERT [dbo].[users] ([user_id], [address], [avatar], [dob], [email], [first_name], [gender], [last_name], [national_back_image], [national_front_image], [nationalid], [password], [phone_number], [refund_money], [roleid], [statusid]) VALUES (4, 'Adress1', 'https://images2.thanhnien.vn/528068263637045248/2023/4/23/dv-truc-anh-1682251770601542649392.jpeg',CAST(N'1990-01-01' AS Date), N'user1@gmail.com', N'User', N'1', N'2', 'staff_avatar2.png', 'staff_avatar2.png', N'0123456788', N'$2a$10$XDVenY.7wuqc4V/LRDhEi.KYbv5A3HwxTikEw.pF9UuCUEPxdbjfy', N'0358210807', CAST(0 AS Numeric(19, 0)), 1, 2)

SET IDENTITY_INSERT [dbo].[Users] OFF
GO
SET IDENTITY_INSERT [dbo].[Land] ON 
INSERT INTO dbo.land ([land_id],[name],[contact], description, [district], location, path, price, province, ward, user_user_id, length, square, width)
VALUES 
(1,'Nha Hat cua nhung giac mo','0123456789', 'Beautiful land near river', 'District 1', '10.1234, 106.1234', '/path/to/location1', 5000000, 'Ho Chi Minh', 'Ward 1', 1,20.0 , 400.00 , 20.0),
(2,'Land2','0987654321', 'Land with mountain view', 'District 2', '10.5678, 106.5678', '/path/to/location2', 7000000, 'Da Nang', 'Ward 2', 1,20.0 , 400.00 , 20.0),
(3,'Land3','0123984756', 'Land near city center', 'District 3', '10.9101, 106.9101', '/path/to/location3', 10000000, 'Hanoi', 'Ward 3', 3,20.0 , 400.00 , 20.0),
(4,'Land4','0172839456', 'Agricultural land', 'District 4', '11.1234, 107.1234', '/path/to/location4', 3000000, 'Can Tho', 'Ward 4', 3,20.0 , 400.00 , 20.0),
(5,'Land5','0192837465', 'Commercial land', 'District 5', '12.1234, 108.1234', '/path/to/location5', 12000000, 'Binh Duong', 'Ward 5', 1,20.0 , 400.00 , 20.0);
SET IDENTITY_INSERT [dbo].[Land] OFF
GO
SET IDENTITY_INSERT [dbo].[Land_Image] ON 
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL]) VALUES (1, 1, N'https://live.staticflickr.com/5214/5537865137_7809faf72d.jpg')
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL]) VALUES (2, 2, N'https://live.staticflickr.com/5176/5538426974_7799968305.jpg')
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL]) VALUES (3, 3, N'https://live.staticflickr.com/5176/5538426974_7799968305.jpg')
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL]) VALUES (4, 4, N'https://media-cdn-v2.laodong.vn/storage/newsportal/2023/8/26/1233821/Giai-Nhi-1--Nang-Tre.jpg')
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL]) VALUES (5, 1, N'https://media-cdn-v2.laodong.vn/storage/newsportal/2023/8/26/1233821/Giai-Nhi-1--Nang-Tre.jpg')
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL]) VALUES (6, 1, N'https://media-cdn-v2.laodong.vn/storage/newsportal/2023/8/26/1233821/Giai-Nhi---Dem-Ngan-.jpg')
SET IDENTITY_INSERT [dbo].[Land_Image] OFF
GO
SET IDENTITY_INSERT [dbo].[Auction] ON 
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (1, 1, CAST(N'2024-10-09T16:08:09.823' AS DateTime), CAST(N'2024-11-16T16:08:09.823' AS DateTime), 12, 0)
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (2, 2, CAST(N'2024-10-09T16:08:09.823' AS DateTime), CAST(N'2024-10-14T16:08:09.823' AS DateTime), 10, 0)
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (3, 3, CAST(N'2024-10-09T16:08:09.823' AS DateTime), CAST(N'2024-10-19T16:08:09.823' AS DateTime), 11, 0)
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (4, 4, CAST(N'2024-10-09T16:08:09.823' AS DateTime), CAST(N'2024-11-12T16:08:09.823' AS DateTime), 10, 0)
SET IDENTITY_INSERT [dbo].[Auction] OFF
GO
	SET IDENTITY_INSERT [dbo].[Asset_Registration] ON 
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments], [reason],[registration_date]) VALUES (1, 1, 5, 4, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 1','Reason 1', CAST(N'2024-10-09T16:08:06.653' AS DateTime))
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments], [reason],[registration_date]) VALUES (2, 2, 4, 4, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 2','Reason 1', CAST(N'2024-10-09T16:08:06.653' AS DateTime))
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments], [reason],[registration_date]) VALUES (3, 3, 6, 4, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 3','Reason 1', CAST(N'2024-10-09T16:08:06.653' AS DateTime))
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments], [reason],[registration_date]) VALUES (4, 4, 5, 4, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 4','Reason 1', CAST(N'2024-10-09T16:08:06.653' AS DateTime))
SET IDENTITY_INSERT [dbo].[Asset_Registration] OFF






SET IDENTITY_INSERT [dbo].[Payment] ON 

INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [StatusID], [AuctionID], payment_date, payment_description) VALUES (1, 1, 100, 1, 1)
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [StatusID], [AuctionID]) VALUES (2, 2, 200, 1, 2)
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [StatusID], [AuctionID]) VALUES (3, 3, 300, 2, 3)
INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [StatusID], [AuctionID]) VALUES (4, 4, 400, 2, 4)
SET IDENTITY_INSERT [dbo].[Payment] OFF
GO
SET IDENTITY_INSERT [dbo].[News] ON 
INSERT [dbo].[News] ([News_ID], [Title], [Content], [Image], [UserID], [Time]) VALUES (1, N'News 1', N'This is the content for news 1.', N'http://example.com/news1.jpg', 1, CAST(N'2024-10-09T16:08:21.970' AS DateTime))
INSERT [dbo].[News] ([News_ID], [Title], [Content], [Image], [UserID], [Time]) VALUES (2, N'News 2', N'This is the content for news 2.', N'http://example.com/news2.jpg', 2, CAST(N'2024-10-09T16:08:21.970' AS DateTime))
INSERT [dbo].[News] ([News_ID], [Title], [Content], [Image], [UserID], [Time]) VALUES (3, N'News 3', N'This is the content for news 3.', N'http://example.com/news3.jpg', 3, CAST(N'2024-10-09T16:08:21.970' AS DateTime))
INSERT [dbo].[News] ([News_ID], [Title], [Content], [Image], [UserID], [Time]) VALUES (4, N'News 4', N'This is the content for news 4.', N'http://example.com/news4.jpg', 4, CAST(N'2024-10-09T16:08:21.970' AS DateTime))
SET IDENTITY_INSERT [dbo].[News] OFF
GO
SET IDENTITY_INSERT [dbo].[Task] ON 

INSERT [dbo].[Task] ([Task_ID], [UserID], [AuctionID], [request_time], [statusID], [task_name], [description]) VALUES (1, 1, 1, CAST(N'2024-10-09T16:08:29.713' AS DateTime), 1, N'Task 1', N'Description for task 1.')
INSERT [dbo].[Task] ([Task_ID], [UserID], [AuctionID], [request_time], [statusID], [task_name], [description]) VALUES (2, 2, 2, CAST(N'2024-10-09T16:08:29.713' AS DateTime), 1, N'Task 2', N'Description for task 2.')
INSERT [dbo].[Task] ([Task_ID], [UserID], [AuctionID], [request_time], [statusID], [task_name], [description]) VALUES (3, 3, 3, CAST(N'2024-10-09T16:08:29.713' AS DateTime), 2, N'Task 3', N'Description for task 3.')
INSERT [dbo].[Task] ([Task_ID], [UserID], [AuctionID], [request_time], [statusID], [task_name], [description]) VALUES (4, 4, 4, CAST(N'2024-10-09T16:08:29.713' AS DateTime), 2, N'Task 4', N'Description for task 4.')
SET IDENTITY_INSERT [dbo].[Task] OFF
GO
SET IDENTITY_INSERT [dbo].[Wishlist] ON 
SET IDENTITY_INSERT [dbo].[Wishlist] OFF
GO
SET IDENTITY_INSERT [dbo].[Notification] ON 
INSERT [dbo].[Notification] ([Notification_ID], [Title], [Content]) VALUES (1, N'Notification 1', N'Content for notification 1.')
INSERT [dbo].[Notification] ([Notification_ID], [Title], [Content]) VALUES (2, N'Notification 2', N'Content for notification 2.')
INSERT [dbo].[Notification] ([Notification_ID], [Title], [Content]) VALUES (3, N'Notification 3', N'Content for notification 3.')
INSERT [dbo].[Notification] ([Notification_ID], [Title], [Content]) VALUES (4, N'Notification 4', N'Content for notification 4.')
SET IDENTITY_INSERT [dbo].[Notification] OFF
	SET IDENTITY_INSERT [dbo].[Asset_Registration] ON 
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments], [reason],[registration_date]) VALUES (1, 1, 5, 4, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 1','Reason 1', CAST(N'2024-10-09T16:08:06.653' AS DateTime))
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments], [reason],[registration_date]) VALUES (2, 2, 4, 4, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 2','Reason 1', CAST(N'2024-10-09T16:08:06.653' AS DateTime))
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments], [reason],[registration_date]) VALUES (3, 3, 6, 4, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 3','Reason 1', CAST(N'2024-10-09T16:08:06.653' AS DateTime))
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments], [reason],[registration_date]) VALUES (4, 4, 5, 4, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 4','Reason 1', CAST(N'2024-10-09T16:08:06.653' AS DateTime))
SET IDENTITY_INSERT [dbo].[Asset_Registration] OFF

SET IDENTITY_INSERT [dbo].[Auction_Change_Log] ON 

INSERT [dbo].[Auction_Change_Log] ([log_id], [AuctionID], [action_type], [change_time], [reason], [description]) VALUES (1, 1, N'Create', CAST(N'2024-10-09T16:08:12.420' AS DateTime), N'Initial creation', N'Auction created successfully')
INSERT [dbo].[Auction_Change_Log] ([log_id], [AuctionID], [action_type], [change_time], [reason], [description]) VALUES (2, 2, N'Update', CAST(N'2024-10-09T16:08:12.420' AS DateTime), N'Status update', N'Auction status changed to active')
INSERT [dbo].[Auction_Change_Log] ([log_id], [AuctionID], [action_type], [change_time], [reason], [description]) VALUES (3, 3, N'Cancel', CAST(N'2024-10-09T16:08:12.420' AS DateTime), N'Cancellation', N'Auction cancelled due to unforeseen circumstances')
INSERT [dbo].[Auction_Change_Log] ([log_id], [AuctionID], [action_type], [change_time], [reason], [description]) VALUES (4, 4, N'Update', CAST(N'2024-10-09T16:08:12.420' AS DateTime), N'Price update', N'Highest bid updated')
SET IDENTITY_INSERT [dbo].[Auction_Change_Log] OFF
GO
SET IDENTITY_INSERT [dbo].[Auction_Registration] ON 
SET IDENTITY_INSERT [dbo].[Auction_Registration] OFF
GO
SET IDENTITY_INSERT [dbo].[Bids] ON 
SET IDENTITY_INSERT [dbo].[Bids] OFF
GO

