package com.tushar.ems.dao;

import com.tushar.ems.model.Grievance;
import com.tushar.ems.model.RegisterUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class EMSDao {
    private static final Logger logger = LoggerFactory.getLogger(EMSDao.class);

    private static final String ID = "Id";
    private static final String NAME = "Name";
    private static final String ADDRESS = "Address";
    private static final String PIN_CODE = "Pincode";
    private static final String CITY = "City";
    private static final String CUSTOMER_ID = "CustomerId";
    private static final String SUBJECT = "Subject";
    private static final String SUMMARY = "Summary";
    private static final String GRIEVANCE_ID = "GrievanceId";
    private static final String CREATE_USER = "INSERT INTO Customer(ID,Name,Address,City,Pincode) VALUES(" +
            ":" + ID + "" +
            ":" + NAME + "" +
            ":" + ADDRESS + "" +
            ":" + CITY + "" +
            ":" + PIN_CODE + ")";
    private static final String REGISTER_GRIEVANCE = "INSERT INTO Grievance(GrievanceId,CustomerId,Subject,Summary) VALUES(" +
            ":" + GRIEVANCE_ID + "" +
            ":" + CUSTOMER_ID + "" +
            ":" + SUBJECT + "" +
            ":" + SUMMARY;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    public EMSDao(final NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Boolean createUser(RegisterUser registerUser, UUID customerId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(ID, customerId)
                .addValue(NAME, registerUser.getName())
                .addValue(ADDRESS, registerUser.getAddress())
                .addValue(CITY, registerUser.getCity())
                .addValue(PIN_CODE, registerUser.getPincode());
        logger.info("Registering user with params {}", parameterSource);
        try {
            namedParameterJdbcTemplate.update(CREATE_USER, parameterSource);
            return true;
        } catch (Exception e) {
            logger.error("Error while registering user {}", e.getMessage());
            return false;
        }
    }

    public Boolean registerGrievance(Grievance grievance, UUID grievanceId) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue(GRIEVANCE_ID, grievanceId)
                .addValue(CUSTOMER_ID, grievance.getCustomerId())
                .addValue(SUBJECT, grievance.getSubject())
                .addValue(SUMMARY, grievance.getSummery());
        logger.info("Registering grievance with params {}", parameterSource);
        try {
            namedParameterJdbcTemplate.update(REGISTER_GRIEVANCE, parameterSource);
            return true;
        } catch (Exception e) {
            logger.error("Error while registering the grievance {}", e.getMessage());
            return false;
        }
    }
}
