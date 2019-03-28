INSERT INTO `vwt_ins_shopping_cart` VALUES ('1', '2', '18752334498', '1', '1');
INSERT INTO `vwt_ins_shopping_cart` VALUES ('2', '2', '18752334498', '1', '3');
INSERT INTO `vwt_ins_shopping_cart` VALUES ('3', '3', '18752334498', '2', '5');

INSERT INTO `vwt_ins_order` VALUES ('1', 'nanjin', 'business hall of nanjing', '2019-03-25 18:34:46', '2', '123456', '18752334498', '18752334498', 'Tom', '1', '1');
INSERT INTO `vwt_ins_order` VALUES ('2', 'nanjin', 'business hall of nanjing', '2019-03-25 18:34:49', '1', '123456', '18752334498', '18752334498', 'Tom', '2', '4');
INSERT INTO `vwt_ins_order` VALUES ('3', 'huaian', 'business hall of huaian', '2019-03-25 18:34:52', '2', '456789', '18752334498', '18752334498', 'Tom', '3', '6');
INSERT INTO `vwt_ins_order` VALUES ('4', 'huaian', 'business hall of huaian', '2019-03-25 18:34:54', '2', '456789', '18752334498', '18752334498', 'Tom', '1', '3');

INSERT INTO `vwt_ins_goods_type` VALUES ('1', 'phone', '1');
INSERT INTO `vwt_ins_goods_type` VALUES ('2', 'iPad', '2');
INSERT INTO `vwt_ins_goods_type` VALUES ('3', 'notebook', '3');

INSERT INTO `vwt_ins_goods_model` VALUES ('1', '2000', 'xioami9_red', '55', '2299', '1');
INSERT INTO `vwt_ins_goods_model` VALUES ('2', '2200', 'xioami9_white', '0', '2499', '1');
INSERT INTO `vwt_ins_goods_model` VALUES ('3', '2400', 'xioami9_blue', '3', '2699', '1');
INSERT INTO `vwt_ins_goods_model` VALUES ('4', '1999', 'honor9_red', '34', '2199', '2');
INSERT INTO `vwt_ins_goods_model` VALUES ('5', '2199', 'honor9_black', '80', '2399', '2');
INSERT INTO `vwt_ins_goods_model` VALUES ('6', '4000', 'lenovo_yellow', '34', '5000', '3');
INSERT INTO `vwt_ins_goods_model` VALUES ('7', '6000', 'ipadmini_gold', '24', '7000', '5');

INSERT INTO `vwt_ins_goods_info` VALUES ('1', 'xiaomi', 'Live for a fever', 'xiaomi9', '2', null, '1', '2019-03-26 12:44:58', 'AABB', '1');
INSERT INTO `vwt_ins_goods_info` VALUES ('2', 'honor', 'Chinese pride', 'honor9', '1', null, '1', '2019-03-26 12:45:01', 'CCDD', '1');
INSERT INTO `vwt_ins_goods_info` VALUES ('3', 'lenovo', 'American brand', 'Y7000', '2', null, '1', '2019-03-26 12:45:05', 'EEFF', '3');
INSERT INTO `vwt_ins_goods_info` VALUES ('4', 'dell', 'What the fuck', 'Y4000', '1', null, '1', '2019-03-26 12:45:08', 'GGHH', '3');
INSERT INTO `vwt_ins_goods_info` VALUES ('5', 'ipad', 'Save the world', 'ipadmini4', '1', null, '1', '2019-03-26 12:45:10', 'MMNN', '2');

INSERT INTO `vwt_ins_buy_limit` VALUES ('1', 'AABB', '5');
INSERT INTO `vwt_ins_buy_limit` VALUES ('2', 'CCDD', '6');
INSERT INTO `vwt_ins_buy_limit` VALUES ('3', 'EEFF', '4');

INSERT INTO `vwt_ins_activity` VALUES ('1', 'Thank for your money!', '2019-03-25 17:41:21', 10,'Activity now is starting!', '2019-04-12 17:41:28');