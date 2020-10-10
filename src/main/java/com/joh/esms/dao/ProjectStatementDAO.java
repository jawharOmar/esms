package com.joh.esms.dao;

import com.joh.esms.domain.model.ProjectStatementD;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ProjectStatementDAO {

    @PersistenceContext
    private EntityManager em;

    public List<ProjectStatementD> findPSByProject(int customerId, int projectId) {
        Query query = em.createNativeQuery("select o.I_CUSTOMER as I_CUSTOMER,'Order' as TYPE,o.I_CUSTOMER_ORDER AS ACTION_ID,if(o.NOTE is null,'empty',o.NOTE) as note,o.INVOICE_ID as Invoice,o.ORDER_TIME as Time,if(o.DISCOUNT is null,0,if(o.DISCOUNT=0,0,o.DISCOUNT*-1) ) as Discount,(o.TOTAL_PRICE-if(o.DISCOUNT is null,0,o.DISCOUNT)) as Debit,if(o.TOTAL_PAYMENT is null,0,o.TOTAL_PAYMENT) as Credit from CUSTOMER_ORDERS o where o.I_CUSTOMER=?1 AND o.I_CUSTOMER_PROJECT=?2\n" +
                "UNION ALL \n" +
                "select p.I_CUSTOMER as I_CUSTOMER,'Payment' as TYPE,p.I_CUSTOMER_PAYMENT as ACTION_ID ,if(p.NOTE is null,'empty',p.NOTE) as note,p.I_CUSTOMER_PAYMENT as Invoice,p.PAYMENT_TIME as Time,if(p.DISCOUNT is null,0,p.DISCOUNT) as Discount,0 as Debit,(p.TOTAL_PAYMENT+if(p.DISCOUNT is null,0,p.DISCOUNT)) as Credit from CUSTOMER_PAYMENTS p where p.I_CUSTOMER=?1 AND  p.I_CUSTOMER_PROJECT=?2\n" +
                "UNION ALL\n" +
                "select r.I_CUSTOMER as I_CUSTOMER,'Return' as TYPE,r.I_CUSTOMER_ORDER_RETURN AS ACTION_ID,'empty' as note,if(r.REFERENCE_INVOICE_ID is null,0,r.REFERENCE_INVOICE_ID) as Invoice,r.CUSTOMER_ORDER_RETURN_TIME as Time,0 as Discount,if(r.TOTAL_PAID is null,0,r.TOTAL_PAID) as Debit,r.TOTAL_PRICE as Credit from CUSTOMER_ORDER_RETURNS r where r.I_CUSTOMER=?1 AND r.I_CUSTOMER_PROJECT=?2");
        query.setParameter(1, customerId);
        query.setParameter(2, projectId);

        List<ProjectStatementD> projectStatementDS = new ArrayList<>();

        List<Object[]> resultList = query.getResultList();

        for (Object[] row : resultList) {
            ProjectStatementD projectStatementD = new ProjectStatementD();
            projectStatementD.setId(Integer.parseInt(row[0] + ""));
            projectStatementD.setType(row[1] + "");
            projectStatementD.setActionId(Integer.parseInt(row[2] + ""));
            projectStatementD.setNote(row[3] + "");
            projectStatementD.setInvoice(Integer.parseInt(row[4] + ""));

            Date date = (Date) row[5];
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            projectStatementD.setTime(sdf.format(date));


            projectStatementD.setDiscount(Double.parseDouble(row[6] + ""));
            projectStatementD.setDebit(Double.parseDouble(row[7] + ""));
            projectStatementD.setCredit(Double.parseDouble(row[8] + ""));

            projectStatementDS.add(projectStatementD);
        }

        return projectStatementDS;
    }

    public List<ProjectStatementD> findPSByProjectAndType(int customerId, int projectId, String actionType) {
        Query query = em.createNativeQuery("select * from (select o.I_CUSTOMER as I_CUSTOMER,'Order' as TYPE,o.I_CUSTOMER_ORDER AS ACTION_ID,if(o.NOTE is null,'empty',o.NOTE) as note,o.INVOICE_ID as Invoice,o.ORDER_TIME as Time,if(o.DISCOUNT is null,0,if(o.DISCOUNT=0,0,o.DISCOUNT*-1) ) as Discount,(o.TOTAL_PRICE-if(o.DISCOUNT is null,0,o.DISCOUNT)) as Debit,if(o.TOTAL_PAYMENT is null,0,o.TOTAL_PAYMENT) as Credit from CUSTOMER_ORDERS o where o.I_CUSTOMER=?1 AND o.I_CUSTOMER_PROJECT=?2\n" +
                "UNION ALL \n" +
                "select p.I_CUSTOMER as I_CUSTOMER,'Payment' as TYPE,p.I_CUSTOMER_PAYMENT as ACTION_ID ,if(p.NOTE is null,'empty',p.NOTE) as note,p.I_CUSTOMER_PAYMENT as Invoice,p.PAYMENT_TIME as Time,if(p.DISCOUNT is null,0,p.DISCOUNT) as Discount,0 as Debit,(p.TOTAL_PAYMENT+if(p.DISCOUNT is null,0,p.DISCOUNT)) as Credit from CUSTOMER_PAYMENTS p where p.I_CUSTOMER=?1 AND p.I_CUSTOMER_PROJECT=?2\n" +
                "UNION ALL\n" +
                "select r.I_CUSTOMER as I_CUSTOMER,'Return' as TYPE,r.I_CUSTOMER_ORDER_RETURN AS ACTION_ID,'empty' as note,if(r.REFERENCE_INVOICE_ID is null,0,r.REFERENCE_INVOICE_ID) as Invoice,r.CUSTOMER_ORDER_RETURN_TIME as Time,0 as Discount,if(r.TOTAL_PAID is null,0,r.TOTAL_PAID) as Debit,r.TOTAL_PRICE as Credit from CUSTOMER_ORDER_RETURNS r where r.I_CUSTOMER=?1 AND r.I_CUSTOMER_PROJECT=?2) A Where TYPE=?3");
        query.setParameter(1, customerId);
        query.setParameter(2, projectId);
        query.setParameter(3, actionType);

        List<ProjectStatementD> projectStatementDS = new ArrayList<>();

        List<Object[]> resultList = query.getResultList();

        for (Object[] row : resultList) {
            ProjectStatementD projectStatementD = new ProjectStatementD();
            projectStatementD.setId(Integer.parseInt(row[0] + ""));
            projectStatementD.setType(row[1] + "");
            projectStatementD.setActionId(Integer.parseInt(row[2] + ""));
            projectStatementD.setNote(row[3] + "");
            projectStatementD.setInvoice(Integer.parseInt(row[4] + ""));
            Date date = (Date) row[5];
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            projectStatementD.setTime(sdf.format(date));
            projectStatementD.setDiscount(Double.parseDouble(row[6] + ""));
            projectStatementD.setDebit(Double.parseDouble(row[7] + ""));
            projectStatementD.setCredit(Double.parseDouble(row[8] + ""));

            projectStatementDS.add(projectStatementD);
        }

        return projectStatementDS;
    }
}
