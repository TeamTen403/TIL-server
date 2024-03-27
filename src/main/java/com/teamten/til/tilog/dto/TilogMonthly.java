package com.teamten.til.tilog.dto;

import java.io.Serializable;
import java.time.YearMonth;
import java.util.List;

public class TilogMonthly implements Serializable {
	private YearMonth ym;
	private List<TilogInfo> tilogList;

}
