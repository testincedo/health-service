# **Healthcare Analytics Dashboard APIs**

#### **Overview**

This project implements a Healthcare Analytics Dashboard backend using Spring Boot (Java) and MySQL/H2. It allows healthcare
institutions to query, filter, and analyze patient and operational data in real-time with minimal latency.

### Core features include:  

* Querying patient records with insights like visits, diagnoses, and doctors.
* Defining and saving patient cohort selection sets.
* Aggregating statistics (PatientAgg) such as total visits, recent visits, and top diagnoses/doctors.
* Exposing REST APIs (with Swagger) for easy integration.

#### **Tech Stack**

* Backend Framework: Spring Boot
* Database: MySQL (H2 for local dev/testing)
* ORM: Spring Data JPA + Hibernate
* API Style: REST (Swagger-UI enabled)
* Build Tool: Maven 


**Run locally:**
`# Build
mvn clean install &
mvn spring-boot:run`

**Swagger UI**

Once running, explore APIs at:
http://localhost:8080/swagger-ui.html

Example: 

/api/v1/patients/search Sample Request to fetch all Male patients in last one year

`{ "genders": [ "M" ], "minVisitsTotal": 0, "minVisitsLast365d": 0, "withinDays": 365 } `

/api/v1/cohorts/execute Sample request to fetch all patientIds on ageBasedCohert 

`{ "name": "Age Based Request", "strategyKey": "ageBasedCohortStrategy", "diseaseCode": "string", "minAge": 0, "maxAge": 99 }`

**Database Schema:** resources/data/Database-ER.png
**Table Structure:** resources/data/DatabaseDesign.txt


#### **Technical Decisions**

**Spring Data JPA vs Native SQL**

* Used JPA for portability & cleaner queries.
* For complex aggregates (visitsLast365d), had to use either separate JPQL queries or native SQL due to JPQL’s lack of CASE WHEN.
* Trade-off: readability + portability vs performance.

**Aggregated Table (PatientAgg)**

* Chose to persist aggregates in a separate table for faster queries.
* Requires batch job/scheduled refresh.
* Trade-off: extra storage vs query performance.

**H2 for Local Development**

* Easier setup, in-memory testing.
* Some SQL behaviors differ from MySQL (e.g., ORDER BY rules).
* Trade-off: developer convenience vs production fidelity.

**Many-to-Many Mapping (VisitDiagnosis)**

* Used an explicit join entity instead of @ManyToMany.
* Allows storing primaryFlag attribute.
* Trade-off: more boilerplate vs flexibility.

**JSON columns in PatientAgg**

* Used JSON type (MySQL 5.7+/Postgres).
* Limited querying capabilities compared to normalized schema.
* Trade-off: storage simplicity vs advanced query support.

**Criteria API for Search**

* Allows dynamic filtering (genders, withinDays, diagnosisCodes).
* More verbose and harder to maintain than JPQL.
* Trade-off: flexibility vs readability.



**Future Improvements**

* Introduce materialized views or ETL jobs for heavy aggregations.
* Add caching layer (Redis) for frequent queries.
* Support GraphQL API for flexible queries.
* Integrate Auth0 / JWT authentication for secure access.
* Add CI/CD pipeline with integration tests across H2 + MySQL.