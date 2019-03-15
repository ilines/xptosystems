package com.senior.xptosystems.repositories;

import com.senior.xptosystems.model.City;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Query("SELECT C FROM City C WHERE C.capital = 1 ORDER BY C.name")
    List<City> findByCapitalOrderByNameAsc();

    // @Query("SELECT C FROM City C WHERE C.ibge_id = ?1")
    City findByIbgeId(Long ibgeId);

    @Query("SELECT C FROM City C WHERE C.uf.id = ?1 ORDER BY C.name")
    List<City> findByUf_id(Long uf_if);

    @Query(
            value = " "
            + "(\n"
            + " SELECT \n"
            + "    'bigger' AS ufcounts, \n"
            + "    u.name, count(*) city_count\n"
            + "    FROM city C\n"
            + "    INNER JOIN Uf AS u ON u.id = C.uf_id \n"
            + "    GROUP BY u.name \n"
            + "    ORDER BY count(*) ASC \n"
            + "    LIMIT 1\n"
            + ") \n"
            + "UNION ALL\n"
            + "(\n"
            + "    SELECT \n"
            + "    'smaller' AS ufcounts,\n"
            + "    u.name, count(*) city_count\n"
            + "    FROM city C\n"
            + "    INNER JOIN Uf AS u ON u.id = C.uf_id \n"
            + "    GROUP BY u.name \n"
            + "    ORDER BY count(*) DESC \n"
            + "    LIMIT 1 \n"
            + " )",
            nativeQuery = true
    )
    List findMinMaxCitiesByUf();

    @Query(value = "SELECT count(*) \n"
            + "FROM city C\n"
            + "WHERE C.uf_id = ?1",
            nativeQuery = true)
    Integer findCountByUf(Long uf_id);

    @Query(value = "SELECT count(*) FROM city",
            nativeQuery = true)
    Integer total();

    @Query(value = ""
            + "            SELECT CONCAT(A.name, '/', UFA.name) AS from_city, CONCAT(B.name, '/', UFB.name) AS to_city, \n"
            + "               111.111 *\n"
            + "                DEGREES(ACOS(LEAST(COS(RADIANS(a.lat))\n"
            + "                     * COS(RADIANS(b.lat))\n"
            + "                     * COS(RADIANS(a.lon- b.lon))\n"
            + "                     + SIN(RADIANS(a.lat))\n"
            + "                     * SIN(RADIANS(b.lat)), 1.0))) AS distance_in_km\n"
            + "              FROM city AS a\n"
            + "              INNER JOIN Uf AS ufa ON ufa.id = A.uf_id \n"
            + "              INNER JOIN city AS b ON a.id <> b.id\n"
            + "              INNER JOIN Uf AS ufb ON ufb.id = B.uf_id \n"
            + "             WHERE a.id = (select c3.id from city c3 where c3.location_point in (select max(location_point) as max from city)) AND b.id = (select id from city where location_point in (select min(location_point) as max from city))"
            + "", nativeQuery = true)
    List findTwoDistanceCities();

    @Query(value = "SELECT * FROM city C INNER JOIN Uf ufs ON ufs.id = c.uf_id WHERE UPPER(c.name) LIKE UPPER('%' || :name || '%' ) ORDER BY ufs.name ASC, C.no_accents ASC", nativeQuery = true)
    List<City> fetchByName(@Param("name") String name);

    @Query(value = "SELECT * FROM city C INNER JOIN Uf ufs ON ufs.id = c.uf_id WHERE UPPER(c.no_accents) LIKE UPPER('%' || :noAccents || '%' ) ORDER BY ufs.name ASC, C.no_accents ASC", nativeQuery = true)
    List<City> fetchByNoAccents(@Param("noAccents") String noAccents);

    @Query(value = "SELECT * FROM city C INNER JOIN Uf ufs ON ufs.id = c.uf_id WHERE UPPER(c.alternative_names) LIKE UPPER('%' || :alternativeNames || '%' ) ORDER BY ufs.name ASC, C.no_accents ASC", nativeQuery = true)
    List<City> fetchByAlternativeNames(@Param("alternativeNames") String alternativeNames);

    @Query(value = "SELECT * FROM city C INNER JOIN Uf ufs ON ufs.id = c.uf_id WHERE UPPER(ufs.name) LIKE UPPER('%' || :ufName || '%' ) ORDER BY ufs.name ASC, C.no_accents ASC", nativeQuery = true)
    List<City> fetchUfByName(@Param("ufName") String ufName);

    @Query(value = "SELECT * FROM city C INNER JOIN mesoregion mr ON mr.id = c.microregions_id WHERE UPPER(mr.name) LIKE UPPER('%' || :mesoregionName || '%' ) ORDER BY ufs.name ASC, MR.name, C.no_accents", nativeQuery = true)
    List<City> fetchMesoregionsByName(@Param("mesoregionName") String mesoregionName);

    @Query(value = "SELECT * FROM city C INNER JOIN microregion mr ON mr.id = c.microregions_id WHERE UPPER(mr.name) LIKE UPPER('%' || :microregionName || '%' ) ORDER BY ufs.name ASC, MR.name, C.no_accents", nativeQuery = true)
    List<City> fetchMicroregionsByName(@Param("microregionName") String microregionName);

    // COUNTS
    List countByUfs();

    List countByName();

    List countByNoAccents();

    List countByAlternativeNames();

    List countByMicrorgions();

    List countByMesoregions();

}

//            SELECT CONCAT(A.name, '/', UFA.name) AS from_city, CONCAT(B.name, '/', UFB.name) AS to_city, 
//               111.111 *
//                DEGREES(ACOS(LEAST(COS(RADIANS(a.lat))
//                     * COS(RADIANS(b.lat))
//                     * COS(RADIANS(a.lon- b.lon))
//                     + SIN(RADIANS(a.lat))
//                     * SIN(RADIANS(b.lat)), 1.0))) AS distance_in_km
//              FROM city AS a
//              INNER JOIN Uf AS ufa ON ufa.id = A.uf_id 
//              INNER JOIN city AS b ON a.id <> b.id
//              INNER JOIN Uf AS ufb ON ufb.id = B.uf_id 
//             WHERE a.id = (select id from city where location_point in (select max(location_point) as max from city)) AND b.id = (select id from city where location_point in (select min(location_point) as max from city))
