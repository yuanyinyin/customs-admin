package com.nteport.admin.dto;

import com.nteport.admin.entity.*;
import lombok.Data;

import java.util.List;
@Data
public class QpDecHeadDto extends QpDecHead {

    private QpDecFreeTxt qpDecFreeTxt;

    private List<QpDecList> qpDecLists;

    private List<QpDecContainer> qpDecContainerList;

    private List<QpDecLicenseDocu> qpDecLicenseDocuList;

    private List<QpDecRequestCert> qpDecRequestCertList;




}
