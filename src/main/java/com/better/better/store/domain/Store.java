package com.better.better.store.domain;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "store_info")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Store {

    @Id
    @Column(name = "STORE_ID")
    private String storeId;

    @Column(name = "STORE_NAME")
    private String storeName;

    @Column(name = "BRANCH_NAME")
    private String branchName;

    @Column(name = "CATEGORY_L_CODE")
    private String categoryLCode;

    @Column(name = "CATEGORY_L_NAME")
    private String categoryLName;

    @Column(name = "CATEGORY_M_CODE")
    private String categoryMCode;

    @Column(name = "CATEGORY_M_NAME")
    private String categoryMName;

    @Column(name = "CATEGORY_S_CODE")
    private String categorySCode;

    @Column(name = "CATEGORY_S_NAME")
    private String categorySName;

    @Column(name = "INDUSTRY_CODE")
    private String industryCode;

    @Column(name = "INDUSTRY_NAME")
    private String industryName;

    @Column(name = "SIDO_CODE")
    private String sidoCode;

    @Column(name = "SIDO_NAME")
    private String sidoName;

    @Column(name = "SIGUNGU_CODE")
    private String sigunguCode;

    @Column(name = "SIGUNGU_NAME")
    private String sigunguName;

    @Column(name = "ADMIN_DONG_CODE")
    private String adminDongCode;

    @Column(name = "ADMIN_DONG_NAME")
    private String adminDongName;

    @Column(name = "LEGAL_DONG_CODE")
    private String legalDongCode;

    @Column(name = "LEGAL_DONG_NAME")
    private String legalDongName;

    @Column(name = "JIBUN_CODE")
    private String jibunCode;

    @Column(name = "LAND_DIV_CODE")
    private String landDivCode;

    @Column(name = "LAND_DIV_NAME")
    private String landDivName;

    @Column(name = "JIBUN_MAIN_NO")
    private Integer jibunMainNo;

    @Column(name = "JIBUN_SUB_NO")
    private Integer jibunSubNo;

    @Column(name = "JIBUN_ADDRESS")
    private String jibunAddress;

    @Column(name = "ROAD_CODE")
    private String roadCode;

    @Column(name = "ROAD_NAME")
    private String roadName;

    @Column(name = "BUILDING_MAIN_NO")
    private Integer buildingMainNo;

    @Column(name = "BUILDING_SUB_NO")
    private Integer buildingSubNo;

    @Column(name = "BUILDING_MANAGE_NO")
    private String buildingManageNo;

    @Column(name = "BUILDING_NAME")
    private String buildingName;

    @Column(name = "ROAD_ADDRESS")
    private String roadAddress;

    @Column(name = "OLD_ZIP_CODE")
    private String oldZipCode;

    @Column(name = "NEW_ZIP_CODE")
    private String newZipCode;

    @Column(name = "DONG_INFO")
    private String dongInfo;

    @Column(name = "FLOOR_INFO")
    private String floorInfo;

    @Column(name = "UNIT_INFO")
    private String unitInfo;

    @Column(name = "LNG")
    private Double lng;

    @Column(name = "LAT")
    private Double lat;
}

