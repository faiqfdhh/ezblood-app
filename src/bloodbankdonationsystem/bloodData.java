/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bloodbankdonationsystem;

import java.sql.Date;


public class bloodData {

    private final String donorName;
    private final String donorId;
    private final String bloodType;
    private final Double pintsDonated;
    private final String batchNo;
    private final Date date;

    public bloodData(String donorName, String donorId, String bloodType,
                        Double pintsDonated, String batchNo, Date date) {
        this.donorName = donorName;
        this.donorId = donorId;
        this.bloodType = bloodType;
        this.pintsDonated = pintsDonated;
        this.batchNo = batchNo;
        this.date = date;
    }

    public String getDonorName() {
        return donorName;
    }

    public String getDonorId() {
        return donorId;
    }

    public String getBloodType() {
        return bloodType;
    }

    public Double getPintsDonated() {
        return pintsDonated;
    }

    public String getBatchNo() {
        return batchNo;
    }

    public Date getDate() {
        return date;
    }
}
