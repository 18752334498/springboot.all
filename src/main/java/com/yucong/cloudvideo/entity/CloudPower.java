package com.yucong.cloudvideo.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "vwt_cloud_power")
public class CloudPower {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "vwt_cloud_power_gen")
    @SequenceGenerator(name = "vwt_cloud_power_gen", sequenceName = "vwt_cloud_power_seq", allocationSize = 1)
    private Long id;

    /** 用户id，在后台配置 */
    @Column(name = "user_id")
    private String userId;

    /** 企业id，在后台配置 */
    @Column(name = "corp_id")
    private String corpId;

    /** token，可设置过期时间，由第三方提供 */
    @Column(name = "token")
    private String token;

    /** 修改时间 */
    @Column(name = "create_time")
    private Date createTime;

}
