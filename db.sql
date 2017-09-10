CREATE TABLE `m_user` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'surrogate key',
  `email_address` tinytext COMMENT 'メールアドレス＝ログイン名',
  `name` tinytext COMMENT 'ユーザーの名前',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8 COMMENT='ユーザー情報の管理';
SELECT * FROM demo.m_user;


CREATE TABLE `m_role` (
  `id` int(11) NOT NULL,
  `name` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
