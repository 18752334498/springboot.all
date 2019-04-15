package com.yucong.cloudvideo.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "vwt_cloud_meeting")
public class CloudMeeting {

    /** 主键 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "vwt_cloud_meeting_gen")
    @SequenceGenerator(name = "vwt_cloud_meeting_gen", sequenceName = "vwt_cloud_meeting_seq", allocationSize = 1)
    private Long id;

    /** 会议id，由第三方提供 */
    @Column(name = "meeting_id")
    private String meetingId;

    /** 开会人员 */
    @OneToMany(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER, mappedBy = "cloudMeetingId")
    private List<MeetingMember> meetingMembers;

    /** 会议创建人，关联CloudPower中的userId */
    @Column(name = "creator_id")
    private String creatorId;

    /** 企会议类型：1即时会议，2预约会议 */
    @Column(name = "meeting_type")
    private String meetingType;

    /** 预约时间 */
    @Column(name = "order_time")
    private Date orderTime;

    /** 预约会议是否发出通知：0未通知，1已通知 */
    @Column(name = "status")
    private String status;

    /** 创建时间 */
    @Column(name = "create_time")
    private Date createTime;

}
