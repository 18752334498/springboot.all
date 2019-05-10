package com.yucong.mail;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.yucong.util.ZkUtil;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Data
public class MailProperty {

	private final ZkUtil zkUtil;
	private final String ZK = "/mail";
	private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

	private String host;
	private Integer port;
	private String username;
	private String password;
	private String protocol = "smtp";
	private Charset defaultEncoding = DEFAULT_CHARSET;
	private String jndiName;
	private boolean testConnection;
	private Map<String, String> properties = new HashMap<>();

	public MailProperty(ZkUtil zkUtil) {
		this.zkUtil = zkUtil;
	}

	public void init() {
		try {
			log.info("MailProperty start init！！！");

			String host = zkUtil.findData(ZK + "/host");
			String username = zkUtil.findData(ZK + "/username");
			String password = zkUtil.findData(ZK + "/password");
			String mail_smtp_auth = zkUtil.findData(ZK + "/mail.smtp.auth");
			String mail_smtp_starttls_enable = zkUtil.findData(ZK + "/mail.smtp.starttls.enable");
			String mail_smtp_starttls_required = zkUtil.findData(ZK + "/mail.smtp.starttls.required");
			String mail_smtp_ssl_enable = zkUtil.findData(ZK + "/mail.smtp.ssl.enable");

			if (StringUtils.isEmpty(host) || StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
				log.info("MailProperty failed init！！！");
				System.exit(1);
			}

			if (StringUtils.isNotEmpty(host)) {
				this.host = host;
			}
			if (StringUtils.isNotEmpty(username)) {
				this.username = username;
			}
			if (StringUtils.isNotEmpty(password)) {
				this.password = password;
			}
			if (StringUtils.isNotEmpty(mail_smtp_auth)) {
				String[] split = mail_smtp_auth.split("=");
				properties.put(split[0], split[1]);
			}
			if (StringUtils.isNotEmpty(mail_smtp_starttls_enable)) {
				String[] split = mail_smtp_starttls_enable.split("=");
				properties.put(split[0], split[1]);
			}
			if (StringUtils.isNotEmpty(mail_smtp_starttls_required)) {
				String[] split = mail_smtp_starttls_required.split("=");
				properties.put(split[0], split[1]);
			}
			if (StringUtils.isNotEmpty(mail_smtp_ssl_enable)) {
				String[] split = mail_smtp_ssl_enable.split("=");
				properties.put(split[0], split[1]);
			}

			log.info("mail连接属性，host={}", this.host);
			log.info("mail连接属性，username={}", this.username);
			log.info("mail连接属性，password={}", this.password.substring(0, 2) + "******");
			log.info("mail连接属性，properties={}", this.properties);

			log.info("MailProperty end init！！！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
