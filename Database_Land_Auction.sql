USE [master]
GO
/****** Object:  Database [LandAuction]    Script Date: 10/6/2024 4:18:53 PM ******/
CREATE DATABASE [LandAuction]
GO
USE [LandAuction]
GO
ALTER DATABASE [LandAuction] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [LandAuction] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [LandAuction] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [LandAuction] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [LandAuction] SET ARITHABORT OFF 
GO
ALTER DATABASE [LandAuction] SET AUTO_CLOSE ON 
GO
ALTER DATABASE [LandAuction] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [LandAuction] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [LandAuction] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [LandAuction] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [LandAuction] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [LandAuction] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [LandAuction] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [LandAuction] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [LandAuction] SET  ENABLE_BROKER 
GO
ALTER DATABASE [LandAuction] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [LandAuction] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [LandAuction] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [LandAuction] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [LandAuction] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [LandAuction] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [LandAuction] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [LandAuction] SET RECOVERY SIMPLE 
GO
ALTER DATABASE [LandAuction] SET  MULTI_USER 
GO
ALTER DATABASE [LandAuction] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [LandAuction] SET DB_CHAINING OFF 
GO
ALTER DATABASE [LandAuction] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [LandAuction] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [LandAuction] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [LandAuction] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
ALTER DATABASE [LandAuction] SET QUERY_STORE = ON
GO
ALTER DATABASE [LandAuction] SET QUERY_STORE (OPERATION_MODE = READ_WRITE, CLEANUP_POLICY = (STALE_QUERY_THRESHOLD_DAYS = 30), DATA_FLUSH_INTERVAL_SECONDS = 900, INTERVAL_LENGTH_MINUTES = 60, MAX_STORAGE_SIZE_MB = 1000, QUERY_CAPTURE_MODE = AUTO, SIZE_BASED_CLEANUP_MODE = AUTO, MAX_PLANS_PER_QUERY = 200, WAIT_STATS_CAPTURE_MODE = ON)
GO
USE [LandAuction]
GO
/****** Object:  Table [dbo].[Asset_Registration]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Asset_Registration](
	[DocumentID] [int] IDENTITY(1,1) NOT NULL,
	[LandID] [int] NOT NULL,
	[StatusID] [int] NULL,
	[UserID] [int] NULL,
	[Approval_Date] [datetime] NULL,
	[Comments] [nvarchar](max) NULL,
	[Path] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[DocumentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Auction]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Auction](
	[AuctionID] [int] IDENTITY(1,1) NOT NULL,
	[LandID] [int] NOT NULL,
	[Start_Time] [datetime] NULL,
	[End_Time] [datetime] NULL,
	[StatusID] [int] NULL,
	[Highest_Bid] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[AuctionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Auction_Change_Log]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Auction_Change_Log](
	[log_id] [int] IDENTITY(1,1) NOT NULL,
	[AuctionID] [int] NOT NULL,
	[action_type] [varchar](100) NOT NULL,
	[change_time] [datetime] NULL,
	[reason] [varchar](max) NOT NULL,
	[description] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[log_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Auction_Registration]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Auction_Registration](
	[RegistrationID] [int] IDENTITY(1,1) NOT NULL,
	[AuctionID] [int] NOT NULL,
	[UserID] [int] NOT NULL,
	[statusID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[RegistrationID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Bids]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Bids](
	[BidID] [int] IDENTITY(1,1) NOT NULL,
	[RegistrationID] [int] NOT NULL,
	[bid_amount] [decimal](18, 2) NOT NULL,
	[bid_time] [datetime] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[BidID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Land]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Land](
	[LandID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [nvarchar](255) NOT NULL,
	[UserID] [int] NOT NULL,
	[Location] [nvarchar](max) NULL,
	[Description] [nvarchar](max) NULL,
	[Price] [float] NULL,
	[Contact] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[LandID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Land_Image]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Land_Image](
	[ImageID] [int] IDENTITY(1,1) NOT NULL,
	[LandID] [int] NOT NULL,
	[Image_URL] [varchar](255) NOT NULL,
	[Name] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[ImageID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[News]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[News](
	[NewsID] [int] IDENTITY(1,1) NOT NULL,
	[Title] [nvarchar](255) NULL,
	[Content] [nvarchar](max) NULL,
	[Image] [varchar](255) NULL,
	[UserID] [int] NOT NULL,
	[Time] [datetime] NULL,
PRIMARY KEY CLUSTERED 
(
	[NewsID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Notification]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Notification](
	[NotificationID] [int] IDENTITY(1,1) NOT NULL,
	[Title] [nvarchar](255) NULL,
	[Content] [nvarchar](max) NULL,
	[UserID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[NotificationID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Payment]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Payment](
	[PaymentID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[Payment_Amount] [float] NOT NULL,
	[StatusID] [int] NULL,
	[AuctionID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[PaymentID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Roles]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Roles](
	[RoleID] [int] IDENTITY(1,1) NOT NULL,
	[Role_Name] [nvarchar](50) NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[RoleID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Status]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Status](
	[StatusID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [varchar](255) NULL,
	[Description] [varchar](255) NULL,
 CONSTRAINT [PK_Status] PRIMARY KEY CLUSTERED 
(
	[StatusID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Task]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Task](
	[TaskID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[AuctionID] [int] NOT NULL,
	[request_time] [datetime] NULL,
	[statusID] [int] NULL,
	[task_name] [nvarchar](max) NULL,
	[description] [nvarchar](max) NULL,
PRIMARY KEY CLUSTERED 
(
	[TaskID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY]
GO
/****** Object:  Table [dbo].[User_Auction_Participation]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[User_Auction_Participation](
	[UserID] [int] NOT NULL,
	[AuctionID] [int] NOT NULL,
PRIMARY KEY CLUSTERED 
(
	[UserID] ASC,
	[AuctionID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Users]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Users](
	[UserID] [int] IDENTITY(1,1) NOT NULL,
	[User_name] [nvarchar](50) NOT NULL,
	[Password] [varchar](255) NOT NULL,
	[Name] [nvarchar](255) NOT NULL,
	[Email] [varchar](255) NOT NULL,
	[Phone_Number] [varchar](15) NOT NULL,
	[Avatar] [varchar](255) NULL,
	[RoleID] [int] NULL,
	[Dob] [date] NULL,
	[NationalID] [int] NULL,
	[Wallet] [float] NULL,
	[StatusID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[UserID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[Phone_Number] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[User_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY],
UNIQUE NONCLUSTERED 
(
	[Email] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[Wishlist]    Script Date: 10/6/2024 4:18:53 PM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[Wishlist](
	[WishlistID] [int] IDENTITY(1,1) NOT NULL,
	[UserID] [int] NOT NULL,
	[AuctionID] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[WishlistID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
ALTER TABLE [dbo].[Asset_Registration] ADD  DEFAULT (getdate()) FOR [Approval_Date]
GO
ALTER TABLE [dbo].[Auction] ADD  DEFAULT (getdate()) FOR [Start_Time]
GO
ALTER TABLE [dbo].[Auction] ADD  DEFAULT ((0)) FOR [Highest_Bid]
GO
ALTER TABLE [dbo].[Auction_Change_Log] ADD  DEFAULT (getdate()) FOR [change_time]
GO
ALTER TABLE [dbo].[News] ADD  DEFAULT (getdate()) FOR [Time]
GO
ALTER TABLE [dbo].[Task] ADD  DEFAULT (getdate()) FOR [request_time]
GO
ALTER TABLE [dbo].[Users] ADD  DEFAULT ((0)) FOR [Wallet]
GO
ALTER TABLE [dbo].[Asset_Registration]  WITH CHECK ADD FOREIGN KEY([LandID])
REFERENCES [dbo].[Land] ([LandID])
GO
ALTER TABLE [dbo].[Asset_Registration]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Asset_Registration]  WITH CHECK ADD  CONSTRAINT [FK_Asset_Registration_Status] FOREIGN KEY([StatusID])
REFERENCES [dbo].[Status] ([StatusID])
GO
ALTER TABLE [dbo].[Asset_Registration] CHECK CONSTRAINT [FK_Asset_Registration_Status]
GO
ALTER TABLE [dbo].[Auction]  WITH CHECK ADD FOREIGN KEY([LandID])
REFERENCES [dbo].[Land] ([LandID])
GO
ALTER TABLE [dbo].[Auction_Change_Log]  WITH CHECK ADD FOREIGN KEY([AuctionID])
REFERENCES [dbo].[Auction] ([AuctionID])
GO
ALTER TABLE [dbo].[Auction_Registration]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Auction_Registration]  WITH CHECK ADD  CONSTRAINT [FK_Auction_Registration_Auction] FOREIGN KEY([AuctionID])
REFERENCES [dbo].[Auction] ([AuctionID])
GO
ALTER TABLE [dbo].[Auction_Registration] CHECK CONSTRAINT [FK_Auction_Registration_Auction]
GO
ALTER TABLE [dbo].[Auction_Registration]  WITH CHECK ADD  CONSTRAINT [FK_Auction_Registration_Status] FOREIGN KEY([statusID])
REFERENCES [dbo].[Status] ([StatusID])
GO
ALTER TABLE [dbo].[Auction_Registration] CHECK CONSTRAINT [FK_Auction_Registration_Status]
GO
ALTER TABLE [dbo].[Bids]  WITH CHECK ADD FOREIGN KEY([RegistrationID])
REFERENCES [dbo].[Auction_Registration] ([RegistrationID])
GO
ALTER TABLE [dbo].[Land]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Land_Image]  WITH CHECK ADD FOREIGN KEY([LandID])
REFERENCES [dbo].[Land] ([LandID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[News]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Notification]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Payment]  WITH CHECK ADD FOREIGN KEY([AuctionID])
REFERENCES [dbo].[Auction] ([AuctionID])
GO
ALTER TABLE [dbo].[Payment]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Payment]  WITH CHECK ADD  CONSTRAINT [FK_Payment_Status] FOREIGN KEY([StatusID])
REFERENCES [dbo].[Status] ([StatusID])
GO
ALTER TABLE [dbo].[Payment] CHECK CONSTRAINT [FK_Payment_Status]
GO
ALTER TABLE [dbo].[Task]  WITH CHECK ADD FOREIGN KEY([AuctionID])
REFERENCES [dbo].[Auction] ([AuctionID])
GO
ALTER TABLE [dbo].[Task]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Task]  WITH CHECK ADD  CONSTRAINT [FK_Task_Status] FOREIGN KEY([statusID])
REFERENCES [dbo].[Status] ([StatusID])
GO
ALTER TABLE [dbo].[Task] CHECK CONSTRAINT [FK_Task_Status]
GO
ALTER TABLE [dbo].[User_Auction_Participation]  WITH CHECK ADD FOREIGN KEY([AuctionID])
REFERENCES [dbo].[Auction] ([AuctionID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[User_Auction_Participation]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
ON DELETE CASCADE
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD FOREIGN KEY([RoleID])
REFERENCES [dbo].[Roles] ([RoleID])
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD  CONSTRAINT [FK_Users_Status] FOREIGN KEY([StatusID])
REFERENCES [dbo].[Status] ([StatusID])
GO
ALTER TABLE [dbo].[Users] CHECK CONSTRAINT [FK_Users_Status]
GO
ALTER TABLE [dbo].[Wishlist]  WITH CHECK ADD FOREIGN KEY([AuctionID])
REFERENCES [dbo].[Auction] ([AuctionID])
GO
ALTER TABLE [dbo].[Wishlist]  WITH CHECK ADD FOREIGN KEY([UserID])
REFERENCES [dbo].[Users] ([UserID])
GO
ALTER TABLE [dbo].[Auction]  WITH CHECK ADD CHECK  (([Highest_Bid]>=(0)))
GO
ALTER TABLE [dbo].[Land]  WITH CHECK ADD CHECK  (([Price]>=(0)))
GO
ALTER TABLE [dbo].[Payment]  WITH CHECK ADD CHECK  (([Payment_Amount]>=(0)))
GO
ALTER TABLE [dbo].[Users]  WITH CHECK ADD CHECK  (([Wallet]>=(0)))
GO
USE [master]
GO
ALTER DATABASE [LandAuction] SET  READ_WRITE 
GO
