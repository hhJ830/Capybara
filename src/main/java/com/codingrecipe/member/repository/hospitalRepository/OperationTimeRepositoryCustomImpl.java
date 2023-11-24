package com.codingrecipe.member.repository.hospitalRepository;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.List;

@Repository
public class OperationTimeRepositoryCustomImpl implements OperationTimeRepositoryCustom{
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public String findOperatingHoursByHospitalIdAndDay(String businessId, String dayOfWeek) {
        String jpql = "SELECT oh.openingHours FROM OperatingHours oh WHERE oh.hospital.id = :hospitalId AND oh.dayOfWeek = :dayOfWeek";
        String operatingHours = null;
        System.out.println("OperationTimeRepositoryCustomImpl.findOperatingHoursByHospitalIdAndDay");
        List<String> result = entityManager.createQuery(jpql, String.class)
                .setParameter("hospitalId", businessId)
                .setParameter("dayOfWeek", dayOfWeek)
                .getResultList();

        if (!result.isEmpty()) {
            // 운영 시간 정보가 있을 경우 첫 번째 결과를 반환
            operatingHours = result.get(0);
        } else {
            System.out.println("운영시간 정보 없음");
            // 해당 병원과 요일에 대한 운영 시간 정보가 없을 때
            // 예외를 던지지 않고 공백 문자열을 반환
            operatingHours = "";
        }

        System.out.println(operatingHours);
        return operatingHours != null ? operatingHours : "";
    }

}
