package com.pg.annotat.onetoMany.bi.report;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Report implements Serializable{
    private static final long serialVersionUID = 9146156921169669644L;
    private Integer id;
    private String name;
    private Set<ReportSummary> reportSummaryList  = new HashSet<ReportSummary>();
}

class ReportSummary implements Serializable{
	private ReportSummaryId id;
	private String name;
}

class ReportSummaryId implements Serializable{
	private Integer id;
	private Report report;
}
