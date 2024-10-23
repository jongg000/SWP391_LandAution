
SET IDENTITY_INSERT [dbo].[Roles] ON 
INSERT [dbo].[Roles] ([RoleID], [Role_Name]) VALUES (1, N'ROLE_CUSTOMER')
INSERT [dbo].[Roles] ([RoleID], [Role_Name]) VALUES (2, N'ROLE_ADMIN')
INSERT [dbo].[Roles] ([RoleID], [Role_Name]) VALUES (3, N'ROLE_STAFF')
INSERT [dbo].[Roles] ([RoleID], [Role_Name]) VALUES (4, N'ROLE_CUSTOMER_CARE')
SET IDENTITY_INSERT [dbo].[Roles] OFF
GO
SET IDENTITY_INSERT [dbo].[Status] ON 
INSERT [dbo].[Status] ([StatusID], [Name], [Description]) VALUES (1, N'unverified ', NULL)
INSERT [dbo].[Status] ([StatusID], [Name], [Description]) VALUES (2, N'verified ', NULL)
INSERT [dbo].[Status] ([StatusID], [Name], [Description]) VALUES (3, N'ban', NULL)
SET IDENTITY_INSERT [dbo].[Status] OFF
GO
SET IDENTITY_INSERT [dbo].[Users] ON 
INSERT [dbo].[Users] ([User_ID], [User_name], [Password], [Name], [Email], [Phone_Number], [Avatar], [RoleID], [Dob], [NationalID], [Wallet], [StatusID]) VALUES (1, N'admin', N'password123', N'Admin User', N'admin@example.com', N'123456789', N'admin_avatar.png', 1, CAST(N'1990-01-01' AS Date), 123456789, 0, NULL)
INSERT [dbo].[Users] ([User_ID], [User_name], [Password], [Name], [Email], [Phone_Number], [Avatar], [RoleID], [Dob], [NationalID], [Wallet], [StatusID]) VALUES (2, N'customercare', N'password123', N'Customer care ', N'customercare@example.com', N'987654321', N'staff_avatar.png', 3, CAST(N'1992-05-05' AS Date), 987654321, 0, NULL)
INSERT [dbo].[Users] ([User_ID], [User_name], [Password], [Name], [Email], [Phone_Number], [Avatar], [RoleID], [Dob], [NationalID], [Wallet], [StatusID]) VALUES (3, N'customer1', N'password123', N'Customer User 1', N'customer1@example.com', N'111222333', N'customer_avatar.png', 4, CAST(N'1995-10-10' AS Date), 111222333, 0, NULL)
INSERT [dbo].[Users] ([User_ID], [User_name], [Password], [Name], [Email], [Phone_Number], [Avatar], [RoleID], [Dob], [NationalID], [Wallet], [StatusID]) VALUES (4, N'customer2', N'password123', N'Customer User 2', N'customer2@example.com', N'444555666', N'customer_avatar2.png', 4, CAST(N'1996-12-12' AS Date), 444555666, 0, NULL)
INSERT [dbo].[Users] ([User_ID], [User_name], [Password], [Name], [Email], [Phone_Number], [Avatar], [RoleID], [Dob], [NationalID], [Wallet], [StatusID]) VALUES (5, N'staff', N'password123', N'Staff', N'staff2@example.com', N'777888999', N'staff_avatar2.png', 2, CAST(N'1993-03-03' AS Date), 777888999, 0, NULL)
INSERT [dbo].[Users] ([User_ID], [User_name], [Password], [Name], [Email], [Phone_Number], [Avatar], [RoleID], [Dob], [NationalID], [Wallet], [StatusID]) VALUES (11, N'chungldd', N'$2a$10$mY2Xgw/aSlUR4uThPAlvDuEKqz15jIC.uTJQM7M.2JIx9AaZwj5W2', NULL, N'chung01102004@gmail.com', N'0358210806', N'/uploads/1728361798275_313402388_149124661175986_59953819095635420_n.jpg', 1, CAST(N'2024-10-01' AS Date), NULL, NULL, 1)
INSERT [dbo].[Users] ([User_ID], [User_name], [Password], [Name], [Email], [Phone_Number], [Avatar], [RoleID], [Dob], [NationalID], [Wallet], [StatusID]) VALUES (12, N'jong', N'$2a$10$x5D3APov4A34ruXTt2Ku1OTp4z5I/jOdxyj5OlShVL00/ddKgc3XK', NULL, N'chung011020084@gmail.com', N'0358210807', N'/uploads/1728397606732_315774582_194770889703744_1174891184273262713_n.jpg', 1, CAST(N'2024-09-30' AS Date), NULL, NULL, 1)
INSERT [dbo].[Users] ([User_ID], [User_name], [Password], [Name], [Email], [Phone_Number], [Avatar], [RoleID], [Dob], [NationalID], [Wallet], [StatusID]) VALUES (13, N'asd', N'$2a$10$6BaU64hVf6M.yWLFoYCFfu.165HQHt.VZoC0W.IRyj8bujDPOPfJa', NULL, N'chung0110200as4@gmail.com', N'0358210809', N'1728399141700_313198920_457714449802139_3213737886465386738_n.jpg', 1, CAST(N'2024-10-01' AS Date), NULL, NULL, 1)
SET IDENTITY_INSERT [dbo].[Users] OFF
GO
SET IDENTITY_INSERT [dbo].[Land] ON 
INSERT INTO dbo.land ([name],[contact], description, [district], location, path, price, province, ward, user_user_id)
VALUES 
('Nha Hat cua nhung giac mo','0123456789', 'Beautiful land near river', 'District 1', '10.1234, 106.1234', '/path/to/location1', 5000000.00, 'Ho Chi Minh', 'Ward 1', 1),
('Land2','0987654321', 'Land with mountain view', 'District 2', '10.5678, 106.5678', '/path/to/location2', 7000000.00, 'Da Nang', 'Ward 2', 2),
('Land3','0123984756', 'Land near city center', 'District 3', '10.9101, 106.9101', '/path/to/location3', 10000000.00, 'Hanoi', 'Ward 3', 3),
('Land4','0172839456', 'Agricultural land', 'District 4', '11.1234, 107.1234', '/path/to/location4', 3000000.00, 'Can Tho', 'Ward 4', 4),
('Land5','0192837465', 'Commercial land', 'District 5', '12.1234, 108.1234', '/path/to/location5', 12000000.00, 'Binh Duong', 'Ward 5', 5);
SET IDENTITY_INSERT [dbo].[Land] OFF
GO
SET IDENTITY_INSERT [dbo].[Land_Image] ON 
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL], [Name]) VALUES (1, 1, N'https://live.staticflickr.com/5214/5537865137_7809faf72d.jpg', N'Image 1')
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL], [Name]) VALUES (2, 2, N'https://live.staticflickr.com/5176/5538426974_7799968305.jpg', N'Image 2')
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL], [Name]) VALUES (3, 3, N'https://live.staticflickr.com/5176/5538426974_7799968305.jpg', N'Image 3')
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL], [Name]) VALUES (4, 4, N'https://media-cdn-v2.laodong.vn/storage/newsportal/2023/8/26/1233821/Giai-Nhi-1--Nang-Tre.jpg', N'Image 4')
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL], [Name]) VALUES (5, 1, N'https://media-cdn-v2.laodong.vn/storage/newsportal/2023/8/26/1233821/Giai-Nhi-1--Nang-Tre.jpg', N'Image 5')
INSERT [dbo].[Land_Image] ([Image_ID], land_land_id, [Image_URL], [Name]) VALUES (6, 1, N'https://media-cdn-v2.laodong.vn/storage/newsportal/2023/8/26/1233821/Giai-Nhi---Dem-Ngan-.jpg', N'Image 6')
SET IDENTITY_INSERT [dbo].[Land_Image] OFF
GO
SET IDENTITY_INSERT [dbo].[Auction] ON 
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (1, 1, CAST(N'2024-10-09T16:08:09.823' AS DateTime), CAST(N'2024-10-16T16:08:09.823' AS DateTime), 1, 0)
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (2, 2, CAST(N'2024-10-09T16:08:09.823' AS DateTime), CAST(N'2024-10-14T16:08:09.823' AS DateTime), 1, 0)
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (3, 3, CAST(N'2024-10-09T16:08:09.823' AS DateTime), CAST(N'2024-10-19T16:08:09.823' AS DateTime), 1, 0)
INSERT [dbo].[Auction] ([Auction_ID], [LandID], [Start_Time], [End_Time], [StatusID], [Highest_Bid]) VALUES (4, 4, CAST(N'2024-10-09T16:08:09.823' AS DateTime), CAST(N'2024-10-12T16:08:09.823' AS DateTime), 2, 0)
SET IDENTITY_INSERT [dbo].[Auction] OFF
GO
SET IDENTITY_INSERT [dbo].[Payment] ON 

INSERT [dbo].[Payment] ([Payment_ID], [User_ID], [Payment_Amount], [StatusID], [AuctionID]) VALUES (1, 1, 100, 1, 1)
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
INSERT [dbo].[Wishlist] ([Wishlist_ID], [UserID], [AuctionID]) VALUES (1, 1, 1)
INSERT [dbo].[Wishlist] ([Wishlist_ID], [UserID], [AuctionID]) VALUES (2, 2, 2)
INSERT [dbo].[Wishlist] ([Wishlist_ID], [UserID], [AuctionID]) VALUES (3, 3, 3)
INSERT [dbo].[Wishlist] ([Wishlist_ID], [UserID], [AuctionID]) VALUES (4, 4, 4)
SET IDENTITY_INSERT [dbo].[Wishlist] OFF
GO
SET IDENTITY_INSERT [dbo].[Notification] ON 
INSERT [dbo].[Notification] ([Notification_ID], [Title], [Content]) VALUES (1, N'Notification 1', N'Content for notification 1.')
INSERT [dbo].[Notification] ([Notification_ID], [Title], [Content]) VALUES (2, N'Notification 2', N'Content for notification 2.')
INSERT [dbo].[Notification] ([Notification_ID], [Title], [Content]) VALUES (3, N'Notification 3', N'Content for notification 3.')
INSERT [dbo].[Notification] ([Notification_ID], [Title], [Content]) VALUES (4, N'Notification 4', N'Content for notification 4.')
SET IDENTITY_INSERT [dbo].[Notification] OFF
	SET IDENTITY_INSERT [dbo].[Asset_Registration] ON 
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments]) VALUES (2, 1, 1, 1, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 1')
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments]) VALUES (3, 2, 1, 2, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 2')
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments]) VALUES (4, 3, 2, 3, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 3')
INSERT [dbo].[Asset_Registration] ([Document_ID], [LandID], [StatusID], [UserID], [Approval_Date], [Comments]) VALUES (5, 4, 2, 4, CAST(N'2024-10-09T16:08:06.653' AS DateTime), N'Comment 4')
SET IDENTITY_INSERT [dbo].[Asset_Registration] OFF

SET IDENTITY_INSERT [dbo].[Auction_Change_Log] ON 

INSERT [dbo].[Auction_Change_Log] ([log_id], [AuctionID], [action_type], [change_time], [reason], [description]) VALUES (1, 1, N'Create', CAST(N'2024-10-09T16:08:12.420' AS DateTime), N'Initial creation', N'Auction created successfully')
INSERT [dbo].[Auction_Change_Log] ([log_id], [AuctionID], [action_type], [change_time], [reason], [description]) VALUES (2, 2, N'Update', CAST(N'2024-10-09T16:08:12.420' AS DateTime), N'Status update', N'Auction status changed to active')
INSERT [dbo].[Auction_Change_Log] ([log_id], [AuctionID], [action_type], [change_time], [reason], [description]) VALUES (3, 3, N'Cancel', CAST(N'2024-10-09T16:08:12.420' AS DateTime), N'Cancellation', N'Auction cancelled due to unforeseen circumstances')
INSERT [dbo].[Auction_Change_Log] ([log_id], [AuctionID], [action_type], [change_time], [reason], [description]) VALUES (4, 4, N'Update', CAST(N'2024-10-09T16:08:12.420' AS DateTime), N'Price update', N'Highest bid updated')
SET IDENTITY_INSERT [dbo].[Auction_Change_Log] OFF
GO
SET IDENTITY_INSERT [dbo].[Auction_Registration] ON 
INSERT [dbo].[Auction_Registration] ([RegistrationID], [AuctionID], [UserID], [statusID]) VALUES (1, 1, 1, 1)
INSERT [dbo].[Auction_Registration] ([RegistrationID], [AuctionID], [UserID], [statusID]) VALUES (2, 2, 2, 1)
INSERT [dbo].[Auction_Registration] ([RegistrationID], [AuctionID], [UserID], [statusID]) VALUES (3, 3, 3, 2)
INSERT [dbo].[Auction_Registration] ([RegistrationID], [AuctionID], [UserID], [statusID]) VALUES (4, 4, 4, 2)
SET IDENTITY_INSERT [dbo].[Auction_Registration] OFF
GO
SET IDENTITY_INSERT [dbo].[Bids] ON 

INSERT [dbo].[Bids] ([Bid_ID], [RegistrationID], [bid_amount], [bid_time]) VALUES (1, 1, CAST(1000.00 AS Decimal(18, 2)), CAST(N'2024-10-09T16:08:16.447' AS DateTime))
INSERT [dbo].[Bids] ([Bid_ID], [RegistrationID], [bid_amount], [bid_time]) VALUES (2, 2, CAST(1500.00 AS Decimal(18, 2)), CAST(N'2024-10-09T16:08:16.447' AS DateTime))
INSERT [dbo].[Bids] ([Bid_ID], [RegistrationID], [bid_amount], [bid_time]) VALUES (3, 3, CAST(2000.00 AS Decimal(18, 2)), CAST(N'2024-10-09T16:08:16.447' AS DateTime))
INSERT [dbo].[Bids] ([Bid_ID], [RegistrationID], [bid_amount], [bid_time]) VALUES (4, 4, CAST(2500.00 AS Decimal(18, 2)), CAST(N'2024-10-09T16:08:16.447' AS DateTime))
SET IDENTITY_INSERT [dbo].[Bids] OFF
GO

